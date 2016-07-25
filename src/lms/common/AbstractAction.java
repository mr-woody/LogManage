package lms.common;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lms.code.action.returnconst.AbstractActionConst;
import lms.struts.tags.Page;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ValidationAwareSupport;

import dev.frame.util.StringUtil;
@Controller
@Scope("prototype")
@Results({ 
	@Result(name = AbstractActionConst.SessionOut, location = "/common/SessionOut.jsp")
   ,@Result(name = AbstractActionConst.Page404, location = "/common/Page404.jsp")
})
public abstract class AbstractAction extends ActionSupport implements 
		ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = 1455454783011520337L;
	private static final ActionSupport actionSupport = new ActionSupport();
	private  ValidationAwareSupport validationAware = new ValidationAwareSupport();
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Logger logger = Logger.getLogger(this.getClass().getName());
	protected String method;
	protected Integer pageIndex = 1;
	protected Integer pageSize = 10;
	protected Integer pageCount;
	protected Integer recordCount;
	protected Page page = new Page();
	protected String actionScript;
	
	public String execute() throws Exception {
		SiteConfig.DoMain = StringUtil.isNullOrEmpty(SiteConfig.DoMain)
			?(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/")
			:SiteConfig.DoMain;
		String actionForward = ERROR;
		request.setAttribute("URICopy", request.getRequestURI());
		request.setAttribute("methodCopy", method);
		Initialize();
		logger.info("开始执行" + method + "...");
		if (method != null && method.length() > 0) {
			// 如果用户没有登陆或登陆信息失效
			if (!method.equals("staffSignIn") && getSessionUser() == null) {
				return AbstractActionConst.SessionOut;
			}
			Object[] obj = {};
			if (method.indexOf(",") != -1) {
				method = method.substring(0, method.indexOf(","));
			}
			Method m = getMethodByName(method, obj.length);
			logger.info("开始运行" + method + "...");
			if (m == null) {
				logger.error(method + "不存在！");
			} else {
				try {
					logger.info("执行" + method + "方法");
					actionForward = (String) m.invoke(this, obj);
					if (actionForward == null) {
						// actionForward = SUCCESS;
					}
				} catch (Throwable e) {
					logger.error(method + "发生错误");
					e.printStackTrace();
				}
			}
		} else {
			return AbstractActionConst.Page404;
		}
		method = "";
		return actionForward;
	}
	
	
	//[start] private methods
	private void Initialize(){
		
	}
	private Method getMethodByName(String methodName, int paramNum) {
		Method method = null;
		Class<?> testInfo = getClass();
		Method methodsub[] = testInfo.getMethods();
		for (int i = 0; i < methodsub.length; i++)
			if (methodsub[i].getName().equals(methodName.trim())
					&& methodsub[i].getParameterTypes().length == paramNum
					&& methodsub[i] != null) {
				method = methodsub[i];
				return method;
			}

		return method;
	}
	//[end]
	
	// [start] action often use methods
	protected String getRequest(String paramName) {
		return this.request.getParameter(paramName);
	}
	protected HttpSession getSession(){
		return request.getSession();
	}
	
	protected Object getSession(String sessionKey) {
		return this.getSession().getAttribute(sessionKey);
	}
	protected void setSession(String sessionKey,Object sessionValue) {
		 this.getSession().setAttribute(sessionKey,sessionValue);
	}
	
	protected SessionUser getSessionUser() {
		return (SessionUser) this.getSession().getAttribute(SiteConfig.SessionUserKey);
	}

	protected String getRequestUrl() {
		return this.request.getRequestURI();
	}
	
	public  void setActionErrors(Collection<String> errorMessages) {
		validationAware.setActionErrors(errorMessages);
	}

	public Collection<String> getActionErrors() {
		return validationAware.getActionErrors();
	}

	public void setActionMessages(Collection<String> messages) {
		validationAware.setActionMessages(messages);
	}

	public Collection<String> getActionMessages() {
		return validationAware.getActionMessages();
	}

	public void setFieldErrors(Map<String,List<String>> errorMap) {
		validationAware.setFieldErrors(errorMap);
	}

	public Map<String,List<String>> getFieldErrors() {
		return validationAware.getFieldErrors();
	}

	public boolean hasActionErrors() {
		return validationAware.hasActionErrors();
	}

	public boolean hasActionMessages() {
		return validationAware.hasActionMessages();
	}

	public boolean hasErrors() {
		return validationAware.hasErrors();
	}

	public boolean hasFieldErrors() {
		return validationAware.hasFieldErrors();
	}

	public void addActionError(String anErrorMessage) {
		validationAware.addActionError(getGlobalMessage(anErrorMessage));
	}

	public void addActionError(String... anErrorMessage) {
		validationAware.addActionError(getGlobalMessage(anErrorMessage));
	}

	public void addActionMessage(String... aMessage) {
		validationAware.addActionMessage(getGlobalMessage(aMessage));
	}

	public void addActionMessage(String aMessage) {
		validationAware.addActionMessage(getGlobalMessage(aMessage));
		request.setAttribute("flag", "success");
	}

	private String getGlobalMessage(String... messages) {
		String globalMessage = "";
		for (String m : messages) {
			String tempmessage = actionSupport.getText(m);
			if (!tempmessage.equals("")) {
				globalMessage += tempmessage;
			} else {
				globalMessage += m;
			}
		}
		return globalMessage;
	}
	public void addFieldError(String fieldName, String errorMessage) {
		validationAware.addFieldError(fieldName, errorMessage);
	}
	// [end]
	
	//[start] register script to jsp
	/**
	 * 为前端jsp页面注册脚本，<br/>如果前端需要获取则要显示${actionScript}action变量
	 * @param script 脚本内容
	 * @param isAddScriptTag 是否添加script标签
	 */
	protected void registerScript(String script,boolean isAddScriptTag){
		this.actionScript = script;
		if(isAddScriptTag)
			this.actionScript = "<script type=\"text/javascript\">"+script+"</script>";
	}
	//[end]
	
	// [start] Get and set methods
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public HttpServletRequest getServletRequest() {
		return this.request;
	}

	public HttpServletResponse getServletResponse() {
		return this.response;
	}
	public String getMethod() {
		return method;
	}
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		Page.setEveryPage(pageSize);
		this.pageSize = pageSize;
	}


	public void setMethod(String method) {
		this.method = method;
	}
	
	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageNo) {
		this.page.setCurrentPage(pageNo);
		this.pageIndex = pageNo;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getActionScript() {
		return actionScript;
	}

	public void setActionScript(String actionScript) {
		this.actionScript = actionScript;
	}

	public Integer getRecordCount() {
		return page.getElementTotal();
	}


	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}
	
	public Integer getPageCount() {
		return this.page.getTotalPage();
	}


	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	
	
	// [end]
}
