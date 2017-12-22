package com.logSys.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ytj on 2017/12/20.
 */
public class DateUtil {
    /**
     * 时间戳格式化成String
     * @param time
     * @param strFormat
     * @return
     */
    public final static String formatTime(long time,String strFormat){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strFormat);
        return simpleDateFormat.format(new Date(time));
    }
    /**
     * string2Date:(日期字符转Date). <br/>
     *
     * @param dateString
     * @return java.util.Date
     * @since V1.0.0
     */
    public final static Date stringToDate(String dateString) {
        if (dateString == null || dateString.trim().length() == 0) {
            return new Date(0);
        }

        try {
            String strFormat = "";
            switch (dateString.length()) {
                case 6: // yymmdd
                    strFormat = "yyMMdd";
                    break;
                case 8: // yyyymmdd
                    strFormat = "yyyyMMdd";
                    break;
                case 10: // yyyy-mm-dd
                    strFormat = "yyyy-MM-dd";
                    break;
                case 14:
                    strFormat = "yyyyMMddHHmmss";
                    break;
                case 16:
                    strFormat = "yyyy-MM-dd HH:mm";
                    break;
                default:
                    strFormat = "yyyy-MM-dd HH:mm:ss";
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strFormat);
            Date timeDate = simpleDateFormat.parse(dateString);
            return timeDate;
        } catch (Exception e) {
            return new Date(0);
        }
    }
}
