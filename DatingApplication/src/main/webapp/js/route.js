(function() {
	angular.module("adminMessage").config(AdminMsgConfiguration);

	AdminMsgConfiguration.$inject = [ "$stateProvider", "$urlRouterProvider" ]
	function AdminMsgConfiguration($stateProvider, $urlRouterProvider) {
		$urlRouterProvider.otherwise("/");
		$stateProvider.state("adminMsg", {
			url : "/adminMsg",
			templateUrl : "html/admin/messageview.html",
			controller : "adminMessageController as controller"
		}).state("readmsg", {
			url : "/adminMsg/{msg_id}",
			templateUrl : "html/admin/readmessage.html",
			controller : "readMsgController as controller",
			resolve : {
				messageDetails : ["$stateParams", "adminMessageService", "baseUrl", function($stateParams, adminMessageService, baseUrl){
					var details = adminMessageService.getEmailDetails(baseUrl);
					return details.then(function(response){
						var result = response.data.incomingEmailDetails;
						var emailInfo = result[$stateParams.msg_id];
						return emailInfo;
					})
				}]
			}
		});
	}
})();