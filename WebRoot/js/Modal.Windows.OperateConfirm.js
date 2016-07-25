$(document).ready(function(){
	InitializeConfirmWindow();
	InitializeConfirmResetPwd();
});

function InitializeConfirmWindow(){
	window.DialogConfirm = $.scojs_confirm({
		target : '#confirm_delete',
		content : "确定要删除么？",
		action : function() {
			this.close();
		},
		buttonText : {
			"left" : "确定",
			"right" : "取消"
		}
	});
};

function InitializeConfirmResetPwd(){
	window.DialogConfirmResetPwd = $.scojs_confirm({
		target : '#confirm_resetPwd',
		content : "确定重置该用户密码？",
		action : function() {
			this.close();
		},
		buttonText : {
			"left" : "确定",
			"right" : "取消"
		}
	});
};