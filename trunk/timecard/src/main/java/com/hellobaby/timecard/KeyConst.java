package com.hellobaby.timecard;

import android.os.Environment;

import java.io.File;

/**
 * Created by zwj on 2017/3/14.
 * description :
 */

public class KeyConst {
    /**
     * 基础目录
     */
    public final static String basePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "hellobaby" + File.separator + "tcm" + File.separator;
    public final static String cameraSavePath = basePath + "camera" + File.separator;
    //用户名
    public static final String kUserName = "userName";
    //密码
    public static final String kPwd = "passWord";
    //是否为
    public static final String kIsAutoTime = "isAutoTime";
    public static final String kAutoTime = "autoTime";
    public static final String kTeacherGoWorkTime = "goWorkTime"; //教师上班时间
    public static final String kTeacherOffWorkTime = "offWorkTime"; // 教师下班时间
    public static final String kUUID = "kuuid";//考勤机机器码
    public static final int kUUIDRequest = 1;
    public static final int kUUIDResult = 2;

    /**
     * 考勤机下载地址
     */
    public final static String url_timeCard = "http://www.hellobaobei.com.cn/dl/timecard.xml";

}
