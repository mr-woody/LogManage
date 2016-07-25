package lms.code.dao;

import org.springframework.stereotype.Repository;

import lms.code.beans.LMS_Staffs;
import lms.common.AbstractDao;


@Repository
public class StaffDao extends AbstractDao {
	public final String QuerySuperAdmin = " from LMS_Staffs where LoginName = '{0}'";
	public final String StaffSignIn = " from LMS_Staffs where LoginName = '{0}' and PassWord = '{1}'";
	public final String GetStaffByLoginName = " from LMS_Staffs where LoginName = '{0}'";
	public final String GetStaffList  = " from LMS_Staffs where LoginName <> '{0}'";
	public final String GetStaffByStaffName = " from LMS_Staffs where Name = '{0}' and staffID <> {1}";
	public final String GetStaffsByRoleID = " from LMS_Staffs where roleID = {0}";
	public final String GetStaffExceptAdmin = " from LMS_Staffs where LoginName <> '{0}'";
	public final String GetWorkReportLeaders = " from LMS_Staffs a where a.role.authors like '%{0}%' and a.loginName not in ('{1}','{2}')";
	@Override
	public Class<?> getReferenceClass() {
		return LMS_Staffs.class;
	}
}
