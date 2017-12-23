package com.logSys.controller;

import com.logSys.service.QueryLog;
import com.logSys.vo.LogQueryVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by ytj on 2017/12/21.
 */
@Controller
@RequestMapping("/logQuery")
public class QueryController {
    private static final Logger logger = LoggerFactory.getLogger(QueryController.class);
    @Resource
    private QueryLog queryLog;
    @RequestMapping(value = "query", method = RequestMethod.POST)
    @ResponseBody
    public Object save(LogQueryVo vo){
        try {
            verify(vo);
            return queryLog.queryLog(vo);
        }catch (Exception e){
            logger.error("save error,vo={}",vo,e);
            return e.getMessage();
        }
    }
    private boolean verify(LogQueryVo vo) throws Exception{
        if(vo == null){
            throw new Exception("请求参数为空");
        }
        if(vo.getBeginTime() == null || vo.getEndTime() == null){
            throw new Exception("请选择开始时间和结束时间");
        }
        return true;
    }

}
