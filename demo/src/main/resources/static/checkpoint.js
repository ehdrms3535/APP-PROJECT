(function() {
  'use strict';

  // 'rideMapApp' 모듈이 이미 선언되어 있으면 아래 배열 [] 제거
  angular.module('rideMapApp', [])
    .controller('CheckpointController', ['$scope', '$http', function($scope, $http) {
      // 초기 모델 설정
      $scope.checkpoint = { name: '', number: null };
      $scope.checkpoints = [];

      // 서버에서 체크포인트 목록 불러오기
      $http.get('/api/checkpoints')
        .then(function(response) {
          $scope.checkpoints = response.data;
        })
        .catch(function(error) {
          console.error('체크포인트 로드 실패:', error);
        });

      // 새로운 체크포인트 저장 함수
      $scope.saveCheckpoint = function() {
        if (!$scope.checkpoint.name || $scope.checkpoint.number == null) return;
        $http.post('/api/checkpoints', $scope.checkpoint)
          .then(function(response) {
            // 저장된 데이터 목록에 추가 후 입력 폼 초기화
            $scope.checkpoints.push(response.data);
            $scope.checkpoint = { name: '', number: null };
          })
          .catch(function(error) {
            console.error('체크포인트 저장 실패:', error);
            alert('체크포인트 저장 중 오류가 발생했습니다.');
          });
      };

            // 3) 삭제 함수 추가 위치 ↓
            $scope.deleteCheckpoint = function(id) {
              if (!confirm('정말 삭제하시겠습니까?')) return;
              $http.delete('/api/checkpoints/' + id)
                .then(function() {
                  // 화면에서 리스트 업데이트
                  $scope.checkpoints = $scope.checkpoints.filter(function(c) {
                    return c.id !== id;
                  });
                })
                .catch(function(err) {
                  console.error('삭제 실패', err);
                  alert('삭제 중 오류가 발생했습니다.');
                });
            };
    }]);
})();