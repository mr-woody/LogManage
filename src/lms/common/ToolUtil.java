package lms.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dev.frame.util.StringUtil;

public class ToolUtil {
	public ToolUtil(){
		
	}
	public static final String [] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
	
	public static String doGetLogListTitle(String selectDate){
		DateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dateStr = simple.parse(selectDate);
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(dateStr);
	        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
	        if (w < 0)  w = 0;
	        return selectDate+ "  "+weekDays[w];

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String doGetReportListDate(Date logDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(logDate);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)  w = 0;
        return weekDays[w];
	}
	
	public static String doGetReportListTitleDate(Date logSection){
		DateFormat simple = new SimpleDateFormat("yyyy年MM月dd日");
        Calendar cal = Calendar.getInstance();
        cal.setTime(logSection);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)  w = 0;
        return simple.format(logSection)+" "+weekDays[w];
	}
	/**
	 * 时间戳转成制定日期格式的字符串
	 * @param mill
	 * @param format
	 * @return
	 */
	public static String convertTimeStamp(long mill,String format){
		Date date = new Date(mill*1000);
		String strs = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			strs = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strs;
	}
	
	/** 
     * 日期格式字符串转换成时间戳字符串
     * @param date 字符串日期 
     * @param format 如：yyyy-MM-dd HH:mm:ss 
     * @return 
     */  
    public static String date2TimeStampByString(String date_str,String format){  
        try {  
            SimpleDateFormat sdf = new SimpleDateFormat(format);  
            return String.valueOf(sdf.parse(date_str).getTime()/1000);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return "";  
    }
    
    /** 
     * 日期格式字符串转换成时间戳Long值
     * @param date 字符串日期 
     * @param format 如：yyyy-MM-dd HH:mm:ss 
     * @return 
     */  
    public static Long date2TimeStampByLong(String date_str,String format){  
        try {  
            SimpleDateFormat sdf = new SimpleDateFormat(format);  
            return sdf.parse(date_str).getTime()/1000;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return 0L;
    }
    
    /**
     * 日期格式字符串格式化
     * @param date_str
     * @param format
     * @return
     */
    public static String dateFormatByString(String date_str,String format1,String format2){  
        try {  
            SimpleDateFormat sdf = new SimpleDateFormat(format1);
            Date date =sdf.parse(date_str);
            return new SimpleDateFormat(format2).format(date);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return "";
    }
    
    public static String dateFormatByString(Date date,String format){  
        try {
            return new SimpleDateFormat(format).format(date);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return "";
    }
    
    /**
     * month个月前的今天
     * @return
     */
    public static String getPreviousMonth(int month){ 
       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
       Calendar lastDate = Calendar.getInstance(); 
       lastDate.add(Calendar.MONTH,month);
       return sdf.format(lastDate.getTime()); 
    }  
    
    
      
    /** 
     * 取得当前时间戳（精确到秒） 
     * @return 
     */  
    public static String timeStamp(){  
        long time = System.currentTimeMillis();  
        String t = String.valueOf(time/1000);  
        return t;  
    }  
	/**
	 * 换行显示
	 * @param mill
	 * @param format
	 * @return
	 */
	public static String convertTimeStampLine(long mill,String format){
		Date date = new Date(mill*1000);
		String strs = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			strs = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!StringUtil.isNullOrEmpty(strs)){
			String[] arr=strs.split(" ");
			strs=arr[0]+"<br/>"+arr[1];
		}
		return strs;
	}
	
	/**
     * 生成id编号
     * @return
     */
    public static synchronized Long getNo(int orderNum) {
    	String str = new SimpleDateFormat("yyMMddHHmm").format(new Date());
        long orderNo = Long.parseLong((str)) * 10000;
        orderNo += (orderNum+1);
        return orderNo;
    }
	 
}
