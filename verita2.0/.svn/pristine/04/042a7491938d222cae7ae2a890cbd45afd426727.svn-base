mainApp.controller('testerUpdatesControllerCompare', function($scope,
		$sessionStorage, $rootScope, $http, widgetService, testUpdateService) {

	init();

	function init() {
		// retreiveRecords('COMPARISION_TEST');
	}
	$scope.selected = {};

	$scope.showfullData = false;

	$scope.savecomment = function(testType) {

		// console.log('The new comment is :',$scope.newcomment.comment);
		// console.log('The user id is :',$sessionStorage.username);
		// Edited by Sankar added testnum

		var testwith = $('#test_with').find(":selected").text();
		var testto = $('#test_to').find(":selected").text();

		console.log("test_with:" + testwith + "testto:" + testto);

		var testUpdateResp = testUpdateService.addTestUpdateDetailsCompare(
				$scope.newcomment.comment, $sessionStorage.username, testType,
				testto, testwith);

		testUpdateResp.then(function(resp) {
			$scope.newcomment.comment = '';
			// console.log('....5.......',resp.data.result);
			if (resp.data.result == "success") {
				retreiveRecords('COMPARISION_TEST');
			}
			$scope.myVar = false;

		}, function(errorPayload) {
			$scope.newcomment.comment = '';
			console.log('Failure', errorPayload);
		});

	}

	$scope.editComment = function(comment) {
		// console.log('The edit is :');
		$scope.selected = angular.copy(comment);
	}

	$scope.delComment = function(comment) {
		// console.log('The del is :');
		var testUpdateResp = testUpdateService.deleteTestUpdate(comment.id);

		testUpdateResp.then(function(resp) {

			// console.log('....5.......',resp.data.result);
			if (resp.data.result == "success") {
				retreiveRecords('COMPARISION_TEST');
			}

			$scope.reset();
		}, function(errorPayload) {
			$scope.newcomment.comment = '';
			console.log('Failure', errorPayload);
		});
	}

	$scope.updateComment = function(comment) {
		// console.log('The update is :',comment.id);

		// $scope.selected = angular.copy(comment);

		var testUpdateResp = testUpdateService.saveTestUpdateDetails(
				comment.id, comment.comment_name, comment.user_name,
				comment.comment_date);
		// console.log('The updatedd is :',testUpdateResp);
		testUpdateResp.then(function(resp) {

			// console.log('....5.......',resp.data.result);
			if (resp.data.result == "success") {
				retreiveRecords('COMPARISION_TEST');
			}

			$scope.reset();
		}, function(errorPayload) {
			$scope.newcomment.comment = '';
			console.log('Failure', errorPayload);
		});
	}

	$scope.reset = function() {
		$scope.selected = {};
	};

	$scope.getTemplate = function(comment) {
		if (comment.id === $scope.selected.id) {
			return 'edit';
		} else
			return 'display';
	};

	$scope.myVar = false;
	$scope.toggle = function() {

		$scope.myVar = !$scope.myVar;
	};

	$scope.tdTitle = 'Tester Updates';

	$('.testerUpdatePanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		/* unpin : false, */
		reload : true,
		editTitle : false,
		minimize : false,
		unpin : {
			icon : 'fa fa-thumb-tack'
		},
		/*
		 * minimize : { icon : 'fa fa-chevron-up', icon2 : 'fa fa-chevron-down' },
		 */
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

	$('.testerUpdatePanel').on('onPin.lobiPanel', function(ev, lobiPanel) {
		window.console.log("Pinned");
		$(".tdPanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#avgrespTime").css({
			"width" : "370px",
			"height" : "200px"
		});
	});

	$('.testerUpdatePanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
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
		$("#avgrespTime").css({
			"width" : "100%",
			"height" : "300px"
		});
	});

	$('.testerUpdatePanel').on(
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

	$('.testerUpdatePanel').on('onFullScreen.lobiPanel',
			function(ev, lobiPanel) {
				$('#testerUpdateLoadImg_id').show();
				window.console.log("value full event called");
				$(".panel-body").css({
					"max-height" : "100%",
					"min-height" : "93%"
				});
				$("#tupdate").css({
					"width" : "100%",
					"overflow-x" : "hidden",
					"overflow-y" : "auto",
					"max-height" : "100%",
					"min-height" : "40%"
				});
				$("#tupdateTest1").css({
					"width" : "100%",
					"overflow-x" : "hidden",
					"overflow-y" : "auto",
					"max-height" : "100%",
					"min-height" : "40%"
				});
				$("#tupdateTest2").css({
					"width" : "100%",
					"overflow-x" : "hidden",
					"overflow-y" : "auto",
					"max-height" : "100%",
					"min-height" : "40%"
				});
				$('#addButton').css({
					"margin-right" : "20%",
					"margin-top" : "-2%",
				});
				$('#addButton').css({
					"margin" : "-3% 20% 0% 0%",
					"float" : "right",
				});

				$scope.showfullData = true;
				// console.log("onFullScreen - show full data: ");
				// console.log($scope.showfullData);
				// $scope.$digest();
				$('#testerUpdateLoadImg_id').hide();
			});
	$('.testerUpdatePanel').on('onSmallSize.lobiPanel',
			function(ev, lobiPanel) {
				$('#testerUpdateLoadImg_id').show();
				window.console.log("value small event called");
				$(".panel-body").css({
					"max-height" : "300px",
					"min-height" : "300px",
					"overflow-y" : "scroll !important;"
				});
				/*$("#tupdate").css({
					"height" : "120px",
					"overflow-x" : "hidden",
					"overflow-y" : "auto",
					"width" : "95%"
				});*/
				/*$("#tupdateTest1").css({
					"height" : "120px",
					"overflow-x" : "hidden",
					"overflow-y" : "auto",
					"width" : "95%"
				});
				$("#tupdateTest2").css({
					"height" : "120px",
					"overflow-x" : "hidden",
					"overflow-y" : "auto",
					"width" : "95%"
				});*/
				$('#addButton').css({
					"margin" : "-6% 12% 1% 2%",
					"float" : "right",
				});

				$scope.showfullData = false;
				// console.log("onSmallSize - show full data: ");
				// console.log($scope.showfullData);
				// $scope.$digest();
				$('#testerUpdateLoadImg_id').hide();
			});

	$('.testerUpdatePanel .fa-refresh').on('click', function(ev, lobiPanel) {
		// console.log("onReload.lobiPanel");
		$('.perfJMErrorPer').hide();
		$('.jmErrorPerTitle').hide();
		$('#perfJMErrorPerLoadImg_id').show();
	});

	$scope.comments = [];
	$scope.test1Comments = [];
	$scope.test2Comments = [];

	function retreiveRecords(testType) {
		// console.log('......6......');

		var testwith = $('#test_with').find(":selected").text();
		$scope.testwith = testwith;
		var testto = $('#test_to').find(":selected").text();
		$scope.testto = testto;

		var test1UpdateRetreiveResp = testUpdateService
				.retreiveTestUpdateDetails('SINGLE_TEST',
						$sessionStorage.testwith_number);
		test1UpdateRetreiveResp.then(function(resp) {
			$scope.test1Comments = resp.data;
			console.log($scope.test1Comments[0]);
		}, function(errorPayload) {
			console.log('Failure', errorPayload);
		});

		var test2UpdateRetreiveResp = testUpdateService
				.retreiveTestUpdateDetails('SINGLE_TEST',
						$sessionStorage.test_to_number);
		test2UpdateRetreiveResp.then(function(resp) {
			$scope.test2Comments = resp.data;
		}, function(errorPayload) {
			console.log('Failure', errorPayload);
		});

		var testUpdateRetreiveResp = testUpdateService
				.retreiveTestUpdateDetailsCompare(testType, testwith, testto);
		testUpdateRetreiveResp.then(function(resp) {
			$('#testerUpdateLoadImg_id').hide();
			$scope.comments = resp.data;
		}, function(errorPayload) {
			console.log('Failure', errorPayload);
		});
	}

	$("#compare").click(function() {
		$('#testerUpdateLoadImg_id').show();
		var testwith = $('#test_with').find(":selected").text();
		var testto = $('#test_to').find(":selected").text();
		retreiveRecords('COMPARISION_TEST');
	});

	function fetchCommnents() {
		// need to change the contoller path and qury String to get test compae
		// observations
		$http.get("lennox/rest/aggregateReport/live?testId=" + testwith).then(
				function(response) {

				});
	}
	/*
	 * $scope.edit = function(id) { for (i in $scope.comments) { if
	 * ($scope.comments[i].id == id) { $scope.newcomment =
	 * angular.copy($scope.comments[i]); } } }
	 */

});