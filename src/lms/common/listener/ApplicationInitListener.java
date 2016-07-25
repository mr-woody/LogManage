package lms.common.listener;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import lms.code.beans.LMS_Roles;
import lms.code.beans.LMS_Staffs;
import lms.code.dao.NewLogDao;
import lms.code.dao.RoleDao;
import lms.code.dao.StaffDao;
import lms.common.SiteConfig;
import lms.common.SpringBeanUtility;
import lms.common.ToolUtil;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import dev.frame.encrypt.DESEncrypt;
import dev.frame.util.StringUtil;
import dev.frame.xml.XMLUtility;
@SuppressWarnings({"unchecked"})
public class ApplicationInitListener extends ContextLoaderListener {
	private static Logger logger = Logger.getLogger(ApplicationInitListener.class.getName());
	
	private RoleDao roleDao;
	private StaffDao staffDao;
	private NewLogDao logDao;
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		SiteConfig.AppPath = servletContext.getRealPath("/");
		roleDao =  (RoleDao) SpringBeanUtility.getBean("roleDao");
		staffDao = (StaffDao) SpringBeanUtility.getBean("staffDao");
		logDao= (NewLogDao) SpringBeanUtility.getBean("newLogDao");
		try {
			initializeRoleData();
			initializeStaffData();
			initializeLogsData();
		} catch (Exception e) {
			logger.error("种子数据初始化失败：" + e.getMessage());
		}
	}

	/**
	 * 初始化权限数据
	 */
	private void initializeRoleData() {
		int querySuperAdmin = roleDao.queryByConditions(StringUtil.format(roleDao.QuerySuperAdmin, SiteConfig.SuperAdminName)).size();
		if (querySuperAdmin == 0) {
			LMS_Roles superAdminRole = new LMS_Roles();
			superAdminRole.setName(SiteConfig.SuperAdminName);
			XMLUtility xmlUtility = new XMLUtility(SiteConfig.AppPath+"/WEB-INF/xml/pop.xml");
			Collection<String> attrValues = xmlUtility.getAttrVals(xmlUtility.getRootElement().elements(), "code");
			superAdminRole.setAuthors(StringUtil.join(attrValues, ';'));
		    roleDao.addObject(superAdminRole);
		}
	}

	/**
	 * 初始化账号数据
	 */
	private void initializeStaffData() throws Exception {
		int adminQuery = staffDao.queryByConditions(StringUtil.format(staffDao.QuerySuperAdmin, SiteConfig.SuperAdminLoginName)).size();
		if (adminQuery == 0) {
			Collection<LMS_Roles> adminRole =  roleDao.queryByConditions(StringUtil.format(roleDao.QuerySuperAdmin, SiteConfig.SuperAdminName));
			if(adminRole.size() > 0){
				LMS_Staffs adminStaff = new LMS_Staffs();
				adminStaff.setRole(new ArrayList<LMS_Roles>(adminRole).get(0));
				adminStaff.setLoginName(SiteConfig.SuperAdminLoginName);
				adminStaff.setPassWord(DESEncrypt.encrypt(SiteConfig.SuperAdminLoginName,SiteConfig.StaffPasswordDeKey));
				staffDao.addObject(adminStaff);
			}
		}
	}
	
	/**
	 * 初始化日志数据
	 */
	private void initializeLogsData() {
		logDao.executeUpdate(StringUtil.format(logDao.deleteLogs,ToolUtil.getPreviousMonth(-2)));
		logDao.executeUpdate(StringUtil.format(logDao.deleteHtmlLogs,ToolUtil.getPreviousMonth(-2)));
		logDao.insertBatchLogs(ToolUtil.getPreviousMonth(-2),null);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("Web容器已关闭");
	}
}
