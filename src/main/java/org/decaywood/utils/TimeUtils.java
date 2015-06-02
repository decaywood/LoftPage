package org.decaywood.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: decaywood
 * @date: 2015/5/27 15:00
 */
public class TimeUtils {

    private final static SimpleDateFormat TOTAL_TIME_FORMAT = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");

    public static String getTime() {
        return TOTAL_TIME_FORMAT.format(new Date());
    }

}
