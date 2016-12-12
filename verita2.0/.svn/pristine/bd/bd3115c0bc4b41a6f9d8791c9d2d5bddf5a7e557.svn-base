mainApp.service('userProfileService', function($http) {
	return {
		addTestNameDetails : function(testName,startTime,endTime) {
			console.log('...addTestNameDetails...1....',testName);
			console.log('...addTestNameDetails...2....',startTime);
			console.log('...addTestNameDetails...3....',endTime);
			var dataObj = {
					
					testName: testName,
					startTime : startTime,
					endTime : endTime
				};
				return $http({
					method : "POST",
					url : "lennox/rest/userProfileSaveTestName",
					data : dataObj
				});
				
		},
		
		/*addTotalUsersDetails : function(totalUsers, startTime,endTime) {
			console.log('...addTotalUsersDetails...1....',totalUsers);
			console.log('...addTotalUsersDetails...2....',startTime);
			console.log('...addTotalUsersDetails...3....',endTime);
			var dataObj = {
					
					totalUsers: totalUsers,
					startTime : startTime,
					endTime : endTime
				};
				return $http({
					method : "POST",
					url : "lennox/rest/userProfileSaveTotalUsers",
					data : dataObj
				});
				
		},*/
		addScenariosDetails : function(scenarios,startTime,endTime,test_num) {
			console.log('...addScenariosDetails...1....',scenarios);
			console.log('...addScenariosDetails...2....',startTime);
			console.log('...addScenariosDetails...3....',endTime);
			var dataObj = {
					
					scenarios: scenarios,
					startTime : startTime,
					endTime : endTime,
					test_num  : test_num
				};
				return $http({
					method : "POST",
					url : "lennox/rest/userProfileSaveScenarios",
					data : dataObj
				});
				
		},
		addQaEnvUrlDetails : function(testEnvUrl,startTime,endTime,test_num) {
			console.log('...addQaEnvUrlDetails...1....',testEnvUrl);
			console.log('...addQaEnvUrlDetails...2....',startTime);
			console.log('...addQaEnvUrlDetails...3....',endTime);
			var dataObj = {
					
					testEnvUrl: testEnvUrl,
					startTime : startTime,
					endTime : endTime,
					test_num  : test_num
				};
				return $http({
					method : "POST",
					url : "lennox/rest/userProfileSaveTestEnvUrl",
					data : dataObj
				});
				
		},
		
		addQaTestDateDetails : function(testDate,startTime,endTime,test_num) {
			console.log('...addQaTestDateDetails...1....',testDate);
			console.log('...addQaTestDateDetails...2....',startTime);
			console.log('...addQaTestDateDetails...3....',endTime);
			var dataObj = {
					
					testDate: testDate,
					startTime : startTime,
					endTime : endTime,
					test_num  : test_num
				};
				return $http({
					method : "POST",
					url : "lennox/rest/userProfileSaveTestDate",
					data : dataObj 
				});
				
		}
		
		
	
	}
});