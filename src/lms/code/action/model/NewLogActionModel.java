package lms.code.action.model;

public class NewLogActionModel {
	private Long logId;
	private String type;//日志类型
	private String uid;//用户uid
	private String channel;//用户渠道
	private String devicePlatform;//设备平台  
	private String errorData;//异常数据    
	private String deviceMessage;//平台返回提示信息
	private String appVersion;//软件版本
	private String deviceBrand;//厂商
	private String deviceModel;//手机型号
	private String osVersion;//系统版本
	
	private String startTime;//日志查询开始时间
	private String endTime;//日志查询结束时间
	private String messageId;//消息详情


	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getDevicePlatform() {
		return devicePlatform;
	}

	public void setDevicePlatform(String devicePlatform) {
		this.devicePlatform = devicePlatform;
	}

	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}

	public String getDeviceMessage() {
		return deviceMessage;
	}

	public void setDeviceMessage(String deviceMessage) {
		this.deviceMessage = deviceMessage;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getDeviceBrand() {
		return deviceBrand;
	}

	public void setDeviceBrand(String deviceBrand) {
		this.deviceBrand = deviceBrand;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	
	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}
	// [end]

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
}
