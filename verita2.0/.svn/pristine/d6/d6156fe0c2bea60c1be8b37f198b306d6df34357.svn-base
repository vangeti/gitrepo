mainApp.controller('PerfErrorPerController', function($scope, $sessionStorage, $rootScope, widgetService) {
	
	$scope.errPTitle = 'Error Percentile Current Buildss';

	
	$('.errPercPanel').lobiPanel({
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

	$('.errPercPanel').on('onPin.lobiPanel', function(ev, lobiPanel){
	    window.console.log("Pinned");
	    $(".epPanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#errorPercChart").css({
			"width" : "600px",
			"height" : "300px"
		});
		drawValueChart();
	});
	
	$('.errPercPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel){
		lobiPanel.$options.expand = false;
	    window.console.log("Unpinned");
	    $(".errPercPanel").css({
	    	"left" : "150.5px",
	    	"top" : "122px",
			"height" : "80%",
			"width" : "80%"
		});
	    $(".epPanel-body").css({
	    	"width" : "100%",
			"height" : "100%",
			"max-height" : "85%",
			"min-height" : "85%"
		});
		$("#errorPercChart").css({
			"width" : "100%",
			"height" : "400px"
		});
		drawValueChart();
	});

	
	$(".artKPI").on("click", function() {
		
		if ($(this).attr("class").indexOf("artKPI") >= 0) {
			$scope.tpsKPI = true;
			drawValueComboChart();
		} else {
			$scope.tpsKPI = false;
			drawValueChart();
		}
	});
	
	$('.errPercPanel').on('beforeClose.lobiPanel', function(ev, lobiPanel) {
		var widgetResp = widgetService.deleteWidget(

				$sessionStorage.userId, "epercPanelFlag");


		widgetResp.then(function(resp) {
		
			
			if (resp.data.Status === "Success") {
				$rootScope.epercPanelFlag = false;
				$rootScope.widgetList.push({
					"key":"epercPanelFlag",
					"value":$scope.errPTitle
				});
				$rootScope.epercPanelFlag = false;
			}
		}, function(errorPayload) {
			
		});
	});

	$('.errPercPanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
	
		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		$("#errorPercChart").css({
			"width" : "900px",
			"height" : "600px"
		});
		drawValueChart();
	});
	$('.errPercPanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
		
		$(".panel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#errorPercChart").css({
			"width" : "600px",
			"height" : "300px"
		});
		drawValueChart();
	});
	
	$('.errPercPanel .fa-refresh').on('click', function(ev, lobiPanel){
		
		$('.perfErrorPer').hide();
		$('.errorPerTitle').hide();
		$('#perfErrorPerLoadImg_id').show();
		drawValueChart();
		   });
	
	google.charts.setOnLoadCallback(drawValueChart);
	/*
	$('#perfErrorPerLoadImg_id').show();*/
	
	var color = '#000'
	function drawValueChart() {
		
		$('#perfErrorPerLoadImg_id').hide();
		
		$('.errorPerTitle').show();
		$('.perfErrorPer').show();
		
		 var data = google.visualization.arrayToDataTable([
		                                                   ['Elasped Time', 'ErrorRate'],
		                                                   ['10',  10],
		                                                   ['20',  5],
		                                                   ['30',  15],
		                                                   ['40',  20],
		                                                   ['50',  22],
		                                                   ['60',  24],
		                                                   ['70',  30],
		                                                   ['80',  15],
		                                                   
		                                                 ]);

		                                                 var options = {
		                                     					title : '',
		                                    					titleTextStyle : {
		                                    						color : color,
		                                    						fontName : 'Open Sans, Arial, Helvetica, sans-serif'
		                                    					},
		                                    					backgroundColor : 'white',
		                                    					colors : [ '#ff884d'],
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
		                                    							italic: false
		                                    						},
		                                    						textStyle : {
		                                    							color : '#999',
		                                    							fontSize : 12,
		                                    							fontName : 'Open Sans, Arial, Helvetica, sans-serif',
		                                    							fontSize : 12,
		                                    							italic: false
		                                    						},
		                                    						direction : 1,
		                                    						slantedText : true,
		                                    						slantedTextAngle : 20

		                                    					},

		                                    					vAxis : {
		                                    						title : 'Error Rate(%)',
		                                    						titleTextStyle : {
		                                    							color : '#999',
		                                    							fontName : 'Open Sans, Arial, Helvetica, sans-serif',
		                                    							fontSize : 12,
		                                    							italic: false
		                                    						},
		                                    						textStyle : {
		                                    							color : '#999',
		                                    							fontName : 'Open Sans, Arial, Helvetica, sans-serif',
		                                    							fontSize : 12,
		                                    							italic: false
		                                    						},
		                                    						gridlines : {
		        														color : 'transparent'
		        													}
		                                    					},
		                                                 };

		                                                 var chart = new google.visualization.AreaChart(document.getElementById('errorPercChart'));
		                                                 chart.draw(data, options);
	}
	
});