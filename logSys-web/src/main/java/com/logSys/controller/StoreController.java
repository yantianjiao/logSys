package com.logSys.controller;

import com.logSys.service.StoreLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by ytj on 2017/12/21.
 */
@Controller
@RequestMapping("/logStore")
public class StoreController {
    private static final Logger logger = LogManager.getLogger(StoreController.class);
    @Resource
    private StoreLog storeLog;
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(@RequestBody String msg){
        return storeLog.storeMsg(decode(msg)) ? "OK" : "ERROR";
    }
    private static String decode(String msg){
        try {
            return URLDecoder.decode(msg,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("decode error,msg={}",msg);
        }
        return "";
    }
}
