package lms.code.model;

import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;

import dev.frame.util.StringUtil;
import lms.code.dao.NewLogDao;
import lms.common.SpringBeanUtility;
import lms.common.ToolUtil;

public class QuartzScheduler {
	private static Logger logger = Logger.getLogger(QuartzScheduler.class.getName());
	public void doJob() {
		System.out.println("定时器-调度进行中...");
		NewLogDao logDao= (NewLogDao) SpringBeanUtility.getBean("newLogDao");
		if(logDao!=null){
			deleteBatchLogs(logDao);
			insertBatchLogsByection(logDao);
		}
	}
	/**
	 * 批量插入数据库最后时间到现在的数据
	 * @return
	 */
	public void insertBatchLogsByection(NewLogDao logDao) {
		logger.error("定时器执行：批量插入数据库最后时间到现在的数据insertBatchLogsByection(NewLogDao logDao)");
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
	/**
	 * 批量删除2个月以前的日志
	 */
	public void deleteBatchLogs(NewLogDao logDao) {
		logger.error("定时器执行：批量删除2个月以前的日志deleteBatchLogs(NewLogDao logDao)");
		logDao.executeUpdate(StringUtil.format(logDao.deleteLogs,ToolUtil.getPreviousMonth(-2)));
		logDao.executeUpdate(StringUtil.format(logDao.deleteHtmlLogs,ToolUtil.getPreviousMonth(-2)));
	}
}