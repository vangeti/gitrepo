mainApp.controller('responseCodesController', function($scope, $sessionStorage,
		$rootScope, $http, widgetService) {

	$scope.tdTitle = 'Response Codes';

	$('.responseCodesPanel').lobiPanel({
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

	$('.responseCodesPanel').on('onPin.lobiPanel', function(ev, lobiPanel) {
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

	$('.responseCodesPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
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

	$('.responseCodesPanel').on(
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

	$('.responseCodesPanel').on('onFullScreen.lobiPanel',
			function(ev, lobiPanel) {
				$('#respnsecodeLoadImg_id').show();
				$('#responseCodesGraph').empty();
				window.console.log("value full event called");
				$(".panel-body").css({
					"max-height" : "100%",
					"min-height" : "100%"
				});
				$("#responseCodesGraph").css({
					"width" : "100%",
					"height" : "600px"
				});
				drawValueChart();
			});
	$('.responseCodesPanel').on('onSmallSize.lobiPanel',
			function(ev, lobiPanel) {
				$('#respnsecodeLoadImg_id').show();
				$('#responseCodesGraph').empty();
				window.console.log("value small event called");
				$(".panel-body").css({
					"max-height" : "300px",
					"min-height" : "300px"
				});
				$("#responseCodesGraph").css({
					"width" : "100%",
					"height" : "300px"
				});
				drawValueChart();
			});

	/* $('#perfTestDetailsLoadImg_id').show(); */

	//google.charts.setOnLoadCallback(drawValueChart);
	$('#respnsecodeLoadImg_id').show();
	drawValueChart();
	function drawValueChart() {
	$http.get("lennox/rest/responseCodes/live?testId="+$sessionStorage.test_num).then(
//		$http.get("lennox/rest/responseCodes").then(
				function(response) {
					$('#respnsecodeLoadImg_id').hide();
					var jsonResponse = response.data;
					var arrayObject = new Array();
					if ($sessionStorage.test_num == 1 || $sessionStorage.test_num == 2) {
						for ( var key in jsonResponse) {
							arrayObject.push([ jsonResponse[key].elapsedTime, jsonResponse[key].responseCode ]);
						};
					} else {
						//real code starts from here....
						//get all the response codes & store them in an array
						var allResponseCodes = [];
						for ( var key in jsonResponse) {
							var jsonResponse1 = jsonResponse[key];
							for ( var key in jsonResponse1) {
								if(allResponseCodes.indexOf(key) < 0){
									allResponseCodes.push(key);						
								}
							}
						}
						//iterate these resp codes... to get graph wrt this resp code...
						var data = [];
						for (var i in allResponseCodes) {
							 var tempRespCode = allResponseCodes[i];
							 var elapsedTimes = [];
							 var responseCodes = [];
							 var codeCounts = [];
							//iterate the jsonResponse
							 for ( var key in jsonResponse) {
								 var tempElapsedTime = key;
								 var jsonResponse1 = jsonResponse[key];
								 for ( var key in jsonResponse1) {
									//if the object contains this resp code, get that count
									if(key == tempRespCode)	{
										//add time, code & count to that respective arrays 
										
										elapsedTimes.push(new Date(tempElapsedTime));
										responseCodes.push(key);
										codeCounts.push(jsonResponse1[key]);
									}
								 }
							 }
							 console.log("jsonResponse",jsonResponse);
							 data.push({ x: elapsedTimes, y: codeCounts, text: responseCodes, type: 'scatter',name: tempRespCode, hoverinfo: 'text+x+y',
								 			mode: 'lines'
							 			});
						}
						var layout = { 
								displayModeBar: false,
								showlegend: true,
								legend: {"y": -0.5, "x": 0.1,"orientation": "h", type: 'grid'}
						     };
						
						Plotly.newPlot('responseCodesGraph', data, layout,{displayModeBar:false});
					}
				});
	}
	
	
	//------------------PDF-------------------------------
	$scope.rcgeneratePDF = function () {
		var doc = new jsPDF('p', 'pt', 'a4', false) /* Creates a new Document*/
		doc.setFontSize(15); /* Define font size for the document */
		var yAxis = 30;
		var imageTags = $('#responseCodesPdf img');
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
		doc.save('ResponseCodesReport' + '.pdf') /* Prompts user to save file on his/her machine */
	}
	//------------------PDF-------------------------------

});