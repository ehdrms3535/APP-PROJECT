// /src/main/resources/static/js/auth.js
(function() {
  'use strict';

  angular.module('rideMapApp')
    .controller('AuthCtrl', ['$scope', '$http', '$window', function($scope, $http, $window) {
      // 토글 플래그와 모델 초기화
      $scope.showSignUp = false;
      $scope.loginData    = { username: '', password: '' };
      $scope.registerData = { username: '', password: '', repeatPassword: '' };
      $scope.error        = null;

      // 로그인
      $scope.login = function() {
        $scope.error = null;
        $http.post('/api/login', {
          username: $scope.loginData.username,
          password: $scope.loginData.password
        }).then(function(res) {
          localStorage.setItem('loginId', res.data.username);
          $window.location.href = '/main';
        }).catch(function(err) {
          $scope.error = err.data?.message || '로그인 실패';
        });
      };

      // 회원가입
      $scope.register = function() {
        $scope.error = null;
        if ($scope.registerData.password !== $scope.registerData.repeatPassword) {
          $scope.error = '비밀번호가 일치하지 않습니다.';
          return;
        }
        $http.post('/api/register', {
          username: $scope.registerData.username,
          password: $scope.registerData.password
        }).then(function(res) {
          // 성공하면 바로 로그인 폼으로 돌아가고, 로그인 필드에 채워줌
          $scope.showSignUp = false;
          $scope.loginData.username = $scope.registerData.username;
          $scope.loginData.password = $scope.registerData.password;
          $scope.registerData = { username:'', password:'', repeatPassword:'' };
        }).catch(function(err) {
          $scope.error = err.data?.message || '회원가입 실패';
        });
      };
    }]);
})();
