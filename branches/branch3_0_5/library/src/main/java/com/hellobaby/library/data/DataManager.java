package com.hellobaby.library.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.ClassModel;
import com.hellobaby.library.data.model.ReviewModel;
import com.hellobaby.library.data.model.SchoolModel;
import com.hellobaby.library.data.model.TeacherModel;
import com.hellobaby.library.data.model.TeachingPlanModel;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.data.remote.APIService;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zwj on 2016/9/27.
 * description :
 */
@Singleton
public class DataManager {
    private final APIService mAPIService;
    //    public static final String token = "e72a44611e94f6939171fcd423998eeaa8c4d3e4";
//    public static final String token = "1321354654";
    public static String token = "e72a44611e94f6939171fcd423998eeaa8c4d3e8";
    /**
     * 图片上传类型
     */
    private final String mediaTypeImageJpeg = "image/jpeg";
    /**
     * 文件上传类型
     */
    private final String mediaTypemultipart = "multipart/form-data";

    @Inject
    public DataManager(APIService apiService) {
        mAPIService = apiService;
    }

    public Observable<String> getLogin() {
        return Observable.just("123");
    }

    /**
     * 用户登录接口
     *
     * @param phone
     * @param pwd
     * @return
     */
    public Observable<BaseModel<String>> checkLoginUser(String phone, String pwd) {
        return mAPIService.checkLoginUser(phone, pwd);
    }

    /**
     * 1.4 家长端获取短信验证码
     *
     * @param phone
     * @return
     */
    public Observable<BaseModel<String>> checkUserExits(String phone) {
        return mAPIService.checkUserExits(phone);
    }

    /**
     * 1.6 接口功能：家长端（忘记密码 & 修改密码）获取短信验证码
     *
     * @param phone
     * @return
     */
    public Observable<BaseModel<String>> checkPhoneExists(String phone) {
        return mAPIService.checkPhoneExists(phone);
    }

    /**
     * 1.5 接口功能：家长端 忘记&修改
     *
     * @param phone
     * @param pwd
     * @return
     */
    public Observable<BaseModel<String>> smsCodeBasedPasswordChange(String phone, String pwd, String smsCode) {
        return mAPIService.smsCodeBasedPasswordChange(phone, pwd, smsCode);
    }

    /**
     * 9.6 接口功能：教师端（忘记密码 & 修改密码）获取短信验证码
     *
     * @param phone
     * @return
     */
    public Observable<BaseModel<String>> teacherCheckPhoneExists(String phone) {
        return mAPIService.teacherCheckPhoneExists(phone);
    }

    /**
     * 9.5 接口功能：教师端 忘记&修改
     *
     * @param phone
     * @param pwd
     * @return
     */
    public Observable<BaseModel<String>> teacherChangeTeacherPassword(String phone, String pwd, String smsCode) {
        return mAPIService.teacherChangeTeacherPassword(phone, pwd, smsCode);
    }

    /**
     * 1.2 接口功能：老师登入
     *
     * @param phone
     * @param pwd
     * @return
     */
    public Observable<BaseModel<String>> checkTeacherLogin(String phone, String pwd) {
        return mAPIService.checkTeacherLogin(phone, pwd);
    }

    /**
     * 1.13 接口功能：教师端根据Token找到teacherId
     *
     * @return
     */
    public Observable<BaseModel<String>> checkTeacherToken() {
        return mAPIService.checkTeacherToken(token);
    }

    /**
     * 1.9 接口功能：家长端关注宝宝
     *
     * @param userId 当前用户的userId
     * @param babyId 被关注的宝宝id
     * @return
     */
    public Observable<BaseModel<String>> insertCareBaby(String userId, String babyId) {
        return mAPIService.insertCareBaby(token, userId, babyId);
    }

    /**
     * 1.3 接口功能：家长端注册
     *
     * @param phone
     * @param pwd
     * @return
     */
    public Observable<BaseModel<String>> subRegister(String phone, String pwd, String smsCode) {
        return mAPIService.subRegister(phone, pwd, smsCode);
    }

    /**
     * 根据token获取用户的id
     *
     * @return
     */
    public Observable<BaseModel<String>> checkToken() {
        return mAPIService.checkToken(token);
    }


    /**
     * 2.1 接口功能：家长宝贝列表
     *
     * @return
     */
    public Observable<BaseModel<List<BabyModel>>> selectTUserbabyById(String userId) {
        return mAPIService.selectTUserbabyById(token, userId);
    }

    /**
     * 2.6 创建相册
     */
    public Observable<BaseModel<AlbumModel>> createAlbum(String userId, String babyId, String title, String content) {
        return mAPIService.albumCreate(token, userId, babyId, title, content);
    }

    /**
     * 2.5 上传照片到相册
     *
     * @param commonId 相册id
     * @param path
     * @return
     */
    public Observable<BaseModel<AlbumModel>> uploadAlbumImg(String commonId, String path) {
        RequestBody requestToken = RequestBody.create(null, token);
        RequestBody requestCommonId = RequestBody.create(null, commonId);
        RequestBody requestBody = RequestBody.create(MediaType.parse(mediaTypeImageJpeg), new File(path));
        return mAPIService.albumUploadImg(requestToken, requestCommonId, requestBody);
    }

    /**
     * 2.3 家长端，取相册图片列表
     *
     * @param commonId
     * @return
     */
    public Observable<BaseModel<AlbumModel>> albumGetImgs(String commonId) {
        return mAPIService.albumGetImgs(token, commonId);
    }

