package lms.code.action.test;
import lms.common.AbstractAction;
import lms.common.SiteConfig;
import org.apache.struts2.StrutsTestCase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.WebApplicationContext;

import com.opensymphony.xwork2.ActionProxy;
/**
 * JUnit 测试 Struts action的超类，
 * 因为是在不开启tomcat的情况下进行，
 * 所以request、response等http对象都得经过此类进行虚拟开启并加载spring容器对象。
 * @author 耗子
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ 
    "classpath:config/spring/applicationContext-*.xml"
})
public abstract class AbstractSpringStruts2JUnit4 extends StrutsTestCase implements ApplicationContextAware {

    protected ApplicationContext applicationContext;
    
    @Autowired
    protected SessionFactory sessionFactory;
    
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @BeforeClass
    public static void initialize() {
    	SiteConfig.AppPath = "/Users/ldfs/tomcat/apache-tomcat-7.0.65/webapps/LogManage";
        // in my case, I create a mock JNDI here, 
        // including a mailSession using a Wiser mock SMTP server
    }

    @Before
    public void setUp() throws Exception {
        super.setUp(); // use JUnit3 setUp chain. In this case setupBeforeInitDispatcher() will be called
        if (! TransactionSynchronizationManager.hasResource(sessionFactory)) {
            Session session = SessionFactoryUtils.getSession(sessionFactory, true);  // first @Before only
            TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
        }
    }

    @Override
    protected void setupBeforeInitDispatcher() throws Exception {
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, applicationContext);
        // inject hibernate sessionFactory into Transaction Management, simulating OpenSessionInView
    }

    @AfterClass
    public static void shutdown() {
        // in my case, I shutdown the mock mail server here
    }
    
    protected AbstractAction createAction(String namespace){
    	ActionProxy proxy = super.getActionProxy(namespace);
		AbstractAction action = (AbstractAction) proxy.getAction();
		action.setServletRequest(request);
		action.setServletResponse(response);
		return action;
    }
    
}