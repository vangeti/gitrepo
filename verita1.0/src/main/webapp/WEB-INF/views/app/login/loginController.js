mainApp
		.controller(
				'LoginController',
				function($scope, $sessionStorage, $state, loginService) {

					var domainDetails = loginService.getDomainDetails();
					var projectDetails = loginService.getProjectDetails();
					$scope.buName="";
					$scope.projectName="";
					domainDetails
							.then(function(domaindata) {

								domaindata = domaindata.data;

								$("#domainSelectBox")
										.append(
												'<option value="0" selected="selected">------------------Select BU-------------------</option>');
								for ( var key in domaindata) {

									var item = domaindata[key];
									
									$("#domainSelectBox").append(
											'<option value="' + item[0] + '" >'
													+ item[1] + '</option>');

								}

							}

							);

					$('#domainDiv')
							.on(
									'change',
									'select',
									function(e) {

										$("#projectSelectBox").empty();

										projectDetails
												.then(function(projectdata) {
													projectdata = projectdata.data;
													for ( var key in projectdata) {
														var item = projectdata[key];
														if (item[2] == $(
																"#domainSelectBox option:selected")
																.text())
															$(
																	"#projectSelectBox")
																	.append(
																			'<option id="project_'
																					+ item[0]
																					+ '" value="'
																					+ item[0]
																					+ '" >'
																					+ item[1]
																					+ '</option>');
													}
												});

									});

					this.username;
					this.password;
					
					$sessionStorage.userInfo = [];
					$sessionStorage.hiddenWidgetInfo = [];
					$sessionStorage.buName="";
					$sessionStorage.projectName="";
					$sessionStorage.test_num="";
					$sessionStorage.buId="";
					$sessionStorage.projectId="";

					$(".bodyback")
							.css(
									{
										"background-image" : "url('./views/images/background/banner-blueswan.jpg')",
									});

					this.authenticate = function() {
						console.log('authenticating');
						$('#domainSelectBox').prop('disabled', false);
						$('#projectSelectBox').prop('disabled', false);

					}
					this.login = function() {

						// TODO do some authentication or call authentication
						// service
						// for login
						this.message = '';
						// alert(this.username+' '+this.password);
						var domain_id=$("#domainSelectBox option:selected").val();
						var project_id=$("#projectSelectBox option:selected").val();
						
						if (this.username === '' || this.password === '') {
							// loginService.getLogin('','');
							// $state.go('home');
							$scope.message = "Please Enter User Name/Password";
						}else if(domain_id === '0' || project_id === '0'){
							$scope.buMessage = "Please Select BU/Project";
							
						}else {
							
							
							
							$sessionStorage.username = this.username;
							$sessionStorage.password = this.password;
							
							$sessionStorage.buName = $("#domainSelectBox option:selected").text();
							$sessionStorage.projectName = $("#projectSelectBox option:selected").text();
							
							$sessionStorage.buId = domain_id;
							$sessionStorage.projectId = project_id;
							
							
						
							var loginRes = loginService.postLogin(
									this.username, this.password,domain_id,project_id);

							loginRes
									.then(
											function(payload) {
												console.log(payload);
												
												$sessionStorage.userId = payload.data.user_id;
												$sessionStorage.validUser = payload.data.validUser;
												if (payload.data.validUser === "false") {
													$scope.message = "Invalid user/password";
												} else if (payload.data.validUser === "true") {
													console.log('==================');
													console.log(payload.data);
													$sessionStorage.userInfo = payload.data.graphList;
													$sessionStorage.hiddenWidgetInfo = payload.data.hidegraphList;
													$sessionStorage.url = payload.data.url;
													$(".bodyback")
															.css(
																	{
																		"background" : "#999999",
																	});
													$state.go('home');
												}
											},
											function(errorPayload) {
												console.log(errorPayload);
												$scope.message = "Internal Server Error";
											});

						}

					}
				});