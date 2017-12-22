package com.logSys.transport.impl;

import com.logSys.transport.TransporterService;
import com.logSys.util.HttpUtil;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ytj on 2017/12/21.
 */
@Service
public class HttpTransporterServiceImpl implements TransporterService {
    private static String LOG_SERVER_URL ="";

    public static int CORE_POOL_SIZE = 100;
    public static int MAX_POOL_SIZE = 100;
    public static int BLOCK_CAPACITY = 1000;

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 30000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(BLOCK_CAPACITY),new ThreadPoolExecutor.CallerRunsPolicy());
    @Override
    public boolean invoke(final String msg) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUtil.httpGet(LOG_SERVER_URL,"msg="+encode(msg));
            }
        });

        return true;
    }

    private static String encode(String msg){
        try {
            return URLEncoder.encode(msg,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
