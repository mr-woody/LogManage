package lms.code.action;

import java.util.Collection;
import java.util.LinkedList;
import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.opensymphony.xwork2.ModelDriven;
import dev.frame.util.StringUtil;
import lms.code.action.model.NewLogActionModel;
import lms.code.action.returnconst.NewLogActionConst;
import lms.code.beans.LMS_ExceLogs;
import lms.code.beans.LMS_HtmlLogs;
import lms.code.service.NewLogService;
import lms.common.AbstractAction;
import lms.struts.tags.Page;


@ParentPackage("logPackage")
@Action(value = "newLogActions")
@Results({ 
	@Result(name = NewLogActionConst.GetLogsByPage_Success,location = "/manage/LogManage/LogList.jsp")
   ,@Result(name = NewLogActionConst.GetLogsByDetail_Success,location = "/manage/LogManage/LogContentDetail.jsp")
   ,@Result(name = NewLogActionConst.InitializeSelectDays,location = "/manage/LogManage/LogSelectDays.jsp")
})
public class NewLogAction extends AbstractAction implements
		ModelDriven<NewLogActionModel> {
	private static final long serialVersionUID = 5464930498377307800L;
	@Resource
	private NewLogService logService;
	private NewLogActionModel actionModel = new NewLogActionModel();
	private LMS_ExceLogs logDetail;
	private LMS_HtmlLogs htmlDetail;
	private NewLogActionModel actionModelDetail;
	private Collection<LMS_ExceLogs> logList;
	private String selectDate;
	private LinkedList<String> logStr;
	private String logString;
	private String reportedData;
	
	
	public String getLogsByPage(){
		Page.setEveryPage(10);
		if(!StringUtil.isNullOrEmpty(actionModel.getStartTime()))
			this.setSelectDate(actionModel.getStartTime());
		this.actionModelDetail=actionModel;
		this.logList = logService.getLogsByPage(actionModel,page);
		return NewLogActionConst.GetLogsByPage_Success;
	}
	
	public String addOneLogs(){
		logService.addBatchLog(actionModel.getStartTime(),actionModel.getEndTime());
		return SUCCESS;
	}
	public String insertBatchLogsByection(){
		logService.insertBatchLogsByection();
		return SUCCESS;
	}
	public String deleteBatchLogs(){
		logService.deleteBatchLogs();
		return SUCCESS;
	}
	
	public String deleteOneLog(){
		logService.deleteOneLog(actionModel.getLogId());
		return getLogsByPage();
	}
	
	public String getLogsByDetail(){
		this.logDetail = logService.getOneLog(actionModel.getLogId());
		return NewLogActionConst.GetLogsByDetail_Success;
	}
	public String getDeviceMessageDetail(){
		this.htmlDetail = logService.queryDeviceMessageDetail(actionModel.getMessageId());
		return NewLogActionConst.GetLogsByDetail_Success;
	}

	public String initializeSelectDays(){
		return NewLogActionConst.InitializeSelectDays;
	}
	
	// [start] Get and set methods
	@Override
	public NewLogActionModel getModel() {
		return actionModel;
	}

	public String getReportedData() {
		return reportedData;
	}

	public void setReportedData(String reportedData) {
		this.reportedData = reportedData;
	}

	public String getLogString() {
		return logString;
	}

	public void setLogString(String logString) {
		this.logString = logString;
	}

	public LinkedList<String> getLogStr() {
		return logStr;
	}

	public void setLogStr(LinkedList<String> logStr) {
		this.logStr = logStr;
	}

	public LMS_ExceLogs getLogDetail() {
		return logDetail;
	}

	public void setLogDetail(LMS_ExceLogs logDetail) {
		this.logDetail = logDetail;
	}

	public String getSelectDate() {
		return selectDate;
	}

	public void setSelectDate(String selectDate) {
		this.selectDate = selectDate;
	}

	public Collection<LMS_ExceLogs> getLogList() {
		return logList;
	}

	public void setLogList(Collection<LMS_ExceLogs> logList) {
		this.logList = logList;
	}

	public NewLogService getLogService() {
		return logService;
	}

	public void setLogService(NewLogService logService) {
		this.logService = logService;
	}

	public LMS_HtmlLogs getHtmlDetail() {
		return htmlDetail;
	}

	public void setHtmlDetail(LMS_HtmlLogs htmlDetail) {
		this.htmlDetail = htmlDetail;
	}

	public NewLogActionModel getActionModelDetail() {
		return actionModelDetail;
	}

	public void setActionModelDetail(NewLogActionModel actionModelDetail) {
		this.actionModelDetail = actionModelDetail;
	}
	
	// [end]
}
