mainApp.controller('PerfTestDetailsController', function($scope,
		$sessionStorage, $rootScope, $http, widgetService) {

	$scope.tdTitle = 'Test Execution Summary';

	$('.tdPanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		/*unpin : false,*/
		reload : true,
		editTitle : false,
		minimize : false,
		close : false,
		expand : false,
		unpin : {
			icon : 'fa fa-thumb-tack'
		},
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

	$('.tdPanel').on('onPin.lobiPanel', function(ev, lobiPanel) {
		window.console.log("Pinned");
		$(".tdPanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
	});

	$('.tdPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
		lobiPanel.$options.expand = false;
		window.console.log("Unpinned");
		$(".tdPanel").css({
			"left" : "150.5px",
			"top" : "122px",
			"height" : "80%",
			"width" : "80%"
		});
		$(".tdPanel-body").css({
			"width" : "100%",
			"height" : "100%",
			"max-height" : "85%",
			"min-height" : "85%"
		});
	});

	$('.tdPanel').on(
			'beforeClose.lobiPanel',
			function(ev, lobiPanel) {
				var widgetResp = widgetService.deleteWidget(
						$sessionStorage.userId, "tdPanelFlag");

				widgetResp.then(function(resp) {

					if (resp.data.Status === "Success") {
						$rootScope.tdPanelFlag = false;
						$rootScope.widgetList.push({
							"key" : "tdPanelFlag",
							"value" : $scope.tdTitle
						});
					}
				}, function(errorPayload) {

				});
			});

	$('#perfTestDetailsLoadImg_id').show();

	$http.get("devops/rest/tes").then(function(response) {

		$('#perfTestDetailsLoadImg_id').hide();

		$('.perfTestDetailsTitle').show();
		$('.perfTestDetailsTable').show();

		var jsonresp = response.data;

		$scope.projectName = jsonresp.Header[0].projectName;
		$scope.date = jsonresp.Header[0].date;
		$scope.duration = jsonresp.Header[0].duration;
		$scope.users = jsonresp.Header[0].users;
		$scope.averageHits = jsonresp.Average[0].hits;
		$scope.averagePages = jsonresp.Average[0].pages;
		$scope.averageResponse = jsonresp.Average[0].response;
		$scope.averageThroughput = jsonresp.Average[0].throughput;
		$scope.totalHits = jsonresp.Total[0].hits;
		$scope.totalPages = jsonresp.Total[0].pages;
		$scope.totalThroughput = jsonresp.Total[0].throughput;
		$scope.percentileError = jsonresp.Total[0].percentileError;

	});

	/*$('.ValuePanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value full event called");
		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		$("#donutchart").css({
			"width" : "900px",
			"height" : "600px"
		});
		drawValueChart();
	});
	$('.ValuePanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value small event called");
		$(".panel-body").css({
			"max-height" : "300px",
			"min-height" : "300px"
		});
		$("#donutchart").css({
			"width" : "250px",
			"height" : "245px"
		});
		drawValueChart();
	});*/

});