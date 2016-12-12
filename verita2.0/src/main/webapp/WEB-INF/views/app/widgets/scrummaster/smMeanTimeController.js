mainApp.controller('smMeanTimeController', function($scope, $sessionStorage,
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

	$('.smMTTFPanel').lobiPanel({
		// Options go here
		sortable : false,
		editTitle : false,
		unpin : {
			icon : 'fa fa-thumb-tack'
		},
		reload : true,
		minimize : false,
		close : false,
		expand : false,
		/*
		 * minimize : { icon : 'fa fa-chevron-up', icon2 : 'fa fa-chevron-down' },
		 */
		/*
		 * editTitle : { icon : 'fa fa-table mtTable', icon2 : 'fa fa-bar-chart
		 * mtChart', tooltip : 'View' },
		 */
		reload : {
			icon : 'fa fa-refresh'
		},
	/*
	 * close : { icon : 'fa fa-times-circle' }, expand : { icon : 'fa
	 * fa-expand', icon2 : 'fa fa-compress' }
	 */
	});

	$(".mtTable").on("click", function() {
		// alert($(this).attr("class"));
		if ($(this).attr("class").indexOf("mtTable") >= 0) {
			$scope.fire('table');
		} else {
			$scope.fire('chart');
		}
		$scope.$digest();
	});

	$('.smMTTFPanel').on('onPin.lobiPanel', function(ev, lobiPanel) {
		window.console.log("Pinned");
		$(".meanTimePanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#smMTTFChart").css({
			"width" : "600px",
			"height" : "300px"
		});
		drawValueChart();
	});

	$('.smMTTFPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
		lobiPanel.$options.expand = false;
		window.console.log("Unpinned");
		$(".smMTTFPanel").css({
			"left" : "150.5px",
			"top" : "122px",
			"height" : "80%",
			"width" : "80%"
		});
		$(".meanTimePanel-body").css({
			"width" : "100%",
			"height" : "100%",
			"max-height" : "85%",
			"min-height" : "85%"
		});
		$("#smMTTFChart").css({
			"width" : "100%",
			"height" : "400px"
		});
		drawValueChart();
	});

	/*
	 * $('.smMTTFPanel').on('beforeClose.lobiPanel', function(ev, lobiPanel) {
	 * var widgetResp = widgetService.deleteWidget(
	 * 
	 * $sessionStorage.userId, "rmMTTFFlag");
	 * 
	 * widgetResp.then(function(resp) {
	 * 
	 * if (resp.data.Status === "Success") { $rootScope.tpsPanelFlag = false;
	 * $rootScope.widgetList.push({ "key" : "rmMTTFFlag", "value" :
	 * $scope.sMTTFTitle }); $rootScope.rmMTTFFlag = false; } },
	 * function(errorPayload) {
	 * 
	 * }); });
	 * 
	 * $('.smMTTFPanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
	 * 
	 * $(".panel-body").css({ "max-height" : "100%", "min-height" : "100%" });
	 * $("#smMTTFChart").css({ "width" : "900px", "height" : "600px" });
	 * drawValueChart(); }); $('.smMTTFPanel').on('onSmallSize.lobiPanel',
	 * function(ev, lobiPanel) {
	 * 
	 * $(".panel-body").css({ "max-height" : "300px", "min-height" : "300px" });
	 * $("#smMTTFChart").css({ "width" : "600px", "height" : "300px" });
	 * drawValueChart(); });
	 */

	$('.smMTTFPanel .fa-refresh').on('click', function(ev, lobiPanel) {

		$('.smMTTF').hide();
		$('.smMTTFTitle').hide();
		$('#meanTimeLoadImg_id').show();
		drawValueChart();
	});

	google.charts.setOnLoadCallback(drawValueChart);

	/* $('#meanTimeLoadImg_id').show(); */

	function drawValueChart() {

		$http.get("devops/rest/smmt")
				.then(
						function(response) {

							$('#meanTimeLoadImg_id').hide();
							$('.smMTTF').show();
							$('.smMTTFTitle').show();

							var jsonresp = response.data;
							var meanTimeMap = jsonresp.meanTimeMap;
							$scope.mTimeMap = meanTimeMap;

							var keys = [];
							var values = [];
							for ( var key in meanTimeMap) {

								keys.push(key);
								values.push(meanTimeMap[key]);
							}
							var data = google.visualization
									.arrayToDataTable([
											[ 'Element', '', {
												role : 'style'
											} ],
											[ keys[0], parseFloat(values[0]),
													'#ff884d' ],
											[ keys[1], parseFloat(values[1]),
													'#ffaa80' ],
											[ keys[2], parseFloat(values[2]),
													'#ffbb99' ],
											[ keys[3], parseFloat(values[3]),
													'#ffddcc' ] ]);

							$scope.sMTTFTitle = jsonresp.meanTotal + ' days';

							drawColumnChartfinal(data,

							'Application Name', 'Hours', '100%', '100%',
									'smMTTFChart');

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

	/*
	 * google.charts.setOnLoadCallback(drawValueChart); var color = '#000'
	 * function drawValueChart() {
	 * 
	 * $http.get("devops/rest/scrummastermeantime").then( function(response) {
	 * var jsonresp = response.data; console.log(jsonresp);
	 * 
	 * var keys = []; var values = []; for ( var key in jsonresp) {
	 * console.log(key + ' is ' + jsonresp[key]); keys.push(key);
	 * values.push(jsonresp[key]); }
	 * 
	 * var data = google.visualization.arrayToDataTable([ [ 'Application Name',
	 * 'Hours', { role : 'style' } ], [ keys[0], parseFloat(values[0]),
	 * '#ff0000' ], [ keys[1], parseFloat(values[1]), '#0066cc' ], [ keys[2],
	 * parseFloat(values[2]), '#006600' ], [ keys[3], parseFloat(values[3]),
	 * '#ff9933' ] ]);
	 * 
	 * var options = { title : '', titleTextStyle : { color : color, },
	 * backgroundColor : 'white', timeline : { colorByRowLabel : false }, legend : {
	 * position : 'none' }, hAxis : { title : 'Application Name', format : 'h:mm
	 * a', titleTextStyle : { color : color }, textStyle : { color : color,
	 * fontSize : 10 }, direction : 1, slantedText : true, slantedTextAngle : 20
	 *  },
	 * 
	 * vAxis : { title : 'Hours', titleTextStyle : { color : color }, textStyle : {
	 * color : color } },
	 *  };
	 * 
	 * var chart = new google.visualization.ColumnChart(document
	 * .getElementById('smMTTFChart'));
	 * 
	 * chart.draw(data, options); }); }
	 */

});