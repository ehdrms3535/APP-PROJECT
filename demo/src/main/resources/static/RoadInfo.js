// /js/mapController.js
angular.module('rideMapApp')
  .controller('MapController', ['$scope', '$http', function($scope, $http) {
    const map = L.map('ref-map').setView([37.5665, 126.9780], 13);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map);

    // 1) Overpass → GeoJSON → 스타일맵
    async function loadRoadsWithPreference() {
      const query = `
        [out:json][timeout:25];
        way["highway"]["surface"];
        out body geom tags;
      `;
      const res = await fetch('https://overpass-api.de/api/interpreter', { method:'POST', body:query });
      const osm = await res.json();
      const gj  = osmtogeojson(osm);

      L.geoJSON(gj, {
        style:feature => {
                    const tags = feature.properties.tags || {};
                    const pref = calcPreference(tags);
                    // optional: feature.properties.preference = pref;
                    // 색을 선호도에 따라 결정
                    const color = pref >= 8   ? '#006837'
                                : pref >= 5   ? '#31a354'
                                : pref >= 0   ? '#78c679'
                                : pref >= -5  ? '#c2e699'
                                :               '#ffffcc';
                    return {
                      color,
                      weight: Math.max(1, (pref + 5) / 3),
                      opacity: 0.8
                    };
                  },
        onEachFeature: (feature, layer) => {
          // 클릭 시 선택
          layer.on('click', () => {
            $scope.$apply(() => {
              $scope.selectedRoad = {
                osmId: feature.properties.id,
                name: feature.properties.tags.name || '',
                notes: ''
              };
            });
            document.getElementById('road-form').scrollIntoView({behavior:'smooth'});
          });
        }
      }).addTo(map);
    }
    loadRoadsWithPreference();

    // 2) 저장된 도로 정보 불러오기
    $scope.savedRoads = [];
    function loadSavedRoads() {
      $http.get('/api/roads').then(res => {
        $scope.savedRoads = res.data;
      });
    }
    loadSavedRoads();

    // 3) 도로 정보 저장
    $scope.saveRoadInfo = function() {
      $http.post('/api/roads', $scope.selectedRoad)
        .then(res => {
          loadSavedRoads();
          delete $scope.selectedRoad;
          alert('도로 정보 저장 완료');
        })
        .catch(err => alert('저장 실패: '+err));
    };
  }]);