    /**
     * 2.4 接口功能：家长端，相册删除某张图片(单张&批量)
     *
     * @param imageIds   图片id的数组 1,2,3 逗号区分
     * @param imageNames 图片名字的数组 1.jpg,2.jpg,3.jpg
     * @return
     */
    public Observable<BaseModel<String>> albumDelImgs(String imageIds, String imageNames) {
        return mAPIService.albumDelImgs(token, imageIds, imageNames);
    }

    /**
     * 2.22接口功能：日志\相册内容修改
     *
     * @param commonId
     * @param title
     * @param content
     * @return
     */
    public Observable<BaseModel<AlbumModel>> albumUpdateContent(String commonId, String title, String content) {
        return mAPIService.albumUpdateContent(token, commonId, title, content);
    }

    /**
     * 2.7 接口功能：家长端，日志新建
     *
     * @param title
     * @param content
     * @return
     */
    public Observable<BaseModel<AlbumModel>> logCreate(String userId,
                                                       String babyId,
                                                       String title,
                                                       String content) {
        return mAPIService.logCreate(token, userId, babyId, title, content);
    }

    /**
     * 2.8接口功能：家长端，日志删除 同2.11
     *
     * @param commonId
     * @return
     */
    public Observable<BaseModel<AlbumModel>> logDel(String commonId) {
        return mAPIService.logDel(token, commonId);
    }


    /**
     * 1.8 接口功能：家长端修改宝宝头像
     *
     * @param babyId 宝宝的id
     * @param path
     * @return
     */
    public Observable<BaseModel<BabyModel>> babyUploadHeadImg(String babyId, String path) {
        RequestBody requestToken = RequestBody.create(null, token);
        RequestBody requestBabyId = RequestBody.create(null, babyId);
        RequestBody requestFile = RequestBody.create(MediaType.parse(mediaTypeImageJpeg), new File(path));
        return mAPIService.babyUploadHeadImg(requestToken, requestBabyId, requestFile);
    }

    /**
     * 2.9 接口功能：家长端，小视频新建-第一帧图片先上传
     *
     * @param userId
     * @param babyId
     * @param content
     * @param path
     * @return
     */
    public Observable<BaseModel<AlbumModel>> videoFirstFrameImg(String userId, String babyId, String content, String path) {
        RequestBody requestToken = RequestBody.create(null, token);
        RequestBody requestUserId = RequestBody.create(null, userId);
        RequestBody requestBabyId = RequestBody.create(null, babyId);
        RequestBody requestContent = RequestBody.create(null, content);
        RequestBody requestFile = RequestBody.create(MediaType.parse(mediaTypeImageJpeg), new File(path));
        return mAPIService.videoFirstFrameImg(requestToken, requestUserId, requestBabyId, requestContent, requestFile);
    }

    /**
     * 2.10接口功能：家长端，小视频新建-上传小视频
     *
     * @param videoId
     * @param videoName
     * @param path
     * @return
     */
    public Observable<BaseModel<AlbumModel>> videoUpload(String videoId, String videoName, String path) {
        RequestBody requestToken = RequestBody.create(null, token);
        RequestBody requestVideoId = RequestBody.create(null, videoId);
        RequestBody requestVideoName = RequestBody.create(null, videoName);
        RequestBody requestFile = RequestBody.create(MediaType.parse(mediaTypeImageJpeg), new File(path));
        return mAPIService.videoUpload(requestToken, requestVideoId, requestVideoName, requestFile);
    }

    /**
     * 2.11接口功能：家长端，小视频删除(ui:考虑不用刷新首页)  两个接口做成公共接口
     *
     * @param commonId
     * @return
     */
    public Observable<BaseModel<AlbumModel>> videoDel(String commonId) {
        return mAPIService.videoDel(token, commonId);
    }

    /**
     * 2.30 接口功能：家长端，相册删除
     *
     * @param commonId
     * @return
     */
    public Observable<BaseModel<String>> deleteAlbumById(String commonId) {
        return mAPIService.deleteAlbumById(token, commonId);
    }

    /**
     * 修改宝宝信息
     */
    public Observable<BaseModel<BabyModel>> babayUpdateInfo(String baby) {
        return mAPIService.babyUpdateInfo(token, baby);
    }

    /**
     * 5.6 接口功能：家长端，修改家长信息
     */
    public Observable<BaseModel<UserModel>> updateUserInfo(String babyId, String userId, String userName, String relation, String userEmail) {
        return mAPIService.updateUserInfo(token, babyId, userId, userName, relation, userEmail);
    }

    /**
     * 2.29接口功能：家长端，修改家长头像
     *
     * @param userId 宝宝的id
     * @param path
     * @return
     */
    public Observable<BaseModel<String>> changeHeadImgById(String userId, String path) {
        RequestBody requestToken = RequestBody.create(null, token);
        RequestBody requestUserId = RequestBody.create(null, userId);
        RequestBody requestFile = RequestBody.create(MediaType.parse(mediaTypeImageJpeg), new File(path));
        return mAPIService.changeHeadImgById(requestToken, requestUserId, requestFile);
    }

    /**
     * 5.17 接口功能：家长端，获取个人信息
     */
    @Deprecated
    public Observable<BaseModel<UserModel>> selectUserByToken() {
        return mAPIService.selectUserByToken(token);
    }

