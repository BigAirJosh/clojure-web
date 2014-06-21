var clojureWebApp = angular.module('clojureWebApp', []);

clojureWebApp.controller('UserController', function ($scope, $http) {
    $http.get('/api/user').success(function(data) {
	$scope.users = data;
    });

    $scope.reset = function() {
	$scope.user = {};
    }

    $scope.save = function(user) {
	if(user.id)
	    $http.put('/api/user/' + user.id, {user: user}).success(function(data) {
		$scope.user = data;
	    });
	else
	    $http.post('/api/user', {user: user}).success(function(data) {
		$scope.user = data;
		$scope.users.push(data);
		$scope.reset();
	    });
    };

    $scope.edit = function(user) {
	$scope.user = user;
    }

    $scope.delete = function(user) {
	$http.delete('/api/user/' + user.id).success(function(data) {
	    var i = $scope.users.indexOf(user);
	    if(i > -1)
		$scope.users.splice(i, 1);
	});
    }

    $scope.user = {};
});

