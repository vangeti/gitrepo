mainApp.controller('Copy_of_lennoxUserProfileControllerCompare', function($scope, $sessionStorage, $rootScope,$http, widgetService,userProfileService,testUpdateService) {

	$scope.test_to_nameToDisplay = '';
	$scope.testwith_nameToDisplay = '';
	
	$scope.tdTitle = 'User Profile';
	$scope.startTimeFromBean =1;
	$scope.endTimeFromBean=2;
	
	$scope.showTestName = true;
	$scope.showSaveTestButton = true;
	$scope.showTestNameEditButton = false;
	$scope.showTestNameDiv= false;

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
	$scope.userProfileBeanTestTo = {};
	$scope.userProfileBeanTestWith = {};
	
	$('.lennoxUserProfilePanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		/*unpin : false,*/
		reload : true,
		editTitle : false,
		minimize : false,
		unpin : {
			icon : 'fa fa-thumb-tack'
		},
		/*minimize : {
			icon : 'fa fa-chevron-up',
			icon2 : 'fa fa-chevron-down'
		},*/
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
		var widgetResp = widgetService.deleteWidget($sessionStorage.userId, "tdPanelFlag");

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

	$( "#compare" ).click(function() {
		var testto=$sessionStorage.test_to_number;
		
		$scope.test_to_nameToDisplay = $sessionStorage.test_to_nameToDisplay;
		$scope.testwith_nameToDisplay = $sessionStorage.testwith_nameToDisplay;
		
		$http.get("lennox/rest/userProfile/live?testId="+$sessionStorage.testwith_number).then(
			function(response) {
			var jsonresp = response.data;
			//Below if block won't get executed moving forward, only else block gets executed...
			if($sessionStorage.test_num==1 || $sessionStorage.test_num==2) {
				$scope.userProfileBean = jsonresp;
				$scope.startTimeFromBean = $scope.userProfileBean.dbStartTime;
				$scope.endTimeFromBean = $scope.userProfileBean.dbEndTime;
				
				if(jsonresp.testName == "" || jsonresp.testName == null) {
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
				//for scenarios
				if(jsonresp.scenarios == 0 || jsonresp.scenarios == null) {
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
				//for testUrl
				if(jsonresp.testEnvUrl == "" || jsonresp.testEnvUrl == null) {
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
				
				//for testUrl
				if(jsonresp.testDate == "" || jsonresp.testDate == null) {
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
			} else {
				$scope.userProfileBeanTestWith = jsonresp;
			 } 
		});
	
	$http.get("lennox/rest/userProfile/live?testId="+testto).then(
			function(response) {
				$('#tstsummryLoadImg_id').hide();
				$('#test_id').hide();

				$('.perfTestDetailsTitle').show();
				$('.perfTestDetailsTable').show();

				var jsonresp = response.data;
				//Below if block won't get executed moving forward, only else block gets executed...
				if($sessionStorage.test_num==1 || $sessionStorage.test_num==2) {
				$scope.userProfileBean = jsonresp;
				$scope.startTimeFromBean = $scope.userProfileBean.dbStartTime;
				$scope.endTimeFromBean = $scope.userProfileBean.dbEndTime;
				
				if(jsonresp.testName == "" || jsonresp.testName == null) {
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
				//for scenarios
				if(jsonresp.scenarios == 0 || jsonresp.scenarios == null) {
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
				//for testUrl
				if(jsonresp.testEnvUrl == "" || jsonresp.testEnvUrl == null) {
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
				
				//for testUrl
				if(jsonresp.testDate == "" || jsonresp.testDate == null) {
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
			} else {
				$scope.userProfileBeanTestTo = jsonresp;
			 } 
		});
	});

	$('#tstsummryLoadImg_id').hide();

/*	$('#test_id').hide();

	$('.perfTestDetailsTitle').show();
	$('.perfTestDetailsTable').show();
	*/
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