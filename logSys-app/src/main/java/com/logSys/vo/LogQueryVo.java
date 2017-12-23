package com.logSys.vo;

import java.util.Date;
import java.util.List;

/**
 * Created by ytj on 2017/12/19.
 */
public class LogQueryVo extends BaseVo{
    private Date beginTime;
    private Date endTime;
    private String userId;
    private String sid;
    private List<QueryConditionVo> conditions;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public List<QueryConditionVo> getConditions() {
        return conditions;
    }

    public void setConditions(List<QueryConditionVo> conditions) {
        this.conditions = conditions;
    }
}
