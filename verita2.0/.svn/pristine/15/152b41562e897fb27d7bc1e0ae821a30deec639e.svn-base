mainApp.controller('smOpenDefectsController', function($scope, $sessionStorage,
		$rootScope, $http, widgetService) {

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

	$('.smOpenDefectsPanel').lobiPanel({
		// Options go here
		sortable : false,
		editTitle : false,
		reload : true,
		unpin : {
			icon : 'fa fa-thumb-tack'
		},
		minimize : false,
		close : false,
		expand : false,
		/*
		 * minimize : { icon : 'fa fa-chevron-up', icon2 : 'fa fa-chevron-down' },
		 */
		/*
		 * editTitle : { icon : 'fa fa-table odsTable', icon2 : 'fa fa-bar-chart
		 * odsChart', tooltip : 'View' },
		 */
		reload : {
			icon : 'fa fa-refresh'
		},
	/*
	 * close : { icon : 'fa fa-times-circle' }, expand : { icon : 'fa
	 * fa-expand', icon2 : 'fa fa-compress' }
	 */
	});

	$(".odsTable").on("click", function() {
		// alert($(this).attr("class"));
		if ($(this).attr("class").indexOf("odsTable") >= 0) {
			$scope.fire('table');
		} else {
			$scope.fire('chart');
		}
		$scope.$digest();
	});

	$('.smOpenDefectsPanel').on('onPin.lobiPanel', function(ev, lobiPanel) {

		$(".openDefectsPanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#smODSChart").css({
			"width" : "600px",
			"height" : "300px"
		});
		drawValueChart();
	});

	$('.smOpenDefectsPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
		lobiPanel.$options.expand = false;

		$(".smOpenDefectsPanel").css({
			"left" : "150.5px",
			"top" : "122px",
			"height" : "80%",
			"width" : "80%"
		});
		$(".openDefectsPanel-body").css({
			"width" : "100%",
			"height" : "100%",
			"max-height" : "85%",
			"min-height" : "85%"
		});
		$("#smODSChart").css({
			"width" : "100%",
			"height" : "400px"
		});
		drawValueChart();
	});

	$('.smOpenDefectsPanel .fa-refresh').on('click', function(ev, lobiPanel) {
		$('.smODS').hide();
		$('.smODSTitle').hide();
		$('#openDefectsLoadImg_id').show();
		drawValueChart();
	});

	google.charts.setOnLoadCallback(drawValueChart);

	/* $('#openDefectsLoadImg_id').show(); */

	function drawValueChart() {

		$http.get("devops/rest/smods").then(
				function(response) {

					/*
					 * $('#openDefectsLoadImg_id').hide(); $('.smODS').show();
					 * $('.smODSTitle').show();
					 */

					$('#openDefectsLoadImg_id').hide();
					$('.smODS').show();
					$('.smODSTitle').show();

					var jsonresp = response.data;
					var openDefectSeverityMap = jsonresp.openDefectSeverityMap;
					$scope.openDefects = jsonresp.openDefects;
					$scope.allOpenDefects = jsonresp.allOpenDefects;

					var keys = [];
					var values = [];
					for ( var key in openDefectSeverityMap) {
						keys.push(key);
						values.push(openDefectSeverityMap[key]);
					}

					var data = google.visualization.arrayToDataTable([
							[ 'Element', 'S1', 'S2', 'S3', 'S4', 'Total', {
								role : 'style'
							} ],
							[ keys[0], parseInt(values[0].severityOne),
									parseInt(values[0].severityTwo),
									parseInt(values[0].severityThree),
									parseInt(values[0].severityFour),
									parseInt(values[0].totalSeverity), '' ],
							[ keys[1], parseInt(values[1].severityOne),
									parseInt(values[1].severityTwo),
									parseInt(values[1].severityThree),
									parseInt(values[1].severityFour),
									parseInt(values[1].totalSeverity), '' ],
							[ keys[2], parseInt(values[2].severityOne),
									parseInt(values[2].severityTwo),
									parseInt(values[2].severityThree),
									parseInt(values[2].severityFour),
									parseInt(values[2].totalSeverity), '' ],
							[ keys[3], parseInt(values[3].severityOne),
									parseInt(values[3].severityTwo),
									parseInt(values[3].severityThree),
									parseInt(values[3].severityFour),
									parseInt(values[3].totalSeverity), '' ] ]);

					$scope.sODSTitle = jsonresp.allDefectSeverityCount;

					drawStackedColumnChart(data, 'Application Name',
							'Number of Defects', '100%', '100%', 'smODSChart');

				});

	}

	function drawStackedColumnChart(data, hAxisTitle, vAxisTitle, height,
			width, divContainer) {
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
			colors : [ '#ff9966', '#ffddcc', '#ffbb99', '#ffaa80', '#ff884d' ],
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
			isStacked : true,

		};

		var chart = new google.visualization.ColumnChart(document
				.getElementById(divContainer));

		chart.draw(data, options);
	}

});