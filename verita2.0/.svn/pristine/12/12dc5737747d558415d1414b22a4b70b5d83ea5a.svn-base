mainApp.controller('smMonitorController', function($scope, $sessionStorage,
		$rootScope, $http, widgetService) {

	$scope.smMonitotrTitle = 'Monitor';

	drawMonitorTable();

	$('.smMonitorPanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		unpin : {
			icon : 'fa fa-thumb-tack'
		},
		reload : true,
		editTitle : false,
		minimize : false,
		close : false,
		expand : false,
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

	$('.smMonitorPanel').on('onPin.lobiPanel', function(ev, lobiPanel) {

		$(".monitorPanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
	});

	$('.smMonitorPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
		lobiPanel.$options.expand = false;

		$(".smMonitorPanel").css({
			"left" : "150.5px",
			"top" : "122px",
			"height" : "80%",
			"width" : "80%"
		});
		$(".monitorPanel-body").css({
			"width" : "100%",
			"height" : "100%",
			"max-height" : "85%",
			"min-height" : "85%"
		});
	});

	$('.smMonitorPanel .fa-refresh').on('click', function(ev, lobiPanel) {

		$('.monitorTable').hide();
		$('.monitorTitle').hide();
		$('#monitorLoadImg_id').show();
		drawMonitorTable();
	});

	//google.charts.setOnLoadCallback(drawValueChart);
	var color = '#000'

	/*$('#monitorLoadImg_id').show();*/

	function drawMonitorTable() {

		$http.get("devops/rest/scrummastermonitor").then(function(response) {

			$('#monitorLoadImg_id').hide();
			$('.monitorTable').show();
			$('.monitorTitle').show();

			var jsonresp = response.data;

			$scope.scrumMasterMonitorDetails = jsonresp;

		});
	}

});