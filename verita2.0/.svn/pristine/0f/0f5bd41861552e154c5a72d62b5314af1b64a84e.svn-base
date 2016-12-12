mainApp.controller('lennoxObservationsController', function($scope, $sessionStorage, $rootScope,$http, widgetService) {
	
	$scope.tdTitle = 'Observations';
	
	$('.lennoxObservationsPanel').lobiPanel({
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

	$('.lennoxObservationsPanel').on('onPin.lobiPanel', function(ev, lobiPanel){
	    window.console.log("Pinned");
	    $(".tdPanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#avgrespTime").css({
			"width" : "370px",
			"height" : "200px"
		});
		//drawValueChart();
	});
	
	$('.lennoxObservationsPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel){
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
		//drawValueChart();
	});
	
	$('.lennoxObservationsPanel').on('beforeClose.lobiPanel', function(ev, lobiPanel) {
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
	
	/*$('#perfTestDetailsLoadImg_id').show();*/
	//$http.get("lennox/rest/activeThreads/live?testId="+$sessionStorage.test_num).then(function(response) {
//	$http.get("lennox/rest/Observations")
	$http.get("lennox/rest/Observations/live?testId="+$sessionStorage.test_num)
	 .then(function(response) {
		  
		  $('#test_id').hide();
		  $('#kpiLoadImg_id').hide();
		  $('.perfTestDetailsTitle').show();
		  $('.perfTestDetailsTable').show();
		  
	      var jsonresp = response.data;
	      
	  //    console.log("Single KPI jsonresp ==========================", jsonresp);
	      
	      $scope.aggList = jsonresp;
	      
	      var errorflag = false;
	      var tpsflag = false;
	      
	      var errorKPI = 0;
	      var errorValue = jsonresp.errorForTest1;
	      var errorDiff = errorKPI - errorValue;
	      if(errorDiff >= 0)     {
	    	  $scope.errorColor="label-success";
	    	  errorflag = true;
	      } else{
	    	  $scope.errorColor="label-danger";
	    	  errorDiff = Math.abs(errorDiff);
	      }
	      $scope.errorKPI = errorKPI;
	      $scope.errorValue = errorValue;
	      $scope.errorDiff = errorDiff;
	   	      
	      var tpsKPI = 120;
	      var tpsValue = jsonresp.tpsForTest1;
	      var tpsDiff = tpsKPI - tpsValue;
	      
	      
	      if(tpsDiff <= 0)
	      {
	    	  $scope.tpsColor="label-success";
	    	  tpsDiff = Math.abs(tpsDiff);
	    	  tpsflag = true;
	      }
	      else{
	    	  $scope.tpsColor="label-danger";
	      }
	      $scope.tpsKPI = tpsKPI;
	      $scope.tpsValue = tpsValue;
	      $scope.tpsDiff = tpsDiff;
	      
	      if(errorflag && tpsflag)
	      {
	    	  $scope.impStatus = "greencolor";
	      }
	      else{
	    	  $scope.impStatus = "redcolor";
	      }  
	      
	  });
	

	$('.lennoxObservationsPanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value full event called");
		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		$("#donutchart").css({
			"width" : "900px",
			"height" : "600px"
		});
		//drawValueChart();
	});
	$('.lennoxObservationsPanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value small event called");
		$(".panel-body").css({
			"max-height" : "300px",
			"min-height" : "300px"
		});
		$("#donutchart").css({
			"width" : "250px",
			"height" : "245px"
		});
		//drawValueChart();
	});
	
});