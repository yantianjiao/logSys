package com.logSys.service;

/**
 * Created by ytj on 2017/12/18.
 */
public interface StoreLog {
    /**
     * 日志存储
     * @param msg
     * @return
     */
    public boolean storeMsg(String msg);
}
