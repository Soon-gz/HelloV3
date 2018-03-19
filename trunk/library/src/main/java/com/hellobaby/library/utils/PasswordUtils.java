package com.hellobaby.library.utils;

/**
 * Created by Administrator on 2016/9/23.
 */


import android.content.Context;

import com.hellobaby.library.R;

import java.util.regex.Pattern;

/**
 * Created by zwj on 16/9/23.
 * 密码验证工具类
 */
public class PasswordUtils {

    /**
     * 验证手机号
     * @param phone
     * @return
     */
    public static String isPhoneNum(String phone){
        if(!StringUtils.isPhoneNum(phone)){
            return "请输入正确的手机号";
        }
        return null;
    }

    /**
     * 登录界面的登录验证
     * @param userName
     * @param pwd
     * @return
     */
    public static String isLoginAvailable(String userName, String pwd) {

        String isUserNameAvailable = isUserNameAvailable(userName);
        if (isUserNameAvailable != null) {
            return isUserNameAvailable;
        }

        String isPwdEmpty = isPwdEmpty(pwd);
        if (isPwdEmpty != null) {
            return isPwdEmpty;
        }
        String isPwdLengthRange = isPwdLengthRange(pwd);
        if (isPwdLengthRange != null) {
            return isPwdLengthRange;
        }


        return null;
    }


    /**
     * 密码必须是8-16位的数字、字母
     *
     * @param pwd
     * @return
     */
    public static String isPwdLengthRange(String pwd) {
        if(pwd.length()==32){
            //验证密码
            return null;
        }
        if (pwd.length() < 6 || pwd.length() > 16) {
            return "密码必须是8-16位的数字、字母";
        }
        return null;
    }

    /**
     * 请输入用户名
     *
     * @param userName
     * @return
     */
    public static String isUserNameAvailable(String userName) {
        if (userName == null || userName.isEmpty()) {
            return "请输入用户名";
        }else if(userName.length()!=11){
            return "请输入11位手机号";
        }
        return null;
    }

    /**
     * 输入密码不能为空
     *
     * @param pwd
     * @return
     */
    public static String isPwdEmpty(String pwd) {
        if (pwd == null || pwd.isEmpty()) {
            return "请输入密码";
        }
        return null;
    }

    /**
     * 判断密码是否过于简单
     * 1.判断数字或者字母为连续重复的，比如8个1或者8个a这样的
     * 2.判断数字或者字母为有序的输入，比如12345678或者abcdefgh
     *
     * @param context
     * @param pwd     密码
     * @return true:密码可用;false:密码不可用
     */
    public static String isPwdEasy(Context context, String pwd) {
        String pwdEmpty = isPwdEmpty(pwd);
        if (pwdEmpty != null) {
            return pwdEmpty;
        }
        String regular = context.getResources().getString(R.string.pwdutils_repeat_regular, String.valueOf(pwd.length() - 1));
        if (Pattern.matches(regular, pwd)) {
            //匹配重复字符
            return context.getResources().getString(R.string.pwdutils_repeat_regular);
        }
        if (isPasswordContinuous(pwd)) {
            //正序 || 反续
            return context.getResources().getString(R.string.pwdutils_repeat_regular);
        }
        return null;
    }


    /**
     * 密码是否是正序或反序连续
     *
     * @param pwd
     * @return true为连续，false为不连续。
     */
    private static boolean isPasswordContinuous(String pwd) {
        int count = 0;// 正序次数
        int reverseCount = 0;// 反序次数
        final int pwdLength = pwd.length();
        final int maxCount = pwdLength - 2;
        String[] strArr = pwd.split("");
        // 从1开始是因为划分数组时，第一个为空
        for (int i = 1; i < pwdLength - 1; i++) {
            if (isPositiveContinuous(strArr[i], strArr[i + 1])) {
                count++;
            } else {
                count = 0;
            }
            if (isReverseContinuous(strArr[i], strArr[i + 1])) {
                reverseCount++;
            } else {
                reverseCount = 0;
            }
            if (count > maxCount || reverseCount > maxCount)
                break;
        }
        if (count >= maxCount || reverseCount >= maxCount)
            return true;
        return false;
    }

    /**
     * 是否是正序连续
     *
     * @param str1
     * @param str2
     * @return
     */
    private static boolean isPositiveContinuous(String str1, String str2) {
        if (str2.hashCode() - str1.hashCode() == 1)
            return true;
        return false;
    }

    /**
     * 是否是反序连续
     *
     * @param str1
     * @param str2
     * @return
     */
    private static boolean isReverseContinuous(String str1, String str2) {
        if (str2.hashCode() - str1.hashCode() == -1)
            return true;
        return false;
    }
}