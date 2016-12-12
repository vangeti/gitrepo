/**
 * 
 */
mainApp.controller('compare', function($scope, $sessionStorage,
              $rootScope, $http, $state, widgetService) {
  $http.get("devops/rest/alltestnos").then(
		  function (response)
          {
                $scope.testname=response.data;               
          });
  $scope.compare=function() {
	  console.log("value",$scope.test_to);
	  
	  $('#textDiv').hide();
	  $('#widgetDiv').show();
	  $sessionStorage.test_to_number= $scope.test_to.testname;
	  $sessionStorage.test_to_nameToDisplay= $scope.test_to.testsuite;
	  $sessionStorage.testwith_number=$scope.testwith.testname;
	  $sessionStorage.testwith_nameToDisplay= $scope.testwith.testsuite;
	  //$scope.test_to=$scope.test_to.testname;
	 // $scope.test_with=$scope.testwith.testname;
  };
  $scope.$watch('test_to',function()
{//alert("welcome");
},true);
});
