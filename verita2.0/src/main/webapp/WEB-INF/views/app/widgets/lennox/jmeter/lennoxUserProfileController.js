mainApp.controller('lennoxUserProfileController', function($scope, $sessionStorage, $rootScope,$http, widgetService,userProfileService,testUpdateService) {

	$scope.tdTitle = 'User Profile';
	$scope.startTimeFromBean =1;
	$scope.endTimeFromBean=2;
	
	$scope.showTestName = true;
	$scope.showSaveTestButton = true;
	$scope.showTestNameEditButton = false;
	$scope.showTestNameDiv= false;
	
	
	/*
	 * $scope.showTotalUsers = true; $scope.showTotalUsersButton = true;
	 */
	
	
	$scope.showScenarios = true;
	$scope.showScenariosButton = true;
	$scope.showScenariosEditButton = false;
	$scope.showScenariosDiv= false;
	
	$scope.showUrl = true;
	$scope.showUrlButton = true;
	$scope.showUrlEditButton = false;
	$scope.showUrlDiv= false;
	
	$scope.showTestDate = true;
	$scope.showTestDateButton = true;
	$scope.showTestDateEditButton = false;
	$scope.showTestDateDiv= false;

	$scope.selected1 = {};
	$scope.selected2 = {};
	$scope.selected3 = {};
	$scope.selected4 = {};
	$scope.userProfileBean = {};
	
	$scope.getTemplate1 = function (userProfileBean) {
		try{
		if (userProfileBean.testName === $scope.selected1.testName){
			return 'addscenarioname';
		} else 
			return 'editscenario';
		}catch(err){}
	};

	// Edited by Sankar
	$scope.getTemplateTimezone = function (userProfileBean) {
		if (userProfileBean.testName === $scope.selected2.testName){
			return 'addtimezone';
		} else 
			return 'edittimezone';
	};
	
	
$scope.getTemplateScenarios = function (userProfileBean) {
	if (userProfileBean.testName === $scope.selected3.testName){	// testscenarios
			return 'addscenariosss';
		} else 
			return 'editscenariosdetails';
	};
	
	
	$scope.getTemplateEnvUrl = function (userProfileBean) {
		if (userProfileBean.testName === $scope.selected4.testName){
			return 'addenvurl';
		} else 
			return 'editenvurl';
	};
	
	$('.lennoxUserProfilePanel').lobiPanel({
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
		 * minimize : { icon : 'fa fa-chevron-up', icon2 : 'fa fa-chevron-down' },
		 */
		reload: {
			icon: 'fa fa-refresh'
		},
		close : {
			icon : 'fa fa-times-circle'
		},
		expand : {
			icon : 'fa fa-expand',
			icon2 : 'fa fa-compress'
		}
	});

	$('.lennoxUserProfilePanel').on('onPin.lobiPanel', function(ev, lobiPanel){
		window.console.log("Pinned");
		$(".tdPanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#avgrespTime").css({
			"width" : "370px",
			"height" : "200px"
		});
		drawValueChart();
	});

	$('.lennoxUserProfilePanel').on('onUnpin.lobiPanel', function(ev, lobiPanel){
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
		drawValueChart();
	});

	$('.lennoxUserProfilePanel').on('beforeClose.lobiPanel', function(ev, lobiPanel) {
		var widgetResp = widgetService.deleteWidget(
				$sessionStorage.userId, "tdPanelFlag");

		widgetResp.then(function(resp) {

			if (resp.data.Status === "Success") {
				$rootScope.tdPanelFlag = false;
				$rootScope.widgetList.push({
					"key":"tdPanelFlag",
					"value":$scope.tdTitle
				});
			}
		}, function(errorPayload) {

		});
	});

	/* $('#perfTestDetailsLoadImg_id').show(); */

	$scope.editTestName = function(userProfileBean) {
		// console.log('The edit is :');
		 $scope.selected1 = angular.copy(userProfileBean);
	};
	
	// Edited by Sankar
	$scope.editDateTimeZone = function(userProfileBean) {
		// console.log('The edit is :');
		 $scope.selected2 = angular.copy(userProfileBean);
	};
	
	$scope.editScenarioDetails = function(userProfileBean) {
		console.log('The edit is  :');
		 $scope.selected3 = angular.copy(userProfileBean);
	};
	
	$scope.editQaEnvUrlDetails = function(userProfileBean) {
		console.log('The edit  envurl is  :');
		 $scope.selected4 = angular.copy(userProfileBean);
	};
	
	$scope.reset = function () {
        $scope.selected1 = {};
    };
    $scope.resetTestDateTimeZone = function () {
        $scope.selected2 = {};
    };
    
    $scope.resetScenario = function () {
        $scope.selected3 = {};
    };
    
    $scope.resetUrl = function () {
        $scope.selected4 = {};
    };
    
	$scope.updateTestName = function(userProfileBean) {
		// this method to saves/updates the test name given by the user...
		if(userProfileBean.scenario_name.length > 5 && userProfileBean.scenario_name.length <= 50)	{
			var testNameUpdateResp = testUpdateService.saveTestNameUpdateDetails(userProfileBean.testName, userProfileBean.scenario_name);
			testNameUpdateResp.then(function(resp) {
				if(resp.data.result == "success"){
					alert("Success....");
					console.log("Success....");
				}
				$scope.reset();
			}, function(errorPayload) {
				$scope.userProfileBean.scenario_name='';
				console.log('Failure',errorPayload);
			});
		}	else	{
			alert("Please provide the Test name of length between 5 to 50 characters.");
		}
	};
	
	// Edited by Sankar
	
	$scope.updateTestDateTimeZone = function(userProfileBean) {
		var testDateTimeZoneUpdateResp = testUpdateService.saveDateTimeZonedateDetails(userProfileBean.testName, userProfileBean.testdatetimezone);
		testDateTimeZoneUpdateResp.then(function(resp) {
			if(resp.data.result == "success"){
				alert("Success....");
				console.log("Success....");
			}
			$scope.resetTestDateTimeZone();
		}, function(errorPayload) {
			$scope.userProfileBean.testdatetimezone='';
			console.log('Failure',errorPayload);
		});
	};
	
	
	$scope.updateScenariosDetails = function(userProfileBean) {
		console.log("Success....saveupdateScenariosDetails");
		var updateScenariosDetailsResp = testUpdateService.saveupdateScenariosDetails(userProfileBean.testName, userProfileBean.testscenarios);
		updateScenariosDetailsResp.then(function(resp) {
			if(resp.data.result == "success"){
				alert("Success....");
				console.log("Success....");
			}
			$scope.resetScenario();
		}, function(errorPayload) {
			$scope.userProfileBean.testscenarios='';
			console.log('Failure',errorPayload);
		});
	};
	
	
	$scope.updateQaEnvDetails = function(userProfileBean) {
		console.log("Success....updateQaEnvDetails");
		var updateQaEnvDetailsResp = testUpdateService.saveupdateQaEnvDetails(userProfileBean.testName, userProfileBean.testenvurl);
		updateQaEnvDetailsResp.then(function(resp) {
			if(resp.data.result == "success"){
				alert("Success....");
				console.log("Success....");
			}
			$scope.resetUrl();
		}, function(errorPayload) {
			$scope.userProfileBean.testscenarios='';
			console.log('Failure',errorPayload);
		});
	};
	
	
	// $http.get("lennox/rest/activeThreads/live?testId="+$sessionStorage.test_num).then(
	
	$('#tstsummryLoadImg_id').show();
	$('#profileid').hide();
	$http.get("lennox/rest/userProfile/live?testId="+$sessionStorage.test_num).then(

	// $http.get("lennox/rest/userProfile").then

	   //$http.get("lennox/rest/userProfile").then
		function(response) {
		$('#tstsummryLoadImg_id').hide();
		$('#profileid').show();
		$('#test_id').hide();
		$('.perfTestDetailsTitle').show();
		$('.perfTestDetailsTable').show();
		var jsonresp = response.data;

		// console.log("UserProfile jsonresp==", jsonresp);
		if($sessionStorage.test_num==1 || $sessionStorage.test_num==2)
			{
		$scope.userProfileBean = jsonresp;
		$scope.startTimeFromBean = $scope.userProfileBean.dbStartTime;
		$scope.endTimeFromBean = $scope.userProfileBean.dbEndTime;
		
		if(jsonresp.testName == "" || jsonresp.testName == null) {
		// if(jsonresp.testName != "" || jsonresp.testName != null){

			/*
			 * $('#testNameTd').empty();
			 * 
			 * $('#testNameTd').append(jsonresp.testName);
			 * $('#testNameTextBoxTd').hide();
			 */
			$scope.showTestName = true;
			$scope.showSaveTestButton = true;

			
		} else if(jsonresp.testName != "" || jsonresp.testName != null){
			
			$scope.testNameSaved = jsonresp.testName;
			$scope.testNameBind = $scope.userProfileBean.testName;
			$scope.showTestName = false;
			$scope.showSaveTestButton = false;
			$scope.showTestNameDiv = true;
			$scope.showTestNameEditButton = true;

		}
		/*
		 * //for total users: if(jsonresp.totalUsers == "" ||
		 * jsonresp.totalUsers == null) { //if(jsonresp.testName != "" ||
		 * jsonresp.testName != null){
		 * 
		 * 
		 * $scope.showTotalUsers = true; $scope.showTotalUsersButton = true;
		 * 
		 *  } else if(jsonresp.totalUsers != "" || jsonresp.totalUsers != null){
		 * 
		 * $scope.totalUsersSaved = jsonresp.totalUsers; $scope.showTotalUsers =
		 * false; $scope.showTotalUsersButton = false;
		 *  }
		 */
		// for scenarios
		if(jsonresp.scenarios == 0 || jsonresp.scenarios == null) {
			// if(jsonresp.testName != "" || jsonresp.testName != null){

				
				$scope.showScenarios = true;
				$scope.showScenariosButton = true;

				
			} else if(jsonresp.scenarios != "" || jsonresp.scenarios != null){
				
				$scope.scenariosSaved = jsonresp.scenarios;
				$scope.scenariosBind = jsonresp.scenarios;
				$scope.showScenarios = false;
				$scope.showScenariosButton = false;
				$scope.showScenariosEditButton = true;
				$scope.showScenariosDiv= true;

			}
		// for testUrl
		if(jsonresp.testEnvUrl == "" || jsonresp.testEnvUrl == null) {
			// if(jsonresp.testName != "" || jsonresp.testName != null){

				$scope.showUrl = true;
				$scope.showUrlButton = true;

				
			} else if(jsonresp.testEnvUrl != "" || jsonresp.testEnvUrl != null){
				
				$scope.urlSaved = jsonresp.testEnvUrl;
				$scope.testUrlBind = jsonresp.testEnvUrl;
				$scope.showUrl = false;
				$scope.showUrlButton = false;
				$scope.showUrlEditButton = true;
				$scope.showUrlDiv= true;

			}
		
		// for testUrl
		if(jsonresp.testDate == "" || jsonresp.testDate == null) {
			// if(jsonresp.testName != "" || jsonresp.testName != null){

				$scope.testDate = true;
				$scope.showTestDateButton = true;

				
			} else if(jsonresp.testDate != "" || jsonresp.testDate != null){
				
				$scope.testDateSaved = jsonresp.testDate;
				$scope.testDateBind = jsonresp.testDate;
				$scope.showTestDate = false;
				$scope.showTestDateButton = false;
				$scope.showTestDateEditButton = true;
				$scope.showTestDateDiv= true;

			}
		
			}
		else
			{
		// $scope.userProfileBean=jsonresp;
			$scope.userProfileBean = jsonresp;
			// $scope.selected.scenario_name=$scope.userProfileBean.scenario_name;
// console.log("userprofliebean",$scope.userProfileBean);
			} 
		
		

	});
	
	$( "#compare" ).click(function() {

	var testto=$sessionStorage.test_to_number;// $('#test_to').find(":selected").text();
// console.log("testto.....+++++++++++today got executed:"+testto);

	//var testto=$sessionStorage.test_to_number;

	var testto=$sessionStorage.test_to_number;

	$http.get("lennox/rest/userProfile/live?testId="+testto).then(
			// $http.get("lennox/rest/userProfile").then
				function(response) {
				$('#tstsummryLoadImg_id').hide();

				$('#test_id').hide();

				$('.perfTestDetailsTitle').show();
				$('.perfTestDetailsTable').show();

				var jsonresp = response.data;

				// console.log("UserProfile jsonresp==", jsonresp);
				if($sessionStorage.test_num==1 || $sessionStorage.test_num==2)
					{
				$scope.userProfileBean = jsonresp;
				$scope.startTimeFromBean = $scope.userProfileBean.dbStartTime;
				$scope.endTimeFromBean = $scope.userProfileBean.dbEndTime;
				
				if(jsonresp.testName == "" || jsonresp.testName == null) {
				// if(jsonresp.testName != "" || jsonresp.testName != null){

					/*
					 * $('#testNameTd').empty();
					 * 
					 * $('#testNameTd').append(jsonresp.testName);
					 * $('#testNameTextBoxTd').hide();
					 */
					$scope.showTestName = true;
					$scope.showSaveTestButton = true;

					
				} else if(jsonresp.testName != "" || jsonresp.testName != null){
					
					$scope.testNameSaved = jsonresp.testName;
					$scope.testNameBind = $scope.userProfileBean.testName;
					$scope.showTestName = false;
					$scope.showSaveTestButton = false;
					$scope.showTestNameDiv = true;
					$scope.showTestNameEditButton = true;

				}
				/*
				 * //for total users: if(jsonresp.totalUsers == "" ||
				 * jsonresp.totalUsers == null) { //if(jsonresp.testName != "" ||
				 * jsonresp.testName != null){
				 * 
				 * 
				 * $scope.showTotalUsers = true; $scope.showTotalUsersButton =
				 * true;
				 * 
				 *  } else if(jsonresp.totalUsers != "" || jsonresp.totalUsers !=
				 * null){
				 * 
				 * $scope.totalUsersSaved = jsonresp.totalUsers;
				 * $scope.showTotalUsers = false; $scope.showTotalUsersButton =
				 * false;
				 *  }
				 */
				// for scenarios
				if(jsonresp.scenarios == 0 || jsonresp.scenarios == null) {
					// if(jsonresp.testName != "" || jsonresp.testName != null){

						
						$scope.showScenarios = true;
						$scope.showScenariosButton = true;

						
					} else if(jsonresp.scenarios != "" || jsonresp.scenarios != null){
						
						$scope.scenariosSaved = jsonresp.scenarios;
						$scope.scenariosBind = jsonresp.scenarios;
						$scope.showScenarios = false;
						$scope.showScenariosButton = false;
						$scope.showScenariosEditButton = true;
						$scope.showScenariosDiv= true;

					}
				// for testUrl
				if(jsonresp.testEnvUrl == "" || jsonresp.testEnvUrl == null) {
					// if(jsonresp.testName != "" || jsonresp.testName != null){

						$scope.showUrl = true;
						$scope.showUrlButton = true;

						
					} else if(jsonresp.testEnvUrl != "" || jsonresp.testEnvUrl != null){
						
						$scope.urlSaved = jsonresp.testEnvUrl;
						$scope.testUrlBind = jsonresp.testEnvUrl;
						$scope.showUrl = false;
						$scope.showUrlButton = false;
						$scope.showUrlEditButton = true;
						$scope.showUrlDiv= true;

					}
				
				// for testUrl
				if(jsonresp.testDate == "" || jsonresp.testDate == null) {
					// if(jsonresp.testName != "" || jsonresp.testName != null){

						$scope.testDate = true;
						$scope.showTestDateButton = true;

						
					} else if(jsonresp.testDate != "" || jsonresp.testDate != null){
						
						$scope.testDateSaved = jsonresp.testDate;
						$scope.testDateBind = jsonresp.testDate;
						$scope.showTestDate = false;
						$scope.showTestDateButton = false;
						$scope.showTestDateEditButton = true;
						$scope.showTestDateDiv= true;

					}
				
					}
				else
					{
					// $scope.userProfileBean = jsonresp;
				/*
				 * $scope.testNameBind=$scope.userProfileBean.testname;
				 * /*$scope.testNameBind=$scope.userProfileBean.testname;
				 * $scope.userProfileBean.testName=userProfileBean.testname;
				 * $scope.userProfileBean.startTime=userProfileBean.startTime;
				 */
					// $scope.userProfileBean=jsonresp;
					/*
					 * $scope.userProfileBean.totalUsers=jsonresp.maxThreads;
					 * $scope.userProfileBean.startTime=jsonresp.teststarttime;
					 * $scope.userProfileBean.endTime=jsonresp.testendtime;
					 * $scope.userProfileBean.duration=jsonresp.testduration;
					 * $scope.userProfileBean.rampUpDuration=jsonresp.rampup;
					 * $scope.userProfileBean.steadyPeriod=jsonresp.steadyperiod;
					 * $scope.userProfileBean.rampDownDuration=jsonresp.rampdown;
					 */
					$scope.userProfileBean = jsonresp;
					} 
				
				

			});
	});

	// -------------------------------------------------------
	$scope.saveTestName = function() {
		var testNameResp = userProfileService.addTestNameDetails($scope.testNameBind,$scope.startTimeFromBean,$scope.endTimeFromBean);
		testNameResp.then(function(resp) {
			if(resp.data.result == "success"){
				$scope.testNameSaved = $scope.testNameBind;
				$scope.testNameBind = $scope.testNameBind;
				$scope.showTestNameDiv = true;
				$scope.showTestName = false;
				$scope.showSaveTestButton = false;
				$scope.showTestNameEditButton = true;
			}


		}, function(errorPayload) {
			console.log('Failure',errorPayload);
			
			
		});
	}
	
	/*
	 * $scope.saveTotalUsers = function() {
	 * 
	 * console.log('Test TOTALUSERS is :',$scope.totalUsersBind);
	 * 
	 * 
	 * var totalUsersResp =
	 * userProfileService.addTotalUsersDetails($scope.totalUsersBind,$scope.startTimeFromBean,$scope.endTimeFromBean);
	 * 
	 * totalUsersResp.then(function(resp) {
	 * 
	 * console.log("TOTALUSERS RESULT= ",resp.data.result); if(resp.data.result ==
	 * "success"){ console.log("Lakshmi-------------success");
	 * $scope.totalUsersSaved = $scope.totalUsersBind; $scope.showTotalUsers =
	 * false; $scope.showTotalUsersButton = false; }
	 * 
	 *  }, function(errorPayload) { console.log('Failure',errorPayload);
	 * 
	 * 
	 * }); }
	 */
	$scope.saveScenarios = function() {

		// console.log('The saveScenarios is :',$scope.scenariosBind);

		var scenariosResp = userProfileService.addScenariosDetails($scope.scenariosBind,$scope.startTimeFromBean,$scope.endTimeFromBean,$sessionStorage.test_num);

		scenariosResp.then(function(resp) {

			// console.log("saveScenarios RESULT= ",resp.data.result);
			if(resp.data.result == "success"){
				// retreiveTopFiveRecords();
			// console.log("saveScenarios-------------success");
				$scope.scenariosSaved = $scope.scenariosBind;
				$scope.scenariosBind = $scope.scenariosBind;
				$scope.showScenarios = false;
				$scope.showScenariosButton = false;
				$scope.showScenariosEditButton = true;
				$scope.showScenariosDiv= true;
			}


		}, function(errorPayload) {
			console.log('Failure',errorPayload);
			
			
		});
	}
	
	$scope.saveTestUrl = function() {

		console.log('The saveTestUrl is  :',$scope.tesUrlBind);

		var testUrslResp = userProfileService.addQaEnvUrlDetails($scope.testUrlBind,$scope.startTimeFromBean,$scope.endTimeFromBean,$sessionStorage.test_num);

		testUrslResp.then(function(resp) {

			console.log("saveTestUrl RESULT= ",resp.data.result);
			if(resp.data.result == "success"){
				// retreiveTopFiveRecords();
				console.log("saveTestUrl-------------success");
				$scope.urlSaved = $scope.testUrlBind;
				$scope.testUrlBind = $scope.testUrlBind;
				$scope.showUrl = false;
				$scope.showUrlButton = false;
				$scope.showUrlEditButton = true;
				$scope.showUrlDiv= true;				
			}


		}, function(errorPayload) {
			console.log('Failure',errorPayload);
			
			
		});
	}
	
	// ----EDIT BUTTON FUNCTIONALITY
	$scope.showTestNameDetails = function() {
		
		// $scope.tbTestName = $scope.userProfileBean.testName;
		$scope.showTestName = true;
		$scope.showSaveTestButton = true;
		$scope.showTestNameEditButton = false;
		$scope.showTestNameDiv= false;
		
	}
	
$scope.showScenariosDetails = function() {
		
		// $scope.tbTestName = $scope.userProfileBean.testName;
		$scope.showScenarios = true;
		$scope.showScenariosButton = true;
		$scope.showScenariosEditButton = false;
		$scope.showScenariosDiv= false;
		
	}

$scope.showUrlDetails = function() {
	
	// $scope.tbTestName = $scope.userProfileBean.testName;
	$scope.showUrl = true;
	$scope.showUrlButton = true;
	$scope.showUrlEditButton = false;
	$scope.showUrlDiv= false;
	
}

$scope.showTestDateDetails = function() {
	
	// $scope.tbTestName = $scope.userProfileBean.testName;
	$scope.showTestDate = true;
	$scope.showTestDateButton = true;
	$scope.showTestDateEditButton = false;
	$scope.showTestDateDiv= false;
	
}
	
	
	// -------------------------------------------------------

		
		$('.lennoxUserProfilePanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
			$('#tstsummryLoadImg_id').show();
			window.console.log("value full event called");
			$(".panel-body").css({
				"max-height" : "100%",
				"min-height" : "100%"
			});
			$("#userScroll").css({
				"width" : "100%",
				"overflow-x": "hidden",
				"overflow-y": "auto",
				"max-height" : "100%",
				"min-height" : "100%"
			});
			$("#submit1").css({
				"margin-top" : "-3%"
				
			});
			$("#submit2").css({
				"margin-top" : "-3%"
				
			});
			$("#submit3").css({
				"margin-top" : "-3%"
				
			});
			$("#edit1").css({
				"margin-top" : "0%"
				
			});
			$("#edit2").css({
				"margin-top" : "0%"
				
			});
			$("#edit3").css({
				"margin-top" : "0%"
				
			});
			$('#tstsummryLoadImg_id').hide();
		});
		$('.lennoxUserProfilePanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
			$('#tstsummryLoadImg_id').show();
			window.console.log("value small event called");
			$(".panel-body").css({
				"max-height" : "300px",
				"min-height" : "300px"
			});
			$("#userScroll").css({					
				"height": "300px",
				"overflow-x": "hidden",
				"overflow-y": "auto",
				
			});
			$('#tstsummryLoadImg_id').hide();
		});

	});