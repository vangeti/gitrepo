mainApp.controller('responseTimesController', function($scope, $sessionStorage,
		$rootScope, $http, widgetService) {

	$scope.tdTitle = 'Response Times';

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
				window.console.log("value full event called");
				$(".panel-body").css({
					"max-height" : "100%",
					"min-height" : "100%"
				});
				$("#rtGraph").css({
					"width" : "1500px",
					"height" : "700px"
				});
				drawValueChart();
			});
	$('.responseTimesPanel').on('onSmallSize.lobiPanel',
			function(ev, lobiPanel) {
				window.console.log("value small event called");
				$(".panel-body").css({
					"max-height" : "300px",
					"min-height" : "300px"
				});
				$("#rtGraph").css({
					"width" : "600px",
					"height" : "300px"
				});
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

	google.charts.setOnLoadCallback(drawValueChart);
	$('#testCaseYieldLoadImg_id').show();

	function drawValueChart() {

		$http.get("lennox/rest/responseTimes/live?testId="+$sessionStorage.test_num).then(
//		$http.get("lennox/rest/responseTimes").then(
				function(response) {
					/*
					 * $('#test_id').hide();
					 * 
					 * $('.perfTestDetailsTitle').show();
					 * $('.perfTestDetailsTable').show();
					 */
					$('#testCaseYieldLoadImg_id').hide();

					var jsonResponse = response.data;
					
					var transNames = jsonResponse.trnNames;
					
					var elapseTimes = jsonResponse.elapseTimes;
					
					var result = jsonResponse.resultMap;
					
					var data = new google.visualization.DataTable();

					
					data.addColumn('string', 'Elapsed Time');
					
					for ( var key in transNames) {
						data.addColumn('number', transNames[key]);
					}					
					
					var arrayObject = new Array();					
								
					//console.log("result map js  ========",result);
					
					for (var i=0; i < elapseTimes.length; i++){
						var tempArray = new Array();
						tempArray.push(elapseTimes[i]);						
						for ( var key in transNames) {
							//var results = result;							
							var trTime = getRespTime(result, elapseTimes[i], transNames[key]);
							tempArray.push(trTime);
						}
							arrayObject.push(tempArray);
					}
					
					//console.log("arrayObject ========",arrayObject);
					
					function getRespTime(results, eltime, transName) {
						var temp = null;
						var results1 = null;
						for ( var key in results) {
							if(key == eltime) {	
								results1 = results[key];
								break;
							}
						}						
						
							for ( var key1 in results1) {				
								if(results1[key1].transactionName == transName) {
										temp = results1[key1].responseTime;
										break;
								}
							}							
						
						return temp;
					}
				// static					
				/*	data.addColumn('number', 'Login');
					data.addColumn('number', 'RequestData_Mobile');
					data.addColumn('number', 'Update');
					data.addColumn('number', 'authenticate');
					data.addColumn('number', 'homes');
					data.addColumn('number', 'publish_LCC');
					data.addColumn('number', 'requestData_WeatherDetails_LCC');
					data.addColumn('number', 'retrieve?_LCC');
					data.addColumn('number', 'retrieve?_Mobile');
					data.addRows([
		              ["10:38",  1, 0, 1, 3, 2, 4, 1, 2, 1],
		              ["10:39",  2, 2, 2, 4, 3, 5, 2, 7, 3],
		              ["10:40",  6, 7, 3, 6, 6, 7, 3, 8, 7],
		              ["10:41",  8, 8, 7, 7, 9, 9, 8, 9, 9],

		              ]);*/
				// static	
				data.addRows(arrayObject);
					

					var options = {
							hAxis : {
								title : 'Elapsed Time(HH:MM:SS)'
							},
							vAxis : {
								title : 'Response times per ms'
							},
//							title : 'Response Times',
							legend: { position: 'top', alignment: 'end' }
						};

					var chart = new google.visualization.LineChart(document
							.getElementById('rtGraph'));
					
					//------------------PDF-------------------------------
					google.visualization.events.addListener(chart, 'ready', function () {
						  var content = '<img src="' + chart.getImageURI() + '">';
						  $('#responseTimesPdf').append(content);
						});
						
					//------------------PDF-------------------------------

					chart.draw(data, options);


				});
	}
	
	//------------------PDF-------------------------------
	$scope.rtgeneratePDF = function () {
		var doc = new jsPDF('p', 'pt', 'a4', false) /* Creates a new Document*/
		doc.setFontSize(15); /* Define font size for the document */
		var yAxis = 30;
		var imageTags = $('#responseTimesPdf img');
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
		doc.save('ResponseTimesReport' + '.pdf') /* Prompts user to save file on his/her machine */
	}
	//------------------PDF-------------------------------

});