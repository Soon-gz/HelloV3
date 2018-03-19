package com.hellobaby.library;

import android.os.Environment;

import java.io.File;

/**
 * Created by zwj on 2016/12/29.
 */
public class Const {

//    public final static String baseUrl = "http://192.168.1.198:8080/";// 175  138  130 71
    public final static String baseUrl = "http://120.27.144.211:8899/";//外网
    /**
     * 0.新闻链接路径
     */
    public final static String URL_news= "";

    public final static String baseImgUrl = "http://hellobaobei.oss-cn-shanghai.aliyuncs.com/";//oss
    /**
     * 1.baby头像存放路径为N
     */
    public final static String URL_BabyHead = baseImgUrl + "static/images/babyHead/";
    /**
     * 2.家长相册图片存放路径
     */
    public final static String URL_Album = baseImgUrl + "static/images/indexCommonImg/";
    /**
     * 3.家长小视频第一帧存放路径
     */
    public final static String URL_VideoFirstFrame = baseImgUrl + "static/images/indexCommonImg/firstFrameImg/";
    /**
     * 4.家长小视频存放路径
     */
    public final static String URL_Video = baseImgUrl + "static/images/indexCommonImg/smallVideo/";
    /**
     * 5.teacher头像存放路径为
     */
    public final static String URL_TeacherHead = baseImgUrl + "static/images/teacherHead/";
    /**
     * 6.教学计划存放路径为
     */
    public final static String URL_teachingPlan = baseImgUrl + "static/images/teachingPlan/";
    /**
     * 7.动态图片存放路径：
     */
    public final static String URL_dynamicImgs = baseImgUrl + "static/images/dynamicImgs/";
    /**
     * 8.动态小视频第一帧存放路径：
     */
    public final static String URL_dynamicFirstFrame = baseImgUrl + "static/images/dynamicFirstFrame/";
    /**
     * 9.动态小视频存放路径：
     */
    public final static String URL_dynamicSmallVideo = baseImgUrl + "static/images/dynamicSmallVideo/";
    /**
     * 10.家长头像存放路径
     */
    public final static String URL_userHead = baseImgUrl + "static/images/userHead/";
    /**
     * 11.学校头像存放路径
     */
    public final static String URL_schoolHead = baseImgUrl + "static/images/schoolHead/";
    /**
     * 12.接送卡像存放路径
     */
    public final static String URL_pickHead = baseImgUrl + "static/images/pickHead/";

    /**
     * 12.接送拍摄照片存放路径
     */
    public final static String URL_timeCardImg= baseImgUrl + "static/images/timeCardImg/";

    /**
     * 13.兑换奖品的图片地址
     */
    public final static String URL_prizeDrawImg = baseImgUrl + "static/images/recordImg/";
    /**
     * 14.咨询图片地址
     */
    public final static String URL_newsInfoImge = baseImgUrl + "static/images/newsInfoImge/";
    /**
     * 15.课堂助手封面图片
     */
    public final static String URL_ClassRoom = baseImgUrl + "static/images/classroom/";

    /**
     * 基础目录
     */
    public final static String basePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "hellobaby" + File.separator;
    /**
     * 下载app目录
     */
    public final static String apkDownPath = basePath + "down" + File.separator;
    /**
     * 视频目录
     */
    public final static String videoPath = basePath + "video" + File.separator;
    /**
     * 保存到本地目录
     */
    public final static String saveImagePath = Environment.getExternalStorageDirectory().getPath()+File.separator+"HellobabySave";

    public final static String imageCache = basePath + "imageCache" + File.separator;
    //拍摄存储路径
    public final static String imageCamera = basePath + "imageCamera" + File.separator;

    /**
     * 手机号的key
     */
    public final static String keyPhoneNum = "PHONENUM";
    /**
     * 密码
     */
    public final static String keyPwd = "PASSWORD";


    /**
     * 家长端版本下载地址
     */
    public final static String url_baby = "http://www.hellobaobei.com.cn/dl/HelloBabyV3.xml";
    /**
     * 老师端版本下载地址
     */
    public final static String url_teacher = "http://www.hellobaobei.com.cn/dl/teacherV3.xml";


