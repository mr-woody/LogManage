package lms.struts.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;
import com.opensymphony.xwork2.util.ValueStack;

public class PageTag extends ComponentTagSupport {
	private static final long serialVersionUID = 3904791755785891199L;
	private String pageIndex;
	private String pageCount;
	private String styleClass;
	private String theme;
	private String url;
	private String urlType;
	private String recordCount;
	@Override
	public Component getBean(ValueStack arg0, HttpServletRequest arg1,HttpServletResponse arg2) {
		return new Pages(arg0, arg1);
	}

	protected void populateParams() {
		super.populateParams();
		Pages pages = (Pages) component;
		pages.setPageIndex(pageIndex);
		pages.setPageCount(pageCount);
		pages.setStyleClass(styleClass);
		pages.setTheme(theme);
		pages.setUrl(url);
		pages.setUrlType(urlType);
		pages.setRecordCount(recordCount);
	}

	//[start] get and set methods

	public String getStyleClass() {
		return styleClass;
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}


	public String getPageCount() {
		return pageCount;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

	public String getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

	//[end]
}
