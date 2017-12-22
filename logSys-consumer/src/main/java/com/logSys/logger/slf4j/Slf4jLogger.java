package com.logSys.logger.slf4j;

import com.logSys.bean.LogInfo;
import com.logSys.logger.ILogger;
import com.logSys.transport.TransporterService;
import com.logSys.util.LogContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.net.InetAddress;

@Service
public class Slf4jLogger implements ILogger, Serializable {

    private static final long serialVersionUID = 1L;

    @Resource
    private TransporterService transporterService;

    @Override
    public void info(String msg) {
        LogInfo info = LogContext.getContextLogInfo();
        if(info == null){
            info = LogContext.begin(getLocalIp(),"","","");
        }
        transporterService.invoke(info.toString() + msg);

    }

    /**
     * 获取当前机器ip
     * @return
     */
    private static String getLocalIp(){
        try {
            return InetAddress.getLocalHost().getHostAddress().toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
