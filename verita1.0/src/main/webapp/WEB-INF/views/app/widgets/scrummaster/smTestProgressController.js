mainApp.controller('smTestProgressController', function($scope,
		$sessionStorage, $rootScope, $http, widgetService) {

	$scope.chartFlag = true;
	$scope.tableFlag = false;

	$scope.fire = function(type) {
		console.log(type);
		if (type === 'table') {
			$scope.chartFlag = false;
			$scope.tableFlag = true;
		} else {
			$scope.chartFlag = true;
			$scope.tableFlag = false;
		}
	}

	$('.smTestProgressPanel').lobiPanel({
		// Options go here
		sortable : false,
		reload : true,
		minimize : false,
		close : false,
		expand : false,
		editTitle : false,
		/*
		 * editTitle : { icon : 'fa fa-table tepTable', icon2 : 'fa fa-bar-chart
		 * tepChart', tooltip : 'View' },
		 */
		unpin : {
			icon : 'fa fa-thumb-tack'
		},
		/*
		 * minimize : { icon : 'fa fa-chevron-up', icon2 : 'fa fa-chevron-down' },
		 */
		reload : {
			icon : 'fa fa-refresh'
		},
	/*
	 * close : { icon : 'fa fa-times-circle' }, expand : { icon : 'fa
	 * fa-expand', icon2 : 'fa fa-compress' }
	 */
	});

	$(".tepTable").on("click", function() {
		// alert($(this).attr("class"));
		if ($(this).attr("class").indexOf("tepTable") >= 0) {
			$scope.fire('table');
		} else {
			$scope.fire('chart');
		}
		$scope.$digest();
	});

	$('.smTestProgressPanel').on('onPin.lobiPanel', function(ev, lobiPanel) {

		$(".testProgressPanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#smTEPChart").css({
			"width" : "370px",
			"height" : "200px"
		});
		drawValueChart();
	});

	$('.smTestProgressPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
		lobiPanel.$options.expand = false;

		$(".smTestProgressPanel").css({
			"left" : "150.5px",
			"top" : "122px",
			"height" : "80%",
			"width" : "80%"
		});
		$(".testProgressPanel-body").css({
			"width" : "100%",
			"height" : "100%",
			"max-height" : "85%",
			"min-height" : "85%"
		});
		$("#smTEPChart").css({
			"width" : "100%",
			"height" : "300px"
		});
		drawValueChart();
	});

	$('.smTestProgressPanel').on('beforeClose.lobiPanel',
			function(ev, lobiPanel) {
				var widgetResp = widgetService.deleteWidget(

				$sessionStorage.userId, "rmTEPFlag");

				widgetResp.then(function(resp) {

					if (resp.data.Status === "Success") {
						$rootScope.tpsPanelFlag = false;
						$rootScope.widgetList.push({
							"key" : "rmTEPFlag",
							"value" : $scope.smTEPTitle
						});
						$rootScope.rmTEPFlag = false;
					}
				}, function(errorPayload) {
				});
			});

	$('.smTestProgressPanel').on('onFullScreen.lobiPanel',
			function(ev, lobiPanel) {
				$(".panel-body").css({
					"max-height" : "100%",
					"min-height" : "100%"
				});
				$("#smTEPChart").css({
					"width" : "900px",
					"height" : "600px"
				});
				drawValueChart();
			});
	$('.smTestProgressPanel').on('onSmallSize.lobiPanel',
			function(ev, lobiPanel) {
				$(".panel-body").css({
					"max-height" : "300px",
					"min-height" : "300px"
				});
				$("#smTEPChart").css({
					"width" : "370px",
					"height" : "200px"
				});

				drawValueChart();
			});

	$('.smTestProgressPanel .fa-refresh').on('click', function(ev, lobiPanel) {
		// console.log("onReload.lobiPanel");
		$('.smTEP').hide();
		$('.smTepTitle').hide();
		$('#testProgressLoadImg_id').show();
		drawValueChart();
	});

	google.charts.setOnLoadCallback(drawValueChart);

	/* $('#testProgressLoadImg_id').show(); */

	function drawValueChart() {

		$http.get("devops/rest/smtep").then(
				function(response) {

					$('#testProgressLoadImg_id').hide();
					$('.smTEP').show();
					$('.smTepTitle').show();

					var jsonresp = response.data;

					console.log("jsonresp tep  ===", jsonresp);

					var executionProgressMap = jsonresp.executionProgressMap;
					$scope.failedCasesList = jsonresp.failedCases;
					$scope.allTestCasesMap = jsonresp.allTestCasesMap;
					var keys = [];
					var values = [];
					for ( var key in executionProgressMap) {

						keys.push(key);
						values.push(executionProgressMap[key]);
					}

					var data = google.visualization.arrayToDataTable([
							[ 'Element', '', {
								role : 'style'
							} ], [ keys[0], parseFloat(values[0]), '#ff884d' ],
							[ keys[1], parseFloat(values[1]), '#ffaa80' ],
							[ keys[2], parseFloat(values[2]), '#ffbb99' ],
							[ keys[3], parseFloat(values[3]), '#ffbb99' ] ]);

					$scope.smTEPTitle = jsonresp.meanTotal + '%';

					drawColumnChartfinal(data, 'Application Name',
							'Percentage', '100%', '100%', 'smTEPChart');

				});
	}

	function drawColumnChartfinal(data, hAxisTitle, vAxisTitle, height, width,
			divContainer) {
		var color = "#000000";
		var options = {
			/* title : garphTitle, */
			titleTextStyle : {
				color : '#999',
				fontName : 'Open Sans, Arial, Helvetica, sans-serif',
				fontSize : 12,
				italic : false
			},
			backgroundColor : 'white',
			timeline : {
				colorByRowLabel : false
			},
			legend : {
				position : 'none'
			},
			// height : height,
			// width : width,
			hAxis : {
				title : hAxisTitle,
				format : 'h:mm a',
				titleTextStyle : {
					color : '#999',
					fontName : 'Open Sans, Arial, Helvetica, sans-serif',
					fontSize : 12,
					italic : false
				},
				textStyle : {
					color : '#999',
					fontName : 'Open Sans, Arial, Helvetica, sans-serif',
					fontSize : 12,
					italic : false
				},
				direction : 1,
				slantedText : true,
				slantedTextAngle : 20

			},

			vAxis : {
				title : vAxisTitle,
				titleTextStyle : {
					color : '#999',
					fontName : 'Open Sans, Arial, Helvetica, sans-serif',
					fontSize : 12,
					italic : false
				},
				textStyle : {
					color : '#999',
					fontName : 'Open Sans, Arial, Helvetica, sans-serif',
					fontSize : 12,
					italic : false
				},
				gridlines : {
					color : 'transparent'
				}
			},

		};

		var chart = new google.visualization.ColumnChart(document
				.getElementById(divContainer));

		chart.draw(data, options);
	}

});