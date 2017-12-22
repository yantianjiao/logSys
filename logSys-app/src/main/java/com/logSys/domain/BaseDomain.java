package com.logSys.domain;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by ytj on 2017/12/20.
 */
public class BaseDomain implements Serializable {
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
