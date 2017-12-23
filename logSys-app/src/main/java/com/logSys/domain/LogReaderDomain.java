package com.logSys.domain;

import java.util.List;

/**
 * Created by ytj on 2017/12/19.
 */
public class LogReaderDomain extends BaseDomain {

    private List<LogInfo> content;
    private boolean finished;

    public List<LogInfo> getContent() {
        return content;
    }

    public void setContent(List<LogInfo> content) {
        this.content = content;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public static void main(String[] args) {
        LogReaderDomain vo = new LogReaderDomain();
        vo.setFinished(true);
        System.out.println(vo);
    }
}
