mainApp.controller('securityVulnerabilityController', function($scope,
		$sessionStorage, $rootScope, $http, widgetService) {

	$scope.svcTitle = "Security Vulnerability";
	$scope.chartFlag = true;
	$scope.tableFlag = false;

	$scope.fire = function(type) {

		if (type === 'table') {
			$scope.chartFlag = false;
			$scope.tableFlag = true;
		} else {
			$scope.chartFlag = true;
			$scope.tableFlag = false;
		}
	}

	$('.securityPanel').lobiPanel({
		// Options go here
		/* sortable : false, */
		editTitle : false,
		/* unpin : false, */
		reload : true,
		close : false,
		expand : false,
		unpin : {
			icon : 'fa fa-thumb-tack'
		},
		/*
		 * editTitle : { icon : 'fa fa-table costTable', icon2 : 'fa
		 * fa-bar-chart costChart', tooltip : 'View' },
		 */

		minimize : false,
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

	$('.securityPanel').on('onPin.lobiPanel', function(ev, lobiPanel) {
		window.console.log("Pinned");
		$(".svcPanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#securityChart").css({
			"width" : "600px",
			"height" : "300px"
		});
		drawValueChart();
	});

	$('.securityPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
		lobiPanel.$options.expand = false;
		window.console.log("Unpinned");
		$(".securityPanel").css({
			"left" : "150.5px",
			"top" : "122px",
			"height" : "80%",
			"width" : "80%"
		});
		$(".svcPanel-body").css({
			"width" : "100%",
			"height" : "100%",
			"max-height" : "85%",
			"min-height" : "85%"
		});
		$("#securityChart").css({
			"width" : "100%",
			"height" : "400px"
		});
		drawValueChart();
	});

	$(".costTable").on("click", function() {
		// alert($(this).attr("class"));
		if ($(this).attr("class").indexOf("costTable") >= 0) {
			$scope.fire('table');
		} else {
			$scope.fire('chart');
		}
		$scope.$digest();
	});

	/*
	 * $('.securityPanel').on('beforeClose.lobiPanel', function(ev, lobiPanel) {
	 * var widgetResp = widgetService.deleteWidget(
	 * 
	 * $sessionStorage.userId, "SecVCGFlag");
	 * 
	 * 
	 * widgetResp.then(function(resp) {
	 * 
	 * if (resp.data.Status === "Success") { $rootScope.tpsPanelFlag = false;
	 * $rootScope.widgetList.push({ "key":"SecVCGFlag",
	 * "value":$scope.SecVCGFlag }); $rootScope.SecVCGFlag = false; } },
	 * function(errorPayload) {
	 * 
	 * }); });
	 * 
	 * $('.securityPanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
	 * $(".panel-body").css({ "max-height" : "100%", "min-height" : "100%" });
	 * $("#securityChart").css({ "width" : "900px", "height" : "600px" });
	 * drawValueChart(); }); $('.securityPanel').on('onSmallSize.lobiPanel',
	 * function(ev, lobiPanel) { $(".panel-body").css({ "max-height" : "300px",
	 * "min-height" : "300px" }); $("#securityChart").css({ "width" : "370px",
	 * "height" : "200px" });
	 * 
	 * drawValueChart(); });
	 */

	$('.securityPanel .fa-refresh').on('click', function(ev, lobiPanel) {

		$('#securityChart').hide();
		$('.securityTitle').hide();
		$('#securityLoadImg_id').show();
		drawValueChart();
	});

	google.charts.setOnLoadCallback(drawValueChart);

	/* $('#testCoverageLoadImg_id').show(); */

	function drawValueChart() {

		$('#securityLoadImg_id').hide();
		$('#securityChart').show();
		$('.securityTitle').show();

		var data = google.visualization.arrayToDataTable([ [ 'Element', '', {
			role : 'style'
		} ], [ 'Critical', 85, '#ff884d' ], [ 'High', 20, '#ffaa80' ],
				[ 'Medium', 312, '#ffbb99' ], [ 'Low', 30, '#ffddcc' ] ]);

		drawColumnChartfinal(data, 'Severity', 'Count', '100%', '100%',
				'securityChart');

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