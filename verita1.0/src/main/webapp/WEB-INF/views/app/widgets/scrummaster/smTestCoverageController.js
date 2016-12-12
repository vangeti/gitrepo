mainApp.controller('smTestCoverageController', function($scope,
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

	$('.smTCPanel').lobiPanel({
		// Options go here
		sortable : false,
		editTitle : false,
		reload : true,
		minimize : false,
		unpin : {
			icon : 'fa fa-thumb-tack'
		},
		close : false,
		expand : false,
		/*
		 * minimize : { icon : 'fa fa-chevron-up', icon2 : 'fa fa-chevron-down' },
		 */
		/*
		 * editTitle : { icon : 'fa fa-table tcTable', icon2 : 'fa fa-bar-chart
		 * tcChart', tooltip : 'View' },
		 */
		reload : {
			icon : 'fa fa-refresh'
		},
	/*
	 * close : { icon : 'fa fa-times-circle' }, expand : { icon : 'fa
	 * fa-expand', icon2 : 'fa fa-compress' }
	 */
	});

	$(".tcTable").on("click", function() {
		// alert($(this).attr("class"));
		if ($(this).attr("class").indexOf("tcTable") >= 0) {
			$scope.fire('table');
		} else {
			$scope.fire('chart');
		}
		$scope.$digest();
	});

	$('.smTCPanel').on('onPin.lobiPanel', function(ev, lobiPanel) {

		$(".testCoveragePanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#smTCChart").css({
			"width" : "600px",
			"height" : "300px"
		});
		drawValueChart();
	});

	$('.smTCPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
		lobiPanel.$options.expand = false;

		$(".smTCPanel").css({
			"left" : "150.5px",
			"top" : "122px",
			"height" : "80%",
			"width" : "80%"
		});
		$(".testCoveragePanel-body").css({
			"width" : "100%",
			"height" : "100%",
			"max-height" : "85%",
			"min-height" : "85%"
		});
		$("#smTCChart").css({
			"width" : "100%",
			"height" : "400px"
		});
		drawValueChart();
	});

	$('.smTCPanel .fa-refresh').on('click', function(ev, lobiPanel) {

		$('.smTC').hide();
		$('.smTcTitle').hide();
		$('#testCoverageLoadImg_id').show();
		drawValueChart();
	});

	google.charts.setOnLoadCallback(drawValueChart);

	/* $('#testCoverageLoadImg_id').show(); */

	function drawValueChart() {

		$http.get("devops/rest/smtc").then(
				function(response) {

					$('#testCoverageLoadImg_id').hide();
					$('.smTC').show();
					$('.smTcTitle').show();

					var jsonresp = response.data;
					var testCoverageMap = jsonresp.testCoverageMap;
					$scope.totalMap = jsonresp.totalMap;
					$scope.reqWithNoTestCases = jsonresp.reqWithNoTestCases;

					var keys = [];
					var values = [];
					for ( var key in testCoverageMap) {
						keys.push(key);
						values.push(testCoverageMap[key]);
					}
					var data = google.visualization.arrayToDataTable([
							[ 'Element', '', {
								role : 'style'
							} ], [ keys[0], parseFloat(values[0]), '#ff884d' ],
							[ keys[1], parseFloat(values[1]), '#ffaa80' ],
							[ keys[2], parseFloat(values[2]), '#ffbb99' ],
							[ keys[3], parseFloat(values[3]), '#ffddcc' ] ]);

					$scope.sTCTitle = jsonresp.meanTotal + '%';

					drawColumnChartfinal(data, 'Application Name',
							'Percentage', '100%', '100%', 'smTCChart');

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