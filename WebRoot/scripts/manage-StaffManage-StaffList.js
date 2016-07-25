$(document).ready(function() {
	$("#btnNewStaff").click(fnCreateNewStaff);
	$('button[operate="deleteStaff"]').click(fnDeleteOneStaff);
	$('button[operate="editStaff"]').click(fnEditOneStaff);
	$('button[operate="resetPwd"]').click(fnResetStaffPassword);
});

function fnCreateNewStaff() {
	window.parent.DialogCreateStaff.options.onClose = function() {
		if (window.parent.DialogCreateStaffResult !== undefined && window.parent.DialogCreateStaffResult === "success") {
			window.location = SiteConfig.DoMain+"actions/staff/staffActions.action?method=getStaffInfoList";
		}
	}
	window.parent.DialogCreateStaff.show();
};

function fnDeleteOneStaff(){
	$("#hidMethodName").val("deleteOneStaffInfo");
	$("#hidStaffID").val($(this).attr("staffid"));
	window.parent.DialogConfirm.options.action = function(){
		$("#staffActions").trigger("submit");
	};
	window.parent.DialogConfirm.show();
};

function fnEditOneStaff(){
	var content = window.parent.DialogEditStaff.options.content;
	var contentUrl = SiteConfig.DoMain +"actions/staff/staffActions.action?method=getOneStaffInfo&staffID="+$(this).attr("staffid");
	$(content).attr("src",contentUrl);
	window.parent.DialogEditStaff.options.onClose = function(){
		if (window.parent.DialogEditStaffResult !== undefined && window.parent.DialogEditStaffResult === "success") {
			window.location = SiteConfig.DoMain+"actions/staff/staffActions.action?method=getStaffInfoList";
			window.parent.Messenger().post({
				message:"用户信息已更改！"
			   ,hideAfter:3
			});
		}
	};
	window.parent.DialogEditStaff.show();
};

function fnResetStaffPassword(){
	$("#hidMethodName").val("resetStaffPwd");
	$("#hidStaffID").val($(this).attr("staffid"));
	window.parent.DialogConfirmResetPwd.options.action = function(){
		$("#staffActions").trigger("submit");
	};
	window.parent.DialogConfirmResetPwd.show();
};