mainApp
		.controller(
				'PerfKPIController',
				function($scope, $sessionStorage, $rootScope, $http) {

					$scope.kpiTitle = 'KPI Status Since Last Build';

					$('.kpiPanel').lobiPanel({
						// Options go here
						sortable : false,
						// editTitle : false,
						/* unpin : false, */
						reload : true,
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
						/*close : {
							icon : 'fa fa-times-circle'
						},
						expand : {
							icon : 'fa fa-expand',
							icon2 : 'fa fa-compress'
						}*/
					});

					$('.kpiPanel').on('onPin.lobiPanel',
							function(ev, lobiPanel) {
								window.console.log("Pinned");
								$(".kctPanel-body").css({
									"max-height" : "315px",
									"min-height" : "315px"
								});
							});

					$('.kpiPanel').on('onUnpin.lobiPanel',
							function(ev, lobiPanel) {
								lobiPanel.$options.expand = false;
								window.console.log("Unpinned");
								$(".kpiPanel").css({
									"left" : "150.5px",
									"top" : "122px",
									"height" : "80%",
									"width" : "80%"
								});
								$(".kctPanel-body").css({
									"width" : "100%",
									"height" : "100%",
									"max-height" : "85%",
									"min-height" : "85%"
								});
							});

					$('.kpiPanel').on('beforeClose.lobiPanel',
							function(ev, lobiPanel) {
								$rootScope.kpiPanelFlag = false;
								$rootScope.$digest();
							});

					$('.kpiPanel').on('onFullScreen.lobiPanel',
							function(ev, lobiPanel) {

								$(".panel-body").css({
									"max-height" : "100%",
									"min-height" : "100%"
								});

							});
					$('.kpiPanel').on('onSmallSize.lobiPanel',
							function(ev, lobiPanel) {

								$(".panel-body").css({
									"max-height" : "315px",
									"min-height" : "315px"
								});

							});

					/*
					 * $('.kpiPanel .fa-refresh').on('click', function(ev,
					 * lobiPanel){ // console.log("onReload.lobiPanel");
					 * $('.perfKpiPanelContent').hide();
					 * $('.perfKpiTitle').hide();
					 * $('#perfJMKPILoadImg_id').show(); //drawKPI(); });
					 */

					$('#perfKPILoadImg_id').show();

					$http
							.get("devops/rest/kpflb")
							.then(
									function(response) {

										$scope.bulbColor = 'red';

										var jsonresp = response.data;

										$('#perfKPILoadImg_id').hide();
										$('.perfKpiPanelContent').show();

										$('.perfKpiTitle').show();

										var resTimeKpi = 1.2;
										var tpsTimeKpi = 0.20;
										var errorTimeKpi = 10.0;

										$scope.PrevrespTime = round(
												jsonresp.ReponseTime[0].Prevvalue,
												2);
										$scope.respTime = round(
												jsonresp.ReponseTime[0].currentvalue,
												2);

										$scope.Prevtps = round(
												jsonresp.TPS[0].Prevvalue, 2);
										$scope.tps = round(
												jsonresp.TPS[0].currentvalue, 2);

										$scope.Preverrors = round(
												jsonresp.Errors[0].Prevvalue, 2);
										$scope.errors = round(
												jsonresp.Errors[0].currentvalue,
												2);

										$scope.respTimeDiff = (jsonresp.ReponseTime[0].Prevvalue - jsonresp.ReponseTime[0].currentvalue)
												.toFixed(2);
										$scope.tpsTimeDiff = (jsonresp.TPS[0].Prevvalue - jsonresp.TPS[0].currentvalue)
												.toFixed(2);
										$scope.errorTimeDiff = (jsonresp.Errors[0].Prevvalue - jsonresp.Errors[0].currentvalue)
												.toFixed(2);

										if (jsonresp.ReponseTime[0].Prevvalue <= jsonresp.ReponseTime[0].currentvalue) {
											$scope.respcolor = "label-danger";
										} else {
											$scope.respcolor = "label-success";
										}

										if (jsonresp.TPS[0].Prevvalue > jsonresp.TPS[0].currentvalue) {
											$scope.tpscolor = "label-danger";
										} else {
											$scope.tpscolor = "label-success";
										}

										if (jsonresp.Errors[0].Prevvalue <= jsonresp.Errors[0].currentvalue) {
											$scope.errorcolor = "label-danger";
										} else {
											$scope.errorcolor = "label-success";
										}

										if ((jsonresp.ReponseTime[0].currentvalue <= resTimeKpi)
												&& (jsonresp.TPS[0].currentvalue >= tpsTimeKpi)
												&& (jsonresp.Errors[0].currentvalue <= errorTimeKpi)) {
											$scope.trafficClass = "label-success";
										} else {
											$scope.trafficClass = "label-danger";
										}

									});

					function round(value, exp) {
						if (typeof exp === 'undefined' || +exp === 0)
							return Math.round(value);

						value = +value;
						exp = +exp;

						if (isNaN(value)
								|| !(typeof exp === 'number' && exp % 1 === 0))
							return NaN;

						// Shift
						value = value.toString().split('e');
						value = Math
								.round(+(value[0] + 'e' + (value[1] ? (+value[1] + exp)
										: exp)));

						// Shift back
						value = value.toString().split('e');
						return +(value[0] + 'e' + (value[1] ? (+value[1] - exp)
								: -exp));
					}

				});