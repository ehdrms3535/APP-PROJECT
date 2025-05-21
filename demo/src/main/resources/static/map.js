angular
  .module('rideMapApp', [])
  .controller('MapController', ['$scope', '$http', function($scope, $http) {
    // 1) 맵을 딱 한 번만 생성
    window.map = L.map('map').setView([0, 0], 2);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png')
      .addTo(window.map);

    // 2) 위치를 얻으면 뷰만 옮기기
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        pos => {
          const lat = pos.coords.latitude;
          const lng = pos.coords.longitude;
          console.log(`내 위치: ${lat}, ${lng}`);
          $scope.$apply(() => {
            $scope.lat = lat;
            $scope.lng = lng;
          });
          // 맵 재초기화가 아니라 뷰만 이동
          window.map.setView([lat, lng], 13);
          L.marker([lat, lng]).addTo(window.map)
            .bindPopup("여기에 있습니다").openPopup();
        },
        err => console.error("위치 정보를 가져오지 못했습니다.", err),
        { enableHighAccuracy: true }
      );
    }

    // 3) 이후 로드·이동 이벤트에서는 window.map만 사용
    function getBbox() {
      const b = window.map.getBounds();
      return [b.getSouth(), b.getWest(), b.getNorth(), b.getEast()];
    }

    function makeOverpassQuery(bbox) {
      const [s, w, n, e] = bbox;
      return `[out:json][timeout:25];` +
             `way["highway"](${s},${w},${n},${e});` +
             `out body;>;out skel qt;`;
    }

    async function loadRoads() {
      const resp = await $http.post(
        'https://overpass-api.de/api/interpreter',
        makeOverpassQuery(getBbox()),
        { headers: {'Content-Type':'text/plain'} }
      );
      const geojson = osmtogeojson(resp.data);
      if (window.roadsLayer) window.map.removeLayer(window.roadsLayer);
      window.roadsLayer = L.geoJSON(geojson, { style:{weight:2} })
        .addTo(window.map);
    }

    loadRoads();
    let debounce;
    window.map.on('moveend', () => {
      clearTimeout(debounce);
      debounce = setTimeout(loadRoads, 500);
    });

    window.map.on('click', e => {
      const {lat, lng} = e.latlng;
      L.popup()
       .setLatLng(e.latlng)
       .setContent(`위도: ${lat.toFixed(5)}<br>경도: ${lng.toFixed(5)}`)
       .openOn(window.map);
    });

    $scope.$on('$destroy', () => {
      window.map.remove();
      window.map = null;
    });
  }]);
