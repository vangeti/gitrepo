mainApp.controller('PerfTransRespController', function($scope, $sessionStorage,
		$rootScope, $http, widgetService) {

	$scope.tRespTitle = 'Transaction Response Times';

	$('.tresPanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		/*unpin : false,*/
		unpin : {
			icon : 'fa fa-thumb-tack'
		},
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

	$('.tresPanel').on('onPin.lobiPanel', function(ev, lobiPanel) {
		window.console.log("Pinned");
		$(".trPanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		/*$("#perftransdetails").css({
			"width" : "600px",
			"height" : "300px"
		});
		drawValueChart();*/
	});

	$('.tresPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
		lobiPanel.$options.expand = false;
		window.console.log("Unpinned");
		$(".tresPanel").css({
			"left" : "150.5px",
			"top" : "122px",
			"height" : "80%",
			"width" : "80%"
		});
		$(".trPanel-body").css({
			"width" : "100%",
			"height" : "100%",
			"max-height" : "85%",
			"min-height" : "85%"
		});
		/*$("#perftransdetails").css({
			"width" : "100%",
			"height" : "400px"
		});
		drawValueChart();*/
	});
	$('.tresPanel').on(
			'beforeClose.lobiPanel',
			function(ev, lobiPanel) {
				var widgetResp = widgetService.deleteWidget(
						$sessionStorage.userId, "trespPanelFlag");

				widgetResp.then(function(resp) {

					if (resp.data.Status === "Success") {
						$rootScope.trespPanelFlag = false;
						$rootScope.widgetList.push({
							"key" : "trespPanelFlag",
							"value" : $scope.tRespTitle
						});
					}
				}, function(errorPayload) {

				});
			});

	$('#perfTransRespLoadImg_id').show();

	$http.get("devops/rest/td").then(function(response) {

		$('#perfTransRespLoadImg_id').hide();

		$('.perfTransrespTitle').show();
		$('.perftransdetails').show();

		var jsonresp = response.data;

		$scope.transactionDetails = jsonresp;

	});

});