mainApp.service('customwidgetService', function($http) {
	return {
	
		getcustomDefectDetails : function() {	
			return $http.get("devops/rest/customdefect");
		},
		postCustomWidgetDetails : function(dataObj) {	
			return $http({
				method : "POST",
				url : "devops/rest/addCustomWidget",
				data : dataObj
			});
		}
	}
});