    /**
     * 5.17 接口功能：家长端，获取个人信息
     */
    public Observable<BaseModel<UserModel>> selectUserExtendByUBId(String userId, String babyId) {
        return mAPIService.selectUserExtendByUBId(token, userId, babyId);
    }

    /**
     * 5.7接口功能：家长端，通讯录-是否公开个人手机号
     *
     * @param userId
     * @param isPublic 是否公开 0：是 ；1：否
     * @return
     */
    public Observable<BaseModel<String>> changePublic(String userId, String isPublic) {
        return mAPIService.changePublic(token, userId, isPublic);
    }

    /**
     * 6.1 接口功能：教师端-获取所在幼儿园信息
     *
     * @param teacherId
     * @return
     */
    public Observable<BaseModel<SchoolModel>> selectSchoolByTeacherId(String teacherId) {
        return mAPIService.selectSchoolByTeacherId(token, teacherId);
    }

    /**
     * 6.2 接口功能：教师端-获取所班级列表
     *
     * @param teacherId
     * @return
     */
    public Observable<BaseModel<List<ClassModel>>> selectClassesByTeacherId(String teacherId) {
        return mAPIService.selectClassesByTeacherId(token, teacherId);
    }

    /**
     * 9.19 接口功能：教师端获取个人信息
     *
     * @return
     */
    @Deprecated
    public Observable<BaseModel<TeacherModel>> selectTeacherByToken() {
        return mAPIService.selectTeacherByToken(token);
    }

    /**
     * 9.1 接口功能：教师端-老师个人信息
     *
     * @return
     */
    public Observable<BaseModel<TeacherModel>> selectTeacherById(String teacherId) {
        return mAPIService.selectTeacherById(token, teacherId);
    }

    //9.3接口功能：教师端-老师个人信息-头像修改(tTeacher/changeHeadImgById)
    public Observable<BaseModel<String>> tChangeHeadImgById(String teacherId, String path) {
        RequestBody requestToken = RequestBody.create(null, token);
        RequestBody requestTeacherId = RequestBody.create(null, teacherId);
        RequestBody requestFile = RequestBody.create(MediaType.parse(mediaTypeImageJpeg), new File(path));
        return mAPIService.tChangeHeadImgById(requestToken, requestTeacherId, requestFile);
    }

    /**
     * 7.1 接口功能：教师端，动态-图片 一次全上传
     *
     * @param teacherId
     * @param description
     * @param classIds
     * @param paths
     * @return
     */
    public Observable<BaseModel<String>> dynamicUploadFiles(String teacherId, String description, String[] classIds, String[] paths) {
        RequestBody requestToken = RequestBody.create(null, token);
        RequestBody requestTeacherId = RequestBody.create(null, teacherId);
        RequestBody requestDescription = RequestBody.create(null, description);
        RequestBody requestClassIds = RequestBody.create(null, new Gson().toJson(classIds));
        RequestBody[] files = new RequestBody[paths.length];
        for (int i = 0; i < files.length; i++) {
            files[i] = RequestBody.create(MediaType.parse(mediaTypeImageJpeg), new File(paths[i]));
        }
        return mAPIService.dynamicUploadFiles(requestToken, requestTeacherId, requestDescription, requestClassIds, files);
    }

    /**
     * 7.2 接口功能：教师端，动态-小视频 第一帧
     *
     * @param teacherId
     * @param description
     * @param classIds
     * @param paths
     * @return
     */
    public Observable<BaseModel<JSONObject>> dynamicUploadFirstFrame(String teacherId, String description, String[] classIds, String[] paths) {
        RequestBody requestToken = RequestBody.create(null, token);
        RequestBody requestTeacherId = RequestBody.create(null, teacherId);
        RequestBody requestDescription = RequestBody.create(null, description);
        RequestBody requestClassIds = RequestBody.create(null, new Gson().toJson(classIds));
        RequestBody[] files = new RequestBody[paths.length];
        for (int i = 0; i < files.length; i++) {
            files[i] = RequestBody.create(MediaType.parse(mediaTypeImageJpeg), new File(paths[i]));
        }
        return mAPIService.dynamicUploadFirstFrame(requestToken, requestTeacherId, requestDescription, requestClassIds, files);
    }

    /**
     * 7.3 接口功能：教师端，动态-小视频
     *
     * @param videoIds
     * @param videoNames
     * @param paths
     * @return
     */
    public Observable<BaseModel<JSONObject>> dynamicUploadSmallVideo(String videoIds, String videoNames, String[] paths) {
        RequestBody requestToken = RequestBody.create(null, token);
        RequestBody requestVideoIds = RequestBody.create(null, videoIds);
        RequestBody requestVideoNames = RequestBody.create(null, videoNames);
        RequestBody[] files = new RequestBody[paths.length];
        for (int i = 0; i < files.length; i++) {
            files[i] = RequestBody.create(MediaType.parse(mediaTypeImageJpeg), new File(paths[i]));
        }
        return mAPIService.dynamicUploadSmallVideo(requestToken, requestVideoIds, requestVideoNames, files);
    }

    /**
     * 6.2 接口功能：教师端-获取所班级列表
     *
     * @param babyId
     * @return
     */
    public Observable<BaseModel<ReviewModel>> selectTevaluationAllBybabyid(String babyId, String time) {
        time = time == null ? "" : time;
        return mAPIService.selectTevaluationAllBybabyid(token, babyId, time);
    }
//---------------------------------------------------------------------------------------------

