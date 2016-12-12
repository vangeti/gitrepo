mainApp.controller('hitsPerSecondControllerCompare', function($scope, $sessionStorage,
		$rootScope, $http, widgetService) {

	$scope.tdTitle = 'Hits Per Second';

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
		$('#hitsSecLoadImg_id').show();
				window.console.log("value full event called");
				$(".panel-body").css({
					"max-height" : "100%",
					"min-height" : "100%"
				});
				$("#hitsPSCompare").css({
					
					"width" : "1400px",
					"height" : "600px"
				});
				//drawValueChart();
			/*	var testwith = $('#test_with').find(":selected").text();
				var testto=$('#test_to').find(":selected").text();*/
				//alert("test to:"+testto+ "  test with:"+testwith);
				
			//	drawValueChart(testto,testwith);
				drawValueChart($sessionStorage.test_to_number, $sessionStorage.testwith_number);
			});
	$('.hitsPerSecondPanel').on('onSmallSize.lobiPanel',
			function(ev, lobiPanel) {
		$('#hitsSecLoadImg_id').show();	
		window.console.log("value small event called");
				$(".panel-body").css({
					"max-height" : "300px",
					"min-height" : "300px"
				});
				$("#hitsPSCompare").css({
					"width" : "600px",
					"height" : "300px"
				});
				//drawValueChart();
				/*var testwith = $('#test_with').find(":selected").text();
				var testto=$('#test_to').find(":selected").text();
				drawValueChart(testto,testwith);*/
				drawValueChart($sessionStorage.test_to_number, $sessionStorage.testwith_number);
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

	//Added by sankar
	$( "#compare" ).click(function() {
		$('#hitsSecLoadImg_id').show();
		drawValueChart($sessionStorage.test_to_number, $sessionStorage.testwith_number);
		});
	function drawValueChart(testto,testwith) {
		var jsonresp1=new Array();
		var jsonresp2;
			$http.get("lennox/rest/hitsPerSec/live?testId="+testwith).then(
					function(response) {
						$('#hitsSecLoadImg_id').hide();
						jsonresp1 = response.data;
						$http.get("lennox/rest/hitsPerSec/live?testId=" +testto).then(
								function(response) {
									jsonresp2 = response.data;
									
									console.log("hitsper second comparision jsonresp2:",jsonresp2);
									
									var keys1=[];
									for ( var key in jsonresp1) {
										keys1.push([ key ]);
									};
									var keys2=[];
									for( var key in jsonresp2) {
										keys2.push([ key ]);
									};
									var x=new Date();
									var data=[];
									var item = {};
									var elapsedTime=[];
									var responseTime=[];
									for(var i=0; i<jsonresp1.length; i++)	{
										var v=jsonresp1[i];
								
										//	
										if(i==0){ 
										x=new Date(v[0]);
										date1=new Date(x); 
										x.setHours(00);
										x.setMinutes(00);
										x.setSeconds(00, 00);
										
										}
										else
											{
											var difference=new Date(v[0])-date1;
											x.setMilliseconds(difference);
											date1.setMilliseconds(difference);
											}
										
										var y=v[1];
										elapsedTime.push(new Date(x));
										responseTime.push(y);
									}
															
									item["x"]=elapsedTime;
									item["y"]=responseTime;
									item["type"]='scatter';
									item["name"]=testwith;
									data.push(item);
									
									var item = {};
									var elapsedTime=[];
									var responseTime=[];
									
									for(var i=0; i<jsonresp2.length; i++)	{
										var v=jsonresp2[i];
										
										if(i==0){ 
											x=new Date(v[0]);
											date1=new Date(x); 
											x.setHours(00);
											x.setMinutes(00);
											x.setSeconds(00, 00);
											
											}
											else
												{
												var difference=new Date(v[0])-date1;
												x.setMilliseconds(difference);
												date1.setMilliseconds(difference);
												}
										
										var y=v[1];
										elapsedTime.push(new Date(x));
										responseTime.push(y);
									}
															
									item["x"]=elapsedTime;
									item["y"]=responseTime;
									item["type"]='scatter';
									item["name"]=testto;
									data.push(item);
									var layout = {
											showlegend: true,
											legend: {"y": -0.5, "x": 0.1,"orientation": "h", type: 'grid'},
									};
									
									
								Plotly.newPlot('hitsPSCompare', data, layout,{displayModeBar:false});
									
									/*
									jsonresp2 = response.data;
									$('#hpsLoadImg_id').hide();
									$('#test_id').hide();
									$('.perfTestDetailsTitle').show();
									$('.perfTestDetailsTable').show();
									
									var data1 = new google.visualization.DataTable();
									data1.addColumn('number', 'Elapsed Time');
									data1.addColumn('number','No.of Hits per sec test1');
									data1.addColumn('number','No.of Hits per sec test2');
									var keys=[];
									var i=0;
									for ( var key in jsonresp1) {
										keys.push(key );
									};
									
									for( var key in jsonresp2) {
										keys.push(key );
									};
								
									var arrayObject2 = new Array();
									var counter=false;
									var uniquekeys = [];
									
									$.each(keys, function(i, el){
									    if($.inArray(el, uniquekeys) === -1) uniquekeys.push(el);
									});
									keys=uniquekeys;
									for(var i=0;i<keys.length;i++)
									{
										console.log("jsonresp1.hasOwnProperty(keys[i]) ",jsonresp1.hasOwnProperty(keys[i]) );
									 if (jsonresp1.hasOwnProperty(keys[i]) && jsonresp2.hasOwnProperty(keys[i])) {
										 if(jsonresp1.hasOwnProperty(keys[i])==0 && jsonresp2.hasOwnProperty(keys[i])==0)
											{
											}
										 if(jsonresp1[keys[i]]==0)
											 {
											 arrayObject2.push([ parseInt(keys[i]), null,jsonresp2[keys[i]]]);}
										 if(jsonresp2[keys[i]]==0)
											 {
											 arrayObject2.push([ parseInt(keys[i]), jsonresp1[keys[i]],null]);}
										 else {
											 arrayObject2.push([ parseInt(keys[i]), jsonresp1[keys[i]],jsonresp2[keys[i]]]);}
										  }
									 else
										 {
										 if(jsonresp1.hasOwnProperty(keys[i])){
											 arrayObject2.push([ parseInt(keys[i]), jsonresp1[keys[i]],null]);
										 }
										 if(jsonresp2.hasOwnProperty(keys[i]))
											 {
											 arrayObject2.push([parseInt(keys[i]), null,jsonresp2[keys[i]]]);
											 }
										 }
									}
									data1.addRows(arrayObject2);
										var options = {
												hAxis : {
													gridlines: {color: 'none'}
												},
												vAxis : {
														gridlines: {color: 'none'}
												},
												legend: 'bottom',
												
											};

										var chart = new google.visualization.LineChart(document.getElementById('hitsPSCompare'));
										//------------------PDF-------------------------------
										google.visualization.events.addListener(chart, 'ready', function () {
											  var content = '<img src="' + chart.getImageURI() + '">';
											  $('#hitsPerSecondComparePdf').append(content);
											});
											
										//------------------PDF-------------------------------

										chart.draw(data1, options);
								*/
									
								});
						});
			
			}
	//------------------PDF-------------------------------
	$scope.htcgeneratePDF = function () {
		var doc = new jsPDF('p', 'pt', 'a4', false) /* Creates a new Document*/
		doc.setFontSize(15); /* Define font size for the document */
		var yAxis = 30;
		var imageTags = $('#hitsPerSecondComparePdf img');
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
		doc.save('HitsPerSecondCompareReport' + '.pdf') /* Prompts user to save file on his/her machine */
	}
	//------------------PDF-------------------------------
});