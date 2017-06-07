function addFriend(){
$.ajax({
url: '/DatingApplication/manageFriends/addFriends',
type: 'POST',
data : "",
contentType: false,
dataType : false,
success: function (returndata) {
	var inputElement = "<input type='button' class='btn btn-lg btn-success' value = 'Friend Request Sent'>";
	$(".friendreqbtn").append($(inputElement));
	$(".friendreqbtn").html(inputElement);
},
error: function (xhRequest, ErrorText, thrownError) {
      console.log("xhRequest:" , xhRequest ,"\n", 'ErrorText: ' , ErrorText , "\n", 'thrownError: ' , thrownError , "\n");
	}
});
}

$(function() {
$(".friendreqbtn").on("mouseover", "input", function() {
var currentButton = $(".friendreqbtn input").attr("value");
var check = currentButton.toLowerCase();
if (check.indexOf("sent") > -1) {
	$(".friendreqbtn input").attr("value", "Cancel Friend Request");
	$(".friendreqbtn input").addClass("btn btn-danger");
}
});
$(".friendreqbtn").on("click", "input", function() {
var currentButton = $(".friendreqbtn input").attr("value");
var check = currentButton.toLowerCase();
if (check.indexOf("cancel") > -1) {
	$(".friendreqbtn input").remove();
	cancelFriendRequest();
}
});

$(".friendreqbtn").on("mouseout", "input", function() {
var currentButton = $(".friendreqbtn input").attr("value");
var check = currentButton.toLowerCase();
if (check.indexOf("cancel") > -1) {
	$(".friendreqbtn input").attr("value", "Friend Request Sent");
	$(".friendreqbtn input").removeClass("btn btn-danger");
}
});

$(".friendreqbtn").on("click", "input", function() {
var currentButton = $(".friendreqbtn input").attr("value");
if (typeof (currentButton) != "undefined") {
	var check = currentButton.toLowerCase();
	if (check.indexOf("add") > -1) {
		$(".friendreqbtn input").remove();
		addFriend();
		}
	}
});
})

function cancelFriendRequest(){
$.ajax({
url: '/DatingApplication/manageFriends/cancelFriendRequestSent',
type: 'POST',
success: function (returndata) {
	if(returndata.toLowerCase() === "success"){
		var inputElement = "<input type='button' class='btn btn-lg btn-info' value = 'Add Friend'>";
		$(".friendreqbtn").append($(inputElement));
	}
},
error: function (xhRequest, ErrorText, thrownError) {
      console.log("xhRequest:" , xhRequest ,"\n", 'ErrorText: ' , ErrorText , "\n", 'thrownError: ' , thrownError , "\n");
	}
});
}

function acceptFriendRequest(obj) {
var selector = $(obj).siblings("span");
var userName = $(selector).text();
$.ajax({
url : '/DatingApplication/manageFriends/acceptFriendRequest',
type : 'POST',
data : JSON.stringify(userName),
contentType : 'application/json',
dataType : 'text',
success : function(returndata) {
	if (returndata.toLowerCase() === "success") {
		$(obj).hide();
		$(obj).siblings("input").hide();
		$(selector).html("Friend Request Accepted : " + userName);
		$(selector).fadeOut(2000, function(){
			$(".frn_req_list").hide();
		});
	}
},
error : function(xhRequest, ErrorText, thrownError) {
	console.log("xhRequest:", xhRequest, "\n", 'ErrorText: ',
			ErrorText, "\n", 'thrownError: ', thrownError, "\n");
	}
});
};

function rejectFriendRequest(obj) {
var selector = $(obj).siblings("span");
var userName = $(selector).text();
$.ajax({
url : '/DatingApplication/manageFriends/rejectFriendRequest',
type : 'POST',
data : JSON.stringify(userName),
contentType : 'application/json',
dataType : 'text',
success : function(returndata) {
	if (returndata.toLowerCase() === "success") {
		$(obj).hide();
		$(obj).siblings("input").hide();
		$(selector).html("Friend Request Rejected : " + userName);
		$(selector).fadeOut(2000, function(){
			$(".frn_req_list").hide();
		});
	}
},
error : function(xhRequest, ErrorText, thrownError) {
	console.log("xhRequest:", xhRequest, "\n", 'ErrorText: ',
			ErrorText, "\n", 'thrownError: ', thrownError, "\n");
}
});
};

$(function(){
	$("#contactBox").hide();
	$("#send_text_btn").click(function(){
		$("#contactBox").fadeIn(2000);
		$("#send_text_btn").hide();
		$("#cancelBtn").click(function(){
			$("#contactBox").fadeOut(200, function(){
				$("#send_text_btn").show();
			})
		})
	});
})
function sendText(){
	var number = "+1"+$("#user_phone_number").val();
	var message = $(".messagebox").val()+"\n"+"From: "+number;
	var inputData = {
			message : message 
	}
	$.ajax({
	url: '/DatingApplication/sendText',
	type: 'POST',
	datatype : "application/json",
	contentType : "application/json",
	data : JSON.stringify(inputData),
	success: function (returndata) {
		if(returndata === 'success'){
			$("#contactBox").hide();
			$("#text_successmsg").html("Your text has been sent.")
		}
		else{
			$("#text_successmsg").html("Unable to send text");
			$("#contactBox").show();
		}
	},
	error: function (xhRequest, ErrorText, thrownError) {
			$("#text_successmsg").html("Unable to send text");
			$("#contactBox").show();
	      console.log("xhRequest:" , xhRequest ,"\n", 'ErrorText: ' , ErrorText , "\n", 'thrownError: ' , thrownError , "\n");
		}
	});
	}
