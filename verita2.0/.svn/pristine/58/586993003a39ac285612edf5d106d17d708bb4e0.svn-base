mainApp.controller('JenBuildDetailsController', function($scope,
		$sessionStorage, $rootScope, $http, widgetService) {

	$scope.jbdcTitle = "Build Details"

	$('.jbdcPanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		reload : true,
		editTitle : false,
		minimize : false,
		close : false,
		expand : false,
		unpin : {
			icon : 'fa fa-thumb-tack'
		},
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

	$('.jbdcPanel').on('onPin.lobiPanel', function(ev, lobiPanel) {
		window.console.log("Pinned");
		$(".jbdcPanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#jbdcChart").css({
			"width" : "600px",
			"height" : "300px"
		});
		drawValueChart();
	});

	$('.jbdcPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
		lobiPanel.$options.expand = false;
		window.console.log("Unpinned");
		$(".jbdcPanel").css({
			"left" : "150.5px",
			"top" : "122px",
			"height" : "80%",
			"width" : "80%"
		});
		$(".jbdcPanel-body").css({
			"width" : "100%",
			"height" : "100%",
			"max-height" : "85%",
			"min-height" : "85%"
		});
		$("#jbdcChart").css({
			"width" : "100%",
			"height" : "400px"
		});
		drawValueChart();
	});

	/*$('.jbdcPanel').on('beforeClose.lobiPanel', function(ev, lobiPanel) {
		var widgetResp = widgetService.deleteWidget(

		$sessionStorage.userId, "jenkinsFlag");

		widgetResp.then(function(resp) {

			if (resp.data.Status === "Success") {
				$rootScope.tpsPanelFlag = false;
				$rootScope.widgetList.push({
					"key" : "jenkinsFlag",
					"value" : $scope.jenkinsFlag
				});
				$rootScope.jenkinsFlag = false;
			}
		}, function(errorPayload) {

		});
	});

	$('.jbdcPanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		$("#jbdcChart").css({
			"width" : "100%",
			"height" : "100%"
		});
		drawValueChart();
	});
	$('.jbdcPanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
		$(".panel-body").css({
			"max-height" : "300px",
			"min-height" : "300px"
		});
		$("#jbdcChart").css({
			"width" : "370px",
			"height" : "200px"
		});

		drawValueChart();
	});*/

	$('.jbdcPanel .fa-refresh').on('click', function(ev, lobiPanel) {

		$('.jbdc').hide();
		$('.jbdcTitle').hide();
		$('#jenBuildLoadImg_id').show();
		drawValueChart();
	});

	google.charts.setOnLoadCallback(drawValueChart);

	/*$('#testCoverageLoadImg_id').show();*/

	function drawValueChart() {

		$http.get("devops/rest/lndbcpd").then(
				function(response) {

					$('#jenBuildLoadImg_id').hide();
					$('#jbdcChart').show();
					$('.jbdcTitle').show();

					var jsonresp = response.data;
					console.log(jsonresp);

					var keys = [];
					var values = [];
					for ( var key in jsonresp) {
						console.log(jsonresp[key]);
						var item = jsonresp[key];
						for ( var key1 in item) {
							console.log(item[key1]['build no']);
							keys.push(item[key1]['date']);
							values.push(item[key1]['build no']);
						}

					}
					var data = google.visualization.arrayToDataTable([
							[ 'Element', '', {
								role : 'style'
							} ], [ keys[0], parseFloat(values[0]), '#ff884d' ],
							[ keys[1], parseFloat(values[1]), '#ff884d' ],
							[ keys[2], parseFloat(values[2]), '#ff884d' ],
							[ keys[3], parseFloat(values[3]), '#ff884d' ] ]);

					$scope.sTCTitle = 'Test Coverage' + ' ' + values[4] + '%';

					drawColumnChartfinal(data, 'Build Number', 'Count', '100%',
							'100%', 'jbdcChart');

				});
	}

	function drawColumnChartfinal(data, hAxisTitle, vAxisTitle, height, width,
			divContainer) {
		var color = "#000000";
		var options = {
			/*title : garphTitle,*/
			titleTextStyle : {
				color : '#999',
				fontName : 'Open Sans, Arial, Helvetica, sans-serif'
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
					fontStyle : 'normal'
				},
				textStyle : {
					color : '#999',
					fontSize : 12,
					fontName : 'Open Sans, Arial, Helvetica, sans-serif',
					fontStyle : 'normal'
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