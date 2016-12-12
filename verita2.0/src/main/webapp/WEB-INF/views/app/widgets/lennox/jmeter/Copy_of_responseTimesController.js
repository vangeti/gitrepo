mainApp.controller('responseTimesController', function($scope, $sessionStorage,
		$rootScope, $http, widgetService) {

	$scope.tdTitle = 'Response Times';
	var showLegendFlag = false;

	$('.responseTimesPanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		/* unpin : false, */
		reload : true,
		editTitle : false,
		minimize : false,
		unpin : {
			icon : 'fa fa-thumb-tack'
		},
		/*
		 * minimize : { icon : 'fa fa-chevron-up', icon2 : 'fa fa-chevron-down' },
		 */
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

	$('.responseTimesPanel').on('onPin.lobiPanel', function(ev, lobiPanel) {
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

	$('.responseTimesPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
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

	$('.responseTimesPanel').on(
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

	/* $('#perfTestDetailsLoadImg_id').show(); */

	/*
	 * $http.get("devops/rest/tes") .then(function(response) {
	 * 
	 * $('#test_id').hide();
	 * 
	 * $('.perfTestDetailsTitle').show(); $('.perfTestDetailsTable').show();
	 * 
	 * var jsonresp = response.data;
	 * 
	 * $scope.projectName = jsonresp.Header[0].projectName; $scope.date =
	 * jsonresp.Header[0].date; $scope.duration = jsonresp.Header[0].duration;
	 * $scope.users = jsonresp.Header[0].users; $scope.averageHits =
	 * jsonresp.Average[0].hits; $scope.averagePages =
	 * jsonresp.Average[0].pages; $scope.averageResponse =
	 * jsonresp.Average[0].response; $scope.averageThroughput =
	 * jsonresp.Average[0].throughput; $scope.totalHits =
	 * jsonresp.Total[0].hits; $scope.totalPages = jsonresp.Total[0].pages;
	 * $scope.totalThroughput = jsonresp.Total[0].throughput;
	 * $scope.percentileError = jsonresp.Total[0].percentileError;
	 * 
	 * });
	 */

	$('.responseTimesPanel').on('onFullScreen.lobiPanel',
			function(ev, lobiPanel) {
				$('#testCaseYieldLoadImg_id').show();
				$('#rtGraph').empty();
				window.console.log("value full event called");
				$(".panel-body").css({
					"max-height" : "700px",
					"min-height" : "700px"
				});
				$("#rtGraph").css({
					"width" : "100%",
					"max-height" : "700px",
					"min-height" : "700px",
					"overflow-x" : "auto", 
					"overflow-y" : "hidden"
				});
				
				showLegendFlag = true;
				console.log('onFullScreen: ' + showLegendFlag);
				drawValueChart();
			});
	$('.responseTimesPanel').on('onSmallSize.lobiPanel',
			function(ev, lobiPanel) {
				$('#testCaseYieldLoadImg_id').show();
				$('#rtGraph').empty();
				window.console.log("value small event called");
				$(".panel-body").css({
					"max-height" : "330px",
					"min-height" : "330px"
				});
				$("#rtGraph").css({
					"width" : "100%",
					"height" : "330px",//330
					"max-height" : "330px",
					"min-height" : "330px",
					"overflow-x" : "hidden", 
					"overflow-y" : "hidden"
				});
				//layout.showlegend = false;
				showLegendFlag = false;
				console.log('onSmallSize: ' + showLegendFlag);
				drawValueChart();
			});

	/*
	 * google.charts.setOnLoadCallback(drawValueChart); function
	 * drawValueChart() { var data = google.visualization.arrayToDataTable([
	 * ['Elapsed Time', 'Response times in ms'], ['00:00:00', 0 ], ['00:15:04',
	 * 200 ], ['00:30:09', 500 ], ['00:45:14', 800], ['01:00:19', 1000 ],
	 * ['01:15:24', 1100 ], ['01:30:29', 1500 ], ['01:45:34', 1500 ],
	 * ['02:00:39', 1500 ], ['02:15:44', 1500 ], ['02:30:49', 0 ] ]);
	 * 
	 * var options = { hAxis: { title: 'Elapsed Time' }, vAxis: {title:
	 * 'Response times in ms' }, title: 'Response Times' };
	 * 
	 * var chart = new
	 * google.visualization.LineChart(document.getElementById('rtGraph'));
	 * 
	 * chart.draw(data, options); }
	 */

	// google.charts.setOnLoadCallback(drawValueChart);
	drawValueChart();
	$('#testCaseYieldLoadImg_id').show();

	function drawValueChart() {

		$http.get(
				"lennox/rest/responseTimes/live/ploty?testId="
						+ $sessionStorage.test_num).then(
		// $http.get("lennox/rest/responseTimes").then(
		function(response) {
			/*
			 * $('#test_id').hide();
			 * 
			 * $('.perfTestDetailsTitle').show();
			 * $('.perfTestDetailsTable').show();
			 */
			$('#testCaseYieldLoadImg_id').hide();

			var jsonResponse = response.data;

			// var transNames = jsonResponse.trnNames;
			// var elapseTimes = jsonResponse.elapseTimes;
			// var result = jsonResponse.resultMap;
			// var data = new google.visualization.DataTable();
			console.log("jsonResponse for response times,", jsonResponse);
			var data = [];

			for ( var key in jsonResponse) {
				var allcount = jsonResponse[key];
				var item = {};
				var elapsedTime = [];
				var responseTime = [];
				// elapsedTime.push(arrayObject[i]);
				for (var j = 0; j < allcount.length; j++) {
					var v = allcount[j];
					var x = v[0];
					var y = v[1];
					// console.log("date"+x);
					elapsedTime.push(new Date(x));
					responseTime.push(y / 1000);
				}

				item["x"] = elapsedTime;
				item["y"] = responseTime;
				item["type"] = 'scatter';
				item["name"] = key;
				// item["showlegend"]=true;
				// item["legend"]={"orientation": "h"};
				data.push(item);

			}
			/*
			 * layout.legend = { type: 'grid', grid: [ [ 'trace 0', 'trace 1',
			 * 'trace 2'], ['trace 4', 'trace 3'] ] };
			 */
			var legends = [];
			var temp = [];
			var len = jsonResponse.length;
			var count = 0;
			if (len / 2 == 0) {
				count = 4;
			} else {
				count = 3;
			}
			for ( var key in jsonResponse) {

				if (temp.length == count) {
					temp.push[key];
					legends.push[temp];
					temp = [];

				}

			}
			/*
			 * layout.legend = { type: 'grid', grid: [ [ 'trace 0', 'trace 1',
			 * 'trace 2'], ['trace 4', 'trace 3'] ] };
			 */

			console.log('showLegendFlag: ' + showLegendFlag);
			
			layout = {
				displayModeBar : false,
				//showlegend : showLegendFlag,
				legend : {
						/*"y" : -0.5,
						"x" : 0.1,*/
						"orientation" : "v",
						type : 'grid'
					},
				};
			
			layout.showlegend = showLegendFlag;
			
			console.log("Plotty log ,", data);
			Plotly.newPlot('rtGraph', data, layout, {/* displaylogo: false, */
				displayModeBar : false
			});

			/*var options = {
				legend : {
					position : 'bottom'
				}
			};*/

		});
	}

	// ------------------PDF-------------------------------
	$scope.rtgeneratePDF = function() {
		var doc = new jsPDF('p', 'pt', 'a4', false) /* Creates a new Document */
		doc.setFontSize(15); /* Define font size for the document */
		var yAxis = 30;
		var imageTags = $('#responseTimesPdf img');
		for (var i = 0; i < imageTags.length; i++) {
			if (i % 2 == 0 && i != 0) { /* I want only two images in a page */
				doc.addPage(); /* Adds a new page */
				yAxis = 30; /*
							 * Re-initializes the value of yAxis for newly added
							 * page
							 */
			}
			var someText = 'Chart ' + (i + 1);
			doc.text(60, yAxis, someText); /* Add some text in the PDF */
			yAxis = yAxis + 20; /* Update yAxis */
			// doc.addImage(imageTags[i], 'png', 40, yAxis, 530, 350, undefined,
			// 'none');
			// yAxis = yAxis+ 360; /* Update yAxis */
			try {
				doc.addImage(imageTags[i], 'png', 40, yAxis, 530, 350,
						undefined, 'none');
				yAxis = yAxis + 360; /* Update yAxis */
			} catch (e) {
				doc.text(120, yAxis + 30,
						'SOME ERROR OCCURRED WHILE ADDING IMAGE');
				yAxis = yAxis + 50 /* Update yAxis */
			}

		}
		doc.save('ResponseTimesReport' + '.pdf') /*
													 * Prompts user to save file
													 * on his/her machine
													 */
	}
	// ------------------PDF-------------------------------

});