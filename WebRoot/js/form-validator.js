/*****************************************
 * 表单验证js组件(需要jquery支持)
 * 目前支持以下几种验证
 * 1.非空验证
 * 	示例：<input type="text" form-val="empty" val-empty-msg="XXXX不能为空!" />
 * 	说明:目前密码框无法显示错误文本，建议type='password'的验证不要赋予val-empty-msg值
 * 
 * 2.手机验证
 * 	示例：<input type="text" form-val="phone"  val-phone-msg="手机号码格式不正确!" />
 * 
 * 3.身份证号码验证
 * 	示例：<input type="text" form-val="idcard" val-phone-msg="身份证号码格式不正确" />
 * 
 * 4.邮箱验证
 * 	示例：<input type="text" form-val="email" val-email-msg="电子邮箱格式不正确" />
 * 
 * 5.textarea长度验证
 *  示例：<textarea form-val="empty,maxlength(1000)" />
 ****************************************/
var empty_normal_style = {"border":"#ABADB3 1px solid","background-color":"","color":"black"};
var empty_error_style = {"border": "#C66161 1px solid","background-color": "#FBE2E2","color": "red"};
var phone_normal_style = {"border":"#ABADB3 1px solid","background-color":""};
var phone_error_style = {"border": "#C66161 1px solid","background-color": "#FBE2E2"};
var warp_text_style = {"text-decoration":"none","padding":"0 0 0 5px","display": "block","overflow":"hidden","white-space":"nowrap","text-overflow":"ellipsis"};
var ResizeTextWarp = function (){
	if($('[text-warp*=","]').length > 0){
		$('[text-warp*=","]').each(function(){
			var parentAttr = $(this).attr("text-warp").split(',')[0];
			var parentAttrValue = $(this).attr("text-warp").split(',')[1];
			var parentDefined = '['+parentAttr+'="'+parentAttrValue+'"'+"]";
			$('[text-warp*=","]').css(warp_text_style).css("width","0px");
			$('[text-warp*=","]').css("width",$(parentDefined).width());
		});
	}
};
$(window).resize(ResizeTextWarp);
$(document).ready(function(){
	ResizeTextWarp();
	$('input[form-val*="empty"],textarea[form-val*="empty"]').blur(form_val_empty_blur).focus(form_val_empty_focus);
	$('input[form-val*="phone"]').blur(form_val_phone_blur).focus(form_val_phone_focus);
	$('input[form-val*="idcard"]').blur(form_val_idcard_blur).focus(form_val_idcard_focus);
	$('input[form-val*="email"]').blur(form_val_email_blur).focus(form_val_email_focus);
	$('textarea[form-val*="maxlength("]').keyup(form_val_maxlength_keyup);
});

function form_val_empty_blur(){
	var blIsEmpty = $.trim($(this).val()).length == 0;
	var blIsError = $(this).attr("empty-val-error") === "true";
	if (blIsEmpty && !blIsError) {
		$(this).css(empty_error_style).attr("empty-val-error","true").val($(this).attr("val-empty-msg"));
	} 
};

function form_val_empty_focus(){
	 var blIsError = $(this).attr("empty-val-error") === "true";
	 if(blIsError){	
		 $(this).css(empty_normal_style).attr("empty-val-error","false").val("");
	 }
};

function form_val_phone_blur(){
	var blIsError = $(this).attr("phone-val-error") === "true";
	var blIsPhone = $(this).val().match(/^0?1(3|5|8)\d{9}$/);
	if(!blIsError && !blIsPhone){
		$(this).css(phone_error_style).attr("phone-val-error","true");
		$("#"+$(this).attr("msg-ctrl-id")).text($(this).attr("val-phone-msg"));
	}
};

function form_val_phone_focus(){
	 var blIsError = $(this).attr("phone-val-error") === "true";
	 if(blIsError){	
		 $(this).css(phone_normal_style).attr("phone-val-error","false");
		 $("#"+$(this).attr("msg-ctrl-id")).text("");
	 }
};

function form_val_idcard_blur(){
	var blIsError = $(this).attr("idcard-val-error") === "true";
	var blIsIdCard = $(this).val().match(/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/);
	if(!blIsError && !blIsIdCard){
		$(this).css(phone_error_style).attr("idcard-val-error","true");
		$("#"+$(this).attr("msg-ctrl-id")).text($(this).attr("val-idcard-msg"));
	}
};

function form_val_idcard_focus(){
	 var blIsError = $(this).attr("idcard-val-error") === "true";
	 if(blIsError){	
		 $(this).css(phone_normal_style).attr("idcard-val-error","false");
		 $("#"+$(this).attr("msg-ctrl-id")).text("");
	 }
};

function form_val_email_blur(){
	var blIsError = $(this).attr("email-val-error") === "true";
	var blIsEmail = $(this).val().match(/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/);
	if(!blIsError && !blIsEmail){
		$(this).css(phone_error_style).attr("email-val-error","true");
		$("#"+$(this).attr("msg-ctrl-id")).text($(this).attr("val-email-msg"));
	}
};

function form_val_email_focus(){
	 var blIsError = $(this).attr("email-val-error") === "true";
	 if(blIsError){	
		 $(this).css(phone_normal_style).attr("email-val-error","false");
		 $("#"+$(this).attr("msg-ctrl-id")).text("");
	 }
};
function form_val_maxlength_keyup(){
	var maxlength_value = $(this).attr("form-val").match(/maxlength\((.*?)\)/)[1];
	if(this.value.length > maxlength_value) {
		this.value = this.value.substr(0,maxlength_value);
	}
};
//手动验证方法
function excute_form_validator(){
	$('input[form-val*="empty"],textarea[form-val*="empty"]').trigger("blur");
	$('input[form-val*="phone"]').trigger("blur");
	$('input[form-val*="idcard"]').trigger("blur");
	$('input[form-val*="email"]').trigger("blur");
	var blEmpty = ($('input[empty-val-error="true"],textarea[empty-val-error="true"]').length === 0);
	var blPhone = ($('input[phone-val-error="true"]').length === 0);
	var blIdcard = ($('input[idcard-val-error="true"]').length === 0);
	var blEmail = ($('input[email-val-error="true"]').length === 0);
	return blEmpty && blPhone && blIdcard && blEmail;
};