    public static final int CAMERA_ORIENTATION = 90;
    public static final int FRONT_CAMERA_ROTATION = 270;
    public static final int BACK_CAMERA_ROTATION = 90;
    public static final boolean FLAG_DECODE_BITMAP = true;
    public static final boolean FLAG_SAVE_IMAGE = true;
    //bugly AppId
    public static final String BUGLY_APPID_BABY = "4c9617f555";
    public static final String BUGLY_APPID_TEACHER = "c8ba82fa19";
    public static final String BUGLY_APPID_TIMECARD = "8f17569995";
    public static final String CLIENT_USER = "1";
    public static final String CLIENT_TEACHER = "0";
    public static final String AGREELIST = "AGREELIST";
    public static final String FROMWHERE = "FROMWHERE";
    public static final String BABY_CARD_RELATION = "BABY_CARD_RELATION";
    public static final String BABY_CARD_QRCODE = "BABY_CARD_QRCODE";
    //校长新功能  可选择班级查看动态
    public static final Object SELECT_SCHOOL = "selectSchool";
    public static final Object SELECT_OTHER_PAGES = "selectOtherPages";


    public static int CAMERA_PREVIEW_WIDTH = 0;
    public static int CAMERA_PREVIEW_HEIGHT = 0;

    public static int CAMERA_PICTURE_WIDTH = 0;
    public static int CAMERA_PICTURE_HEIGHT = 0;

    public static boolean BACK_CAMERA_IN_USE = true;

    //for saving the image
    public static final String DIRECTORY_NAME = "hellobaby";
    public static final String DATE_PATTERN = "yyyyMMdd_HHmmss";
    public static final String IMAGE_POSTFIX = "IMG_";
    public static final String IMAGE_EXTENSION = ".jpg";
    public static final String VIDEO_POSTFIX = "VID_";
    public static final String VIDEO_EXTENSION = ".mp4";
    public static final String IMAGE_SAVE_SUCCESS_MESSAGE = "Image saved successfully";
    public static final String IMAGE_SAVE_FAILURE_MESSAGE = "Failed to save image";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final String FAIL = "FAILED";
    public static final int NORMAL_ACTIVITY_RESULT = 1008;
    //校园类型
    public static final String SCHOOL_ALL = "0";
    public static final String SCHOOL_COOKBOOK = "30";
    public static final String SCHOOL_EVENT = "40";
    public static final String SCHOOL_DYNAMIC = "20";
    public static final String SCHOOL_NEWS = "10";

    //通知弹出提示所需数据
    public static String careBabyAlert = "careBabyAlert";
    public static String careBabyUserId = "careBabyUserId";
    public static String careBabyBabyId = "careBabyBabyId";

    //延迟
    public static final int THREAD_SLEEPTIME = 100;

    //延迟
    public static final int BADGEVIEW_INVISABLE = 101;

    //延迟箭头旋转
    public static final int ROAT_TIME = 250;

    //积分相关变量
    public static final String BABY_COMMENSION = "1";
    public static final String SCHOOL_PRAMSE = "2";
    public static final String SHARE_APP = "3";


    /**
     * Andfix热更新起始版本
     * 1、在登录的时候，通过友盟的在线参数来查看是否有当前版本的修复包
     * 2、如果需要及时更新，就使用极光推送，来实现在线下载修复包
     */
    public static final String ANDFIX_START_VERSION = "3.4.2";
    public static final String ANDFIX_UMENG_ONLINE_DATA = "andfixdata";
    public static final String ANDFIX_FILE_PATH = basePath+"andfixBaby"+File.separator;
    public static final String ANDFIX_FILE_PATH_TEACHER = basePath+"andfixTeacher"+File.separator;

    public static String getBabyAndfixURL(String url,String app_v,String path_v){
        return url+app_v+"_"+path_v+"_baby.apatch";
    }

    public static String getTeacherAndfixURL(String url,String app_v,String path_v){
        return url+app_v+"_"+path_v+"_teacher.apatch";
    }

    public static String getBabyAndfixFileName(String app_v,String path_v){
        return app_v+"_"+path_v+"_baby.apatch";
    }

    public static String getTeacherAndfixFileName(String app_v,String path_v){
        return app_v+"_"+path_v+"_teacher.apatch";
    }

}
