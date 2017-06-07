(function() {
	angular.module("userDetailsModule").controller("userDetailsController",
			UserDetailsController);

	UserDetailsController.$inject = [ "userInfo", "$http", "$timeout" ]
	function UserDetailsController(userInfo, $http, $timeout) {
		var controller = this;
		controller.addressDetails = userInfo.addressDetails;
		controller.educationDetails = userInfo.educationDetails;
		controller.userDetails = userInfo.userDetailsModel;
		controller.updateAddressDetails = function(index, value){
			console.log(index, value)
		}
		controller.saveEducationDetails = function(education){
			var response = $http({
				method : "POST",
				data: {
					educationDetailsModel : education
				},
				url : "/DatingApplication/updateEducationDetails"
			}).success(function(data, status, headers, config) {
				controller.updateSuccessMsg = "Education details successfully updated.";
			})
			.error(function(data, status, headers, config) {
				console.log(data, status, headers, config);
				service.errorMessage = "Error while updating education details."
			});
			return response;
		}
		
		controller.saveAddressDetails = function(address){
			var response = $http({
				method : "POST",
				data: {
					addressDetailsModel : address
				},
				url : "/DatingApplication/updateAddressDetails"
			}).success(function(data, status, headers, config) {
				controller.updateSuccessMsg = "Address details successfully updated.";
			})
			.error(function(data, status, headers, config) {
				console.log(data, status, headers, config);
				service.errorMessage = "Error while updating address details."
			});
			return response;
		}
		controller.saveUserDetails = function(user){
			var response = $http({
				method : "POST",
				data: {
					userDetailsModel : user
				},
				url : "/DatingApplication/updatePersonalDetails"
			}).success(function(data, status, headers, config) {
				controller.updateSuccessMsg = "User details successfully updated.";
			})
			.error(function(data, status, headers, config) {
				console.log(data, status, headers, config);
				service.errorMessage = "Error while updating user details."
			});
			return response;
		}
	}
})();