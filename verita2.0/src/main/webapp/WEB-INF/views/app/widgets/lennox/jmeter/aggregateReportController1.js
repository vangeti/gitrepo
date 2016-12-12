mainApp.controller('aggregateReportController', function($scope,
              $sessionStorage, $rootScope, $http, widgetService) {

       $scope.tdTitle = 'Aggregate Report';

       $('.aggregateReportPanel').lobiPanel({
              // Options go here
              sortable : false,
              // editTitle : false,
              /* unpin : false, */
              reload : true,
              editTitle : false,
              minimize : false,
              unpin : {
                     icon : 'fa fa-thumb-tack'
              },
              /*
              * minimize : { icon : 'fa fa-chevron-up', icon2 : 'fa fa-chevron-down' },
              */
              reload : {
                     icon : 'fa fa-refresh'
              },
              close : {
                     icon : 'fa fa-times-circle'
              },
              expand : {
                     icon : 'fa fa-expand',
                     icon2 : 'fa fa-compress'
              }
       });

       $('.aggregateReportPanel').on('onPin.lobiPanel', function(ev, lobiPanel) {
              window.console.log("Pinned");
              $(".tdPanel-body").css({
                     "max-height" : "315px",
                     "min-height" : "315px"
              });
              $("#avgrespTime").css({
                     "width" : "370px",
                     "height" : "200px"
              });
              drawTable();
       });

       $('.aggregateReportPanel').on('onUnpin.lobiPanel', function(ev, lobiPanel) {
              lobiPanel.$options.expand = false;
              window.console.log("Unpinned");
              $(".tdPanel").css({
                     "left" : "150.5px",
                     "top" : "122px",
                     "height" : "80%",
                     "width" : "80%"
              });
              $(".tdPanel-body").css({
                     "width" : "100%",
                     "height" : "100%",
                     "max-height" : "85%",
                     "min-height" : "85%"
              });
              $("#avgrespTime").css({
                     "width" : "100%",
                     "height" : "300px"
              });
              drawTable();
       });

       $('.aggregateReportPanel').on(
                     'beforeClose.lobiPanel',
                     function(ev, lobiPanel) {
                           var widgetResp = widgetService.deleteWidget(
                                         $sessionStorage.userId, "tdPanelFlag");

                           widgetResp.then(function(resp) {

                                  if (resp.data.Status === "Success") {
                                         $rootScope.tdPanelFlag = false;
                                         $rootScope.widgetList.push({
                                                "key" : "tdPanelFlag",
                                                "value" : $scope.tdTitle
                                         });
                                  }
                           }, function(errorPayload) {

                           });
                     });

       $('.aggregateReportPanel').on('onFullScreen.lobiPanel',
                     function(ev, lobiPanel) {
                           window.console.log("value full event called");
                           $(".panel-body").css({
                                  "max-height" : "100%",
                                  "min-height" : "100%"
                           });    
                           $("#divScroll").css({
                                  "width" : "100%",
                                  "overflow-x": "hidden",
                                  "overflow-y": "auto",
                                  "max-height" : "100%",
                                  "min-height" : "100%"
                           });
                           drawTable();
                     });
       $('.aggregateReportPanel').on('onSmallSize.lobiPanel',
                     function(ev, lobiPanel) {
                           window.console.log("value small event called");
                           $(".panel-body").css({
                                  "max-height" : "300px",
                                  "min-height" : "300px"
                           });
                           $("#divScroll").css({                                  
                                  "height": "209px",
                                  "overflow-x": "hidden",
                                  "overflow-y": "auto",
                                  "width": "95%"
                           });
                           drawTable();
                     });
       
       drawTable();
       /* $('#perfTestDetailsLoadImg_id').show(); */
       function drawTable() {
              
              $http.get("lennox/rest/aggregateReport").then(function(response) {
                     console.log("inside Aggregate==");
                     $('#test_id').hide();

                     $('.perfTestDetailsTitle').show();
                     $('.perfTestDetailsTable').show();

                     var jsonresp = response.data;

                     //console.log("Aggregate==", jsonresp);

                     $scope.aggList = jsonresp;
                     $scope.backupList = jsonresp;

              });

       }
       
       $scope.applyFilter = function(keyword) {
              var tempList = $scope.aggList;
              var newList = new Array();
              if(keyword != '') {
                     for(var i = 0; i < tempList.length; i++) {
                       var transName = tempList[i].transactionName.toLowerCase();
                       if(transName.indexOf(keyword.toLowerCase()) > 0) {
                         newList.push(tempList[i]);
                       }
                     }             
                     $scope.aggList = newList;
              } else {
                     $scope.aggList = $scope.backupList;
              }
       };
       
       // create the list of sushi rolls
       $scope.sushi = [ {
              name : 'AU_ClearanceCenter_01_Lanuch.Transaction',
              count : 642,
              average : 321.96,
              line : 603.08,
              min : 1.01,
              max : 1537.58,
              error : 77.57
       }, {
              name : 'AU_ClearanceCenter_02_CIKSignIn.Transaction',
              count : 144,
              average : 91.00,
              line : 301.94,
              min : 0.92,
              max : 2604.89,
              error : 35.14
       }, {
              name : 'AU_ClearanceCenter_03_Login.Transaction',
              count : 111,
              average : 263.53,
              line : 809.94,
              min : 1.73,
              max : 870.32,
              error : 22.92
       }, {
              name : 'AU_ClearanceCenter_04_CIKOnClearanceCenter.Transaction',
              count : 489,
              average : 75.23,
              line : 24.90,
              min : 1.37,
              max : 1589.66,
              error : 6.34

       }, {
              name : 'AU_ClearanceCenter_05_SelectProduct.Transaction',
              count : 439,
              average : 23.86,
              line : 21.00,
              min : 0.91,
              max : 608.09,
              error : 6.38
       }, {
              name : 'AU_ClearanceCenter_06_AddToCart.Transaction',
              count : 123,
              average : 16.38,
              line : 14.65,
              min : 4.38,
              max : 302.44,
              error : 6.50
       }, {
              name : 'AU_ClearanceCenter_07_ViewCart.Transaction',
              count : 112,
              average : 4.67,
              line : 21.01,
              min : 0.96,
              max : 42.37,
              error : 7.14
       }, {
              name : 'AU_ClearanceCenter_08_PONumAdd.Transaction',
              count : 103,
              average : 4.45,
              line : 11.09,
              min : 0.81,
              max : 23.61,
              error : 6.80
       }, {
              name : 'AU_ClearanceCenter_09_ProceedToCheckOut.Transaction',
              count : 95,
              average : 4.39,
              line : 6.19,
              min : 1.61,
              max : 43.89,
              error : 4.21
       }, {
              name : 'AU_ClearanceCenter_10_ReviewOrder.Transaction',
              count : 90,
              average : 2.68,
              line : 3.11,
              min : 0.99,
              max : 22.30,
              error : 3.33
       }, {
              name : 'AU_ClearanceCenter_11_SubmitOrder.Transaction',
              count : 86,
              average : 3.91,
              line : 4.32,
              min : 1.47,
              max : 24.32,
              error : 2.33
       }, {
              name : 'AU_ClearanceCenter_12_ViewOrderStatus.Transaction',
              count : 84,
              average : 1.65,
              line : 1.68,
              min : 0.75,
              max : 25.82,
              error : 0.00
       }, {
              name : 'AU_Comm_01_Lanuch.Transaction',
              count : 710,
              average : 306.93,
              line : 602.46,
              min : 1.02,
              max : 1378.06,
              error : 78.45
       }, {
              name : 'AU_Comm_02_ClickSignIn.Transaction',
              count : 153,
              average : 60.94,
              line : 249.48,
              min : 0.94,
              max : 872.15,
              error : 16.34
       }, {
              name : 'AU_Comm_03_Login.Transaction',
              count : 128,
              average : 223.93,
              line : 840.32,
              min : 1.75,
              max : 2510.10,
              error : 32.03
       }, {
              name : 'AU_Comm_04_CIKComm.Transaction',
              count : 468,
              average : 167.79,
              line : 575.72,
              min : 0.93,
              max : 1131.73,
              error : 47.01
       }, {
              name : 'AU_Comm_05_SelectCategory.Transaction',
              count : 244,
              average : 18.29,
              line : 21.98,
              min : 1.01,
              max : 654.39,
              error : 10.25
       }, {
              name : 'AU_Comm_06_SelectType.Transaction',
              count : 777,
              average : 21.84,
              line : 21.34,
              min : 1.76,
              max : 904.59,
              error : 5.79
       } ];

});
