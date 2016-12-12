mainApp
		.controller(
				'PerfErrorPerTrendController',
				function($scope, $sessionStorage, $rootScope, $http) {
					
					$('#perfErrorPerTrendLoadImg_id').show();
					$('.perfErrorTrend').hide();

					$scope.errPTTitle = 'Error Percentage';

					$scope.tpsKPI = false;
					$scope.keys = [];
					$scope.values = [];

					$('.errPerTPanel').lobiPanel({
						// Options go here
						sortable : false,
						editTitle : false,
						close : false,
						expand : false,
						/* unpin : false, */
						reload : true,
						/*
						 * editTitle : { icon : 'fa fa-line-chart eptKPI', icon2 :
						 * 'fa fa-bar-chart eptChart', tooltip : 'Plot Trend
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

					$('.errPerTPanel').on('onPin.lobiPanel',
							function(ev, lobiPanel) {
								window.console.log("Pinned");
								$(".eptPanel-body").css({
									"max-height" : "315px",
									"min-height" : "315px"
								});
								$("#errPerTrendChart").css({
									"width" : "600px",
									"height" : "300px"
								});
								drawValueChart();
							});

					$('.errPerTPanel').on('onUnpin.lobiPanel',
							function(ev, lobiPanel) {
								lobiPanel.$options.expand = false;
								window.console.log("Unpinned");
								$(".errPerTPanel").css({
									"left" : "150.5px",
									"top" : "122px",
									"height" : "80%",
									"width" : "80%"
								});
								$(".eptPanel-body").css({
									"width" : "100%",
									"height" : "100%",
									"max-height" : "85%",
									"min-height" : "85%"
								});
								$("#errPerTrendChart").css({
									"width" : "100%",
									"height" : "400px"
								});
								drawValueChart();
							});

					$(".eptKPI").on("click", function() {

						if ($(this).attr("class").indexOf("eptKPI") >= 0) {
							$scope.tpsKPI = true;
							drawValueComboChart();
						} else {
							$scope.tpsKPI = false;
							drawValueChart();
						}
					});

					$('.errPerTPanel').on('beforeClose.lobiPanel',
							function(ev, lobiPanel) {
								$rootScope.eperTPanelFlag = false;
								$rootScope.$digest();
							});

					$('.errPerTPanel').on('onFullScreen.lobiPanel',
							function(ev, lobiPanel) {

								$(".panel-body").css({
									"max-height" : "100%",
									"min-height" : "100%"
								});
								$("#errPerTrendChart").css({
									"width" : "900px",
									"height" : "600px"
								});
								if ($scope.tpsKPI) {
									drawValueComboChart();
								} else {
									drawValueChart();
								}
							});
					$('.errPerTPanel').on('onSmallSize.lobiPanel',
							function(ev, lobiPanel) {

								$(".panel-body").css({
									"max-height" : "315px",
									"min-height" : "315px"
								});
								$("#errPerTrendChart").css({
									"width" : "600px",
									"height" : "300px"
								});
								if ($scope.tpsKPI) {
									drawValueComboChart();
								} else {
									drawValueChart();
								}
							});

					$('.errPerTPanel .fa-refresh').on('click',
							function(ev, lobiPanel) {

								$('.perfErrorTrend').hide();
								$('.errorTrendTitle').hide();
								$('#perfErrorPerTrendLoadImg_id').show();
								if ($scope.tpsKPI) {
									drawValueComboChart();
								} else {
									drawValueChart();
								}
							});


					google.charts.setOnLoadCallback(drawValueChart);

					var color = '#000'
					function drawValueChart() {

						$('#perfErrorPerTrendLoadImg_id').hide();
						$('.perfErrorTrend').show();

						$http
								.get("devops/rest/jmept")
								.then(
										function(response) {

											var jsonresp = response.data;

											for ( var key in jsonresp) {

												$scope.keys.push(key);
												$scope.values
														.push(jsonresp[key]);
											}

											var data = google.visualization
													.arrayToDataTable([
															[ 'Test Number',
																	'Error Perc' ],
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
													title : 'Build Number',

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
													title : 'Error Rate(%)',
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

											var chart = new google.visualization.LineChart(
													document
															.getElementById('errPerTrendChart'));

											chart.draw(data, options);

										});
					}

					/*
					 * function drawValueComboChart() { var data =
					 * google.visualization.arrayToDataTable([ [ 'Build No',
					 * 'Error Perc', 'Errors' ], [ $scope.keys[0],
					 * parseFloat($scope.values[0]), 10.0 ], [ $scope.keys[1],
					 * parseFloat($scope.values[1]), 10.0 ], [ $scope.keys[2],
					 * parseFloat($scope.values[2]), 10.0 ], [ $scope.keys[3],
					 * parseFloat($scope.values[3]), 10.0 ], [ $scope.keys[4],
					 * parseFloat($scope.values[4]), 10.0 ] ]);
					 * 
					 * var options = {
					 * 
					 * title : '', titleTextStyle : { color : color, },
					 * backgroundColor : 'white', timeline : { colorByRowLabel :
					 * false }, legend : { position : 'none' }, colors : [
					 * '#3399ff', 'red' ], hAxis : { title : 'Build Number',
					 * 
					 * titleTextStyle : { color : color }, textStyle : { color :
					 * color, fontSize : 10 }, direction : 1, slantedText :
					 * true, slantedTextAngle : 20
					 *  },
					 * 
					 * vAxis : { title : 'Error Rate(%)', titleTextStyle : {
					 * color : color }, textStyle : { color : color } }, };
					 * 
					 * var chart = new google.visualization.LineChart(document
					 * .getElementById('errPerTrendChart'));
					 * 
					 * chart.draw(data, options); }
					 */

				});