mainApp
		.controller(
				'PerfKPIController',
				function($scope, $sessionStorage, $rootScope, $http) {

					$scope.kpiTitle = 'KPI Status Since Last Build';
					
					$('#perfKPILoadImg_id').show();
					$('.perfKpiPanelContent').hide();

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

					$('#perfKPILoadImg_id').show();
					
					$http.get("devops/rest/jmkpflb").then(function(response) {
						
						$('#perfKPILoadImg_id').hide();
						$('.perfKpiPanelContent').show();
						
						$scope.bulbColor = 'red';
						
						var jsonresp = response.data;

						var resTimeKpi=1.2;
						var tpsTimeKpi=0.20;
						var errorTimeKpi=10.0;
						
						console.log('KPIii');
						console.log(jsonresp);

						$scope.PrevrespTime=(jsonresp.ReponseTime[0].currentvalue).toFixed(1);
						$scope.respTime= (jsonresp.ReponseTime[0].Prevvalue).toFixed(1);
						
						$scope.Prevtps=jsonresp.TPS[0].Prevvalue;
						$scope.tps=jsonresp.TPS[0].currentvalue;
						
				//		$scope.Prevtps=0.3;
		//				$scope.tps=0.2;
						
						$scope.Preverrors=jsonresp.Errors[0].Prevvalue;
						$scope.errors=jsonresp.Errors[0].currentvalue;
						
						if(jsonresp.ReponseTime[0].Prevvalue>jsonresp.ReponseTime[0].currentvalue)
							{
							$scope.respTimeDiff=(jsonresp.ReponseTime[0].Prevvalue-jsonresp.ReponseTime[0].currentvalue).toFixed(1);
							}
						else{
							$scope.respTimeDiff=(jsonresp.ReponseTime[0].currentvalue-jsonresp.ReponseTime[0].Prevvalue).toFixed(1);	
						}
						//$scope.respTimeDiff=
						$scope.tpsTimeDiff=($scope.Prevtps-$scope.tps).toFixed(1);
						if(jsonresp.Errors[0].Prevvalue>jsonresp.Errors[0].currentvalue)
							{
							$scope.errorTimeDiff=(jsonresp.Errors[0].Prevvalue-jsonresp.Errors[0].currentvalue).toFixed(1);
							}else
								{
								$scope.errorTimeDiff=(jsonresp.Errors[0].currentvalue-jsonresp.Errors[0].Prevvalue).toFixed(1);	
								}

						if( jsonresp.ReponseTime[0].currentvalue>=jsonresp.ReponseTime[0].Prevvalue){
							$scope.respcolor="label-success";
						}else{
							$scope.respcolor="label-danger";
						}
						
						/*if(jsonresp.TPS[0].currentvalue> jsonresp.TPS[0].Prevvalue){
							$scope.tpscolor="label-danger";
						}else{
							$scope.tpscolor="label-success";
						}*/
						
						if($scope.tps> $scope.Prevtps){
							$scope.tpscolor="label-success";
						}else{
							$scope.tpscolor="label-danger";
						}
						
						if( jsonresp.Errors[0].currentvalue>=jsonresp.Errors[0].Prevvalue){
							$scope.errorcolor="label-success";
						}else{
							$scope.errorcolor="label-danger";
						}
						
						
						if( (jsonresp.ReponseTime[0].Prevvalue <= resTimeKpi) /*&& (jsonresp.TPS[0].currentvalue >= tpsTimeKpi) */&& (jsonresp.Errors[0].Prevvalue <= errorTimeKpi)){
							$scope.trafficClass="label-success";
						}else{
							$scope.trafficClass="label-danger";
						}
						
					});
					

					
					function round(value, exp) {
						  if (typeof exp === 'undefined' || +exp === 0)
						    return Math.round(value);

						  value = +value;
						  exp = +exp;

						  if (isNaN(value) || !(typeof exp === 'number' && exp % 1 === 0))
						    return NaN;

						  // Shift
						  value = value.toString().split('e');
						  value = Math.round(+(value[0] + 'e' + (value[1] ? (+value[1] + exp) : exp)));

						  // Shift back
						  value = value.toString().split('e');
						  return +(value[0] + 'e' + (value[1] ? (+value[1] - exp) : -exp));
						}

				});