package com.logSys.vo;

import com.logSys.command.CommandEnum;

import java.io.Serializable;

/**
 * fieldName字段非时间，需要加keyword，如mobileNo.keyword<br />
 *
 * Created by kevin on 2017/9/14.
 */
public class QueryConditionVo implements Serializable{
    /** 搜索的值 */
    private String targetValue;
    /** 操作，默认相等 */
    private CommandEnum command = CommandEnum.EQUAL_OPER;

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }

    public CommandEnum getCommand() {
        return command;
    }

    public void setCommand(CommandEnum command) {
        this.command = command;
    }
}
