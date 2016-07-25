package lms.code.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.frame.util.StringUtil;

import lms.code.beans.LMS_Roles;
import lms.code.beans.LMS_Staffs;
import lms.code.dao.RoleDao;
import lms.code.dao.StaffDao;
import lms.code.service.RoleService;
import lms.common.SiteConfig;


@Service("RoleService")
@Transactional
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleDao roleDao;
	@Resource
	private StaffDao staffDao;
	
	public Collection<LMS_Roles> getAllRoles() {
		String hql = StringUtil.format(roleDao.GetAllRoles, SiteConfig.SuperAdminName);
		return roleDao.queryByConditions(hql);
	}

	public LMS_Roles getOneRole(Long roleID) {
		return (LMS_Roles)roleDao.getObjectByPK(roleID);
	}

	public int addOneRole(LMS_Roles roleInfo) {
		String hql = StringUtil.format(roleDao.GetOneRoleByRoleName,roleInfo.getName());
		Collection<LMS_Roles> roleList = roleDao.queryByConditions(hql);
		if (roleList.size()> 0) {
			return 0;
		}else {
			return roleDao.addObject(roleInfo);	
		}
	}

	public boolean deleteOneRole(Long roleID) {
		String hql = StringUtil.format(staffDao.GetStaffsByRoleID,roleID);
		Collection<LMS_Staffs> staffs = staffDao.queryByConditions(hql);
		for (LMS_Staffs lms_Staffs : staffs) {
			lms_Staffs.setRole(null);
		}
		staffDao.updateObjs(staffs, false);
		return roleDao.deleteByPks(roleID)> 0;
	}

	public int updateOneRole(LMS_Roles roleInfo) {
		String hql = StringUtil.format(roleDao.CheckRoleNameIsExist,roleInfo.getName(),roleInfo.getRoleID());
		Collection<LMS_Roles> roleList = roleDao.queryByConditions(hql);
		if (roleList.size() > 0) {
			return 0;
		}else {
			return roleDao.updateObj(roleInfo,true) ;
		}
	}
}
