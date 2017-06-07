//This function executes before loading the page.
$(function() {
	$(".editedValue").hide();
	$("#userdetailsjson").hide();
	$(".textField").hide();
	$(".completeBtn").hide();
	$(".navigation").hide();
	$(".navigation").show();
	/*$("#home_section").animate({
		"margin-left" : "0"
	});*/
	 $(".profileImg").hide();
	 $("#profilePicUploadMsg span:nth-child(even)").hide();
	//document.getElementsByClassName('nav_logo')[0].style.pointerEvents = 'none';
	 $("#profileSignUpComplete").css({"display":"none"});
});
// For the spin load icon before page loads
//For the spin load icon before page loads
document.onreadystatechange = function() {
	var currentState = document.readyState;
	if (currentState == 'interactive') {
		var image = new Image();
		image.src = "./Images/spin.gif";
		$("body").css({"background":'url(' + image.src + ') fixed center no-repeat', "opacity": "0"});
	} else if (currentState == 'complete') {
		$("body").css({"background":"none", "opacity": "1"});
	}
}

function displayServices(){
	$("#services>span").toggleClass("glyphicon-minus");
	$("#services_list").slideToggle();
	
}

$(function(){
	$("#contact").click(function(event){
		$("#contact>span").toggleClass("glyphicon-minus");
		$("#contact_medium").slideToggle();
	});
});

$(function(){
	$("#total_frn_req").click(function(event){
		$(this).find("#friendreq_count").remove();
	});
});

// For the menu item that is displayed on the click of an icon on left side
var isMarginMoved=true;
function displayMenu() {
	if(isMarginMoved){
		$("#home_section").animate({
			"margin-left" : "0"
		});
		$(".navigation").slideUp(500);
		isMarginMoved = false;
	}
	else if(!isMarginMoved){
		$("#home_section").animate({
			"margin-left" : "15%"
		});
		$(".navigation").slideDown(500);
		isMarginMoved = true;
	}
}
$(function() {
	$("#btn_status").click(function() {
		var counter = $(".statusholder").children().length + 1;
		var sentData = $("#status_inputbox").val(); //The data to be sent to the controller from client. Value comes from UI input box to input the status
		$.ajax({
			url : '/DatingApplication/poststatus',
			type : 'POST',
			datatype : "application/json",
			contentType : "text/plain",
			data : sentData,
			success : function(returndata) {
				if(returndata){
					var divElement = $("<div></div>");
					 $(".statusholder").prepend(divElement);
					$(divElement).addClass("newstatus"+counter).css({"margin-bottom": "10px"});
					var userNameBox = $("<div id='userId'></div>");
					var user = $(userNameBox).append($("#hiddenUserName").attr("value"));
					
					var image = $("<img>").attr("src", returndata.image);
					
					$(".newstatus"+counter).html("<div id='comments'>"+returndata.postedStatus+"<div>").prepend(image).prepend(user);
					$("#status_inputbox").val(""); //clearing input box
				}
			}
		});
	});
})
$(function(){
	$("#uploadBtn").change(function(){
		var formData = new FormData($("#imageUploadForm")[0]);
		$.ajax({
			url: '/DatingApplication/imgupload',
		    type: 'POST',
		    data: formData,
		    cache: false,
		    contentType: false,   //not mentioning contentType:false will cause the request is not MultiPartRequest in the controller
		    processData: false, 
		    success: function (returndata) {
		      $("#uploadedImage").attr("src", returndata);
		      $(".profileImg").show();
		      $(".imageuploadedmsg").text("The image has been successfully uploaded in the server.")
		      $("#profilePicUploadMsg div h2").hide();
		      $("#uploadedImgMsg").append("<h2>Image Uploaded</h2>");
		      $("#profilePicUploadMsg span:first").hide();
		      $("#profilePicUploadMsg span:nth-child(even)").show();
		      var imagePath = returndata;
		      $(".profile_pic").attr("src", imagePath);
		    }
		});
	});
});

function unfriendUser(user){
	var unfrienduser = {"userName" : user}
	$.ajax({
		url: '/DatingApplication/manageFriends/unFriendUser',
	    type: 'POST',
	    datatype : "application/json",
		contentType : "application/json",
	    data: JSON.stringify(unfrienduser),
	    success: function (returndata) {
    		$("#friendwrapper").css({"color":"red"});
    		$("#friendwrapper").html("User : "+user+" removed from friend list");
    		$("#friendwrapper").fadeOut(2000);
	    }
	});	
}


$(function(){
	$(".editDetailsEditLink").click(function(){
		 window.location =$(this).find("a").attr("href");
	});	
	
	$("parishram_logo").click(function(){
		window.location.href = "./";
	});
})

var homeApp = angular.module("myApp", ["ngMessages"]);
//the array is [ ] created to ease JavaScript minification
	homeApp.controller("mainController", ["$scope","$log",function($scope, $log){
	}]);

var usernamePasswordApp = angular.module("usernamePasswordApp", ["ngMessages"]);
usernamePasswordApp.controller("usernamePasswordController",["$scope","$log",function($scope, $log){
}]);

