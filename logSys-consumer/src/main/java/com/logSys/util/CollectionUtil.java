package com.logSys.util;

import java.util.Collection;
import java.util.Map;

/**
 * Created by ytj on 2017/12/21.
 */
public class CollectionUtil {
    public static boolean isEmpty(Collection collection) {
        return (collection == null || collection.isEmpty());
    }
    public static boolean isEmpty(Map map) {
        return (map == null || map.isEmpty());
    }
}
