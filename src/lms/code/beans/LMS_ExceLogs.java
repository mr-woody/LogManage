package lms.code.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "LMS_ExceLogs")
public class LMS_ExceLogs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "S_LMS_ExceLogs")
	@SequenceGenerator(name = "S_LMS_ExceLogs", allocationSize = 1, initialValue = 1, sequenceName = "S_LMS_ExceLogs")
	public long logID;
	public long ct;//日志创建时间     
	public int type;//日志类型          
	public String url;//网络请求日志请求url  
	public String requestMethod;//网络请求
	public String uid;//用户uid  
	public String channel;//用户渠道
	public int devicePlatform;//设备平台
	@Column(columnDefinition="TEXT default null")
	public String errorData;//异常数据
	public long messageId;//返回信息id
	@Column(columnDefinition="TEXT default null")
	public String deviceMessage;//平台返回提示信息
	public String serverMessage;//服务器消
	public long useTime;//用户使用时间      
	public String appVersion;//软件版本
	public String deviceBrand;//厂商    
	public String deviceModel;//手机型号  
	public String access;//手机网络       
	public int width;//屏幕宽            
	public int height;//屏幕高           
	public String osVersion;//系统版本    
	public int cpuCount;//cpu个数       
	public String freeMemory;//空闲内存   
	public String totalMemory;//可使用总内存

	
	
	// [start] get and set methods
	public long getLogID() {
		return logID;
	}

	public void setLogID(long logID) {
		this.logID = logID;
	}
	public long getCt() {
		return ct;
	}

	public void setCt(long ct) {
		this.ct = ct;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
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

	public int getDevicePlatform() {
		return devicePlatform;
	}

	public void setDevicePlatform(int devicePlatform) {
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

	public String getServerMessage() {
		return serverMessage;
	}

	public void setServerMessage(String serverMessage) {
		this.serverMessage = serverMessage;
	}

	public long getUseTime() {
		return useTime;
	}

	public void setUseTime(long useTime) {
		this.useTime = useTime;
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

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public int getCpuCount() {
		return cpuCount;
	}

	public void setCpuCount(int cpuCount) {
		this.cpuCount = cpuCount;
	}

	public String getFreeMemory() {
		return freeMemory;
	}

	public void setFreeMemory(String freeMemory) {
		this.freeMemory = freeMemory;
	}

	public String getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(String totalMemory) {
		this.totalMemory = totalMemory;
	}
	// [end]
	
}
