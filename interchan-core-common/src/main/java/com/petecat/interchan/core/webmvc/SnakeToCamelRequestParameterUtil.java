package com.petecat.interchan.core.webmvc;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: SnakeToCamelRequestParameterUtil
 * @Description:下划线转驼峰的参数工具类
 * @author: mhuang
 * @date: 2018年4月27日 下午2:58:32
 */
public class SnakeToCamelRequestParameterUtil {

    public static String convertSnakeToCamel(String snake) {

        if (snake == null) {
            return null;
        }

        if (snake.indexOf("_") < 0) {
            return snake;
        }

        StringBuilder result = new StringBuilder();

        String[] split = StringUtils.split(snake, "_");
        int index = 0;
        for (String s : split) {
            if (index == 0) {
                result.append(s.toLowerCase());
            } else {
                result.append(capitalize(s));
            }
            index++;
        }

        return result.toString();
    }

    private static String capitalize(String s) {

        if (s == null) {
            return null;
        }

        if (s.length() == 1) {
            return s.toUpperCase();
        }

        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
