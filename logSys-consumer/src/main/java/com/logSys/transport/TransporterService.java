package com.logSys.transport;

/**
 * Created by ytj on 2017/12/21.
 */
public interface TransporterService {
    /***
     * 调用
     * @param msg
     * @return
     */
    boolean invoke(String msg);
}
