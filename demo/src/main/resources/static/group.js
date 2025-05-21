(function() {
  'use strict';

  angular.module('rideMapApp', [])
    .controller('GroupCtrl', ['$scope', '$http', '$window', function($scope, $http, $window) {
      // 현재 사용자 ID (로그인된 사용자로 동적으로 설정 필요)
      $scope.currentUserId = 1;
      $scope.groupName = '';

      /**
       * 그룹 검색 또는 생성 후 채팅방으로 이동
       */
      $scope.enterGroup = function() {
        var input = $scope.groupName.trim();
        if (!input) {
          alert('그룹명을 입력하세요.');
          return;
        }

        // 숫자만 입력된 경우: 그룹 생성/조회 로직 없이 바로 이동
        if (/^\d+$/.test(input)) {
          $window.location.href = '/chat/' + input;
          return;
        }

        // 문자열(그룹명)인 경우: 기존 로직대로 이름으로 조회/생성
        $http.get('/api/groups/name/' + encodeURIComponent(input))
          .then(res => res.data.id)
          .catch(() =>
            // 그룹이 없으면 자동 생성
            $http.post('/api/groups', {
              name: input,
              ownerId: $scope.currentUserId,
              memberIds: [$scope.currentUserId]
            }).then(res => res.data.groupId)
          )
          .then(groupId =>
            // 그룹 가입
            $http.post(`/api/groups/${groupId}/join`, { userId: $scope.currentUserId })
              .then(() => groupId)
          )
          .then(groupId => {
            // 채팅방 이동
            $window.location.href = '/chat/' + groupId;
          })
          .catch(err => {
            console.error('그룹 처리 중 오류:', err);
            const msg = err.data?.message || err.statusText;
            alert('오류 발생: ' + msg);
          });
      };
    }]);
})();