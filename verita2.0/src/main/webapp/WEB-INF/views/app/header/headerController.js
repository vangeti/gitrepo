mainApp.controller('headerController', function($scope, $location,
		$sessionStorage, $state, $rootScope, loginService, $interval,
		widgetService) {

	// and fire it after definition
	init();

	$scope.username = $sessionStorage.username;
	$scope.buName = $sessionStorage.buName;
	$scope.projectName = $sessionStorage.projectName;

	/*$scope.selectedStyle = '';
	$scope.stylePath = 'style.css';
	$scope.names = [ "style", "style1" ];*/

	$rootScope.widgetList = [];
	$scope.showWidget = '';

	for ( var i in $sessionStorage.hiddenWidgetInfo) {
		// $rootScope[$sessionStorage.userInfo[i]] = true;
		$rootScope.widgetList.push({
			"key" : $sessionStorage.hiddenWidgetInfo[i][0]['id'],
			"value" : $sessionStorage.hiddenWidgetInfo[i][0]['title'],
			"widgetid" : $sessionStorage.hiddenWidgetInfo[i][0]['widgetid']
		});
	}

	console.log($sessionStorage.validUser);
	if ($sessionStorage.username == undefined) {
		// console.log($scope.username);
		$state.go('login');
	} else {
		$scope.username = $scope.username.substr(0, 1).toUpperCase()
				+ $scope.username.substr(1);
		$scope.activeMenu = '';
		$scope.isActiveMenu = function(route) {
			return route === $location.path();
		};

		/*
		 * $scope.Timer = $interval(function() { // Display the current time.
		 * var loginRes = loginService.postLogin($sessionStorage.username,
		 * $sessionStorage.password); console.log('refresh-----');
		 * console.log(loginRes); }, 5000);
		 */
	}

	$scope.logout = function() {
		// TODO do some authentication or call authentication service for login
		$sessionStorage.$reset();
		$state.go('login');
	}

	/*
	 * $scope.changePath = function(style) { $scope.stylePath = style + '.css'; };
	 */

	$scope.updateCSS = function() {
		// TODO do some authentication or call authentication service for login
		$rootScope.stylePath = $scope.selectedStyle + '.css';
	}

	$scope.updateWidgets = function() {

		var widgetResp = widgetService.addWidget($sessionStorage.userId,
				$scope.showWidget);

		widgetResp.then(function(resp) {

			if (resp.data.Status === "Success") {
				for (x in $rootScope.widgetList) {
					console.log(x + ' --- ' + $scope.showWidget + " === "
							+ $rootScope.widgetList[x].key);
					if ($scope.showWidget === $rootScope.widgetList[x].key) {

						console.log($rootScope.widgetList[x].key);
						$rootScope.widgetList.splice($rootScope.widgetList
								.indexOf(x));
						// delete
						// $rootScope.widgetList[$rootScope.widgetList[x].key];
					}
				}
				$rootScope[$scope.showWidget] = true;
			}
		}, function(errorPayload) {
			console.log(errorPayload);
		});

	}
	// at the bottom of your controller
	function init() {
		var loginRes = loginService.postLogin($sessionStorage.username,
				$sessionStorage.password, $sessionStorage.buId,
				$sessionStorage.projectId);

		loginRes.then(function(payload) {

			$sessionStorage.userId = payload.data.user_id;
			$sessionStorage.validUser = payload.data.validUser;
			if (payload.data.validUser === "true") {
				$sessionStorage.userInfo = payload.data.graphList;
				$sessionStorage.hiddenWidgetInfo = payload.data.hidegraphList;
			}
		}, function(errorPayload) {
			console.log(errorPayload);
		});
	}

	$('#ePDF').click(function() {
		document.execCommand("SaveAs");
	});

});