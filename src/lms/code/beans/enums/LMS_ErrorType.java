package lms.code.beans.enums;

/**
 * Created by cz on 15/11/24.
 */
public enum LMS_ErrorType {
    //    异常类型;1:无网络,2:解析异常:3:网络超时,4:返回数据异常,5:应用崩溃,6:程序片断异常,7:日志记录
    NO_NETWORK, JSON_ERROR, TIME_OUT, SERVER_ERROR, APP_ERROR, ACTION_ERROR, LOG;
}
