mainApp.controller('PerfJMErrorPerController', function($scope, $sessionStorage, $rootScope, widgetService,$http) {
	
	$scope.errPTitle = 'Error Percentile Current Build';
	

	
	$('.jmerrPercPanel').lobiPanel({
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

	$('.jmerrPercPanel').on('beforeClose.lobiPanel', function(ev, lobiPanel) {
		var widgetResp = widgetService.deleteWidget(
				$sessionStorage.userId, "JMepercPanelFlag");

		widgetResp.then(function(resp) {
			console.log("delete response");
			console.log(resp);
			if (resp.data.Status === "Success") {
				$rootScope.JMepercPanelFlag = false;
				$rootScope.widgetList.push({
					"key":"JMepercPanelFlag",
					"value":$scope.errPTitle
				});
				
				$rootScope.JMepercPanelFlag = false;
//				$rootScope.$digest();
			}
		}, function(errorPayload) {
			console.log(errorPayload);
		});
	});

	$('.jmerrPercPanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value full event called");
		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		$("#jmerrorPercChart").css({
			"width" : "900px",
			"height" : "600px"
		});
		drawValueChart();
	});
	$('.jmerrPercPanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value small event called");
		$(".panel-body").css({
			"max-height" : "300px",
			"min-height" : "300px"
		});
		$("#jmerrorPercChart").css({
			"width" : "280px",
			"height" : "220px"
		});
		drawValueChart();
	});
	
	$('.jmerrPercPanel .fa-refresh').on('click', function(ev, lobiPanel){
		 // console.log("onReload.lobiPanel");
		$('.perfJMErrorPer').hide();
		$('.jmErrorPerTitle').hide();
		$('#perfJMErrorPerLoadImg_id').show();
		drawValueChart();
		   });
	
	google.charts.setOnLoadCallback(drawValueChart);
	
	$('#perfJMErrorPerLoadImg_id').show();
	
	var color = '#000'
	function drawValueChart() {
		$http.get("devops/rest/jmetflb").then(
				function(response) {
					
					$('#perfJMErrorPerLoadImg_id').hide();
					
					$('.jmErrorPerTitle').show();
					$('.perfJMErrorPer').show();
					
					var jsonresp = response.data;
					
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
		/* var data = google.visualization.arrayToDataTable([
		                                                   ['Elasped Time', 'ErrorRate'],
		                                                   ['10',  10],
		                                                   ['20',  5],
		                                                   ['30',  15],
		                                                   ['40',  20],
		                                                   ['50',  22],
		                                                   ['60',  24],
		                                                   ['70',  30],
		                                                   ['80',  15],
		                                                   
		                                                 ]);*/

		                                                 var options = {
		                                     					title : '',
		                                    					titleTextStyle : {
		                                    						color : color,
		                                    					},
		                                    					backgroundColor : 'white',
		                                    					colors : [ '#FF0000',],
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
		                                    						title : 'Error Rate(%)',
		                                    						titleTextStyle : {
		                                    							color : color
		                                    						},
		                                    						textStyle : {
		                                    							color : color
		                                    						}
		                                    					},
		                                                 };

		                                                 var chart = new google.visualization.AreaChart(document.getElementById('jmerrorPercChart'));
		                                                 chart.draw(data, options);
		                                                 
		                                                 
				});
	}
	
});