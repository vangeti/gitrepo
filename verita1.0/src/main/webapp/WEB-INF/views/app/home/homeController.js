mainApp.controller('HomeController', function($scope, $sessionStorage,
		$rootScope, indexService) {
	$rootScope.dummyFlag = true;

	$rootScope.rrPanelFlag = false;
	$rootScope.rrPanelWidgetId;
	$rootScope.ltPanelFlag = false;
	$rootScope.cPanelFlag = false;
	$rootScope.vPanelFlag = false;

	$rootScope.kpiPanelFlag = true;
	$rootScope.tdPanelFlag = true;
	$rootScope.trespPanelFlag = true;
	$rootScope.epercPanelFlag = true;
	$rootScope.respTimePanelFlag = true;
	$rootScope.tpsPanelFlag = true;
	$rootScope.avgRTPanelFlag = true;
	$rootScope.eperTPanelFlag = true;
	
	$rootScope.jiraFlag = false;
	
	$rootScope.jenkinsBuildFlag = false;
	$rootScope.monitorFlag = false;
	$rootScope.DeployFlag = false;
	$rootScope.sonarFlag = false;	
	$rootScope.jenkinsFlag = false;
	$rootScope.SecVCGFlag = false;
	
	$rootScope.JMkpiPanelFlag = false;
	$rootScope.JMtrespPanelFlag = false;
	$rootScope.JMtpsPanelFlag = false;
	$rootScope.JMtrespPanelFlag = false;
	$rootScope.JMavgRTPanelFlag = false;
	$rootScope.JMrespTimePanelFlag = false;
	$rootScope.JMtdPanelFlag = false;
	$rootScope.JMepercPanelFlag = false;
	$rootScope.JMeperTPanelFlag = false;
	
	$rootScope.rmDSIFlag = false;
	$rootScope.rmRSIFlag = false;
	$rootScope.rmTEPFlag = false;
	$rootScope.rmTCFlag = false;
	$rootScope.rmODSFLag = false;
	$rootScope.rmFRFlag = false;
	$rootScope.rmTCYFlag = false;
	$rootScope.rmMTTFFlag = false;
	
	$rootScope.SelTESFlag = false;
	

	$rootScope.jmTSFLPFlag = false;
	
	
	for ( var i in $sessionStorage.userInfo) {
		// $rootScope[$sessionStorage.userInfo[i]] = true;
		
		$rootScope[$sessionStorage.userInfo[i][0]['id']] = true;
	}

	for ( var i in $sessionStorage.hiddenWidgetInfo) {
		// $rootScope[$sessionStorage.userInfo[i]] = true;
		$rootScope[$sessionStorage.hiddenWidgetInfo[i][0]['id']] = false;
	}

});