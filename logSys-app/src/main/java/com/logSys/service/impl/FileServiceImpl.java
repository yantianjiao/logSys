package com.logSys.service.impl;

import com.logSys.domain.LogInfo;
import com.logSys.service.FileService;
import com.logSys.util.DateUtil;
import com.logSys.vo.LogQueryVo;
import com.logSys.vo.QueryConditionVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytj on 2017/12/19.
 */
@Service
public class FileServiceImpl implements FileService{
    @Value("${log.file}")
    private String fileName;//日志文件
    @Value("${log.dir}")
    private String path;//日志存储目录
    /**
     * 根据行数读取文件
     * @param files 文件
     * @return
     * @throws Exception
     */
    public  List<LogInfo> readFiles(List<File> files,LogQueryVo vo) {
        List<LogInfo> content = new ArrayList();
        if(files != null){
            for (File file : files){
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(file));
                    String temp = null;
                    int count = 0;
                    while((temp = br.readLine() ) != null){
                        LogInfo logInfo = getLogInfo(temp);
                        if(matchCondition(logInfo,vo)){
                            content.add(logInfo);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(br != null){
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return content;
    }
    private static boolean matchCondition(LogInfo logInfo,LogQueryVo vo){
        if(logInfo == null){
            return false;
        }
        if(vo == null){
            return true;
        }
        if(!StringUtils.isEmpty(vo.getSid()) && !vo.getSid().equals(logInfo.getSid())){
            return false;
        }
        if(!StringUtils.isEmpty(vo.getUserId()) && !vo.getUserId().equals(logInfo.getUserId())){
            return false;
        }
        if(vo.getBeginTime() != null && logInfo.getRecordTime() < vo.getBeginTime().getTime()){
            return false;
        }
        if(vo.getEndTime() != null && logInfo.getRecordTime() > vo.getEndTime().getTime()){
            return false;
        }
        if(vo.getConditions() != null && vo.getConditions().size() > 0){
            for(QueryConditionVo conditionVo : vo.getConditions()){
                switch (conditionVo.getCommand()){
                    case EQUAL_OPER:
                        if(!logInfo.getMsg().contains(conditionVo.getTargetValue())){
                            return false;
                        }
                        break;
                    case NOT_EQULA_OPER:
                        if(logInfo.getMsg().contains(conditionVo.getTargetValue())){
                            return false;
                        }
                        break;
                }
            }
        }
        return true;
    }
    private LogInfo getLogInfo(String lineMsg){
        if(StringUtils.isEmpty(lineMsg)){
            return null;
        }
        String[] params = lineMsg.split("\\|");
        //logId + "|" + recordTime +"|" + clientIp + "|" + userId + "|" + sid + "|"+msg
        String logId = params[0];
        long recordTime = params.length > 1 ? Long.parseLong(params[1]) : 0;
        String clientIp = params.length > 2 ? params[2] : "";
        String userId = params.length > 3 ? params[3] : "";
        String sid = params.length > 4 ? params[4] : "";
        String msg = getMsg(params);
        LogInfo result = new LogInfo();
        result.setLogId(logId);
        result.setRecordTime(recordTime);
        result.setRecordTimeString(DateUtil.formatTime(recordTime,"yyyy-MM-dd HH:mm:ss"));
        result.setClientIp(clientIp);
        result.setUserId(userId);
        result.setSid(sid);
        result.setMsg(msg);
        return result;
    }
    private String getMsg(String[] params){
        StringBuilder stringBuilder = new StringBuilder();
        if(params.length > 5){
            for(int i=5;i<params.length;i++){
                stringBuilder.append("|"+params[i]);
            }
        }
        return stringBuilder.toString().substring(1);
    }


    /**
     * 根据文件名，获取文件
     * @param vo
     * @return
     */
    public List<File> getFile(LogQueryVo vo){
        File directory = new File(path);
//        if(!directory.exists() && !directory.isDirectory()){
//            return null;
//        }
        List<File> result = new ArrayList<File>();
        File[] files = directory.listFiles();
        if(files != null){
            for(File file : files){
                if(choseTime(file.getName(),vo)){
                    result.add(file);
                }
            }
        }
        return result;
    }
    private boolean choseTime(String curFileName,LogQueryVo vo){
        if(!curFileName.contains(fileName)){
            return false;
        }
        if(curFileName.endsWith(".log")){
            return true;
        }
        if(vo != null && vo.getBeginTime() != null){
            String formatTime = DateUtil.dateToString(vo.getBeginTime(),"yyyy-MM-dd");
            if(curFileName.contains(formatTime)){
                return true;
            }
        }
        if(vo != null && vo.getEndTime() != null){
            String formatTime = DateUtil.dateToString(vo.getEndTime(),"yyyy-MM-dd");
            if(curFileName.contains(formatTime)){
                return true;
            }
        }
        return false;
    }

}
