package lms.code.service;

import java.util.Collection;

import lms.code.action.model.NewLogActionModel;
import lms.code.beans.LMS_ExceLogs;
import lms.code.beans.LMS_HtmlLogs;
import lms.struts.tags.Page;

public interface NewLogService {
	
	/**
	 * 根据条件获取所有的日志对象
	 * @return 日志列表
	 */
	Collection<LMS_ExceLogs> getAllLogs(NewLogActionModel date);
	
	/**
	 * 根据条件获取分页日志列表
	 * @param date 条件
	 * @return 日志列表
	 */
	Collection<LMS_ExceLogs> getLogsByPage(NewLogActionModel date,Page page);
	
	/**
	 * 添加一个工作日志
	 * @param log 日志对象
	 * @return 1：成功 <br/> 0：失败
	 */
	boolean addOneLog(LMS_ExceLogs log);
	/**
	 * 批量添加日志信息
	 * @param data 插入条件
	 * @return
	 */
	void addBatchLog(String start,String end);
	
	/**
	 * 删除一个日志对象
	 * @param logid 日志id
	 * @return 
	 */
	boolean deleteOneLog(Long logid);
	
	/**
	 * 根据logid获取一条log对象
	 * @param logid 
	 * @return
	 */
	LMS_ExceLogs getOneLog(Long logid);
	
	/**
	 * 编辑一个日志信息 
	 * @param log 日志对象
	 * @return
	 */
	boolean updateOneLog(LMS_ExceLogs log);
	
	/**
	 * 批量插入数据库最后时间到现在的数据
	 * @return
	 */
	void insertBatchLogsByection();
	/**
	 * 批量删除2个月以前的日志
	 */
	void deleteBatchLogs();

	LMS_HtmlLogs queryDeviceMessageDetail(String messageId);
	
}
