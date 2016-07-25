var timepickerOptions = {
	pickerPosition : "bottom-left",
	minView:2,
	todayHighlight:true,
	autoclose: true,
    todayBtn: true,
	format : 'yyyy-mm-dd',
	language : 'zh-CN'
};
$(document).ready(function(){
	//下拉菜单
	$('.selectpicker').selectpicker({noneSelectedText:$("#hidAppVersionData").val()==""?'请选择':$("#hidAppVersionData").val()});
	$('.selectpicker').selectpicker('val',$("#hidAppVersionData").val());
	$("#txtStartTime").datetimepicker(timepickerOptions).on('changeDate', function(ev){
		var endTime = new Date(ev.date);
		var start = parseInt(endTime.format("yyyyMMdd"));
		var txtEndTimeVal=$("#txtEndTime > input").val();
		if(txtEndTimeVal!=""){
			var end = parseInt(txtEndTimeVal.replace(":",""));
			if(start > end){
				$("#txtEndTime > input").val(endTime.format("yyyy-MM-dd"));
			}
		}
		$("#txtEndTime").datetimepicker('setStartDate', endTime);
	});
	$("#txtEndTime").datetimepicker(timepickerOptions).on('changeDate',function(ev){
		var startTime = new Date(ev.date);
		var end = parseInt(startTime.format("yyyyMMdd"));
		var txtStartTimeVal=$("#txtStartTime > input").val();
		if(txtStartTimeVal!=""){
			var start = parseInt(txtStartTimeVal.replace(":",""));
			if(start > end){
				$("#txtStartTime > input").val(startTime.format("yyyy-MM-dd"));
			}
		}
	});
	$("#navPage a").each(function(){
		if($(this).attr("href").indexOf("void(0)") < 0){
			var pageHref = $(this).attr("href");
			pageHref+="&startTime="+$("#startDateValue").val()+"&endTime="+$("#endDateValue").val()+"&appVersion="+$('.selectpicker').val()+"&errorData="+$("#errorDataID").val();
			$(this).attr("href",pageHref);
		}
	});
	$('[operate="workContentDetail"]').click(fnWorkContentDetail);
	$('[operate="deviceMessageDetail"]').click(fnDeviceMessageDetail);
	$("#btnSubmit").click(fnLogsInfo);
});

function fnLogsInfo(){
	if($("#startDateValue").val()!=null&&$("#startDateValue").val()!=""){
		return true;
	}else{
		return excute_form_validator();
	}
};
function fnDeviceMessageDetail(){
	var messageId = $(this).attr("messageId");
	var dialogUrl = SiteConfig.DoMain+"actions/log/newLogActions.action?method=getDeviceMessageDetail&messageId="+messageId;
	window.parent.DialogWorkContentDetail.options.remote = dialogUrl;
	window.parent.DialogWorkContentDetail.show();
}

function fnWorkContentDetail(){
	var logid = $(this).attr("logid");
	var dialogUrl = SiteConfig.DoMain+"actions/log/newLogActions.action?method=getLogsByDetail&logId="+logid;
	window.parent.DialogWorkContentDetail.options.remote = dialogUrl;
	window.parent.DialogWorkContentDetail.show();
};