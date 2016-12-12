
mainApp.controller('sidebarController', function($scope,$state,$sessionStorage,$location) {
	
	$scope.isActiveMenu = function(route) {
		return route === $location.path();
	};
	//$sessionStorage.test_num = '';
	/*if ($sessionStorage) {
        // no logged user, we should be going to the login route
        if (next.templateUrl == "partials/login.html") {
            // already going to the login route, no redirect needed
        } else {
            // not going to the login route, we should redirect now
            $location.path("/login");
        }
	*/
	$scope.logout = function() {
		// TODO do some authentication or call authentication service for login
		$sessionStorage.$reset();
		$state.go('login');
	}
	
	//console.log("mmmmmmmmmmmmmmmmmmmmmm########################################################");
	//console.log("Session:///////////////////////////////////////////" + $sessionStorage.test_num);
	if($location.path() == '/dashboard') {
	if ($sessionStorage.test_num == ''|| $sessionStorage.test_num == undefined) {
		$location.path('/home');
	} else {
		$location.path('/dashboard');
	}
	}
	
});