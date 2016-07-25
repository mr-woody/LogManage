$(document).ready(function() {
	$("#btnSubmit").click(fnSubmitChangePassword);
	$(".input-xlarge").focus(function() {
		$(this).parent().children(".input-error-info").text("");
	});
});
function fnSubmitChangePassword() {
	var blIsNoError = excute_form_validator();
	if(blIsNoError){
		blIsNoError = $("#txtSureNewPwd").val() == $("#txtNewPwd").val();
		if (!blIsNoError) {
			$("#txtSureNewPwd").parent().children(".input-error-info").text("两次填写密码不一致!");
		}
	}
	return blIsNoError;
};

function fnOldPasswordError(){
	$("#txtOldPwd").parent().children(".input-error-info").text("旧密码填写错误!");
	$("#txtOldPwd").attr("empty-val-error","true").css(errorStyle);
};


function fnChangePasswordSuccess(){
	window.parent.Messenger().post({
		message:"您的密码修改成功！"
	   ,hideAfter:3
	});
	window.parent.DialogChangePassword.close();
};