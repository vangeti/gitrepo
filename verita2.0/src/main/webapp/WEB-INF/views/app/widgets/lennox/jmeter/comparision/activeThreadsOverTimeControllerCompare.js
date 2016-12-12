mainApp.controller('activeThreadsOverTimeControllerCompare', function($scope,
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
				drawValueChart($sessionStorage.test_to_number, $sessionStorage.testwith_number);
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
				$("#activeTOTCompare").css({
					"width" : "1500px",
					"height" : "700px"
				});
				drawValueChart($sessionStorage.test_to_number, $sessionStorage.testwith_number);
			});
	$('.activeThreadsOverTimePanel').on('onSmallSize.lobiPanel',
			function(ev, lobiPanel) {
				window.console.log("value small event called");
				$(".panel-body").css({
					"max-height" : "300px",
					"min-height" : "300px"
				});
				$("#activeTOTCompare").css({
					"width" : "600px",
					"height" : "300px"
				});
				drawValueChart($sessionStorage.test_to_number, $sessionStorage.testwith_number);
			});

	$('.activeThreadsOverTimePanel .fa-refresh').on('click',
			function(ev, lobiPanel) {
				// console.log("onReload.lobiPanel");
				$('.perfJMErrorPer').hide();
				$('.jmErrorPerTitle').hide();
				$('#perfJMErrorPerLoadImg_id').show();
				drawValueChart($sessionStorage.test_to_number, $sessionStorage.testwith_number);
			});

	/* $('#perfTestDetailsLoadImg_id').show(); */

