$(document).ready(function(){
	InitializeStaffLoginPage();
	$("form").submit(StaffLoginValidator);
});
function InitializeStaffLoginPage(){
	var blIsCheck = $.cookie("isCheck") == "true";
	$("#txtLoginName").val($.cookie("loginName"));
	$("#txtPassword").val($.cookie("passWord"));
	$("#btnRemeberPwd").attr("checked",blIsCheck);
};
function StaffLoginValidator(){
	var isChecked = $("#btnRemeberPwd").is(":checked");
	if(isChecked){
		$.cookie("loginName",$("#txtLoginName").val(),{expires:365});
		$.cookie("passWord",$("#txtPassword").val(),{expires:365});
	}else{
		$.cookie("loginName",null);
		$.cookie("passWord",null);
	}
	$.cookie("isCheck",isChecked,{expires:365});
	return excute_form_validator();
};

function fnPasswordError(){
	$._messengerDefaults = {extraClasses: 'messenger-fixed messenger-theme-block messenger-on-top'};
	window.parent.Messenger().post({
		message:"登录失败，用户名或密码错误！"
	   ,hideAfter:3
	   ,type:"error"
	});
};