package lms.code.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import dev.frame.util.StringUtil;
import lms.code.action.model.NewLogActionModel;
import lms.code.beans.LMS_ExceLogs;
import lms.common.AbstractDao;
import lms.common.IOUtils;
import lms.common.ToolUtil;
import lms.common.Utils;

@Repository
public class NewLogDao extends AbstractDao{
	private static Logger logger = Logger.getLogger(NewLogDao.class.getName());
	private final String LOGS_FILE_PATH = "/data/logs/wkd/";
	private final String PATHS[] = {"wx1/","wx2/","wx3/"};
	
	private String GetNewLogsByTable = " from LMS_ExceLogs a where 1=1 ";

	private String insertNewLogs = " insert into LMS_ExceLogs(ct,type,url,requestMethod,uid,channel,devicePlatform," +
			"errorData,messageId,deviceMessage,serverMessage,useTime,appVersion,deviceBrand,deviceModel,access,width,height," +
			"osVersion,cpuCount,freeMemory,totalMemory) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
	
	private String insertHtmlLogs = " insert into LMS_HtmlLogs(messageId,errorData,ct) values(?,?,?) ";
	//获取目前数据库中最大的时间和最小时间区域
	public String timeSectionLogs = " SELECT min(ct) as startTime,max(ct) as endTime FROM LMS_ExceLogs ";
	//删除指定时间区域的日志
	public String deleteLogs = " delete from LMS_ExceLogs where ct>{0} ";
	public String deleteHtmlLogs = " delete from LMS_HtmlLogs where ct>{0} ";
	//删除指定时间日志
	public String deleteOneLogs = " delete from LMS_ExceLogs where ct>={0} and ct<={1}";
	public String deleteOneHtmlLogs = " delete from LMS_HtmlLogs where ct>={0} and ct<={1} ";
	
	public String getHtmlLogs = " SELECT * from LMS_HtmlLogs where messageId={0} ";
	
	public String getSQL(NewLogActionModel data){
		String sql=GetNewLogsByTable;
		//日志查询开始时间
		if(!StringUtil.isNullOrEmpty(data.getStartTime())){
			sql+="and ct >= '"+ToolUtil.date2TimeStampByLong(data.getStartTime(), "yyyy-MM-dd") +"' ";
		}
		//日志查询结束时间
		if(!StringUtil.isNullOrEmpty(data.getEndTime())){
			sql+="and ct <= '"+ToolUtil.date2TimeStampByLong(data.getEndTime(), "yyyy-MM-dd")+"' ";
		}
		//日志类型
		if(!StringUtil.isNullOrEmpty(data.getType())){
			sql+="and type= '"+data.getType()+"' ";
		}
		//用户uid
		if(!StringUtil.isNullOrEmpty(data.getUid())){
			sql+="and uid= '"+data.getUid()+"' ";
		}
		//用户渠道
		if(!StringUtil.isNullOrEmpty(data.getChannel())){
			sql+="and channel like '%"+data.getChannel()+"%' ";
		}
		//设备平台(Android/Ios)
		if(!StringUtil.isNullOrEmpty(data.getDevicePlatform())){
			sql+="and devicePlatform= '"+data.getDevicePlatform()+"' ";
		}
		//异常数据
		if(!StringUtil.isNullOrEmpty(data.getErrorData())){
			sql+="and errorData like '%"+data.getErrorData()+"%' ";
		}
		
		//平台返回提示信息
		if(!StringUtil.isNullOrEmpty(data.getDeviceMessage())){
			sql+="and deviceMessage like '%"+data.getDeviceMessage()+"%' ";
		}
		String appVersion=data.getAppVersion();
		//软件版本
		if(!StringUtil.isNullOrEmpty(appVersion)){
			if(appVersion.contains(",")){
				String appVersionStr=(" ('"+appVersion+"') ").replaceAll(", ", "','");
				sql+="and appVersion in "+appVersionStr+" ";
			}else{
				sql+="and appVersion= '"+data.getAppVersion()+"' ";
			}
		}
		//系统版本
		if(!StringUtil.isNullOrEmpty(data.getOsVersion())){
			sql+="and osVersion like '%"+data.getOsVersion()+"%' ";
		}
		
		//厂商
		if(!StringUtil.isNullOrEmpty(data.getDeviceBrand())){
			sql+="and deviceBrand like '%"+data.getDeviceBrand()+"%' ";
		}
		//手机型号
		if(!StringUtil.isNullOrEmpty(data.getDeviceModel())){
			sql+="and deviceModel like '%"+data.getDeviceModel()+"%' ";
		}
		sql+=" order by ct desc";
		return sql;
	}
	public void insertBatchLogs(String start,String end){
		for(int i=0;i<PATHS.length;i++){
			insertBatchLogs(LOGS_FILE_PATH+PATHS[i],start,end);
		}
	}
	
