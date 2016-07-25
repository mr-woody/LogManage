package lms.code.action;
import java.util.Collection;
import java.util.LinkedList;
import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.dom4j.Element;

import com.opensymphony.xwork2.ModelDriven;

import dev.frame.util.StringUtil;
import dev.frame.xml.XMLUtility;
import lms.code.action.model.StaffActionModel;
import lms.code.action.returnconst.StaffActionConst;
import lms.code.beans.LMS_Roles;
import lms.code.beans.LMS_Staffs;
import lms.code.service.RoleService;
import lms.code.service.StaffService;
import lms.common.AbstractAction;
import lms.common.AuthorInfo;
import lms.common.BeanUtility;
import lms.common.SessionUser;
import lms.common.SiteConfig;

@ParentPackage("staffPackage")
@Action(value = "staffActions")
@Results({ 
	@Result(name = StaffActionConst.StaffSignIn_Success,location = "/Main.jsp") 
   ,@Result(name = StaffActionConst.StaffSignIn_Fialed, location = "/SignIn.jsp")
   ,@Result(name = StaffActionConst.StaffSignOut_Success,location = "/SignIn.jsp")
   ,@Result(name = StaffActionConst.StaffChangePassword_OldPwdError,location = "/manage/StaffManage/ChangePwd.jsp")
   ,@Result(name = StaffActionConst.StaffChangePassword_Success,location = "/manage/StaffManage/ChangePwd.jsp")
   ,@Result(name = StaffActionConst.InitialAddStaffInfo_Success,location = "/manage/StaffManage/AddStaff.jsp")
   ,@Result(name = StaffActionConst.AddStaffInfo_Success,location = "/manage/StaffManage/AddStaff.jsp")
   ,@Result(name = StaffActionConst.CompleteStaffInfo_Success,location = "/manage/StaffManage/StaffInfo.jsp")
   ,@Result(name = StaffActionConst.GetStaffInfoDetail_Success,location = "/manage/StaffManage/StaffInfo.jsp")
   ,@Result(name = StaffActionConst.GetStaffList_Success,location = "/manage/StaffManage/StaffList.jsp")
   ,@Result(name = StaffActionConst.DeleteOneStaffInfo_Success,location = "/actions/staff/staffActions.action?method=getStaffInfoList" ,type="redirect")
   ,@Result(name = StaffActionConst.GetStaffByStaffID_Success,location = "/manage/StaffManage/EditStaff.jsp")
   ,@Result(name = StaffActionConst.EditStaffInfo_Success,location = "/manage/StaffManage/EditStaff.jsp")
   ,@Result(name = StaffActionConst.EditStaffInfo_NameIsExist,location = "/manage/StaffManage/EditStaff.jsp")
   ,@Result(name = StaffActionConst.ResetStaffPwd_Success,location = "/actions/staff/staffActions.action?method=getStaffInfoList&resetpwd=1" ,type="redirect")
   ,@Result(name = StaffActionConst.ResetStaffPwd_Fialed,location = "/actions/staff/staffActions.action?method=getStaffInfoList&resetpwd=0" ,type="redirect")
})
@SuppressWarnings("unchecked")
public class StaffAction extends AbstractAction implements
		ModelDriven<StaffActionModel> {
	private static final long serialVersionUID = -7604937715777424700L;
	private StaffActionModel actionModel = new StaffActionModel();
	
	@Resource
	private StaffService staffService;
	@Resource
	private RoleService roleService;
	
	private Collection<LMS_Roles> addStaffRoles;
	
	private LMS_Staffs staffInfo;
	
	private Collection<LMS_Staffs> staffList;
	
	private Collection<AuthorInfo> staffAuthors;
	
	public String staffSignIn() {
		String userName = actionModel.getLoginName();
		String passWord = actionModel.getPassWord();
		LMS_Staffs loginStaff = staffService.staffLogin(userName, passWord);
		if (loginStaff != null) {
			staffAuthors = new LinkedList<AuthorInfo>();
			String [] authors = loginStaff.getRole().getAuthors().split(";");
			XMLUtility xmlUtility = new XMLUtility(SiteConfig.AppPath+"/WEB-INF/xml/pop.xml");
			Collection<Element> authorElements = xmlUtility.getRootElement().elements();
			for (String author : authors) {
				for (Element element : authorElements) {
					String nameValue = element.attribute("name").getValue();
					String codeValue = element.attribute("code").getValue();
					String urlValue = element.attribute("url").getValue();
					String iconValue = element.attribute("icon").getValue();
					AuthorInfo authorInfo = new AuthorInfo();
					if (codeValue.equals(author)) {
						authorInfo.setName(nameValue);
						authorInfo.setCode(codeValue);
						authorInfo.setUrl(urlValue);
						authorInfo.setIcon(iconValue);
						staffAuthors.add(authorInfo);
					}
				}
			}
			super.setSession(SiteConfig.SessionUserKey, new SessionUser(loginStaff));
			logger.info("用户："+loginStaff.getLoginName()+"已登录!");
			return StaffActionConst.StaffSignIn_Success;
		}
		registerScript("fnPasswordError();",true);
		return StaffActionConst.StaffSignIn_Fialed;
	}
	
	public String staffSignOut() {
		SessionUser sessionUser = getSessionUser();
		super.getSession().removeAttribute(SiteConfig.SessionUserKey);
		logger.info("用户："+sessionUser.getLoginName()+"已退出!");
		return StaffActionConst.StaffSignOut_Success;
	}
	
	public String staffChangePassword(){
		SessionUser sessionUser = getSessionUser();
		int changeResult = staffService.changePassword(sessionUser.getLoginName(), actionModel.getPassWord(), actionModel.getNewPassWord());
		if (changeResult == 0) {
			registerScript("fnOldPasswordError();", true);
			logger.error("用户："+sessionUser.getLoginName()+"修改密码，旧密码出错！");
			return StaffActionConst.StaffChangePassword_OldPwdError;
		}
		registerScript("fnChangePasswordSuccess();", true);
		logger.info("用户："+sessionUser.getLoginName()+"已修改新密码！");
		return StaffActionConst.StaffChangePassword_Success;
	}
	
	public String initialAddStaffInfo(){
		this.addStaffRoles = roleService.getAllRoles();
		return StaffActionConst.InitialAddStaffInfo_Success;
	}
	
	public String addStaffInfo(){
		this.addStaffRoles = roleService.getAllRoles();
		int excResult = this.staffService.addNewStaffInfo(this.actionModel.getStaffName(), this.actionModel.getStaffRole());
		if(excResult == 1){
			logger.info("添加新用户："+this.actionModel.getStaffName()+"成功！");
			registerScript("fnAddNewStaffSuccess();", true);
		}else{
			logger.error("添加新用户失败："+this.actionModel.getStaffName()+"已存在！");
			registerScript("fnStaffNameIsExist();", true);
		}
		return StaffActionConst.AddStaffInfo_Success;
	}
	
	public String editStaffInfo(){
		this.addStaffRoles = roleService.getAllRoles();
		LMS_Staffs staffInfo = staffService.getStaffByStaffID(actionModel.getStaffID());
		staffInfo.setName(actionModel.getStaffName());
		LMS_Roles roleInfo = roleService.getOneRole(actionModel.getStaffRole());
		staffInfo.setRole(roleInfo);
		int updateResult  = staffService.updateStaff(staffInfo);
		if (updateResult == 0) {
			registerScript("fnEditStaffNameIsExist();", true);
			return  StaffActionConst.EditStaffInfo_NameIsExist;	
		}
		registerScript("fnEditStaffSuccess();", true);
		return StaffActionConst.EditStaffInfo_Success;
	}
	
	public String getOneStaffInfo(){
		this.addStaffRoles = roleService.getAllRoles();
		this.staffInfo = staffService.getStaffByStaffID(actionModel.getStaffID());
		return StaffActionConst.GetStaffByStaffID_Success;
	}
	
	public String completeStaffInfo(){
		SessionUser sessionUser = getSessionUser();
		try {
			LMS_Staffs staffInfo = staffService.getStaffByUserName(sessionUser.getLoginName());
			BeanUtility.copyProperties(actionModel, staffInfo);
			staffService.updateStaffInfo(staffInfo);
			registerScript("fnCompleteStaffInfo();", true);
			logger.info(sessionUser.getLoginName()+"已完善个人资料！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(sessionUser.getLoginName()+"完善资料出错:"+e.getMessage());
		}
		return StaffActionConst.CompleteStaffInfo_Success;
	}
	public String getStaffInfoDetail(){
		SessionUser sessionUser = getSessionUser();
		try {
			this.staffInfo = staffService.getStaffByUserName(sessionUser.getLoginName());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(sessionUser.getLoginName()+"完善资料出错:"+e.getMessage());
		}
		return StaffActionConst.GetStaffInfoDetail_Success;
	}
	
	public String getStaffInfoList(){
		this.setPageSize(5);
		this.staffList = staffService.getStaffList(page);
		if(!StringUtil.isNullOrEmpty(getRequest("resetpwd")) && getRequest("resetpwd").equals("1")){
			registerScript(
				"window.parent.Messenger().post({message:\"密码重置成功！\",hideAfter:3});"
			,true);
		}
		return StaffActionConst.GetStaffList_Success;
	}
	
	public String deleteOneStaffInfo(){
		staffService.deleteOneStaff(actionModel.getStaffID());
		return StaffActionConst.DeleteOneStaffInfo_Success;
	}
	
	public String resetStaffPwd(){
		boolean resetResult = staffService.updateStaffPassword(actionModel.getStaffID());
		if(resetResult)
			return StaffActionConst.ResetStaffPwd_Success;
		else
			return StaffActionConst.ResetStaffPwd_Fialed;
	}
	// [start] Get and set methods

	
	public Collection<LMS_Roles> getAddStaffRoles() {
		return addStaffRoles;
	}

	
	public Collection<AuthorInfo> getStaffAuthors() {
		return staffAuthors;
	}

	public void setStaffAuthors(Collection<AuthorInfo> staffAuthors) {
		this.staffAuthors = staffAuthors;
	}

	public void setAddStaffRoles(Collection<LMS_Roles> addStaffRoles) {
		this.addStaffRoles = addStaffRoles;
	}
	

	public LMS_Staffs getStaffInfo() {
		return staffInfo;
	}

	public void setStaffInfo(LMS_Staffs staffInfo) {
		this.staffInfo = staffInfo;
	}

	public StaffService getStaffService() {
		return staffService;
	}

	public void setStaffService(StaffService staffService) {
		this.staffService = staffService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public Collection<LMS_Staffs> getStaffList() {
		return staffList;
	}

	public void setStaffList(Collection<LMS_Staffs> staffList) {
		this.staffList = staffList;
	}
	@Override
	public StaffActionModel getModel() {
		return actionModel;
	}
	// [end]
}
