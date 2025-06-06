angular
  .module('rideMapApp', [])
  .controller('MapController', ['$scope', '$http', function($scope, $http) {
    // ─────────────────────────────────────
    // 전역 변수
    // ─────────────────────────────────────
    let graph = {}, nodes = {}, routeLine, debounce;

    // ─────────────────────────────────────
    // 1) util 함수들
    // ─────────────────────────────────────
    function haversine(lat1, lon1, lat2, lon2) {
      const R = 6371, toRad = x => x * Math.PI / 180;
      const dLat = toRad(lat2 - lat1), dLon = toRad(lon2 - lon1);
      const a = Math.sin(dLat/2)**2 +
        Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
        Math.sin(dLon/2)**2;
      return 2 * R * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    function calcPreference(tags) {
      let score = 0;
      const w = {
        cycleway:10, residential:8, tertiary:6,
        secondary:4, primary:2, trunk:-5, motorway:-5
      };
      score += w[tags.highway] || 0;
      const surf = (tags.surface||'').toLowerCase();
      if (['asphalt','paved','concrete'].includes(surf)) score += 3;
      else if (surf === 'compacted') score += 1;
      else if (['dirt','ground'].includes(surf)) score -= 5;
      if (tags.incline) {
        const inc = parseFloat(tags.incline);
        if (!isNaN(inc)) score -= Math.min(Math.abs(inc), 10);
      }
      return score;
    }

    function buildGraph(gj) {
      graph = {};
      nodes = {};
      gj.features.forEach(f => {
        const tags = f.properties.tags || {};
        const basePref = calcPreference(tags);
        const coords = f.geometry.coordinates;
        // reference 보너스 함수
        const refBonus = pt => ($scope.references || []).reduce((sum, r) => {
          const d = haversine(pt[1], pt[0], r.latitude, r.longitude);
          return sum + (d < 0.5 ? 1 : 0);
        }, 0);

        for (let i = 0; i < coords.length - 1; i++) {
          const A = coords[i], B = coords[i+1];
          const keyA = A.join(','), keyB = B.join(',');
          nodes[keyA] = A; nodes[keyB] = B;
          const d = haversine(A[1], A[0], B[1], B[0]);
          const pref = basePref + refBonus([
            (A[0] + B[0]) / 2,
            (A[1] + B[1]) / 2
          ]);
          const weight = d / (1 +  pref);
          graph[keyA] = graph[keyA] || [];
          graph[keyA].push({ to: keyB, weight });
          graph[keyB] = graph[keyB] || [];
          graph[keyB].push({ to: keyA, weight });
        }
      });
    }

    function dijkstra(start, goal) {
      const dist = {}, prev = {};
      dist[start] = 0;
      const pq = [{ node:start, cost:0 }];
      while (pq.length) {
        pq.sort((a,b)=>a.cost-b.cost);
        const { node, cost } = pq.shift();
        if (node === goal) break;
        if (cost > dist[node]) continue;
        (graph[node]||[]).forEach(edge => {
          const nc = cost + edge.weight;
          if (dist[edge.to] == null || nc < dist[edge.to]) {
            dist[edge.to] = nc;
            prev[edge.to] = node;
            pq.push({ node: edge.to, cost: nc });
          }
        });
      }
      const path = [], u = goal;
      for (let cur = u; cur; cur = prev[cur]) path.push(cur);
      return path.reverse();
    }

    function findNearestNode(lat, lon) {
      let best, bestD = Infinity;
      Object.entries(nodes).forEach(([key,pt]) => {
        const d = haversine(lat, lon, pt[1], pt[0]);
        if (d < bestD) { bestD = d; best = key; }
      });
      return best;
    }

    // ─────────────────────────────────────
    // 2) 지도 초기화
    // ─────────────────────────────────────
    const map = L.map('map').setView([0,0],2);
    window.map = map;
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png')
      .addTo(map);

    // 내 위치로 센터 이동
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(pos => {
        const [lat,lng] = [pos.coords.latitude, pos.coords.longitude];
        $scope.$apply(()=> { $scope.lat = lat; $scope.lng = lng; });
        map.setView([lat,lng], 13);
        L.marker([lat,lng]).addTo(map)
          .bindPopup('여기에 있습니다').openPopup();
      }, ()=>{}, { enableHighAccuracy:true });
    }

    $scope.getLocation = function() {
          if (!navigator.geolocation) {
            return alert('이 브라우저는 위치 정보를 지원하지 않습니다.');
          }
          navigator.geolocation.getCurrentPosition(
            function(pos) {
              const lat = pos.coords.latitude;
              const lng = pos.coords.longitude;
              // Angular digest cycle 에 반영
              $scope.$apply(() => {
                // (선택) 범용으로 쓰기 위해 모델에 저장
                $scope.lat = lat;
                $scope.lng = lng;
              });
              // 지도의 뷰를 현재 위치로 이동
              map.setView([lat, lng], 15);
              // (선택) 현재 위치에 마커/원 마커 표시
              L.circleMarker([lat, lng], {
                radius: 8,
                color: '#007bff',
                fillColor: '#007bff',
                fillOpacity: 0.7
              })
              .addTo(map)
              .bindPopup('현재 위치')
              .openPopup();
            },
            function(err) {
              console.error('위치 정보를 가져오지 못했습니다.', err);
              alert('위치 정보를 가져오지 못했습니다.');
            },
            {
              enableHighAccuracy: true,
              timeout: 5000,
              maximumAge: 0
            }
          );
        };

    // ─────────────────────────────────────
    // 3) 체크포인트 불러오기
    // ─────────────────────────────────────
    $scope.checkpoints = [];
    $http.get('/api/checkpoints').then(r => {
      $scope.checkpoints = r.data.sort((a,b)=>a.number-b.number);
      r.data.forEach(cp => {
        const lat = cp.latitude, lng = cp.longitude;
        L.marker([lat,lng]).addTo(map)
         .bindPopup(`${cp.name} (#${cp.number})`);
      });
    });

    // ─────────────────────────────────────
    // 4) 도로 + 그래프 로드
    // ─────────────────────────────────────

    function getClampedBBox() {
      const b = map.getBounds();
      const clamp = (v, min, max) => Math.min(Math.max(v, min), max);
      // ε 만큼 살짝 안쪽으로 클램핑
      const ε = 1e-6;
      const s = clamp(b.getSouth() + ε, -90 + ε, 90 - ε);
      const w = clamp(b.getWest()  + ε, -180 + ε, 180 - ε);
      const n = clamp(b.getNorth() - ε, -90 + ε, 90 - ε);
      const e = clamp(b.getEast()  - ε, -180 + ε, 180 - ε);
      return [s, w, n, e];
    }


    function makeOverpassQuery([s, w, n, e]) {
      return `
        [out:json][timeout:25];
        way["highway"](${s},${w},${n},${e});
        out geom;
      `;
    }
    async function loadRoads() {
      const bbox = getClampedBBox();
      const q    = makeOverpassQuery(bbox);
      const resp = await fetch('https://overpass-api.de/api/interpreter', {
        method:'POST', headers:{'Content-Type':'text/plain'}, body:q
      });
      if (!resp.ok) {
        console.error('Overpass 실패:', await resp.text());
        return;
      }
      const osm = await resp.json();
      const gj  = osmtogeojson(osm);

      if (window.roadsLayer) map.removeLayer(window.roadsLayer);
      window.roadsLayer = L.geoJSON(gj, {
        style: ()=>({ color:'#3388ff', weight:4, opacity:0.6 }),
        onEachFeature: (feat, layer) => {
          layer.on('click', ()=> {
            $scope.$apply(()=> {
              $scope.selectedRoad = {
                osmId: feat.properties.id,
                preference: 3,
                notes: ''
              };
            });
            document.getElementById('road-form').style.display = 'block';
          });
        }
      }).addTo(map);

      buildGraph(gj);
    }
    loadRoads();
    map.on('moveend', () => {
      clearTimeout(debounce);
      debounce = setTimeout(() => {
        if (map.getZoom() >= 8) {   // 예: zoom 12 이상일 때만 로드
          loadRoads();
        }
      }, 1000);
    });

    // ─────────────────────────────────────
    // 5) 경로 생성
    // ─────────────────────────────────────
    $scope.makeRoute = function() {
      if (routeLine) map.removeLayer(routeLine);
      const cps = $scope.checkpoints;
      if (!cps.length) return alert('체크포인트가 없습니다.');

      // 순회: 1→2→…→N→1
      const seq = cps.concat([cps[0]]);
      let allLatLngs = [];
      for (let i=0; i<seq.length-1; i++) {
        const a = seq[i], b = seq[i+1];
        const n1 = findNearestNode(a.latitude, a.longitude);
        const n2 = findNearestNode(b.latitude, b.longitude);
        const path = dijkstra(n1, n2);
        allLatLngs = allLatLngs.concat(
          path.map(key => {
            const [lng,lat] = key.split(',').map(parseFloat);
            return [lat, lng];
          })
        );
      }

      routeLine = L.polyline(allLatLngs, { color:'red', weight:5 })
                    .addTo(map);

      const routeLayer = L.geoJSON(routeGeoJson, {
                style: ()=>({ color:'#ff7800', weight:3 })
              });
      window.userRouteLayer.addLayer(routeLayer);

      map.fitBounds(routeLine.getBounds());
    };

$scope.mapConfigs = [];
    function loadConfigs(){
      $http.get('/api/mapconfigs')
        .then(r=> $scope.mapConfigs = r.data);
    }
    loadConfigs();

   window.userRouteLayer  = new L.FeatureGroup().addTo(map);  // 폴리라인 전용
   window.checkpointLayer = new L.FeatureGroup().addTo(map);  // 마커 전용

   // 2) Draw 컨트롤에 marker 와 polyline 활성화
   map.addControl(new L.Control.Draw({
     draw: {
       polyline: true,    // 경로 그리기
       marker:   true,    // 체크포인트 추가
       polygon:  false,
       circle:   false,
       rectangle:false,
       circlemarker:false
     },
     edit: {
       // 편집 모드에서 두 레이어 모두 선택 가능
       featureGroup: new L.FeatureGroup([window.userRouteLayer, window.checkpointLayer])
     }
   }));

   // 3) 그리기 완료 이벤트 핸들러
   map.on(L.Draw.Event.CREATED, function(e) {
     const layer = e.layer;
     if (e.layerType === 'polyline') {
       // 경로를 userRouteLayer 에 추가
       window.userRouteLayer.addLayer(layer);
     }
     else if (e.layerType === 'marker') {
       // 체크포인트를 checkpointLayer 에 추가
       window.checkpointLayer.addLayer(layer);
     }
   });

   // 4) 레이어별 삭제 함수 (필요할 때 버튼 등에 연결)
   $scope.clearRoutes = function() {
     window.userRouteLayer.clearLayers();
   };
   $scope.clearCheckpoints = function() {
     window.checkpointLayer.clearLayers();
   };

   // 5) 저장 시에는 userRouteLayer 만 서버로 전송
   $scope.saveMap = function(){
     if (!$scope.mapName.trim()) {
       return alert('먼저 맵 이름을 입력하세요.');
     }
     if (window.userRouteLayer.getLayers().length === 0) {
       return alert('저장할 경로가 없습니다.');
     }

     const payload = {
       name:    $scope.mapName,
       geoJson: JSON.stringify(window.userRouteLayer.toGeoJSON())
     };
     $http.post('/api/mapconfigs', payload)
       .then(() => {
         alert('맵이 저장되었습니다.');
         $scope.mapName = '';
         loadConfigs();
       })
       .catch(err => {
         console.error(err);
         alert('저장 중 오류가 발생했습니다.');
       });
   };

    // --- 저장된 맵 불러오기 ---
    let savedLayer = null;
    $scope.loadMap = function(id){
      $http.get('/api/mapconfigs/'+id)
        .then(r => {
          const gj = JSON.parse(r.data.geoJson);
          // ① 이전에 로드된 레이어가 있으면 제거
          if (savedLayer) map.removeLayer(savedLayer);
          // ② 선택한 맵만 로드
          savedLayer = L.geoJSON(gj, {
            style: ()=>({ color:'#ff7800', weight:3 })
          }).addTo(map);
          map.fitBounds(savedLayer.getBounds());
        })
        .catch(_ => alert('불러오기 실패'));
    };

    // 꺼질 때 정리
    $scope.$on('$destroy', ()=> map.remove());
  }]);
