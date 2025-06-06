// /js/reference.js
(function(){
  'use strict';
  angular.module('rideMapApp', [])
  .controller('MapController',['$scope','$http',function($scope,$http){
    // --- 1) Reference 로드 ---
    $scope.references = [];
    $http.get('/api/references').then(r=> $scope.references = r.data);

    // --- 2) 지도 초기화 (id="ref-map") ---
    const map = L.map('map').setView([37.5665,126.9780],13);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png')
      .addTo(map);

    // --- 3) calcPreference 정의 ---
    function calcPreference(tags){
      // 예시 로직
      let score=0;
      const w={cycleway:10,residential:8,tertiary:6,secondary:4,primary:2,trunk:-5,motorway:-5};
      score+=w[tags.highway]||0;
      const surf=(tags.surface||'').toLowerCase();
      if(['asphalt','paved','concrete'].includes(surf)) score+=3;
      else if(surf==='compacted') score+=1;
      else if(['dirt','ground'].includes(surf)) score-=5;
      if(tags.incline){
        const inc=parseFloat(tags.incline);
        if(!isNaN(inc)) score-=Math.min(Math.abs(inc),10);
      }
      return score;
    }

    // --- 4) 도로 레이어 & 클릭 핸들러 ---
    async function loadRoads(){
      const q=`
        [out:json][timeout:25];
        way["highway"]["surface"];
        out body geom tags;
      `;
      const resp=await fetch('https://overpass-api.de/api/interpreter',{
        method:'POST',body:q
      });
      const osm=await resp.json();
      const gj=osmtogeojson(osm);
      L.geoJSON(gj,{
        style:f=>{
          const tags=f.properties.tags||{};
          const p=calcPreference(tags);
          const c=p>=8? '#006837':p>=5? '#31a354':p>=0? '#78c679':p>=-5? '#c2e699':'#ffffcc';
          return {color:c,weight:Math.max(1,(p+5)/3),opacity:0.8};
        },
        onEachFeature:(feat,layer)=>{
          layer.on('click',()=>{
            $scope.$apply(()=>{
              $scope.selectedRoad={
                osmId: feat.properties.id,
                name: feat.properties.tags.name||'Unnamed',
                notes: ''
              };
            });
            document.getElementById('road-form')
                    .scrollIntoView({behavior:'smooth'});
          });
        }
      }).addTo(map);
    }
    loadRoads();

    // --- 5) 저장된 도로 로드/저장 ---
    $scope.savedRoads=[];
    function loadSaved(){ $http.get('/api/roads').then(r=> $scope.savedRoads=r.data); }
    loadSaved();
    $scope.saveRoadInfo=function(){
      $http.post('/api/roads',$scope.selectedRoad)
        .then(_=>{ loadSaved(); delete $scope.selectedRoad; })
        .catch(()=>alert('저장 실패'));
    };


  }]);
})();
