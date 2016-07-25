//说明：调整页面布局
var fnResize = function () {
    var tmpBodyHeight = $("body").height();
    var tmpBodyWidth = $("body").width();
    if ($.browser.msie && $.browser.version == 6) {
        $(".main").width(tmpBodyWidth - 220);
        $(".main").height(tmpBodyHeight   - 24 - 6);
        $(".left_menu").height(tmpBodyHeight - 24 - 6);
        $(".well").height(tmpBodyHeight - 24 - 6);
    }
    $("#frmContent").height(tmpBodyHeight);
    window.MainHeight = tmpBodyHeight;
    window.MainWidth = tmpBodyWidth - 220;
    $(".left_menu").height(tmpBodyHeight);
    $(".well").height(tmpBodyHeight-50);
};
$(window).resize(fnResize);
$(document).ready(function(){
	fnResize();
	$._messengerDefaults = {extraClasses: 'messenger-fixed messenger-theme-block messenger-on-top'};
	$("a[author-code^='A00']").click(function(){
		$("#frmContent").attr("src",(SiteConfig.DoMain+$(this).attr("url")));
	});
	$("#btnEditPwd").click(fnChangePassword);
	$("#btnStaffInfo").click(function(){window.DialogStaffInfo.show();});
	$("#btnMsgCenter").click(function(){
		window.DialogMyRemind.show();
	});
});

function fnChangePassword(){
	window.DialogChangePassword.show();
};
function ShowMsgPrompt(){
	var tmpOpacity = parseFloat($("#btnMsgCenter > i").css("opacity"));
	tmpOpacity = tmpOpacity == 1 ? 0 :tmpOpacity;
	tmpOpacity += 0.1;
	$("#btnMsgCenter > i").css("opacity",tmpOpacity);
	clearTimeout(window.ShowMessagePrompt);
	window.ShowMessagePrompt = setTimeout("ShowMsgPrompt()",50);
};
function HideMsgPrompt(){
	clearTimeout(window.ShowMessagePrompt);
	$("#btnMsgCenter > i").css("opacity",1);
};