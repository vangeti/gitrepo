mainApp.controller('activeThreadsOverTimeController', function($scope,
		$sessionStorage, $rootScope, $http, widgetService) {

	$scope.tdTitle = 'Active Threads Over time';

	$('.activeThreadsOverTimePanel').lobiPanel({
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

	$('.activeThreadsOverTimePanel').on('onPin.lobiPanel',
			function(ev, lobiPanel) {
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

	$('.activeThreadsOverTimePanel').on('onUnpin.lobiPanel',
			function(ev, lobiPanel) {
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

	$('.activeThreadsOverTimePanel').on(
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

	// START
	$('.activeThreadsOverTimePanel').on('onFullScreen.lobiPanel',
			function(ev, lobiPanel) {
				window.console.log("value full event called");
				$(".panel-body").css({
					"max-height" : "100%",
					"min-height" : "100%"
				});
				$("#activeTOT").css({
					"width" : "1400px",
					"height" : "600px"
				});
				drawValueChart();
			});
	$('.activeThreadsOverTimePanel').on('onSmallSize.lobiPanel',
			function(ev, lobiPanel) {
				window.console.log("value small event called");
				$(".panel-body").css({
					"max-height" : "300px",
					"min-height" : "300px"
				});
				$("#activeTOT").css({
					"width" : "600px",
					"height" : "300px"
				});
				drawValueChart();
			});

	$('.activeThreadsOverTimePanel .fa-refresh').on('click',
			function(ev, lobiPanel) {
				// console.log("onReload.lobiPanel");
				$('.perfJMErrorPer').hide();
				$('.jmErrorPerTitle').hide();
				$('#perfJMErrorPerLoadImg_id').show();
				drawValueChart();
			});

	/* $('#perfTestDetailsLoadImg_id').show(); */

	google.charts.setOnLoadCallback(drawValueChart);
	$('#activethreadsLoadImg_id').show();
	function drawValueChart() {
	    $http.get("lennox/rest/activeThreads/live?testId="+$sessionStorage.test_num).then(
//		$http.get("lennox/rest/activeThreads").then(
				function(response) {
					$('#activethreadsLoadImg_id').hide();
					/*
					 * $('#test_id').hide();
					 * 
					 * $('.perfTestDetailsTitle').show();
					 * $('.perfTestDetailsTable').show();
					 */

					var jsonResponse = response.data;
					
				//	console.log("activeThreads====jsonResponse",jsonResponse);

					var arrayObject = new Array();

					/*arrayObject.push([ 'Elapsed Time',
							'No.of Active Threads per sec' ]);*/
					for ( var key in jsonResponse) {
//						arrayObject.push([ new Date(key), jsonResponse[key]]);
						arrayObject.push([ key, jsonResponse[key]]);
					};
					
					

					var data = new google.visualization.DataTable();
					data.addColumn('string', 'Elapsed Time');
					data.addColumn('number', 'No.of Active Threads');

					data.addRows(arrayObject);
				      
					var grouped_data = google.visualization.data.group(data, [0],  [{ 'column': 1, 'aggregation':google.visualization.data.sum, 'type': 'number'}]);

					var options = {
						hAxis : {
							title : 'Elapsed Time (HH:MM:SS)',
							format: "HH:mm:ss",
							gridlines: {color: 'none'},						
							viewWindow: {
						        min: "00:00:00",
						        max: "00:05:00"
						    },
						  //  ticks: ["00:00:30", "00:01:00", "00:01:30", "00:02:00", "00:02:30","00:03:00","00:03:30","00:04:00","00:04:30","00:05:00"]
						},
						
					  
						vAxis : {
							title : 'Number of Active Threads',
							//gridlines: {color: 'none'}
								
						},
						legend: 'bottom'
//						title : 'Active Threads Over Time'
					};

					var chart = new google.visualization.LineChart(document.getElementById('activeTOT'));
					
					
					//------------------PDF-------------------------------
					google.visualization.events.addListener(chart, 'ready', function () {
						  var content = '<img src="' + chart.getImageURI() + '">';
						  $('#activeThreadsPdf').append(content);
						});
						
					//------------------PDF-------------------------------

//					chart.draw(data, options);
					chart.draw(grouped_data, options);
					

				});
	}
	//------------------PDF-------------------------------
	$scope.atgeneratePDF = function () {
		var doc = new jsPDF('p', 'pt', 'a4', false) /* Creates a new Document*/
		doc.setFontSize(15); /* Define font size for the document */
		var yAxis = 30;
		var imageTags = $('#activeThreadsPdf img');
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
		doc.save('ActiveThreadsReport' + '.pdf') /* Prompts user to save file on his/her machine */
	}
	//------------------PDF-------------------------------
});
