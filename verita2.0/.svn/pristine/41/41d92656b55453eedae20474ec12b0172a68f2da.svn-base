mainApp.controller('perfJMRespTimeController', function($scope, $sessionStorage, $rootScope, widgetService,$http) {
	
	$scope.respTTitle = 'Response Times';
	

	
	$('.jmresTimePanel').lobiPanel({
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

	$('.jmresTimePanel').on('beforeClose.lobiPanel', function(ev, lobiPanel) {
		var widgetResp = widgetService.deleteWidget(
				$sessionStorage.userId, "JMrespTimePanelFlag");

		widgetResp.then(function(resp) {
			console.log("delete response");
			console.log(resp);
			if (resp.data.Status === "Success") {
				$rootScope.JMrespTimePanelFlag = false;
				$rootScope.widgetList.push({
					"key":"JMrespTimePanelFlag",
					"value":$scope.respTTitle
				});
				$rootScope.JMrespTimePanelFlag = false;
			}
		}, function(errorPayload) {
			console.log(errorPayload);
		});
	});

	$('.jmresTimePanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value full event called");
		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		$("#jmperfrespcurrbuildChart").css({
			"width" : "900px",
			"height" : "600px"
		});
		drawValueChart();
	});
	$('.jmresTimePanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value small event called");
		$(".panel-body").css({
			"max-height" : "300px",
			"min-height" : "300px"
		});
		$("#jmperfrespcurrbuildChart").css({
			"width" : "280px",
			"height" : "220px"
		});
		drawValueChart();
	});
	
	$('.jmresTimePanel .fa-refresh').on('click', function(ev, lobiPanel){
		 // console.log("onReload.lobiPanel");
		$('.perfJMRespTime').hide();
		$('.jmRespTitle').hide();
		$('#perfJMRespTimeLoadImg_id').show();
		drawValueChart();
		   });
	
	google.charts.setOnLoadCallback(drawValueChart);
	
	$('#perfJMRespTimeLoadImg_id').show();
	
	var color = '#000'
	function drawValueChart() {
		
		$http.get("devops/rest/jmrtlb").then(
				function(response) {
					
					$('#perfJMRespTimeLoadImg_id').hide();
					
					$('.perfJMRespTime').show();
					$('.jmRespTitle').show();
					
					var jsonresp = response.data;
					console.log("***************************");
					console.log(jsonresp);

					var keys = [];
					var values = [];
					for ( var key in jsonresp) {
						console.log(key + ' is ' + jsonresp[key]);
						keys.push((key/ 1000).toFixed(1));
						values.push((jsonresp[key]/ 1000).toFixed(1));
					}
					var data = google.visualization.arrayToDataTable([
							[ 'Elasped Time(Sec)', 'Response Time(Sec)' ], 
							[ ""+keys[0]+"", parseFloat(values[0])],
							[ ""+keys[1]+"", parseFloat(values[1]) ],
							[ ""+keys[2]+"", parseFloat(values[2])],
							[ ""+keys[3]+"", parseFloat(values[3]) ],
							[ ""+keys[4]+"", parseFloat(values[4])] ]);
					console.log(data)
		/* var data = google.visualization.arrayToDataTable([
		                                                   ['Elasped Time(Sec)', 'Response Time(Sec)'],
		                                                   ['10',  10],
		                                                   ['20',  5],
		                                                   ['30',  15],
		                                                   ['40',  20],
		                                                   ['50',  22],
		                                                   ['60',  24],
		                                                   ['70',  30],
		                                                   ['80',  15],
		                                                   
		                                                 ]);
*/
		                                                 var options = {
		                                     					title : '',
		                                    					titleTextStyle : {
		                                    						color : color,
		                                    					},
		                                    					backgroundColor : 'white',
		                                    					colors : [ '#006400',],
		                                    					timeline : {
		                                    						colorByRowLabel : false
		                                    					},
		                                    					legend : {
		                                    						position : 'none'
		                                    					},
		                                    					
		                                    					hAxis : {
		                                    						title : 'Elasped Time(Secs)',
		                                    						
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
		                                    						title : 'Response Time(Sec)',
		                                    						titleTextStyle : {
		                                    							color : color
		                                    						},
		                                    						textStyle : {
		                                    							color : color
		                                    						}
		                                    					},
		                                                 };

		                                                 var chart = new google.visualization.SteppedAreaChart(document.getElementById('jmperfrespcurrbuildChart'));
				                                                chart.draw(data, options);
				                                                
				}); 
	}
	
});