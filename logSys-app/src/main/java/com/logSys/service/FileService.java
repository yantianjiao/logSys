package com.logSys.service;

import com.logSys.domain.LogInfo;
import com.logSys.vo.LogQueryVo;

import java.io.File;
import java.util.List;

/**
 * Created by ytj on 2017/12/23.
 */
public interface FileService {
    public  List<LogInfo> readFiles(List<File> files, LogQueryVo vo);
    public List<File> getFile(LogQueryVo vo);
}
