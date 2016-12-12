mainApp.controller('JiraFeatureListImpController', function($scope,
		$sessionStorage, $rootScope, widgetService, $http) {

	$('.jiraPanel').lobiPanel({
		// Options go here
		sortable : false,
		// editTitle : false,
		unpin : true,
		reload : false,
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

	/*$('.jiraPanel').on('beforeClose.lobiPanel', function(ev, lobiPanel) {
		$rootScope.jiraFlag = false;
		$rootScope.$digest();
	});
	
	$('.jiraPanel').on('beforeClose.lobiPanel', function(ev, lobiPanel) {
		var widgetResp = widgetService.deleteWidget(
				$sessionStorage.userId, "jiraFlag");

		widgetResp.then(function(resp) {
			
			if (resp.data.Status === "Success") {
				$rootScope.jiraFlag = false;
				$rootScope.widgetList.push({
					"key":"jiraFlag",
					"value":$scope.jiraTitle
				});
				$rootScope.jiraFlag = false;
			}
		}, function(errorPayload) {
			console.log(errorPayload);
		});
	});*/

	/*$('.jiraPanel').on('onFullScreen.lobiPanel', function(ev, lobiPanel) {
		
		
		$(".panel-body").css({
			"max-height" : "100%",
			"min-height" : "100%"
		});
		$("#featureWidget").css({
			"width" : "100%",
			"height" : "600px"
		});
		drawValueChart();

	});
	

	
	$('.jiraPanel').on('onSmallSize.lobiPanel', function(ev, lobiPanel) {
		
		$(".panel-body").css({
			"max-height" : "300px",
			"min-height" : "300px"
		});
		$("#featureWidget").css({
			"width" : "400px",
			"height" : "300px"
		});
		drawValueChart();
	});*/

	$('.jiraPanel').on('onPin.lobiPanel', function(ev, lobiPanel) {
		$(".jiraPanel-body").css({
			"max-height" : "315px",
			"min-height" : "315px"
		});
		$("#featureWidget").css({
			"width" : "600px",
			"height" : "300px"
		});
		drawValueChart();
	});

	$('.jiraPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
		lobiPanel.$options.expand = false;
		window.console.log("Unpinned");
		$(".jiraPanel").css({
			"left" : "150.5px",
			"top" : "122px",
			"height" : "80%",
			"width" : "80%"
		});
		$(".jiraPanel-body").css({
			"width" : "100%",
			"height" : "100%",
			"max-height" : "85%",
			"min-height" : "85%"
		});
		$("#featureWidget").css({
			"width" : "100%",
			"height" : "400px"
		});
		drawValueChart();
	});

	//$('.jiraPanel').on('loaded.lobiPanel', function(ev, lobiPanel){
	$('.jiraPanel .fa-refresh').on('click', function(ev, lobiPanel) {
		$('.jiraFeature').hide();
		$('.featureTitle').hide();
		$('#jiraFeatureLoadImg_id').show();
		drawValueChart();
	});

	google.charts.setOnLoadCallback(drawValueChart);
	var color = '#000'

	/*	$('#jiraFeatureLoadImg_id').show();*/

	function drawValueChart() {
		$http.get("devops/rest/jira").then(
				function(response) {

					$('#jiraFeatureLoadImg_id').hide();

					$('.jiraFeature').show();
					$('.featureTitle').show();

					var jsonresp = response.data;
					var data = google.visualization.arrayToDataTable([
							[ 'Task', 'Hours per Day' ],

							[ 'TODO', parseInt(jsonresp.todo) ],
							[ 'WIP', parseInt(jsonresp.wip) ],
							[ 'DONE', parseInt(jsonresp.done) ], ]);

					var featureTitle = jsonresp.sprintstartdate + ' TO '
							+ jsonresp.sprintenddate;
					$scope.jiraTitle = jsonresp.sprintname;
					drawPieChart(data, featureTitle, 'featureWidget');

				});
	}

	function drawPieChart(data, featureTitle, divContainer) {
		var options = {
			title : featureTitle,

			titleTextStyle : {
				color : '#999',
				fontName : 'Open Sans, Arial, Helvetica, sans-serif',
				fontSize : 12,
				italic : false
			},
			legend : {
				textStyle : {
					color : '#999',
					fontName : 'Open Sans, Arial, Helvetica, sans-serif',
					fontSize : 12,
					italic : false
				}
			},

			is3D : true,
			color : '#999'
		};

		var colors = {
			colors : [ '#ff884d', '#ffbb99', '#ffaa80' ]
		};
		$.extend(options, colors);

		var chart = new google.visualization.PieChart(document
				.getElementById(divContainer));
		chart.draw(data, options);
	}

});