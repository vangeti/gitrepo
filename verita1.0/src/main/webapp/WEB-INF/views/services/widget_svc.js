mainApp
		.service(
				'widgetService',
				function($http) {
					return {
						deleteWidget : function(userId, widgetId) {
							console.log("--- in wid svc ---");
							return $http
									.get("devops/rest/addWidget/"+userId+"/"+widgetId);

						},
						addWidget : function(userId, widgetId) {
							console.log("--- in wid svc ---");
							return $http
									.get("devops/rest/delWidget/"+userId+"/"+widgetId);

						}
					}
				});
