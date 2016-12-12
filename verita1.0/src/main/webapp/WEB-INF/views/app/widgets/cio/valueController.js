mainApp.controller('ValueController', function($scope, widgetService,
		$rootScope, $sessionStorage) {
	
	$('#valueLoadImg_id').hide();
	$('#donutchart').show();

	$scope.valueTitle = "272k $ ";

	$('.ValuePanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		close : false,
		reload : true,
		editTitle : false,
		minimize : false,
		expand : false,
		unpin : {
			icon : 'fa fa-thumb-tack'
		},
		/*
		 * minimize : { icon : 'fa fa-chevron-up', icon2 : 'fa
		 * fa-chevron-down' },
		 */
		reload : {
			icon : 'fa fa-refresh'
		},
	/*close : {
		icon : 'fa fa-times-circle'
	},*/
	/*expand : {
		icon : 'fa fa-expand',
		icon2 : 'fa fa-compress'
	}*/
	});

	$('.ValuePanel').on('onPin.lobiPanel', function(ev, lobiPanel) {

		$(".valuePanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#donutchart").css({
			"width" : "600px",
			"height" : "300px"
		});
		drawValueChart();
	});

	$('.ValuePanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
		lobiPanel.$options.expand = false;

		$(".ValuePanel").css({
			"left" : "150.5px",
			"top" : "122px",
			"height" : "80%",
			"width" : "80%"
		});
		$(".valuePanel-body").css({
			"width" : "100%",
			"height" : "100%",
			"max-height" : "85%",
			"min-height" : "85%"
		});
		$("#donutchart").css({
			"width" : "100%",
			"height" : "400px"
		});
		drawValueChart();
	});

	$('.ValuePanel').on(
			'beforeClose.lobiPanel',
			function(ev, lobiPanel) {
				var widgetResp = widgetService.deleteWidget(
						$sessionStorage.userId, "vPanelFlag");

				widgetResp.then(function(resp) {

					if (resp.data.Status === "Success") {
						$rootScope.vPanelFlag = false;
						$rootScope.widgetList.push({
							"key" : "vPanelFlag",
							"value" : $scope.valueTitle
						});
						//						$rootScope.$digest();
					}
				}, function(errorPayload) {

				});
			});

	$('.ValuePanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		$("#donutchart").css({
			"width" : "900px",
			"height" : "600px"
		});
		drawValueChart();
	});
	$('.ValuePanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
		$(".panel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#donutchart").css({
			"width" : "600px",
			"height" : "300px"
		});
		drawValueChart();
	});

	$('.ValuePanel .fa-refresh').on('click', function(ev, lobiPanel) {
		$('.donut').hide();
		$('#valueLoadImg_id').show();
		drawValueChart();
	});

	google.charts.setOnLoadCallback(drawValueChart);

	/*$('#valueLoadImg_id').show();*/

	function drawValueChart() {

		/*	$('#valueLoadImg_id').hide();
			$('.donut').show();*/

		var data = google.visualization.arrayToDataTable([
				[ 'Task', 'Hours per Day' ], [ 'Value Realised (k USD)', 184 ],
				[ 'Value Pending Realization (k USD)', 88 ] ]);

		var options = {
			legend : {
				position : 'right',
				textStyle : {
					color : '#999',
					fontName : 'Open Sans, Arial, Helvetica, sans-serif',
					italic : false
				}
			/*,
			maxLines : 3,
			textStyle : {
			color : '#000000'
			}*/
			},
			colors : [ '#ff884d', '#ff661a' ],
			// title : 'Cost : 272k $ ',
			titleTextStyle : {
				color : '#999',
				fontName : 'Open Sans, Arial, Helvetica, sans-serif',
				fontSize : 16,
				italic : false
			},
			pieHole : 0.4,
		/*
		 * height : '500px', width : '500px',
		 */
		};

		var chart = new google.visualization.PieChart(document
				.getElementById('donutchart'));
		chart.draw(data, options);
	}
});