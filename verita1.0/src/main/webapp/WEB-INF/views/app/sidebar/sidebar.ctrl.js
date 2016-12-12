mainApp.controller('sidebarController', function($scope, $state,
		$sessionStorage, $location) {

	$scope.username = 'CIO';
	$scope.userImg = 'views/images/users/rahulP.png';
	$scope.user = 'Rahul';
	
	$scope.leftFeatures = false;
	

	$scope.isActiveMenu = function(route) {
		return route === $location.path();
	};

	$scope.logout = function() {
		// TODO do some authentication or call authentication service for login
		$sessionStorage.$reset();
		$state.go('login');
	}

	$scope.userImg = 'views/images/users/userpic_'+$sessionStorage.username+'.jpg';
	
	if ($sessionStorage.username === 'cigniticio') {
		$scope.username = 'CIO';
		$scope.user = 'Rahul';
	} else if ($sessionStorage.username === 'cignitiperf') {
		$scope.username = 'Performance';
		$scope.user = 'Rajesh';
		$scope.leftFeatures = true;
	} else if ($sessionStorage.username === 'cignitiscrum') {
		$scope.username = 'ScrumMaster';
		$scope.user = 'Moshe';
	}
	
	
	 
});