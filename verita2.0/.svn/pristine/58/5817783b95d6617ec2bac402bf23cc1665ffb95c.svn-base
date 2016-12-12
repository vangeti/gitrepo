mainApp.controller('lennoxComparisionKPIController', function($scope, $sessionStorage, $rootScope,$http, widgetService) {
	
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
	var testwith = null;
	var testto = null;
	$("#compare").click(function() {
		/*testwith = $('#test_with').find(":selected").text();
		testto = $('#test_to').find(":selected").text();
		getKPI(testwith,testto);*/
		getKPI($sessionStorage.test_to_number, $sessionStorage.testwith_number);
	});
	
	function getKPI(testwith,testto) {
		var jsonresp1=new Array();
		var jsonresp2;
		var tpsKPI = 120;
		var errorKPI = 0;
	$http.get("lennox/rest/Observations/live?testId="+testwith).then(
		function(response) {
	      jsonresp1 = response.data;
	      
	      $scope.tpsKPI = tpsKPI;
	      
	      var tpsValueForTest1 = jsonresp1.tpsForTest1; 
	      $scope.tpsValueForTest1 = tpsValueForTest1;
					      
	      var tpsDiffForTest1 = tpsKPI - tpsValueForTest1;
	      if (tpsDiffForTest1 <= 0) {
				$scope.tpsColorTest1 = "label-success";
				tpsDiffForTest1 = Math.abs(tpsDiffForTest1);
				tpsflag = true;
		  } else {
				$scope.tpsColorTest1 = "label-danger";
		  }
	      $scope.tpsDiffForTest1 = tpsDiffForTest1;

	      /*var tpsDiffPercentageForTest1 = jsonresp1.tpsDiffPercentage;
	      $scope.tpsDiffPercentageForTest1 = tpsDiffPercentageForTest1;*/
	      
	      var tpsDiffPercentageForTest1 = 0;
	      if(tpsKPI > 0){
	    	  tpsDiffPercentageForTest1 = (tpsDiffForTest1/tpsKPI)*100;
	      }
	      $scope.tpsDiffPercentageForTest1 = tpsDiffPercentageForTest1.toFixed(2);
	      
	      $scope.errorKPI = errorKPI;
	      
	      var errorValueForTest1 = jsonresp1.errorForTest1;	     
	      $scope.errorValueForTest1 = errorValueForTest1;
	      
	      var errorDiffForTest1 = errorKPI - errorValueForTest1;
	      if(errorDiffForTest1 >= 0)     {
	    	  $scope.errorColorTest1="label-success";
	      } else{
	    	  $scope.errorColorTest1="label-danger";
	    	  errorDiffForTest1 = Math.abs(errorDiffForTest1);
	    	  errorflag = true;
	      }
	      $scope.errorDiffForTest1 = errorDiffForTest1;
	      
	      var errorDiffPercentageForTest1 = 0;
	      if(errorKPI > 0)	{
	    	  errorDiffPercentageForTest1 = (errorDiffForTest1/errorKPI)*100;  
	      }
	      $scope.errorDiffPercentageForTest1 = errorDiffPercentageForTest1.toFixed(2);
	  });
	$http.get("lennox/rest/Observations/live?testId="+testto).then(
		function(response) {
		  $('#kpiLoadImg_id').hide();
		  $('#test_id').hide();
		  $('.perfTestDetailsTitle').show();
		  $('.perfTestDetailsTable').show();
		  jsonresp2 = response.data;
	      
	      var tpsValueForTest2 = jsonresp2.tpsForTest1; 
	      $scope.tpsValueForTest2 = tpsValueForTest2;
					      
	      var tpsDiffForTest2 = tpsKPI - tpsValueForTest2;
	      if (tpsDiffForTest2 <= 0) {
				$scope.tpsColorTest2 = "label-success";
				tpsDiffForTest2 = Math.abs(tpsDiffForTest2);
				tpsflag = true;
		  } else {
				$scope.tpsColorTest2 = "label-danger";
		  }
	      $scope.tpsDiffForTest2 = tpsDiffForTest2;
	      
/*	      var tpsDiffPercentageForTest2 = jsonresp2.tpsDiffPercentage;
	      $scope.tpsDiffPercentageForTest2 = tpsDiffPercentageForTest2;*/
	      
	      var tpsDiffPercentageForTest2 = 0;
	      if(tpsKPI > 0){
	    	  tpsDiffPercentageForTest2 = (tpsDiffForTest2/tpsKPI)*100;
	      }
	      $scope.tpsDiffPercentageForTest2 = tpsDiffPercentageForTest2.toFixed(2);
	      
	      var errorValueForTest2 = jsonresp2.errorForTest1;	     
	      $scope.errorValueForTest2 = errorValueForTest2;
	      
	      var errorDiffForTest2 = errorKPI - errorValueForTest2;
	      if(errorDiffForTest2 >= 0)     {
	    	  $scope.errorColorTest2="label-success";
	    	  errorflag = true;
	      } else{
	    	  $scope.errorColorTest2="label-danger";
	    	  errorDiffForTest2 = Math.abs(errorDiffForTest2);
	      }
	      $scope.errorDiffForTest2 = errorDiffForTest2;
	      
	      var errorDiffPercentageForTest2 = 0;
	      if(errorKPI > 0){
	    	  errorDiffPercentageForTest2 = (errorDiffForTest2/errorKPI)*100;
	      }
	      $scope.errorDiffPercentageForTest2 = errorDiffPercentageForTest2.toFixed(2);
	  });
	}

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
		$(".lennoxObservationsPanel").css({
			"height" : "378px"
		})
		//drawValueChart();
	});
	
});