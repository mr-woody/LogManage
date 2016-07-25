package lms.code.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.frame.convert.ChineseConvert;
import dev.frame.encrypt.DESEncrypt;
import dev.frame.util.StringUtil;
import lms.code.beans.LMS_Roles;
import lms.code.beans.LMS_Staffs;
import lms.code.dao.RoleDao;
import lms.code.dao.StaffDao;
import lms.code.service.StaffService;
import lms.common.ServiceImplBase;
import lms.common.SiteConfig;
import lms.struts.tags.Page;
@Service("StaffService")
@Transactional
public class StaffServiceImpl extends ServiceImplBase implements StaffService {
	@Resource
	private StaffDao staffDao;
	@Resource
	private RoleDao roleDao;
	
	public LMS_Staffs staffLogin(String userName, String passWord) {
		try {
			String decodePwd = DESEncrypt.encrypt(passWord, SiteConfig.StaffPasswordDeKey);
			String hql = StringUtil.format(staffDao.StaffSignIn,userName,decodePwd);
			Collection<LMS_Staffs> loginStaffs =  staffDao.queryByConditions(hql);
			return loginStaffs.size()== 0 ? null : (LMS_Staffs)loginStaffs.toArray()[0];
		} catch (Exception e) {
			logger.error("用户登录加密出错："+e.getMessage());
		}
		return null;
	}
	
	public int changePassword(String userName, String oldPwd,String newPwd) {
		try {
			String decodePwd = DESEncrypt.encrypt(oldPwd, SiteConfig.StaffPasswordDeKey);
			String hql = StringUtil.format(staffDao.StaffSignIn,userName,decodePwd);
			Collection<LMS_Staffs> loginStaffs =  staffDao.queryByConditions(hql);
			LMS_Staffs staffInfo = loginStaffs.size()== 0 ? null : (LMS_Staffs)loginStaffs.toArray()[0];
			if (staffInfo == null) return 0;
			decodePwd = DESEncrypt.encrypt(newPwd, SiteConfig.StaffPasswordDeKey);
			staffInfo.setPassWord(decodePwd);
			return staffDao.updateObj(staffInfo,false);
		} catch (Exception e) {
			logger.error("用户密码修改失败："+e.getMessage());
		}
		return 0;
	}

	public LMS_Staffs getStaffByUserName(String userName) {
		String hql = StringUtil.format(staffDao.GetStaffByLoginName,userName);
		Collection<LMS_Staffs> staffs = staffDao.queryByConditions(hql);
		return staffs.size()== 0 ? null : (LMS_Staffs)staffs.toArray()[0];
	}

	public boolean updateStaffInfo(LMS_Staffs staffInfo) {
		return staffDao.updateObj(staffInfo,false) >0;
	}

	public int addNewStaffInfo(String staffName, Long roleID) {
		int returnResult = 0;
		String loginName = ChineseConvert.getPingYin(staffName);
		boolean staffLoginNameIsExist = getStaffByUserName(loginName) != null;
		if (!staffLoginNameIsExist) {
			LMS_Staffs newStaff = new LMS_Staffs();
			newStaff.setName(staffName);
			newStaff.setLoginName(loginName);
			try {
				String password = DESEncrypt.encrypt(SiteConfig.StaffInitialPassword, SiteConfig.StaffPasswordDeKey);
				newStaff.setPassWord(password);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("添加用户出错："+e.getMessage());
			}
			LMS_Roles roleInfo = (LMS_Roles) roleDao.getObjectByPk(LMS_Roles.class, roleID);
			newStaff.setRole(roleInfo);
			staffDao.addObject(newStaff);
			returnResult = 1;
		}
		return returnResult;
	}

	public Collection<LMS_Staffs> getStaffList(Page page){
		String hql = StringUtil.format(staffDao.GetStaffList,SiteConfig.SuperAdminLoginName);
		return  staffDao.queryByConditions(hql,page);
	}

	public boolean deleteOneStaff(Long staffID) {
		return staffDao.deleteByPks(staffID) > 0;
	}

	public LMS_Staffs getStaffByStaffID(Long staffID) {
		return (LMS_Staffs) staffDao.getObjectByPK(staffID);
	}

	public int updateStaff(LMS_Staffs staffInfo) {
		String hql = StringUtil.format(staffDao.GetStaffByStaffName,staffInfo.getName(),staffInfo.getStaffID());
		Collection<LMS_Staffs> staffList =  staffDao.queryByConditions(hql);
		if (staffList.size() > 0) {
			return 0;
		}
		return staffDao.updateObj(staffInfo,true);
	}
	
	public boolean updateStaffPassword(Long staffID){
		LMS_Staffs staffInfo = getStaffByStaffID(staffID);
		try {
			String resetPwd = DESEncrypt.encrypt(SiteConfig.StaffInitialPassword, SiteConfig.StaffPasswordDeKey);
			staffInfo.setPassWord(resetPwd);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return staffDao.updateObj(staffInfo, false) > 0;
	}

	public Collection<LMS_Staffs> getStaffListExceptAdmin() {
		String hql = StringUtil.format(staffDao.GetStaffExceptAdmin,SiteConfig.SuperAdminLoginName);
		return staffDao.queryByConditions(hql);
	}
	
	public Collection<LMS_Staffs> getWorkReportLeaders(String myLoginName){
		String hql = StringUtil.format(staffDao.GetWorkReportLeaders, "A005",myLoginName,SiteConfig.SuperAdminLoginName);
		return staffDao.queryByConditions(hql);
	}
}
