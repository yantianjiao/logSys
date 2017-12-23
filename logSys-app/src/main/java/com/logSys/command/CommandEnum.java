package com.logSys.command;

/**
 * 操作
 * Created by ytj on 2017/12/20.
 */
public enum CommandEnum {
    EQUAL_OPER("=","等于"),
    NOT_EQULA_OPER("!=","不等于");
    ;
    /** 操作 */
    private String command;
    /** 描述 */
    private String msg;

    CommandEnum(String command, String msg) {
        this.command = command;
        this.msg = msg;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}



