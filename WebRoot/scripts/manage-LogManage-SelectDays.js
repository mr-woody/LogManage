$(document).ready(function(){
	$("#selControlSize").change(function(){
		changeCalSize($(this).val());
		$.cookie("CalendarOption",$(this).val(),{expires:365});
	});
	$("#selControlDays").change(function(){$('#calOne').data('days', $('#selControlDays').val());});
	$("#btnMyLogs").click(function(){
		window.location.href = SiteConfig.DoMain+"actions/reportActions.action?method=getMyWorkReports";
	});
	$('#calOne').jCal({
		day:			new Date(),
		days:			1,
		showMonths:		2,
		monthSelect:	true,
		dCheck:		    CalendarCheck,
		callback:	    CalendarCallBack
	});
	var tempCalendarOption = $.cookie("CalendarOption");
	tempCalendarOption = tempCalendarOption == null ? 40 : tempCalendarOption;
	$("#selControlSize").val(tempCalendarOption);
	changeCalSize(tempCalendarOption);
});
function changeCalSize (daySize) {
	var daySize = (parseInt(daySize) || 30), 
		monthSize = ( daySize + 2 ) * 7,
		titleSize = monthSize - 16,
		titleMsgSize = ( titleSize / 2 ) - 4;
	$('head:first').append(
		'<style>' +
			'.jCalMo .day,.jCalMo .invday,.jCalMo .pday,.jCalMo .aday,.jCalMo .selectedDay,.jCalMo .dow { width:' + daySize + 'px !important; height:' + daySize + 'px !important; }' +
			'.jCalMo .dow { height:auto !important }' +
			'.jCalMo, .jCalMo .jCal { width:' + monthSize + 'px !important; }' +
			'.jCalMo .month { width:' + titleSize + 'px !important; }' +
			'.jCalMo .month span { width:' + titleMsgSize  + 'px !important; }' +
		'</style>');
};

function CalendarCheck(day){
	var currentDate = new Date();
	var dateDiv = String.format('#c2d_{0}_{1}_{2}',(currentDate.getMonth()+1),currentDate.getDate(),currentDate.format('yyyy'));
	$(dateDiv,'#calOne').css({'border':'1px solid blue'});
	if ( day.getTime() == (new Date('8/7/2008')).getTime() )
		return 'invday';
	else if (day.getDate() != 3)
		return 'day';
	else
		return 'invday';
};

function CalendarCallBack(day, days){
	$('#calOneDays').val( days );
	$(this._target).find('.dInfo').remove();
	var dCursor = new Date(day.getTime());
	if (days == 1) {
		$("#startDateValue").val(day.format("yyyy-MM-dd"));
		$("#endDateValue").val(day.format("yyyy-MM-dd"));
		$("#getLogsByPage").trigger("submit");
	} else {
		var endDate = new Date(day);
		endDate.setDate((endDate.getDate() + days - 1));
		$("#startDateValue").val(day.format("yyyy-MM-dd"));
		$("#endDateValue").val(endDate.format("yyyy-MM-dd"));
		$("#getLogsByPage").trigger("submit");
	}
	if ( typeof $(this._target).data('day') == 'object' &&
		 $(this._target).data('day').getTime() == day.getTime() &&
		 $(this._target).data('days') == days ) {
		$('#calOneResult').append('<div style="clear:both; font-size:7pt;">' + days + ' days starting ' +
			( day.getMonth() + 1 ) + '/' + day.getDate() + '/' + day.getFullYear() + ' RECLICKED</div>');
	} else {
		$('#calOneResult').append('<div style="clear:both; font-size:7pt;">' + days + ' days starting ' +
			( day.getMonth() + 1 ) + '/' + day.getDate() + '/' + day.getFullYear() + '</div>');
	}
	return true;
};