mainApp.controller('perfJMErrorPerTrendController', function($scope, $sessionStorage, $rootScope, widgetService, $http) {
	
	$scope.errPTTitle = 'Error Percentage';

	$('.jmerrPerTPanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		unpin : false,
		reload : true,
		editTitle : false,
		minimize : false,
		reload: {
	        icon: 'fa fa-refresh'
	    },
		close : {
			icon : 'fa fa-times-circle'
		},
		expand : {
			icon : 'fa fa-expand',
			icon2 : 'fa fa-compress'
		}
	});

	$('.jmerrPerTPanel').on('beforeClose.lobiPanel', function(ev, lobiPanel) {
		var widgetResp = widgetService.deleteWidget(
				$sessionStorage.userId, "JMeperTPanelFlag");

		widgetResp.then(function(resp) {
			console.log("delete response");
			console.log(resp);
			if (resp.data.Status === "Success") {
				$rootScope.JMeperTPanelFlag = false;
				$rootScope.widgetList.push({
					"key":"JMeperTPanelFlag",
					"value":$scope.errPTTitle
				});
				
				$rootScope.JMeperTPanelFlag = false;
//				$rootScope.$digest();
			}
		}, function(errorPayload) {
			console.log(errorPayload);
		});
	});

	$('.jmerrPerTPanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value full event called");
		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		$("#jmerrPerTrendChart").css({
			"width" : "900px",
			"height" : "600px"
		});
		drawValueChart();
	});
	$('.jmerrPerTPanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value small event called");
		$(".panel-body").css({
			"max-height" : "300px",
			"min-height" : "300px"
		});
		$("#jmerrPerTrendChart").css({
			"width" : "280px",
			"height" : "220px"
		});
		drawValueChart();
	});
	
	$('.jmerrPerTPanel .fa-refresh').on('click', function(ev, lobiPanel){
		 // console.log("onReload.lobiPanel");
		$('.perfJMErrorTrend').hide();
		$('.jmErrorTrendTitle').hide();
		$('#perfJMErrorPerTrendLoadImg_id').show();
		drawValueChart();
		   });
	
	google.charts.setOnLoadCallback(drawValueChart);
	
	/*$('#perfJMErrorPerTrendLoadImg_id').show();*/
	
	var color = '#000'
	function drawValueChart() {
		
		$http.get("devops/rest/jmept").then(
				function(response) {
					
					$('#perfJMErrorPerTrendLoadImg_id').hide();
					
					$('.jmErrorTrendTitle').show();
					$('.perfJMErrorTrend').show();
					
					var jsonresp = response.data;
					console.log(jsonresp);

					var keys = [];
					var values = [];
					for ( var key in jsonresp) {
						console.log(key + ' error perc ' + jsonresp[key]);
						keys.push(key);
					    values.push(jsonresp[key]);
					}
		var data = google.visualization.arrayToDataTable([
		                                                  ['Build No', 'Error Perc'],
		                                                  [ keys[0], parseFloat(values[0]) ], [ keys[1], parseFloat(values[1])],
		                        							[ keys[2], parseFloat(values[2])], [ keys[3], parseFloat(values[3])],
		                        							[ keys[4], parseFloat(values[4])]
		                                                ]);

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
		                                    						title : 'Error Rate(%)',
		                                    						titleTextStyle : {
		                                    							color : color
		                                    						},
		                                    						textStyle : {
		                                    							color : color
		                                    						}
		                                    					},
		                                                };

		                                                var chart = new google.visualization.LineChart(document.getElementById('jmerrPerTrendChart'));

		                                                chart.draw(data, options);
				});
	}
	
});