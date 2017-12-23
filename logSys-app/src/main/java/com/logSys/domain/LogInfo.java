package com.logSys.domain;

/**
 * Created by ytj on 2017/12/20.
 */
public class LogInfo {
    /**
     * 日志唯一ID用于跟踪处理经过代码路劲的跟踪。唯一标示请求链路
     */
    private String logId;

    /**
     * 客户端请求IP地址
     */
    private String clientIp;

    /**
     * 日志发生记录时间
     */
    private long recordTime;
    /**
     * 日志发生记录时间
     */
    private String recordTimeString;

    /**
     * 用户ID 只有在用户登录方式请求时才有意义
     */
    private String userId;

    /**
     * 用户登录标示
     */
    private String sid;
    /**
     * 日志内容
     */
    private String msg;


    @Override
    public String toString() {
        return logId + "|" + recordTime +"|" + clientIp + "|" + userId + "|" + sid + "|" + "|" + msg;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }


    public long getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(long recordTime) {
        this.recordTime = recordTime;
    }

    public String getRecordTimeString() {
        return recordTimeString;
    }

    public void setRecordTimeString(String recordTimeString) {
        this.recordTimeString = recordTimeString;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
