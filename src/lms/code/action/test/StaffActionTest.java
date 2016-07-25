package lms.code.action.test;

import javax.servlet.http.HttpServletRequest;

import lms.code.action.StaffAction;
import lms.code.action.model.StaffActionModel;
import lms.code.action.returnconst.StaffActionConst;
import lms.common.SessionUser;
import lms.common.SiteConfig;

import org.junit.Before;
import org.junit.Test;
public class StaffActionTest extends AbstractSpringStruts2JUnit4 {
	
	@Before
	public void staffSignInTest() throws Exception {
		StaffAction staffAction = (StaffAction) super.createAction("/actions/staff/staffActions.action");
		StaffActionModel actionModel = staffAction.getModel();
		actionModel.setLoginName("admin");
		actionModel.setPassWord("admin");
		assertEquals(StaffActionConst.StaffSignIn_Success,staffAction.staffSignIn());
	}
	
	@Test
	public void staffSignInTest2() throws Exception {
		StaffAction staffAction = (StaffAction) super.createAction("/actions/staff/staffActions.action");
		HttpServletRequest httpServletRequest = staffAction.getServletRequest();
		SessionUser user =  (SessionUser) httpServletRequest.getSession().getAttribute(SiteConfig.SessionUserKey);
		System.out.println(user);
	}
}
