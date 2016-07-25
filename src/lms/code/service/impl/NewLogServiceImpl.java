package lms.code.service.impl;

import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.frame.util.StringUtil;

import lms.code.action.model.NewLogActionModel;
import lms.code.beans.LMS_ExceLogs;
import lms.code.beans.LMS_HtmlLogs;
import lms.code.dao.NewLogDao;
import lms.code.service.NewLogService;
import lms.common.ToolUtil;
import lms.struts.tags.Page;

@Service("NewLogService")
@Transactional
public class NewLogServiceImpl implements NewLogService {
	@Resource
	private NewLogDao logDao;

	public Collection<LMS_ExceLogs> getAllLogs(NewLogActionModel data) {
		return logDao.queryByConditions(logDao.getSQL(data));
	}
	public Collection<LMS_ExceLogs> getLogsByPage(NewLogActionModel data,Page page) {
		return logDao.queryByConditions(logDao.getSQL(data), page);
	}
	
	public boolean addOneLog(LMS_ExceLogs log) {
		return logDao.addObject(log) > 0;
	}
	
	public boolean deleteOneLog(Long logid) {
		return logDao.deleteByPks(logid) > 0;
	}
	
	public LMS_ExceLogs getOneLog(Long logid) {
		return (LMS_ExceLogs) logDao.getObjectByPK(logid);
	}
	
	public boolean updateOneLog(LMS_ExceLogs log) {
		return logDao.updateObj(log, true) > 0;
	}
	@Override
	public void addBatchLog(String start,String end) {
		logDao.insertBatchLogs(start,end);
	}
	@Override
	public void insertBatchLogsByection() {
		Collection<Object> timeList = logDao.queryByConditions(logDao.timeSectionLogs);
		if (timeList!=null && timeList.size()> 0) {
			Object[] list= timeList.toArray();
			if(list!=null && list.length>0){
				Long time2=(Long) ((Object[])list[0])[1];
				if(time2!=null){
					String start=ToolUtil.convertTimeStamp(time2,"yyyy-MM-dd");
					String end=ToolUtil.dateFormatByString(new Date(),"yyyy-MM-dd");
				
					Long startTime1=ToolUtil.date2TimeStampByLong(start +" 0:00:00","yyyy-MM-dd HH:mm:ss");
					Long endTime1=ToolUtil.date2TimeStampByLong(start +" 23:59:59","yyyy-MM-dd HH:mm:ss");
					logDao.executeUpdate(StringUtil.format(logDao.deleteOneLogs,startTime1,endTime1));
					logDao.executeUpdate(StringUtil.format(logDao.deleteOneHtmlLogs,startTime1,endTime1));
					logDao.insertBatchLogs(start,end);
				}
			}
		}
	}
	@Override
	public void deleteBatchLogs() {
		logDao.executeUpdate(StringUtil.format(logDao.deleteLogs,ToolUtil.getPreviousMonth(-2)));
		logDao.executeUpdate(StringUtil.format(logDao.deleteHtmlLogs,ToolUtil.getPreviousMonth(-2)));
	}
	@Override
	public LMS_HtmlLogs queryDeviceMessageDetail(String messageId) {
		return (LMS_HtmlLogs)logDao.queryBySQL(StringUtil.format(logDao.getHtmlLogs,messageId)).toArray()[0];
	}
	
}
