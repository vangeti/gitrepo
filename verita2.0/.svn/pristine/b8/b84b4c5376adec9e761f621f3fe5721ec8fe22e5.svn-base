mainApp.controller('perfJMAvgRespTimeController', function($scope,
		$sessionStorage, $rootScope, $http, widgetService) {

	$scope.artTitle = 'Avg Response Time';

	$scope.tpsKPI = false;
	

	$('.jmavgRtPanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		unpin : false,
		reload : true,
		minimize : false,
		editTitle : {
			icon : 'fa fa-line-chart artJMKPI',
			icon2 : 'fa fa-bar-chart artJMChart',
			tooltip : 'Plot Trend Against KPI'
		},
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

	$(".artJMKPI").on("click", function() {
		// alert($(this).attr("class"));
		if ($(this).attr("class").indexOf("artJMKPI") >= 0) {
			$scope.tpsKPI = true;
			drawValueComboChart();
		} else {
			$scope.tpsKPI = false;
			drawValueChart();
		}
	});

	$('.jmavgRtPanel').on(
			'beforeClose.lobiPanel',
			function(ev, lobiPanel) {
				var widgetResp = widgetService.deleteWidget(
						$sessionStorage.userId, "JMavgRTPanelFlag");

				widgetResp.then(function(resp) {
					console.log("delete response");
					console.log(resp);
					if (resp.data.Status === "Success") {
						$rootScope.JMavgRTPanelFlag = false;
						$rootScope.widgetList.push({
							"key" : "JMavgRTPanelFlag",
							"value" : $scope.artTitle
						});
//						 $rootScope.$digest();
					}
				}, function(errorPayload) {
					console.log(errorPayload);
				});
			});

	$('.jmavgRtPanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value full event called");
		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		$("#jmavgrespTime").css({
			"width" : "100%",
			"height" : "580px"
		});
		if ($scope.tpsKPI) {
			drawValueComboChart();
		} else {
			drawValueChart();
		}
	});
	$('.jmavgRtPanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value small event called");
		$(".panel-body").css({
			"max-height" : "300px",
			"min-height" : "300px"
		});
		$("#jmavgrespTime").css({
			"width" : "280px",
			"height" : "220px"
		});
		if ($scope.tpsKPI) {
			drawValueComboChart();
		} else {
			drawValueChart();
		}
	});
	
	$('.jmavgRtPanel .fa-refresh').on('click', function(ev, lobiPanel){
		 // console.log("onReload.lobiPanel");
		$('.jmAvgRespTitle').hide();
		$('.perfJMAvgResp').hide();
		$('#perfJMAvgRespLoadImg_id').show();
		drawValueChart();
		   });

	google.charts.setOnLoadCallback(drawValueChart);
	
	$('#perfJMAvgRespLoadImg_id').show();
	
	var color = '#000'
	function drawValueChart() {
		keys = [];
		values = [];
		$http.get("devops/rest/jmrtfpb").then(function(response) {
			
			console.log("Madhu===");
			console.log(response);
			
			$('#perfJMAvgRespLoadImg_id').hide();
			
			$('.jmAvgRespTitle').show();
			$('.perfJMAvgResp').show();
			
			var jsonresp = response.data;
			
			for ( var key in jsonresp) {
				console.log(key + ' is ' + jsonresp[key]);
				keys.push(key);
				values.push(jsonresp[key]);
			}

		
		var data = google.visualization.arrayToDataTable([
				[ 'Build No', 'Avg Resp Time(sec)', {
					role : 'style'
				} ],
				[ keys[0], parseFloat(values[0]), '#ff884d' ],
				[ keys[1], parseFloat(values[1]), '#ff884d' ],
				[ keys[2], parseFloat(values[2]), '#ff884d' ],
				[ keys[3], parseFloat(values[3]), '#ff884d' ],
				[ keys[4], parseFloat(values[4]), '#ff884d' ] ]);

		var options = {
			title : '',
			titleTextStyle : {
				color : color,
			},
			backgroundColor : 'white',
			timeline : {
				colorByRowLabel : false
			},
			legend : {
				position : 'none'
			},
			hAxis : {
				title : 'Build No',

				titleTextStyle : {
					color : color,

				},
				textStyle : {
					color : color,
					fontSize : 10
				},
				direction : 1,
				slantedText : true,
				slantedTextAngle : 20

			},

			vAxis : {
				title : 'Avg Resp Time(sec)',

				titleTextStyle : {
					color : color
				},
				textStyle : {
					color : color
				}
			},

		};

		var chart = new google.visualization.ColumnChart(document
				.getElementById('jmavgrespTime'));
		chart.draw(data, options);
		});
	}

/*	function drawValueComboChart() {
		keys = [];
		values = [];
		$http.get("devops/rest/jmrtfpb").then(function(response) {
			var jsonresp = response.data;

			for ( var key in jsonresp) {
				console.log(key + ' is ' + jsonresp[key]);
				keys.push(key);
				values.push(jsonresp[key]);
			}

		});

		var data = google.visualization.arrayToDataTable([
				[ 'Build No', 'Avg Resp Time(sec)', 'ART' ],
				[ keys[0], parseFloat(values[0]), 1.2 ],
				[ keys[1], parseFloat(values[1]), 1.2 ],
				[ keys[2], parseFloat(values[2]), 1.2 ],
				[ keys[3], parseFloat(values[3]), 1.2 ],
				[ keys[4], parseFloat(values[4]), 1.2 ] ]);

		var options = {
			title : '',
			titleTextStyle : {
				color : color,
			},
			backgroundColor : 'white',
			timeline : {
				colorByRowLabel : false
			},
			legend : {
				position : 'none'
			},
			hAxis : {
				title : 'Build No',

				titleTextStyle : {
					color : color,

				},
				textStyle : {
					color : color,
					fontSize : 10
				},
				direction : 1,
				slantedText : true,
				slantedTextAngle : 20

			},
			series : {
				1 : {
					type : 'line'
				}
			},
			colors : [ '#ff884d', 'red' ],
			vAxis : {
				title : 'Avg Resp Time(sec)',

				titleTextStyle : {
					color : color
				},
				textStyle : {
					color : color
				}
			},

		};

		var chart = new google.visualization.ColumnChart(document
				.getElementById('jmavgrespTime'));
		chart.draw(data, options);
	}*/

});