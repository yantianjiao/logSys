/**
 * 文件名：PacketType.java
 * 创建日期：  2016年8月9日
 * 作者：      lizhangxiong
 * 版权所有(C) 2016-2017 深圳市华康全景信息技术有限公司
 * 保留所有权利.
 */
package com.logSys.util;

/**
 * 功能描述：HTTP 请求报文类型
 * Created by ytj on 2017/12/21.
 */
public enum ContentTypeEnum {

    JSON("application/json;charset=UTF-8"),
    XML("text/xml;charset=UTF-8"),
    FORM("application/x-www-form-urlencoded;charset=UTF-8");

    private String value;

    ContentTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
