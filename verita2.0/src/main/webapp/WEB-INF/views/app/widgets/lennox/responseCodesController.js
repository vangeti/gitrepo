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
				window.console.log("value full event called");
				$(".panel-body").css({
					"max-height" : "100%",
					"min-height" : "100%"
				});
				$("#responseCodesGraph").css({
					"width" : "1500px",
					"height" : "700px"
				});
				drawValueChart();
			});
	$('.responseCodesPanel').on('onSmallSize.lobiPanel',
			function(ev, lobiPanel) {
				window.console.log("value small event called");
				$(".panel-body").css({
					"max-height" : "300px",
					"min-height" : "300px"
				});
				$("#responseCodesGraph").css({
					"width" : "600px",
					"height" : "220px"
				});
				drawValueChart();
			});

	/* $('#perfTestDetailsLoadImg_id').show(); */

	google.charts.setOnLoadCallback(drawValueChart);
	$('#respnsecodeLoadImg_id').show();
	
	function drawValueChart() {
	$http.get("lennox/rest/responseCodes/live?testId="+$sessionStorage.test_num).then(
//		$http.get("lennox/rest/responseCodes").then(
				function(response) {
					$('#respnsecodeLoadImg_id').hide();
					var jsonResponse = response.data;
					var arrayObject = new Array();

					/*arrayObject.push([ 'Elapsed Time', 'No.of responses per sec' ]);*/
					if ($sessionStorage.test_num == 1 || $sessionStorage.test_num == 2) {
						for ( var key in jsonResponse) {
							arrayObject.push([ jsonResponse[key].elapsedTime, jsonResponse[key].responseCode ]);
						};
					} else {
						//get the response codes and add in a list...
						var allRespCodes = [];
						for ( var key in jsonResponse) {
							var jsonRespCodes = jsonResponse[key].respCodesList; //returns an array...
							//iterate the jsonRespCodes[Array] & add the resp codes
							for (var i = 0; i < jsonRespCodes.length; i++) {
								var isRespCodeExists = false;
							 //   console.log(jsonRespCodes[i]);
							    for (var j = 0; j < allRespCodes.length; j++) {
							    	if(allRespCodes[j] == jsonRespCodes[i]){
							    		isRespCodeExists = true;
							    		break;
							    	}
							    }
							    if(isRespCodeExists == false)	{
							    	allRespCodes.push(jsonRespCodes[i]);							    	
							    }
							}
						}
						//console.log("************ values of*****",allRespCodes);
						for ( var key in jsonResponse) {
							var temp=new Array();
							//console.log("222222",jsonResponse[key].respCodeCounts);
							temp[0]=jsonResponse[key].elapsedTime;
							//console.log("response code",jsonResponse[key].respCodeCounts);
							
							var codes=jsonResponse[key].respCodeCounts;
							//console.log("jsonResponse[key].respCodeCounts:",codes.length);
							for(var i=0; i<allRespCodes.length; i++) {
								temp[i+1]=codes[i];
								console.log("in loop",codes[i]);
								//temp.push[jsonResponse[key].elapsedTime,jsonResponse[key].respCodeCounts];
							//	temp[i]=jsonResponse[key].respCodeCounts;
							}
							//console.log("temp array:",temp);
							arrayObject.push(temp);
						//	arrayObject.push([ jsonResponse[key].elapsedTime, jsonResponse[key].responseCode ]);
						};
						console.log("arrayObject",arrayObject);
					}
					var data = new google.visualization.DataTable();
					data.addColumn('string', 'Elapsed Time');
					for(var i=0; i<allRespCodes.length; i++) {
						data.addColumn('number', allRespCodes[i]);
						//console.log("********* in loop");
					}
				//	console.log("arrayObject:",arrayObject);
				//	console.log("jsonResponse:",jsonResponse);
					//data.addColumn('number', 'No.of response codes per sec');
					data.addRows(arrayObject);

					var options = {
						hAxis : {
							title : 'Elapsed Time',
//							format: "HH:mm:ss",
							gridlines: {color: 'none'}
						},
						vAxis : {
							title : 'No.of responses per sec'
						},
						title : 'Response Codes'
					};

					var chart = new google.visualization.LineChart(document.getElementById('responseCodesGraph'));

					//------------------PDF-------------------------------
					google.visualization.events.addListener(chart, 'ready', function () {
						  var content = '<img src="' + chart.getImageURI() + '">';
						  $('#responseCodesPdf').append(content);
						});
						
					//------------------PDF-------------------------------
					
					
					chart.draw(data, options);

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