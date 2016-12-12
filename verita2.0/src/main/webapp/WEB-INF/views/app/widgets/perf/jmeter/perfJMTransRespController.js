mainApp.controller('perfJMTransRespController', function($scope, $sessionStorage,
		$rootScope, $http, widgetService) {

	$scope.tRespTitle = 'Transaction Response Times';

	$scope.respTime = 0.640;
	$scope.tps = 0.640;
	$scope.errors = 0.640;

	$('.jmtresPanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		unpin : false,
		reload : true,
		editTitle : false,
		minimize : false,
		reload : {
			icon : 'fa fa-refresh'
		},
		close : {
			icon : 'fa fa-times-circle'
		},
		expand : {
			icon : 'fa fa-expand',
			icon2 : 'fa fa-compress'
		}
	});

	$('.jmtresPanel').on(
			'beforeClose.lobiPanel',
			function(ev, lobiPanel) {
				var widgetResp = widgetService.deleteWidget(
						$sessionStorage.userId, "JMtrespPanelFlag");

				widgetResp.then(function(resp) {
					console.log("delete response");
					console.log(resp);
					if (resp.data.Status === "Success") {
						$rootScope.trespPanelFlag = false;
						$rootScope.widgetList.push({
							"key" : "JMtrespPanelFlag",
							"value" : $scope.tRespTitle
						});
						$rootScope.JMtrespPanelFlag = false;
					}
				}, function(errorPayload) {
					console.log(errorPayload);
				});
			});

	/*$('.jmtresPanel .fa-refresh').on('click', function(ev, lobiPanel){
		 // console.log("onReload.lobiPanel");
		$('.transdetails').hide();
		$('.jmTransrespTitle').hide();
		$('#perfJMTransPerSecLoadImg_id').show();
		
		//drawValueChart();
		   });*/
	
	$('#perfJMTransRespLoadImg_id').show();
	
	$http.get("devops/rest/jmtd").then(function(response) {
		
		$('#perfJMTransRespLoadImg_id').hide();
		
		$('.jmTransrespTitle').show();
		 $('.transdetails').show();
		
		var jsonresp = response.data;
		/*console.log("resp time " + jsonresp);*/
		//console.log(jsonresp);
		$scope.transactionDetails = jsonresp;

	});
	

});