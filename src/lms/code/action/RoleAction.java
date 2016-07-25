package lms.code.action;


import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.dom4j.Element;

import com.opensymphony.xwork2.ModelDriven;

import dev.frame.util.StringUtil;
import dev.frame.xml.XMLUtility;
import lms.code.action.model.RoleActionModel;
import lms.code.action.returnconst.RoleActionConst;
import lms.code.beans.LMS_Roles;
import lms.code.service.RoleService;
import lms.common.AbstractAction;
import lms.common.SiteConfig;

@ParentPackage("rolePackage")
@Action(value = "roleActions")
@Results({ 
	@Result(name = RoleActionConst.DeleteOneRole_Success,location = "/actions/role/roleActions.action?method=getRoleList",type="redirect") 
   ,@Result(name = RoleActionConst.GetRoleList_Success,location = "/manage/RoleManage/RoleList.jsp")
   ,@Result(name = RoleActionConst.InitialAddOneRole_Success,location = "/manage/RoleManage/AddRole.jsp")
   ,@Result(name = RoleActionConst.AddOneRole_NameExist,location = "/manage/RoleManage/AddRole.jsp")
   ,@Result(name = RoleActionConst.AddOneRole_Success,location = "/manage/RoleManage/AddRole.jsp")
   ,@Result(name = RoleActionConst.GetOneRole_Success,location = "/manage/RoleManage/EditRole.jsp")
   ,@Result(name = RoleActionConst.EditOneRole_NameExist,location = "/manage/RoleManage/EditRole.jsp")
   ,@Result(name = RoleActionConst.EditOneRole_Success,location = "/manage/RoleManage/EditRole.jsp")
})
@SuppressWarnings("unchecked")
public class RoleAction extends AbstractAction implements ModelDriven<RoleActionModel>{
	private static final long serialVersionUID = 4854711175195916257L;
	
	@Resource
	private RoleService roleService;
	private RoleActionModel actionModel = new RoleActionModel();
	
	private Collection<LMS_Roles> roleInfoList;
	
	private Hashtable<String, String> authorList;
	
	private LMS_Roles editRole;
	
	public String getRoleList(){
		roleInfoList = roleService.getAllRoles();
		return RoleActionConst.GetRoleList_Success;
	}
	
	public String deleteOneRole(){
		roleService.deleteOneRole(actionModel.getRoleID());
		return RoleActionConst.DeleteOneRole_Success;
	}
	
	
	public String initialAddOneRole(){
		XMLUtility xmlUtility = new XMLUtility(SiteConfig.AppPath+"/WEB-INF/xml/pop.xml");
		List<Element> popList = xmlUtility.getRootElement().elements();
		authorList = new Hashtable<String, String>();
		for (Element element : popList) {
			authorList.put(element.attributeValue("code"),element.attributeValue("name"));
		}
		return RoleActionConst.InitialAddOneRole_Success;
	}
	
	public String addOneRole(){
		LMS_Roles roleInfo = new LMS_Roles();
		roleInfo.setName(actionModel.getRoleName());
		String authors = StringUtil.join(actionModel.getAuthors(),';');
		roleInfo.setAuthors(authors);
		int addRoleResult = roleService.addOneRole(roleInfo);
		initialAddOneRole();
		if (addRoleResult == 0) {
			registerScript("fnRoleNameIsExist();",true);
			return RoleActionConst.AddOneRole_NameExist;
		}
		registerScript("fnAddNewRoleSuccess();",true);
		return RoleActionConst.AddOneRole_Success;
	}
	
	public String getOneRole(){
		initialAddOneRole();
		this.editRole = roleService.getOneRole(actionModel.getRoleID());
		return RoleActionConst.GetOneRole_Success;
	}
	
	public String editOneRole(){
		initialAddOneRole();
		LMS_Roles roleInfo = roleService.getOneRole(actionModel.getRoleID());
		roleInfo.setName(actionModel.getRoleName());
		String authors = StringUtil.join(actionModel.getAuthors(),';');
		roleInfo.setAuthors(authors);
		int updateResult = roleService.updateOneRole(roleInfo);
		if (updateResult == 0) {
			registerScript("fnRoleNameIsExist();", true);
			return RoleActionConst.EditOneRole_NameExist;
		}
		registerScript("fnEditRoleSuccess();", true);
		return RoleActionConst.EditOneRole_Success;
	}
	
	//[start] Get and set methods
	@Override
	public RoleActionModel getModel() {
		return actionModel;
	}
	
	public Hashtable<String, String> getAuthorList() {
		return authorList;
	}

	public void setAuthorList(Hashtable<String, String> authorList) {
		this.authorList = authorList;
	}
	
	public LMS_Roles getEditRole() {
		return editRole;
	}

	public void setEditRole(LMS_Roles editRole) {
		this.editRole = editRole;
	}

	public Collection<LMS_Roles> getRoleInfoList() {
		return roleInfoList;
	}

	public void setRoleInfoList(Collection<LMS_Roles> roleInfoList) {
		this.roleInfoList = roleInfoList;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	//[end]
}
