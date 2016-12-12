mainApp.service('customwidgetService', function($http) {
	return {
	
		getcustomDefectDetails : function() {	
			return $http.get("devops/rest/customdefect");
		}
	}
});
