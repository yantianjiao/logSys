package com.logSys.service.impl;

import com.logSys.service.StoreLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * slf4j存储
 * Created by ytj on 2017/12/18.
 */
@Service("storeLog")
public class Slf4jStoreLog implements StoreLog {

    private static final Logger logger = LogManager.getLogger(Slf4jStoreLog.class);
    public static int CORE_POOL_SIZE = 100;
    public static int MAX_POOL_SIZE = 100;
    public static int BLOCK_CAPACITY = 1000;

    ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 30000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(BLOCK_CAPACITY),new ThreadPoolExecutor.CallerRunsPolicy());


    @Override
    public boolean storeMsg(final String msg) {
        executor.execute(new Runnable() {
            public void run() {
                logger.info(msg);
            }
        });
        return true;
    }
}
