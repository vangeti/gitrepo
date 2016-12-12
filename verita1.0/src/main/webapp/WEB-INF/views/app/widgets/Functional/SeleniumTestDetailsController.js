mainApp.controller('SeleniumTestDetailsController', function($scope,
		$sessionStorage, $rootScope, $http, widgetService) {

	$scope.stdcTitle = 'Test Execution Summary';

	$('.stdcPanel').lobiPanel({
		// Options go here
		sortable : false,
		editTitle : false,
		/*unpin : false,*/
		reload : true,
		editTitle : false,
		minimize : false,
		close : false,
		expand : false,
		/*editTitle : {
			 icon : 'fa fa-bug seleniumReport',
			 icon2 : 'fa fa-bug seleniumReport',
		     tooltip : 'DrillDown'
		},*/
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

	$('.stdcPanel').on('onPin.lobiPanel', function(ev, lobiPanel) {
		window.console.log("Pinned");
		$(".panel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
	});

	$('.stdcPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
		lobiPanel.$options.expand = false;
		window.console.log("Unpinned");
		$(".stdcPanel").css({
			"left" : "150.5px",
			"top" : "122px",
			"height" : "80%",
			"width" : "80%"
		});
		$(".stdcPanel-body").css({
			"width" : "100%",
			"height" : "100%",
			"max-height" : "85%",
			"min-height" : "85%"
		});
	});

	$('.stdcPanel .seleniumReport').on('click', function(ev, lobiPanel) {
		$('#seleniumReport').modal('show');
	});

	/*$('.stdcPanel').on('beforeClose.lobiPanel', function(ev, lobiPanel) {
		var widgetResp = widgetService.deleteWidget(
				$sessionStorage.userId, "SelTESFlag");

		widgetResp.then(function(resp) {
			
			if (resp.data.Status === "Success") {
				$rootScope.SelTESFlag = false;
				$rootScope.widgetList.push({
					"key":"SelTESFlag",
					"value":$scope.stdcTitle
				});
			}
		}, function(errorPayload) {
		
		});
	});*/

	/*$('#perfTestDetailsLoadImg_id').show();*/

	$http.get("devops/rest/std").then(function(response) {

		$('#seleniumTestDetailsLoadImg_id').hide();

		$('.stdcTestDetailsTitle').show();
		$('.stdcTestDetailsTable').show();

		var jsonresp = response.data;
		console.log(jsonresp);

//		$scope.date = jsonresp.startTime;
		$scope.date = 'Sep 2, 2016 9:00:14 AM';
		$scope.duration = jsonresp.testDuration;
		$scope.buildNo = jsonresp.buildNo;
		$scope.browserName = jsonresp.browserName;
		$scope.os = jsonresp.os;
		$scope.totalTestsRun = jsonresp.totalTestsRun;
		$scope.succesFullTests = jsonresp.succesFullTests;
		$scope.failureTests = jsonresp.failureTests;
		$scope.skipTests = jsonresp.skipTests;

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