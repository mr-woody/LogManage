package lms.code.action.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import lms.code.action.NewLogAction;
import lms.code.action.StaffAction;
import lms.code.action.returnconst.RoleActionConst;
import lms.code.action.returnconst.StaffActionConst;
import lms.code.beans.LMS_ExceLogs;
import lms.common.IOUtils;
import lms.common.SessionUser;
import lms.common.SiteConfig;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import dev.frame.util.StringUtil;

public class ConToMySQL extends AbstractSpringStruts2JUnit4{
	private static final int MAX_LENGTH = 100;
//	public static void main(String[] args) {
//		// 这里"liuyan"是MySql下建立的一个测试数据库
//		// 3306是MySql数据库服务的默认端口
//		String url = "jdbc:mysql://localhost:3306/Log";
//		String userName = "root";
//		String password = "mysql";
//		Connection con = null;
//		try {
//
//			// 加载驱动器类
//			Class.forName("com.mysql.jdbc.Driver");
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			System.out.println("加载驱动器类时出现异常");
//		}
//		try {
//
//			// 获取数据库连接
//			con = (Connection) DriverManager.getConnection(url, userName,
//					password);
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("出现SQLException异常" + e);
//		}
//		System.out.println("加载驱动器成功");
//		// 接着就可以操作MySql数据库了
//		try {
//			String sql = "CREATE TABLE tableName (id int not null, name varchar(20) not null, age int null, primary key (id));";
//			PreparedStatement preStatement = (PreparedStatement) con
//					.prepareStatement(sql);
//			preStatement.executeUpdate();
//			preStatement.close();
//			con.close();
//			System.out.println("写入MySQL数据库成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	@Test
	public void addOneLogsInTest() throws Exception {
	    File file = new File("/Users/ldfs/Desktop/conf/");
	    if (!file.exists()) return;
	    File[] files = file.listFiles();
	    int count = 0;
	    if (null != files) {
	        ArrayList<String> results = new ArrayList<String>();
	        for (int i = 0; i < files.length; i++) {
	            if (!files[i].getName().endsWith(".log")) continue;
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
	                        count++;
	                        builder.delete(0, builder.length());//清空上一次文本内容
	                    } else if (startMatcher.find()) {
	                        builder.delete(0, builder.length());//清空上一次文本内容
	                        //单独记录头
	                        builder.append(line);
	                    } else if (endMatcher.find()) {
	                        builder.append(line);
	                        results.add(builder.toString());
	                        count++;
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
	        ArrayList<LMS_ExceLogs> items = new ArrayList<LMS_ExceLogs>(size);
	        String valueTag = "Value:";
	        Pattern tagPattern = Pattern.compile(valueTag + "(<!DOCTYPE html>|<html>)");
	        for (int i = 0; i < size; i++) {
	            String result = results.get(i);
	            Matcher matcher = pattern.matcher(result);
	            if (matcher.find()) {
	            	LMS_ExceLogs item = new LMS_ExceLogs();
	                item.ct = getIntValue(matcher.group(1));//1447666667
	                item.type = getIntValue(matcher.group(2));//5
	                item.url = matcher.group(3);//null
	                item.requestMethod = matcher.group(4);//null
	                item.uid = matcher.group(5);//6042237
	                item.channel = matcher.group(6);//c1005
	                item.devicePlatform = getIntValue(matcher.group(7));//2
	                String errorData = matcher.group(8);//java.lang.RuntimeException: Adding window failed
	                item.errorData = errorData;
	                String deviceMessage = matcher.group(9);// 应用崩滑了~
	                item.deviceMessage = deviceMessage;
	                item.serverMessage = matcher.group(10);//null
	                item.useTime = getIntValue(matcher.group(11));//943
	                item.appVersion = matcher.group(12);//2.0.2
	                item.deviceBrand = matcher.group(13);//oppo
	                item.deviceModel = matcher.group(14);//x9007
	                item.access = matcher.group(15);//wifi
	                item.width = getIntValue(matcher.group(16));//1080
	                item.height = getIntValue(matcher.group(17));//1920
	                item.osVersion = matcher.group(18);//X9007_12_141117
	                item.cpuCount = getIntValue(matcher.group(19));//4
	                item.freeMemory = matcher.group(20);//505 MB
	                item.totalMemory = matcher.group(21);//1.76 GB
	                items.add(item);
	                
	                if (!StringUtil.isNullOrEmpty(errorData) && MAX_LENGTH < errorData.length()) {
	                NewLogAction logAction = (NewLogAction) super.createAction("/actions/log/newLogActions.action");
	                logAction.setLogDetail(item);
	                assertEquals(RoleActionConst.AddOneRole_Success,logAction.addOneLogs());
	                }
	                
	     
	            } else {
	                System.out.println(result);
	            }
	        }
	        System.out.println("总数:" + size + " 检测记录:" + items.size());
	    }
	}

	public static int getIntValue(String value) {
	    int target = 0;
	    if (!StringUtil.isNullOrEmpty(value)) {
	        try {
	            target = Integer.valueOf(value);
	        } catch (Exception e) {
	            target = -1;
	        }
	    }
	    return target;
	}
}
