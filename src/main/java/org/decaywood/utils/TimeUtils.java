package org.decaywood.utils;

import org.joda.time.DateTime;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * @author: decaywood
 * @date: 2015/5/27 15:00
 */
public class TimeUtils {

    private final static SimpleDateFormat TOTAL_TIME_FORMAT = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");

    public static Date getSqlTime() {
        return new Date(new DateTime().getMillis());
    }

}
