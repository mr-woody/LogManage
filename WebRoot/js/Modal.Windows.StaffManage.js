$(document).ready(function(){
	InitializeAddStaff();
	InitializeEditStaff();
	InitializeStaffInfo();
	InitializeStaffChangePassword();
});

//新用户添加窗体
function InitializeAddStaff(){
	if (window.DialogCreateStaff === undefined) {
		var iframeElement = $("<iframe>").attr({
			"frameborder" : "0",
			"scrolling" : "no",
			"src" : SiteConfig.DoMain +"actions/staff/staffActions.action?method=initialAddStaffInfo"
		}).css({
			"width" : "100%",
			"height" : "180px"
		});
		window.DialogCreateStaff = $.scojs_modal({
			target : '#modal_addnewstaff',
			title : '添加新用户',
			content:iframeElement
		});
	}
};

//用户编辑窗体
function InitializeEditStaff(){
	if (window.DialogEditStaff === undefined) {
		var iframeElement = $("<iframe>").attr({
			"frameborder" : "0",
			"scrolling" : "no",
			"src" : SiteConfig.DoMain +"actions/staff/staffActions.action?method=getOneStaffInfo"
		}).css({
			"width" : "100%",
			"height" : "180px"
		});
		window.DialogEditStaff = $.scojs_modal({
			target : '#modal_editStaff',
			title : '用户编辑',
			content:iframeElement
		});
	}
};

//用户个人资料
function InitializeStaffInfo(){
	if (window.DialogStaffInfo === undefined) {
		var iframeElement = $("<iframe>").attr({
			"frameborder" : "0",
			"scrolling" : "no",
			"src" : SiteConfig.DoMain +"actions/staff/staffActions.action?method=getStaffInfoDetail"
		}).css({
			"width" : "100%",
			"height" : "450px"
		});
		
		window.DialogStaffInfo  = $.scojs_modal({
			target : '#modal_staffinfo',
			title : '完善个人资料',
			content:iframeElement,
			onClose : function() {
				if (window.DialogStaffInfoResult !== undefined && window.DialogStaffInfoResult === "success") {
					window.DialogStaffInfoResult = null;
					window.Messenger().post({
						message:"您的个人资料已更新！"
					   ,hideAfter:3
					});
				}
			}
		});
	}
};
//密码修改窗体
function InitializeStaffChangePassword(){
	if (window.DialogChangePassword === undefined) {
		var iframeElement = $("<iframe>").attr({
			"frameborder" : "0",
			"scrolling" : "no",
			"src" : SiteConfig.DoMain + 'manage/StaffManage/ChangePwd.jsp'
		}).css({
			"width" : "100%",
			"height" : "280px"
		});
		window.DialogChangePassword = $.scojs_modal({
			target : '#modal_changepwd',
			title : '修改密码',
			content : iframeElement
		});
	}
};
