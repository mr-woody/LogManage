$(document).ready(function(){
	$("#btnSubmit").click(fnSubmitAddOneRole);
});

function fnSubmitAddOneRole(){
	return excute_form_validator();
};

function fnAddNewRoleSuccess(){
	window.parent.DialogAddRoleResult = "success";
	window.parent.DialogAddRole.close();
};

function fnRoleNameIsExist(){
	$("#roleNameInfo").text("该角色名称已存在！");
};
