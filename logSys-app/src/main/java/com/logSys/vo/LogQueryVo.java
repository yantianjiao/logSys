package com.logSys.vo;

import java.util.List;

/**
 * Created by ytj on 2017/12/19.
 */
public class LogQueryVo extends BaseVo{
    private int from;
    private int to;
    private String pathname;
    private List<QueryConditionVo> conditions;

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getPathname() {
        return pathname;
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
    }

    public List<QueryConditionVo> getConditions() {
        return conditions;
    }

    public void setConditions(List<QueryConditionVo> conditions) {
        this.conditions = conditions;
    }
}
