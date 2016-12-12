mainApp.controller('perfJMTransPerSecController', function($scope, $sessionStorage,
		$rootScope, $http, widgetService) {

	$scope.tpsPTitle = 'Transaction Per Second Trend';



	$('.jmtransPSPanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		unpin : false,
		reload : true,
		editTitle : false,
		minimize : false,
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

	$('.jmtransPSPanel').on('beforeClose.lobiPanel', function(ev, lobiPanel) {
		var widgetResp = widgetService.deleteWidget(
				$sessionStorage.userId, "JMtpsPanelFlag");

		widgetResp.then(function(resp) {
			console.log("delete response");
			console.log(resp);
			if (resp.data.Status === "Success") {
				$rootScope.tpsPanelFlag = false;
				$rootScope.widgetList.push({
					"key":"JMtpsPanelFlag",
					"value":$scope.tpsPTitle
				});
				$rootScope.JMtpsPanelFlag = false;
			}
		}, function(errorPayload) {
			console.log(errorPayload);
		});
	});

	$('.jmtransPSPanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value full event called");
		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		$("#jmtpsChart").css({
			"width" : "100%",
			"height" : "600px"
		});
		drawValueChart();
	});
	$('.jmtransPSPanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value small event called");
		$(".panel-body").css({
			"max-height" : "300px",
			"min-height" : "300px"
		});
		$("#jmtpsChart").css({
			"width" : "280px",
			"height" : "220px"
		});
		drawValueChart();
	});

	$('.jmtransPSPanel .fa-refresh').on('click', function(ev, lobiPanel){
		 // console.log("onReload.lobiPanel");
		$('.perfJMtps').hide();
		$('.jmTransPerSecTitle').hide();
		$('#perfJMTransPerSecLoadImg_id').show();
		
		drawValueChart();
		   });
	
	google.charts.setOnLoadCallback(drawValueChart);
	
	/*$('#perfJMTransPerSecLoadImg_id').show();*/
	
	var color = '#000'
	function drawValueChart() {

		$http.get("devops/rest/jmtpswb").then(
				function(response) {
					
					$('#perfJMTransPerSecLoadImg_id').hide();
					 
					 $('.jmTransPerSecTitle').show();
					 $('.perfJMtps').show();
					
					var jsonresp = response.data;
					console.log(jsonresp);

					var keys = [];
					var values = [];
					for ( var key in jsonresp) {
						console.log(key + ' is ' + jsonresp[key]);
						keys.push(key);
					    values.push(jsonresp[key]);
					}
				
					var data = google.visualization.arrayToDataTable([
							[ 'Build Number', 'Transaction Per Second', {
								role : 'style'
							} ], [ keys[0], parseFloat(values[0]), '#ff884d' ], [ keys[1], parseFloat(values[1]), '#ff884d' ],
							[ keys[2], parseFloat(0.0066311274), '#ff884d' ], [ keys[3], parseFloat(0.0046311274), '#ff884d'],
							[ keys[4], parseFloat(0.0056311274), '#ff884d' ] ]);

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
							title : 'Build Number',
							format : 'h:mm a',
							titleTextStyle : {
								color : color
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
							title : 'Transaction Per Second',
							titleTextStyle : {
								color : color
							},
							textStyle : {
								color : color
							}
						},

					};

					var chart = new google.visualization.ColumnChart(document
							.getElementById('jmtpsChart'));

					chart.draw(data, options);
				});
	}

});