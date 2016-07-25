package lms.common;

import lms.code.beans.LMS_Staffs;

public class SessionUser  {
	public SessionUser() {

	}

	public SessionUser(LMS_Staffs staff) {
		this.setLoginName(staff.getLoginName());
		this.setStaffID(staff.getStaffID());
	}

	private String loginName;
	private long staffID;
	private long reportID;
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public long getStaffID() {
		return staffID;
	}

	public void setStaffID(long l) {
		this.staffID = l;
	}

	public long getReportID() {
		return reportID;
	}

	public void setReportID(long reportID) {
		this.reportID = reportID;
	}
}
