mainApp
		.controller(
				'PerfAvgRTController',
				function($scope, $sessionStorage, $rootScope, $http,
						widgetService) {

					$('.perfAvgResp').hide();
					$('#perfAvgRtLoadImg_id').show();

					$scope.artTitle = 'Avg Response Time';

					$scope.tpsKPI = false;
					$scope.keys = [];
					$scope.values = [];

					// and fire it after definition

					// init();

					$('.avgRtPanel').lobiPanel({
						// Options go here
						sortable : false,
						editTitle : false,
						close : false,
						expand : false,
						/* unpin : false, */
						reload : true,
						/*
						 * editTitle : { icon : 'fa fa-line-chart artKPI', icon2 :
						 * 'fa fa-bar-chart artChart', tooltip : 'Plot Trend
						 * Against KPI' },
						 */
						minimize : false,
						unpin : {
							icon : 'fa fa-thumb-tack'
						},
						/*
						 * minimize : { icon : 'fa fa-chevron-up', icon2 : 'fa
						 * fa-chevron-down' },
						 */
						reload : {
							icon : 'fa fa-refresh'
						},
					/*
					 * close : { icon : 'fa fa-times-circle' }, expand : { icon :
					 * 'fa fa-expand', icon2 : 'fa fa-compress' }
					 */
					});

					$('.avgRtPanel').on('onPin.lobiPanel',
							function(ev, lobiPanel) {
								window.console.log("Pinned");
								$(".avgrtPanel-body").css({
									"max-height" : "315px",
									"min-height" : "315px"
								});
								$("#avgrespTime").css({
									"width" : "600px",
									"height" : "300px"
								});
								drawValueChart();
							});

					$('.avgRtPanel').on('onUnpin.lobiPanel',
							function(ev, lobiPanel) {
								lobiPanel.$options.expand = false;
								window.console.log("Unpinned");
								$(".avgRtPanel").css({
									"left" : "150.5px",
									"top" : "122px",
									"height" : "80%",
									"width" : "80%"
								});
								$(".avgrtPanel-body").css({
									"width" : "100%",
									"height" : "100%",
									"max-height" : "85%",
									"min-height" : "85%"
								});
								$("#avgrespTime").css({
									"width" : "100%",
									"height" : "400px"
								});
								drawValueChart();
							});

					$(".artKPI").on("click", function() {

						if ($(this).attr("class").indexOf("artKPI") >= 0) {
							$scope.tpsKPI = true;
							drawValueComboChart();
						} else {
							$scope.tpsKPI = false;
							drawValueChart();
						}
					});

					$('.avgRtPanel').on(
							'beforeClose.lobiPanel',
							function(ev, lobiPanel) {
								var widgetResp = widgetService.deleteWidget(
										$sessionStorage.userId,
										"avgRTPanelFlag");

								widgetResp.then(function(resp) {

									if (resp.data.Status === "Success") {
										$rootScope.widgetList.push({
											"key" : "avgRTPanelFlag",
											"value" : $scope.artTitle
										});
										$rootScope.avgRTPanelFlag = false;
										// $rootScope.$digest();
									}
								}, function(errorPayload) {

								});
							});

					$('.avgRtPanel').on('onFullScreen.lobiPanel',
							function(ev, lobiPanel) {

								$(".panel-body").css({
									"max-height" : "100%",
									"min-height" : "100%"
								});
								$("#avgrespTime").css({
									"width" : "100%",
									"height" : "600px"
								});
								if ($scope.tpsKPI) {
									drawValueComboChart();
								} else {
									drawValueChart();
								}
							});
					$('.avgRtPanel').on('onSmallSize.lobiPanel',
							function(ev, lobiPanel) {

								$(".panel-body").css({
									"max-height" : "315px",
									"min-height" : "315px"
								});
								$("#avgrespTime").css({
									"width" : "600px",
									"height" : "300px"
								});
								if ($scope.tpsKPI) {
									drawValueComboChart();
								} else {
									drawValueChart();
								}
							});

					google.charts.setOnLoadCallback(drawValueChart);

					var color = '#000'
					function drawValueChart() {

						$http.get("devops/rest/jmrtfpb").then(
										function(response) {


											var jsonresp = response.data;
											console.log(jsonresp);
											for ( var key in jsonresp) {
												console.log(key + ' is '
														+ jsonresp[key]);
												$scope.keys.push(key);
												$scope.values
														.push(jsonresp[key]);
											}

											var data = google.visualization
													.arrayToDataTable([
															[
																	'Test Number',
																	'Avg Resp Time(sec)',
																	{
																		role : 'style'
																	} ],
															[
																	$scope.keys[0],
																	parseFloat($scope.values[0]),
																	'#ff884d' ],
															[
																	$scope.keys[1],
																	parseFloat($scope.values[1]),
																	'#ff884d' ],
															[
																	$scope.keys[2],
																	parseFloat($scope.values[2]),
																	'#ff884d' ],
															[
																	$scope.keys[3],
																	parseFloat($scope.values[3]),
																	'#ff884d' ],
															[
																	$scope.keys[4],
																	parseFloat($scope.values[4]),
																	'#ff884d' ] ]);

											var options = {
												title : '',
												titleTextStyle : {
													color : '#999',
													fontName : 'Open Sans, Arial, Helvetica, sans-serif'
												},
												backgroundColor : 'white',
												timeline : {
													colorByRowLabel : false
												},
												legend : {
													position : 'none'
												},
												hAxis : {
													title : 'Test Number',

													titleTextStyle : {
														color : '#999',
														fontName : 'Open Sans, Arial, Helvetica, sans-serif',
														fontStyle : 'normal'
													},
													textStyle : {
														color : '#999',
														fontSize : 12,
														fontName : 'Open Sans, Arial, Helvetica, sans-serif',
														fontStyle : 'normal'
													},
													direction : 1,
													slantedText : true,
													slantedTextAngle : 20

												},

												vAxis : {
													title : 'Avg Resp Time(sec)',
													titleTextStyle : {
														color : '#999',
														fontName : 'Open Sans, Arial, Helvetica, sans-serif',
														fontSize : 12,
														italic : false
													},
													textStyle : {
														color : '#999',
														fontName : 'Open Sans, Arial, Helvetica, sans-serif',
														fontSize : 12,
														italic : false
													},
													gridlines : {
														color : 'transparent'
													}
												},

											};

											var chart = new google.visualization.ColumnChart(
													document
															.getElementById('avgrespTime'));
											chart.draw(data, options);
											
											$('#perfAvgRtLoadImg_id').hide();
											$('.perfAvgResp').show();

										});
					}

				});