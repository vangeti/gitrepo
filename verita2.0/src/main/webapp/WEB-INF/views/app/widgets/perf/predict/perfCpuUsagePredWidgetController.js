mainApp.controller('perfCpuUsagePredWidgetController', function($scope, $sessionStorage,
		$rootScope, $http, widgetService) {

	$scope.perfCpuUsagePredTitle = 'CPU Utilization Percentage';

	$('.perfCpuUsagePredPanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		unpin : false,
		reload : true,
		editTitle : false,
		minimize : false,
		/*minimize : {
			icon : 'fa fa-chevron-up',
			icon2 : 'fa fa-chevron-down'
		},*/
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

	$('.perfCpuUsagePredPanel').on('beforeClose.lobiPanel', function(ev, lobiPanel) {
		var widgetResp = widgetService.deleteWidget(

				$sessionStorage.userId, "rmODSFLag");


		widgetResp.then(function(resp) {
			console.log("delete response");
			console.log(resp);
			if (resp.data.Status === "Success") {
				$rootScope.tpsPanelFlag = false;
				$rootScope.widgetList.push({
					"key":"rmODSFLag",
					"value":$scope.perfCpuUsagePredTitle
				});
				$rootScope.rmODSFLag = false;
			}
		}, function(errorPayload) {
			console.log(errorPayload);
		});
	});

	$('.perfCpuUsagePredPanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value full event called");
		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		$("#perfCpuUsagePredChart").css({
			"width" : "900px",
			"height" : "600px"
		});
		drawValueChart();
	});
	$('.perfCpuUsagePredPanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value small event called");
		$(".panel-body").css({
			"max-height" : "300px",
			"min-height" : "300px"
		});
		$("#perfCpuUsagePredChart").css({
			"width" : "280px",
			"height" : "200px"
		});
		drawValueChart();
	});
	
	

});