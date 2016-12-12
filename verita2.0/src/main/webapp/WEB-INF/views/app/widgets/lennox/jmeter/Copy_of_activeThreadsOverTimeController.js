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
				$('#activethreadsLoadImg_id').show();
				$('#activeTOT').empty();
				window.console.log("value full event called");
				$(".panel-body").css({
					"max-height" : "100%",
					"min-height" : "100%"
				});
				/*$('#activethreadsLoadImg_id').hide();*/
				$("#activeTOT").css({
					"width" : "1400px",
					"height" : "600px"
				});
				drawValueChart();
			});
	$('.activeThreadsOverTimePanel').on('onSmallSize.lobiPanel',
			function(ev, lobiPanel) {
				$('#activethreadsLoadImg_id').show();
				$('#activeTOT').empty();
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

	// google.charts.setOnLoadCallback(drawValueChart);
	drawValueChart();
	$('#activethreadsLoadImg_id').show();
	function drawValueChart() {
		$http.get("lennox/rest/activeThreads/live/plotty?testId="+ $sessionStorage.test_num).then(
				function(response) {
					$('#activethreadsLoadImg_id').hide();
					var jsonResponse = response.data;
					$http.get("lennox/rest/userProfile/live?testId="+$sessionStorage.test_num).then(
							function(response) {
								var jsonRespUserProfileBean = response.data;
								var data = [];
								var item = {};
								var elapsedTime = [];
								var activeThreads = [];
								
								var jsonRespTestStartTime = jsonRespUserProfileBean.teststarttime;
								elapsedTime.push(jsonRespTestStartTime);
								activeThreads.push(0);

								for (var i = 0; i < jsonResponse.length; i++) {
									var v=jsonResponse[i];
									var x=v[0];
									var y=v[1];
									elapsedTime.push(new Date(x));
									activeThreads.push(y);
								}
								if(activeThreads[(jsonResponse.length)] !== 0)	{
									var jsonRespTestEndTime = jsonRespUserProfileBean.testendtime;
									elapsedTime.push(jsonRespTestEndTime);
									activeThreads.push(0);
								}
								item["x"] = elapsedTime;
								item["y"] = activeThreads;
								item["type"] = 'scatter';
								data.push(item);
								var layout = { showlegend: false, legend: {"orientation": "h", type: 'grid'}, legend: 'bottom'};
								Plotly.newPlot('activeTOT', data, layout, { displayModeBar : false });
						});				
				});
	}
	// ------------------PDF-------------------------------
	$scope.atgeneratePDF = function() {
		var doc = new jsPDF('p', 'pt', 'a4', false) /* Creates a new Document */
		doc.setFontSize(15); /* Define font size for the document */
		var yAxis = 30;
		var imageTags = $('#activeThreadsPdf img');
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
		doc.save('ActiveThreadsReport' + '.pdf') /*
													 * Prompts user to save file
													 * on his/her machine
													 */
	}
	// ------------------PDF-------------------------------
});
