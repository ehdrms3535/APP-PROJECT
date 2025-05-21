(function() {
  'use strict';

  angular.module('rideMapApp')
    .controller('ChatController', ['$scope', '$http', '$window', function($scope, $http, $window) {
      // 로그인 ID (예: localStorage)
      var loginId = localStorage.getItem('loginId') || 'Anonymous';

      // URL에서 groupId 추출 (페이지 로드 시 단 한 번)
      $scope.groupId = $window.location.pathname.split('/').pop();

      $scope.messages   = [];
      $scope.newMessage = '';

      // 해당 그룹의 메시지 로드
      $http.get('/api/groups/' + $scope.groupId + '/messages')
        .then(function(res) {
          $scope.messages = res.data;
        })
        .catch(function(err) {
          console.error('메시지 로드 실패:', err);
        });

      // 메시지 전송
      $scope.sendMessage = function() {
        var payload = {
          sender: loginId,
          text:   $scope.newMessage
        };
        $http.post('/api/groups/' + $scope.groupId + '/messages', payload)
          .then(function(res) {
            $scope.messages.push(res.data);
            $scope.newMessage = '';
            // 맨 아래로 스크롤
            setTimeout(function() {
              var m = document.getElementById('messages');
              m.scrollTop = m.scrollHeight;
            }, 0);
          })
          .catch(function(err) {
            console.error('메시지 전송 실패:', err);
          });
      };
    }]);
})();
