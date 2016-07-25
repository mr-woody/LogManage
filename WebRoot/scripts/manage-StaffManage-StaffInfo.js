
$(document).ready(function(){
	$("#btnSubmit").click(fnSubmitStaffInfoChange);
});

function fnSubmitStaffInfoChange(){
	return excute_form_validator();
};

function fnCompleteStaffInfo(){
	window.parent.DialogStaffInfoResult = "success";
	window.parent.DialogStaffInfo.close();
};