    /**
     * 1.7 添加宝宝信息
     */
    public Observable<BaseModel<BabyModel>> createBaby(String userId, String baby, String phoneNum) {
        return mAPIService.createBaby(token, userId, baby, phoneNum);
    }

    /**
     * 2.32 接口功能：家长端，关联幼儿园成功后设置邮件总人数+1
     */
    public Observable<BaseModel<String>> setMessageTotalNum(String classId) {
        return mAPIService.setMessageTotalNum(token, classId);
    }

    /**
     * 5.9家长端，查询通讯录-baby所在班级老师通讯录
     */
    public Observable<BaseModel<List<TeacherModel>>> selectTeacherphonesfrombaby(String babyId) {
        return mAPIService.selectTeacherphonesfrombaby(token, babyId);
    }

    /**
     * 5.10 家长端 获取baby所在班级同学的通讯录
     */
    public Observable<BaseModel<List<BabyModel>>> selectTBabyphonesfrombaby(String babyId) {
        return mAPIService.selectTBabyphonesfrombaby(token, babyId);
    }

    /**
     * 5.11 接口功能：家长端，通讯录-baby所在班级同学-关系人通讯录
     */
    public Observable<BaseModel<List<UserModel>>> selectTBabysphonesfrombaby(String babyId) {
        return mAPIService.selectTBabysphonesfrombaby(token, babyId);
    }

    /**
     * 9.12 接口功能：教师端，通讯录-登录老师所在班级的所有宝宝名称列表
     */
    public Observable<BaseModel<List<BabyModel>>> selectTBabysphonesfromteacher(String teacherId) {
        return mAPIService.selectTBabysphonesfromteacher(token, teacherId);
    }

    /**
     * 9.14 接口功能：教师端，通讯录-全校通讯录
     *
     * @param schoolId
     * @return
     */
    public Observable<BaseModel<List<TeacherModel>>> selectTBabysphonesAll(String schoolId) {
        return mAPIService.selectTBabysphonesAll(token, schoolId);
    }

    /**
     * 2.2接口功能：家长一个宝贝的首页信息
     */
    public Observable<BaseModel<JSONObject>> getIndexCommon(String babyId) {
        return mAPIService.getIndexCommon(token, babyId);
    }

    /**
     * 2.12接口功能：家长端，体重身高最新数据获取(height/selectLatestHeight)
     * 上行:
     * baby_id:
     * token:
     * 下行：
     * {
     * "code": "200",
     * "msg": "ok",
     * "data": {
     * "weight": 43.6,
     * "height": 15
     * }
     * }
     */
    public Observable<BaseModel<JSONObject>> selectLatestHeight(String babyId) {
        return mAPIService.selectLatestHeight(token, babyId);
    }

    /**
     * 2.13接口功能：家长端，身高初始化(height/initHeight)
     * 上行:
     * userId:
     * babyId：
     * birthHeight：
     * nowHeight:
     * birthDate：
     * inputDate：
     * token:
     * 下行：
     * {
     * "code": "200",
     * "msg": "ok",
     * "data": null
     * }
     */
    public Observable<BaseModel<JSONObject>> initHeight(String userId, String babyId, String birthHeight, String nowHeight, String birthDate, String inputDate) {
        return mAPIService.initHeight(userId, babyId, birthHeight, nowHeight, birthDate, inputDate, token);
    }

    /**
     * 2.23接口功能：家长端，身高添加(weight/insertHeight)
     * 上行:
     * userId:
     * babyId:
     * nowHeight:
     * inputDate:
     * token:
     * 下行：
     * {
     * "code": "200",
     * "msg": "ok",
     * "data": null
     * }
     */
    public Observable<BaseModel<JSONObject>> insertHeight(String userId, String babyId, String nowHeight, String inputDate) {
        return mAPIService.insertHeight(userId, babyId, nowHeight, inputDate, token);
    }

    /**
     * 2.14接口功能：家长端，身高 历史记录(height/selectHisHeight)
     * 上行:
     * babyId:
     * token:
     * 下行：
     * {
     * "code": "200",
     * "msg": "ok",
     * "data": [
     * {
     * "heightId": 8,
     * "babyId": 1,
     * "creatorId": 1,
     * "height": 11.2,
     * "inputDate": "2016-12-29"
     * },
     * {
     * "heightId": 9,
     * "babyId": 1,
     * "creatorId": 1,
     * "height": 13.2,
     * "inputDate": "2016-12-29"
     * },
     * {
     * "heightId": 10,
     * "babyId": 1,
     * "creatorId": 1,
     * "height": 15,
     * "inputDate": "2016-12-29"
     * }
     * ]
     * }
     */
    public Observable<BaseModel<JSONArray>> selectHisHeight(String babyId) {
        return mAPIService.selectHisHeight(babyId, token);
    }

    /**
     * 2.15接口功能：家长端，身高 修改(height/updateHeightById)
     * 上行:
     * heightId：
     * nowHeight：
     * token：
     * 下行：
     * {
     * "code": "200",
     * "msg": "ok",
     * "data": null
     * }
     */
    public Observable<BaseModel<JSONObject>> updateHeightById(String heightId, String nowHeight) {
        return mAPIService.updateHeightById(heightId, nowHeight, token);
    }

