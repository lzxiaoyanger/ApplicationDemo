package com.zz.personal.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public final static String EMAIL_REGEX = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
    public final static String YMD = "\\d{4}-\\d{2}-\\d{2}";

    /**
     *
     * @param str 要验证的字符串
     * @param regex 验证规则
     * @return
     */
    public static boolean checkRegex(String str, String regex) {
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        return matcher.matches();
    }

    public static boolean checkEmail(String email){
        return checkRegex(email,EMAIL_REGEX);
    }

    public static String replace(String regex,String replaceStr,final String original){
        String result = null;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(original);
        return matcher.replaceAll(replaceStr);
    }

    public static String getRegex(String regex, String str){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return null;
        }
    }
}
