mainApp.controller('HomeController', function($scope, $sessionStorage,
		$rootScope, indexService) {
	$rootScope.dummyFlag = true;

	$rootScope.rrPanelFlag = false;
	$rootScope.rrPanelWidgetId;
	$rootScope.ltPanelFlag = false;
	$rootScope.cPanelFlag = false;
	$rootScope.vPanelFlag = false;

	$rootScope.kpiPanelFlag = false;
	$rootScope.tdPanelFlag = false;
	$rootScope.trespPanelFlag = false;
	$rootScope.epercPanelFlag = false;
	$rootScope.respTimePanelFlag = false;
	$rootScope.tpsPanelFlag = false;
	$rootScope.avgRTPanelFlag = false;
	$rootScope.eperTPanelFlag = false;
	
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
	
	//Lennox flags
	
	$rootScope.aggregateRepFlag = false;
	$rootScope.activeThrOvrTimeFlag = false;
	$rootScope.observationFlag = false;
	$rootScope.responseCodeFlag = false;
	
	$rootScope.FLAG_LEX_TS = false;
	$rootScope.FLAG_LEX_KPI = false;
	$rootScope.FLAG_LEX_AT = false;
	$rootScope.FLAG_LEX_AG = false;
	$rootScope.FLAG_LEX_HS = false;
	$rootScope.FLAG_LEX_RC = false;
	$rootScope.FLAG_LEX_RT = false;
	
	$rootScope.FLAG_LEX_KPI_CMP= false;
	$rootScope.FLAG_LEX_AG_CMP= false;
	$rootScope.FLAG_LEX_HS_CMP= false;
	$rootScope.FLAG_LEX_RC_CMP= false;
	$rootScope.FLAG_LEX_AT_CMP= false;
	
	for ( var i in $sessionStorage.userInfo) {
		// $rootScope[$sessionStorage.userInfo[i]] = true;
		
		$rootScope[$sessionStorage.userInfo[i][0]['id']] = true;
	}

	for ( var i in $sessionStorage.hiddenWidgetInfo) {
		// $rootScope[$sessionStorage.userInfo[i]] = true;
		$rootScope[$sessionStorage.hiddenWidgetInfo[i][0]['id']] = false;
	}

	jQuery(function($) {
		var panelList = $('#homeContainer');

		panelList.sortable({
			// Only make the .panel-heading child elements support dragging.
			// Omit this to make then entire <li>...</li> draggable.
			handle : '.panel-heading',
			update : function() {
				$('.panel', panelList).each(function(index, elem) {
					var $listItem = $(elem), newIndex = $listItem.index();

					// Persist the new indices.
				});
			}
		});
	});

});