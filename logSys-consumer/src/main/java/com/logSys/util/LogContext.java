package com.logSys.util;

import com.logSys.bean.LogInfo;

import java.util.Date;
import java.util.UUID;

/**
 * Created by ytj on 2017/12/20.
 */
public class LogContext {
    private static ThreadLocal<LogInfo> logThreadLocal = new ThreadLocal<LogInfo>();

    /**
     * 开始记录日志，初始化日志上线文
     * @param clientIp  客户端IP
     * @param userId    当前用户ID
     * @param sid       当前登录sid
     */
    public static LogInfo begin(String clientIp,String userId,String sid)
    {
        LogInfo info =new LogInfo();
        info.setLogId(UUID.randomUUID().toString());
        info.setClientIp(clientIp);
        info.setSid(sid);
        info.setUserId(userId);
        info.setRecordTime(new Date().getTime());
        logThreadLocal.set(info);
        return info;
    }

    /**
     * 设置用户ID
     * @param userId
     */
    public static void setUserId(String userId)
    {
        LogInfo info= logThreadLocal.get();
        if(null!=info)
        {
            info.setUserId(userId);
        }
    }
    /**
     * 删除本地线程变量
     */
    public static void end()
    {
        logThreadLocal.remove();
    }

    /**
     * 获取当前日志基础对象
     * @return
     */
    public static LogInfo getContextLogInfo()
    {
        return logThreadLocal.get();
    }
}
