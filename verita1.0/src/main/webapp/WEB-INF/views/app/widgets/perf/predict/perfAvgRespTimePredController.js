mainApp.controller('perfAvgRespTimePredController', function($scope, indexService,
		$rootScope, widgetService, $http) {
	$scope.perfavgtimepredTitle = "Average Response Time";
	$scope.perfavgtimepredChartFlag = true;
	$scope.perfavgtimepredTableFlag = false;

	$scope.partPredView = function(type) {
		console.log(type);
		if (type === 'table') {
			$scope.perfavgtimepredChartFlag = false;
			$scope.perfavgtimepredTableFlag = true;
		} else {
			$scope.perfavgtimepredChartFlag = true;
			$scope.perfavgtimepredTableFlag = false;
		}
	}

	$('.perfavgtimepredPanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		unpin : false,
		reload : true,
		minimize : false,
		editTitle : {
			icon : 'fa fa-table partPredTable',
			icon2 : 'fa fa-bar-chart partPredChart',
			tooltip : 'View'
		},
		reload : {
			icon : 'fa fa-refresh'
		},
		minimize : false,
		/*
		 * minimize : { icon : 'fa fa-chevron-up', icon2 : 'fa fa-chevron-down' },
		 */
		close : {
			icon : 'fa fa-times-circle'
		},
		expand : {
			icon : 'fa fa-expand',
			icon2 : 'fa fa-compress'
		}
	});

	$(".partPredTable").on("click", function() {
		// alert($(this).attr("class"));
		if ($(this).attr("class").indexOf("partPredTable") >= 0) {
			$scope.partPredView('table');
		} else {
			$scope.partPredView('chart');
		}
		$scope.$digest();
	});

	$('.perfavgtimepredPanel').on('beforeClose.lobiPanel', function(ev, lobiPanel) {
		$rootScope.ltPanelFlag = false;
		$rootScope.widgetList.push({
			"key" : "ltPanelFlag",
			"value" : $scope.perfavgtimepredTitle
		});
		$rootScope.$digest();
	});

	$('.perfavgtimepredPanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
		window.console.log("lead full event called");
		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		$("#perfavgtimepred").css({
			"width" : "100%",
			"height" : "600px"
		});
		drawPerfAvgTimePredChart();
	});
	$('.perfavgtimepredPanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
		window.console.log("lead small event called");
		$(".panel-body").css({
			"max-height" : "300px",
			"min-height" : "300px"
		});
		$("#perfavgtimepred").css({
			"width" : "280px",
			"height" : "220px"
		});
		drawPerfAvgTimePredChart();
	});
	
	google.charts.setOnLoadCallback(drawPerfAvgTimePredChart);

	$('#perfAvgRespTimePredLoadImg_id').show();
	
	function drawPerfAvgTimePredChart() {
		
		$http.get("devops/rest/perfavgresptimepred").then(
				function(response) {
					
					$('#perfAvgRespTimePredLoadImg_id').hide();
					
					var jsonresp = response.data;
					console.log("avg resp time: :"+jsonresp[0].callsPerminute);
		
					$scope.perfARTPredDetails = jsonresp;
					
		var data = new google.visualization.DataTable();
		data.addColumn('number', 'Calls Per Minute');
		data.addColumn('number', 'Actual');
		data.addColumn('number', 'Prediction');

		data.addRows([ [ parseInt(jsonresp[0].callsPerminute), parseInt(jsonresp[0].actual), parseInt(jsonresp[0].prediction) ],
		               [ parseInt(jsonresp[1].callsPerminute), parseInt(jsonresp[1].actual), parseInt(jsonresp[1].prediction) ],
		               [ parseInt(jsonresp[2].callsPerminute), parseInt(jsonresp[2].actual), parseInt(jsonresp[2].prediction) ],
		               [ parseInt(jsonresp[3].callsPerminute), parseInt(jsonresp[3].actual), parseInt(jsonresp[3].prediction) ],
		               [ parseInt(jsonresp[4].callsPerminute), parseInt(jsonresp[4].actual), parseInt(jsonresp[4].prediction) ]
				 ]);

		var options = {
			legend : {
				position : 'bottom',
			},
			hAxis : {
				title : 'Calls Per Minute'
			},
			vAxis : {
				title : 'Response Time in MS'
			},
			series : {
				1 : {
					curveType : 'function'
				}
			}
		};
		var chart = new google.visualization.LineChart(document
				.getElementById('perfavgtimepred'));
		chart.draw(data, options);
	});

		
	}
	
});
	
	/*google.charts.setOnLoadCallback(drawPerfAvgTimePredChart);
	function drawPerfAvgTimePredChart() {
		
		$http.get("devops/rest/perfavgresptimepred").then(
				function(response) {
					var jsonresp = response.data;
					console.log("avg resp time: :"+jsonresp[0].callsPerminute);
		
					$scope.perfARTPredDetails = jsonresp;
					
		var data = google.visualization.arrayToDataTable([
				[ 'Calls Per Minute', 'Actual', 'Prediction' ],
				[jsonresp[0].callsPerminute, parseInt(jsonresp[0].actual), parseInt(jsonresp[0].prediction)],
				[jsonresp[1].callsPerminute, parseInt(jsonresp[1].actual), parseInt(jsonresp[1].prediction)],
				[jsonresp[2].callsPerminute, parseInt(jsonresp[2].actual), parseInt(jsonresp[2].prediction)],
				[jsonresp[3].callsPerminute, parseInt(jsonresp[3].actual), parseInt(jsonresp[3].prediction)],
				[jsonresp[4].callsPerminute, parseInt(jsonresp[4].actual), parseInt(jsonresp[4].prediction)],
				[ 'Sprint1_WK1', 11, 17 ], [ 'Sprint1_WK2', 12, 23 ],
				[ 'Sprint2_WK1', 12, 31 ], [ 'Sprint2_WK2', 13, 33 ],
				[ 'Sprint3_WK1', 12, 32 ], [ 'Sprint3_WK2', 16, 39 ],
				[ 'Sprint4_WK1', 12, 29 ], [ 'Sprint4_WK2', 14, 36 ], ]);

		drawAreaChart(data, 'Average Response Time', 'Calls per Minute', 'Response Time in ms', '100%',
				'100%', 'perfavgtimepred');
		
		});

	}

	function drawAreaChart(data, garphTitle, hAxisTitle,  vAxisTitle, height,
			width, divContainer) {
		var color = "#000000";
		var options = {
			legend : {
				position : 'bottom',
				maxLines : 2,
				textStyle : {
					color : color
				}
			},
			backgroundColor : 'white',
			
			 * height : height, width : width,
			 

			colors : [ '#008000', '#FF0000' ],
			color : '#FFFFFF',
			 title : garphTitle,
			titleTextStyle : {
				color : '#000000',
				fontSize : 17
			},
			hAxis : {
				title : hAxisTitle,
				titleTextStyle : {
					color : color
				},
				textStyle : {
					color : color
				}
			},
			vAxis : {
				title : vAxisTitle,
				titleTextStyle : {
					color : color
				},
				textStyle : {
					color : color
				}
			}
		};

		var chart = new google.visualization.AreaChart(document
				.getElementById(divContainer));
		chart.draw(data, options);
	}
});*/