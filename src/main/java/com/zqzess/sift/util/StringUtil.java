package com.zqzess.sift.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/01 15:02
 * @Package com.zqzess.sift.util
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
public class StringUtil {
    public static boolean isNotEmpty(String var0) {
        return var0 != null && var0.length() != 0;
    }

    /**
     * 根据指定分隔符切割字符串，并且移除字符串收尾空白
     * @param var0
     * @param seprator
     * @return
     */
    public static String[] splitAndTrim(String var0, String seprator) {
        String[] strArr = StringUtils.split(var0, seprator);
        if (strArr == null) {
            return new String[]{};
        }
        String[] resArr = new String[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            resArr[i] = StringUtils.trim(strArr[i]);
        }
        return resArr;
    }

    /**
     * 校验url格式，支持一个或多个url
     * 10.22.132.67:27001;10.22.132.68:27001
     * @param url
     * @return
     */
    public static boolean validateUrl(String url) {
        String regex = "^(?:\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}:\\d{1,5}(?:;|$))+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
    public static boolean validateIp(String ip) {
        String regex = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    /**
     *
     * @param input 需要切分的字符串
     * @return
     */
    public static Map<String, String> parseKeyValueString(String input) {
        return parseKeyValueString(input, ",|=");
    }

    /**
     *
     * @param input
     * @param splitComma 分隔符 默认使用  ,|=
     * @return
     */
    public static Map<String, String> parseKeyValueString(String input, String splitComma) {
        String[] splitInput = input.split(splitComma);
        Map<String, String> result = new HashMap<>();

        for (int i = 0; i < splitInput.length; i += 2) {
            String key = splitInput[i];
            String value = splitInput[i + 1];
            result.put(key, value);
        }
        return result;
    }

    public static String valueOf(String resultVal) {
        return valueOf(resultVal, "");
    }

    public static String valueOf(String resultVal, String defaultStr) {
        if (StringUtil.isNotEmpty(resultVal)) {
            return resultVal;
        }
        return defaultStr;
    }

}
