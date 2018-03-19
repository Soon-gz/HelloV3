package com.hellobaby.library.data;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hellobaby.library.data.model.AdviceModel;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.AppVersionModel;
import com.hellobaby.library.data.model.AskForLeaveHistoryModel;
import com.hellobaby.library.data.model.AskForLeaveModel;
import com.hellobaby.library.data.model.AttendenceHistoryModel;
import com.hellobaby.library.data.model.AttendenceLeaveHistoryModel;
import com.hellobaby.library.data.model.AttendenceTeacherModel;
import com.hellobaby.library.data.model.BabyAttendancesModel;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.BabyRelationModel;
import com.hellobaby.library.data.model.BaseInfoHintModel;
import com.hellobaby.library.data.model.BaseInfoHintOldModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.CareOrCaredModel;
import com.hellobaby.library.data.model.ClassModel;
import com.hellobaby.library.data.model.CommentModel;
import com.hellobaby.library.data.model.ExchangeModel;
import com.hellobaby.library.data.model.InfoPersonMsgModel;
import com.hellobaby.library.data.model.InfomationModel;
import com.hellobaby.library.data.model.PrizeAddressModel;
import com.hellobaby.library.data.model.PrizeContactInfoModel;
import com.hellobaby.library.data.model.PrizeDrawBean;
import com.hellobaby.library.data.model.PrizeHistoryBean;
import com.hellobaby.library.data.model.PrizeLuckyModel;
import com.hellobaby.library.data.model.PrizeOrderDetailModel;
import com.hellobaby.library.data.model.ReviewModel;
import com.hellobaby.library.data.model.SchoolMasterModel;
import com.hellobaby.library.data.model.SchoolModel;
import com.hellobaby.library.data.model.SelectInfoDetailModel;
import com.hellobaby.library.data.model.ServerCarebabyCache;
import com.hellobaby.library.data.model.TeacherModel;
import com.hellobaby.library.data.model.TeacherTokenModel;
import com.hellobaby.library.data.model.TeachingPlanModel;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.data.remote.APIService;
import com.hellobaby.library.utils.JsonUtils;
import com.hellobaby.library.utils.LogZS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.http.Field;
import rx.Observable;
import rx.functions.Func1;

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
     * 1 Android家长端
     *
     * @return
     */
    public Observable<BaseModel<AppVersionModel>> appVersionGetUser() {
        return mAPIService.appVersionGet("1");
    }

    /**
     * 2 Android教师端
     *
     * @return
     */
    public Observable<BaseModel<AppVersionModel>> appVersionGetTeacher() {
        return mAPIService.appVersionGet("2");
    }

    /**
     * 5 Android考勤机
     *
     * @return
     */
    public Observable<BaseModel<AppVersionModel>> appVersionGetkaoqin() {
        return mAPIService.appVersionGet("5");
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
     * 接口功能：家长端（更换手机号码）获取短信验证码
     *
     * @param phone
     * @return
     */
    public Observable<BaseModel<String>> changePhoneCode(String phone) {
        return mAPIService.changePhoneCode(phone);
    }

    /**
     * 接口功能：教师端（更换手机号码）获取短信验证码
     *
     * @param phone
     * @return
     */
    public Observable<BaseModel<String>> tChangePhoneCode(String phone) {
        return mAPIService.tChangePhoneCode(phone);
    }

    /**
     * 接口功能：家长端（更换手机号码）修改手机号
     *
     * @param newPhone
     * @param smsCode
     * @param oldPwd
     * @return
     */
    public Observable<BaseModel<String>> smsCodeBasedPhoneNumChange(String newPhone, String smsCode, String oldPwd) {
        return mAPIService.smsCodeBasedPhoneNumChange(token, newPhone, smsCode, oldPwd);
    }

    /**
     * 接口功能：教师端（更换手机号码）修改手机号
     * @param newPhone
     * @param smsCode
     * @param oldPwd
     * @return
     */
    public Observable<BaseModel<String>> tsmsCodeBasedPhoneNumChange(String newPhone,String smsCode,String oldPwd) {
        return mAPIService.tsmsCodeBasedPhoneNumChange(token,newPhone,smsCode,oldPwd);
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
     * 1.2.1 接口功能：老师登入
     *
     * @param phone
     * @param pwd
     * @return
     */
    public Observable<BaseModel<TeacherTokenModel>> checkTeacherLoginRole(String phone, String pwd) {
        return mAPIService.checkTeacherLoginRole(phone, pwd);
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
    public Observable<BaseModel<String>> subRegister(String phone, String pwd, String smsCode, String userName) {
        return mAPIService.subRegister(phone, pwd, smsCode, userName);
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
     * 2.41 接口功能：家长端，相册新建（发布中的功能,先建一个相册，再执行单张上传的接口，回到首页后重新调用首页接口）（/indexCommon/insertAlbumAndImgs）
     */
    public Observable<BaseModel<JSONObject>> insertAlbumAndImgs(String userId, String babyId, String title, String content, String imgNames) {
        return mAPIService.insertAlbumAndImgs(token, userId, babyId, title, content, imgNames);
    }

    /**
     * 2.42 接口功能：家长端，上传图片（/indexCommon/uploadIndexFile）
     */
    public Observable<BaseModel<JSONObject>> uploadIndexFile(String commonId, String imgNames) {
        return mAPIService.uploadIndexFile(token, commonId, imgNames);
    }

    /**
     * 7.4 接口功能：教师端，动态-上传照片 (tDynamic/uploadDynamicFiles)
     */
    public Observable<BaseModel<JSONObject>> uploadDynamicFiles(String teacherId, String description, String classIds, String imgNames) {
        return mAPIService.uploadDynamicFiles(token, teacherId, description, classIds, imgNames);
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
     * @Deprecated 被insertFirstFrameAndVideo 替换了
     */
    @Deprecated
    public Observable<BaseModel<AlbumModel>> videoUpload(String videoId, String videoName, String path) {
        RequestBody requestToken = RequestBody.create(null, token);
        RequestBody requestVideoId = RequestBody.create(null, videoId);
        RequestBody requestVideoName = RequestBody.create(null, videoName);
        RequestBody requestFile = RequestBody.create(MediaType.parse(mediaTypeImageJpeg), new File(path));
        return mAPIService.videoUpload(requestToken, requestVideoId, requestVideoName, requestFile);
    }

    @Deprecated
    public Observable<BaseModel<JSONObject>> insertFirstFrameAndVideo(String userId, String babyId, String content, String firstFrameName, String videoName) {
        return mAPIService.insertFirstFrameAndVideo(token, userId, babyId, content, firstFrameName, videoName);
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
     * 2.44 接口功能：家长 通知服务器去下载
     *
     * @return
     */
    public Observable<BaseModel<JSONObject>> downloadSmallVideo(String fileName) {
        return mAPIService.downloadSmallVideo(token, fileName);
    }
    public Observable<BaseModel<JSONObject>> downloadDynamicSmallVideo(String fileName) {
        return mAPIService.downloadDynamicSmallVideo(token, fileName);
    }

//    insertDynamicFirstFrameAndVideo
    /**
     * 2.47 接口功能：动态小视频- 插入t_dynamic和t_video表中
     *tDynamic/insertDynamicFirstFrameAndVideo
     * @return
     */
    public Observable<BaseModel<JSONObject>> insertDynamicFirstFrameAndVideo(String teacherId,String description,String classIds,String firstFrameName,String videoName) {
        return mAPIService.insertDynamicFirstFrameAndVideo(token,teacherId,description,classIds,firstFrameName,videoName);
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
    public Observable<BaseModel<BabyModel>> insertAndUpdateTBaby(String userId, String baby, String phoneNum) {
        return mAPIService.insertAndUpdateTBaby(token, userId, baby, phoneNum);
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

    /**
     * 13.5 设置家庭成员有无权限接送孩子（userbaby/update/pick）
     */
    public Observable<BaseModel<String>> updatePick(String userId, String babyId, boolean isPick) {
        String pick = isPick ? "1" : "0";//0表示不能接送，1表示能接送
        return mAPIService.updatePick(token,userId, babyId, pick);
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
    public Observable<BaseModel<JSONArray>> selectTeachingplanByTeacherId(String teacherId) {
        return mAPIService.selectTeachingplanByTeacherId(token, teacherId);
    }

    //    2.31接口功能：家长-教学计划-获取教学计划列表（tTeachingplan/selectTeachingplanFromUserId）
//    classId:
//    planningTime: 2017-01-01
    public Observable<BaseModel<List<TeachingPlanModel>>> selectTeachingplanFromUserId(String classId, String planningTime) {
        return mAPIService.selectTeachingplanFromUserId(token, classId, planningTime);
    }

    //   2.38接口功能：家长-教学计划-获取教学计划列表
//    classId:
//    planningTime: 2017-01-01
    public Observable<BaseModel<List<TeachingPlanModel>>> selectTeachingplan3FromUserId(String classId) {
        return mAPIService.selectTeachingplan3FromUserId(token, classId);
    }

    //    6.18 接口功能：教师端-教学计划-删除教学计划（tTeachingplan/deleteTeachingplanByTeacherId）
    public Observable<BaseModel<JSONObject>> deleteTeachingplanByTeacherId(String teachingplanId) {
        return mAPIService.deleteTeachingplanByTeacherId(token, teachingplanId);
    }

//    /**
//     * 6.7 接口功能：教师端-教学计划-上传教学计划
//     */
//    public Observable<BaseModel<JSONObject>> insertTeachingplan(String tid, String classstr, String time, String path) {
//        RequestBody requestToken = RequestBody.create(null, token);
//        RequestBody teacherId = RequestBody.create(null, tid);
//        RequestBody classIds = RequestBody.create(null, classstr);
//        RequestBody planningTime = RequestBody.create(null, time);
//        RequestBody requestBody = RequestBody.create(MediaType.parse(mediaTypeImageJpeg), new File(path));
//        return mAPIService.insertTeachingplan(requestToken, teacherId, classIds, planningTime, requestBody);
//    }

    /**
     * 6.7 接口功能：教师端-教学计划-上传教学计划
     */
    public Observable<BaseModel<JSONObject>> insertTeachingplan(String tid, String classstr, String time, String endTime, String path) {
        RequestBody requestToken = RequestBody.create(null, token);
        RequestBody teacherId = RequestBody.create(null, tid);
        RequestBody classIds = RequestBody.create(null, classstr);
        RequestBody planningEndtime = RequestBody.create(null, endTime);
        RequestBody planningTime = RequestBody.create(null, time);
        RequestBody requestBody = RequestBody.create(MediaType.parse(mediaTypeImageJpeg), new File(path));
        return mAPIService.insertTeachingplan(requestToken, teacherId, classIds, planningTime, planningEndtime, requestBody);
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

    //   //   8.5获取校园列表大接口 （分页）    /tevet/selectEventOnTeacherPage
    public Observable<BaseModel<JSONObject>> selectEventOnTeacherPage(String teacherid, String type, String schoolId, int pageNum) {
        return mAPIService.selectEventOnTeacherPage(token, teacherid, type, schoolId, pageNum);
    }

    // 搜索校园    /tevet/selectEventOnTeacher
    public Observable<BaseModel<JSONArray>> selectEventOnTeacher(String teacherid, String type, String schoolId, String conditions) {
        return mAPIService.selectEventOnTeacher(token, teacherid, type, schoolId, conditions);
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

    //   8.2接口功能：教师端，获取活动详情     /tevet/selectEventObjectOnTeacher
    public Observable<BaseModel<JSONObject>> selectEventObjectOnTeacher(String teacherId, String eventid) {
        return mAPIService.selectEventObjectOnTeacher(token, teacherId, eventid);
    }

    // 3.3家长端 参加\取消参加  /tevet/isNotJoinEvent
    public Observable<BaseModel<JSONArray>> isNotJoinEvent(String babyid, String eventid, String type) {
        return mAPIService.isNotJoinEvent(token, babyid, eventid, type);
    }

    //先这么写
    // 3.3家长端 参加\取消参加  /tevet/isNotJoinEvent
    public Observable<BaseModel<JSONArray>> isNotJoinEventAddNum(String babyid, String eventid, String type, String babyNumber, String parentsNumber) {
        return mAPIService.isNotJoinEventAddNum(token, babyid, eventid, type, babyNumber, parentsNumber);
    }

    // 3.1获取baby所在班级的所有活动   /tevet/selectEventByClassToOneBaby
    public Observable<BaseModel<JSONArray>> selectEventByClassToOneBaby(String classid, String babyid, String schoolid, String type) {
        return mAPIService.selectEventByClassToOneBaby(token, classid, babyid, schoolid, type);
    }

    // 3.5接口功能：家长端，获取”全部”、”新闻”、”动态”、“食谱”、”活动”校园内容列表(分页)
    public Observable<BaseModel<JSONObject>> selectEventByClassToOneBabyPage(String classid, String babyid, String schoolid, String type, int pageNum) {
        return mAPIService.selectEventByClassToOneBabyPage(token, classid, babyid, schoolid, type, pageNum);
    }

    //校园搜索
    public Observable<BaseModel<JSONArray>> selectEventByClassToOneBaby(String classid, String babyid, String schoolid, String type, String condition) {
        return mAPIService.selectEventByClassToOneBaby(token, classid, babyid, schoolid, type, condition);
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

    public Observable<BaseModel<JSONObject>> insertAttendanceWithType(String babyattendances) {
        return mAPIService.insertAttendanceWithType(token, babyattendances);
    }

    //    6.4接口功能：教师端-班级考勤，获取某个班级考勤列表（tBabyattendance/selectAttendanceByClassId）
    public Observable<BaseModel<JSONArray>> selectAttendanceByClassId(String classId, String searchtime) {
        return mAPIService.selectAttendanceByClassId(token, classId, searchtime);
    }

    //selectAttendanceByClassIdAndType
    public Observable<BaseModel<JSONArray>> selectAttendanceByClassIdAndType(String classId, String searchtime, String type) {
        return mAPIService.selectAttendanceByClassIdAndType(token, classId, searchtime, type);
    }

    //6.21 接口功能：教师端-班级考勤，获取某个班级考勤列表（点名未完成时）（tBabyattendance/selectAttendanceByClassIdUnComplete）
    public Observable<BaseModel<JSONArray>> selectAttendanceByClassIdUnComplete(String classId, String searchtime) {
        return mAPIService.selectAttendanceByClassIdUnComplete(token, classId, searchtime);
    }

    //    selectAttendanceByClassIdWithTypeUnComplete
    public Observable<BaseModel<JSONArray>> selectAttendanceByClassIdWithTypeUnComplete(String classId, String searchtime, String type) {
        return mAPIService.selectAttendanceByClassIdWithTypeUnComplete(token, classId, searchtime, type);
    }

    //    6.3接口功能：教师端-班级考勤，获取班级列表+考勤信息（tBabyattendance/selectAttendanceByTeacherId）
    public Observable<BaseModel<JSONObject>> selectAttendanceByTeacherId(String searchtime, String teacherId) {
        return mAPIService.selectAttendanceByTeacherId(token, searchtime, teacherId);
    }

    public Observable<BaseModel<JSONObject>> selectAttendanceByTeacherIdWithType(String searchtime, String teacherId) {
        return mAPIService.selectAttendanceByTeacherIdWithType(token, searchtime, teacherId);
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


    //    6.81接口功能：教师端-宝宝评语-获取某个班级当天的评价完成度（服务器认为班级内某个宝宝有值就认为该项已经完成）
    public Observable<BaseModel<JSONArray>> selectCompleteByTeacherId(String searchtime) {
        return mAPIService.selectCompleteByTeacherId(token, searchtime);
    }

    //    教师食谱
    public Observable<BaseModel<JSONArray>> selectTrecipeTeacher(String schoolId) {
        return mAPIService.selectTrecipeTeacher(token, schoolId);
    }

    //    教师食谱
    public Observable<BaseModel<JSONObject>> selectTrecipeTeacherPage(String schoolId, int pageNum) {
        return mAPIService.selectTrecipeTeacherPage(token, schoolId, pageNum);
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

    //    5.12接口功能：家长端，收藏夹    获取收藏列表
//    9.12 家长端同
    public Observable<BaseModel<JSONObject>> SelectTFavoritePage(String createId, String CreateType, int pageNum) {
        return mAPIService.SelectTFavoritePage(token, createId, CreateType, pageNum);
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

    //    4.1接口功能：家长端，资讯列表(分页)
//    /tevet/selectNewsInfoBySchoolId
//    schoolId 1
    public Observable<BaseModel<JSONObject>> selectNewsInfoBySchoolIdPage(String schoolId, int pageNum) {
        return mAPIService.selectNewsInfoBySchoolIdPage(token, schoolId, pageNum);
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

    /**
     * 2.33 接口功能：家长一个宝贝的首页信息(indexCommon/getIndexCommonPage)（分页）
     *
     * @param babyId
     * @return
     */
    public Observable<BaseModel<JSONObject>> getIndexCommon(String babyId, int pageNum) {
        return mAPIService.getIndexCommon(token, babyId, pageNum);
    }

    /**
     * 12.1 提交意见反馈
     *
     * @param adviceModel
     * @return
     */
    public Observable<BaseModel<String>> postAdvice(AdviceModel adviceModel) {
        Log.i("tag", JsonUtils.toJson(adviceModel));
        return mAPIService.postAdvice(JsonUtils.toJson(adviceModel), token);
    }

    /**
     * 2.35 接口功能：家长端删除发件箱(tMessagesignMore/deleteSendMessageOfUser)
     */
    public Observable<BaseModel<JSONObject>> deleteSendMessageOfUser(String messageId) {
        return mAPIService.deleteSendMessageOfUser(token, messageId);
    }

    /**
     * 2.36 接口功能：家长端删除收件箱(tMessagesignMore/deleteReceiveMessageOfUser)
     */
    public Observable<BaseModel<String>> deleteReceiveMessageOfUser(String messageId, String subscriberId) {
        return mAPIService.deleteReceiveMessageOfUser(token, messageId, subscriberId);
    }

    /**
     * 6.19 接口功能：教师端删除发件箱(tMessagesign/deleteSendMessageOfTeacher)
     */
    public Observable<BaseModel<JSONObject>> deleteSendMessageOfTeacher(String messageId) {
        return mAPIService.deleteSendMessageOfTeacher(token, messageId);
    }

    /**
     * 6.20 接口功能：教师端删除收件箱(tMessagesign/deleteReceiveMessageOfTeacher)
     */
    public Observable<BaseModel<JSONObject>> deleteReceiveMessageOfTeacher(String messageId, String subscriberId) {
        return mAPIService.deleteReceiveMessageOfTeacher(token, messageId, subscriberId);
    }

    //     * 3.6获取活动参加人员列表
    public Observable<BaseModel<JSONArray>> selectEventNumList(String eventId) {
        return mAPIService.selectEventNumList(token, eventId);
    }

    //     3.7删除活动 给创建老师所在班级发送信息
    public Observable<BaseModel<JSONObject>> deleteEventsByEventId(String eventId) {
        return mAPIService.deleteEventsByEventId(token, eventId);
    }

    /**
     * 13.1 接口功能：推送一条宝宝关注信息（userbaby/sendCareNoteToUser）
     *
     * @return
     */
    public Observable<BaseModel<JSONObject>> noficationBabyParent(String userId, String babyId, String userName) {
        Log.i("tag00", "userId:" + userId + " babyId: " + babyId + " userName: " + userName);
        return mAPIService.noficationBabyParent(userId, babyId, userName, token);
    }

    /**
     * 13.3 接口功能：点击否的时候调用该接口（userbaby/denyCareApply）
     * userId:   关注人userId  babyId:   token:
     */
    public Observable<BaseModel<String>> disagreeCarebaby(String userId, String babyId) {
        return mAPIService.disagreeCarebaby(userId, babyId, token);
    }

    /**
     * 13.2 接口功能：在客户端没有成功接收推送的情况下再次请求服务器数据库（userbaby/sendCareNoteWithUnNotified）
     * userId:   当前登录用户id    token:
     */
    public Observable<BaseModel<List<ServerCarebabyCache>>> sendCareNoteWithUnNotified(String userId) {
        return mAPIService.sendCareNoteWithUnNotified(userId, token);
    }

    /**
     * 2.37 接口功能：删除某宝宝的身高体重信息
     */
    public Observable<BaseModel<String>> deleteHeightWeightByBabyId(String babyId) {
        return mAPIService.deleteHeightWeightByBabyId(token, babyId);
    }

    //未知接口 deleteDynamicById
    public Observable<BaseModel<JSONObject>> deleteDynamicById(String dynamicId) {
        return mAPIService.deleteDynamicById(token, dynamicId);
    }

    /**
     * 14.1 接口功能：设置用户标签 (tUserTags/updateTag) 上行：userId： tag： json串  {1,2,3}
     */
    public Observable<BaseModel<String>> postJpushTags(String userId, String tags) {
        return mAPIService.postJpushTages(userId, tags);
    }

    //    2.39 接口功能：相册图片上传完后将commonId传过来设置最小的image_id为封面 （indexCommon/setAlbumCoverByCommonId）
    public Observable<BaseModel<JSONObject>> setAlbumCoverByCommonId(String commonId) {
        return mAPIService.setAlbumCoverByCommonId(token, commonId);
    }

    //    2.40 接口功能：根据上传过来的imageId设置为封面 （indexCommon/setAlbumCoverByImageId）
    public Observable<BaseModel<JSONObject>> setAlbumCoverByImageId(String commonId, String imageId) {
        return mAPIService.setAlbumCoverByImageId(token, commonId, imageId);
    }

    /**
     * 14.10 接口功能：扫描二维码的时候，提示用户此卡是否可以用（tQrcode/checkUsable）上行：   qrcode:   token:
     */
    public Observable<BaseModel<String>> qrScanCode(String code) {
        return mAPIService.qrScanCode(code, token);
    }

    /**
     * 14.6 接口功能：添加卡片人（tPickUp/insertTPickUp）上行：userId,babyId,qrcode,userName,phoneNum ,token:
     *
     * @return
     */
    public Observable<BaseModel<String>> insertBabycard(String userId, String babyId, String qrcode, String userName, String phoneNum, String relation) {
        Log.i("tag00", "userId:" + userId + " babyId:" + babyId + " qrcode:" + qrcode + " userName:" + userName + " relation:" + relation);
        return mAPIService.insertTPickUp(userId, babyId, qrcode, userName, phoneNum, relation, token);
    }

    /**
     * 14.6 接口功能：添加卡片人（tPickUp/insertTPickUp）上行：userId,babyId,qrcode,userName,phoneNum ,token:
     *
     * @return
     */
    public Observable<BaseModel<String>> uploadBabyCardHead(int fileId, String path) {
        Log.i("tag00", "fileId:" + fileId + " path:" + path);
        RequestBody requestFile = RequestBody.create(MediaType.parse(mediaTypeImageJpeg), new File(path));
        return mAPIService.babycardUploadImg(token, fileId, requestFile);
    }


    //    14.4 接口功能：插入请假信息，批量请假(tLeave/insertLeave)
//    userId：
//    babyId:
//    classId：
//    startTime:2017-03-10 09:00:00
//    endTime:2017-03-12 09:00:00
//    description:
    //    2.40 接口功能：根据上传过来的imageId设置为封面 （indexCommon/setAlbumCoverByImageId）
    public Observable<BaseModel<JSONObject>> insertLeave(String userId, String babyId,
                                                         String classId, String startTime,
                                                         String endTime, String description, String type, String className, String babyName) {
        return mAPIService.insertLeave(token, userId, babyId, classId, startTime, endTime, description, type, className, babyName);
    }

    //    14.3.设置代接送人员
    public Observable<BaseModel<JSONObject>> insertAgent(String tAgent, String agentSendContent) {
        return mAPIService.insertAgent(token, tAgent, agentSendContent);
    }

    //     14.1.获取当天考勤信息。三种情况：a-请假；b-无数据；c-有考勤数据（获取考勤机的数据）
    public Observable<BaseModel<JSONObject>> selectTodayAttendance(String babyId) {
        return mAPIService.selectTodayAttendance(token, babyId);
    }

    //------------------------------------------------考勤机------------------------------------------------

    /**
     * 14.12 根据考勤的机器序列号获取院校信息
     *
     * @param machineCode 设备id
     * @return
     */
    public Observable<BaseModel<SchoolModel>> SchoolByMachineCode(String machineCode) {
        return mAPIService.SchoolByMachineCode(token, machineCode);
    }

    /**
     * 绑定机器码状态
     * @param machineCode
     * @return
     */
    public Observable<BaseModel<String>> updateState(String machineCode) {
        return mAPIService.updateState(machineCode);
    }

    /**
     * 14.13 考勤机扫描二维码获取当时接送人和baby的信息
     *
     * @param schoolId 学校id
     * @param babyId   宝宝id
     * @param qrcode   qrcode
     * @return
     */
    public Observable<BaseModel<JSONObject>> babyAndPersonInfoByQrcode(String schoolId, String babyId, String userId, String qrcode, int eventType) {
        return mAPIService.babyAndPersonInfoByQrcode(token, schoolId, babyId, userId, qrcode, eventType);
    }

    /**
     * 14.14. 考勤机记录当时接送人的照片,并记录考勤
     *
     * @param schoolId  学校id
     * @param babyId    宝宝id
     * @param qrcode    babyId和qrcode二选一填，如果都没会报错
     * @param eventType 0到校  ，1离校
     * @param path      图片路径
     * @return
     */
    public Observable<BaseModel<String>> uploadTimeCardImg(String schoolId, String babyId, String userId, String qrcode, String eventType, String path) {
        RequestBody requestToken = RequestBody.create(null, token);
        RequestBody requestSchoolId = RequestBody.create(null, schoolId);
        RequestBody requestBabyId = RequestBody.create(null, babyId);
        RequestBody requestQrcode = RequestBody.create(null, qrcode);
        RequestBody requestEventType = RequestBody.create(null, eventType);
        RequestBody requestUserId = RequestBody.create(null, userId);
        RequestBody requestBody = RequestBody.create(MediaType.parse(mediaTypeImageJpeg), new File(path));
        return mAPIService.uploadTimeCardImg(requestToken, requestSchoolId, requestBabyId, requestUserId, requestQrcode, requestEventType, requestBody);
    }

    /**
     * 14.15. 考勤机记录当时接送人的照片,并记录考勤
     *
     * @param schoolId  学校id
     * @param babyId    宝宝id
     * @param qrcode    babyId和qrcode二选一填，如果都没会报错
     * @param eventType 0到校  ，1离校
     * @param imageName 图片名称
     * @return
     */
    public Observable<BaseModel<String>> uploadNewTimeCardImg(String schoolId, String babyId, String userId, String qrcode, String eventType, String imageName) {
        return mAPIService.uploadNewTimeCardImg(token, schoolId, babyId, userId, qrcode, eventType, imageName);
    }

    /**
     * 14.15. 考勤机记录当时接送人的照片,并记录考勤
     *
     * @param schoolId  学校id
     * @param qrcode    babyId和qrcode二选一填，如果都没会报错
     * @return
     */
    public Observable<BaseModel<String>> uploadTeacherCardImg(String schoolId, String teacherId, String qrcode, String imgName,int eventType,long time) {
        return mAPIService.uploadTeacherCardImg(schoolId, teacherId, qrcode, imgName ,eventType,time);
    }


    /**
     * 14.17. 考勤机记录当时接送人的照片,并记录考勤  只有两个状态
     *
     * @param schoolId  学校id
     * @param qrcode    babyId和qrcode二选一填，如果都没会报错
     * @return
     */
    public Observable<BaseModel<String>> uploadTeacherAttendance(String schoolId, String teacherId, String qrcode, String imgName,int eventType,long time) {
        return mAPIService.uploadTeacherAttendance(schoolId, teacherId, qrcode, imgName ,eventType,time);
    }

    /**
     * 14.16. 班级学生考勤
     */
    public Observable<BaseModel<JSONArray>> selectBabyTimeCardByClass(String classId, String searchTime) {
        return mAPIService.selectBabyTimeCardByClass(token, classId, searchTime);
    }

    //------------------------------------------------考勤机------------------------------------------------

    //   //  14.2.获取历史记录考勤机数据（展示为时间倒序）
    public Observable<BaseModel<JSONArray>> selectHistoryAttendance(String babyId) {
        return mAPIService.selectHistoryAttendance(token, babyId);
    }

    /**
     * 14.5 接口功能：获取接送卡列表(tPickUp/selectPickUpsByBabyId)上行：babyId:  token:
     */
    public Observable<BaseModel<List<BabyRelationModel>>> getBabyRelationes(int babyId) {
        return mAPIService.getBabyRelatinCards(babyId, token);
    }

    /**
     * 14.9 接口功能：编辑接送卡（tPickUp/updatePickUpById）上行：pickUpId:userName：phoneNum：relation：token:
     */
    public Observable<BaseModel<JSONObject>> updateBabyCard(int pickUpId, String userName, String phoneNum, String relation) {
        Log.i("tag00", "pickUpId:" + pickUpId + " phoneNum:" + phoneNum + " userName:" + userName + " relation:" + relation);
        return mAPIService.updateBabyCard(pickUpId, userName, phoneNum, relation, token);
    }

    /**
     * 14.8 接口功能：删除接送卡（tPickUp/deletePickUpById）上行：pickUpId:token:
     */
    public Observable<BaseModel<JSONObject>> deleteBabyCard(int pickUpId) {
        return mAPIService.deleteBabyCard(pickUpId, token);
    }

    /**
     * 14.12 接口功能： 查询班级未到和请假情况 selectLeaveByClassId
     */
    @Deprecated
    public Observable<BaseModel<List<BabyAttendancesModel>>> selectLeaveByClassId(String classId) {
        return mAPIService.selectLeaveByClassId(classId, token);
    }

    /**
     * 教师端-班级考勤，获取某个班级考勤列表 跟宝宝评价有关
     */
    public Observable<BaseModel<List<BabyAttendancesModel>>> selectLeaveByClassIdWithType(String classId) {
        return mAPIService.selectLeaveByClassIdWithType(classId, token);
    }

    /**
     * 15.1 用户创建和关注时为每个用户插入提醒信息
     */
    public Observable<BaseModel<JSONObject>> insertInitAlert(String tAlert) {
        return mAPIService.insertInitAlert(token, tAlert);
    }

    /**
     * 15.2  更新Alert  系统消息、收发件箱、校园、宝宝评语、考勤、教学计划等。
     */
    public Observable<BaseModel<JSONObject>> updateAlert(String tAlert) {
        return mAPIService.updateAlert(token, tAlert);
    }

    /**
     * 15.3 获取Alert  系统消息、收发件箱、校园、宝宝评语、考勤、教学计划等。
     */
    public Observable<BaseModel<JSONObject>> selectAlert(String userId, String classId, String schoolId, String babyId) {
        return mAPIService.selectAlert(token, userId, classId, schoolId, babyId);
    }

    /**
     * 15.4  更新TeacherAlert  系统消息、收发件箱、校园
     */
    public Observable<BaseModel<JSONObject>> updateTeacherAlert(String tTeacherAlert) {
        return mAPIService.updateTeacherAlert(token, tTeacherAlert);
    }

    /**
     * 15.5 教师端获取提醒信息
     */
    public Observable<BaseModel<JSONObject>> selectTeacherAlert(String teacherId, String schoolId) {
        return mAPIService.selectTeacherAlert(token, teacherId, schoolId);
    }

    /**
     * 获得当前用户的积分
     *
     * @return
     */
    public Observable<BaseModel<String>> getScores() {
        return mAPIService.getScores(token);
    }

    /**
     * 获得可兑换的商品
     *
     * @return
     */
    public Observable<BaseModel<List<PrizeDrawBean>>> getRecordDrawList() {
        return mAPIService.getRecordDrawList(token);
    }

    /**
     * 获得中奖信息
     *
     * @return
     */
    public Observable<BaseModel<PrizeLuckyModel>> getLuckDraw() {
        return mAPIService.getLuckDraw(token);
    }

    /**
     * 获得中奖信息
     *
     * @return
     */
    public Observable<BaseModel<List<PrizeHistoryBean>>> getRecordLine() {
        return mAPIService.getRecordLine(token);
    }

    /**
     * 获得当前中奖用户默认收货地址
     *
     * @return
     */
    public Observable<BaseModel<List<PrizeContactInfoModel>>> getContactInfo() {
        return mAPIService.getContactInfo(token);
    }

    /**
     * 获得兑换结果
     *
     * @return
     */
    public Observable<BaseModel<ExchangeModel>> exchangePool(String exchangeId) {
        return mAPIService.exchangePool(exchangeId);
    }

    /**
     * 获得兑换结果
     *
     * @return
     */
    public Observable<BaseModel<PrizeAddressModel>> insertContactInfo(String name, String phoneNum, String fullAddress, String locationArea, String drawId, String orderNum, String id) {
        return mAPIService.insertContactInfo(name, phoneNum, fullAddress, locationArea, drawId, orderNum, id);
    }

    /**
     * 添加积分记录
     *
     * @return
     */
    public Observable<BaseModel<JSONObject>> insertIRecord(String classId, String type) {
        return mAPIService.insertIRecord(classId, type);
    }

    /**
     * 获得积分订单记录
     *
     * @return
     */
    public Observable<BaseModel<List<PrizeOrderDetailModel>>> getOrderList() {
        return mAPIService.getOrderList(token);
    }

    /**
     * 确认收货
     *
     * @param orderId
     * @return
     */
    public Observable<BaseModel<JSONObject>> sureGetThings(String orderId) {
        return mAPIService.sureGetThings(orderId);
    }

    /**
     * 删除家长发件箱
     * @param messageIds
     * @return
     */
    public Observable<BaseModel<JSONObject>> deleteAllSendMessageOfUser(String messageIds){
        return mAPIService.deleteAllSendMessageOfUser(messageIds,token);
    }

    /**
     * 删除家长收件箱
     * @param messageIds
     * @return
     */
    public Observable<BaseModel<JSONObject>> deleteAllReceiveMessageOfUser(String messageIds,String subscriberId){
        return mAPIService.deleteAllReceiveMessageOfUser(messageIds,token,subscriberId);
    }

    /**
     * 删除教师发件箱
     * @param messageIds
     * @return
     */
    public Observable<BaseModel<JSONObject>> deleteBatchSendMessageOfTeacher(String messageIds){
        return mAPIService.deleteBatchSendMessageOfTeacher(messageIds,token);
    }

    /**
     * 删除教师收件箱
     * @param messageIds
     * @return
     */
    public Observable<BaseModel<JSONObject>> deleteBatchReceiveMessageOfTeacher(String messageIds,String subscriberId){
        return mAPIService.deleteBatchReceiveMessageOfTeacher(messageIds,token,subscriberId);
    }

    /**
     * 日程获取月列表
     * @return
     */
    public Observable<BaseModel<JSONArray>> selectSchedule(String year,String month){
        return mAPIService.selectSchedule(token,year,month);
    }

    /**
     * 获取某天日程具体安排
     * @return
     */
    public Observable<BaseModel<JSONArray>> selectScheduleDetailsByDay(String time){
        return mAPIService.selectScheduleDetailsByDay(token,time);
    }

    /**
     * @param userId
     * @param phoneNum
     * @param appVersion
     * @param equipmentVersion
     * @param uType            1：家长  2：老师
     * @return
     */
    public Observable<BaseModel<JSONObject>> insertEquipment(String userId, String phoneNum, String appVersion, String equipmentVersion,int uType) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userId",userId);
        jsonObject.addProperty("phoneNum",phoneNum);
        jsonObject.addProperty("appVersion",appVersion);
        jsonObject.addProperty("equipmentVersion",equipmentVersion);
        jsonObject.addProperty("uType",uType); //uType  1：家长 2：老师
        jsonObject.addProperty("type",1); //1：安卓  2：IOS
        return mAPIService.insertEquipment(token,jsonObject.toString());
    }

    // uType 2：老师
    public Observable<BaseModel<JSONObject>> insertEquipmentTeacher(String userId, String phoneNum, String appVersion, String equipmentVersion) {
        return insertEquipment(userId, phoneNum, appVersion, equipmentVersion, 2);
    }

    //uType  1：家长
    public Observable<BaseModel<JSONObject>> insertEquipmentUser(String userId, String phoneNum, String appVersion, String equipmentVersion) {
        return insertEquipment(userId, phoneNum, appVersion, equipmentVersion, 1);
    }
    /**
     * 2.49 创建相册 新接口V3.0 /indexCommon/insertAlbum
     */
    public Observable<BaseModel<JSONObject>> insertAlbum(String userId, String babyId, String title, String content, String imgNames, String existCommonId, String isPublic) {
        return mAPIService.insertAlbum(token, userId, babyId, title, content, imgNames, existCommonId, isPublic);
    }

    public Observable<BaseModel<JSONObject>> insertVideoWithPublic(String userId, String babyId, String content, String firstFrameName, String videoName,String isPublic) {
        return mAPIService.insertVideoWithPublic(token, userId, babyId, content, firstFrameName, videoName,isPublic);
    }

    /**
     * 2.50 接口功能：相册获取列表相册名的接口
     *
     * @return
     */
    public Observable<BaseModel<JSONObject>> selectAlbumList(String userId, String babyId) {
        return mAPIService.selectAlbumList(token, userId, babyId);
    }

    /**
     * 获取咨询发现列表
     * @return
     */
    public Observable<BaseModel<List<InfomationModel>>> getTinfoDiscover(String state,int pageNum) {
        return mAPIService.getTinfoDiscover(token,state,pageNum);
    }

    /**
     * 获取咨询关注列表
     * @return
     */
    public Observable<BaseModel<List<InfomationModel>>> getTinfoCared(String state,int pageNum) {
        return mAPIService.getTinfoCared(token,state,pageNum);
    }

    /**
     * 获取咨询个人发布的信息
     * @return
     */
    public Observable<BaseModel<InfoPersonMsgModel>> getUserInfoMsg(String userId,int pageNum,String state,String creatorId) {
        return mAPIService.getUserInfoMsg(userId,pageNum,state,creatorId);
    }

    /**
     * 获取咨询我关注的人
     * @return
     */
    public Observable<BaseModel<List<CareOrCaredModel>>> getMyCaredPersons() {
        return mAPIService.getMyCaredPersons(token);
    }

    /**
     * 获取咨询关注我的人
     * @return
     */
    public Observable<BaseModel<List<CareOrCaredModel>>> getCaredMePersons() {
        return mAPIService.getCaredMePersons(token);
    }

    /**
     * 添加关注的人
     * @return
     */
    public Observable<BaseModel<Integer>> addCarePerson(String fromState,String toState,String toUserId) {
        return mAPIService.addCarePerson(token,fromState,toState,toUserId);
    }

    /**
     * 取消关注的人
     * @return
     */
    public Observable<BaseModel<Integer>> deleteCarePerson(String relationId) {
        return mAPIService.deleteCarePerson(relationId);
    }

    /**
     * 搜索数据
     * @return
     */
    public Observable<BaseModel<List<InfomationModel>>> searchInfoMsg(String condition) {
        return mAPIService.searchInfoMsg(condition,token);
    }

    /**
     * 点赞
     * @return
     */
    public Observable<BaseModel<JSONObject>> addLikeInfo(String topicType ,String topicId) {
        return mAPIService.addLikeInfo(token,topicType,topicId);
    }

    /**
     * 获得评论
     * @return
     */
    public Observable<BaseModel<List<CommentModel>>> getCommentList(String topicType, String topicId) {
        return mAPIService.getCommentList(token,topicType,topicId);
    }

    /**
     * 添加评论
     * @return
     */
    public Observable<BaseModel<String>> addComment(String topicType, String topicId, String commentContent, String toReplyUid,
                                                                String toReplyUtype, String commentUtype) {
        return mAPIService.addComment(token,topicType,topicId,commentContent,toReplyUid,toReplyUtype,commentUtype);
    }

    /**
     * 删除评论
     * @return
     */
    public Observable<BaseModel<String>> deleteComment(String tInfoCommId,String topicId,String topicType) {
        return mAPIService.deleteComment(token,tInfoCommId,topicId,topicType);
    }

    /**
     * 删除咨询
     * @return
     */
    public Observable<BaseModel<String>> deleteAlbum(String subAlbumId) {
        return mAPIService.deleteAlbum(subAlbumId);
    }

    /**
     * 通过id查询咨询详情
     * @return
     */
    public Observable<BaseModel<SelectInfoDetailModel>> selectInfoDetails(String topicId, String topicType) {
        return mAPIService.selectInfoDetails(topicId,topicType,token);
    }

    /**
     * 课堂助手 列表，包含搜索
     * @param title
     * @param type //类型  1：健康  2：科学  3：社会 4：语言  5：艺术
     * @param pageSize
     * @param pageNum
     * @return
     */
    private Observable<BaseModel<JSONObject>> classroomAssistList(String title, int type, int pageSize, int pageNum) {
        return mAPIService.classroomAssistList(token, title, type,pageSize,pageNum);
    }

    /**
     * 课堂助手 列表
     * @param type  1：健康  2：科学  3：社会 4：语言  5：艺术
     * @param pageNum  页码
     */
    public Observable<BaseModel<JSONObject>> classroomAssistList(int type,int pageNum) {
        return classroomAssistList("", type,10,pageNum);
    }

    /**
     * 课堂助手 搜索
     * @param title  1：健康  2：科学  3：社会 4：语言  5：艺术
     * @param pageNum  页码
     */
    public Observable<BaseModel<JSONObject>> classroomAssistSearchList(String title ,int pageNum) {
        return classroomAssistList(title,0 ,10,pageNum);
    }

    /**
     获取资讯是否有新的评论或回复（红点）
     */
    public Observable<BaseModel<String>> selectInfoIsUpdate() {
        return mAPIService.selectInfoIsUpdate(token);
    }

    /**
     获取资讯新的评论或回复
     */
    public Observable<BaseModel<List<BaseInfoHintModel>>> selectInfoUpdateList() {
        return mAPIService.selectInfoUpdateList(token);
    }

    /**
     获取资讯旧的评论或回复
     */
    public Observable<BaseModel<BaseInfoHintOldModel>> selectCommList(String pageNum) {
        return mAPIService.selectCommList(token,pageNum);
    }

    /**
     * 教师打卡 获取教师信息
     */
    public Observable<BaseModel<AttendenceTeacherModel>> teacherInfoByQrcode(String schoolId, String teacherId, String qrcode, int eventType){
        return mAPIService.teacherInfoByQrcode(schoolId,teacherId,qrcode,eventType);
    }

    /**
     * 教师打卡 获取教师信息
     */
    public Observable<BaseModel<AttendenceHistoryModel>> selectTeacherAttendanceList(int pageNum){
        return mAPIService.selectTeacherAttendanceList(token,pageNum);
    }

    /**
     * @param startTime  请假开始时间
     * @param endTime   请假结束时间
     * @param type      请假类型  1 病假 2 事假
     * @param reason    请假原因
     * @return
     */
    public Observable<BaseModel<String>> insertLeaveTeacher(long startTime, long endTime,int type, String reason){
        return mAPIService.insertLeaveTeacher(startTime, endTime, type, reason);
    }

    /**
     * 教师请假历史
     * @return
     */
    public Observable<BaseModel<List<AskForLeaveHistoryModel>>> selectLeave(){
        return mAPIService.selectLeave();
    }

    /**
     * 获取教师的历史考勤记录  包括了考勤和请假记录  2017/11/24
     * @return
     */
    public Observable<BaseModel<AttendenceLeaveHistoryModel>> selectHistoryAttendance(){
        return mAPIService.selectHistoryAttendance();
    }
    /**
     * 校长获取请假审批数据
     * @return
     */
    public Observable<BaseModel<List<AskForLeaveModel>>> selectApprovalList(){
        return mAPIService.selectApprovalList();
    }

    /**
     * 校长审批是否同意
     * 状态 0:待批准 1：已批准 2：已拒绝"
     * @return
     */
    public Observable<BaseModel<String>> updateLeaveState(int leaveId,int state){
        return mAPIService.updateLeaveState(leaveId,state);
    }

    /**
     * 通过学校id查询全校的考勤记录
     * @param schoolId
     * @return
     */
    public Observable<BaseModel<SchoolMasterModel>> selectLeaveList(int schoolId){
        return mAPIService.selectLeaveList(schoolId);
    }

    /**
     * 作者：ShuWen
     * 日期：2017/12/12  14:55
     * 描述：校长考勤界面请假审批，查看是否有新的请假申请
     *
     * @return [返回类型说明]
     */
    public Observable<BaseModel<Integer>> selectTeacherLeaveAlert(int schoolId){
        return mAPIService.selectTeacherLeaveAlert(schoolId);
    }

    /**
     * 作者：ShuWen
     * 日期：2017/12/28  16:58
     * 描述：校长获取全校的年纪和班级
     *
     * @return [返回类型说明]
     */
    public Observable<BaseModel<JSONObject>> selectClassAndGrade(int schoolId){
        return mAPIService.selectClassAndGrade(schoolId);
    }

    /**
     * 作者：ShuWen
     * 日期：2018/1/3  11:09
     * 描述：
     *
     * @return [返回类型说明]
     */
    public Observable<BaseModel<JSONObject>> selectEventBySchoolAdminPage(String classIds,String schoolId, String type, int pageNum){
        LogZS.i("requestClassIds:"+classIds);
        return mAPIService.selectEventBySchoolAdminPage( classIds,schoolId, type, pageNum);
    }


    /**
     * @param url      下载地址
     * @param fileName 存储的文件名
     * @return
     */
    public Observable<String> downloadFile(String url, final String fileName) {
        return Observable.just(url).flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String downloadUrl) {
                try {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();

                    builder.connectTimeout(3000, TimeUnit.MILLISECONDS);
                    builder.readTimeout(3000, TimeUnit.MILLISECONDS);
                    builder.writeTimeout(3000, TimeUnit.MILLISECONDS);
                    OkHttpClient mOkHttpClient = builder.build();
                    Request request = new Request.Builder().url(downloadUrl).tag(this).build();
                    Response response = mOkHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        InputStream is = response.body().byteStream();
                        byte[] buf = new byte[2048];
                        int len = 0;
                        FileOutputStream fos = null;
                        try {
                            is = response.body().byteStream();
                            File file = new File(fileName);
                            if (!file.getParentFile().exists()){
                                file.getParentFile().mkdirs();
                            }
                            fos = new FileOutputStream(file);
                            while ((len = is.read(buf)) != -1) {
                                fos.write(buf, 0, len);
                            }
                            fos.flush();
                            return Observable.just(file.getAbsolutePath());
                        } finally {
                            if (is != null) is.close();
                            if (fos != null) fos.close();
                        }

                    } else {
                        return null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

}