//	google.charts.setOnLoadCallback(drawValueChart);

	$("#compare").click(function() {

		// alert( "Handler for .click() called."+$scope.test_to );
		/*var testwith = $('#test_with').find(":selected").text();
		var testto = $('#test_to').find(":selected").text();*/
		//alert("test to:" + testto + "  test with:" + testwith);

		//drawValueChart(testto, testwith);
		drawValueChart($sessionStorage.test_to_number, $sessionStorage.testwith_number);
	});
	
	
	function drawValueChart(testto, testwith) {
		var jsonresp1=new Array();
		var jsonresp2;
//		$http.get("lennox/rest/activeThreads").then(
		$http.get("lennox/rest/activeThreads/live?testId=" +testwith).then(
				function(response) {
					$('#test_id').hide();
					$('.perfTestDetailsTitle').show();
					$('.perfTestDetailsTable').show();
					
					jsonresp1 = response.data;
					
					$http.get("lennox/rest/activeThreads/live?testId=" +testto).then(
							function(response) {
								jsonresp2=response.data;
								
								var data1 = new google.visualization.DataTable();
								data1.addColumn('string', 'Elapsed Time');
								data1.addColumn('number','No.of Active Threads test1');
								//data1.addColumn('number','No.of Active Threads test2');
								
								var keys1=[];
								for ( var key in jsonresp1) {
									keys1.push([ key ]);
								};
								var keys2=[];
								for( var key in jsonresp2) {
									keys2.push([ key ]);
								};
								var arrayObject2 = new Array();
								var counter=0;
								/*for(var i=0;i<keys.length;i++) {
									if (jsonresp1.hasOwnProperty(keys[i]) && jsonresp2.hasOwnProperty(keys[i])) {
										if (keys[i] === "00:00:00" && counter==0) {
										//	console.log("in line number 191 :"+jsonresp1[keys[i]] +"  ddvdv"+jsonresp2[keys[i]]  );
											arrayObject2.push([ keys[i].toString(),0,0 ]);
											counter=1;
										} else {
											if (jsonresp2[keys[i]] === "00:00:00" && jsonresp1[keys[i]] === "00:00:00") {
											//	console.log("in line number 197:"+jsonresp1[keys[i]] +"  ddvdv"+jsonresp2[keys[i]]  );
												arrayObject2.push([ keys[i].toString(), null, null ]);
											} else if (jsonresp1[keys[i]] === "00:00:00") {
//												alert("value in zero");
										//		console.log("in line number 202:"+jsonresp1[keys[i]] +"  ddvdv"+jsonresp2[keys[i]]  );
												arrayObject2.push([ keys[i].toString(), null, jsonresp2[keys[i]] ]);
											} else {
												if (jsonresp2[keys[i]] === "00:00:00") {
										//			console.log("in line number 207:"+jsonresp1[keys[i]] +"  ddvdv"+jsonresp2[keys[i]]  );
													arrayObject2.push([ keys[i].toString(), jsonresp1[keys[i]], null ]);
												} else {
											//		console.log("in line number 211:"+jsonresp1[keys[i]] +"  ddvdv"+jsonresp2[keys[i]]  );
													arrayObject2.push([ keys[i].toString(), jsonresp1[keys[i]], jsonresp2[keys[i]] ]);
												}
											}

										}
									} else {
										if (jsonresp1.hasOwnProperty(keys[i])) {
											if(jsonresp1[keys[i]]==="00:00:00") {
												arrayObject2.push([ keys[i].toString(), null, null ]);
											} else{
												arrayObject2.push([ keys[i].toString(), jsonresp1[keys[i]], null ]);
											}
										}
										if (jsonresp2.hasOwnProperty(keys[i])) {
											if(jsonresp2[keys[i]]==="00:00:00") {
												arrayObject2.push([ keys[i].toString(), null, 	null ]);
												}else
													arrayObject2.push([ keys[i].toString(), null, jsonresp2[keys[i]] ]);

										}
									}
								}*/
								var jsonactivethreads=jsonresp1;
								console.log("jsonactivethreads:",jsonactivethreads.length);
								var allactiveThreadds1=[];
								var allactiveThreadds2=[];
							/*	for(var i=0;i<jsonactivethreads.length;i++)
									{
									console.log("jsonactivethreads[0]jsonactivethreads[0]jsonactivethreads[0]",jsonactivethreads[0]);
									allactiveThreadds1.push([jsonactivethreads[0],jsonactivethreads[1]]);
									}*/
								for ( var key in jsonresp1) {
									allactiveThreadds1.push([key,jsonactivethreads[key]]);	
								};
								data1.addRows(allactiveThreadds1);
								
								console.log("allactiveThreadds1",allactiveThreadds1);
								jsonactivethreads=jsonresp2;
								var data2 = new google.visualization.DataTable();
								data2.addColumn('string', 'Elapsed Time');
								data2.addColumn('number','No.of Active Threads test2');
								
								for ( var key in jsonresp2) {
									allactiveThreadds2.push([key,jsonactivethreads[key]]);	
								};
								data2.addRows(allactiveThreadds2);
								var joinedData = google.visualization.data.join(data1, data2, 'full', [[0, 0]], [1], [1]);
								console.log("final data row:",arrayObject2);
								var options = {
									hAxis : {
										title : 'Elapsed Time (HH:MM:SS)',
										//format : "HH:mm:ss",
										gridlines : {color : 'none'}
									},
									vAxis : {
										title : 'Number of Active Threads',
									},
									title : 'Active Threads Over Time',
									legend: { position: 'top', alignment: 'end' }
								};
								var chart = new google.visualization.LineChart(document.getElementById('activeTOTCompare'));
								chart.draw(joinedData, options);
							});
					
					
					
				});
		
		

	}
	
	//------------------PDF-------------------------------
	$scope.atcgeneratePDF = function () {
		var doc = new jsPDF('p', 'pt', 'a4', false) /* Creates a new Document*/
		doc.setFontSize(15); /* Define font size for the document */
		var yAxis = 30;
		var imageTags = $('#activeThreadsComparePdf img');
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
		doc.save('ActiveThreadsCompareReport' + '.pdf') /* Prompts user to save file on his/her machine */
	}
	//------------------PDF-------------------------------
});