    /**
     * 2.16接口功能：家长端，身高 删除(height/deleteHeightById)
     * 上行:
     * heightId：
     * token：
     * 下行：
     * {
     * "code": "200",
     * "msg": "ok",
     * "data": null
     * }
     */
    public Observable<BaseModel<JSONObject>> deleteHeightById(String heightId) {
        return mAPIService.deleteHeightById(heightId, token);
    }

    //    2.24接口功能：家长端，体重初始化(weight/initWeight)
//    上行:
//    userId:
//    babyId：
//    birthWeight：
//    nowWeight:
//    birthDate：
//    inputDate：
//    token:
//    下行：
//    {
//        "code": "200",
//            "msg": "ok",
//            "data": null
//    }
//
    public Observable<BaseModel<JSONObject>> initWeight(String userId, String babyId, String birthWeight, String nowWeight, String birthDate, String inputDate) {
        return mAPIService.initWeight(userId, babyId, birthWeight, nowWeight, birthDate, inputDate, token);
    }

    //    2.25接口功能：家长端，体重添加(weight/insertWeight)
//    上行:
//    userId:
//    babyId:
//    nowWeight:
//    inputDate:
//    token:
//    下行：
//    {
//        "code": "200",
//            "msg": "ok",
//            "data": null
//    }
//
    public Observable<BaseModel<JSONObject>> insertWeight(String userId, String babyId, String nowWeight, String inputDate) {
        return mAPIService.insertWeight(userId, babyId, nowWeight, inputDate, token);
    }

    //    2.26接口功能：家长端，体重 历史记录(weight/selectHisWeight)
//    上行:
//    babyId:
//    token:
//    下行：
//    {
//        "code": "200",
//            "msg": "ok",
//            "data": [
//        {
//            "weightId": 1,
//                "babyId": 1,
//                "creatorId": 1,
//                "weight": 30.5,
//                "inputDate": "2016-12-29"
//        },
//        {
//            "weightId": 2,
//                "babyId": 1,
//                "creatorId": 1,
//                "weight": 40.5,
//                "inputDate": "2016-12-29"
//        },
//        {
//            "weightId": 4,
//                "babyId": 1,
//                "creatorId": 1,
//                "weight": 40.5,
//                "inputDate": "2016-12-29"
//        },
//        {
//            "weightId": 5,
//                "babyId": 1,
//                "creatorId": 1,
//                "weight": 43.6,
//                "inputDate": "2016-12-29"
//        }
//        ]
//    }
//
    public Observable<BaseModel<JSONArray>> selectHisWeight(String babyId) {
        return mAPIService.selectHisWeight(babyId, token);
    }

    //    2.27接口功能：家长端，体重 修改(weight/updateWeightById)
//    上行:
//    weightId：
//    nowWeight：
//    token：
//    下行：
//    {
//        "code": "200",
//            "msg": "ok",
//            "data": null
//    }
    public Observable<BaseModel<JSONObject>> updateWeightById(String weightId, String nowWeight) {
        return mAPIService.updateWeightById(weightId, nowWeight, token);
    }

    //    2.28接口功能：家长端，体重 删除(weight/deleteWeightById)
//    上行:
//    weightId：
//    token：
//    下行：
//    {
//        "code": "200",
//            "msg": "ok",
//            "data": null
//    }
//
    public Observable<BaseModel<JSONObject>> deleteWeightById(String weightId) {
        return mAPIService.deleteWeightById(weightId, token);
    }

    //    5.4接口功能：家长端，获取家庭人s信息(userbaby/selectCareUsers)
//    上行：
//    babyId:
//    token:
//    下行：
//    {
//        "code": "200",
//            "msg": "ok",
//            "data": [
//        {
//            "userId": 1,
//                "userName": "张三",
//                "headImageUrl": "ddd.jpg",
//                "relation": "爸爸",
//                "userEmail": "wosdhf@163.com"
//        },
//        {
//            "userId": 2,
//                "userName": "李四",
//                "headImageUrl": "dddd.jpg",
//                "relation": "叔叔",
//                "userEmail": "dsfasdf@asdf.com"
//        }
//        ]
//    }
    public Observable<BaseModel<List<UserModel>>> selectCareUsers(String babyId) {
        return mAPIService.selectCareUsers(token, babyId);
    }

    //6.6接口功能：教师端-教学计划-获取教学计划列表(tTeachingplan/selectTeachingplanByTeacherId)
//    上行:
//    Token：
//    下行：
//    {
//        "code": "200",
//            "msg": "ok",
//            "data": [
//        {
//            "teachingplanId": 1,
//                "creatorId": 1,
//                "classId": 1,
//                "imageurl": "051d8b6916334fa8b30495f314701fb8.png",
//                "createTime": "2016-12-31 15-29-27",
//                "planningTime": "2016-12-01"
//        },
//        {
//            "teachingplanId": 2,
//                "creatorId": 1,
//                "classId": 2,
//                "imageurl": "051d8b6916334fa8b30495f314701fb8.png",
//                "createTime": "2016-12-31 15-29-27",
//                "planningTime": "2016-12-01"
//        }]
//    }
    public Observable<BaseModel<List<TeachingPlanModel>>> selectTeachingplanByTeacherId(String teacherId) {
        return mAPIService.selectTeachingplanByTeacherId(token, teacherId);
    }

