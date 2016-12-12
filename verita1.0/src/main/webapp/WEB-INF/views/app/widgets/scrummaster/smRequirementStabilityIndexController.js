mainApp.controller('smRequirementStabilityIndexController', function($scope, $sessionStorage,
		$rootScope, $http, widgetService) {

	

	$('.smRSIPanel').lobiPanel({
		// Options go here
		sortable : false,
		 editTitle : false,
		unpin : {
			icon : 'fa fa-thumb-tack'
		},
		reload : true,
		editTitle : false,
		minimize : false,
		close : false,
		expand : false,
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

	$('.smRSIPanel').on('onPin.lobiPanel', function(ev, lobiPanel){
	    window.console.log("Pinned");
	    $(".panel-body").css({
			"max-height" : "300px",
			"min-height" : "300px"
		});
		$("#smRSIChart").css({
			"width" : "600px",
			"height" : "300px"
		});
		drawValueChart();
	});
	
	$('.smRSIPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel){
		lobiPanel.$options.expand = false;
	    window.console.log("Unpinned");
	    $(".smRSIPanel").css({
	    	"left" : "150.5px",
	    	"top" : "122px",
			"height" : "80%",
			"width" : "80%"
		});
	    $(".reqStabilityPanel-body").css({
	    	"width" : "100%",
			"height" : "100%",
			"max-height" : "85%",
			"min-height" : "85%"
		});
		$("#smRSIChart").css({
			"width" : "100%",
			"height" : "400px"
		});
		drawValueChart();
	});

	
	
	$('.smRSIPanel .fa-refresh').on('click', function(ev, lobiPanel){
		$('.smRSI').hide();
		$('.smRsiTitle').hide();
		
		$('#reqStabilityLoadImg_id').show();
		drawValueChart();
		   });

	google.charts.setOnLoadCallback(drawValueChart);
	
	$('#reqStabilityLoadImg_id').show();
	
	function drawValueChart() {						
	
		 $http.get("devops/rest/smrsi").then(
				function(response) {
					
					$('#reqStabilityLoadImg_id').hide();
					$('.smRSI').show();
					$('.smRsiTitle').show();
					
					var jsonresp = response.data;
					var requirementStabilityIndexMap = jsonresp.requirementStabilityIndexMap;
					
					var keys = [];
					var values = [];
					for ( var key in requirementStabilityIndexMap) {
						keys.push(key);
						values.push(requirementStabilityIndexMap[key]);
					}
					var data = google.visualization.arrayToDataTable([
							[ 'Element', '', {
								role : 'style'
							} ], [ keys[0], parseFloat(values[0]), '#ff884d' ],
							[ keys[1], parseFloat(values[1]), '#ffaa80' ],
							[ keys[2], parseFloat(values[2]), '#ffbb99' ],
							[ keys[3], parseFloat(values[3]), '#ffddcc' ] ]);
					
					$scope.sRSiTitle = jsonresp.meanTotal + '%';
					
					drawColumnChartfinal(data,
							'Application Name', 'Percentage', '100%',
							'100%', 'smRSIChart');

				});
		}
	
	function drawColumnChartfinal(data, hAxisTitle,
			vAxisTitle, height, width, divContainer) {
		var color = "#000000";
		var options = {
			/*title : garphTitle,*/
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

		var chart = new google.visualization.ColumnChart(
				document.getElementById(divContainer));

		chart.draw(data, options);
	}


});