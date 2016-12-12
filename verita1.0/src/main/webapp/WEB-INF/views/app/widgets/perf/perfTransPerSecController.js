mainApp
		.controller(
				'PerfTransPerSecController',
				function($scope, $sessionStorage, $rootScope, $http,
						widgetService) {

					$scope.tpsPTitle = 'Transaction Per Second Trend';

					$scope.tpsKPI = false;

					$scope.keys = [];
					$scope.values = [];
					// init();
					$('.transPSPanel').lobiPanel({
						// Options go here
						sortable : false,
						editTitle : false,
						close : false,
						expand : false,
						/* unpin : false, */
						reload : true,
						/*editTitle : {
							icon : 'fa fa-line-chart tpsKPI',
							icon2 : 'fa fa-bar-chart tpsChart',
							tooltip : 'Plot Trend Against KPI'
						},*/
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
						/*close : {
							icon : 'fa fa-times-circle'
						},
						expand : {
							icon : 'fa fa-expand',
							icon2 : 'fa fa-compress'
						}*/
					});

					$('.transPSPanel').on('onPin.lobiPanel',
							function(ev, lobiPanel) {
								window.console.log("Pinned");
								$(".epPanel-body").css({
									"max-height" : "315px",
									"min-height" : "315px"
								});
								$("#tpsChart").css({
									"width" : "600px",
									"height" : "300px"
								});
								drawValueChart();
							});

					$('.transPSPanel').on('onUnpin.lobiPanel',
							function(ev, lobiPanel) {
								lobiPanel.$options.expand = false;
								window.console.log("Unpinned");
								$(".transPSPanel").css({
									"left" : "150.5px",
									"top" : "122px",
									"height" : "80%",
									"width" : "80%"
								});
								$(".epPanel-body").css({
									"width" : "100%",
									"height" : "100%",
									"max-height" : "85%",
									"min-height" : "85%"
								});
								$("#tpsChart").css({
									"width" : "100%",
									"height" : "400px"
								});
								drawValueChart();
							});

					$(".tpsKPI").on("click", function() {
						// alert($(this).attr("class"));
						if ($(this).attr("class").indexOf("tpsKPI") >= 0) {
							drawValueComboChart();
						} else {
							drawValueChart();
						}
					});


					$('.transPSPanel .fa-refresh').on('click',
							function(ev, lobiPanel) {

								$('.perfTransPerSecTitle').hide();
								$('.perfTps').hide();
								$('#perfTransPerLoadImg_id').show();

								drawValueChart();
							});

					/* $('#perfTransPerLoadImg_id').show(); */

					/*
					 * $http.get("devops/rest/tpswb").then(function(response) {
					 * 
					 * $('#perfTransPerLoadImg_id').hide();
					 * 
					 * $('.perfTransPerSecTitle').show(); $('.perfTps').show();
					 * 
					 * var jsonresp = response.data; console.log(jsonresp); for (
					 * var key in jsonresp) { console.log(key + ' is ' +
					 * jsonresp[key]); $scope.keys.push(key);
					 * $scope.values.push(jsonresp[key]); }
					 * 
					 * 
					 * });
					 */

					google.charts.setOnLoadCallback(drawValueChart);
					var color = '#000'
					function drawValueChart() {
						$http.get("devops/rest/jmtpswb")
								.then(
										function(response) {
											$('#perfTransPerLoadImg_id').hide();

											$('.perfTransPerSecTitle').show();
											$('.perfTps').show();
											var jsonresp = response.data;

											for ( var key in jsonresp) {

												$scope.keys.push(key);
												$scope.values
														.push(jsonresp[key]);
											}

											// init();
											var data = google.visualization
													.arrayToDataTable([
															[ 'Test Number',
																	'Transaction Per Second' ],
															[
																	$scope.keys[0],
																	parseFloat($scope.values[0]) ],
															[
																	$scope.keys[1],
																	parseFloat($scope.values[1]) ],
															[
																	$scope.keys[2],
																	parseFloat($scope.values[2]) ],
															[
																	$scope.keys[3],
																	parseFloat($scope.values[3]) ],
															[
																	$scope.keys[4],
																	parseFloat($scope.values[4]) ] ]);

											var options = {
												title : '',
												titleTextStyle : {
													color : color,
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
													format : 'h:mm a',
													titleTextStyle : {
														color : '#999',
														fontName : 'Open Sans, Arial, Helvetica, sans-serif',
														fontSize : 12,
														fontStyle : 'normal'
													},
													textStyle : {
														color : '#999',
														fontName : 'Open Sans, Arial, Helvetica, sans-serif',
														fontSize : 12,
														fontStyle : 'normal'
													},
													direction : 1,
													slantedText : true,
													slantedTextAngle : 20

												},
												colors : [ '#ff884d', 'red' ],
												vAxis : {
													title : 'Transaction Per Second',
													titleTextStyle : {
														color : '#999',
														fontName : 'Open Sans, Arial, Helvetica, sans-serif',
														fontSize : 12,
														fontStyle : 'normal'
													},
													textStyle : {
														color : '#999',
														fontName : 'Open Sans, Arial, Helvetica, sans-serif',
														fontSize : 12,
														fontStyle : 'normal'
													},
													gridlines : {
														color : 'transparent'
													}
												},

											};

											var chart = new google.visualization.ColumnChart(
													document
															.getElementById('tpsChart'));
											chart.draw(data, options);

										});
					}
					/*
					 * function init() {
					 * $http.get("devops/rest/tpswb").then(function(response) {
					 * var jsonresp = response.data; console.log(jsonresp); for (
					 * var key in jsonresp) { console.log(key + ' is ' +
					 * jsonresp[key]); $scope.keys.push(key);
					 * $scope.values.push(jsonresp[key]); }
					 * 
					 * }); };
					 */
					/*google.charts.setOnLoadCallback(drawValueChart);
					var color = '#000'*/
					function drawValueComboChart() {
						var data = google.visualization.arrayToDataTable([
								[ 'Build Number', 'Transaction Per Second',
										'TPS' ],
								[ $scope.keys[0], parseFloat($scope.values[0]),
										0.210 ],
								[ $scope.keys[1], parseFloat($scope.values[1]),
										0.210 ],
								[ $scope.keys[2], parseFloat($scope.values[2]),
										0.210 ],
								[ $scope.keys[3], parseFloat($scope.values[3]),
										0.210 ],
								[ $scope.keys[4], parseFloat($scope.values[4]),
										0.210 ] ]);

						var options = {
							title : '',
							titleTextStyle : {
								color : color,
								fontName : 'Open Sans, Arial, Helvetica, sans-serif'
							},
							backgroundColor : 'white',
							timeline : {
								colorByRowLabel : false
							},
							legend : {
								position : 'none'
							},
							series : {
								1 : {
									type : 'line'
								}
							},
							hAxis : {
								title : 'Build Number',
								format : 'h:mm a',
								titleTextStyle : {
									color : color
								},
								textStyle : {
									color : color,
									fontSize : 10,
									fontName : 'Open Sans, Arial, Helvetica, sans-serif'
								},
								direction : 1,
								slantedText : true,
								slantedTextAngle : 20

							},
							colors : [ '#ff884d', 'red' ],
							vAxis : {
								title : 'Transaction Per Second',
								titleTextStyle : {
									color : color,
									fontName : 'Open Sans, Arial, Helvetica, sans-serif'
								},
								textStyle : {
									color : color,
									fontName : 'Open Sans, Arial, Helvetica, sans-serif'
								}
							},

						};

						var chart = new google.visualization.ColumnChart(
								document.getElementById('tpsChart'));

						chart.draw(data, options);
					}

				});