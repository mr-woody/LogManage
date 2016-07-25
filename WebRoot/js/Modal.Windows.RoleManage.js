$(document).ready(function(){
	InitializeAddRole();
	InitializeEditRole();
});
//新建角色
function InitializeAddRole(){
	if(window.DialogAddRole === undefined){
		var iframeElement = $("<iframe>").attr({
			"frameborder" : "0",
			"scrolling" : "no",
			"src" : SiteConfig.DoMain +"actions/role/roleActions.action?method=initialAddOneRole"
		}).css({
			"width" : "100%",
			"height" : "300px"
		});
		window.DialogAddRole = $.scojs_modal({
			target : '#modal_addRole',
			title : '新建角色',
			content:iframeElement
		});
	}
}
//编辑角色
function InitializeEditRole() {
	if(window.DialogEditRole === undefined){
		var iframeElement = $("<iframe>").attr({
			"frameborder" : "0",
			"scrolling" : "no",
			"src" : SiteConfig.DoMain +"actions/role/roleActions.action?method=getOneRole"
		}).css({
			"width" : "100%",
			"height" : "300px"
		});
		window.DialogEditRole = $.scojs_modal({
			target : '#modal_editRole',
			title : '角色编辑',
			content:iframeElement
		});
	}
};