    //    2.31接口功能：家长-教学计划-获取教学计划列表（tTeachingplan/selectTeachingplanFromUserId）
//    classId:
//    planningTime: 2017-01-01
    public Observable<BaseModel<List<TeachingPlanModel>>> selectTeachingplanFromUserId(String classId, String planningTime) {
        return mAPIService.selectTeachingplanFromUserId(token, classId, planningTime);
    }

    //    6.18 接口功能：教师端-教学计划-删除教学计划（tTeachingplan/deleteTeachingplanByTeacherId）
    public Observable<BaseModel<JSONObject>> deleteTeachingplanByTeacherId(String teachingplanId) {
        return mAPIService.deleteTeachingplanByTeacherId(token, teachingplanId);
    }

    /**
     * 6.7 接口功能：教师端-教学计划-上传教学计划
     */
    public Observable<BaseModel<JSONObject>> insertTeachingplan(String tid, String classstr, String time, String path) {
        RequestBody requestToken = RequestBody.create(null, token);
        RequestBody teacherId = RequestBody.create(null, tid);
        RequestBody classIds = RequestBody.create(null, classstr);
        RequestBody planningTime = RequestBody.create(null, time);
        RequestBody requestBody = RequestBody.create(MediaType.parse(mediaTypeImageJpeg), new File(path));
        return mAPIService.insertTeachingplan(requestToken, teacherId, classIds, planningTime, requestBody);
    }

    //    8.3老师的活动创建   /tevet/insertEvents
//    int[] class_ids,       4,5,6  (必须是已经有的班级)
//    TEvent tEvent：
    public Observable<BaseModel<JSONObject>> insertEvents(String event, String classIds) {
        return mAPIService.insertEvents(token, event, classIds);
    }

    //    9.7老师端获取 老师关联班级活动列表   /tevet/selecteventsByteacher
    public Observable<BaseModel<JSONArray>> selecteventsByteacher(String teacherid) {
        return mAPIService.selecteventsByteacher(token, teacherid);
    }

    //   8.1获取老师所在所在班级的所有活动(可能更新为校园内)          /tevet/selectEventOnTeacher
    public Observable<BaseModel<JSONArray>> selectEventOnTeacher(String teacherid, String type, String schoolId) {
        return mAPIService.selectEventOnTeacher(token, teacherid, type, schoolId);
    }

    // 搜索校园    /tevet/selectEventOnTeacher
    public Observable<BaseModel<JSONArray>> selectEventOnTeacher(String teacherid, String type, String schoolId,String conditions) {
        return mAPIService.selectEventOnTeacher(token, teacherid, type, schoolId,conditions);
    }
    //-------------------------------------1.3-------------------------
    //  5.15获得家长端 宝宝参加的活动列表   /tevet/selectEventIsJoin
    public Observable<BaseModel<JSONArray>> selectEventIsJoin(String babyid) {
        return mAPIService.selectEventIsJoin(token, babyid);
    }

    //   3.2家长端获取活动详情     /tevet/selectEventObject
    public Observable<BaseModel<JSONObject>> selectEventObject(String babyid, String eventid) {
        return mAPIService.selectEventObject(token, babyid, eventid);
    }

    // 3.3家长端 参加\取消参加  /tevet/isNotJoinEvent
    public Observable<BaseModel<JSONArray>> isNotJoinEvent(String babyid, String eventid, String type) {
        return mAPIService.isNotJoinEvent(token, babyid, eventid, type);
    }

    // 3.1获取baby所在班级的所有活动   /tevet/selectEventByClassToOneBaby
    public Observable<BaseModel<JSONArray>> selectEventByClassToOneBaby(String classid, String babyid, String schoolid, String type) {
        return mAPIService.selectEventByClassToOneBaby(token, classid, babyid, schoolid, type);
    }

    //校园搜索
    public Observable<BaseModel<JSONArray>> selectEventByClassToOneBaby(String classid, String babyid, String schoolid, String type,String condition) {
        return mAPIService.selectEventByClassToOneBaby(token, classid, babyid, schoolid, type,condition);
    }

    /**
     * 2.9 接口功能：家长端，小视频新建-第一帧图片先上传
     */
//    token:
//    teacherId：
//    description:
//    classIds: ["1","2","3"]
//    files:
//    files:
//            .....多个files
    public Observable<BaseModel<AlbumModel>> teacherVideoFirstFrameImg(String teacherId, String description, String classIds, String path) {
        RequestBody requestToken = RequestBody.create(null, token);
        RequestBody requestTeacherId = RequestBody.create(null, teacherId);
        RequestBody requestDescription = RequestBody.create(null, description);
        RequestBody requestClassIds = RequestBody.create(null, classIds);
        RequestBody requestFile = RequestBody.create(MediaType.parse(mediaTypeImageJpeg), new File(path));
        return mAPIService.teacherVideoFirstFrameImg(requestToken, requestTeacherId, requestDescription, requestClassIds, requestFile);
    }

    //9.2接口功能：教师端-老师个人信息-修改(tTeacher/updateTeacherInfo)
    public Observable<BaseModel<JSONObject>> updateTeacherInfo(String tTeacher) {
        return mAPIService.updateTeacherInfo(token, tTeacher);
    }

    //9.4接口功能：教师端-老师个人信息-是否公开个人信息(tTeacher/changePublic)
    public Observable<BaseModel<JSONObject>> teacherChangePublic(String teacherId, String isPublic) {
        return mAPIService.teacherChangePublic(token, teacherId, isPublic);
    }

