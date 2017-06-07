(function() {
	angular.module("adminMessage")
	.constant("baseUrl","admin/adminMessages")
	.service("adminMessageService", AdminMessageService);

	AdminMessageService.$inject = [ "$http", "baseUrl"];
	function AdminMessageService($http, baseUrl) {
		var service = this;
		service.getEmailDetails = function() {
			var response = $http({
				method : "GET",
				url : baseUrl
			});
			return response;
		}
		
		service.replycustomer = function(email_id, content, senderEmail){
			var response = $http({
				method : "POST",
				url : "admin/replycustomer",
				data : {
					emailId : email_id,
					content : content,
					sender : senderEmail
				}
			});
			return response;
		}
		
		service.deleteSelectedEmails = function(deleteEmailArray){
			var response = $http({
				method : "POST",
				url : "admin/deleteEmails",
				data : {
					emails : deleteEmailArray
				}
			});
			return response;
		}
		
		service.getEmailCount = function(){
			var response = $http({
				method : "POST",
				url : "admin/countemail",
			});
			return response;
		}
		
		service.updateEmailCount = function(email_id){
			var response = $http({
				method : "POST",
				url : "admin/updateemailcount",
				data : {
					emailId : email_id,
				}
			});
			return response;
		}
		//First create an empty array. Add all emails that we got after calling spring controller in that new array.
		service.addAllEmailDetailsInList = function(emailDetailsList){
			service.emails = [];
			for(var i =0; i< emailDetailsList.length; i++){
				service.emails.push(emailDetailsList[i]);
			}
		}
		//Once we have all emails in the new array, we will make sure if the array contains emails that are selected.
		//Emails are selected as ng-model = "detail.selected" in messageview.html
		//Iterate each emails that we got as response from controller and check if they are selected
		//If the email exists in the newly created array. Remove it from the array
			service.removeIfParticularEmailSelected = function(emailDetailsList, checkedEmailsToDelete) {
				var deleteEmail = [];
				service.addSelectedEmailInNewArray(emailDetailsList, deleteEmail, checkedEmailsToDelete);
					//delete from server
					service.handleDeletedEmailPromise(deleteEmail)
					//get email count from server
					//service.handleEmailCountPromise();
			}
			
			service.handleDeletedEmailPromise = function(deleteEmail){
				var deleteEmailPromise = service.deleteSelectedEmails(deleteEmail);
				if(deleteEmail.length <=0 ){
					return service.errorMessage();
				}
			
					for(var i=service.emails.length; i>=0 ; i--){
						//delete from the screen
						var index = service.emails.indexOf(deleteEmail[i]);
						if(( index != -1)){
							service.emails.splice(index, 1);
						}
					}		
			}
			
			service.errorMessage = function(){
				return "Cannot perform the operation.";
			}
			
			
			service.addSelectedEmailInNewArray = function(emailDetailsList, deleteEmail, checkedEmailsToDelete){
				angular.forEach(emailDetailsList, function(email) {
					var checkedEmailsIndex = checkedEmailsToDelete.indexOf(email.email_id)
					if(email.selected || checkedEmailsIndex > -1) {
						var index = service.emails.indexOf(email);
						
						if (index > -1 || (checkedEmailsIndex > -1)) {
							deleteEmail.push(email);
						}
					}
				});
				
			}
		
			service.currentStatus = [];
			service.updateStatus = function(value){
				var index = value.index;
				if(index >= 0){
					service.currentStatus.push(index);
					console.log(service.currentStatus)
				}
			}
			
		//Return new array with the emails that are selected by removing them
		service.newEmailList = function(){
			return service.emails;
		}
	}
})();