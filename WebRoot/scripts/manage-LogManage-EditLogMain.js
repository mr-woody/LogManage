$(document).ready(function(){
	InitializeProjects();
});
function InitializeProjects(){
	var ztreeSetting = {
		async:{
			enable: true,
            dataFilter: getProjectsCallBack,
            autoParam: [],
            otherParam: { "otherParam": "zTreeAsyncTest" },
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            type:"post",
            url: getAsyncUrl
		},
		view: {
			selectedMulti: false
		},
		callback: {
            onAsyncSuccess: function (event, treeId, treeNode, msg) {
            	if($("#frmContent").attr("src") === undefined){
	            	var editLogUrl = SiteConfig.DoMain+"actions/log/logActions.action?method=getOneLogDetail&logId="+getParam("logid");
	            	$("#frmContent").attr("src",editLogUrl);
            	}
            }
        }
	};
	$.fn.zTree.init($("#projectTree"), ztreeSetting);
};
function getAsyncUrl(treeId, treeNode) {
	if(treeNode === undefined){
		return SiteConfig.DoMain+"actions/ajax/ajaxActions.action?method=doSearchProjects";
	}
	if(treeNode !== undefined && treeNode.projectID !==undefined){
		return SiteConfig.DoMain+"actions/ajax/ajaxActions.action?method=doSearchSections&projectID="+treeNode.projectID;
	}
	if(treeNode !== undefined && treeNode.sectionID !==undefined){
		return SiteConfig.DoMain+"actions/ajax/ajaxActions.action?method=doSearchSectionTasks&sectionID="+treeNode.sectionID;
	}
	if(treeNode !== undefined && treeNode.taskID !==undefined){
		treeNode.sectionID = treeNode.getParentNode() != null ? treeNode.getParentNode().sectionID : treeNode.sectionID;
		return SiteConfig.DoMain+"actions/ajax/ajaxActions.action?method=doSearchSectionTasks&sectionID="+treeNode.sectionID+"&taskID="+treeNode.taskID;
	}
};
function getProjectsCallBack(treeId, parentNode, result) {
	var data = $.parseJSON(result.result).data;
	var childNodes = [];
	for ( var i = 0; i < data.length; i++) {
		var dataItem = data[i];
		if(dataItem.projectID !== undefined){
			childNodes[i] = {nodeKey:String.format('node_project_{0}',dataItem.projectID),name : dataItem.name,projectID : dataItem.projectID,isParent : true,icon : SiteConfig.DoMain + "images/project.png"};	
		}
		if (dataItem.sectionID !== undefined) {
			childNodes[i] = {nodeKey:String.format('node_section_{0}',dataItem.sectionID),name : dataItem.name,sectionID : dataItem.sectionID,isParent : true,icon : SiteConfig.DoMain + "images/section.png"};
		}
		if(dataItem.taskID !== undefined){
			childNodes[i] = {nodeKey:String.format('node_task_{0}',dataItem.taskID),name : dataItem.name,taskID : dataItem.taskID,isParent : true,icon : SiteConfig.DoMain + "images/task.png"};
		}
	}
	return childNodes;
};
