mainApp
		.controller(
				'smDefectSeverityController',
				function($scope, $sessionStorage, $rootScope, $http,
						widgetService) {

					$('.smDefectSeverityPanel').lobiPanel({
						// Options go here
						sortable : false,
						// editTitle : false,
						unpin : {
							icon : 'fa fa-thumb-tack'
						},
						reload : true,
						editTitle : false,
						minimize : false,
						close : false,
						expand : false,
						/*
						 * minimize : { icon : 'fa fa-chevron-up', icon2 : 'fa
						 * fa-chevron-down' },
						 */
						reload : {
							icon : 'fa fa-refresh',

						},
					/*
					 * close : { icon : 'fa fa-times-circle' }, expand : { icon :
					 * 'fa fa-expand', icon2 : 'fa fa-compress' }
					 */
					});

					$('.smDefectSeverityPanel').on('onPin.lobiPanel',
							function(ev, lobiPanel) {

								$(".panel-body").css({
									"max-height" : "315px",
									"min-height" : "315px"
								});
								$("#smDSIChart").css({
									"width" : "600px",
									"height" : "300px"
								});
								drawValueChart();
							});

					$('.smDefectSeverityPanel').on('onUnpin.lobiPanel',
							function(ev, lobiPanel) {
								lobiPanel.$options.expand = false;

								$(".smDefectSeverityPanel").css({
									"left" : "150.5px",
									"top" : "122px",
									"height" : "80%",
									"width" : "80%"
								});
								$(".defectSevPanel-body").css({
									"width" : "100%",
									"height" : "100%",
									"max-height" : "85%",
									"min-height" : "85%"
								});
								$("#smDSIChart").css({
									"width" : "100%",
									"height" : "400px"
								});
								drawValueChart();
							});

					/*
					 * $('.smDefectSeverityPanel').on('beforeClose.lobiPanel',
					 * function(ev, lobiPanel) { var widgetResp =
					 * widgetService.deleteWidget(
					 * 
					 * $sessionStorage.userId, "rmDSIFlag");
					 * 
					 * widgetResp.then(function(resp) {
					 * 
					 * if (resp.data.Status === "Success") {
					 * $rootScope.tpsPanelFlag = false;
					 * $rootScope.widgetList.push({ "key" : "rmDSIFlag", "value" :
					 * $scope.sDSITitle }); $rootScope.rmDSIFlag = false; } },
					 * function(errorPayload) {
					 * 
					 * }); });
					 * 
					 * $('.smDefectSeverityPanel').on('onFullScreen.lobiPanel',
					 * function(ev, lobiPanel) {
					 * 
					 * $(".panel-body").css({ "max-height" : "100%",
					 * "min-height" : "100%" }); $("#smDSIChart").css({ "width" :
					 * "900px", "height" : "600px" }); drawValueChart(); });
					 * $('.smDefectSeverityPanel').on('onSmallSize.lobiPanel',
					 * function(ev, lobiPanel) {
					 * 
					 * $(".panel-body").css({ "max-height" : "300px",
					 * "min-height" : "300px" }); $("#smDSIChart").css({ "width" :
					 * "370px", "height" : "200px" }); drawValueChart(); });
					 */

					$('.smDefectSeverityPanel .fa-refresh').on('click',
							function(ev, lobiPanel) {

								$('.smDSI').hide();
								$('.smDsiTitle').hide();
								$('#defectSeverityIndex_id').show();
								drawValueChart();
							});

					google.charts.setOnLoadCallback(drawValueChart);

					$('#defectSeverityIndex_id').show();

					function drawValueChart() {

						$http
								.get("devops/rest/smdsi")
								.then(
										function(response) {

											$('#defectSeverityIndex_id').hide();
											$('.smDSI').show();
											$('.smDsiTitle').show();

											var jsonresp = response.data;
											var defectSeverityIndexMap = jsonresp.defectSeverityIndexMap;

											var keys = [];
											var values = [];
											for ( var key in defectSeverityIndexMap) {

												keys.push(key);
												values
														.push(defectSeverityIndexMap[key]);
											}
											var data = google.visualization
													.arrayToDataTable([
															[ 'Element', '', {
																role : 'style'
															} ],
															[
																	keys[0],
																	parseFloat(values[0]),
																	'#ff884d' ],
															[
																	keys[1],
																	parseFloat(values[1]),
																	'#ffaa80' ],
															[
																	keys[2],
																	parseFloat(values[2]),
																	'#ffbb99' ],
															[
																	keys[3],
																	parseFloat(values[3]),
																	'#ffddcc' ] ]);

											$scope.sDSITitle = jsonresp.meanTotal;

											drawColumnChartfinal(data,

											'Application Name', 'Scale 0 to 5',
													'100%', '100%',
													'smDSIChart');

										});
					}

					function drawColumnChartfinal(data, hAxisTitle, vAxisTitle,
							height, width, divContainer) {
						var color = "#000000";
						var options = {
							/* title : garphTitle, */
							titleTextStyle : {
								color : '#999',
								fontName : 'Open Sans, Arial, Helvetica, sans-serif',
								fontSize : 12,
								italic : false
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
							hAxis : {
								title : hAxisTitle,
								format : 'h:mm a',
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
								direction : 1,
								slantedText : true,
								slantedTextAngle : 20

							},

							vAxis : {
								title : vAxisTitle,
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
								document.getElementById(divContainer));

						chart.draw(data, options);
					}

				});