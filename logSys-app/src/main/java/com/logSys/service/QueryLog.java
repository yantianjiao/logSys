package com.logSys.service;

import com.logSys.domain.LogInfo;
import com.logSys.vo.LogQueryVo;

import java.util.List;

/**
 * Created by ytj on 2017/12/20.
 */
public interface QueryLog {
    public List<LogInfo> queryLog(LogQueryVo vo);
}
