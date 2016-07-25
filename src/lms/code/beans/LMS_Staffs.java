package lms.code.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
@Entity(name = "LMS_Staffs")
public class LMS_Staffs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "S_LMS_Staffs")
	@SequenceGenerator(name = "S_LMS_Staffs", allocationSize = 1, initialValue = 1, sequenceName = "S_LMS_Staffs")
	private long staffID;

	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RoleID")
	private LMS_Roles role;
	private String loginName;
	private String passWord;
	private String tel;
	private String crashTel;
	private String email;
	
	@Column(columnDefinition="VARCHAR(2000) default null") 
	private String contactAddress;
	
	//[start] get and set methods
	public long getStaffID() {
		return staffID;
	}

	public void setStaffID(long staffID) {
		this.staffID = staffID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LMS_Roles getRole() {
		return role;
	}

	public void setRole(LMS_Roles role) {
		this.role = role;
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
	//[end]
	
}
