package lms.code.action;

import lms.common.AbstractAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@ParentPackage("ajaxPackage")
@Action("ajaxActions")
@Results({ @Result(type = "json") })
public class AjaxAction extends AbstractAction {
	private static final long serialVersionUID = 206406447636752221L;
	
	private String result;
	
	//[start] get and set methods
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	//[end]
}
