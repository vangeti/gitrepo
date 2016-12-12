mainApp.controller('PerfRespTimeController', function($scope, $sessionStorage,
		$rootScope, widgetService) {

	$scope.respTTitle = 'Response Times';

	$('.resTimePanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		/*unpin : false,*/
		reload : true,
		editTitle : false,
		minimize : false,
		close : false,
		expand : false,
		unpin : {
			icon : 'fa fa-thumb-tack'
		},
		/*minimize : {
			icon : 'fa fa-chevron-up',
			icon2 : 'fa fa-chevron-down'
		},*/
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

	$('.resTimePanel').on('onPin.lobiPanel', function(ev, lobiPanel) {
		window.console.log("Pinned");
		$(".rtcPanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#perfrespcurrbuildChart").css({
			"width" : "600px",
			"height" : "300px"
		});
		drawValueChart();
	});

	$('.resTimePanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
		lobiPanel.$options.expand = false;
		window.console.log("Unpinned");
		$(".resTimePanel").css({
			"left" : "150.5px",
			"top" : "122px",
			"height" : "80%",
			"width" : "80%"
		});
		$(".rtcPanel-body").css({
			"width" : "100%",
			"height" : "100%",
			"max-height" : "85%",
			"min-height" : "85%"
		});
		$("#perfrespcurrbuildChart").css({
			"width" : "100%",
			"height" : "400px"
		});
		drawValueChart();
	});

	$('.resTimePanel').on(
			'beforeClose.lobiPanel',
			function(ev, lobiPanel) {
				var widgetResp = widgetService.deleteWidget(
						$sessionStorage.userId, "respTimePanelFlag");

				widgetResp.then(function(resp) {
					console.log("delete response");
					console.log(resp);
					if (resp.data.Status === "Success") {
						$rootScope.respTimePanelFlag = false;
						$rootScope.widgetList.push({
							"key" : "respTimePanelFlag",
							"value" : $scope.respTTitle
						});
					}
				}, function(errorPayload) {
					console.log(errorPayload);
				});
			});

	$('.resTimePanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {

		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		$("#perfrespcurrbuildChart").css({
			"width" : "900px",
			"height" : "600px"
		});
		drawValueChart();
	});
	$('.resTimePanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {

		$(".panel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#perfrespcurrbuildChart").css({
			"width" : "600px",
			"height" : "300px"
		});
		drawValueChart();
	});

	$('.resTimePanel .fa-refresh').on('click', function(ev, lobiPanel) {

		$('#perfRespTimeLoadImg_id').show();
		$('.perfRespTime').hide();
		$('.perfRespTitle').hide();

		drawValueChart();
	});

	google.charts.setOnLoadCallback(drawValueChart);

	/*$('#perfRespTimeLoadImg_id').show();*/

	var color = '#000'
	function drawValueChart() {
		/*alert("dfd");*/
		$('#perfRespTimeLoadImg_id').addClass("hide");

		$('.perfRespTime').show();
		$('.perfRespTitle').show();

		var data = google.visualization.arrayToDataTable([
				[ 'Elasped Time(Sec)', 'Response Time(Sec)' ], [ '10', 10 ],
				[ '20', 5 ], [ '30', 15 ], [ '40', 20 ], [ '50', 22 ],
				[ '60', 24 ], [ '70', 30 ], [ '80', 15 ],

		]);

		var options = {
			title : '',
			titleTextStyle : {
				color : color,
				fontName : 'Open Sans, Arial, Helvetica, sans-serif'
			},
			backgroundColor : 'white',
			colors : [ '#ff884d', ],
			timeline : {
				colorByRowLabel : false
			},
			legend : {
				position : 'none'
			},

			hAxis : {
				title : 'Elasped Time(Secs)',

				titleTextStyle : {
					color : '#999',
					fontName : 'Open Sans, Arial, Helvetica, sans-serif',
					fontSize : 12,
					fontStyle : 'normal'
				},
				textStyle : {
					color : '#999',
					fontName : 'Open Sans, Arial, Helvetica, sans-serif',
					fontSize : 12,
					fontStyle : 'normal'
				},
				direction : 1,
				slantedText : true,
				slantedTextAngle : 20

			},

			vAxis : {
				title : 'Response Time(Sec)',
				titleTextStyle : {
					color : '#999',
					fontName : 'Open Sans, Arial, Helvetica, sans-serif',
					fontSize : 12,
					fontStyle : 'normal'
				},
				textStyle : {
					color : '#999',
					fontName : 'Open Sans, Arial, Helvetica, sans-serif',
					fontSize : 12,
					fontStyle : 'normal'
				},
				gridlines : {
					color : 'transparent'
				}
			},
		};

		var chart = new google.visualization.SteppedAreaChart(document
				.getElementById('perfrespcurrbuildChart'));
		chart.draw(data, options);
	}

});