mainApp
		.controller(
				'ReleaseReadyController',
				function($scope, widgetService, $rootScope, $http,
						$sessionStorage) {

					var engKPI = 0;
					var anlyKPI = 0;
					$scope.rrKPI = 0;

					$('#releaseReadyLoadImg_id').show();
					$('.rrViewShow').hide();

					$http
							.get("devops/rest/releaseReadynessKPI")
							.then(
									function(response) {

										var releaseReadyness = response.data;

										anlyKPI = releaseReadyness.anlyKPI;
										engKPI = releaseReadyness.engKPI;
										$scope.rrKPI = releaseReadyness.relReady;

										$("#kpiPerc")
												.text(
														"(Engineering KPIs "
																+ releaseReadyness.engKPIPercentage
																+ "%) + (Analytical KPIs "
																+ releaseReadyness.analyKPIPercentage
																+ "%)");

										$scope.rrGTitle = "Release Readiness : "
												+ $scope.rrKPI + "%";
										$scope.engGTitle = "Engineering KPIs : "
												+ engKPI + "%";
										$scope.anGTitle = "Analytical KPIs : "
												+ anlyKPI + "%";

										$scope.gauge1H = 240;
										$scope.gauge1w = 100;

										$scope.gauge2H = 115;
										$scope.gauge2w = 50;

										$scope.rrGaugeFlag = true;
										$scope.rrSubGaugeFlag = false;
										$scope.rrEngFlag = false;
										$scope.rrAnyFlag = false;

										var w = "420";
										var h = "160";

										$scope.subGView = function() {

											if ($scope.rrSubGaugeFlag === false) {
												$scope.rrSubGaugeFlag = true;
												$(".gauge").css({
													"padding-top" : "100px",
													"padding-bottom" : "100px"
												});
											} else {
												$scope.rrSubGaugeFlag = false;
												$(".rrPadding").css({
													"padding-top" : "100px",
													"padding-bottom" : "100px"
												});
											}
										}

										$scope.rrView = function(type) {

											if (type === 'Eng') {
												$scope.rrGaugeFlag = false;
												$scope.rrEngFlag = true;
												$scope.rrAnyFlag = false;
											} else if (type === 'Any') {
												$scope.rrGaugeFlag = false;
												$scope.rrEngFlag = false;
												$scope.rrAnyFlag = true;
											} else if (type === 'RR') {
												$scope.rrGaugeFlag = true;
												$scope.rrEngFlag = false;
												$scope.rrAnyFlag = false;
											}
										}

										/**
										 * LObi panel code
										 */
										$('.rrPanel').lobiPanel({
											// Options go here
											sortable : false,
											// editTitle : false,
											close : false,
											reload : true,
											editTitle : false,
											minimize : false,
											expand : false,
											unpin : {
												icon : 'fa fa-thumb-tack'
											},
											reload : {
												icon : 'fa fa-refresh'
											},
										/*
										 * close : { icon : 'fa fa-times-circle' },
										 */
										/*
										 * expand : { icon : 'fa fa-expand',
										 * icon2 : 'fa fa-compress' }
										 */
										});

										$('.rrPanel')
												.on(
														'onPin.lobiPanel',
														function(ev, lobiPanel) {

															$(".rrPanel-body")
																	.css(
																			{
																				"max-height" : "400px",
																				"min-height" : "400px"
																			});
															$("#gauge").empty();
															$("#gauge")
																	.css(
																			{
																				"width" : "700px",
																				"height" : "360px"
																			});
															var g = new JustGage(
																	{
																		id : "gauge",
																		value : $scope.rrKPI,
																		min : 0,
																		max : 100,
																		title : $scope.rrGTitle
																	});
															$("#EngGauge")
																	.empty();
															$("#EngGauge")
																	.css(
																			{
																				"width" : "300px",
																				"height" : "170px"
																			});
															var g1 = new JustGage(
																	{
																		id : "EngGauge",
																		value : engKPI,
																		min : 0,
																		max : 100,
																		title : $scope.engGTitle
																	});
															$("#AnGauge")
																	.empty();
															$("#AnGauge")
																	.css(
																			{
																				"width" : "300px",
																				"height" : "170px"
																			});
															var g2 = new JustGage(
																	{
																		id : "AnGauge",
																		value : anlyKPI,
																		min : 0,
																		max : 100,
																		title : $scope.anGTitle
																	});
															w = "420";
															h = "160";
															/*
															 * $("#g11") .css( {
															 * "width" :
															 * "290px", "height" :
															 * "170px" });
															 */
															drawcolumnchart1();
															/*
															 * $("#g12") .css( {
															 * "width" :
															 * "290px", "height" :
															 * "170px" });
															 */
															drawcolumnchart2();
															/*
															 * $("#g13") .css( {
															 * "width" :
															 * "290px", "height" :
															 * "170px" });
															 */
															drawcolumnchart3();
															/*
															 * $("#g14") .css( {
															 * "width" :
															 * "290px", "height" :
															 * "170px" });
															 */
															drawcolumnchart4();
															/*
															 * $("#g21") .css( {
															 * "width" :
															 * "290px", "height" :
															 * "170px" });
															 */
															drawcolumnchart5();
															/*
															 * $("#g22") .css( {
															 * "width" :
															 * "290px", "height" :
															 * "170px" });
															 */

															drawcolumnchart6();
															/*
															 * $("#g23") .css( {
															 * "width" :
															 * "290px", "height" :
															 * "170px" });
															 */

															drawcolumnchart7();
															/*
															 * $("#g24") .css( {
															 * "width" :
															 * "290px", "height" :
															 * "170px" });
															 */

															drawcolumnchart8();
														});

										$('.rrPanel')
												.on(
														'onUnpin.lobiPanel',
														function(ev, lobiPanel) {
															lobiPanel.$options.expand = false;

															$(".rrPanel")
																	.css(
																			{
																				"left" : "150.5px",
																				"top" : "122px",
																				"height" : "90%",
																				"width" : "90%"
																			});
															$(".rrPanel-body")
																	.css(
																			{
																				"width" : "100%",
																				"height" : "100%",
																				"max-height" : "92%",
																				"min-height" : "92%"
																			});
															$("#gauge").empty();
															$("#gauge")
																	.css(
																			{
																				"width" : "700px",
																				"height" : "500px"
																			});
															var g = new JustGage(
																	{
																		id : "gauge",
																		value : $scope.rrKPI,
																		min : 0,
																		max : 100,
																		title : $scope.rrGTitle
																	});
															$("#EngGauge")
																	.empty();
															$("#EngGauge")
																	.css(
																			{
																				"width" : "300px",
																				"height" : "250px"
																			});
															var g1 = new JustGage(
																	{
																		id : "EngGauge",
																		value : engKPI,
																		min : 0,
																		max : 100,
																		title : $scope.engGTitle
																	});
															$("#AnGauge")
																	.empty();
															$("#AnGauge")
																	.css(
																			{
																				"width" : "300px",
																				"height" : "250px"
																			});
															var g2 = new JustGage(
																	{
																		id : "AnGauge",
																		value : anlyKPI,
																		min : 0,
																		max : 100,
																		title : $scope.anGTitle
																	});
															w = "480";
															h = "240";
															/*
															 * $("#g11") .css( {
															 * "width" : "100%",
															 * "height" :
															 * "200px" });
															 */
															drawcolumnchart1();
															/*
															 * $("#g12") .css( {
															 * "width" : "100%",
															 * "height" :
															 * "200px" });
															 */
															drawcolumnchart2();
															/*
															 * $("#g13") .css( {
															 * "width" : "100%",
															 * "height" :
															 * "200px" });
															 */
															drawcolumnchart3();
															/*
															 * $("#g14") .css( {
															 * "width" : "100%",
															 * "height" :
															 * "200px" });
															 */
															drawcolumnchart4();
															/*
															 * $("#g21") .css( {
															 * "width" : "100%",
															 * "height" :
															 * "200px" });
															 */
															drawcolumnchart5();
															/*
															 * $("#g22") .css( {
															 * "width" : "100%",
															 * "height" :
															 * "200px" });
															 */
															drawcolumnchart6();
															/*
															 * $("#g23") .css( {
															 * "width" : "100%",
															 * "height" :
															 * "200px" });
															 */
															drawcolumnchart7();
															/*
															 * $("#g24") .css( {
															 * "width" : "100%",
															 * "height" :
															 * "200px" });
															 */

															drawcolumnchart8();
														});

										/*
										 * $('.rrPanel') .on(
										 * 'beforeClose.lobiPanel', function(ev,
										 * lobiPanel) { var widgetResp =
										 * widgetService .deleteWidget(
										 * $sessionStorage.userId,
										 * "rrPanelFlag");
										 * 
										 * widgetResp .then( function( resp) {
										 * 
										 * if (resp.data.Status === "Success") {
										 * $rootScope.rrPanelFlag = false;
										 * $rootScope.widgetList .push({ "key" :
										 * "rrPanelFlag", "value" :
										 * $scope.rrGTitle }); // $rootScope //
										 * .$digest(); } }, function(
										 * errorPayload) {
										 * 
										 * }); });
										 * 
										 * $('.release') .on(
										 * 'onFullScreen.lobiPanel',
										 * function(ev, lobiPanel) {
										 * $(".panel-body") .css( { "max-height" :
										 * "100%", "min-height" : "100%" });
										 * 
										 * $("#gauge").empty(); $("#gauge")
										 * .css( { "width" : "800px", "height" :
										 * "250px" }); var g = new JustGage( {
										 * id : "gauge", value : $scope.rrKPI,
										 * min : 0, max : 100, // title : //
										 * $scope.rrGTitle }); $("#EngGauge")
										 * .empty(); $("#EngGauge") .css( {
										 * "width" : "400px", "height" : "200px"
										 * }); var g1 = new JustGage( { id :
										 * "EngGauge", value : engKPI, min : 0,
										 * max : 100, title : $scope.engGTitle
										 * }); $("#AnGauge") .empty();
										 * $("#AnGauge") .css( { "width" :
										 * "400px", "height" : "200px" }); var
										 * g2 = new JustGage( { id : "AnGauge",
										 * value : anlyKPI, min : 0, max : 100,
										 * title : $scope.anGTitle }); w =
										 * "600"; h = "300"; $("#g11") .css( {
										 * "width" : "100%", "height" : "300px"
										 * }); drawcolumnchart1(); $("#g12")
										 * .css( { "width" : "100%", "height" :
										 * "300px" }); drawcolumnchart2();
										 * $("#g13") .css( { "width" : "100%",
										 * "height" : "300px" });
										 * drawcolumnchart3(); $("#g14") .css( {
										 * "width" : "100%", "height" : "300px"
										 * }); drawcolumnchart4(); $("#g21")
										 * .css( { "width" : "100%", "height" :
										 * "300px" }); drawcolumnchart5();
										 * $("#g22") .css( { "width" : "100%",
										 * "height" : "300px" });
										 * drawcolumnchart6(); $("#g23") .css( {
										 * "width" : "100%", "height" : "300px"
										 * }); drawcolumnchart7(); $("#g24")
										 * .css( { "width" : "100%", "height" :
										 * "300px" });
										 * 
										 * drawcolumnchart8(); });
										 */
										/*
										 * $('.release') .on(
										 * 'onSmallSize.lobiPanel', function(ev,
										 * lobiPanel) { $(".panel-body") .css( {
										 * "max-height" : "300px", "min-height" :
										 * "300px" });
										 * 
										 * $("#gauge").empty(); $("#gauge")
										 * .css( { "width" : "240px", "height" :
										 * "100px" }); var g = new JustGage( {
										 * id : "gauge", value : $scope.rrKPI,
										 * min : 0, max : 100, // title : //
										 * $scope.rrGTitle }); $("#EngGauge")
										 * .empty(); $("#EngGauge") .css( {
										 * "width" : "115px", "height" : "50px"
										 * }); var g1 = new JustGage( { id :
										 * "EngGauge", value : engKPI, min : 0,
										 * max : 100, title : $scope.engGTitle
										 * }); $("#AnGauge") .empty();
										 * $("#AnGauge") .css( { "width" :
										 * "115px", "height" : "50px" }); var g2 =
										 * new JustGage( { id : "AnGauge", value :
										 * anlyKPI, min : 0, max : 100, title :
										 * $scope.anGTitle }); w = "120"; h =
										 * "120"; $("#g11") .css( { "width" :
										 * "120px", "height" : "120px" });
										 * drawcolumnchart1(); $("#g12") .css( {
										 * "width" : "120px", "height" : "120px"
										 * }); drawcolumnchart2(); $("#g13")
										 * .css( { "width" : "120px", "height" :
										 * "120px" }); drawcolumnchart3();
										 * $("#g14") .css( { "width" : "120px",
										 * "height" : "120px" });
										 * drawcolumnchart4(); $("#g21") .css( {
										 * "width" : "120px", "height" : "120px"
										 * }); drawcolumnchart5(); $("#g22")
										 * .css( { "width" : "120px", "height" :
										 * "120px" });
										 * 
										 * drawcolumnchart6(); $("#g23") .css( {
										 * "width" : "120px", "height" : "120px"
										 * });
										 * 
										 * drawcolumnchart7(); $("#g24") .css( {
										 * "width" : "120px", "height" : "120px"
										 * });
										 * 
										 * drawcolumnchart8(); });
										 */

										google.charts
												.setOnLoadCallback(drawcolumnchart1);
										function drawcolumnchart1() {

											var jsonresp = releaseReadyness.testExecutionProgress.executionProgressMap;

											var keys = [];
											var values = [];
											for ( var key in jsonresp) {

												keys.push(key);
												values.push(jsonresp[key]);
											}
											var data = google.visualization
													.arrayToDataTable([
															[ 'Element', '', {
																role : 'style'
															} ],
															[
																	keys[0],
																	parseFloat(values[0]),
																	'#ffccb3' ],
															[
																	keys[1],
																	parseFloat(values[1]),
																	'#ffaa80' ],
															[
																	keys[2],
																	parseFloat(values[2]),
																	'#ff884d' ],
															[
																	keys[3],
																	parseFloat(values[3]),
																	'#ff9933' ] ]);

											drawColumnChartfinal(
													data,
													'Test Progress '
															+ releaseReadyness.testExecutionProgress.meanTotal
															+ '%',
													'Application Name',
													'Percentage', '100%',
													'100%', 'g11', w, h);

										}

										google.charts
												.setOnLoadCallback(drawcolumnchart2);
										function drawcolumnchart2() {

											var jsonresp = releaseReadyness.defectSeverityIndex.defectSeverityIndexMap;

											var keys = [];
											var values = [];
											for ( var key in jsonresp) {

												keys.push(key);
												values.push(jsonresp[key]);
											}
											var data = google.visualization
													.arrayToDataTable([
															[ 'Element', '', {
																role : 'style'
															} ],
															[
																	keys[0],
																	parseFloat(values[0]),
																	'#ffccb3' ],
															[
																	keys[1],
																	parseFloat(values[1]),
																	'#ffaa80' ],
															[
																	keys[2],
																	parseFloat(values[2]),
																	'#ff884d' ],
															[
																	keys[3],
																	parseFloat(values[3]),
																	'#ff9933' ] ]);

											drawColumnChartfinal(
													data,
													'Defect Severity Index '
															+ releaseReadyness.defectSeverityIndex.meanTotal
															+ '%',
													'Application Name',
													'Scale 0 to 5', '100%',
													'100%', 'g12', w, h);

										}

										google.charts
												.setOnLoadCallback(drawcolumnchart3);
										function drawcolumnchart3() {

											var jsonresp = releaseReadyness.testCoverage.testCoverageMap;

											var keys = [];
											var values = [];
											for ( var key in jsonresp) {

												keys.push(key);
												values.push(jsonresp[key]);
											}
											var data = google.visualization
													.arrayToDataTable([
															[ 'Element', '', {
																role : 'style'
															} ],
															[
																	keys[0],
																	parseFloat(values[0]),
																	'#ffccb3' ],
															[
																	keys[1],
																	parseFloat(values[1]),
																	'#ffaa80' ],
															[
																	keys[2],
																	parseFloat(values[2]),
																	'#ff884d' ],
															[
																	keys[3],
																	parseFloat(values[3]),
																	'#ff9933' ] ]);

											drawColumnChartfinal(
													data,
													'Test Coverage '
															+ releaseReadyness.testCoverage.meanTotal
															+ '%',
													'Application Name',
													'Coverage (%)', '100%',
													'100%', 'g13', w, h);

										}

										google.charts
												.setOnLoadCallback(drawcolumnchart4);
										function drawcolumnchart4() {

											var jsonresp = releaseReadyness.openDefectSeverity.openDefectSeverityMap;

											var keys = [];
											var values = [];
											for ( var key in jsonresp) {

												keys.push(key);
												values.push(jsonresp[key]);
											}
											var data = google.visualization
													.arrayToDataTable([
															[
																	'Element',
																	'S1',
																	'S2',
																	{
																		role : 'style'
																	} ],
															[
																	keys[0],
																	parseInt(values[0].severityOne),
																	parseInt(values[0].severityTwo),
																	'' ],
															[
																	keys[1],
																	parseInt(values[1].severityOne),
																	parseInt(values[1].severityTwo),
																	'' ],
															[
																	keys[2],
																	parseInt(values[2].severityOne),
																	parseInt(values[2].severityTwo),
																	'' ],
															[
																	keys[3],
																	parseInt(values[3].severityOne),
																	parseInt(values[3].severityTwo),
																	'' ] ]);

											drawStackedColumnChart(
													data,
													'Open Defects by App '
															+ releaseReadyness.openDefectSeverity.meanTotal,
													'Application Name',
													'Number of Defects',
													'100%', '100%', 'g14', w, h);

										}

										/* First Guage End */

										/* Second Guage Start */

										google.charts
												.setOnLoadCallback(drawcolumnchart5);
										function drawcolumnchart5() {

											var jsonresp = releaseReadyness.requirementStabilityIndex.requirementStabilityIndexMap;

											var keys = [];
											var values = [];
											for ( var key in jsonresp) {

												keys.push(key);
												values.push(jsonresp[key]);
											}

											var data = google.visualization
													.arrayToDataTable([
															[ 'Element', '', {
																role : 'style'
															} ],
															[
																	keys[0],
																	parseFloat(values[0]),
																	'#ffccb3' ],
															[
																	keys[1],
																	parseFloat(values[1]),
																	'#ffaa80' ],
															[
																	keys[2],
																	parseFloat(values[2]),
																	'#ff884d' ],
															[
																	keys[3],
																	parseFloat(values[3]),
																	'#ff9933' ] ]);

											drawColumnChartfinal(
													data,
													'Requirement Stability Index '
															+ releaseReadyness.requirementStabilityIndex.meanTotal
															+ '%',
													'Application Name',
													'RSI (%)', '100%', '100%',
													'g21', w, h);

										}

										google.charts
												.setOnLoadCallback(drawcolumnchart6);
										function drawcolumnchart6() {

											var jsonresp = releaseReadyness.meanTime.meanTimeMap;

											var keys = [];
											var values = [];
											for ( var key in jsonresp) {

												keys.push(key);
												values.push(jsonresp[key]);
											}
											var data = google.visualization
													.arrayToDataTable([
															[ 'Element', '', {
																role : 'style'
															} ],
															[
																	keys[0],
																	parseFloat(values[0]),
																	'#ffccb3' ],
															[
																	keys[1],
																	parseFloat(values[1]),
																	'#ffaa80' ],
															[
																	keys[2],
																	parseFloat(values[2]),
																	'#ff884d' ],
															[
																	keys[3],
																	parseFloat(values[3]),
																	'#ff9933' ] ]);

											drawColumnChartfinal(
													data,
													'Mean Time to Find a Defect '
															+ releaseReadyness.meanTime.meanTotal
															+ ' days',
													'Application Name',
													'Hours', '100%', '100%',
													'g22', w, h);

											/*
											 * var data = google.visualization
											 * .arrayToDataTable([ [ 'Element',
											 * '', { role : 'style' } ], [
											 * 'citrix', 10, '#ff0000' ], // RGB //
											 * value [ 'SAP-billing', 30,
											 * '#0066cc' ], // English // color //
											 * name [ 'SAP-FICO', 50, '#006600' ], [
											 * 'TXWeb', 20, 'color: #ff9933' ], //
											 * CSS-style // declaration ]);
											 * 
											 * drawColumnChartfinal( data, 'Mean
											 * Time to Find a Defect 0 days',
											 * 'Application Name', 'Hours',
											 * '100%', '100%', 'g22');
											 */

										}

										google.charts
												.setOnLoadCallback(drawcolumnchart7);
										function drawcolumnchart7() {

											var jsonresp = releaseReadyness.featureReadyness.featureReadynessMap;

											var keys = [];
											var values = [];
											for ( var key in jsonresp) {

												keys.push(key);
												values.push(jsonresp[key]);
											}

											var data = google.visualization
													.arrayToDataTable([
															[ 'Element', '', {
																role : 'style'
															} ],
															[
																	keys[0],
																	parseFloat(values[0]),
																	'#ffccb3' ],
															[
																	keys[1],
																	parseFloat(values[1]),
																	'#ffaa80' ],
															[
																	keys[2],
																	parseFloat(values[2]),
																	'#ff884d' ],
															[
																	keys[3],
																	parseFloat(values[3]),
																	'#ff9933' ] ]);
											drawColumnChartfinal(
													data,
													'Feature Readiness '
															+ releaseReadyness.featureReadyness.meanTotal
															+ '% ',
													'Application Name',
													'Readiness (%)', '100%',
													'100%', 'g23', w, h);

										}

										google.charts
												.setOnLoadCallback(drawcolumnchart8);
										function drawcolumnchart8() {

											var jsonresp = releaseReadyness.testCaseYield.testCaseYieldMap;

											var keys = [];
											var values = [];
											for ( var key in jsonresp) {

												keys.push(key);
												values.push(jsonresp[key]);
											}
											var data = google.visualization
													.arrayToDataTable([
															[ 'Element', '', {
																role : 'style'
															} ],
															[
																	keys[0],
																	parseFloat(values[0]),
																	'#ffccb3' ],
															[
																	keys[1],
																	parseFloat(values[1]),
																	'#ffaa80' ],
															[
																	keys[2],
																	parseFloat(values[2]),
																	'#ff884d' ],
															[
																	keys[3],
																	parseFloat(values[3]),
																	'#ff9933' ] ]);

											drawColumnChartfinal(
													data,
													'Test Case Yield '
															+ releaseReadyness.testCaseYield.meanTotal
															+ '%',
													'Application Name',
													'Percentage', '100%',
													'100%', 'g24', w, h);

										}

										function drawColumnChartfinal(data,
												garphTitle, hAxisTitle,
												vAxisTitle, height, width,
												divContainer, w, h) {

											var color = "#000000";
											var options = {
												title : garphTitle,
												titleTextStyle : {
													color : '#999',
													fontName : 'Open Sans, Arial, Helvetica, sans-serif',
													fontSize : 12,
													italic : false,
												},
												backgroundColor : 'white',
												timeline : {
													colorByRowLabel : false
												},
												legend : {
													position : 'none'
												},
												// height : height,
												// width : width,
												height : h,
												width : w,
												hAxis : {
													title : hAxisTitle,
													format : 'h:mm a',
													titleTextStyle : {
														color : '#999',
														fontName : 'Open Sans, Arial, Helvetica, sans-serif',
														fontSize : 12,
														italic : false,
													},
													textStyle : {
														color : '#999',
														fontName : 'Open Sans, Arial, Helvetica, sans-serif',
														fontSize : 12,
														italic : false,
													},
												// direction : 1,
												// slantedText : true,
												// slantedTextAngle : 20

												},

												vAxis : {
													title : vAxisTitle,
													titleTextStyle : {
														color : '#999',
														fontName : 'Open Sans, Arial, Helvetica, sans-serif',
														fontSize : 12,
														italic : false,
													},
													textStyle : {
														color : '#999',
														fontName : 'Open Sans, Arial, Helvetica, sans-serif',
														fontSize : 12,
														italic : false,
													},
													gridlines : {
														color : 'transparent'
													}
												},

											};

											var chart = new google.visualization.ColumnChart(
													document
															.getElementById(divContainer));

											chart.draw(data, options);
										}

										function drawStackedColumnChart(data,
												garphTitle, hAxisTitle,
												vAxisTitle, height, width,
												divContainer, w, h) {

											var color = "#000000";
											var options = {
												title : garphTitle,
												titleTextStyle : {
													color : '#999',
													fontName : 'Open Sans, Arial, Helvetica, sans-serif',
													fontSize : 12,
													italic : false,
												},
												backgroundColor : 'white',
												timeline : {
													colorByRowLabel : false
												},
												legend : {
													position : 'none'
												},
												// height : height,
												// width : width,
												height : h,
												width : w,
												colors : [ '#ffccb3', '#ff9933' ],
												isStacked : true,

												hAxis : {
													title : hAxisTitle,
													format : 'h:mm a',
													titleTextStyle : {
														color : '#999',
														fontName : 'Open Sans, Arial, Helvetica, sans-serif',
														fontSize : 12,
														italic : false,
													},
													textStyle : {
														color : '#999',
														fontName : 'Open Sans, Arial, Helvetica, sans-serif',
														fontSize : 12,
														italic : false,
													},
												// direction : 1,
												// slantedText : true,
												// slantedTextAngle : 20

												},

												vAxis : {
													title : vAxisTitle,
													titleTextStyle : {
														color : '#999',
														fontName : 'Open Sans, Arial, Helvetica, sans-serif',
														fontSize : 12,
														italic : false,
													},
													textStyle : {
														color : '#999',
														fontName : 'Open Sans, Arial, Helvetica, sans-serif',
														fontSize : 12,
														italic : false,
													},
													gridlines : {
														color : 'transparent'
													}
												},

											};

											var chart = new google.visualization.ColumnChart(
													document
															.getElementById(divContainer));

											chart.draw(data, options);
										}

										var g = new JustGage({
											id : "gauge",
											value : $scope.rrKPI,
											min : 0,
											max : 100,
											title : $scope.rrGTitle
										});
										var g1 = new JustGage({
											id : "EngGauge",
											value : engKPI,
											min : 0,
											max : 100,
											title : $scope.engGTitle
										});
										var g2 = new JustGage({
											id : "AnGauge",
											value : anlyKPI,
											min : 0,
											max : 100,
											title : $scope.anGTitle
										});

										$('#releaseReadyLoadImg_id').hide();
										$('.rrViewShow').show();
									});
				});