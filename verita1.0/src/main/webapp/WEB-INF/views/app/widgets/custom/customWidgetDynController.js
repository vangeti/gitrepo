mainApp
		.controller(
				'customWidgetDynController',
				function($scope, $sessionStorage, $rootScope, widgetService,
						$http) {

					$('.customWidgetPanel').lobiPanel({
						// Options go here
						sortable : false,
						// editTitle : false,
						unpin : true,
						reload : false,
						editTitle : false,
						minimize : false,
						close : false,
						expand : false,
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

					$http
							.get(
									"/getWidgetDetails.html?user_id=1&project_name=Geo")
							.then(
									function(response) {

										var jsonData;
										var jsonresp;

										jsonresp = response.data

										$http
												.get(jsonresp[0].source_url)
												.then(
														function(responseInner) {
															jsonData = responseInner.data.Defects;
															buildGraph(
																	jsonresp[0].chart_type,
																	'#custWidget',
																	jsonData,
																	jsonresp[0].x_axis_title,
																	jsonresp[0].y_axis_title,
																	jsonresp[0].aggr_func,
																	'',
																	'',
																	jsonresp[0].x_axis_title,
																	jsonresp[0].y_axis_title);

														});

									});

					/*
					 * $('.jiraPanel').on('beforeClose.lobiPanel', function(ev,
					 * lobiPanel) { $rootScope.jiraFlag = false;
					 * $rootScope.$digest(); });
					 * 
					 * $('.jiraPanel').on('beforeClose.lobiPanel', function(ev,
					 * lobiPanel) { var widgetResp = widgetService.deleteWidget(
					 * $sessionStorage.userId, "jiraFlag");
					 * 
					 * widgetResp.then(function(resp) {
					 * 
					 * if (resp.data.Status === "Success") { $rootScope.jiraFlag =
					 * false; $rootScope.widgetList.push({ "key":"jiraFlag",
					 * "value":$scope.jiraTitle }); $rootScope.jiraFlag = false; } },
					 * function(errorPayload) { console.log(errorPayload); });
					 * });
					 */

					/*
					 * $('.jiraPanel').on('onFullScreen.lobiPanel', function(ev,
					 * lobiPanel) {
					 * 
					 * 
					 * $(".panel-body").css({ "max-height" : "100%",
					 * "min-height" : "100%" }); $("#featureWidget").css({
					 * "width" : "100%", "height" : "600px" });
					 * drawValueChart();
					 * 
					 * });
					 * 
					 * 
					 * 
					 * $('.jiraPanel').on('onSmallSize.lobiPanel', function(ev,
					 * lobiPanel) {
					 * 
					 * $(".panel-body").css({ "max-height" : "300px",
					 * "min-height" : "300px" }); $("#featureWidget").css({
					 * "width" : "400px", "height" : "300px" });
					 * drawValueChart(); });
					 */

					/*
					 * $('.customWidgetPanel').on('onPin.lobiPanel',
					 * function(ev, lobiPanel) {
					 * $(".customWidgetPanelBody").css({ "max-height" : "315px",
					 * "min-height" : "315px" }); $("#custWidget").css({ "width" :
					 * "600px", "height" : "300px" }); drawValueChart(); });
					 */

					/*
					 * $('.customWidgetPanel').on('onUnpin.lobiPanel',
					 * function(ev, lobiPanel) { lobiPanel.$options.expand =
					 * false; window.console.log("Unpinned");
					 * $(".customWidgetPanel").css({ "left" : "150.5px", "top" :
					 * "122px", "height" : "80%", "width" : "80%" });
					 * $(".customWidgetPanelBody").css({ "width" : "100%",
					 * "height" : "100%", "max-height" : "85%", "min-height" :
					 * "85%" }); $("#custWidget").css({ "width" : "100%",
					 * "height" : "400px" }); drawValueChart(); });
					 */

					// $('.jiraPanel').on('loaded.lobiPanel', function(ev,
					// lobiPanel){
					/*
					 * $('.customWidgetPanel .fa-refresh').on('click',
					 * function(ev, lobiPanel) { $('.custWidget').hide();
					 * $('#custWidgetLoadImg_id').show(); drawValueChart(); });
					 * 
					 * google.charts.setOnLoadCallback(drawValueChart); var
					 * color = '#000'
					 * 
					 * $('#jiraFeatureLoadImg_id').show();
					 * 
					 * function drawValueChart() { console.log('in widget ctl');
					 * $http .get(
					 * "/devops_admin/getWidgetDetails.html?user_id=1&project_name=Geo")
					 * .then( function(response) {
					 * 
					 * $('#custWidgetLoadImg_id').hide();
					 * 
					 * $('.custWidget').show();
					 * 
					 * var jsonresp = response.data; console
					 * .log('-------------------------------');
					 * console.log(jsonresp[0]); var data = google.visualization
					 * .arrayToDataTable([ [ 'Task', 'Hours per Day' ], [
					 * jsonresp[0].x_axis_title, parseInt(jsonresp[0].x_axis) ], [
					 * jsonresp[0].y_axis_title, parseInt(jsonresp[0].y_axis) ]
					 * ]);
					 * 
					 * var featureTitle = jsonresp[0].projectname;
					 * drawPieChart(data, featureTitle, 'custWidget');
					 * 
					 * }); }
					 */

					/*
					 * function drawPieChart(data, featureTitle, divContainer) {
					 * var options = { title : featureTitle,
					 * 
					 * titleTextStyle : { color : '#999', fontName : 'Open Sans,
					 * Arial, Helvetica, sans-serif', fontSize : 12, italic :
					 * false }, legend : { textStyle : { color : '#999',
					 * fontName : 'Open Sans, Arial, Helvetica, sans-serif',
					 * fontSize : 12, italic : false } },
					 * 
					 * is3D : true, color : '#999' };
					 * 
					 * var colors = { colors : [ '#ff884d', '#ffbb99', '#ffaa80' ] };
					 * $.extend(options, colors);
					 * 
					 * var chart = new google.visualization.PieChart(document
					 * .getElementById(divContainer)); chart.draw(data,
					 * options); }
					 */

					function buildGraph(chartType, chartId, dataSet, dimension,
							group, aggrFunc, groupBy, total_records_id, xLabel,
							yLabel) {

						/*
						 * var dimDataType = checkDateType(dataSet, dimension);
						 * if(dimDataType == 'Date') { var dateFormat =
						 * d3.time.format("%Y-%m-%d");
						 * dataSet.forEach(function(d) { d[dimension] =
						 * dateFormat.parse(d[dimension]);
						 * d[dimension].setDate(1); }); }
						 */
						dimDataType = '';

						var chart = getChartInstance(chartType, chartId);

						var ndx = crossfilter(dataSet);

						if (dimension == "") {
							/*$("#errorMsg").text("Please select x-axis");*/
							return false;
						}

						var objDim = ndx.dimension(function(d) {
							return d[dimension];
						});

						if (aggrFunc == 'count') {
							var objGroup = objDim.group();
						} else if (group != "") {
							var objGroup = objDim.group().reduceSum(
									function(d) {
										return d[group];
									});
						} else {
							/*$("#errorMsg")
									.text(
											"Please select aggregate function or y-axis to plot the graph");*/
							return false;
						}

						if (groupBy != '') {
							var groupByDim = ndx.dimension(function(d) {
								return d[groupBy];
							});
							var groupByGroup = groupByDim.group();
							var groups = groupByGroup.top(Infinity);

							groups.sort(function(a, b) {
								return a.key - b.key;
							});

							var groupGroups = [];
							var sVals = [];
							groups.forEach(function(item, index) {
								var sVal = groups[index].key;
								sVals.push(sVal);
								groupGroups[index] = objDim.group().reduceSum(
										function(d) {
											if (d[groupBy] == sVal) {
												return true;
											} else {
												return false;
											}
										});
							});
						}

						// console.log(objGroup.top(Infinity));
						// console.log(groupByGroup.top(Infinity));

						if (aggrFunc == "count" || group != "") {
							var objGroups = objGroup.top(Infinity);
							if (objGroups.length > 0) {
								objGroups
										.forEach(function(item, index) {
											var sVal = objGroups[index].value;
											if (!sVal) {
												/*$("#errorMsg")
														.text(
																"Please select appropriate values to plot the graph");*/
												return false;
											} else {
												/*$("#errorMsg").text("");*/
												return true;
											}
										});
							}
						} else if (groupBy != '') {
							groups
									.forEach(function(item, index) {
										var sVal = groups[index].key;
										if (!sVal) {
											/*$("#errorMsg")
													.text(
															"Please select appropriate values to plot the graph");*/
											return false;
										} else {
											/*$("#errorMsg").text("");*/
											return true;
										}
									});
						}

						var all = ndx.groupAll();
						if (dimDataType == 'Date') {
							var minDate = objDim.bottom(1)[0][dimension];
							var maxDate = objDim.top(1)[0][dimension];
						}

						/*
						 * var totalRecords =
						 * dc.numberDisplay(total_records_id);
						 * totalRecords.formatNumber(d3.format("d"))
						 * .valueAccessor(function(d) { return d; }).group(all);
						 */

						chart.width(500).height(300);

						if (chartType != 'pieChart') {
							// .margins({top: 10, right: 50, bottom: 30, left:
							// 50})
							chart.margins({
								top : 20,
								right : 10,
								bottom : 80,
								left : 50
							});
						}

						chart.dimension(objDim);

						if (groupBy != '') {
							for (var i = 0; i < groupGroups.length; i++) {
								if (i == 0) {
									chart.group(groupGroups[i], sVals[i]);
								} else {
									chart.stack(groupGroups[i], sVals[i]);
								}
							}

							chart.colors(d3.scale.ordinal().domain(sVals)
									.range(
											[ "#ff1a1a", "#ff4d4d", "#ff8080",
													"#ffb3b3" ]));

							chart.title(function(d) {
								var g = this.layer;
								return d.key + ' - ' + groupBy + '-'
										+ this.layer + ': ' + d.value;
							});

						} else if (aggrFunc == "count" || group != "") {
							chart.group(objGroup);
						}

						if (chartType == 'lineChart') {
							chart.renderArea(true);
						}

						if (chartType != "pieChart") {
							if (dimDataType == 'Date') {
								chart.x(d3.time.scale().domain(
										[ minDate, maxDate ]));
								// chart.x(d3.time.scale().domain(dc.filters.RangedFilter(minDate,
								// maxDate)));
								chart.brushOn(false);

								chart.on('renderlet', function(chart) {
									chart.selectAll("g.x text").attr('dx',
											'-30').attr('dy', '0').attr(
											'transform', "rotate(-55)");
								});
							} else {
								chart.x(d3.scale.ordinal().domain(objDim))
										.xUnits(dc.units.ordinal);
							}

							chart.transitionDuration(500).elasticY(true)
									.elasticX(true).renderHorizontalGridLines(
											true).renderVerticalGridLines(true)
									.yAxisLabel(yLabel).xAxisLabel(xLabel);
						}

						if (chartType == "pieChart") {
							chart
									// .slicesCap(4)
									.innerRadius(50)
									.legend(dc.legend())
									.on(
											'renderlet',
											function(chart) {
												chart
														.selectAll(
																'text.pie-slice')
														.text(
																function(d) {
																	var percentage = dc.utils
																			.printSingleValue((d.endAngle - d.startAngle)
																					/ (2 * Math.PI)
																					* 100)
																			+ '%';
																	var value = d.data.key
																			+ ' '
																			+ percentage;
																	return value;
																})
											});

						}
						if (chartType == 'barChart'
								|| chartType == 'stackedChart') {
							chart.barPadding(0.4).outerPadding(0.2);
							// chart.yAxis().tickFormat(d3.format("d"));
						}

						dc.renderAll();
						//chart.render();
					}

					function getChartInstance(chartType, chartId) {
						var chartInst;
						if (chartType == 'lineChart') {
							chartInst = dc.lineChart(chartId);
						} else if (chartType == 'barChart') {
							chartInst = dc.barChart(chartId);
						} else if (chartType == 'stackedChart') {
							chartInst = dc.barChart(chartId);
						} else if (chartType == 'pieChart') {
							chartInst = dc.pieChart(chartId);
						}
						return chartInst;
					}

				});