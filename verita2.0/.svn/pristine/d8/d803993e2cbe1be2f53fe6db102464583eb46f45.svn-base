mainApp.controller('responseCodesControllerCompare', function($scope, $sessionStorage, $rootScope, $http, widgetService) {

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
		// drawValueChart();
		/*
		 * var testwith = $('#test_with').find(":selected").text(); var testto =
		 * $('#test_to').find(":selected").text(); drawValueChart(testto,
		 * testwith);
		 */
		drawValueChart($sessionStorage.test_to_number, $sessionStorage.testwith_number);
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
		// drawValueChart();
		/*
		 * var testwith = $('#test_with').find(":selected").text(); var testto =
		 * $('#test_to').find(":selected").text();
		 */
		drawValueChart($sessionStorage.test_to_number, $sessionStorage.testwith_number);
	});

	$('.responseCodesPanel').on('beforeClose.lobiPanel',
			function(ev, lobiPanel) {
				var widgetResp = widgetService.deleteWidget($sessionStorage.userId, "tdPanelFlag");
				widgetResp.then(function(resp) {
					if (resp.data.Status === "Success") {
						$rootScope.tdPanelFlag = false;
						$rootScope.widgetList.push({"key" : "tdPanelFlag", "value" : $scope.tdTitle});
					}
				}, function(errorPayload) {
				});
			});

	$('.responseCodesPanel').on('onFullScreen.lobiPanel',
			function(ev, lobiPanel) {
				$('#respnsecodeLoadImg_id').show();
				window.console.log("value full event called");
				$(".panel-body").css({
					"max-height" : "100%",
					"min-height" : "100%"
				});
				$("#responseCodesGraphCompare").css({
					"width" : "1400px",
					"height" : "600px"
				});
				drawValueChart($sessionStorage.test_to_number, $sessionStorage.testwith_number);
			});
	$('.responseCodesPanel').on('onSmallSize.lobiPanel',
			function(ev, lobiPanel) {
				$('#respnsecodeLoadImg_id').show();
				window.console.log("value small event called");
				$(".panel-body").css({
					"max-height" : "300px",
					"min-height" : "300px"
				});
				$("#responseCodesGraphCompare").css({
					"width" : "600px",
					"height" : "280px"
				});
				drawValueChart($sessionStorage.test_to_number, $sessionStorage.testwith_number);
			});

	$("#compare").click(
		function() {
			drawValueChart($sessionStorage.test_to_number, $sessionStorage.testwith_number);
		}
	);
	$('#respnsecodeLoadImg_id').show();
	function drawValueChart(testto,testwith) {
		$http.get("lennox/rest/responseCodes/live?testId="+testwith).then(
				function(response) {
					var jsonResponse1 = response.data;
					var jsonResponse2;
					$http.get("lennox/rest/responseCodes/live?testId="+testto).then(
							function(response) {
								jsonResponse2 = response.data;
								$('#respnsecodeLoadImg_id').hide();
								
								var x=new Date();
								
								var arrayObject = new Array();
								if ($sessionStorage.test_num == 1 || $sessionStorage.test_num == 2) {
									for ( var key in jsonResponse1) {
										arrayObject.push([ jsonResponse1[key].elapsedTime, jsonResponse1[key].responseCode ]);
									};
								} else {
									// get all the response codes & store them in an array
									var allResponseCodes = [];
									for ( var key in jsonResponse1) {
										var jsonResp1= jsonResponse1[key];
										for ( var key in jsonResp1) {
											if(allResponseCodes.indexOf(key) < 0){
												allResponseCodes.push(key);						
											}
										}
									}
									// iterate these resp codes... to get graph wrt this resp code...
									var data = [];
									 var count=0;
									
									for (var i in allResponseCodes) {
										 var tempRespCode = allResponseCodes[i];
										 var elapsedTimes = [];
										 var responseCodes = [];
										 var codeCounts = [];
										 for ( var key in jsonResponse1) {
											 var tempElapsedTime = key;
											 if(count==0){ 
												 var n = Number(tempElapsedTime);
											  	 var tempp=new Date(n);
												 x=new Date(tempp);
												 date1=new Date(x); 
												 x.setHours(00);
												 x.setMinutes(00);
												 x.setSeconds(00, 00);
											} else	{
												var difference=new Date(tempElapsedTime)-date1;
												x.setMilliseconds(difference);
												date1.setMilliseconds(difference);
											}
											 
											 var jsonResp1 = jsonResponse1[key];
											 for ( var key in jsonResp1) {
												// if the object contains this resp code, get that count
												if(key == tempRespCode)	{
													// add time, code & count to that respective arrays
													elapsedTimes.push(new Date(x));
													responseCodes.push(key);
													codeCounts.push(jsonResp1[key]);
												}
											 }
											 count=count+1;
										 }
										 tempRespCode = tempRespCode+"__"+testwith;
//										 console.log("jsonResponse1",jsonResponse1);
										  data.push({ x: elapsedTimes, y: codeCounts, text: responseCodes, type: 'scatter',name: tempRespCode, hoverinfo: 'text+x+y',
//										 data.push({ x: elapsedTimes, y: codeCounts, text: responseCodes, type: 'scatter',name: testwith, hoverinfo: 'text+x+y',
											 			mode: 'lines'
										 			});
									}
									
									var allResponseCodes = [];
									for ( var key in jsonResponse2) {
										var jsonResp2 = jsonResponse2[key];
										for ( var key in jsonResp2) {
											if(allResponseCodes.indexOf(key) < 0){
												allResponseCodes.push(key);						
											}
										}
									}
									// iterate these resp codes... to get graph wrt this resp code...
									for (var i in allResponseCodes) {
										 var tempRespCode = allResponseCodes[i];
										 var elapsedTimes = [];
										 var responseCodes = [];
										 var codeCounts = [];
										 // iterate the jsonResponse2
										 var count1=0;
										 for ( var key in jsonResponse2) {
											 var tempElapsedTime = key;
											 if(count1==0){ 
												 
												 	 var n = Number(tempElapsedTime)
												  	 var tempp=new Date(n);

														x=new Date(tempp);
														date1=new Date(x); 
														x.setHours(00);
														x.setMinutes(00);
														x.setSeconds(00, 00);
														
														}
														else
															{
															var difference=new Date(tempElapsedTime)-date1;
															x.setMilliseconds(difference);
															date1.setMilliseconds(difference);
															}
											 var jsonResp2 = jsonResponse2[key];
											 for ( var key in jsonResp2) {
												// if the object contains this resp code, get that count
												if(key == tempRespCode)	{
													// add time, code & count to that respective arrays
													elapsedTimes.push(new Date(x));
													responseCodes.push(key);
													codeCounts.push(jsonResp2[key]);
												}
											 }
											 
											 count1=count1+1;
										 }
										 tempRespCode = tempRespCode+"__"+testto;
										 data.push({ x: elapsedTimes, y: codeCounts, text: responseCodes, type: 'scatter',name: tempRespCode, hoverinfo: 'text+x+y',
								 			mode: 'lines'
							 				});
									 }
						
									var layout = { 
											displayModeBar: false,
											showlegend: true,
											legend: {"y": -0.5, "x": 0.1,"orientation": "h", type: 'grid'}
									     };
									
									Plotly.newPlot('responseCodesGraphCompare', data, layout,{displayModeBar:false});
								}
							});
						});
				}
	
	// ------------------PDF-------------------------------
	$scope.rccgeneratePDF = function () {
		var doc = new jsPDF('p', 'pt', 'a4', false) /* Creates a new Document */
		doc.setFontSize(15); /* Define font size for the document */
		var yAxis = 30;
		var imageTags = $('#responseCodesComparePdf img');
		for (var i = 0; i < imageTags.length; i++) {
			if (i % 2 == 0 && i != 0) { /* I want only two images in a page */
				doc.addPage();  /* Adds a new page */
				yAxis = 30; /*
							 * Re-initializes the value of yAxis for newly added
							 * page
							 */
			}
			var someText = 'Chart '+(i+1);
			doc.text(60, yAxis, someText); /* Add some text in the PDF */
			yAxis = yAxis + 20; /* Update yAxis */
			// doc.addImage(imageTags[i], 'png', 40, yAxis, 530, 350, undefined,
			// 'none');
			// yAxis = yAxis+ 360; /* Update yAxis */
			try {
				doc.addImage(imageTags[i], 'png', 40, yAxis, 530, 350, undefined, 'none');
				yAxis = yAxis+ 360; /* Update yAxis */
			}
			catch (e) {
				doc.text(120, yAxis + 30, 'SOME ERROR OCCURRED WHILE ADDING IMAGE');
				yAxis = yAxis + 50 /* Update yAxis */
			}

		}
		doc.save('ResponseCodesCompareReport' + '.pdf') /*
														 * Prompts user to save
														 * file on his/her
														 * machine
														 */
	}
	// ------------------PDF-------------------------------

});