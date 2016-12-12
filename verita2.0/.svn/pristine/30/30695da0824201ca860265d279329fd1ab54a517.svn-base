mainApp.controller('testerObservationsController',
		function($scope, $sessionStorage, $state, testerObservationService) {

			var userInfo = $sessionStorage.userInfo;
			
			var widgets = [];
			for(var key in userInfo)
			{
				var value = userInfo[key];				
				widgets.push({key : key, value : value[0].title});
			} 		
			$scope.widgets = widgets;
			
			$scope.submitComment = function(){
				console.log('Test submit button');
				
				var widgetName = $scope.widgetName;
				var comment = $scope.comment;
				var response = testerObservationService.addTesterObservation($sessionStorage.username,$sessionStorage.buName,$sessionStorage.projectName,widgetName,comment);
				response.then(function(resp) {
					alert('success');
				}, function(errorPayload) {					
					alert('Failed');
				});
			}
			
			$scope.onChange = function(){
				console.log('on change');
				
				var widgetName = $scope.widgetName;
				
				var response = testerObservationService.getTesterObservation(widgetName);
				
				response.then(function(resp) {
					console.log('resp == ', resp.data);
					$scope.comment = resp.data;
				}, function(errorPayload) {					
					console.log('Failed');
				});
				
			}
			
			$scope.onChange = function(){
				console.log('on change');
				
				var widgetName = $scope.widgetName;
				
				var response = testerObservationService.getTesterObservation(widgetName);
				
				response.then(function(resp) {
					console.log('resp == ', resp.data);
					$scope.comment = resp.data;
				}, function(errorPayload) {					
					console.log('Failed');
				});
				
			}
			

});