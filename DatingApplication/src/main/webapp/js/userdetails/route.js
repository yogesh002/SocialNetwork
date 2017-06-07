(function() {
	angular.module("userDetailsModule").config(UserDetailsConfiguration);
	UserDetailsConfiguration.$inject = [ "$stateProvider", "$urlRouterProvider" ]
	function UserDetailsConfiguration($stateProvider, $urlRouterProvider) {
		$urlRouterProvider.otherwise("/");
		$stateProvider.state("updatepersonalinfo", {
			url : "/updateuserdetails",
			templateUrl : "html/usersettings/settings.html",
			controller : "userDetailsController as controller",
			resolve : {
				userInfo : ["userDetailService",function(userDetailService){
					var userDetails = userDetailService.personalDetails("updatepersonalInfo");
					return userDetails.then(function(response){
						var details = response.data;
						return details;
					})
				}]
			}
		})
		.state("updateAddressDetails", {
			url : "/updateAddressDetails",
			templateUrl : "html/usersettings/addressdetails.html",
			controller : "userDetailsController as controller",
			resolve : {
				userInfo : ["userDetailService",function(userDetailService){
					var userDetails = userDetailService.personalDetails("updatepersonalInfo");
					return userDetails.then(function(response){
						var details = response.data;
						return details;
					})
				}]
			}
		})
		.state("updateeducationdetails", {
			url : "/updateEducationDetails",
			templateUrl : "html/usersettings/educationdetails.html",
			controller : "userDetailsController as controller",
			resolve : {
				userInfo : ["userDetailService",function(userDetailService){
					var userDetails = userDetailService.personalDetails("updatepersonalInfo");
					return userDetails.then(function(response){
						var details = response.data;
						return details;
					})
				}]
			}
		})
	}
})();