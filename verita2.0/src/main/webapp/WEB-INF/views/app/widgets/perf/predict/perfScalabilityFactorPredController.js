mainApp.controller('perfScalabilityFactorPredController', function($scope, $sessionStorage,
		$rootScope, widgetService, $http) {

	$scope.psfPredTitle = 'Scalability Factor';
	
	$scope.perfScalePredChartFlag = true;
	$scope.perfScalePredTableFlag = false;

	$scope.leadView = function(type) {
		console.log(type);
		if (type === 'table') {
			$scope.perfScalePredChartFlag = false;
			$scope.perfScalePredTableFlag = true;
		} else {
			$scope.perfScalePredChartFlag = true;
			$scope.perfScalePredTableFlag = false;
		}
	}

	$('.psfPredPanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		unpin : false,
		reload : true,
		editTitle : {
			icon : 'fa fa-table leadTable',
			icon2 : 'fa fa-bar-chart leadChart',
			tooltip : 'View'
		},
		reload : {
			icon : 'fa fa-refresh'
		},
		minimize : false,
		/*minimize : {
			icon : 'fa fa-chevron-up',
			icon2 : 'fa fa-chevron-down'
		},*/
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

	/*$('.jiraPanel').on('beforeClose.lobiPanel', function(ev, lobiPanel) {
		$rootScope.jiraFlag = false;
		$rootScope.$digest();
	});*/
	
	$(".leadTable").on("click", function() {
		// alert($(this).attr("class"));
		if ($(this).attr("class").indexOf("leadTable") >= 0) {
			$scope.leadView('table');
		} else {
			$scope.leadView('chart');
		}
		$scope.$digest();
	});
	
	$('.psfPredPanel').on('beforeClose.lobiPanel', function(ev, lobiPanel) {
		var widgetResp = widgetService.deleteWidget(
				$sessionStorage.userId, "jiraFlag");

		widgetResp.then(function(resp) {
			console.log("delete response");
			console.log(resp);
			if (resp.data.Status === "Success") {
				$rootScope.jiraFlag = false;
				$rootScope.widgetList.push({
					"key":"jiraFlag",
					"value":$scope.psfPredTitle
				});
				$rootScope.jiraFlag = false;
			}
		}, function(errorPayload) {
			console.log(errorPayload);
		});
	});

	$('.psfPredPanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value full event called");
		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		$("#psfPredWidget").css({
			"width" : "100%",
			"height" : "600px"
		});
		drawValueChart();

	});
	$('.psfPredPanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value small event called");
		$(".panel-body").css({
			"max-height" : "200px",
			"min-height" : "200px"
		});
		$("#psfPredWidget").css({
			"width" : "400px",
			"height" : "300px"
		});
		drawValueChart();
	});
	
	$('.psfPredPanel .fa-refresh').on('click', function(ev, lobiPanel){
		 // console.log("onReload.lobiPanel");
		$('.perfScale').hide();
		$('.predScaleTitle').hide();
		$('#perfScalabilityPredLoadImg_id').show();
		drawValueChart();
		   });
	
		
	google.charts.setOnLoadCallback(drawValueChart);
	
	$('#perfScalabilityPredLoadImg_id').show();
	
	var color = '#000'
	function drawValueChart() {
		$http.get("devops/rest/scalabilityfactorpred").then(
				function(response) {
					
					$('#perfScalabilityPredLoadImg_id').hide();
					
					$('.predScaleTitle').show();
					$('.perfScale').show();
					
					var jsonresp = response.data;
					console.log("scale:"+jsonresp.memory_used);

					/*var keys = [];
					var values = [];
					for ( var key in jsonresp) {
						console.log(key + ' is ' + jsonresp[key]);
						keys.push(key);
						values.push(jsonresp[key]);
					}*/
					
					$scope.memory_used = jsonresp.memory_used;
					$scope.cpu_usage = jsonresp.cpu_usage;
					$scope.disk_reads = jsonresp.disk_reads;
					$scope.disk_writes = jsonresp.disk_writes;
					
					var data = google.visualization.arrayToDataTable([ [ 'Property Name', 'Property value' ],	                                                
					                                           		
					                                           		["Memory used", parseInt(jsonresp.memory_used)],
					                                           		["CPU Usage", parseInt(jsonresp.cpu_usage)],
					                                           		["Disk reads", parseInt(jsonresp.disk_reads)],
					                                           		["Disk Writes", parseInt(jsonresp.disk_writes)],
					                                           	  ]);

			/*var psfTitle=jsonresp.sprintstartdate+' TO '+jsonresp.sprintenddate;*/
			/*$scope.jiraTitle = jsonresp.sprintname;*/
					drawPieChart(data,'psfPredWidget');
					
				});
	}
	
	function drawPieChart(data,divContainer) {
		var options = {
			/*title:featureTitle,*/
		
			titleTextStyle : {
				color : '#888',
			},
			legend : {
				textStyle : {
					color : '#888'
				}
			},
			
	
			is3D : true,
			color : '#888'
		};

	
			var colors={colors : [ '#2BB12B', '#FF5722','#0000FF', '#8A2BE2' ]};
			$.extend( options, colors );
		
		
		var chart = new google.visualization.PieChart(document
				.getElementById(divContainer));
		chart.draw(data, options);
	}

});