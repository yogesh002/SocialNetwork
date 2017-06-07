(function(){
	angular.module("userDetailsModule")
	.service("userDetailService", UserDetailService);
	
	UserDetailService.$inject=["$http"]
	function UserDetailService($http){
		var service = this;
		service.personalDetails = function(url){
			var response = $http({
				method : "GET",
				url : url
			});
			return response;
		}
	}
})();