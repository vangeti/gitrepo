mainApp.controller('aggregateReportControllerCompare',
				function($scope, $sessionStorage, $rootScope, $http,
						widgetService) {
					var regex = ".Transaction";
					var clickedTabValue = "";
					$scope.tdTitle = 'Aggregate Report';
					$scope.test_to_nameToDisplay = '';
					$scope.testwith_nameToDisplay = '';

					$("#requests").hide();
					$('.aggregateReportPanel').lobiPanel({
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
						 * minimize : { icon : 'fa fa-chevron-up', icon2 : 'fa
						 * fa-chevron-down' },
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

					$('.aggregateReportPanel').on(
							'onPin.lobiPanel',
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
								drawTable($sessionStorage.test_to_number,
										$sessionStorage.testwith_number);
							});

					$('.aggregateReportPanel').on(
							'onUnpin.lobiPanel',
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
								drawTable($sessionStorage.test_to_number,
										$sessionStorage.testwith_number);
							});

					$('.aggregateReportPanel').on(
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

					$('.aggregateReportPanel').on(
							'onFullScreen.lobiPanel',
							function(ev, lobiPanel) {
								$('#aggrprtLoadImgcmore_id').show();
								window.console.log("value full event called");
								$(".panel-body").css({
									"max-height" : "100%",
									"min-height" : "94%"
								});
								$(".divScroll1").css({
									"height" : "520px",
									"overflow-x" : "auto",
									"overflow-y" : "auto",
									"max-height" : "100%",
									"min-height" : "100%"
								});
								drawTable($sessionStorage.test_to_number,
										$sessionStorage.testwith_number);
							});
					$('.aggregateReportPanel').on(
							'onSmallSize.lobiPanel',
							function(ev, lobiPanel) {
								$('#aggrprtLoadImgcmore_id').show();
								window.console.log("value small event called");
								$(".panel-body").css({
									"max-height" : "300px",
									"min-height" : "300px"
								});
								$(".divScroll1").css({
									"height" : "252px",
									"overflow-x" : "auto",
									"overflow-y" : "auto",
									"width" : "100%"
								});
								drawTable($sessionStorage.test_to_number,
										$sessionStorage.testwith_number);
							});
					$("#compare").click(
									function() {
										/*
										 * drawTable(testto,testwith);
										 * drawTable(testto,testwith);
										 */
										$scope.test_to_nameToDisplay = $sessionStorage.test_to_nameToDisplay;
										$scope.testwith_nameToDisplay = $sessionStorage.testwith_nameToDisplay;
										drawTable(
												$sessionStorage.test_to_number,
												$sessionStorage.testwith_number);
										$(".divScroll").css({
											"height" : "209px",
											"overflow-x" : "auto",
											"overflow-y" : "auto",
											"width" : "99%"
										});
									});
					$scope.tabbed = function(value) {
						// $scope.aggList;
						if (value == "transactions") {
							clickedTabValue = "Transactions";
							var found_names = $.grep($scope.aggListbackupCompare,
									function(v) {
										if (v.transactionNameTest1.indexOf(regex) !== -1) {
											return v;
										}
									});
							$scope.aggListCompare = found_names;
						} else {
							clickedTabValue = "Requests";
							var found_names = $.grep($scope.aggListbackupCompare,
									function(v) {
										if (v.transactionNameTest1
												.indexOf(regex) == -1) {
											return v;
										}
									});
							$scope.aggListCompare = found_names;
						}
					};

					/* $('#perfTestDetailsLoadImg_id').show(); */
					function drawTable(testto, testwith) {
						$('#aggrprtLoadImgcmore_id').show();
						$('#hideagg').hide();
						var jsonresp1 = new Array();
						var jsonresp2;
						$http.get("lennox/rest/aggregateReport/live?testId="+ testwith).then(
										function(response) {
											$('#aggrprtLoadImgcmore_id').hide();
											$('#hideagg').show();
											$('#hpsLoadImg_id').hide();
											$('#test_id').hide();
											$('.perfTestDetailsTitle').show();
											$('.perfTestDetailsTable').show();
											jsonresp1 = response.data;//testWith
											$http.get("lennox/rest/aggregateReport/live?testId="+ testto).then(
															function(response) {
																jsonresp2 = response.data;//testTo
																var jsonresp = [];
																for (var i = 0; i < jsonresp2.length; i++) {
																	var object2 = jsonresp2[i];//testTo
																	var object1 = jsonresp1[i];//testWith
																	var transactionname = "";
																	try {
																		if (object1.transactionName != undefined) {
																			transactionname = object1.transactionName;
																		}
																	} catch (err) {
																		transactionname = object2.transactionName;
																	}
																	jsonresp.push({
																				"transactionNameTest1" : transactionname,
																				"countTest1" : object1.count,
																				"avgTest1" : object1.avg,
																				"aggregate_report_90_lineTest1" : object1.aggregate_report_90_line,
																				"aggregate_report_minTest1" : object1.aggregate_report_min,
																				"aggregate_report_maxTest1" : object1.aggregate_report_max,
																				"aggregate_report_errorTest1" : object1.aggregate_report_error,
																				
																				"countTest2" : object2.count,
																				"avgTest2" : object2.avg,
																				"aggregate_report_90_lineTest2" : object2.aggregate_report_90_line,
																				"aggregate_report_minTest2" : object2.aggregate_report_min,
																				"aggregate_report_maxTest2" : object2.aggregate_report_max,
																				"aggregate_report_errorTest2" : object2.aggregate_report_error,
																				
																				"diffAvg" : object1.avg - object2.avg,
																				"diffAvgPercentage" : object1.aggregate_report_error - object2.aggregate_report_error
																			});
																}
																$scope.aggListCompare = jsonresp;
																$scope.backupListCompare = jsonresp;
																$scope.aggListbackupCompare = jsonresp;
																if (clickedTabValue === "Transactions" || clickedTabValue === "") {
																	$scope.aggListCompare = $.grep($scope.aggListbackupCompare,
																					function(v) {
																						if (v.transactionNameTest1.indexOf(regex) !== -1) {
																							return v;
																						}
																					});
																} else {
																	$scope.aggListCompare = $.grep($scope.aggListbackupCompare,
																					function(v) {
																						if (v.transactionNameTest1.indexOf(regex) == -1) {
																							return v;
																						}
																					});
																}
															});

										});

					}

				});