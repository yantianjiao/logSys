package com.logSys.logger;

/**
 * Created by ytj on 2017/12/20.
 */
public interface ILogger {


    /**
     * 输出普通信息
     *
     * @param sid session id 用于标示唯一请求
     * @param userId 用户id，用于标示唯一用户
     * @param msg 信息内容
     */
    public void info(String sid, String userId, String msg);

}
