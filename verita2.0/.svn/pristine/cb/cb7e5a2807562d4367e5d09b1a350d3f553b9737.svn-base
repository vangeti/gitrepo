mainApp.controller('testerUpdateController', function($scope, $sessionStorage,
		$rootScope, $http, widgetService, testUpdateService) {

	init();

	function init() {
		retreiveRecords('SINGLE_TEST');
	}
	$scope.selected = {};

	$scope.showfullData = false;

	$scope.savecomment = function(testType) {

		// console.log('The new comment is :',$scope.newcomment.comment);
		// console.log('The test no is :',$sessionStorage.test_num);
		// Edited by Sankar
		var testUpdateResp = testUpdateService.addTestUpdateDetails(
				$scope.newcomment.comment, $sessionStorage.username, testType,
				$sessionStorage.test_num);

		testUpdateResp.then(function(resp) {
			$scope.newcomment.comment = '';
			// console.log('....5.......',resp.data.result);
			if (resp.data.result == "success") {
				retreiveRecords('SINGLE_TEST');
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

	$scope.addComment = function(comment) {
		// console.log('The edit is :');
		$scope.selected = angular.copy(comment);
	}

	$scope.delComment = function(comment) {
		// console.log('The del is :');
		var testUpdateResp = testUpdateService.deleteTestUpdate(comment.id);

		testUpdateResp.then(function(resp) {

			// console.log('....5.......',resp.data.result);
			if (resp.data.result == "success") {
				retreiveRecords('SINGLE_TEST');
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
				retreiveRecords('SINGLE_TEST');
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

	$scope.myVar = false;
	$scope.toggle = function() {
		$scope.myVar = !$scope.myVar;
	};

	$scope.getTemplate = function(comment) {
		// console.log('The comment is :',comment);
		if (comment.id === $scope.selected.id) {
			return 'edit';
		} else
			return 'display';
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

	// START
	$('.testerUpdatePanel').on('onFullScreen.lobiPanel',
			function(ev, lobiPanel) {
				$('#testupdartesLoadImg_id').show();

				window.console.log("value full event called");
				$(".panel-body").css({
					"max-height" : "100%",
					"min-height" : "100%"
				});
				$("#tupdate").css({
					"width" : "100%",
					"overflow-x" : "hidden",
					"overflow-y" : "auto",
					"max-height" : "100%",
					"min-height" : "60%"
				});
				$('#addButton').css({
					"margin-right" : "20%",
					"margin-top" : "-2%",
				});

				$scope.showfullData = true;
				// console.log("onFullScreen - show full data: ");
				// console.log($scope.showfullData);
				// $scope.$digest();
				$('#testupdartesLoadImg_id').hide();
			});
	$('.testerUpdatePanel').on('onSmallSize.lobiPanel',
			function(ev, lobiPanel) {
				$('#testupdartesLoadImg_id').show();
				window.console.log("value small event called");
				$(".panel-body").css({
					"max-height" : "300px",
					"min-height" : "300px"
				});
				$("#tupdate").css({
					"height" : "120px",
					"overflow-x" : "hidden",
					"overflow-y" : "auto",
					"width" : "95%"
				});
				$('#addButton').css({
					"margin" : "-6% 12% 1% 2%",
					"float" : "right",
				});
				$(".testerUpdatePanel").css({
					"height" : "396px"
				})

				$scope.showfullData = false;
				// console.log("onSmallSize - show full data: ");
				// console.log($scope.showfullData);
				// $scope.$digest();
				$('#testupdartesLoadImg_id').hide();
			});

	$('.testerUpdatePanel .fa-refresh').on('click', function(ev, lobiPanel) {
		// console.log("onReload.lobiPanel");
		$('.perfJMErrorPer').hide();
		$('.jmErrorPerTitle').hide();
		$('#perfJMErrorPerLoadImg_id').show();
	});

	var uid = 1;

	$scope.comments = [];

	// Edited by Sankar

	$('#testupdartesLoadImg_id').show();

	function retreiveRecords(testType) {
		var testUpdateRetreiveResp = testUpdateService
				.retreiveTestUpdateDetails(testType, $sessionStorage.test_num);

		// console.log('......6.5......',testUpdateRetreiveResp);

		testUpdateRetreiveResp.then(function(resp) {
			$('#testupdartesLoadImg_id').hide();
			$scope.comments = resp.data;
		}, function(errorPayload) {
			console.log('Failure', errorPayload);
		});
	}

	/*
	 * $scope.edit = function(id) { for (i in $scope.comments) { if
	 * ($scope.comments[i].id == id) { $scope.newcomment =
	 * angular.copy($scope.comments[i]); } } }
	 */

});