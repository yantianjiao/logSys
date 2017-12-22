/**
 * 文件名：GsonUtil.java
 * 创建日期：  2016年7月25日
 * 作者：      lizhangxiong
 * 版权所有(C) 2016-2017 深圳市华康全景信息技术有限公司
 * 保留所有权利.
 */
package com.logSys.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 功能描述： 谷歌gson组件操作封装
 * Created by ytj on 2017/9/15.
 */
public class GsonUtil {
    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private GsonUtil() {
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        String json = null;
        if (object == null) {
            return json;
        }
        if (gson != null) {
            json = gson.toJson(object);
        }
        return json;
    }

    /**
     * 转成bean
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T toBean(JsonElement json, Class<T> cls) {
        T t = null;
        if (json == null) {
            return t;
        }
        if (gson != null) {
            t = gson.fromJson(json, cls);
        }
        return t;
    }
    /**
     * 转成bean
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T toBean(String json, Class<T> cls) {
        T t = null;
        if (json == null) {
            return t;
        }
        if (gson != null) {
            t = gson.fromJson(json, cls);
        }
        return t;
    }

    /**
     * 转成bean
     *
     * @param json
     * @param typeOfT
     * @return
     */
    public static <T> T toBean(String json, Type typeOfT) {
        T t = null;
        if (json == null || typeOfT == null) {
            return t;
        }
        if (gson != null) {
            t = gson.fromJson(json, typeOfT);
        }
        return t;
    }

    /**
     * 转成list
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> List<T> toList(String json, Class<T> cls) {
        List<T> list = null;
        if (json == null || cls == null) {
            return list;
        }
        if (gson != null) {
            list = gson.fromJson(json, new TypeToken<List<T>>() {
            }.getType());
        }

        return list;
    }

    /**
     * JsonElement 转换成list.map
     * @param json
     * @param <T>
     * @return
     */
    public static <T> List<Map<String, T>> fromJson(JsonElement json,Class<T> cls){
        List<Map<String, T>> list = null;
         list = gson.fromJson(json, new TypeToken<List<Map<String, T>>>() {
        }.getType());
        return list;
    }

    /**
     * 转成list中有map的
     *
     * @param json
     * @return
     */
    public static <T> List<Map<String, T>> toListMaps(String json) {
        List<Map<String, T>> list = null;
        if (json == null) {
            return list;
        }
        if (gson != null) {
            list = gson.fromJson(json, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        }
        return list;
    }
    /**
     * 转成map
     *
     * @param json
     * @return
     */
    public static <T> Map<String, T> toMaps(JsonElement json,Class<T> cls) {
        Map<String, T> map = null;
        if (json == null) {
            return map;
        }
        if (gson != null) {
            map = gson.fromJson(json, new TypeToken<LinkedHashMap<String, Object>>() {
            }.getType());
        }
        return map;
    }

    /**
     * 转成map的
     *
     * @param json
     * @return
     */
    public static <T> Map<String, T> toMaps(String json) {
        Map<String, T> map = null;
        if (json == null) {
            return map;
        }
        if (gson != null) {
            map = gson.fromJson(json, new TypeToken<TreeMap<String, Object>>() {
            }.getType());
        }
        return map;
    }
}
