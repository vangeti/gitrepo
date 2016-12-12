mainApp.controller('hitsPerSecondController', function($scope, $sessionStorage,
		$rootScope, $http, widgetService) {

	$scope.tdTitle = 'Hits Per Second';
	$scope.Math = window.Math;
	$('.hitsPerSecondPanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		/*unpin : false,*/
		reload : true,
		editTitle : false,
		minimize : false,
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
		close : {
			icon : 'fa fa-times-circle'
		},
		expand : {
			icon : 'fa fa-expand',
			icon2 : 'fa fa-compress'
		}
	});

	$('.hitsPerSecondPanel').on('onPin.lobiPanel', function(ev, lobiPanel) {
		window.console.log("Pinned");
		$(".tdPanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#avgrespTime").css({
			"width" : "370px",
			"height" : "200px"
		});
		drawValueChart();
	});

	$('.hitsPerSecondPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
		lobiPanel.$options.expand = false;
		window.console.log("Unpinned");
		$(".tdPanel").css({
			"left" : "150.5px",
			"top" : "122px",
			"height" : "80%",
			"width" : "80%"
		});
		$(".tdPanel-body").css({
			"width" : "100%",
			"height" : "100%",
			"max-height" : "85%",
			"min-height" : "85%"
		});
		$("#avgrespTime").css({
			"width" : "100%",
			"height" : "300px"
		});
		drawValueChart();
	});

	$('.hitsPerSecondPanel').on(
			'beforeClose.lobiPanel',
			function(ev, lobiPanel) {
				var widgetResp = widgetService.deleteWidget(
						$sessionStorage.userId, "tdPanelFlag");

				widgetResp.then(function(resp) {

					if (resp.data.Status === "Success") {
						$rootScope.tdPanelFlag = false;
						$rootScope.widgetList.push({
							"key" : "tdPanelFlag",
							"value" : $scope.tdTitle
						});
					}
				}, function(errorPayload) {

				});
			});

	$('.hitsPerSecondPanel').on('onFullScreen.lobiPanel',
			function(ev, lobiPanel) {
				$('#hpsLoadImg_id').show();
				$('#hitsPS').empty();
				window.console.log("value full event called");
				$(".panel-body").css({
					"max-height" : "100%",
					"min-height" : "100%"
				});
				$("#hitsPS").css({
					"width" : "1400px",
					"height" : "600px"
				});
				drawValueChart();
			});
	$('.hitsPerSecondPanel').on('onSmallSize.lobiPanel',
			function(ev, lobiPanel) {
				$('#hpsLoadImg_id').show();
				$('#hitsPS').empty();
				window.console.log("value small event called");
				$(".panel-body").css({
					"max-height" : "300px",
					"min-height" : "300px"
				});
				$("#hitsPS").css({
					"width" : "600px",
					"height" : "300px"
				});
				drawValueChart();
			});
	/*google.charts.setOnLoadCallback(drawValueChartHitsPerSec);

	function drawValueChartHitsPerSec() {
		var data = google.visualization.arrayToDataTable([
				[ 'Elapsed Time(guanularity 1sec)', 'Number of hits / sec' ],
				[ '00:00:00', 0 ], [ '00:15:04', 120 ], [ '00:30:09', 200 ],
				[ '00:45:14', 280 ], [ '01:00:19', 280 ], [ '01:15:24', 280 ],
				[ '01:30:29', 280 ], [ '01:45:34', 280 ], [ '02:00:39', 200 ],
				[ '02:15:44', 120 ], [ '02:30:49', 0 ] ]);

		var options = {
			hAxis : {
				title : 'Elapsed Time'
			},
			vAxis : {
				title : 'Number of Hits per sec'
			},
			title : 'Hits per second'
		};

		var chart = new google.visualization.LineChart(document
				.getElementById('hitsPS'));

		chart.draw(data, options);
	}
	;*/

	//google.charts.setOnLoadCallback(drawValueChart);
	drawValueChart();
	$('#hpsLoadImg_id').show();
	function drawValueChart() {
		$http.get("lennox/rest/hitsPerSec/live?testId="+$sessionStorage.test_num).then(
//		$http.get("lennox/rest/hitsPerSec").then(
				function(response) {
					$('#hpsLoadImg_id').hide();
					var jsonResponse = response.data;					
					var data=[];
						var item = {};
						var elapsedTime=[];
						var responseTime=[];
						for(var i=0; i<jsonResponse.length; i++)	{
							var v=jsonResponse[i];
							var x=v[0];
							var y=v[1];
							elapsedTime.push(new Date(x));
							responseTime.push(y);
						}
												
						item["x"]=elapsedTime;
						item["y"]=responseTime;
						item["type"]='scatter';
						data.push(item);
						
						var layout = { showlegend: false, legend: {"orientation": "h", type: 'grid'}, legend: 'bottom',
						};
					Plotly.newPlot('hitsPS', data, layout,{displayModeBar:false});
				});
	}
	
	//------------------PDF-------------------------------
	$scope.htgeneratePDF = function () {
		var doc = new jsPDF('p', 'pt', 'a4', false) /* Creates a new Document*/
		doc.setFontSize(15); /* Define font size for the document */
		var yAxis = 30;
		var imageTags = $('#hitsPerSecondPdf img');
		for (var i = 0; i < imageTags.length; i++) {
			if (i % 2 == 0 && i != 0) { /* I want only two images in a page */
				doc.addPage();  /* Adds a new page*/
				yAxis = 30; /* Re-initializes the value of yAxis for newly added page*/
			}
			var someText = 'Chart '+(i+1);
			doc.text(60, yAxis, someText); /* Add some text in the PDF */
			yAxis = yAxis + 20; /* Update yAxis */
			// doc.addImage(imageTags[i], 'png', 40, yAxis, 530, 350, undefined, 'none');
			//  yAxis = yAxis+ 360; /* Update yAxis */
			try {
				doc.addImage(imageTags[i], 'png', 40, yAxis, 530, 350, undefined, 'none');
				yAxis = yAxis+ 360; /* Update yAxis */
			}
			catch (e) {
				doc.text(120, yAxis + 30, 'SOME ERROR OCCURRED WHILE ADDING IMAGE');
				yAxis = yAxis + 50 /* Update yAxis */
			}

		}
		doc.save('HitsPerSecondReport' + '.pdf') /* Prompts user to save file on his/her machine */
	}
	//------------------PDF-------------------------------
	
});