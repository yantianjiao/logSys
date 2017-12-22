package com.logSys.domain;

import java.util.List;

/**
 * Created by ytj on 2017/12/19.
 */
public class LogReaderDomain extends BaseDomain {

    private List<String> content;
    private boolean finished;

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
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
