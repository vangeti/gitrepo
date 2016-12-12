mainApp.controller('perfJMTestDetailsController', function($scope, $sessionStorage, $rootScope,$http, widgetService) {
	
	$scope.tdTitle = 'Test Execution Summary';
	
	
	
	
	$('.jmtdPanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		unpin : false,
		reload : true,
		editTitle : false,
		minimize : false,
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

	$('.jmtdPanel').on('beforeClose.lobiPanel', function(ev, lobiPanel) {
		var widgetResp = widgetService.deleteWidget(
				$sessionStorage.userId, "JMtdPanelFlag");

		widgetResp.then(function(resp) {
			console.log("delete response");
			console.log(resp);
			if (resp.data.Status === "Success") {
				$rootScope.tdPanelFlag = false;
				$rootScope.widgetList.push({
					"key":"JMtdPanelFlag",
					"value":$scope.tdTitle
				});
				$rootScope.JMtdPanelFlag = false;
			}
		}, function(errorPayload) {
			console.log(errorPayload);
		});
	});
	
	$('.jmtdPanel .fa-refresh').on('click', function(ev, lobiPanel){
		 // console.log("onReload.lobiPanel");
		$('.kpitable').hide();
		$('#perfJMKPILoadImg_id').show();
		//drawKPI();
		   });
	
	/*$('#perfJMTestDetailsLoadImg_id').show();*/
	
	$http.get("devops/rest/jmtes")
	  .then(function(response) {
		  
		  $('#perfJMTestDetailsLoadImg_id').hide();
		  
		  $('.testDetailsTable').show();
			$('.testDetailsTitle').show();
		  
	      var jsonresp = response.data;
	      console.log("test details "+ jsonresp.Average[0].response);
	    $scope.projectName = jsonresp.Header[0].projectName;
	    $scope.date = jsonresp.Header[0].date;
	  	$scope.duration = jsonresp.Header[0].duration;
	  	$scope.users = jsonresp.Header[0].users;
	  	$scope.averageHits = jsonresp.Average[0].hits;
	    $scope.averageSamples = jsonresp.Average[0].samples;
	  	$scope.averageResponse = jsonresp.Average[0].response
	  	$scope.averageThroughput = jsonresp.Average[0].throughput
	  	$scope.totalHits = jsonresp.Total[0].hits;
	    $scope.totalSamples = jsonresp.Total[0].samples;
	  	$scope.totalThroughput = jsonresp.Total[0].throughput;
	  	$scope.percentileError = jsonresp.Total[0].percentileError;
	      
	  });
	

	/*$('.ValuePanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value full event called");
		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		$("#donutchart").css({
			"width" : "900px",
			"height" : "600px"
		});
		drawValueChart();
	});
	$('.ValuePanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
		window.console.log("value small event called");
		$(".panel-body").css({
			"max-height" : "300px",
			"min-height" : "300px"
		});
		$("#donutchart").css({
			"width" : "250px",
			"height" : "245px"
		});
		drawValueChart();
	});*/
	
});