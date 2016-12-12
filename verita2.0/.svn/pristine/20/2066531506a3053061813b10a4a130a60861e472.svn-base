mainApp.controller('LeadTimeController', function($scope, widgetService,
		$rootScope, $sessionStorage) {
	$scope.leadTimeTitle = "342 Days";
	$scope.leadChartFlag = true;
	$scope.leadTableFlag = false;

	$scope.leadView = function(type) {

		if (type === 'table') {
			$scope.leadChartFlag = false;
			$scope.leadTableFlag = true;
		} else {
			$scope.leadChartFlag = true;
			$scope.leadTableFlag = false;
		}
	}

	$('.leadPanel').lobiPanel({
		// Options go here
		sortable : false,
		editTitle : false,
		close : false,
		reload : true,
		minimize : false,
		expand : false,
		unpin : {
			icon : 'fa fa-thumb-tack'
		},
		/*editTitle : {
			icon : 'fa fa-table leadTable',
			icon2 : 'fa fa-bar-chart leadChart',
			tooltip : 'View'
		},*/
		reload : {
			icon : 'fa fa-refresh'
		},
	/*
	 * minimize : { icon : 'fa fa-chevron-up', icon2 : 'fa fa-chevron-down' },
	 */
	/*close : {
		icon : 'fa fa-times-circle'
	},*/
	/*expand : {
		icon : 'fa fa-expand',
		icon2 : 'fa fa-compress'
	}*/
	});

	$(".leadTable").on("click", function() {
		// alert($(this).attr("class"));
		if ($(this).attr("class").indexOf("leadTable") >= 0) {
			$scope.leadView('table');
		} else {
			$scope.leadView('chart');
		}
		$scope.$digest();
	});

	$('.leadPanel').on('onPin.lobiPanel', function(ev, lobiPanel) {

		$(".leadPanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#leadtime").css({
			"width" : "600px",
			"height" : "300px"
		});
		drawLeadTimeChart();
	});

	$('.leadPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
		lobiPanel.$options.expand = false;

		$(".leadPanel").css({
			"left" : "150.5px",
			"top" : "122px",
			"height" : "80%",
			"width" : "80%"
		});
		$(".leadPanel-body").css({
			"width" : "100%",
			"height" : "100%",
			"max-height" : "85%",
			"min-height" : "85%"
		});
		$("#leadtime").css({
			"width" : "100%",
			"height" : "400px"
		});
		drawLeadTimeChart();
	});

	$('.leadPanel').on(
			'beforeClose.lobiPanel',
			function(ev, lobiPanel) {
				var widgetResp = widgetService.deleteWidget(
						$sessionStorage.userId, "ltPanelFlag");

				widgetResp.then(function(resp) {

					if (resp.data.Status === "Success") {
						$rootScope.ltPanelFlag = false;
						$rootScope.widgetList.push({
							"key" : "ltPanelFlag",
							"value" : $scope.leadTimeTitle
						});
						//						$rootScope.$digest();
					}
				}, function(errorPayload) {

				});
			});

	$('.leadPanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		$("#leadtime").css({
			"width" : "100%",
			"height" : "600px"
		});
		drawLeadTimeChart();
	});
	$('.leadPanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
		$(".panel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#leadtime").css({
			"width" : "600px",
			"height" : "300px"
		});
		drawLeadTimeChart();
	});

	/*$('.leadPanel .fa-refresh').on('click', function(ev, lobiPanel){
		$('.lead').hide();
		$('#costLoadImg_id').show();
		drawLeadTimeChart();
		   });*/

	google.charts.setOnLoadCallback(drawLeadTimeChart);

	/*$('#leadTimeLoadImg_id').show();*/

	function drawLeadTimeChart() {

		$('#leadTimeLoadImg_id').hide();
		//$('.lead').show();

		var data = google.visualization.arrayToDataTable([
				[ 'Sprints', 'Reaction Time(days)', 'Cycle Time(Days)' ],
				[ 'Sprint1_WK1', 11, 17 ], [ 'Sprint1_WK2', 12, 23 ],
				[ 'Sprint2_WK1', 12, 31 ], [ 'Sprint2_WK2', 13, 33 ],
				[ 'Sprint3_WK1', 12, 32 ], [ 'Sprint3_WK2', 16, 39 ],
				[ 'Sprint4_WK1', 12, 29 ], [ 'Sprint4_WK2', 14, 36 ], ]);

		drawAreaChart(data, 'LeadTime: 342 Days', 'Sprints', 'Days', '100%',
				'100%', 'leadtime');

	}

	function drawAreaChart(data, garphTitle, hAxisTitle, vAxisTitle, height,
			width, divContainer) {
		var color = "#000000";
		var options = {
			legend : {
				position : 'none',
			/*maxLines : 2,
			textStyle : {
				color : color
			}*/
			},
			backgroundColor : 'white',
			/*
			 * height : height, width : width,
			 */

			colors : [ '#ff9966', '#ff661a' ],
			color : '#FFFFFF',
			// title : garphTitle,
			titleTextStyle : {
				color : '#000000',
				fontSize : 17
			},
			hAxis : {
				title : hAxisTitle,
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
				}
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
					italic : false,
				},
				gridlines : {
					color : 'transparent'
				}
			}
		};

		var chart = new google.visualization.AreaChart(document
				.getElementById(divContainer));
		chart.draw(data, options);
	}
});