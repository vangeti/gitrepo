mainApp.controller('widgetController', function($scope,
		testerObservationService) {

	/*$scope.tS = "Test Summary"
	$scope.kpi = "observation"
	$scope.aggegate = "Aggregate Report"
	$scope.actTh = "Active Threads"
	$scope.hPS = "Hits per Sec"
	$scope.rC = "Response Code"
	$scope.rT = "Response Time"*/

	var response = testerObservationService.getTesterObservations();
	response.then(function(resp) {
		for ( var key in resp.data) {
			if (resp.data[key].widget == 'FLAG_LEX_TS') {
				$scope.tS = resp.data[key].comment;
			}
			if (resp.data[key].widget == 'FLAG_LEX_KPI') {
				$scope.kpi = resp.data[key].comment;
			}
			if (resp.data[key].widget == 'FLAG_LEX_AT') {
				$scope.actTh = resp.data[key].comment;
			}
			if (resp.data[key].widget == 'FLAG_LEX_AG') {
				$scope.aggegate = resp.data[key].comment;
			}
			if (resp.data[key].widget == 'FLAG_LEX_HS') {
				$scope.hPS = resp.data[key].comment;
			}
			if (resp.data[key].widget == 'FLAG_LEX_RC') {
				$scope.rC = resp.data[key].comment;
			}
			if (resp.data[key].widget == 'FLAG_LEX_RT') {
				$scope.rT = resp.data[key].comment;
			}
			if (resp.data[key].widget == 'FLAG_LEX_KPI_CMP') {
				$scope.KPI_CMP = resp.data[key].comment;
			}
			if (resp.data[key].widget == 'FLAG_LEX_AG_CMP') {
				$scope.AG_CMP = resp.data[key].comment;
			}
			if (resp.data[key].widget == 'FLAG_LEX_HS_CMP') {
				$scope.HS_CMP = resp.data[key].comment;
			}
			if (resp.data[key].widget == 'FLAG_LEX_RC_CMP') {
				$scope.RC_CMP = resp.data[key].comment;
			}
			if (resp.data[key].widget == 'FLAG_LEX_AT_CMP') {
				$scope.AT_CMP = resp.data[key].comment;
			}
		}

	}, function(errorPayload) {
		console.log('Failed');
	});

	//}

});