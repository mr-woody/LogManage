$(document).ready(function(){
	$("#btnNewRole").click(fnNewRole);
	$('button[operate="deleteRole"]').click(fnDeleteOneRole);
	$('button[operate="editRole"]').click(fnEditOneRole);
});

function fnNewRole(){
	window.parent.DialogAddRole.options.onClose = function(){
		if (window.parent.DialogAddRoleResult !== undefined && window.parent.DialogAddRoleResult === "success") {
			window.parent.DialogAddRoleResult = undefined;
			window.location = SiteConfig.DoMain+"actions/role/roleActions.action?method=getRoleList";
			window.parent.Messenger().post({
				message:"新角色已添加！"
			   ,hideAfter:3
			});
		}
	};
	window.parent.DialogAddRole.show();
};

function fnEditOneRole(){
	var roleid = $(this).attr("roleid");
	var content = window.parent.DialogEditRole.options.content;
	var contentUrl = SiteConfig.DoMain +"actions/role/roleActions.action?method=getOneRole&roleID="+roleid;
	$(content).attr("src",contentUrl);
	window.parent.DialogEditRole.options.onClose = function(){
		if (window.parent.DialogEditRoleResult !== undefined && window.parent.DialogEditRoleResult === "success") {
			window.location = SiteConfig.DoMain+"actions/role/roleActions.action?method=getRoleList";
			window.parent.Messenger().post({
				message:"角色信息已更改！"
			   ,hideAfter:3
			});
		}
	};
	window.parent.DialogEditRole.show();
};

function fnDeleteOneRole(){
	$("#hidDeleteRoleID").val($(this).attr("roleid"));
	window.parent.DialogConfirm.options.action = function(){
		$("#roleActions").trigger("submit");
		window.parent.Messenger().post({
			message:"角色信息已删除！"
		   ,hideAfter:3
		});
	};
	window.parent.DialogConfirm.show();
};
