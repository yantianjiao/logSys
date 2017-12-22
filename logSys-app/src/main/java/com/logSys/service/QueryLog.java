package com.logSys.service;

import com.logSys.domain.LogReaderDomain;
import com.logSys.vo.LogQueryVo;

/**
 * Created by ytj on 2017/12/20.
 */
public interface QueryLog {
    public LogReaderDomain queryLog(LogQueryVo vo);
}
