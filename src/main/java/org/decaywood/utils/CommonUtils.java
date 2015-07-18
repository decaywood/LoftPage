package org.decaywood.utils;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by decaywood on 2015/5/24.
 */
public class CommonUtils {

    private static Logger LOGGER = Logger.getLogger(CommonUtils.class);

    public static String readFile(String fileReletivePath) {
        String line = null;
        try {
            String path = ValidateCode.class.getClassLoader().getResource("").getPath() + "../../" + fileReletivePath;
            InputStream inputStream = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            line = reader.readLine(); // 读取第一行
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return line;
    }


    public static String generateUUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

    public static long generateHashCode(String... seeds) {

        return Arrays.stream(seeds).map(x -> (long)x.hashCode())
                .reduce(0L, (first, second) -> first * 31 + second);

    }




    public static boolean isEmpty(String s){
        return s == null || "".equals(s) || "null".equals(s);
    }


}
