$(document).ready(function(){
	$("#btnSubmit").click(fnSubmitEditRole);
});

function fnSubmitEditRole(){
	return excute_form_validator();
};

function fnRoleNameIsExist(){
	$("#roleNameInfo").text("角色名称已存在！");
};
function fnEditRoleSuccess(){
	window.parent.DialogEditRoleResult = "success";
	window.parent.DialogEditRole.close();
};