    //    6.5接口功能：教师端-班级考勤，上传(tBabyattendance/insertAttendance)
    public Observable<BaseModel<JSONObject>> insertAttendance(String babyattendances) {
        return mAPIService.insertAttendance(token, babyattendances);
    }

    //    6.4接口功能：教师端-班级考勤，获取某个班级考勤列表（tBabyattendance/selectAttendanceByClassId）
    public Observable<BaseModel<JSONArray>> selectAttendanceByClassId(String classId, String searchtime) {
        return mAPIService.selectAttendanceByClassId(token, classId, searchtime);
    }

    //    6.3接口功能：教师端-班级考勤，获取班级列表+考勤信息（tBabyattendance/selectAttendanceByTeacherId）
    public Observable<BaseModel<JSONObject>> selectAttendanceByTeacherId(String searchtime, String teacherId) {
        return mAPIService.selectAttendanceByTeacherId(token, searchtime, teacherId);
    }

    //    6.9接口功能：教师端-宝宝评语-获取某个班级当天的各项评分名单  /Tevaluation/selectTevaluationScore
    public Observable<BaseModel<JSONArray>> selectTevaluationScore(String searchtime, String classId, String type) {
        return mAPIService.selectTevaluationScore(token, searchtime, classId, type);
    }

    //    6.11接口功能：教师端-宝宝评语-获取某个班级当天的各项“评语”名单 /Tevaluation/selectTevaluationComments
    public Observable<BaseModel<JSONArray>> selectTevaluationComments(String searchtime, String classId) {
        return mAPIService.selectTevaluationComments(token, searchtime, classId);
    }

    //    6.10接口功能：教师端-宝宝评语-获取某个班级当天的各项评分名单 上传  /Tevaluation/insertTevaluationScore
    public Observable<BaseModel<JSONObject>> insertTevaluationScore(String searchtime, String type, String scores, String babyIds, String createId) {
        return mAPIService.insertTevaluationScore(token, searchtime, type, scores, babyIds, createId);
    }

    //    6.12接口功能：教师端-宝宝评语-获取某个班级当天的各项”评语”名单 上传
    public Observable<BaseModel<JSONObject>> insertTevaluationComments(String searchtime, String Comments) {
        return mAPIService.insertTevaluationComments(token, searchtime, Comments);
    }

    //    6.8接口功能：教师端-宝宝评语-获取某个班级当天的评价完成度（服务器认为班级内某个宝宝有值就认为该项已经完成）
    public Observable<BaseModel<JSONObject>> SelectTevaluationComplete(String searchtime, String classId) {
        return mAPIService.SelectTevaluationComplete(token, searchtime, classId);
    }

    //    6.8接口功能：教师端-宝宝评语-获取某个班级当天的评价完成度（服务器认为班级内某个宝宝有值就认为该项已经完成）
    public Observable<BaseModel<JSONArray>> selectTrecipeTeacher(String schoolId) {
        return mAPIService.selectTrecipeTeacher(token, schoolId);
    }

    //    2.18接口功能：家长端，获取收件箱列表(tMessage/getReceiveMessageListByUserId)
    public Observable<BaseModel<JSONArray>> getReceiveMessageListByUserId(String receiveId) {
        return mAPIService.getReceiveMessageListByUserId(token, receiveId);
    }

    //  2.20接口功能：家长端，获取发件箱列表(tMessage/getSendMessageListByUserId)
    public Observable<BaseModel<JSONArray>> getSendMessageListByUserId(String sendId) {
        return mAPIService.getSendMessageListByUserId(token, sendId);
    }

    //2.21 接口功能：家长端，发送邮件给老师（tMessage/userSendMessageToTeacher）
    public Observable<BaseModel<JSONObject>> userSendMessageToTeacher(String userId, String receiveTeacherId, String title, String content) {
        return mAPIService.userSendMessageToTeacher(token, userId, receiveTeacherId, title, content);
    }

    //   6.13接口功能：教师端，获取收件箱列表（学校、家长发送的消息）(tMessage/getReceiveMessageListByTeacherId)
    public Observable<BaseModel<JSONArray>> getReceiveMessageListByTeacherId(String receiveId) {
        return mAPIService.getReceiveMessageListByTeacherId(token, receiveId);
    }

    //  6.15接口功能：教师端，获取发件箱列表（获取未读人的列表的接口）(tMessage/getReceiveMessageListByTeacherId)
    public Observable<BaseModel<JSONArray>> getSendMessageListByTeacherId(String sendId) {
        return mAPIService.getSendMessageListByTeacherId(token, sendId);
    }

    //2.21 接口功能：家长端，发送邮件给老师（tMessage/userSendMessageToTeacher）
    public Observable<BaseModel<JSONObject>> sendMessagetoClass(String teacherId, String receiveClassIds, String title, String content) {
        return mAPIService.sendMessagetoClass(token, teacherId, receiveClassIds, title, content);
    }

    public Observable<BaseModel<JSONObject>> setMessageReadWithUserId(String messageId, String subscriberId) {
        return mAPIService.setMessageReadWithUserId(token, messageId, subscriberId);
    }

    public Observable<BaseModel<JSONObject>> setMessageReadWithTeacherId(String messageId, String subscriberId) {
        return mAPIService.setMessageReadWithTeacherId(token, messageId, subscriberId);
    }

