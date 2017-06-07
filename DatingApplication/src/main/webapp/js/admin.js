function editUserRole(obj) {
	var loginId = $(obj).parent().siblings().find("input").val();
	var role = $(obj).parent().siblings().find("#role").find("option:selected").val();
	var user = {
		"loginId" : loginId,
		"role" : role
	}
	$.ajax({
		url : '/DatingApplication/editRole',
		type : 'POST',
		contentType : "application/json",
		data : JSON.stringify(user),
		cache : false,
		success : function(returndata) {
			if(returndata.toLowerCase() !== "failure"){
				$(obj).parent().siblings(".currentrole").html(returndata);
				$("#msg").fadeIn(100);
				$("#msg").html("Update successful");
				$("#msg").css({"color":"red", "font-size":"28px", "text-align":"center"});
				$("#msg").fadeOut(5000);
			}
		}
	});
}


function deleteUser(obj) {
	var loginId = $(obj).parent().siblings().find("input").val();
	$.ajax({
		url : '/DatingApplication/deleteUsers',
		type : 'POST',
		contentType : "application/json",
		data : JSON.stringify(loginId),
		cache : false,
		success : function(returndata) {
			if(returndata.toLowerCase() !== "failure"){
				$(obj).parent().siblings(".currentrole").html(returndata);
				$("#msg").fadeIn(100);
				$("#msg").html("User Deleted");
				$("#msg").css({"color":"red", "font-size":"28px", "text-align":"center"});
				$("#msg").fadeOut(5000);
				$(obj).parents(".userdetails").hide();
			}
			else{
				$("#msg").html("Unable to delete the user. Please try again later.");
				$("#msg").css({"color":"red", "font-size":"28px", "text-align":"center"});
				$(obj).parents(".userdetails").hide();
			}
		}
	});
}

$(function(){
	var totalMessage = $("#newmsgcount").text();
	if(totalMessage > 0){
		 $(".fa-envelope").css({
			 "color":"blue",
			 "font-size":"20px",
			 "margin-left":"10px",
			 "opacity":"0.8"
		 });
		 $("#newmsgcount").css({
			 "color":"white",
			 "padding":"2px",
			 "font-size":"smaller",
			 "position":"relative",
			 "left":"-3px",
			 "background-color":"red",
			 "vertical-align":"super"
		 })
	}
});
