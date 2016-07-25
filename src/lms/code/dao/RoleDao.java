package lms.code.dao;

import org.springframework.stereotype.Repository;

import lms.code.beans.LMS_Roles;
import lms.common.AbstractDao;

@Repository
public class RoleDao extends AbstractDao {
	public final String QuerySuperAdmin = "from LMS_Roles where Name = '{0}'";
	public final String GetOneRoleByRoleName = " from LMS_Roles where name = '{0}'";
	public final String CheckRoleNameIsExist = " from LMS_Roles where name = '{0}' and roleID <> {1}";
	public final String GetAllRoles = " from LMS_Roles where name <> '{0}'";

	
	@Override
	public Class<?> getReferenceClass() {
		return LMS_Roles.class;
	}
}
