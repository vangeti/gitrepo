mainApp.controller('perfJMKPIImpController', function($scope, $sessionStorage,
		$rootScope, widgetService, $http) {

	$scope.kpiTitle = 'KPI Status Since Last Build';

	$('.jmkpiPanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		unpin : false,
		reload : true,
		editTitle : false,
		minimize : false,
		reload : {
			icon : 'fa fa-refresh'
		},
		close : {
			icon : 'fa fa-times-circle'
		},
		expand : {
			icon : 'fa fa-expand',
			icon2 : 'fa fa-compress'
		}
	});

	
	$('.jmkpiPanel').on('beforeClose.lobiPanel', function(ev, lobiPanel) {
		var widgetResp = widgetService.deleteWidget(
				$sessionStorage.userId, "JMkpiPanelFlag");

		widgetResp.then(function(resp) {
			console.log("delete response");
			console.log(resp);
			if (resp.data.Status === "Success") {
				$rootScope.JMkpiPanelFlag = false;
				$rootScope.widgetList.push({
					"key":"JMkpiPanelFlag",
					"value":$scope.kpiTitle
				});
				
				$rootScope.JMkpiPanelFlag = false;
//				$rootScope.$digest();
			}
		}, function(errorPayload) {
			console.log(errorPayload);
		});
	});

	$('.jmkpiPanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value full event called");
		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		//drawKPI();

	});
	$('.jmkpiPanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value small event called");
		$(".panel-body").css({
			"max-height" : "300px",
			"min-height" : "300px"
		});
		//drawKPI();

	});
	
	/*$('.jmkpiPanel .fa-refresh').on('click', function(ev, lobiPanel){
		 // console.log("onReload.lobiPanel");
		$('.kpitable').hide();
		$('#perfJMKPILoadImg_id').show();
		//drawKPI();
		   });*/
	
	
	$http.get("devops/rest/jmkpflb").then(function(response) {
		
		$('#perfJMKPILoadImg_id').hide();
		$('.kpiPanelContent').show();
		
		
		$('.kpiTitle').show();
		
		
		$scope.bulbColor = 'red';
		
		var jsonresp = response.data;

		var resTimeKpi=1.2;
		var tpsTimeKpi=0.20;
		var errorTimeKpi=10.0;
		
		console.log(jsonresp);

		$scope.PrevrespTime=(jsonresp.ReponseTime[0].Prevvalue).toFixed(3);
		$scope.respTime=(jsonresp.ReponseTime[0].currentvalue).toFixed(3);
		
		/*$scope.Prevtps=jsonresp.TPS[0].Prevvalue;
		$scope.tps=jsonresp.TPS[0].currentvalue;
		*/
		$scope.Preverrors=jsonresp.Errors[0].Prevvalue;
		$scope.errors=jsonresp.Errors[0].currentvalue;
		
		$scope.respTimeDiff=(jsonresp.ReponseTime[0].Prevvalue-jsonresp.ReponseTime[0].currentvalue).toFixed(3);
		/*$scope.tpsTimeDiff=(jsonresp.TPS[0].Prevvalue-jsonresp.TPS[0].currentvalue);*/
		$scope.errorTimeDiff=(jsonresp.Errors[0].Prevvalue-jsonresp.Errors[0].currentvalue);
		
		if(jsonresp.ReponseTime[0].Prevvalue <= jsonresp.ReponseTime[0].currentvalue){
			$scope.respcolor="redcolor";
		}else{
			$scope.respcolor="greencolor";
		}
		
		/*if(jsonresp.TPS[0].Prevvalue > jsonresp.TPS[0].currentvalue){
			$scope.tpscolor="redcolor";
		}else{
			$scope.tpscolor="greencolor";
		}*/
		
		if(jsonresp.Errors[0].Prevvalue <= jsonresp.Errors[0].currentvalue){
			$scope.errorcolor="redcolor";
		}else{
			$scope.errorcolor="greencolor";
		}
		

		
		if( (jsonresp.ReponseTime[0].currentvalue <= resTimeKpi) /*&& (jsonresp.TPS[0].currentvalue >= tpsTimeKpi) */&& (jsonresp.Errors[0].currentvalue <= errorTimeKpi)){
			$scope.trafficClass="greencolor";
		}else{
			$scope.trafficClass="redcolor";
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