mainApp.service('testerObservationService', function($http) {
	return {
		addTesterObservation : function(username,buName,projectName,widgetName, comment) {
			console.log('...widget_name...1....',widgetName);
			console.log('...comment...2....',comment);
			var dataObj = {
					userName : username,
					buName : buName,
					projectName : projectName,
					widget : widgetName,
					comment : comment
				};
				return $http({
					method : "POST",
					url : "devops/rest/addTesterObservation",
					data : dataObj
				});
				
		},
		getTesterObservation : function(widgetName) {
		//	console.log('...widget_name...1....',widgetName);			
				return $http({
					method : "GET",
					url : "devops/rest/getTesterObservationsComment?widgetFlag=" + widgetName
				});
				
		},
		getTesterObservations : function() {
		//	console.log('...get all...');			
				return $http({
					method : "GET",
					url : "devops/rest/getTesterObservations"
				});
				
		}
	
	
	
	
	}
});
