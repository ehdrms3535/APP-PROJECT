// login.js
const app = angular.module("rideMapApp", []);

app.controller("LoginController", function($scope, $http) {
    $scope.showSignIn = false;
    $scope.loginData = {};
    $scope.registerData = {};

    $scope.login = function () {
        $http.post("/api/login", $scope.loginData, {
            headers: { "Content-Type": "application/json" }
        }).then(function success() {
            alert("✅ 로그인 성공!");
            window.location.href = "/main";
        }, function error(errorResponse) {
            if (errorResponse.status === 401) {
                alert("❌ 로그인 실패: 아이디 또는 비밀번호 확인");
            } else {
                alert("❌ 서버 오류");
            }
        });
    };

    $scope.register = function () {
        $http.post("/api/register", $scope.registerData, {
            headers: { "Content-Type": "application/json" }
        }).then(function success() {
            alert("✅ 회원가입 완료!");
            window.location.href = "/login";
        }, function error(errorResponse) {
            if (errorResponse.status === 400) {
                alert("❌ 이미 존재하는 사용자입니다.");
            } else {
                alert("❌ 서버 오류 발생");
            }
        });
    };
});
