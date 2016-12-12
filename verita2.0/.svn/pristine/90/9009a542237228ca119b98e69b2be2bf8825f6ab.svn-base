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
				$('#activethreadsLoadImg_id').show();
				window.console.log("value full event called");
				$(".panel-body").css({
					"max-height" : "100%",
					"min-height" : "100%"
				});
				$("#activeTOTCompare").css({
					"width" : "1400px",
					"height" : "600px"
				});
				drawValueChart($sessionStorage.test_to_number, $sessionStorage.testwith_number);
			});
	$('.activeThreadsOverTimePanel').on('onSmallSize.lobiPanel',
			function(ev, lobiPanel) {
				$('#activethreadsLoadImg_id').show();
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
		$('#activethreadsLoadImg_id').show();
		drawValueChart($sessionStorage.test_to_number, $sessionStorage.testwith_number);
	});
	
	
	function drawValueChart(testto, testwith) {
		var jsonresp1=new Array();
		var jsonresp2;
		$http.get("lennox/rest/activeThreads/live/plotty?testId=" +testwith).then(
				function(response) {
					$('.perfTestDetailsTitle').show();
					$('.perfTestDetailsTable').show();
					jsonresp1 = response.data;
					$http.get("lennox/rest/activeThreads/live/plotty?testId=" +testto).then(
							function(response) {
								$('#activethreadsLoadImg_id').hide();
								jsonresp2=response.data;
								$http.get("lennox/rest/userProfile/live?testId="+testwith).then(
								function(response) {
									var jsonRespUserProfileBeanTestWith = response.data;
									$http.get("lennox/rest/userProfile/live?testId="+testto).then(
									function(response) {
										var jsonRespUserProfileBeanTestTo = response.data;
										var data=[];
										var item = {};
										var elapsedTime=[];
										var responseTime=[];
										var date1=new Date();
										
										var jsonRespTestStartTime = jsonRespUserProfileBeanTestWith.teststarttime;
										var x=new Date(jsonRespTestStartTime);
										date1=new Date(x); 
										x.setHours(00);
										x.setMinutes(00);
										x.setSeconds(00, 00);
										
										elapsedTime.push(new Date(x));
										responseTime.push(0);
										
										for(var i=0;i<jsonresp1.length;i++) {
											var v=jsonresp1[i];
											var difference=new Date(v[0])-date1;
											x.setMilliseconds(difference);
											date1.setMilliseconds(difference);
											var y=v[1];
											elapsedTime.push(new Date(x));
											responseTime.push(y);
										}
										var indexToCheck = responseTime.length - 1;
										if(responseTime[indexToCheck] !== 0){
											var jsonRespTestEndTime = jsonRespUserProfileBeanTestWith.testendtime;
											var difference=new Date(jsonRespTestEndTime)-date1;
											x.setMilliseconds(difference);
											elapsedTime.push(new Date(x));
											responseTime.push(0);
										}
										item["x"]=elapsedTime;
										item["y"]=responseTime;
										item["type"]='scatter';
										item["name"]=testwith;
										data.push(item);
										
										//second request
										 item = {};
										 elapsedTime=[];
										 responseTime=[];
										 
										var jsonRespTestToStartTime = jsonRespUserProfileBeanTestTo.teststarttime;
										var x=new Date(jsonRespTestToStartTime);
										date1=new Date(x); 
										x.setHours(00);
										x.setMinutes(00);
										x.setSeconds(00, 00);
										
										elapsedTime.push(new Date(x));
										responseTime.push(0);
										
										 for(var i=0;i<jsonresp2.length;i++) {
											var v=jsonresp2[i];
											var difference=new Date(v[0])-date1;
											x.setMilliseconds(difference);
											date1.setMilliseconds(difference);
											var y=v[1];
											elapsedTime.push(new Date(x));
											responseTime.push(y);
										}
										var indexToCheck = responseTime.length - 1;
										if(responseTime[indexToCheck] !== 0){
											var jsonRespTestEndTime = jsonRespUserProfileBeanTestTo.testendtime;
											var difference=new Date(jsonRespTestEndTime)-date1;
											x.setMilliseconds(difference);
											elapsedTime.push(new Date(x));
											responseTime.push(0);
										}
										item["x"]=elapsedTime;
										item["y"]=responseTime;
										item["type"]='scatter';
										item["name"]=testto;
										data.push(item);
										console.log("datadata",data);
										var layout = {
												displayModeBar: false,
												showlegend: true,
												legend: {"y": -0.5, "x": 0.1,"orientation": "h", type: 'grid'}
										};
										Plotly.newPlot('activeTOTCompare', data, layout, {displayModeBar:false});
								  });	
							 });	
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
