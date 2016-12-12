mainApp.controller('CostController', function($scope, widgetService,
		$rootScope, $sessionStorage) {
	
	$('#costLoadImg_id').show();
	$('.costChart').hide();

	$scope.costTitle = "Total Cost Of Quality: 154k $";
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

	$('.CostPanel').lobiPanel({
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
			icon : 'fa fa-table costTable',
			icon2 : 'fa fa-bar-chart costChart',
			tooltip : 'View'
		},*/
		reload : {
			icon : 'fa fa-refresh'
		},
	/*
	 * minimize : { icon : 'fa fa-chevron-up', icon2 : 'fa
	 * fa-chevron-down' },
	 */
	/*close : {
		icon : 'fa fa-times-circle'
	},*/
	/*expand : {
		icon : 'fa fa-expand',
		icon2 : 'fa fa-compress'
	}*/
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

	$('.CostPanel').on('onPin.lobiPanel', function(ev, lobiPanel) {

		$(".costPanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#chart_div").css({
			"width" : "600px",
			"height" : "300px"
		});
		drawVisualization();
		$scope.$digest();
	});

	$('.CostPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
		lobiPanel.$options.expand = false;

		$(".CostPanel").css({
			"left" : "150.5px",
			"top" : "122px",
			"height" : "80%",
			"width" : "80%"
		});
		$(".costPanel-body").css({
			"width" : "100%",
			"height" : "100%",
			"max-height" : "85%",
			"min-height" : "85%"
		});
		$("#chart_div").css({
			"width" : "100%",
			"height" : "400px"
		});
		drawVisualization();
		$scope.$digest();
	});

	$('.CostPanel').on(
			'beforeClose.lobiPanel',
			function(ev, lobiPanel) {

				var widgetResp = widgetService.deleteWidget(
						$sessionStorage.userId, "cPanelFlag");

				widgetResp.then(function(resp) {
					console.log("delete response");
					console.log(resp);
					if (resp.data.Status === "Success") {
						$rootScope.cPanelFlag = false;
						$rootScope.widgetList.push({
							"key" : "cPanelFlag",
							"value" : $scope.costTitle
						});
						//								$rootScope.$digest();
					}
				}, function(errorPayload) {
					console.log(errorPayload);
				});
			});

	$('.CostPanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
		window.console.log("cost full event called");
		$scope.costTitle = "Total Cost Of Quality: 154k $";
		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		$("#chart_div").css({
			"width" : "100%",
			"height" : "600px"
		});
		drawVisualization();
		$scope.$digest();
	});
	$('.CostPanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
		window.console.log("cost small event called");
		$scope.costTitle = "Total Cost Of Quality: 154k $";
		$(".panel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#chart_div").css({
			"width" : "600px",
			"height" : "300px"
		});
		drawVisualization();
		$scope.$digest();
	});

	$('.CostPanel .fa-refresh').on('click', function(ev, lobiPanel) {
		// console.log("onReload.lobiPanel");
		/*	$('.cost').hide();
			$('#costLoadImg_id').show();*/
		drawVisualization();
	});

	google.charts.setOnLoadCallback(drawVisualization);

	/*$('#costLoadImg_id').show();*/

	function drawVisualization() {

		$('#costLoadImg_id').hide();
		$('.costChart').show();
		// Some raw data (not necessarily accurate)
		var data = google.visualization.arrayToDataTable([
				[ 'Genre', 'Prevention Costs (k USD)',
						'Appraisal Costs (k USD)', 'Internal Failure Costs',
						'External Failure Costs' ],
				[ 'Good Quality', 46, 26, 0, 0 ],
				[ 'Poor Quality', 0, 0, 46, 36 ] ]);

		var options = {
			title : null,
			titleTextStyle : {
				color : '#000000', // any HTML string color
				fontSize : 17, // 12, 18 whatever you want
			// (don't specify px)
			},
			series : {
				0 : {
					color : '#ff884d'
				},
				1 : {
					color : '#ffbb99'
				},
				2 : {
					color : '#ffddcc'
				},
				3 : {
					color : '#ffaa80'
				},

			},
			legend : {
				position : 'none'
			/*,
			maxLines : 3,
			textStyle : {
				color : '#000000'
			}*/
			},
			vAxis : {
				title : 'Cost(USD)',
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
			hAxis : {
				title : 'Applications',
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
			seriesType : 'bars',
			isStacked : true
		};

		var chart = new google.visualization.BarChart(document
				.getElementById('chart_div'));
		chart.draw(data, options);
	}
});