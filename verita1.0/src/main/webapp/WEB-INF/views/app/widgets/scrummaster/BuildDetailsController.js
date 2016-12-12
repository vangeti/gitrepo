
mainApp.controller('BuildDetailsController', function($scope, $sessionStorage,
		$rootScope, widgetService, $http) {
	
	$scope.buildTitle = 'Build Details';
	
	$('.buildPanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		unpin : true,
		reload : true,
		editTitle : false,
		minimize : false,
		close : false,
		expand : false,
		/*minimize : {
			icon : 'fa fa-chevron-up',
			icon2 : 'fa fa-chevron-down'
		},*/
		reload : {
			icon : 'fa fa-refresh'
		},
		/*close : {
			icon : 'fa fa-times-circle'
		},
		expand : {
			icon : 'fa fa-expand',
			icon2 : 'fa fa-compress'
		}*/
	});
	

	/*$('.buildPanel').on('beforeClose.lobiPanel', function(ev, lobiPanel) {
		var widgetResp = widgetService.deleteWidget(
				$sessionStorage.userId, "jenkinsBuildFlag");

		widgetResp.then(function(resp) {
		
			if (resp.data.Status === "Success") {
				$rootScope.jenkinsBuildFlag = false;
				$rootScope.widgetList.push({
					"key":"jenkinsBuildFlag",
					"value":$scope.buildTitle
				});
				$rootScope.jenkinsBuildFlag = false;
			}
		}, function(errorPayload) {
			
		});
	});
	
	$('.buildPanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
		
		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		$("#buildWidget").css({
			"width" : "100%",
			"height" : "600px"
		});
		
		//drawChart();

	});
	$('.buildPanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
		
		$(".panel-body").css({
			"max-height" : "300px",
			"min-height" : "300px"
		});
		$("#buildWidget").css({
			"width" : "400px",
			"height" : "300px"
		});
		//drawChart();
	});*/

});