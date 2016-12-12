mainApp.controller('smDeployController', function($scope, $sessionStorage,
		$rootScope, $http, widgetService) {

	$scope.smDeployTitle = 'Deploy';
	
	drawDeployTable();
	
	$('.smDeployPanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		reload : true,
		editTitle : false,
		minimize : false,
		close : false,
		expand : false,
		unpin : {
			icon : 'fa fa-thumb-tack'
		},
		/*minimize : {
			icon : 'fa fa-chevron-up',
			icon2 : 'fa fa-chevron-down'
		},*/
		reload : {
			icon : 'fa fa-refresh'
		},
		/*close : {
			icon : 'fa fa-times-circle'
		},
		expand : {
			icon : 'fa fa-expand',
			icon2 : 'fa fa-compress'
		}*/
	});

	$('.smDeployPanel').on('onPin.lobiPanel', function(ev, lobiPanel){
	   
	    $(".deployPanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
	});
	
	$('.smDeployPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel){
		lobiPanel.$options.expand = false;
	    $(".smDeployPanel").css({
	    	"left" : "150.5px",
	    	"top" : "122px",
			"height" : "80%",
			"width" : "80%"
		});
	    $(".deployPanel-body").css({
	    	"width" : "100%",
			"height" : "100%",
			"max-height" : "85%",
			"min-height" : "85%"
		});
	});
	
	/*$('.smDeployPanel').on(
			'beforeClose.lobiPanel',
			function(ev, lobiPanel) {
				var widgetResp = widgetService.deleteWidget(
						$sessionStorage.userId, "DeployFlag");

				widgetResp.then(function(resp) {
				
					if (resp.data.Status === "Success") {
						$rootScope.widgetList.push({
							"key" : "DeployFlag",
							"value" : $scope.smDeployTitle
						});
						$rootScope.DeployFlag = false;
//						$rootScope.$digest();
					}
				}, function(errorPayload) {
					
				});
			});

	$('.smDeployPanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
		
		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		$("#avgrespTime").css({
			"width" : "100%",
			"height" : "600px"
		});
		drawDeployTable();
	});
	$('.smDeployPanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
	
		$(".panel-body").css({
			"max-height" : "300px",
			"min-height" : "300px"
		});
		$("#avgrespTime").css({
			"width" : "100px",
			"height" : "100px"
		});
		drawDeployTable();
	});
	*/
	
	$('.smDeployPanel .fa-refresh').on('click', function(ev, lobiPanel){
		
		$('.deployTable').hide();
		$('.deployTitle').hide();
		$('#deployLoadImg_id').show();
		  drawDeployTable();
		   });

	$('#deployLoadImg_id').show();
	
	var color = '#000'
	function drawDeployTable() {
		$http.get("devops/rest/scrummasterdeploy").then(
				function(response) {
					$('#deployLoadImg_id').hide();
					$('.deployTable').show();
					$('.deployTitle').show();
					
					var jsonresp = response.data;	
					$scope.scrumMasterDeployDetails = jsonresp;
					
					
				});
	}

});