var mainApp = angular.module("mainApp", [ 'ui.router', 'ngStorage',
		'ui.bootstrap', 'angularUtils.directives.uiBreadcrumbs' ]);

mainApp
		.config(function($stateProvider, $urlRouterProvider) {

			$urlRouterProvider.otherwise('/');

			$stateProvider
					.state('home', {
						url : '/',
						views : {
							'@' : {
								templateUrl : 'views/layouts/base.tmpl.html'
							},
							'main@home' : {
								templateUrl : 'views/app/home/home.html',
								controller : 'HomeController',
								controllerAs : 'home'
							},
							'side@home' : {
								templateUrl : 'views/app/sidebar/sidebar.html',
								controller : 'sidebarController',
								controllerAs : 'side'
							}
						},
						data : {
							displayName : 'Dashboard'
						}
					})
					.state('over', {
						url : '/over',
						views : {
							'@' : {
								templateUrl : 'views/layouts/base.tmpl.html'
							},
							'main@over' : {
								templateUrl : 'views/app/home/overview.html'
							},
							'side@over' : {
								templateUrl : 'views/app/sidebar/sidebar.html',
								controller : 'sidebarController',
								controllerAs : 'side'
							}
						},
						data : {
							displayName : 'Environment Details'
						}
					})
					.state(
							'reports',
							{
								url : '/reports',
								views : {
									'@' : {
										templateUrl : 'views/layouts/base.tmpl.html'
									},
									'main@reports' : {
										templateUrl : 'views/app/home/customReports.html',
									/*
									 * controller : 'HomeController',
									 * controllerAs : 'home'
									 */
									},
									'side@reports' : {
										templateUrl : 'views/app/sidebar/sidebar.html',
										controller : 'sidebarController',
										controllerAs : 'side'
									}
								},
								data : {
									displayName : 'Custom Reports'
								}
							})
							.state(
							'myDashboard',
							{
								url : '/myDashboard',
								views : {
									'@' : {
										templateUrl : 'views/layouts/base.tmpl.html'
									},
									'main@myDashboard' : {
										templateUrl : 'views/app/home/myDashboard.html',
									/*
									 * controller : 'HomeController',
									 * controllerAs : 'home'
									 */
									},
									'side@myDashboard' : {
										templateUrl : 'views/app/sidebar/sidebar.html',
										controller : 'sidebarController',
										controllerAs : 'side'
									}
								},
								data : {
									displayName : 'My Dashboard'
								}
							})
					.state(
							'workload',
							{
								url : '/workload',
								views : {
									'@' : {
										templateUrl : 'views/layouts/base.tmpl.html'
									},
									'main@workload' : {
										templateUrl : 'views/app/home/workLoadAnalysis.html',
									/*
									 * controller : 'HomeController',
									 * controllerAs : 'home'
									 */
									},
									'side@workload' : {
										templateUrl : 'views/app/sidebar/sidebar.html',
										controller : 'sidebarController',
										controllerAs : 'side'
									}
								},
								data : {
									displayName : 'Work Load'
								}
							})
					.state(
							'liveresults',
							{
								url : '/liveresults',
								views : {
									'@' : {
										templateUrl : 'views/layouts/base.tmpl.html'
									},
									'main@liveresults' : {
										templateUrl : 'views/app/home/liveResults.html',
									/*
									 * controller : 'HomeController',
									 * controllerAs : 'home'
									 */
									},
									'side@liveresults' : {
										templateUrl : 'views/app/sidebar/sidebar.html',
										controller : 'sidebarController',
										controllerAs : 'side'
									}
								},
								data : {
									displayName : 'Live Results'
								}
							})
					.state(
							'compareTest',
							{
								url : '/compareTest',
								views : {
									'@' : {
										templateUrl : 'views/layouts/base.tmpl.html'
									},
									'main@compareTest' : {
										templateUrl : 'views/app/widgets/perf/perfCompareTest.html',
									/*
									 * controller : 'HomeController',
									 * controllerAs : 'home'
									 */
									},
									'side@compareTest' : {
										templateUrl : 'views/app/sidebar/sidebar.html',
										controller : 'sidebarController',
										controllerAs : 'side'
									}
								},
								data : {
									displayName : 'Compare Test'
								}
							})
							.state(
							'jennkins',
							{
								url : '/jennkins',
								views : {
									'@' : {
										templateUrl : 'views/layouts/base.tmpl.html'
									},
									'main@jennkins' : {
										templateUrl : 'views/app/home/jenkinsworkloadmodel.html',
									/*
									 * controller : 'HomeController',
									 * controllerAs : 'home'
									 */
									},
									'side@jennkins' : {
										templateUrl : 'views/app/sidebar/sidebar.html',
										controller : 'sidebarController',
										controllerAs : 'side'
									}
								},
								data : {
									displayName : 'Jenkins Test Pipeline'
								}
							})
					.state(
							'home.diagnoseRelease',
							{
								url : 'diagnoseRelease',
								views : {
									'@' : {
										templateUrl : 'views/layouts/base.tmpl.html'
									},
									'main@home.diagnoseRelease' : {
										templateUrl : 'views/app/widgets/widgetMenuViews/diagnoseReleaseView.html',
									/*
									 * controller : 'HomeController',
									 * controllerAs : 'home'
									 */
									},
									'side@home.diagnoseRelease' : {
										templateUrl : 'views/app/sidebar/sidebar.html',
										controller : 'sidebarController',
										controllerAs : 'side'
									}
								},
								data : {
									displayName : 'RR Diagnose'
								}
							})
					.state(
							'home.prescrptiveRelease',
							{
								url : 'prescrptiveRelease',
								views : {
									'@' : {
										templateUrl : 'views/layouts/base.tmpl.html'
									},
									'main@home.prescrptiveRelease' : {
										templateUrl : 'views/app/widgets/widgetMenuViews/precriptiveReleaseView.html',
									/*
									 * controller : 'HomeController',
									 * controllerAs : 'home'
									 */
									},
									'side@home.prescrptiveRelease' : {
										templateUrl : 'views/app/sidebar/sidebar.html',
										controller : 'sidebarController',
										controllerAs : 'side'
									}
								},
								data : {
									displayName : 'RR Prescribe'
								}
							})
					.state(
							'home.perfDagnose',
							{
								url : 'perfDagnose',
								views : {
									'@' : {
										templateUrl : 'views/layouts/base.tmpl.html'
									},
									'main@home.perfDagnose' : {
										templateUrl : 'views/app/widgets/perf/prescribe/prescribe.html',
									/*
									 * controller : 'HomeController',
									 * controllerAs : 'home'
									 */
									},
									'side@home.perfDagnose' : {
										templateUrl : 'views/app/sidebar/sidebar.html',
										controller : 'sidebarController',
										controllerAs : 'side'
									}
								},
								data : {
									displayName : 'Diagnose'
								}
							})
					.state(
							'home.perfPredict',
							{
								url : 'perfPredict',
								views : {
									'@' : {
										templateUrl : 'views/layouts/base.tmpl.html'
									},
									'main@home.perfPredict' : {
										templateUrl : 'views/app/widgets/perf/predict/predict.html',
									/*
									 * controller : 'HomeController',
									 * controllerAs : 'home'
									 */
									},
									'side@home.perfPredict' : {
										templateUrl : 'views/app/sidebar/sidebar.html',
										controller : 'sidebarController',
										controllerAs : 'side'
									}
								},
								data : {
									displayName : 'Predict'
								}
							}).state('login', {
						url : '/login',
						views : {
							'@' : {
								templateUrl : 'views/app/login/login.html',
							}
						},
						controller : 'LoginController'
					});
		});
