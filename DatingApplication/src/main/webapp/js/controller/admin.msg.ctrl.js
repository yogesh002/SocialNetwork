(function() {
	angular.module("adminMessage")
	.controller("adminMessageController", adminMessageControllerFunction)
	.controller("readMsgController", ReadMsgController);
	
	adminMessageControllerFunction.$inject = ["adminMessageService", "$location", "$window", "$scope"]
	function adminMessageControllerFunction(adminMessageService, $location, $window, $scope) {
		var controller = this;
		var service = adminMessageService;
		var emailCountPromise = service.getEmailCount();
		
		emailCountPromise.then(function(response){
			controller.totalNewEmails = response.data.newEmails;
			controller.totalEmails = response.data.totalEmails;
			controller.originalTotalEmails = controller.totalEmails;
			if(controller.totalEmails <= 0){
				controller.errorMessage = "No Mail";
			}
		});

		var emailDetailsPromise = service.getEmailDetails();
		emailDetailsPromise.then(function(response){
			controller.emailDetails = response.data.incomingEmailDetails;
		})
		
		controller.currentStatus = service.currentStatus;
		controller.updateStatus = function(value){
			service.updateStatus(value)
			};
			//Trash email on click of trash icon
			controller.trashEmails = function(){
				service.addAllEmailDetailsInList(controller.emailDetails);
				service.removeIfParticularEmailSelected(controller.emailDetails, controller.selectedEmailsToDelete);
				var emailCountPromise = service.getEmailCount();
				emailCountPromise.then(function(response){
					controller.isTrashed = true;
					controller.totalNewEmails = response.data.newEmails;
					controller.totalEmails = response.data.totalEmails;
					if(controller.isTrashed && controller.originalTotalEmails <=0){
						controller.errorMessage = service.errorMessage();
					}
					else if(controller.isTrashed && controller.originalTotalEmails <= 0){
						controller.errorMessage = "There are no more messages";
					}
				});
				controller.emailDetails = service.newEmailList();
			}
			controller.selectedEmailsToDelete = [];
			$scope.checkEmailsToDelete = function(emailId){
				controller.selectedEmailsToDelete.push(emailId);
			};
			
			//Go back on click of back button
			controller.goBack = function(){
				$location.url("/");
				var newUrl = $window.location.pathname;
				$window.location = newUrl;
			}
			
	}
	//Reading emails once selected
	ReadMsgController.$inject = ["messageDetails", "adminMessageService"]
	function ReadMsgController(messageDetails, adminMessageService){
		var controller = this;
		controller.email_id = messageDetails.email_id;
		controller.getSenderEmail = messageDetails.from_user;
		controller.getSubject = messageDetails.subject;
		controller.getMessage = messageDetails.message;
		controller.sentDate = messageDetails.sent_date;
		controller.replymsg = "";
		//mark email as read now
		//Reply to the email
		controller.replycustomer = function(content){
			return adminMessageService.replycustomer(controller.email_id, content,controller.getSenderEmail);
		}
		controller.discardMsg = function(message){
			if(message != null){
				confirm("Discard Message?")
				controller.replymsg = "";
			}
		}
		controller.updateEmailCount = function(emailId){
			adminMessageService.updateEmailCount(emailId);
		}
		controller.updateEmailCount(controller.email_id);
	}
})();