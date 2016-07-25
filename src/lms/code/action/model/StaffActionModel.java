package lms.code.action.model;

public class StaffActionModel {
	
	//[start] staffSignIn method parameters
	private String loginName;
	private String passWord;
	//[end]

	//[start] staffChangePassword method parameters
	private String newPassWord;
	//[end]
	
	//[start] completeStaffInfo method parameters
	private String idCard;
	private String tel;
	private String crashTel;
	private String email;
	private String contactAddress;
	//[end]
	
	//[start] addStaffInfo method parameters
	private String staffName;
	private Long staffRole;
	//[end]
	
	//[start] deleteOneStaffInfo method parameters
	private Long staffID;
	//[end]
	
    //[start] Get abd set methods
	
	public String getNewPassWord() {
		return newPassWord;
	}

	public Long getStaffID() {
		return staffID;
	}

	public void setStaffID(Long staffID) {
		this.staffID = staffID;
	}

	public Long getStaffRole() {
		return staffRole;
	}

	public void setStaffRole(Long staffRole) {
		this.staffRole = staffRole;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCrashTel() {
		return crashTel;
	}

	public void setCrashTel(String crashTel) {
		this.crashTel = crashTel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public void setNewPassWord(String newPassWord) {
		this.newPassWord = newPassWord;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	//[end]
}
