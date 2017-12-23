package com.logSys.controller;

import com.logSys.command.CommandEnum;
import com.logSys.service.QueryLog;
import com.logSys.util.DateUtil;
import com.logSys.vo.LogQueryVo;
import com.logSys.vo.QueryConditionVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytj on 2017/12/21.
 */
@Controller
@RequestMapping("/logQuery")
public class QueryController {
    private static final Logger logger = LogManager.getLogger(QueryController.class);

    @Resource
    private QueryLog queryLog;

    @RequestMapping(value = "/logList", method = RequestMethod.GET)
    public String logList() {
        return "/logList";
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public Object save(HttpServletRequest request){
        try {
            LogQueryVo logQueryVo = getLogQueryVo(request);
            verify(logQueryVo);
            return queryLog.queryLog(logQueryVo);
        }catch (Exception e){
            logger.error("save error,msg",e);
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
    private static String decode(String msg){
        try {
            return URLDecoder.decode(msg,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("decode error,msg={}",msg);
        }
        return "";
    }
    private LogQueryVo getLogQueryVo(HttpServletRequest request){
        LogQueryVo logQueryVo = new LogQueryVo();
        logQueryVo.setUserId(request.getParameter("userId"));
        logQueryVo.setSid(request.getParameter("sid"));
        logQueryVo.setBeginTime(DateUtil.stringToDate(request.getParameter("beginTime")));
        logQueryVo.setEndTime(DateUtil.stringToDate(request.getParameter("endtime")));
        logQueryVo.setConditions(getQueryConditionVo(request));
        return logQueryVo;
    }
    private List<QueryConditionVo> getQueryConditionVo(HttpServletRequest request){
        QueryConditionVo queryConditionVo = new QueryConditionVo();
        queryConditionVo.setTargetValue(request.getParameter("conditionValue"));
        queryConditionVo.setCommand(getCommandEnum(request.getParameter("conditionCommand")));
        List<QueryConditionVo> result = new ArrayList<QueryConditionVo>();
        result.add(queryConditionVo);
        return result;
    }
    private CommandEnum getCommandEnum(String conditionCommand){
        if(CommandEnum.EQUAL_OPER.getMsg().equals(conditionCommand)){
            return CommandEnum.EQUAL_OPER;
        }
        return CommandEnum.NOT_EQULA_OPER;
    }

}
