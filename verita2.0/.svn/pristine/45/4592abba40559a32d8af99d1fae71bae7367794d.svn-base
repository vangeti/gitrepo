mainApp.service('testUpdateService', function($http) {
	return {
		addTestUpdateDetails : function(comment_name, user_name,test_type,test_num) {
			console.log('...service...1....',comment);
			console.log('..test_type....',test_type);
			console.log('The test no printing is is the is  :',test_num);
			var dataObj = {
					comment_name : comment_name,
					user_name : user_name,
					test_type : test_type,
					test_num  : test_num
					
				};
			
			//console.log('The test no printing is is the is  :',test_num);
				return $http({
					method : "POST",
					url : "devops/rest/addTestUpdate",
					data : dataObj
				});
				
		},
		
		
		addTestUpdateDetailsCompare : function(comment_name, user_name,test_type,testto,testwith) {
			//console.log('...service...1....',comment);
			console.log('..test_type....',test_type);
			console.log('The test no printing is test updates compare  :',testto);
			console.log('The test no printing is test updates compare  :',testwith);
			var dataObj = {
					comment_name : comment_name,
					user_name : user_name,
					test_type : test_type,
					testto  : testto,
					testwith:testwith
					
				};
			
			//console.log('The test no printing is is the is  :',test_num);
				return $http({
					method : "POST",
					url : "devops/rest/addTestUpdateDetailsCompare",
					data : dataObj
				});
				
		},
		
		saveTestUpdateDetails : function(id,comment_name, user_name,comment_date) {
			console.log('...service...1....',comment_name);
			console.log('...service...2....',user_name);
			var dataObj = {
					id : id,
					comment_name : comment_name,
					user_name : user_name,
					comment_date : comment_date
				};
				return $http({
					method : "POST",
					url : "devops/rest/saveTestUpdate",
					data : dataObj
				});
				
		},		
		saveTestNameUpdateDetails : function(testName, scenario_name) {
			console.log('...test Name....',testName);
			console.log('...Modified Test Scenario...',scenario_name);
			var dataObj = {
					testName : testName,
					scenarios : scenario_name 
				};
				return $http({
					method : "POST",
					url : "devops/rest/saveTestNameUpdate",
					data : dataObj
				});
				
		},	
		
		saveDateTimeZonedateDetails : function(testName, testdatetimezone) {
			console.log('...test Name....',testName);
			console.log('...Modified Test Scenario...',testdatetimezone);
			var dataObj = {
					testName : testName,
					testdatetimezone : testdatetimezone
				};
				return $http({
					method : "POST",
					url : "devops/rest/saveDateTimeZoneUpdate",
					data : dataObj
				});
				
		},	
		
		
		saveupdateScenariosDetails : function(testName, testscenarios) {
			console.log('...test Name....',testName,testscenarios);
			console.log('...Modified Test Scenario...',testscenarios);
			var dataObj = {
					testName : testName,
					testscenarios : testscenarios
				};
				return $http({
					method : "POST",
					url : "devops/rest/updateScenarios",
					data : dataObj
				});
				
		},	
		
		
		
		saveupdateQaEnvDetails : function(testName, testenvurl) {
			console.log('...test Name....',testName,testenvurl);
			console.log('...Modified Test testenvurl...',testenvurl);
			var dataObj = {
					testName : testName,
					testenvurl : testenvurl
				};
				return $http({
					method : "POST",
					url : "devops/rest/updateEnvUrl",
					data : dataObj
				});
				
		},
		
		
		
		
		deleteTestUpdate : function(id) {
			console.log('id....',id);		
			var dataObj = {
					id : id,
				};
				return $http({
					method : "POST",
					url : "devops/rest/delTestUpdate",
					data : dataObj
				});
				
		},//Edited by Sankar
		retreiveTestUpdateDetails : function(test_type, test_num) {
			var dataObj = {
					test_type : test_type,
					test_num  : test_num
				};
				return $http({
					method : "POST",
					url : "devops/rest/retreiveTopFiveTestUpdateDetails",
					data : dataObj
				});
				
		},
		
		retreiveTestUpdateDetailsCompare : function(test_type, testwith,testto) {
			var dataObj = {
					test_type : test_type,
					testwith  : testwith,
					testto    :testto
				};
				return $http({
					method : "POST",
					url : "devops/rest/retreiveTopFiveTestUpdateDetailsCompare",
					data : dataObj
				});
				
		}
	
	}
});
