mainApp.controller('aggregateReportController', function($scope,
		$sessionStorage, $rootScope, $http, widgetService) {
	var regex = ".Transaction";
	$scope.tdTitle = 'Aggregate Report';
	$("#requestsDiv").hide();
	$('.aggregateReportPanel').lobiPanel({
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

	$('.aggregateReportPanel').on('onPin.lobiPanel', function(ev, lobiPanel) {
		window.console.log("Pinned");
		$(".tdPanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#avgrespTime").css({
			"width" : "370px",
			"height" : "200px"
		});
		drawTable();
	});

	$('.aggregateReportPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
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
		drawTable();
	});

	$('.aggregateReportPanel').on(
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

	$('.aggregateReportPanel').on('onFullScreen.lobiPanel',
			function(ev, lobiPanel) {
				$('#aggrprtLoadImg_id').show();
				window.console.log("value full event called");
				$(".panel-body").css({
					"max-height" : "100%",
					"min-height" : "94%"
				});
				$(".userScroll").css({
					"width" : "100%",
					"height" : "555px", 
					"overflow-x" : "hidden",
					"overflow-y" : "auto",
					"max-height" : "100%",
					"min-height" : "100%"
				});
				 $("div").removeClass("aggregateName");
				 $(".aggregateName").css({
					 "width" : "30%",
					 "word-wrap" : "break-word"
				 });
				drawTable();
			});
	$('.aggregateReportPanel').on('onSmallSize.lobiPanel',
			function(ev, lobiPanel) {
		$('#aggrprtLoadImg_id').show();
				window.console.log("value small event called");
				$(".panel-body").css({
					"max-height" : "300px",
					"min-height" : "300px"
				});
				$(".userScroll").css({
					"height" : "300px",
					"overflow-x" : "hidden",
					"overflow-y" : "auto",
				});
				$(".aggregateReportPanel").css({
					"height" : "396px"
				});
				 $("div").removeClass("aggregateName");                 
                 $(".aggregateName").css({
                     "width" : "242px",
                     "word-wrap" : "break-word"
                 });
				drawTable();
			});

	$scope.tabbed = function(value) {
		if (value == "transactions") {
			var found_names = $.grep($scope.aggListbackup, function(v) {
				if (v.transactionName.indexOf(regex) !== -1) {
					return v;
				}
				// return v.name === "Joe" &&
				// v.age < 30;
			});
			console.log("transactionValues,", found_names);
			$("#requestsDiv").hide();
			$("#transactionsDiv").show();
			$scope.aggList = found_names;
		} else {
			var found_names = $.grep($scope.aggListbackup, function(v) {
				if (v.transactionName.indexOf(regex) === -1) {
					return v;
				}
				// return v.name === "Joe" &&
				// v.age < 30;
			});
			console.log("transactionValues,", found_names);
			$("#transactionsDiv").hide();
			$("#requestsDiv").show();
			$scope.aggList = found_names;
		}
	};
	drawTable();

	/* $('#perfTestDetailsLoadImg_id').show(); */
	function drawTable() {
		$('#aggrprtLoadImg_id').show();
		$http.get("lennox/rest/aggregateReport/live?testId="+ $sessionStorage.test_num).then(
		// $http.get("lennox/rest/aggregateReport").then(
		function(response) {
			$('#aggrprtLoadImg_id').hide();
			// console.log("inside Aggregate==");
			$('#test_id').hide();

			$('.perfTestDetailsTitle').show();
			$('.perfTestDetailsTable').show();

			var jsonresp = response.data;

			// console.log("Aggregate==", jsonresp);

			$scope.aggList = jsonresp;
			$scope.backupList = jsonresp;
			$scope.aggListbackup = jsonresp;
			if ($("#transactionsDiv").is(':visible')) {
				$scope.aggList = $.grep($scope.aggListbackup, function(v) {
					if (v.transactionName.indexOf(regex) !== -1) {
						return v;
					}
				});
			} else {
				$scope.aggList = $.grep($scope.aggListbackup, function(v) {
					if (v.transactionName.indexOf(regex) === -1) {
						return v;
					}
				});
			}
			// console.log("transactionValues,", found_names);
			$("#transactionsDiv").show();
			$("#requestsDiv").hide();

			/*
			 * var found_names = $.grep(jsonresp, function(v) { return
			 * v.transactionName.includes(".transaction"); // return v.name ===
			 * "Joe" && v.age < 30; });
			 * 
			 * 
			 */
		});

	}

	$scope.applyFilter = function(keyword) {
		var tempList = $scope.aggList;
		var newList = new Array();
		if (keyword != '') {
			for (var i = 0; i < tempList.length; i++) {
				var transName = tempList[i].transactionName.toLowerCase();
				if (transName.indexOf(keyword.toLowerCase()) > 0) {
					newList.push(tempList[i]);
				}
			}
			$scope.aggList = newList;
		} else {
			$scope.aggList = $scope.backupList;
		}
	};

});