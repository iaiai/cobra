package com.iaiai.cobra.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/1.
 */
public class StringUtil {
    private static final String[] UNSAFE_LESS = { "set-cookie", "script", "<", "%3c", "%3e", ">", "\\" };

    public static String getString(String str,String def){
        return StringUtils.isEmpty(str)?def:str;
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean isEmail(String email) {
        Pattern p = Pattern.compile("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*$");
        Matcher m = p.matcher(email);
        return m.find();
    }

    public static boolean isPhoneNumber(String phoneNum) {
        Pattern p = Pattern.compile("^[1][3456789][0-9]{9}$");
        Matcher m = p.matcher(phoneNum);
        return m.find();
    }

    public static boolean isNumber(String numStr) {
        Pattern p = Pattern.compile("^\\d+?$");
        Matcher m = p.matcher(numStr);
        return m.find();
    }

    public static String hidePhoneNumber(String phoneNum) {
        return phoneNum.substring(0, 4) + "****" + phoneNum.substring(8);
    }

    public static boolean isSafe(String str) {
        if (isNotEmpty(str)) {
            for (String s : UNSAFE_LESS) {
                if (str.toLowerCase().contains(s)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 这个方法返回uuid带中横线
     *
     * @return
     */
    public final static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 返回32位去掉横线的uuid
     *
     * @return
     */
    public final static String get32UUID() {
        return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }

    /**
     * 生成随机数,不包含字母
     *
     * @param length 生成几位的随机数
     * @return
     */
    public final static String randomNumber(int length) {
        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length) - 1;
        return String.valueOf(new Random().nextInt(max) % (max - min + 1) + min);
    }

}
