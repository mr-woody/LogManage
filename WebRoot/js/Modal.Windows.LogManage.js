$(document).ready(function(){
	InitializeAddLog();
	InitializeEditOneLog();
	InitializeWorkContentDetail();
});
//新建日志
function InitializeAddLog(){
	if(window.DialogAddLog === undefined){
		var iframeElement = $("<iframe>").attr({
			"frameborder" : "0",
			"scrolling" : "no",
			"src" : SiteConfig.DoMain +"manage/LogManage/AddLogMain.jsp"
		}).css({
			"width" : "750px",
			"height" : "450px"
		});
		window.DialogAddLog = $.scojs_modal({
			width:"760px",
			target : '#modal_addLog',
			title : '新日志',
			content:iframeElement
		});
	}
};
//编辑日志
function InitializeEditOneLog(){
	if(window.DialogEditOneLog === undefined){
		var iframeElement = $("<iframe>").attr({
			"frameborder" : "0",
			"scrolling" : "no",
			"src" :SiteConfig.DoMain +"manage/LogManage/EditLogMain.jsp"
		}).css({
			"width" : "750px",
			"height" : "450px"
		});
		window.DialogEditOneLog = $.scojs_modal({
			width:"760px",
			target : '#modal_editLog',
			title : '日志编辑',
			content:iframeElement
		});
	}
};
//查看工作内容详细
function InitializeWorkContentDetail(){
	if(window.DialogWorkContentDetail === undefined){
		window.DialogWorkContentDetail = $.scojs_modal({
			target : '#modal_workContentDetail',
			title : '工作内容',
			content:undefined
		});
	}
}