    //    //    5.8 接口功能：家长获取系统消息列表(tSysmsg/getSysmsgByUserId)
//    @FormUrlEncoded
//    @POST("tSysmsg/getSysmsgByUserId")
//    Observable<BaseModel<JSONObject>> getSysmsgByUserId(@Field("token") String token, @Field("subscriberId") String subscriberId);
//
    public Observable<BaseModel<JSONArray>> getSysmsgByUserId(String subscriberId) {
        return mAPIService.getSysmsgByUserId(token, subscriberId);
    }

    //    //   9.15接口功能：教师端，系统消息列表(tSysmsg/getSysmsgByTeacherId)
//    @FormUrlEncoded
//    @POST("tSysmsg/getSysmsgByTeacherId")
//    Observable<BaseModel<JSONObject>> getSysmsgByTeacherId(@Field("token") String token, @Field("subscriberId") String subscriberId);
//
    public Observable<BaseModel<JSONArray>> getSysmsgByTeacherId(String subscriberId) {
        return mAPIService.getSysmsgByTeacherId(token, subscriberId);
    }

    //    //    9.23接口功能：老师设置系统消息为已读（tSyssign/setSysmsgReadWithTeacherId）
//    @FormUrlEncoded
//    @POST("tSyssign/setSysmsgReadWithSchoolTeacherId")
//    Observable<BaseModel<JSONObject>> setSysmsgReadWithTeacherId(@Field("token") String token, @Field("sysmsgId") String sysmsgId, @Field("subscriberId") String subscriberId);
//
    public Observable<BaseModel<JSONObject>> setSysmsgReadWithTeacherId(String sysmsgId, String subscriberId) {
        return mAPIService.setSysmsgReadWithTeacherId(token, sysmsgId, subscriberId);
    }

    //    //    9.24接口功能：家长设置系统消息为已读（tSyssign/setSysmsgReadWithTeacherId）
//    @FormUrlEncoded
//    @POST("tSyssign/setSysmsgReadWithUserId")
//    Observable<BaseModel<JSONObject>> setSysmsgReadWithUserId(@Field("token") String token, @Field("sysmsgId") String sysmsgId, @Field("subscriberId") String subscriberId);
    public Observable<BaseModel<JSONObject>> setSysmsgReadWithUserId(String sysmsgId, String subscriberId) {
        return mAPIService.setSysmsgReadWithUserId(token, sysmsgId, subscriberId);
    }

    //    收藏夹
//    5.12接口功能：家长端，收藏夹    获取收藏列表
//    9.12 家长端同
    public Observable<BaseModel<JSONArray>> SelectTFavorite(String createId, String CreateType) {
        return mAPIService.SelectTFavorite(token, createId, CreateType);
    }

    //    5.13接口功能：家长端，收藏夹-收藏、添加
//    9.10 教师端同
    public Observable<BaseModel<JSONObject>> insertTFavorite(String createId, String CreateType, String favoriteType, String favoriteFavoriteid) {
        return mAPIService.insertTFavorite(token, createId, CreateType, favoriteType, favoriteFavoriteid);
    }

    //    5.14接口功能：家长端，收藏夹-取消收藏
//    9.11 教师端同
    public Observable<BaseModel<JSONObject>> DeleteTFavorite(String favoriteId) {
        return mAPIService.DeleteTFavorite(token, favoriteId);
    }

    //    4.1接口功能：家长端，资讯列表
//    /tevet/selectNewsInfoBySchoolId
//    schoolId 1
    public Observable<BaseModel<JSONArray>> selectNewsInfoBySchoolId(String schoolId, String conditions) {
        return mAPIService.selectNewsInfoBySchoolId(token, schoolId, conditions);
    }

    //    6.16接口功能：教师端，发件箱-获取未读人的列表的接口(tMessage/getSendUnReadMessageByTeacherId)
//    sendId：
//    messageId:
//    token：
    public Observable<BaseModel<JSONArray>> getSendUnReadMessageByTeacherId(String sendId, String messageId) {
        return mAPIService.getSendUnReadMessageByTeacherId(token, sendId, messageId);
    }

    //    宝宝关联  幼儿园(updateTBabyOnClassId)
    public Observable<BaseModel<JSONObject>> updateTBabyOnClassId(String babyId, String phoneNum, String babyName) {
        return mAPIService.updateTBabyOnClassId(token, babyId, phoneNum, babyName);
    }

    //  12.1 接口功能：家长端 首页搜索（indexCommon/selectIndexBySearch）
    // babyId： 发送者id，即当前登录用户id
//    searchContent：
    public Observable<BaseModel<JSONArray>> selectIndexBySearch(String babyId, String searchContent) {
        return mAPIService.selectIndexBySearch(token, babyId, searchContent);
    }

    /**
     * 1.15 家长退出
     *
     * @return
     */
    public Observable<BaseModel<String>> logout() {
        return mAPIService.logout(token);
    }

    /**
     * 1.16 教师长退出
     *
     * @return
     */
    public Observable<BaseModel<String>> teacherLogout() {
        return mAPIService.teacherLogout(token);
    }

    /**
     * 5.3 家长端，取消宝宝关注  ,删除关系人
     *
     * @param userId
     * @param babyId
     * @return
     */
    public Observable<BaseModel<String>> cancelCareBabyById(String userId, String babyId) {
        return mAPIService.cancelCareBabyById(token, userId, babyId);
    }
}
