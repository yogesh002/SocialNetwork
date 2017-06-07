$(function() {
$("#comment_btn").click(function() {
	var counter = $(".imgcommentdisplaybox").children().length+1;
	var image =  $("#profile_image").attr("src");
	var comment =  $("#imgcommentbox").val();
	var commentsResponse = {"image" : image, "comment":comment};
	$.ajax({
		url : '/DatingApplication/postimgcomment',
		type : 'POST',
		data : JSON.stringify(commentsResponse),
		contentType : "application/json", //The type of data we are sending is javascript object - JSON
	    dataType : 'text', //The type of data we are receiving from controller back to javascript is String. So, it is text
		success : function(returndata) {
		if(returndata === "success"){
			if(returndata == "success"){
				var divElement = $("<div></div>");
				 $(".imgcommentdisplaybox").prepend(divElement);
				$(divElement).addClass("cmt_newstatus"+(counter));
				var userDiv = $("<div class= 'comment_username'></div>");
				var user = $("#hiddenUserName").attr("value");
				var result =$(".cmt_newstatus"+(counter)).html("<div id='comment_comment'>"+comment+"</div>").prepend($(userDiv).html(user));
				$("#imgcommentbox").val(""); 
				}
			}
		},
		error: function (xhRequest, ErrorText, thrownError) {
		      console.log("xhRequest:" , xhRequest ,"\n", 'ErrorText: ' , ErrorText , "\n", 'thrownError: ' , thrownError , "\n");
		}
	});
});

var imageSrc =  $("#profile_image").attr("src");
if(imageSrc === ""){
	$("#img_component").hide();
	$(".img_add_btn").show();
	$("#img_del_btn").hide();
	$("#img_update_btn").hide();
	$(".preference_btn").hide();
}
else{
	$(".img_add_btn").hide();
	$("#img_component").show();
}
$("#img_del_btn").click(function(event){
	event.preventDefault();
		confirm('You are going to delete this picture. Do you want to proceed?');
		var profilePicture = {
			profilePicture : imageSrc
		}
		$.ajax({
			url : '/DatingApplication/deleteImage',
			type : 'POST',
			data : JSON.stringify(profilePicture),
			contentType : "application/json", //The type of data we are sending is javascript object - JSON
		    dataType : 'text', //The type of data we are receiving from controller back to javascript is String. So, it is text
			success : function(returndata) {
			if(returndata.toLowerCase() === "success"){
				$("#img_del_btn").hide();
				$(".img_add_btn").show();
				$("#img_component").hide();
				$("#img_update_btn").hide();
				$(".avatar img").hide();
				$(".preference_btn").hide();
			}
			},
			error: function (xhRequest, ErrorText, thrownError) {
			      console.log("xhRequest:" , xhRequest ,"\n", 'ErrorText: ' , ErrorText , "\n", 'thrownError: ' , thrownError , "\n");
			}
	});		
});

$(function(){
	$("#uploadProfilePicBtn").change(function(){
		var formData = new FormData($("#imageUploadForm")[0]);
		$.ajax({
			url: '/DatingApplication/addUpdateProfilePicture',
		    type: 'POST',
		    data: formData,
		    cache: false,
		    contentType: false,   //not mentioning contentType:false will cause the request is not MultiPartRequest in the controller
		    processData: false, 
		    success: function (returndata) {
			    $("#profile_image").attr("src", returndata);
			  	$("#img_del_btn").show();
				$(".img_add_btn").hide();
				$("#img_update_btn").show();
				$(".avatar img").show();
				$(".preference_btn").show();
		    }
		});
	});
});

$(function(){
	$("#thumbsup").click(function(){
		var image =  $("#profile_image").attr("src");
		var commentsResponse = {"image" : image};
		$.ajax({
			url: '/DatingApplication/likeController',
		    type: 'POST',
		    data: JSON.stringify(commentsResponse),
		    cache: false,
		    contentType : "application/json", 
		    dataType : 'text',
		    success: function (returndata) {
		    	if(returndata != -1){
		    	$("#thumbsup").css("color", "blue");
		    	$("#thumbsupcounter").html(returndata).css({
					"color" : "#AD1457",
					"font-weight" : "bolder"
				});
		    	}
		    },
		    error: function (xhRequest, ErrorText, thrownError) {
			      console.log("xhRequest:" , xhRequest ,"\n", 'ErrorText: ' , ErrorText , "\n", 'thrownError: ' , thrownError , "\n");
				}
			});
		});
	});

$(function(){
	$("#likeCount").click(function(){
		var image =  $("#profile_image").attr("src");
		var commentsResponse = {"image" : image};
		$.ajax({
			url: '/DatingApplication/showFriendsWhoLiked',
		    type: 'POST',
		    data: JSON.stringify(commentsResponse),
		    cache: false,
		    contentType : "application/json", 
		    dataType : 'text',
		    success: function (returndata) {
		    	if(returndata !== null){
		    		$("#listOfFriends").html("");
		    		var jsonData = JSON.parse(returndata);
		    		var item = null;
		    		var uoList = "<ul></ul>";
		    		for (var i = 0; i < jsonData.length; i++) {
		    			item = $(uoList).append("<li class='friendsLikeList'>"+ jsonData[i].likedBy + "</li>");
		    			item.css({"cursor":"pointer"})
		    			var link = "<a></a>";
		    			$("#listOfFriends").append($(link).prepend(item));
		    		}
		    	}
		    },
		    error: function (xhRequest, ErrorText, thrownError) {
			      console.log("xhRequest:" , xhRequest ,"\n", 'ErrorText: ' , ErrorText , "\n", 'thrownError: ' , thrownError , "\n");
				}
			});
		});
	});

$(function(){
	$("#listOfFriends").on("click", ".friendsLikeList", function(){
		var userName = $(this).text();
		$.ajax({
			url: '/DatingApplication/findUser',
		    type: 'POST',
		    data: JSON.stringify(userName),
		    cache: false,
		    contentType : "application/json", 
		    dataType : 'text',
		    success: function (returndata) {
		    	 	document.open();
		    	    document.write(returndata);
		    	    document.close();
		    },
		    error: function (xhRequest, ErrorText, thrownError) {
			      console.log("xhRequest:" , xhRequest ,"\n", 'ErrorText: ' , ErrorText , "\n", 'thrownError: ' , thrownError , "\n");
				}
			});
		
	});
});
});

function toggleCommentBox(){
	$("#img_component").slideToggle();
}
