package com.logSys.service.impl;

import com.logSys.domain.LogInfo;
import com.logSys.service.FileService;
import com.logSys.service.QueryLog;
import com.logSys.vo.LogQueryVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 文件读取日志，并按时间排序
 * Created by ytj on 2017/12/20.
 */
@Service("queryLog")
public class FileQueryLogImpl implements QueryLog {
    @Resource
    private FileService fileService;

    @Override
    public List<LogInfo> queryLog(LogQueryVo vo) {
        List<File> files = fileService.getFile(vo);
        try {
            List<LogInfo> list = sort(fileService.readFiles(files,vo));
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 排序
     * @param list
     * @return
     */

    private static List<LogInfo> sort(List<LogInfo> list){
        if(list == null){
            return null;
        }
        list.sort(new Comparator<LogInfo>() {
            @Override
            public int compare(LogInfo o1, LogInfo o2) {
                long o1RecordTime = o1.getRecordTime();
                long o2RecordTime = o2.getRecordTime();
                return (int) (o1RecordTime - o2RecordTime);
            }
        });
        return list;
    }

    public static void main(String[] args) throws  Exception{
        long s1 = System.currentTimeMillis();
        Thread.sleep(1);
        long s2 = System.currentTimeMillis();
        Thread.sleep(1);
        long s3 = System.currentTimeMillis();
        Thread.sleep(1);
        long s4 = System.currentTimeMillis();
        Thread.sleep(1);
        List<LogInfo> list = new ArrayList<LogInfo>();
        LogInfo logInfo = new LogInfo();
        logInfo.setSid("1");
        logInfo.setRecordTime(s1);
        list.add(logInfo);

        LogInfo logInfo1 = new LogInfo();
        logInfo1.setSid("3");
        logInfo1.setRecordTime(s3);
        list.add(logInfo1);
        LogInfo logInfo2 = new LogInfo();
        logInfo2.setSid("2");
        logInfo2.setRecordTime(s2);

        list.add(logInfo2);
        LogInfo logInfo3 = new LogInfo();
        logInfo3.setSid("4");
        logInfo3.setRecordTime(s4);
        list.add(logInfo3);
        System.out.println(list);
        sort(list);
        System.out.println(list);
    }
}
