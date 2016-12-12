mainApp
		.controller(
				'PredictController',
				function($scope, $http) {

					$scope.respTime = null;
					$scope.respTimeValue;

					$scope.getRTime = function() {
						$http
								.get("devops/rest/respTime/" + $scope.respTime)
								.then(
										function(response) {
											var jsonresp = response.data;
											console.log("get Predctive====");
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
						var data = new google.visualization.DataTable();
						data.addColumn('number', 'X');
						data.addColumn('number', 'Actual');
						data.addColumn('number', 'Predict');

						data.addRows([ [ 500, 84.02, 83.32 ], [ 800, 84.27, 83.32 ],
								[ 1000, 84.49, 83.85 ], [ 2000, 85.51, 84.89 ],
								[ 5000, 89.06, 87.96 ] ]);

						var options = {
							legend : {
								position : 'bottom',
							},
							hAxis : {
								title : ''
							},
							vAxis : {
								title : 'Response Time in MS'
							},
							series : {
								1 : {
									curveType : 'function'
								}
							}
						};

						var chart = new google.visualization.LineChart(document
								.getElementById('predictChart_div'));
						chart.draw(data, options);
					}
				});