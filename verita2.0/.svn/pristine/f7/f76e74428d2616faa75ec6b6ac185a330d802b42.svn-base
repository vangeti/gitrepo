mainApp
		.controller(
				'perfCpuUtilizationPredController',
				function($scope, $http) {

					$scope.respTime = null;
					$scope.respTimeValue;

					$scope.getRTime = function() {
						$http
								.get("devops/rest/perfcpuutilization")
								.then(
										function(response) {
											var jsonresp = response.data;
											
											console.log(jsonresp);
											console
													.log(jsonresp.Results.predicted_cpu_usage.value.Values[0][0]);
											$scope.respTimeValue = jsonresp.Results.predicted_cpu_usage.value.Values[0][0];
											$scope.respTimeValue = Math
													.round($scope.respTimeValue * 100) / 100;
										});
					}
					
					google.charts.setOnLoadCallback(drawCurveTypes);

					function drawCurveTypes() {
						
						$http.get("devops/rest/perfcpuutilization").then(
								function(response) {
									var jsonresp = response.data;
									console.log("cpu time: :"+jsonresp[0].actual);
									alert(jsonresp[0].actual);
									$scope.perfCpuPredDetails = jsonresp;
									
						var data = new google.visualization.DataTable();
						data.addColumn('number', 'Calls Per Minute');
						data.addColumn('number', 'Actual CPU Usage');
						data.addColumn('number', 'Predicted CPU Usage');

						data.addRows([ [ parseInt(jsonresp[0].callsPerminute), parseInt(jsonresp[0].actual), parseInt(jsonresp[0].prediction) ],
						               [ parseInt(jsonresp[1].callsPerminute), parseInt(jsonresp[1].actual), parseInt(jsonresp[1].prediction) ],
						               [ parseInt(jsonresp[2].callsPerminute), parseInt(jsonresp[2].actual), parseInt(jsonresp[2].prediction) ],
						               [ parseInt(jsonresp[3].callsPerminute), parseInt(jsonresp[3].actual), parseInt(jsonresp[3].prediction) ],
						               [ parseInt(jsonresp[4].callsPerminute), parseInt(jsonresp[4].actual), parseInt(jsonresp[4].prediction) ]
								 ]);

						var options = {
							legend : {
								position : 'bottom',
							},
							hAxis : {
								title : 'Calls Per Minute'
							},
							vAxis : {
								title : 'Percentage (%)'
							},
							series : {
								1 : {
									curveType : 'function'
								}
							}
						};
						var chart = new google.visualization.LineChart(document
								.getElementById('CPUpredictChart_div'));
						chart.draw(data, options);
					});

						
					}
					
				});
					
					