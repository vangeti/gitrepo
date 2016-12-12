mainApp.service('loginService', function($http) {
	return {
		getLogin : function(userName, passWord) {
			
		},
		postLogin : function(userName, passWord,domain_id,project_id) {
			

			var dataObj = {
				userName : userName,
				password : passWord,
				domainID : domain_id,
				projectID: project_id
				
			};
			return $http({
				method : "POST",
				url : "devops/rest/logincheck",
				data : dataObj
			});
			
		},
		
		getDomainDetails : function() {	
			return $http.get("devops/rest/getDomains");
		},
		
		getProjectDetails : function() {	
			return $http.get("devops/rest/getProjects");
		}
	}
});
