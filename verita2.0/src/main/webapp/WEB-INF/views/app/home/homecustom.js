mainApp.controller('customdashboard', function($scope, $sessionStorage,
              $rootScope, $http, $state, widgetService) {
//     $http.get("devops/rest/lastFiveBuilds").then(
       $http.get("devops/rest/alltestnos").then(
                     function (response)
                     {
                           //alert(response.data);
                           $scope.testnam=response.data;
                           $scope.testsuite=response.data; //added by sankar
                           console.log($scope.testnam); 
                           //alert($scope.testname[0].sno);
                           console.log("*)*",$scope.testnam);
//                           $scope.widgets =  $scope.testnam;
                           /*[ {test_name: 'test1', test_date:'10/10/16', users: 10, test_num: 111}, 
                                              {test_name: 'test2', test_date:'11/10/16', users: 20, test_num: 222}, 
                                              {test_name: 'test3', test_date:'12/10/16', users: 30, test_num: 333}, 
                                              {test_name: 'test4', test_date:'13/10/16', users: 40, test_num: 444},
                                              {test_name: 'test5', test_date:'14/10/16', users: 50, test_num: 555},
                                              {test_name: 'test6', test_date:'15/10/16', users: 60, test_num: 666}
                                            ];*/
                     }
       );
              
       $http.get("devops/rest/lastFiveBuilds").then(
                     function (response)
                     {
                    	   //$scope.testname=response.data;
                    	   $scope.widgets =  response.data;
                    	 //alert(response.data);
                           //$scope.testname=response.data;
                     //     alert($scope.testname[0].sno);
                     }
       );
       //$sessionStorage.test_num = "";
       
       $scope.submit = function() {
       //     alert("hi");
       //       $rootScope.$on('$stateChangeSuccess', function (evt, toState) {
              //       alert("hiiiiiiii");
                         // if (toState.name === 'nv.logout') {
                        //    $grabhutAccountService.logoutUser();
    	   $sessionStorage.test_num= $scope.testTo.testname;
    	   console.log("***",$sessionStorage.test_num);
                            $state.go('dashboard');
                        //  }  
              //        });
       }
       
       $scope.viewInDashboard = function(test_num) {
              console.log('test_num: ' + test_num);
            //  alert(test_num);
              $sessionStorage.test_num = test_num;
              $state.go('dashboard');
       }

});
