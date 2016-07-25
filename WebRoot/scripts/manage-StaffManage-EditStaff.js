$(document).ready(function(){
	$("#btnSubmit").click(fnSubmitEditStaff);
});
function fnSubmitEditStaff(){
	return excute_form_validator();
};

function fnEditStaffNameIsExist(){
	$("#staffNameInfo").text("该用户已存在！");
};
function fnEditStaffSuccess(){
	window.parent.DialogEditStaffResult = "success";
	window.parent.DialogEditStaff.close();
};