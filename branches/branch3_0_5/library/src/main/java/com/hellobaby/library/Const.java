package com.hellobaby.library;

import android.os.Environment;

import java.io.File;

/**
 * Created by zwj on 2016/12/29.
 */
public class Const {


//    public final static String baseUrl = "http://192.168.1.175:8080/";// 175  157
    public final static String baseUrl = "http://120.27.144.211:8899/";//外网
    /**
     * 1.baby头像存放路径为
     */
    public final static String URL_BabyHead = baseUrl + "static/images/babyHead/";
    /**
     * 2.家长相册图片存放路径
     */
    public final static String URL_Album = baseUrl + "static/images/indexCommonImg/";
    /**
     * 3.家长小视频第一帧存放路径
     */
    public final static String URL_VideoFirstFrame = baseUrl + "static/images/indexCommonImg/firstFrameImg/";
    /**
     * 4.家长小视频存放路径
     */
    public final static String URL_Video = baseUrl + "static/images/indexCommonImg/smallVideo/";
    /**
     * 5.teacher头像存放路径为
     */
    public final static String URL_TeacherHead = baseUrl + "static/images/teacherHead/";
    /**
     * 6.教学计划存放路径为
     */
    public final static String URL_teachingPlan = baseUrl + "static/images/teachingPlan/";
    /**
     * 7.动态图片存放路径：
     */
    public final static String URL_dynamicImgs = baseUrl + "static/images/dynamicImgs/";
    /**
     * 8.动态小视频第一帧存放路径：
     */
    public final static String URL_dynamicFirstFrame = baseUrl + "static/images/dynamicFirstFrame/";
    /**
     * 9.动态小视频存放路径：
     */
    public final static String URL_dynamicSmallVideo = baseUrl + "static/images/dynamicSmallVideo/";
    /**
     * 10.家长头像存放路径
     */
    public final static String URL_userHead = baseUrl + "static/images/userHead/";
    /**
     * 11.学校头像存放路径
     */
    public final static String URL_schoolHead = baseUrl + "static/images/schoolHead/";


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
    public final static String saveImagePath = Environment.getExternalStorageDirectory().getPath()+File.separator+"/HellobabySave";

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
}
