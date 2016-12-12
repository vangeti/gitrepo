mainApp.controller('smSonarController', function($scope, $sessionStorage,
		$rootScope, $http, widgetService) {

	$scope.smSonarTitle = 'STATIC ANALYSIS';

	drawSonarTable();

	$('.smSonarPanel').lobiPanel({
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

	$('.smSonarPanel').on('onPin.lobiPanel', function(ev, lobiPanel) {

		$(".sonarPanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
	});

	$('.smSonarPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
		lobiPanel.$options.expand = false;

		$(".smSonarPanel").css({
			"left" : "150.5px",
			"top" : "122px",
			"height" : "80%",
			"width" : "80%"
		});
		$(".sonarPanel-body").css({
			"width" : "100%",
			"height" : "100%",
			"max-height" : "85%",
			"min-height" : "85%"
		});
	});

	$('.smSonarPanel .fa-refresh').on('click', function(ev, lobiPanel) {
		// console.log("onReload.lobiPanel");
		$('.sonar').hide();
		$('.smSonarTitle').hide();
		$('#sonarLoadImg_id').show();
		drawSonarTable();
	});

	//google.charts.setOnLoadCallback(drawValueChart);

	/*$('#sonarLoadImg_id').show();*/

	var color = '#000'
	function drawSonarTable() {

		$http.get("devops/rest/scrummastersonar").then(function(response) {

			$('#sonarLoadImg_id').hide();
			$('.sonar').show();
			$('.smSonarTitle').show();

			var jsonresp = response.data;

			$scope.rule = jsonresp.sqale_debt_ratio;
			$scope.linesOfCodeDebt = jsonresp.sqale_index;
			$scope.linesOfCodeRule = 100 - parseFloat($scope.rule);
			$scope.critical = jsonresp.critical_violations;
			$scope.blocker = jsonresp.blocker_violations;
			$scope.major = jsonresp.major_violations;
			$scope.minor = jsonresp.minor_violations;

		});
	}

});