package lms.common;

import dev.frame.util.StringUtil;

public class Utils {
	  /**
     * 截取字符串　超出的字符用symbol代替 　　
     * 
     * @param len
     *            　字符串长度　长度计量单位为一个GBK汉字　　两个英文字母计算为一个单位长度
     * @param str
     * @param symbol
     * @return
     */
    public static String getLimitLengthString(String str, int len, String symbol) {
        int iLen = len * 2;
        int counterOfDoubleByte = 0;
        String strRet = "";
        try {
            if (str != null) {
                byte[] b = str.getBytes("GBK");
                if (b.length <= iLen) {
                    return str;
                }
                for (int i = 0; i < iLen; i++) {
                    if (b[i] < 0) {
                        counterOfDoubleByte++;
                    }
                }
                if (counterOfDoubleByte % 2 == 0) {
                    strRet = new String(b, 0, iLen, "GBK") + symbol;
                    return strRet;
                } else {
                    strRet = new String(b, 0, iLen - 1, "GBK") + symbol;
                    return strRet;
                }
            } else {
                return "";
            }
        } catch (Exception ex) {
            return str.substring(0, len);
        } finally {
            strRet = null;
        }
    }

    /**
     * 截取字符串　超出的字符用symbol代替 　　
     * 
     * @param len
     *            　字符串长度　长度计量单位为一个GBK汉字　　两个英文字母计算为一个单位长度
     * @param str
     * @param symbol
     * @return12
     */
    public static String getLimitLengthString(String str, int len) {
        return getLimitLengthString(str, len, "...");
    }
    /**
     * 返回对应的type类型
     * @param index
     * @return
     */
    public static String getErrorType(int index) {
    	String  type="";
	    switch (index) {
	        case 1:
	        	type="无网络";
	        	break;
	        case 2:
	        	type="解析异常";
	        	break;
	        case 3:
	        	type="网络超时";
	        	break;
	        case 4:
	        	type="返回数据异常";
	        	break;
	        case 5:
	        	type="应用崩溃";
	        	break;
	        case 6:
	        	type="程序片断异常";
	        	break;
	        case 7:
	        	type="日志记录";
	        	break;
	    }
	    return type;
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