	public void insertBatchLogs(String filePath,String start,String end){
		Long startTime=Long.parseLong(StringUtil.isNullOrEmpty(start)?"0":ToolUtil.dateFormatByString(start, "yyyy-MM-dd","yyyyMMdd"));
		Long endTime=Long.parseLong(StringUtil.isNullOrEmpty(end)?"0":ToolUtil.dateFormatByString(end, "yyyy-MM-dd","yyyyMMdd"));
		
	    File file = new File(filePath);
	    if (!file.exists()) return;
	    File[] files = file.listFiles();
	    if (null != files) {
	        ArrayList<String> results = new ArrayList<String>();
	        for (int i = 0; i < files.length; i++) {
	            if (!files[i].getName().endsWith(".log") && !isExists(files[i].getName(),startTime,endTime)) continue;
	            BufferedReader reader = null;
	            try {
	                reader = new BufferedReader(new FileReader(files[i]));
	                Pattern allPattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}\\|[\\w:\\.\\s|]+memory:[\\w/\\s-]+B");//日志起始正则
	                Pattern startPattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}\\|");//日志起始正则
	                Pattern endPattern = Pattern.compile("memory:[\\w/\\s-]+B");//日志结束正则,memory:145 MB/850 MB
	                String line;
	                StringBuilder builder = new StringBuilder();
	                while (null != (line = reader.readLine())) {
	                    Matcher matcher = allPattern.matcher(line);
	                    Matcher startMatcher = startPattern.matcher(line);
	                    Matcher endMatcher = endPattern.matcher(line);
	                    if (matcher.find()) {
	                        results.add(line);
	                        builder.delete(0, builder.length());//清空上一次文本内容
	                    } else if (startMatcher.find()) {
	                        builder.delete(0, builder.length());//清空上一次文本内容
	                        //单独记录头
	                        builder.append(line);
	                    } else if (endMatcher.find()) {
	                        builder.append(line);
	                        results.add(builder.toString());
	                    } else {
	                        builder.append(line);
	                    }
	                }
	            } catch (FileNotFoundException e) {
	                e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            } finally {
	                IOUtils.closeStream(reader);
	            }
	        }
	        int size = results.size();
	        System.out.println("======检索出:(" + size + ")条记录=========");
	        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}\\|" +
	                "add_time:(\\d+)\\|" +
	                "error_type:(\\d)\\|" +
	                "url:(.+)?\\|" +
	                "request_method:(\\w+)?\\|" +
	                "uid:(\\d+)\\|" +
	                "channel:(\\w+)\\|" +
	                "device_platform:(\\d)\\|" +
	                "error_data:(.+)\\|" +
	                "device_message:(.+)?\\|" +
	                "server_message:(.+)?\\|" +
	                "use_time:(\\d+)\\|" +
	                "app_version:([\\d\\.]+)\\|" +
	                "device_brand:(.+)\\|" +
	                "device_model:(.+)\\|" +
	                "access:(\\w+)\\|" +
	                "resolution:(\\w+)x(\\w+)\\|" +
	                "os_version:(.+)\\|" +
	                "cpu_count:(\\d+)\\|" +
	                "memory:(.+)/(.+)");
	        
	        int insertSize = 0;
	        LMS_ExceLogs item=null;
	        Session session =null;
	        Connection conn=null;
	        PreparedStatement stmt=null,stmt1=null;
	        String valueTag = "Value:";
	        try {
		        session = getHibernateTemplate().getSessionFactory().openSession();
		        conn = session.connection();
				stmt = conn.prepareStatement(insertNewLogs);
				stmt1= conn.prepareStatement(insertHtmlLogs);
				//批量提交
            	conn.setAutoCommit(false);
		        Pattern tagPattern = Pattern.compile(valueTag + "(<!DOCTYPE html|<html)");
		        for (int i = 0; i < size; i++) {
		            String result = results.get(i);
		            Matcher matcher = pattern.matcher(result);
		            if (matcher.find()) {
		            	item = new LMS_ExceLogs();
		                item.ct = Utils.getIntValue(matcher.group(1));//1447666667
		                item.type = Utils.getIntValue(matcher.group(2));//5
		                item.url = matcher.group(3);//null
		                item.requestMethod = matcher.group(4);//null
		                item.uid = matcher.group(5);//6042237
		                item.channel = matcher.group(6);//c1005
		                item.devicePlatform = Utils.getIntValue(matcher.group(7));//2
		                String errorData = matcher.group(8);//java.lang.RuntimeException: Adding window failed
		                item.errorData = errorData;
		                String deviceMessage = matcher.group(9);// 应用崩滑了~
		                item.deviceMessage = deviceMessage;
		                item.serverMessage = matcher.group(10);//null
		                item.useTime = Utils.getIntValue(matcher.group(11));//943
		                item.appVersion = matcher.group(12);//2.0.2
		                item.deviceBrand = matcher.group(13);//oppo
		                item.deviceModel = matcher.group(14);//x9007
		                item.access = matcher.group(15);//wifi
		                item.width = Utils.getIntValue(matcher.group(16));//1080
		                item.height = Utils.getIntValue(matcher.group(17));//1920
		                item.osVersion = matcher.group(18);//X9007_12_141117
		                item.cpuCount = Utils.getIntValue(matcher.group(19));//4
		                item.freeMemory = matcher.group(20);//505 MB
		                item.totalMemory = matcher.group(21);//1.76 GB
		                
		                if (!StringUtil.isNullOrEmpty(errorData)) {
		                	stmt.setLong(1, item.ct);
		                	stmt.setInt(2, item.type);
		                	stmt.setString(3, item.url);
		                	stmt.setString(4, item.requestMethod);
		                	stmt.setString(5, item.uid);
		                	stmt.setString(6, item.channel);
		                	stmt.setInt(7, item.devicePlatform);
		                	stmt.setString(8, item.errorData);
		                	Long messageId=ToolUtil.getNo(i);
		                	stmt.setLong(9, messageId);
		                	if (!StringUtil.isNullOrEmpty(deviceMessage)) {
		                		matcher = tagPattern.matcher(deviceMessage);
			                    if (matcher.find()) {
			                    	stmt.setString(10, "查看信息详情");
			                	}else{
			                		stmt.setString(10, item.deviceMessage);
			                	}
		                	}else{
		                		stmt.setString(10, item.deviceMessage);
		                	}
		                	stmt.setString(11, item.serverMessage);
		                	stmt.setLong(12, item.useTime);
		                	stmt.setString(13, item.appVersion);
		                	stmt.setString(14, item.deviceBrand);
		                	stmt.setString(15, item.deviceModel);
		                	stmt.setString(16, item.access);
		                	stmt.setInt(17, item.width);
		                	stmt.setInt(18, item.height);
		                	stmt.setString(19, item.osVersion);
		                	stmt.setInt(20, item.cpuCount);
		                	stmt.setString(21, item.freeMemory);
		                	stmt.setString(22, item.totalMemory);
		                	stmt.addBatch();
		                	if (!StringUtil.isNullOrEmpty(deviceMessage)) {
			                	 matcher = tagPattern.matcher(deviceMessage);
			                     if (matcher.find()) {
			                    	 stmt1.setLong(1, messageId);
					                 stmt1.setString(2, item.deviceMessage);
					                 stmt1.setLong(3, item.ct);
					                 stmt1.addBatch();
			                     }
			                }
		                	
		                	if (i % 100 == 0) {
			                	stmt.executeBatch();
			                	stmt1.executeBatch();
			                	conn.commit();
		                	}
		                	insertSize++;
		                }
		                
		            } else {
		                System.out.println(result);
		            }
		        }
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	logger.error("批量插入数据失败：" + e.getMessage());
			} finally{
				try {
					if(stmt!=null) stmt.executeBatch();
					if(stmt1!=null) stmt1.executeBatch();
					if(conn!=null) conn.commit();
				} catch (Exception e) {
					logger.error("批量插入数据异常：" + e.getMessage());
				}finally{
					try {
						if(stmt!=null){
							stmt.close();
						}
					} catch (Exception e) {
						logger.error("关闭stmt异常：" + e.getMessage());
					}finally{
						stmt=null;
					}
					try {
						if(stmt1!=null){
							stmt1.close();
						}
					} catch (Exception e) {
						logger.error("关闭stmt1异常：" + e.getMessage());
					}finally{
						stmt1=null;
					}
					try {
						if(conn!=null){
							conn.close();
						}
					} catch (SQLException e) {
						logger.error("关闭conn异常：" + e.getMessage());
					}finally{
						conn=null;
					}
					
					// 关闭session
					session.flush();
					session.clear();
					session.close();
				}
            	
			}
	        logger.info("总数:" + size + " 插入记录:" + insertSize);
	    }
	
	}
	private boolean isExists(String name,Long startTime,Long endTime){
		if(startTime<=0 && endTime<=0){
			return true;
		}
		name=(name.replaceAll("_", "")).replaceAll(".log", "");
		Long currTime=Long.parseLong(StringUtil.isNullOrEmpty(name)?"0":name);
		if(currTime>=startTime && currTime<=endTime || currTime>=startTime && endTime==0){
			return true;
		}
		return false;
	}
	
	@Override
	public Class<?> getReferenceClass() {
		return LMS_ExceLogs.class;
	}
}
