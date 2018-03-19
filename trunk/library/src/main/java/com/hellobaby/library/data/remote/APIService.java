package com.hellobaby.library.data.remote;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zwj on 2016/9/27.
 * description :
 */

public interface APIService {

    /**
     * 1.1 获取最新版本
     *
     * @param type
     * @return
     */
    @GET("appversion/get")
    Observable<BaseModel<AppVersionModel>> appVersionGet(@Query("type") String type);

    /**
     * 1.1 家长登录
     *
     * @param phoneNum
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("checkLogin")
    Observable<BaseModel<String>> checkLoginUser(@Field("phoneNum") String phoneNum, @Field("password") String password);

    /**
     * 1.3 接口功能：家长端注册
     *
     * @param phoneNum
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("subRegister")
    Observable<BaseModel<String>> subRegister(@Field("phoneNum") String phoneNum, @Field("password") String password, @Field("smsCode") String smsCode, @Field("userName") String userName);

    /**
     * 1.4 家长端获取短信验证码
     *
     * @param phoneNum
     * @return
     */
    @FormUrlEncoded
    @POST("checkUserExits")
    Observable<BaseModel<String>> checkUserExits(@Field("phoneNum") String phoneNum);

    /**
     * 接口功能：家长端（更换手机号码）获取短信验证码
     *
     * @param phoneNum
     * @return
     */
    @FormUrlEncoded
    @POST("changePhoneCode")
    Observable<BaseModel<String>> changePhoneCode(@Field("phoneNum") String phoneNum);

    /**
     * 接口功能：教师端（更换手机号码）获取短信验证码
     *
     * @param phoneNum
     * @return
     */
    @FormUrlEncoded
    @POST("tTeacher/changePhoneCode")
    Observable<BaseModel<String>> tChangePhoneCode(@Field("phoneNum") String phoneNum);

    /**
     * 接口功能：家长端（更换手机号码）修改手机号
     *
     * @param phoneNum
     * @return
     */
    @FormUrlEncoded
    @POST("smsCodeBasedPhoneNumChange")
    Observable<BaseModel<String>> smsCodeBasedPhoneNumChange(@Field("token") String token, @Field("phoneNum") String phoneNum, @Field("smsCode") String smsCode, @Field("password") String newPassword);

    /**
     * 接口功能：教师端端（更换手机号码）修改手机号
     *
     * @param phoneNum
     * @return
     */
    @FormUrlEncoded
    @POST("tTeacher/smsCodeBasedPhoneNumChange")
    Observable<BaseModel<String>> tsmsCodeBasedPhoneNumChange(@Field("token") String token, @Field("phoneNum") String phoneNum, @Field("smsCode") String smsCode, @Field("password") String newPassword);

    /**
     * 1.6 接口功能：家长端（忘记密码 & 修改密码）获取短信验证码
     *
     * @param phoneNum
     * @return
     */
    @FormUrlEncoded
    @POST("checkPhoneExists")
    Observable<BaseModel<String>> checkPhoneExists(@Field("phoneNum") String phoneNum);

    /**
     * 1.5接口功能：家长端 忘记&修改
     *
     * @param phoneNum
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("smsCodeBasedPasswordChange")
    Observable<BaseModel<String>> smsCodeBasedPasswordChange(@Field("phoneNum") String phoneNum, @Field("newPassword") String password, @Field("smsCode") String smsCode);


    /**
     * 9.6 接口功能：家长端（忘记密码 & 修改密码）获取短信验证码
     *
     * @param phoneNum
     * @return
     */
    @FormUrlEncoded
    @POST("tTeacher/checkPhoneExists")
    Observable<BaseModel<String>> teacherCheckPhoneExists(@Field("phoneNum") String phoneNum);

    /**
     * 9.5 接口功能：家长端 忘记&修改
     *
     * @param phoneNum
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("tTeacher/smsCodeBasedPasswordChange")
    Observable<BaseModel<String>> teacherChangeTeacherPassword(@Field("phoneNum") String phoneNum, @Field("newPassword") String password, @Field("smsCode") String smsCode);


    /**
     * 家长根据token获取userId
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("checkToken")
    Observable<BaseModel<String>> checkToken(@Field("token") String token);

    /**
     * 1.2.1 教师端登录
     *
     * @param phoneNum
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("tTeacher/checkTeacherLogin")
    Observable<BaseModel<String>> checkTeacherLogin(@Field("phoneNum") String phoneNum, @Field("password") String password);


    /**
     * 1.2 教师端登录
     *
     * @param phoneNum
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("tTeacher/checkTeacherLoginRole")
    Observable<BaseModel<TeacherTokenModel>> checkTeacherLoginRole(@Field("phoneNum") String phoneNum, @Field("password") String password);

    /**
     * 1.13 接口功能：教师端根据Token找到teacherId
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("tTeacher/checkTeacherToken")
    Observable<BaseModel<String>> checkTeacherToken(@Field("token") String token);


    /**
     * 1.9 接口功能：家长端关注宝宝
     *
     * @param token
     * @param userId
     * @param babyId
     * @return
     */
    @FormUrlEncoded
    @POST("userbaby/insertCareBaby")
    Observable<BaseModel<String>> insertCareBaby(@Field("token") String token, @Field("userId") String userId, @Field("babyId") String babyId);


    /**
     * 2.1 接口功能：家长宝贝列表
     *
     * @param token
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("userbaby/selectTUserbabyById")
    Observable<BaseModel<List<BabyModel>>> selectTUserbabyById(@Field("token") String token, @Field("userId") String userId);

    @FormUrlEncoded
    @POST("indexCommon/insertSelective")
    Observable<BaseModel<AlbumModel>> albumCreate(@Field("token") String token, @Field("userId") String userId, @Field("babyId") String babyId, @Field("title") String title, @Field("content") String content);

    /**
     * 2.5 相册上传图片
     *
     * @param token
     * @param commonId
     * @param file1
     * @return
     */
    @Multipart
    @POST("indexCommon/uploadFile")
    Observable<BaseModel<AlbumModel>> albumUploadImg(@Part("token") RequestBody token, @Part("commonId") RequestBody commonId, @Part("file\";filename=\"image.jpg") RequestBody file1);

    /**
     * 2.3 家长端，取相册图片列表
     *
     * @param token
     * @param commonId
     * @return
     */
    @FormUrlEncoded
    @POST("indexCommon/getImgsByCommonId")
    Observable<BaseModel<AlbumModel>> albumGetImgs(@Field("token") String token, @Field("commonId") String commonId);


    /**
     * 2.4接口功能：家长端，相册删除某张图片(单张&批量)
     *
     * @param token
     * @param imageIds   图片id的数组 1,2,3 逗号区分
     * @param imageNames 图片名字的数组 1.jpg,2.jpg,3.jpg
     * @return
     */
    @FormUrlEncoded
    @POST("indexCommon/deleteImgsByIds")
    Observable<BaseModel<String>> albumDelImgs(@Field("token") String token, @Field("imageIds") String imageIds, @Field("imageNames") String imageNames);

    //    2.41 接口功能：家长端，相册新建（发布中的功能,先建一个相册，再执行单张上传的接口，回到首页后重新调用首页接口）（/indexCommon/insertAlbumAndImgs）
    @Deprecated
    @FormUrlEncoded
    @POST("indexCommon/insertAlbumAndImgs")
    Observable<BaseModel<JSONObject>> insertAlbumAndImgs(@Field("token") String token, @Field("userId") String userId, @Field("babyId") String babyId, @Field("title") String title, @Field("content") String content, @Field("imgNames") String imgNames);

    //   2.49 创建相册 新接口V3.0 /indexCommon/insertAlbum
    @FormUrlEncoded
    @POST("indexCommon/insertAlbum")
    Observable<BaseModel<JSONObject>> insertAlbum(@Field("token") String token, @Field("userId") String userId, @Field("babyId") String babyId, @Field("title") String title, @Field("content") String content, @Field("imgNames") String imgNames, @Field("existCommonId") String existCommonId, @Field("isPublic") String isPublic);


    //    2.42 接口功能：家长端，上传图片（/indexCommon/uploadIndexFile）
    @FormUrlEncoded
    @POST("indexCommon/uploadIndexFile")
    Observable<BaseModel<JSONObject>> uploadIndexFile(@Field("token") String token, @Field("commonId") String commonId, @Field("imgNames") String imgNames);

    //    7.4 接口功能：教师端，动态-上传照片 (tDynamic/uploadDynamicFiles)
    @FormUrlEncoded
    @POST("tDynamic/uploadDynamicFiles")
    Observable<BaseModel<JSONObject>> uploadDynamicFiles(@Field("token") String token, @Field("teacherId") String teacherId, @Field("description") String description, @Field("classIds") String classIds, @Field("imgNames") String imgNames);

    /**
     * 2.22接口功能：日志\相册内容修改
     *
     * @param token
     * @param commonId
     * @param title
     * @param content
     * @return
     */
    @FormUrlEncoded
    @POST("indexCommon/updateLogOrAlbum")
    Observable<BaseModel<AlbumModel>> albumUpdateContent(@Field("token") String token, @Field("commonId") String commonId, @Field("title") String title, @Field("content") String content);


    /**
     * 2.7 接口功能：家长端，日志新建
     *
     * @param token
     * @param userId
     * @param babyId
     * @param title
     * @param content
     * @return
     */
    @FormUrlEncoded
    @POST("indexCommon/insertLog")
    Observable<BaseModel<AlbumModel>> logCreate(@Field("token") String token,
                                                @Field("userId") String userId,
                                                @Field("babyId") String babyId,
                                                @Field("title") String title,
                                                @Field("content") String content);

    /**
     * 2.8接口功能：家长端，日志删除
     *
     * @param token
     * @param commonId
     * @return
     */
    @FormUrlEncoded
    @POST("indexCommon/deleteLogById")
    Observable<BaseModel<AlbumModel>> logDel(@Field("token") String token,
                                             @Field("commonId") String commonId);


    /**
     * 2.9 接口功能：家长端，小视频新建-第一帧图片先上传(indexCommon/uploadFirstFrame)
     *
     * @param token
     * @param userId  登录用户的id
     * @param babyId  当前宝宝的id
     * @param content 内容
     * @param file    文件
     * @return
     */
    @Deprecated
    @Multipart
    @POST("indexCommon/uploadFirstFrame")
    Observable<BaseModel<AlbumModel>> videoFirstFrameImg(@Part("token") RequestBody token,
                                                         @Part("userId") RequestBody userId,
                                                         @Part("babyId") RequestBody babyId,
                                                         @Part("content") RequestBody content,
                                                         @Part("file\";filename=\"image.jpg") RequestBody file);

    /**
     * 2.10 接口功能：家长端，小视频新建-上传小视频(indexCommon/uploadSmallVideo)
     *
     * @param token
     * @param videoId
     * @param videoName
     * @param file
     * @return
     * @Deprecated 被insertFirstFrameAndVideo 替换了
     */
    @Deprecated
    @Multipart
    @POST("indexCommon/uploadSmallVideo")
    Observable<BaseModel<AlbumModel>> videoUpload(@Part("token") RequestBody token,
                                                  @Part("videoId") RequestBody videoId,
                                                  @Part("videoName") RequestBody videoName,
                                                  @Part("file\";filename=\"video.mp4") RequestBody file);

    /**
     * 2.43 接口功能：小视频- 插入t_indexcommon和t_video表中
     *
     * @param token
     * @param babyId
     * @param content
     * @param firstFrameName
     * @param videoName
     * @return
     */
    @Deprecated
    @FormUrlEncoded
    @POST("indexCommon/insertFirstFrameAndVideo")
    Observable<BaseModel<JSONObject>> insertFirstFrameAndVideo(@Field("token") String token,
                                                               @Field("userId") String userId,
                                                               @Field("babyId") String babyId,
                                                               @Field("content") String content,
                                                               @Field("firstFrameName") String firstFrameName,
                                                               @Field("videoName") String videoName);

    /**
     * 2.51 小视频新接口V3.0- 插入t_indexcommon和t_video表中
     *
     * @param token
     * @param babyId
     * @param content
     * @param firstFrameName
     * @param videoName
     * @return
     */
    @FormUrlEncoded
    @POST("indexCommon/insertVideoWithPublic")
    Observable<BaseModel<JSONObject>> insertVideoWithPublic(@Field("token") String token,
                                                               @Field("userId") String userId,
                                                               @Field("babyId") String babyId,
                                                               @Field("content") String content,
                                                               @Field("firstFrameName") String firstFrameName,
                                                               @Field("videoName") String videoName,
                                                            @Field("isPublic") String isPublic);

    /**
     * 2.44 接口功能：通知服务器去下载
     */
    @FormUrlEncoded
    @POST("indexCommon/downloadSmallVideo")
    Observable<BaseModel<JSONObject>> downloadSmallVideo(@Field("token") String token,
                                                         @Field("fileName") String fileName);

    /**
     * 2.44 接口功能：通知服务器去下载
     */
    @FormUrlEncoded
    @POST("tDynamic/downloadDynamicSmallVideo")
    Observable<BaseModel<JSONObject>> downloadDynamicSmallVideo(@Field("token") String token,
                                                                @Field("fileName") String fileName);

    /**
     * 2.47 接口功能：动态小视频- 插入t_dynamic和t_video表中
     *
     * @param token
     * @param firstFrameName
     * @param videoName
     * @return
     */
    @FormUrlEncoded
    @POST("tDynamic/insertDynamicFirstFrameAndVideo")
    Observable<BaseModel<JSONObject>> insertDynamicFirstFrameAndVideo(@Field("token") String token,
                                                                      @Field("teacherId") String teacherId,
                                                                      @Field("description") String description,
                                                                      @Field("classIds") String classIds,
                                                                      @Field("firstFrameName") String firstFrameName,
                                                                      @Field("videoName") String videoName);

    /**
     * 2.11 接口功能：家长端，小视频删除
     *
     * @param token
     * @param commonId
     * @return
     */
    @FormUrlEncoded
    @POST("indexCommon/deleteVideoById")
    Observable<BaseModel<AlbumModel>> videoDel(@Field("token") String token,
                                               @Field("commonId") String commonId);

    /**
     * 2.30接口功能：家长端，相册删除
     *
     * @param token
     * @param commonId
     * @return
     */
    @FormUrlEncoded
    @POST("indexCommon/deleteAlbumById")
    Observable<BaseModel<String>> deleteAlbumById(@Field("token") String token,
                                                  @Field("commonId") String commonId);

    /**
     * 5.2 修改宝宝的信息
     *
     * @param token
     * @param baby
     * @return
     */
    @FormUrlEncoded
    @POST("tbaby/updateByBabyId")
    Observable<BaseModel<BabyModel>> babyUpdateInfo(@Field("token") String token,
                                                    @Field("baby") String baby);

    /**
     * 5.6 接口功能：家长端，修改家长信息
     *
     * @param token
     * @param babyId
     * @param userId
     * @param userName
     * @param relation
     * @param userEmail
     * @return
     */
    @FormUrlEncoded
    @POST("userbaby/updateUserInfo")
    Observable<BaseModel<UserModel>> updateUserInfo(@Field("token") String token
            , @Field("babyId") String babyId
            , @Field("userId") String userId
            , @Field("userName") String userName
            , @Field("relation") String relation
            , @Field("userEmail") String userEmail
    );


    /**
     * 2.29 接口功能：家长端，修改家长头像
     *
     * @param token
     * @param userId
     * @param file
     * @return
     */
    @Multipart
    @POST("user/changeHeadImgById")
    Observable<BaseModel<String>> changeHeadImgById(@Part("token") RequestBody token,
                                                    @Part("userId") RequestBody userId,
                                                    @Part("file\";filename=\"image.jpg") RequestBody file);

    /**
     * 5.17 接口功能：家长端，获取个人信息
     *
     * @param token
     * @return
     */
    @Deprecated
    @FormUrlEncoded
    @POST("user/selectUserByToken")
    Observable<BaseModel<UserModel>> selectUserByToken(@Field("token") String token);

    /**
     * 5.17 接口功能：家长端，获取个人信息
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("user/selectUserById")
    Observable<BaseModel<UserModel>> selectUserById(@Field("userId") String userId);

    /**
     * 5.17 接口功能：家长端，获取个人信息(带联系人)
     *
     * @param userId
     * @param babyId
     * @return
     */
    @FormUrlEncoded
    @POST("user/selectUserExtendByUBId")
    Observable<BaseModel<UserModel>> selectUserExtendByUBId(@Field("token") String token, @Field("userId") String userId, @Field("babyId") String babyId);

    /**
     * 5.7接口功能：家长端，通讯录-是否公开个人手机号
     *
     * @param token
     * @param userId
     * @param isPublic 是否公开 0：是 ；1：否
     * @return
     */
    @FormUrlEncoded
    @POST("user/changePublic")
    Observable<BaseModel<String>> changePublic(@Field("token") String token, @Field("userId") String userId, @Field("isPublic") String isPublic);

    /**
     * 6.1 接口功能：教师端-获取所在幼儿园信息
     *
     * @param token
     * @param teacherId
     * @return
     */
    @FormUrlEncoded
    @POST("tschool/selectSchoolByTeacherId")
    Observable<BaseModel<SchoolModel>> selectSchoolByTeacherId(@Field("token") String token, @Field("teacherId") String teacherId);

    /**
     * 6.2 接口功能：教师端-获取所班级列表
     *
     * @param token
     * @param teacherId
     * @return
     */
    @FormUrlEncoded
    @POST("tclass/selectClassesByTeacherId")
    Observable<BaseModel<List<ClassModel>>> selectClassesByTeacherId(@Field("token") String token, @Field("teacherId") String teacherId);

    /**
     * 9.19 接口功能：教师端获取个人信息
     *
     * @param token
     * @return
     */
    @Deprecated
    @FormUrlEncoded
    @POST("tTeacher/selectTeacherByToken")
    Observable<BaseModel<TeacherModel>> selectTeacherByToken(@Field("token") String token);

    /**
     * 9.1 接口功能：教师端-老师个人信息
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("tTeacher/selectTeacherById")
    Observable<BaseModel<TeacherModel>> selectTeacherById(@Field("token") String token, @Field("teacherId") String teacherId);

    /**
     * 9.3 接口功能：教师端-老师个人信息-头像修改
     *
     * @param token
     * @return
     */
    @Multipart
    @POST("tTeacher/changeHeadImgById")
    Observable<BaseModel<String>> tChangeHeadImgById(@Part("token") RequestBody token,
                                                     @Part("teacherId") RequestBody teacherId,
                                                     @Part("file\";filename=\"image.jpg") RequestBody file);

    /**
     * 7.1 接口功能：教师端，动态-图片 一次全上传
     *
     * @param token
     * @return
     */
    @Multipart
    @POST("tDynamic/uploadFiles")
    Observable<BaseModel<String>> dynamicUploadFiles(@Part("token") RequestBody token
            , @Part("teacherId") RequestBody teacherId
            , @Part("description") RequestBody description
            , @Part("classIds") RequestBody classIds
            , @Part("files\";filename=\"image.jpg") RequestBody[] files
    );

    /**
     * 7.2 接口功能：教师端，动态-小视频 第一帧
     *
     * @param token
     * @param teacherId
     * @param description
     * @param classIds
     * @param files
     * @return
     */
    @Multipart
    @POST("tDynamic/uploadFirstFrame")
    Observable<BaseModel<JSONObject>> dynamicUploadFirstFrame(@Part("token") RequestBody token,
                                                              @Part("teacherId") RequestBody teacherId,
                                                              @Part("description") RequestBody description,
                                                              @Part("classIds") RequestBody classIds,
                                                              @Part("files\";filename=\"image.jpg") RequestBody[] files);

    /**
     * 7.3 接口功能：教师端，动态-小视频
     *
     * @param token
     * @param videoIds
     * @param videoNames
     * @param files
     * @return
     */
    @Multipart
    @POST("tDynamic/uploadSmallVideo")
    Observable<BaseModel<JSONObject>> dynamicUploadSmallVideo(@Part("token") RequestBody token,
                                                              @Part("videoIds") RequestBody videoIds,
                                                              @Part("videoNames") RequestBody videoNames,
                                                              @Part("files\";filename=\"video.mp4") RequestBody[] files);

    /**
     * 2.16 接口功能：家长端，宝宝评语
     *
     * @param token
     * @param babyId
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("Tevaluation/selectTevaluationAllBybabyid")
    Observable<BaseModel<ReviewModel>> selectTevaluationAllBybabyid(@Field("token") String token, @Field("babyId") String babyId, @Field("time") String time);

    //-------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 1.7 接口功能：添加宝宝
     *
     * @param token
     * @param baby     baby的字符串
     * @param phoneNum 手机号
     * @return
     */
    @FormUrlEncoded
    @POST("tbaby/insertTBaby")
    Observable<BaseModel<BabyModel>> createBaby(@Field("token") String token, @Field("userId") String userId, @Field("tBaby") String baby, @Field("phoneNum") String phoneNum);

    /**
     * @param token
     * @param userId
     * @param baby
     * @param phoneNum
     * @return
     */
    @FormUrlEncoded
    @POST("tbaby/insertAndUpdateTBaby")
    Observable<BaseModel<BabyModel>> insertAndUpdateTBaby(@Field("token") String token, @Field("userId") String userId, @Field("tBaby") String baby, @Field("phoneNum") String phoneNum);

    /**
     * 2.32 接口功能：家长端，关联幼儿园成功后设置邮件总人数+1
     *
     * @param token
     * @param classId
     * @return
     */
    @FormUrlEncoded
    @POST("tMessage/setMessageTotalNum")
    Observable<BaseModel<String>> setMessageTotalNum(@Field("token") String token, @Field("classId") String classId);


    /**
     * 5.9家长端，查询通讯录-baby所在班级老师通讯录
     *
     * @param token
     * @param babyId
     * @return
     */
    @FormUrlEncoded
    @POST("PhoneList/selectTeacherphonesfrombaby")
    Observable<BaseModel<List<TeacherModel>>> selectTeacherphonesfrombaby(@Field("token") String token, @Field("babyId") String babyId);

    /**
     * 2.2接口功能：家长一个宝贝的首页信息
     *
     * @param token
     * @param babyId
     * @return
     */
    @FormUrlEncoded
    @POST("indexCommon/getIndexCommon")
    Observable<BaseModel<JSONObject>> getIndexCommon(@Field("token") String token, @Field("babyId") String babyId);

    /**
     * 5.10家长端 获取baby所在班级同学的通讯录
     *
     * @param token
     * @param babyId
     * @return
     */
    @FormUrlEncoded
    @POST("PhoneList/selectTBabyphonesfrombaby")
    Observable<BaseModel<List<BabyModel>>> selectTBabyphonesfrombaby(@Field("token") String token, @Field("babyId") String babyId);

    /**
     * 5.11 接口功能：家长端，通讯录-baby所在班级同学-关系人通讯录
     *
     * @param token
     * @param babyId
     * @return
     */
    @FormUrlEncoded
    @POST("PhoneList/selectTBabysphonesfrombaby")
    Observable<BaseModel<List<UserModel>>> selectTBabysphonesfrombaby(@Field("token") String token, @Field("babyId") String babyId);

    /**
     * 9.12 接口功能：教师端，通讯录-登录老师所在班级的所有宝宝名称列表
     *
     * @param token
     * @param teacherId
     * @return
     */
    @FormUrlEncoded
    @POST("PhoneList/selectTBabysphonesfromteacher")
    Observable<BaseModel<List<BabyModel>>> selectTBabysphonesfromteacher(@Field("token") String token, @Field("teacherId") String teacherId);


    /**
     * 9.14 接口功能：教师端，通讯录-全校通讯录
     *
     * @param token
     * @param schoolId 学校的id
     * @return
     */
    @FormUrlEncoded
    @POST("PhoneList/selectTBabysphonesAll")
    Observable<BaseModel<List<TeacherModel>>> selectTBabysphonesAll(@Field("token") String token, @Field("schoolId") String schoolId);

    /**
     * 1.8 接口功能：家长端修改宝宝头像
     *
     * @param token
     * @param babyId
     * @param file
     * @return
     */
    @Multipart
    @POST("tbaby/upload-file")
    Observable<BaseModel<BabyModel>> babyUploadHeadImg(@Part("token") RequestBody token, @Part("babyId") RequestBody babyId, @Part("file\";filename=\"image.jpg") RequestBody file);

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
    @FormUrlEncoded
    @POST("height/selectLatestHeight")
    Observable<BaseModel<JSONObject>> selectLatestHeight(@Field("token") String token, @Field("babyId") String babyId);

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
    @FormUrlEncoded
    @POST("height/initHeight")
    Observable<BaseModel<JSONObject>> initHeight(@Field("userId") String userId, @Field("babyId") String babyId, @Field("birthHeight") String birthHeight,
                                                 @Field("nowHeight") String nowHeight, @Field("birthDate") String birthDate, @Field("inputDate") String inputDate, @Field("token") String token);

    /**
     * 2.23接口功能：家长端，身高添加(height/insertHeight)
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
    @FormUrlEncoded
    @POST("height/insertHeight")
    Observable<BaseModel<JSONObject>> insertHeight(@Field("userId") String userId, @Field("babyId") String babyId,
                                                   @Field("nowHeight") String nowHeight, @Field("inputDate") String inputDate, @Field("token") String token);

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
    @FormUrlEncoded
    @POST("height/selectHisHeight")
    Observable<BaseModel<JSONArray>> selectHisHeight(@Field("babyId") String babyId, @Field("token") String token);

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
    @FormUrlEncoded
    @POST("height/updateHeightById")
    Observable<BaseModel<JSONObject>> updateHeightById(@Field("heightId") String heightId, @Field("nowHeight") String nowHeight, @Field("token") String token);

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
    @FormUrlEncoded
    @POST("height/deleteHeightById")
    Observable<BaseModel<JSONObject>> deleteHeightById(@Field("heightId") String heightId, @Field("token") String token);

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
    @FormUrlEncoded
    @POST("weight/initWeight")
    Observable<BaseModel<JSONObject>> initWeight(@Field("userId") String userId, @Field("babyId") String babyId, @Field("birthWeight") String birthWeight,
                                                 @Field("nowWeight") String nowWeight, @Field("birthDate") String birthDate, @Field("inputDate") String inputDate, @Field("token") String token);

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
    @FormUrlEncoded
    @POST("weight/insertWeight")
    Observable<BaseModel<JSONObject>> insertWeight(@Field("userId") String userId, @Field("babyId") String babyId,
                                                   @Field("nowWeight") String nowWeight, @Field("inputDate") String inputDate, @Field("token") String token);

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
    @FormUrlEncoded
    @POST("weight/selectHisWeight")
    Observable<BaseModel<JSONArray>> selectHisWeight(@Field("babyId") String babyId, @Field("token") String token);

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
    @FormUrlEncoded
    @POST("weight/updateWeightById")
    Observable<BaseModel<JSONObject>> updateWeightById(@Field("weightId") String weightId, @Field("nowWeight") String nowWeight, @Field("token") String token);

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
    @FormUrlEncoded
    @POST("weight/deleteWeightById")
    Observable<BaseModel<JSONObject>> deleteWeightById(@Field("weightId") String weightId, @Field("token") String token);

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
    @FormUrlEncoded
    @POST("userbaby/selectCareUsers")
    Observable<BaseModel<List<UserModel>>> selectCareUsers(@Field("token") String token, @Field("babyId") String babyId);

    /**
     * 13.5 设置家庭成员有无权限接送孩子（userbaby/update/pick）
     * 上行：
     * userId:27
     * babyId:73
     * isPick:0|1  //0表示不能接送，1表示能接送
     * 下行：
     * {
     * "code": "200",
     * "msg": "ok",
     * "data": null
     * - }
     *
     * @param token
     * @param babyId
     * @return
     */
    @FormUrlEncoded
    @POST("userbaby/update/pick")
    Observable<BaseModel<String>> updatePick(@Field("token") String token, @Field("userId") String userId, @Field("babyId") String babyId, @Field("isPick") String isPick);

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
    @FormUrlEncoded
    @POST("tTeachingplan/selectTeachingplanByTeacherId")
    Observable<BaseModel<JSONArray>> selectTeachingplanByTeacherId(@Field("token") String token, @Field("teacherId") String teacherId);

    //    2.31接口功能：家长-教学计划-获取教学计划列表（tTeachingplan/selectTeachingplanFromUserId）
    //    classId:
    //    planningTime: 2017-01-01
    @FormUrlEncoded
    @POST("tTeachingplan/selectTeachingplanFromUserId")
    Observable<BaseModel<List<TeachingPlanModel>>> selectTeachingplanFromUserId(@Field("token") String token, @Field("classId") String classId, @Field("planningTime") String planningTime);

    /**
     * 2.38 接口功能：家长-教学计划-获取教学计划列表
     *
     * @param token
     * @param classId
     * @return
     */
    @FormUrlEncoded
    @POST("tTeachingplan/selectTeachingplan3FromUserId")
    Observable<BaseModel<List<TeachingPlanModel>>> selectTeachingplan3FromUserId(@Field("token") String token, @Field("classId") String classId);

    //    6.18 接口功能：教师端-教学计划-删除教学计划（tTeachingplan/deleteTeachingplanByTeacherId）
    @FormUrlEncoded
    @POST("tTeachingplan/deleteTeachingplanByTeacherId")
    Observable<BaseModel<JSONObject>> deleteTeachingplanByTeacherId(@Field("token") String token, @Field("teachingplanId") String teacherId);
//    /**
//     * 6.7 接口功能：教师端-教学计划-上传教学计划
//     *
//     * @param token
//     * @param teacherId
//     * @param classIds
//     * @param planningTime
//     * @param file1
//     * @return
//     */
//    @Multipart
//    @POST("tTeachingplan/insertTeachingplan")
//    Observable<BaseModel<JSONObject>> insertTeachingplan(@Part("token") RequestBody token, @Part("teacherId") RequestBody teacherId, @Part("classIds") RequestBody classIds, @Part("planningTime") RequestBody planningTime, @Part("file\";filename=\"image.jpg") RequestBody file1);
//向多个班级插入教学计划 6.7接口功能：教师端-教学计划-上传教学计划（一张图片）

    /**
     * 6.7 接口功能：教师端-教学计划-上传教学计划
     *
     * @param token
     * @param teacherId
     * @param classIds
     * @param planningTime
     * @param file1
     * @return
     */
    @Multipart
    @POST("tTeachingplan/insertTeachingplan")
    Observable<BaseModel<JSONObject>> insertTeachingplan(@Part("token") RequestBody token, @Part("teacherId") RequestBody teacherId, @Part("classIds") RequestBody classIds, @Part("planningTime") RequestBody planningTime, @Part("planningEndtime") RequestBody planningEndtime, @Part("file\";filename=\"image.jpg") RequestBody file1);

    //    8.3老师的活动创建   /tevet/insertEvents
//    int[] class_ids,       4,5,6  (必须是已经有的班级)
//    TEvent tEvent：
    @FormUrlEncoded
    @POST("tevet/insertEvents")
    Observable<BaseModel<JSONObject>> insertEvents(@Field("token") String token, @Field("tEvent") String event, @Field("classIds") String classIds);

    //    9.7老师端获取 老师关联班级活动列表   /tevet/selecteventsByteacher
    @FormUrlEncoded
    @POST("tevet/selecteventsByteacher")
    Observable<BaseModel<JSONArray>> selecteventsByteacher(@Field("token") String token, @Field("teacherId") String teacherId);

    //   8.1获取老师所在所在班级的所有活动(可能更新为校园内)          /tevet/selectEventOnTeacher
    @FormUrlEncoded
    @POST("tevet/selectEventOnTeacher")
    Observable<BaseModel<JSONArray>> selectEventOnTeacher(@Field("token") String token, @Field("teacherId") String teacherId, @Field("type") String type, @Field("schoolId") String schoolId);

    //   8.5获取校园列表大接口 （分页）        /tevet/selectEventOnTeacherPage
    @FormUrlEncoded
    @POST("tevet/selectEventOnTeacherPage")
    Observable<BaseModel<JSONObject>> selectEventOnTeacherPage(@Field("token") String token, @Field("teacherId") String teacherId, @Field("type") String type, @Field("schoolId") String schoolId, @Field("pageNum") int pageNum);

    //校长选择班级查看动态
    @FormUrlEncoded
    @POST("/tevet/selectEventBySchoolAdminPage")
    Observable<BaseModel<JSONObject>> selectEventBySchoolAdminPage(@Field("classIds")String classIds,@Field("schoolId")String schoolId,@Field("type") String type, @Field("pageNum") int pageNum);

    //   搜索校园      /tevet/selectEventOnTeacher
    @FormUrlEncoded
    @POST("tevet/selectEventOnTeacher")
    Observable<BaseModel<JSONArray>> selectEventOnTeacher(@Field("token") String token, @Field("teacherId") String teacherId, @Field("type") String type, @Field("schoolId") String schoolId, @Field("conditions") String conditions);

    //  5.15获得家长端 宝宝参加的活动列表   /tevet/selectEventIsJoin
    @FormUrlEncoded
    @POST("tevet/selectEventIsJoin")
    Observable<BaseModel<JSONArray>> selectEventIsJoin(@Field("token") String token, @Field("babyId") String babyId);

    //   3.2家长端获取活动详情     /tevet/selectEventObject
    @FormUrlEncoded
    @POST("tevet/selectEventObject")
    Observable<BaseModel<JSONObject>> selectEventObject(@Field("token") String token, @Field("babyId") String babyId, @Field("eventId") String eventId);

    //   8.2教师端获取活动详情    /tevet/selectEventObjectOnTeacher
    @FormUrlEncoded
    @POST("tevet/selectEventObjectOnTeacher")
    Observable<BaseModel<JSONObject>> selectEventObjectOnTeacher(@Field("token") String token, @Field("teacherId") String babyId, @Field("eventId") String eventId);

    // 3.3家长端 参加\取消参加  /tevet/isNotJoinEvent
    @FormUrlEncoded
    @POST("tevet/isNotJoinEvent")
    Observable<BaseModel<JSONArray>> isNotJoinEvent(@Field("token") String token, @Field("babyId") String babyId, @Field("eventId") String eventId, @Field("type") String type);

    //这里先这么写测一下
    // 3.3家长端 参加\取消参加  /tevet/isNotJoinEvent
    @FormUrlEncoded
    @POST("tevet/isNotJoinEventAddNum")
    Observable<BaseModel<JSONArray>> isNotJoinEventAddNum(@Field("token") String token, @Field("babyId") String babyId, @Field("eventId") String eventId, @Field("type") String type, @Field("babyNumber") String babyNumber, @Field("parentsNumber") String parentsNumber);

    // 3.1获取baby所在班级的所有活动   /tevet/selectEventByClassToOneBaby
    @FormUrlEncoded
    @POST("tevet/selectEventByClassToOneBaby")
    Observable<BaseModel<JSONArray>> selectEventByClassToOneBaby(@Field("token") String token, @Field("classId") String classId, @Field("babyId") String babyId, @Field("schoolId") String schoolId, @Field("type") String type);

    // 3.5接口功能：家长端，获取”全部”、”新闻”、”动态”、“食谱”、”活动”校园内容列表(分页)
    @FormUrlEncoded
    @POST("tevet/selectEventByClassToOneBabyPage")
    Observable<BaseModel<JSONObject>> selectEventByClassToOneBabyPage(@Field("token") String token, @Field("classId") String classId, @Field("babyId") String babyId, @Field("schoolId") String schoolId, @Field("type") String type, @Field("pageNum") int pageNum);

    // 校园搜索   /tevet/selectEventByClassToOneBaby
    @FormUrlEncoded
    @POST("tevet/selectEventByClassToOneBaby")
    Observable<BaseModel<JSONArray>> selectEventByClassToOneBaby(@Field("token") String token, @Field("classId") String classId, @Field("babyId") String babyId, @Field("schoolId") String schoolId, @Field("type") String type, @Field("conditions") String conditions);

    @Multipart
    @POST("tDynamic/uploadFirstFrame")
    Observable<BaseModel<AlbumModel>> teacherVideoFirstFrameImg(@Part("token") RequestBody token,
                                                                @Part("teacherId") RequestBody teacherId,
                                                                @Part("description") RequestBody description,
                                                                @Part("classIds") RequestBody classIds,
                                                                @Part("file\";filename=\"image.jpg") RequestBody file);

    //  9.2接口功能：教师端-老师个人信息-修改(tTeacher/updateTeacherInfo)
    @FormUrlEncoded
    @POST("tTeacher/updateTeacherInfo")
    Observable<BaseModel<JSONObject>> updateTeacherInfo(@Field("token") String token, @Field("tTeacher") String tTeacher);

    //  9.4接口功能：教师端-老师个人信息-是否公开个人信息(tTeacher/changePublic)
    @FormUrlEncoded
    @POST("tTeacher/changePublic")
    Observable<BaseModel<JSONObject>> teacherChangePublic(@Field("token") String token, @Field("teacherId") String teacherId, @Field("isPublic") String isPublic);

    //    6.5接口功能：教师端-班级考勤，上传(tBabyattendance/insertAttendance)
//    上行:
//    token：
//    babyattendances: [{babyId:1,teacherId:1,state:0},  {babyId:2,teacherId:1,state:1},  {babyId:3,teacherId:1,state:2},  {babyId:4,teacherId:1,state:2} ]
//
//    下行：
//    {
//        "code": "200",
//            "msg": "ok",
//            "data": null
//    }
//
    @FormUrlEncoded
    @POST("tBabyattendance/insertAttendance")
    Observable<BaseModel<JSONObject>> insertAttendance(@Field("token") String token, @Field("babyattendances") String babyattendances);

    @FormUrlEncoded
    @POST("tBabyattendance/insertAttendanceWithType")
    Observable<BaseModel<JSONObject>> insertAttendanceWithType(@Field("token") String token, @Field("babyattendances") String babyattendances);

    //    6.4接口功能：教师端-班级考勤，获取某个班级考勤列表（tBabyattendance/selectAttendanceByClassId）
//    上行:
//    classId：
//    searchTime：“2016-12-31”或者不传
//    token：
//    下行：
//    {
//        "code": "200",
//            "msg": "ok",
//            "data": {
//        "attendances": [
//        {
//            "classId": 1,
//                "className": "大班1",
//                "arrivalNum": 1,
//                "nonArrivalNum": 1,
//                "leaveNum": 1
//        },
//        {
//            "classId": 2,
//                "className": "大班2",
//                "arrivalNum": 1,
//                "nonArrivalNum": 1,
//                "leaveNum": 2
//        }
//        ],
//        "currentDate": "2017-01-11"
//    }
//    }
    @FormUrlEncoded
    @POST("tBabyattendance/selectAttendanceByClassId")
    Observable<BaseModel<JSONArray>> selectAttendanceByClassId(@Field("token") String token, @Field("classId") String classId, @Field("searchTime") String searchTime);

    @FormUrlEncoded
    @POST("tBabyattendance/selectAttendanceByClassIdAndType")
    Observable<BaseModel<JSONArray>> selectAttendanceByClassIdAndType(@Field("token") String token, @Field("classId") String classId, @Field("searchTime") String searchTime, @Field("type") String type);

    //6.21 接口功能：教师端-班级考勤，获取某个班级考勤列表（点名未完成时）（tBabyattendance/selectAttendanceByClassIdUnComplete）
    @FormUrlEncoded
    @POST("tBabyattendance/selectAttendanceByClassIdUnComplete")
    Observable<BaseModel<JSONArray>> selectAttendanceByClassIdUnComplete(@Field("token") String token, @Field("classId") String classId, @Field("searchTime") String searchTime);

    @FormUrlEncoded
    @POST("tBabyattendance/selectAttendanceByClassIdWithTypeUnComplete")
    Observable<BaseModel<JSONArray>> selectAttendanceByClassIdWithTypeUnComplete(@Field("token") String token, @Field("classId") String classId, @Field("searchTime") String searchTime, @Field("type") String type);

    //    6.3接口功能：教师端-班级考勤，获取班级列表+考勤信息（tBabyattendance/selectAttendanceByTeacherId）
//    上行:
//    teacherId:
//    searchTime:   “2016-12-31”或者不传
//    token：
//    下行：
//    {
//        "code": "200",
//            "msg": "ok",
//            "data": [
//        {
//            "classId": 1,
//                "className": "大班1",
//                "arrivalNum": 1,                     --到的人数
//            "nonArrivalNum": 1,              --未到人数
//            "leaveNum": 1  			    --请假人数
//        },
//        {
//            "classId": 2,
//                "className": "大班2",
//                "arrivalNum": 1,
//                "nonArrivalNum": 1,
//                "leaveNum": 2
//        }
//        ]
//    }
    @FormUrlEncoded
    @POST("tBabyattendance/selectAttendanceByTeacherId")
    Observable<BaseModel<JSONObject>> selectAttendanceByTeacherId(@Field("token") String token, @Field("searchTime") String searchTime, @Field("teacherId") String teacherId);

    @FormUrlEncoded
    @POST("tBabyattendance/selectAttendanceByTeacherIdWithType")
    Observable<BaseModel<JSONObject>> selectAttendanceByTeacherIdWithType(@Field("token") String token, @Field("searchTime") String searchTime, @Field("teacherId") String teacherId);

    //-----------------------------------------------------------------------------------------------------------------------------//
//    宝宝评语
//    6.9接口功能：教师端-宝宝评语-获取某个班级当天的各项评分名单  /Tevaluation/selectTevaluationScore
//
//    Integer classId,     1  2
//    String type
//    0 吃饭
//    1 喝水
//    2 午休
//    3上厕所
//    4 活动
//    String time    没有表示当前时间   XXXX-xx-xx    2017-01-01
//
//    {
//        "code": "200",
//            "msg": "成功",
//            "data": [
//        {
//            "babyId": 1,
//                "babyName": "宝贝1",
//                "headImageurl": "/static/images/babyHead/baby001.jpg",
//                "score": 123
//        },
//        {
//            "babyId": 2,
//                "babyName": "宝贝2",
//                "headImageurl": "/static/images/babyHead/baby002.jpg",
//                "score": 1
//        }
//        ]
//    }
    @FormUrlEncoded
    @POST("Tevaluation/selectTevaluationScore")
    Observable<BaseModel<JSONArray>> selectTevaluationScore(@Field("token") String token, @Field("time") String searchTime, @Field("classId") String classId, @Field("type") String type);

    //    6.11接口功能：教师端-宝宝评语-获取某个班级当天的各项“评语”名单 /Tevaluation/selectTevaluationComments
//    Integer classId   1
//    String time     2016-12-14
//
//    {
//        "code": "200",
//            "msg": "成功",
//            "data": [
//        {
//            "babyId": 1,
//                "babyName": "宝贝1",
//                "headImageurl": "/static/images/babyHead/baby001.jpg",
//                "eating": 2,
//                "drinking": 2,
//                "noonbreak": 2,
//                "toilet": 2,
//                "activity": 2,
//                "remark": "宝宝评语"
//        },
//        {
//            "babyId": 2,
//                "babyName": "宝贝2",
//                "headImageurl": "/static/images/babyHead/baby002.jpg",
//                "eating": 0,
//                "drinking": 0,
//                "noonbreak": 0,
//                "toilet": 0,
//                "activity": 0,
//                "remark": "Wed Dec 14 00:00:00 CST 2016:宝宝还没有评语"
//        }
//        ]
//    }
    @FormUrlEncoded
    @POST("Tevaluation/selectTevaluationComments")
    Observable<BaseModel<JSONArray>> selectTevaluationComments(@Field("token") String token, @Field("time") String searchTime, @Field("classId") String classId);

    //    6.10接口功能：教师端-宝宝评语-获取某个班级当天的各项评分名单 上传  /Tevaluation/insertTevaluationScore
//    String type 0 1 2 3 4
//    Integer []babyId     1   2  24  25  26
//    Integer []score    1   2    3
//
//    {
//        "code": "200",
//            "msg": "成功",
//            "data": ""
//    }
//
    @FormUrlEncoded
    @POST("Tevaluation/insertTevaluationScore")
    Observable<BaseModel<JSONObject>> insertTevaluationScore(@Field("token") String token, @Field("time") String searchTime, @Field("type") String type, @Field("score") String score, @Field("babyId") String babyId, @Field("createId") String createId);

    //    6.12接口功能：教师端-宝宝评语-获取某个班级当天的各项”评语”名单 上传
//    /Tevaluation/insertTevaluationComments
//    Integer []babyId,   1    2
//    String []remark
//
//    {
//        "code": "200",
//            "msg": "成功",
//            "data": null
//    }
    @FormUrlEncoded
    @POST("Tevaluation/insertTevaluationComments")
    Observable<BaseModel<JSONObject>> insertTevaluationComments(@Field("token") String token, @Field("time") String searchTime, @Field("Comments") String Comments);

    //    6.8接口功能：教师端-宝宝评语-获取某个班级当天的评价完成度（服务器认为班级内某个宝宝有值就认为该项已经完成）
//            /Tevaluation/SelectTevaluationComplete
//    Integer classId    1   2
//    String time           没有默认
//
//    {
//        "code": "200",
//            "msg": "成功",
//            "data": {
//        "isActivity": 0,
//                "isToilet": 0,
//                "isEating": 0,
//                "isDrinking": 0,
//                "isRemark": 1,
//                "isNoonBreak": 0
//    }
//    }
    @FormUrlEncoded
    @POST("Tevaluation/SelectTevaluationComplete")
    Observable<BaseModel<JSONObject>> SelectTevaluationComplete(@Field("token") String token, @Field("time") String searchTime, @Field("classId") String classId);

    //    6.81 接口功能：获取老师对应班级的评价完成度
//        /Tevaluation/selectCompleteByTeacherId?time
    @FormUrlEncoded
    @POST("Tevaluation/selectCompleteByTeacherId")
    Observable<BaseModel<JSONArray>> selectCompleteByTeacherId(@Field("token") String token, @Field("time") String searchTime);

    @FormUrlEncoded
    @POST("tRecipe/selectTrecipeTeacher")
    Observable<BaseModel<JSONArray>> selectTrecipeTeacher(@Field("token") String token, @Field("schoolId") String schoolId);


    @FormUrlEncoded
    @POST("tRecipe/selectTrecipeTeacherPage")
    Observable<BaseModel<JSONObject>> selectTrecipeTeacherPage(@Field("token") String token, @Field("schoolId") String schoolId, @Field("pageNum") int pageNum);

    //    2.18接口功能：家长端，获取收件箱列表(tMessage/getReceiveMessageListByUserId)
//    上行:
//    receiveId： 接收者id，即当前登录用户id
//    token：
//    下行：
//    {
//        "code": "200",
//            "msg": "ok",
//            "data": [
//        {
//            "messageId": 3,
//                "headImageurl": "1362e8fa1f344cd6af4679b2f56ead3b.jpg",
//                "teacherName": "honghongaa",
//                "sendTime": "2017-01-04 11:49:22",
//                "title": "t2c0101",
//                "content": "t2c01t2c01t2c01t2c01",
//                "isRead": 1
//        },
//        {
//            "messageId": 5,
//                "headImageurl": "1362e8fa1f344cd6af4679b2f56ead3b.jpg",
//                "teacherName": "honghongaa",
//                "sendTime": "2017-01-04 11:31:43",
//                "title": "t2c0202",
//                "content": "t2c02t2c02t2c02",
//                "isRead": 0
//        },
//        {
//            "messageId": 7,
//                "headImageurl": "121213",
//                "teacherName": "椒江学校",
//                "sendTime": "2017-01-04 10:44:21",
//                "title": "t2c04",
//                "content": "t2c04t2c04t2c04",
//                "isRead": 0
//        }
//        ]
//    }
    @FormUrlEncoded
    @POST("tMessage/getReceiveMessageListByUserId")
    Observable<BaseModel<JSONArray>> getReceiveMessageListByUserId(@Field("token") String token, @Field("receiveId") String receiveId);

    //2.20接口功能：家长端，获取发件箱列表(tMessage/getSendMessageListByUserId)
    @FormUrlEncoded
    @POST("tMessage/getSendMessageListByUserId")
    Observable<BaseModel<JSONArray>> getSendMessageListByUserId(@Field("token") String token, @Field("sendId") String receiveId);

    //2.21 接口功能：家长端，发送邮件给老师（tMessage/userSendMessageToTeacher）
    @FormUrlEncoded
    @POST("tMessage/userSendMessageToTeacher")
    Observable<BaseModel<JSONObject>> userSendMessageToTeacher(@Field("token") String token, @Field("userId") String userId, @Field("receiveTeacherId") String receiveTeacherId, @Field("title") String title, @Field("content") String content);

    //6.13接口功能：教师端，获取收件箱列表（学校、家长发送的消息）(tMessage/getReceiveMessageListByTeacherId)
    @FormUrlEncoded
    @POST("tMessage/getReceiveMessageListByTeacherId")
    Observable<BaseModel<JSONArray>> getReceiveMessageListByTeacherId(@Field("token") String token, @Field("receiveId") String receiveId);

    //6.15接口功能：教师端，获取发件箱列表（获取未读人的列表的接口）(tMessage/getReceiveMessageListByTeacherId)
    @FormUrlEncoded
    @POST("tMessage/getSendMessageListByTeacherId")
    Observable<BaseModel<JSONArray>> getSendMessageListByTeacherId(@Field("token") String token, @Field("sendId") String receiveId);

    //6.17接口功能：教师端，新建消息发送给班级（tMessage/sendMessagetoClass）
//    上行:
//    token：
//    teacherId:
//    receiveClassIds:["1","2","3"]接受者为班级的id
//    title：
//    content:
    @FormUrlEncoded
    @POST("tMessage/sendMessagetoClass")
    Observable<BaseModel<JSONObject>> sendMessagetoClass(@Field("token") String token, @Field("teacherId") String teacherId, @Field("receiveClassIds") String receiveClassIds, @Field("title") String title, @Field("content") String content);

    //    2.19接口功能：家长端，设置一个邮件为已读(tMessagesign/setMessageReadWithUserId)
//    上行:
//    messageId：            //消息id
//    subscriberId：     //家长id，当前登录用户id
//    token：
//    下行：
//    {
//        "code": "200",
//            "msg": "ok",
//            "data": null
//    }
    @FormUrlEncoded
    @POST("tMessagesign/setMessageReadWithUserId")
    Observable<BaseModel<JSONObject>> setMessageReadWithUserId(@Field("token") String token, @Field("messageId") String messageId, @Field("subscriberId") String subscriberId);

    //    9.23接口功能：老师设置系统消息为已读（tSyssign/setSysmsgReadWithSchoolTeacherId）
    @FormUrlEncoded
    @POST("tMessagesignMore/setMessageReadWithTeacherId")
    Observable<BaseModel<JSONObject>> setMessageReadWithTeacherId(@Field("token") String token, @Field("messageId") String messageId, @Field("subscriberId") String subscriberId);

    //    5.8 接口功能：家长获取系统消息列表(tSysmsg/getSysmsgByUserId)
    @FormUrlEncoded
    @POST("tSysmsg/getSysmsgByUserId")
    Observable<BaseModel<JSONArray>> getSysmsgByUserId(@Field("token") String token, @Field("subscriberId") String subscriberId);

    //   9.15接口功能：教师端，系统消息列表(tSysmsg/getSysmsgByTeacherId)
    @FormUrlEncoded
    @POST("tSysmsg/getSysmsgByTeacherId")
    Observable<BaseModel<JSONArray>> getSysmsgByTeacherId(@Field("token") String token, @Field("subscriberId") String subscriberId);

    //    9.23接口功能：老师设置系统消息为已读（tSyssign/setSysmsgReadWithTeacherId）
    @FormUrlEncoded
    @POST("tSyssign/setSysmsgReadWithTeacherId")
    Observable<BaseModel<JSONObject>> setSysmsgReadWithTeacherId(@Field("token") String token, @Field("sysmsgId") String sysmsgId, @Field("subscriberId") String subscriberId);

    //    9.24接口功能：家长设置系统消息为已读（tSyssign/setSysmsgReadWithTeacherId）
    @FormUrlEncoded
    @POST("tSyssign/setSysmsgReadWithUserId")
    Observable<BaseModel<JSONObject>> setSysmsgReadWithUserId(@Field("token") String token, @Field("sysmsgId") String sysmsgId, @Field("subscriberId") String subscriberId);

    //    收藏夹
//    5.12接口功能：家长端，收藏夹    获取收藏列表
//    9.12 家长端同
//    /TFavorite/SelectTFavorite
//    createId   1       对应用户id
//    CreateType      家长给1      教师  2
    @FormUrlEncoded
    @POST("TFavorite/SelectTFavorite")
    Observable<BaseModel<JSONArray>> SelectTFavorite(@Field("token") String token, @Field("createId") String createId, @Field("CreateType") String CreateType);

    //
//
//    收藏夹
//    5.18 接口功能：家长端，教师端获取收藏夹列表分页
    //    9.12 家长端同
//    /TFavorite/SelectTFavorite
//    createId   1       对应用户id
//    CreateType      家长给1      教师  2
    @FormUrlEncoded
    @POST("TFavorite/SelectTFavoritePage")
    Observable<BaseModel<JSONObject>> SelectTFavoritePage(@Field("token") String token, @Field("createId") String createId, @Field("CreateType") String CreateType, @Field("pageNum") int pageNum);

    //
//    5.13接口功能：家长端，收藏夹-收藏、添加
//    9.10 教师端同
//    /TFavorite/insertTFavorite
//    createId      收藏人id
//    createType    收藏人角色 1 家长 2教师
//    favoriteType   收藏的类型   1新闻 2资讯  （下版本添加单个视屏的收藏）
//    favoriteFavoriteid      收藏的信息的id
//    所有需要的参数不能为空
//
//    {
//        "code": "200",
//            "msg": "添加收藏成功",
//            "data": null
//    }
    @FormUrlEncoded
    @POST("TFavorite/insertTFavorite")
    Observable<BaseModel<JSONObject>> insertTFavorite(@Field("token") String token, @Field("createId") String createId, @Field("CreateType") String CreateType, @Field("favoriteType") String favoriteType, @Field("favoriteFavoriteid") String favoriteFavoriteid);

    //
//    5.14接口功能：家长端，收藏夹-取消收藏
//    9.11 教师端同
//    /TFavorite/DeleteTFavorite
//    favoriteId   1
//
//    {
//        "code": "200",
//            "msg": "删除收藏成功",
//            "data": null
//    }
    @FormUrlEncoded
    @POST("TFavorite/DeleteTFavorite")
    Observable<BaseModel<JSONObject>> DeleteTFavorite(@Field("token") String token, @Field("favoriteId") String favoriteId);

    //    4.1接口功能：家长端，资讯列表
//    /tevet/selectNewsInfoBySchoolId
//    schoolId 1
    @FormUrlEncoded
    @POST("tevet/selectNewsInfoBySchoolId")
    Observable<BaseModel<JSONArray>> selectNewsInfoBySchoolId(@Field("token") String token, @Field("schoolId") String schoolId, @Field("conditions") String conditions);

    //    4.1接口功能：家长端，资讯列表（分页）
//    /tevet/selectNewsInfoBySchoolId
//    schoolId 1
    @FormUrlEncoded
    @POST("tevet/selectNewsInfoBySchoolIdPage")
    Observable<BaseModel<JSONObject>> selectNewsInfoBySchoolIdPage(@Field("token") String token, @Field("schoolId") String schoolId, @Field("pageNum") int pageNum);

    //    6.16接口功能：教师端，发件箱-获取未读人的列表的接口(tMessage/getSendUnReadMessageByTeacherId)
//    sendId：
//    messageId:
//    token：
    @FormUrlEncoded
    @POST("tMessage/getSendUnReadMessageByTeacherId")
    Observable<BaseModel<JSONArray>> getSendUnReadMessageByTeacherId(@Field("token") String token, @Field("sendId") String sendId, @Field("messageId") String messageId);

    //    宝宝关联  幼儿园(updateTBabyOnClassId)
    @FormUrlEncoded
    @POST("tbaby/updateTBabyOnClassId")
    Observable<BaseModel<JSONObject>> updateTBabyOnClassId(@Field("token") String token, @Field("babyId") String babyId, @Field("phoneNum") String phoneNum, @Field("babyName") String babyName);

    //  12.1 接口功能：家长端 首页搜索（indexCommon/selectIndexBySearch）
    // babyId： 发送者id，即当前登录用户id
//    searchContent：
    @FormUrlEncoded
    @POST("indexCommon/selectIndexBySearch")
    Observable<BaseModel<JSONArray>> selectIndexBySearch(@Field("token") String token, @Field("babyId") String babyId, @Field("searchContent") String searchContent);
    //---------------------------------------------------------------------------------

    /**
     * 1.15 家长退出
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("logout")
    Observable<BaseModel<String>> logout(@Field("token") String token);

    /**
     * 1.16 教师长退出
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("tTeacher/logout")
    Observable<BaseModel<String>> teacherLogout(@Field("token") String token);

    /**
     * 5.3 家长端，取消宝宝关注
     *
     * @param token
     * @param userId 用户的id
     * @param babyId 宝宝的id
     * @return
     */
    @FormUrlEncoded
    @POST("userbaby/cancelCareBabyById")
    Observable<BaseModel<String>> cancelCareBabyById(@Field("token") String token, @Field("userId") String userId, @Field("babyId") String babyId);

    /**
     * 2.33 接口功能：家长一个宝贝的首页信息(indexCommon/getIndexCommonPage)（分页）
     *
     * @param token
     * @param babyId
     * @return
     */
    @FormUrlEncoded
    @POST("indexCommon/getIndexCommonPage")
    Observable<BaseModel<JSONObject>> getIndexCommon(@Field("token") String token, @Field("babyId") String babyId, @Field("pageNum") int pageNum);


    /**
     * 12.1  提交意见反馈
     * tFeedback： {content:"this is content",phoneNum:"13757650433",email:"woshijason2008@163.com",device:"iPhone 6s",creatorId:1,type:0}
     *
     * @param tFeedback
     * @return
     */
    @FormUrlEncoded
    @POST("tFeedback/insertFeedback")
    Observable<BaseModel<String>> postAdvice(@Field("tFeedback") String tFeedback, @Field("token") String token);

    /**
     * 2.35 接口功能：家长端删除发件箱(tMessagesignMore/deleteSendMessageOfUser)
     */
    @FormUrlEncoded
    @POST("tMessagesignMore/deleteSendMessageOfUser")
    Observable<BaseModel<JSONObject>> deleteSendMessageOfUser(@Field("token") String token, @Field("messageId") String messageId);

    /**
     * 2.36 接口功能：家长端删除收件箱(tMessagesignMore/deleteReceiveMessageOfUser)
     */
    @FormUrlEncoded
    @POST("tMessagesignMore/deleteReceiveMessageOfUser")
    Observable<BaseModel<String>> deleteReceiveMessageOfUser(@Field("token") String token, @Field("messageId") String messageId, @Field("subscriberId") String subscriberId);

    /**
     * 6.19 接口功能：教师端删除发件箱(tMessagesign/deleteSendMessageOfTeacher)
     */
    @FormUrlEncoded
    @POST("tMessagesign/deleteSendMessageOfTeacher")
    Observable<BaseModel<JSONObject>> deleteSendMessageOfTeacher(@Field("token") String token, @Field("messageId") String messageId);

    /**
     * 6.20 接口功能：教师端删除收件箱(tMessagesign/deleteReceiveMessageOfTeacher)
     */
    @FormUrlEncoded
    @POST("tMessagesign/deleteReceiveMessageOfTeacher")
    Observable<BaseModel<JSONObject>> deleteReceiveMessageOfTeacher(@Field("token") String token, @Field("messageId") String messageId, @Field("subscriberId") String subscriberId);

    //    /**
//     * 3.6获取活动参加人员列表
//     * @param eventId
//     * @return
//     * @date 2017年2月22日
//     */
    @FormUrlEncoded
    @POST("tevet/selectEventNumList")
    Observable<BaseModel<JSONArray>> selectEventNumList(@Field("token") String token, @Field("eventId") String eventId);

    /**
     * 3.7删除活动 给创建老师所在班级发送信息
     */
    @FormUrlEncoded
    @POST("tevet/deleteEventsByEventId")
    Observable<BaseModel<JSONObject>> deleteEventsByEventId(@Field("token") String token, @Field("eventId") String eventId);

    /**
     * 13.1 接口功能：推送一条宝宝关注信息（userbaby/sendCareNoteToUser）
     */
    @FormUrlEncoded
    @POST("userbaby/sendCareNoteToUser")
    Observable<BaseModel<JSONObject>> noficationBabyParent(@Field("userId") String userId, @Field("babyId") String babyId, @Field("userName") String userName, @Field("token") String token);

    /**
     * 13.3 接口功能：点击否的时候调用该接口（userbaby/denyCareApply）
     * userId:   关注人userId  babyId:   token:
     */
    @FormUrlEncoded
    @POST("userbaby/denyCareApply")
    Observable<BaseModel<String>> disagreeCarebaby(@Field("userId") String userId, @Field("babyId") String babyId, @Field("token") String token);

    /**
     * 13.2 接口功能：在客户端没有成功接收推送的情况下再次请求服务器数据库（userbaby/sendCareNoteWithUnNotified）
     * userId:   当前登录用户id    token:
     */
    @FormUrlEncoded
    @POST("userbaby/sendCareNoteWithUnNotified")
    Observable<BaseModel<List<ServerCarebabyCache>>> sendCareNoteWithUnNotified(@Field("userId") String userId, @Field("token") String token);

    /**
     * 2.37 接口功能：删除某宝宝的身高体重信息
     */
    @FormUrlEncoded
    @POST("height/deleteHeightWeightByBabyId")
    Observable<BaseModel<String>> deleteHeightWeightByBabyId(@Field("token") String token, @Field("babyId") String babyId);
//    /tDynamic/deleteDynamicById?dynamicId=34

    /**
     * 未知接口 接口功能：删除动态
     */
    @FormUrlEncoded
    @POST("tDynamic/deleteDynamicById")
    Observable<BaseModel<JSONObject>> deleteDynamicById(@Field("token") String token, @Field("dynamicId") String dynamicId);

    /**
     * 14.1 接口功能：设置用户标签 (tUserTags/updateTag) 上行：userId： tag： json串  {1,2,3}
     */
    @FormUrlEncoded
    @POST("tUserTags/updateTag")
    Observable<BaseModel<String>> postJpushTages(@Field("userId") String userId, @Field("tag") String tag);

    //    2.39 接口功能：相册图片上传完后将commonId传过来设置最小的image_id为封面 （indexCommon/setAlbumCoverByCommonId）
    @FormUrlEncoded
    @POST("indexCommon/setAlbumCoverByCommonId")
    Observable<BaseModel<JSONObject>> setAlbumCoverByCommonId(@Field("token") String token, @Field("commonId") String commonId);

    //  2.40 接口功能：根据上传过来的imageId设置为封面 （indexCommon/setAlbumCoverByImageId）
    @FormUrlEncoded
    @POST("indexCommon/setAlbumCoverByImageId")
    Observable<BaseModel<JSONObject>> setAlbumCoverByImageId(@Field("token") String token, @Field("commonId") String commonId, @Field("imageId") String imageId);

    /**
     * 14.10 接口功能：扫描二维码的时候，提示用户此卡是否可以用（tQrcode/checkUsable）上行：   qrcode:   token:
     */
    @FormUrlEncoded
    @POST("tQrcode/checkUsable")
    Observable<BaseModel<String>> qrScanCode(@Field("qrcode") String qrcode, @Field("token") String token);

    /**
     * 14.6 接口功能：添加卡片人（tPickUp/insertTPickUp）上行：userId,babyId,qrcode,userName,phoneNum , relation： token:
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("tPickUp/insertTPickUp")
    Observable<BaseModel<String>> insertTPickUp(@Field("userId") String userId,
                                                @Field("babyId") String babyId,
                                                @Field("qrcode") String qrcode,
                                                @Field("userName") String userName,
                                                @Field("phoneNum") String phoneNum,
                                                @Field("relation") String relation,
                                                @Field("token") String token);

    /**
     * 14.7 接口功能：上传卡片人的头像（tPickUp/uploadPickUpHeadImg）上行：pickUpId：file: token:
     * @return
     */
    /**
     * 2.5 相册上传图片
     *
     * @param token
     * @param file1
     * @return
     */
    @Multipart
    @POST("tPickUp/uploadPickUpHeadImg")
    Observable<BaseModel<String>> babycardUploadImg(@Part("token") String token, @Part("pickUpId") int pickUpId, @Part("file\";filename=\"image.jpg") RequestBody file1);


    //    14.4 接口功能：插入请假信息，批量请假(tLeave/insertLeave)  type: 0-病假，1-事假
//    userId：
//    babyId:
//    classId：
//    startTime:2017-03-10 09:00:00
//    endTime:2017-03-12 09:00:00
//    description:
    @FormUrlEncoded
    @POST("tLeave/insertLeave")
    Observable<BaseModel<JSONObject>> insertLeave(@Field("token") String token, @Field("userId") String userId, @Field("babyId") String babyId,
                                                  @Field("classId") String classId, @Field("startTime") String startTime,
                                                  @Field("endTime") String endTime, @Field("description") String description,
                                                  @Field("type") String type, @Field("className") String className, @Field("babyName") String babyName);

    /**
     * 14.5 接口功能：获取接送卡列表(tPickUp/selectPickUpsByBabyId)上行：babyId:  token:
     */
    @FormUrlEncoded
    @POST("tPickUp/selectPickUpsByBabyId")
    Observable<BaseModel<List<BabyRelationModel>>> getBabyRelatinCards(@Field("babyId") int babyId, @Field("token") String token);

    //    14.3.设置代接送人员
//    （tAgent/insertAgent）
//    上行：
//    token:
//    tAgent:{"babyId":"1","type":"0","userName":"testUsername","phoneNum":"15088689998","agentTime":"2017-03-10","description":"","creatorId":"2"}
//    classId:
    @FormUrlEncoded
    @POST("tAgent/insertAgent")
    Observable<BaseModel<JSONObject>> insertAgent(@Field("token") String token, @Field("tAgent") String tAgent, @Field("agentSendContent") String agentSendContent);

    //  14.1.获取当天考勤信息。三种情况：a-请假；b-无数据；c-有考勤数据（获取考勤机的数据）
    @FormUrlEncoded
    @POST("tTimeCard/selectTodayAttendance")
    Observable<BaseModel<JSONObject>> selectTodayAttendance(@Field("token") String token, @Field("babyId") String babyId);


    //------------------------------------------------考勤机------------------------------------------------

    /**
     * 14.12 根据考勤的机器序列号获取院校信息
     *
     * @param machineCode 设备号，imei
     * @return
     */
    @FormUrlEncoded
    @POST("attendanceMachine/SchoolByMachineCode")
    Observable<BaseModel<SchoolModel>> SchoolByMachineCode(@Field("token") String token, @Field("machineCode") String machineCode);

    /**
     * 考勤的机器序绑定
     *
     * @param machineCode 设备号，imei
     * @return
     */
    @FormUrlEncoded
    @POST("attendanceMachine/updateState")
    Observable<BaseModel<String>> updateState(@Field("machineCode") String machineCode);

    /**
     * 14.13 考勤机扫描二维码获取当时接送人和baby的信息
     *
     * @param schoolId 学校id
     * @param babyId   宝宝id
     * @param qrcode   qrcode
     * @return
     */
    @FormUrlEncoded
    @POST("attendanceMachine/babyAndPersonInfoByQrcode")
    Observable<BaseModel<JSONObject>> babyAndPersonInfoByQrcode(@Field("token") String token,
                                                                @Field("schoolId") String schoolId,
                                                                @Field("babyId") String babyId,
                                                                @Field("userId") String userId,
                                                                @Field("qrcode") String qrcode,
                                                                @Field("eventType") int eventType);
    /**
     * 14.14 考勤机扫描二维码获取打卡老师信息
     *
     * @param schoolId 学校id
     * @param teacherId   宝宝id
     * @param qrcode   qrcode
     * @return
     */
    @FormUrlEncoded
    @POST("attendanceMachine/teacherInfoByQrcode")
    Observable<BaseModel<AttendenceTeacherModel>> teacherInfoByQrcode(@Field("schoolId") String schoolId,
                                                                      @Field("teacherId") String teacherId,
                                                                      @Field("qrcode") String qrcode,
                                                                      @Field("eventType") int eventType);

    /**
     * 14.14. 考勤机记录当时接送人的照片,并记录考勤
     *
     * @param schoolId  学校id
     * @param babyId    宝宝id
     * @param qrcode    babyId和qrcode二选一填，如果都没会报错
     * @param eventType 0到校  ，1离校
     * @param file      图片
     * @return
     */
    @Multipart
    @POST("attendanceMachine/uploadTimeCardImg")
    Observable<BaseModel<String>> uploadTimeCardImg(
            @Part("token") RequestBody token,
            @Part("schoolId") RequestBody schoolId,
            @Part("babyId") RequestBody babyId,
            @Part("userId") RequestBody userId,
            @Part("qrcode") RequestBody qrcode,
            @Part("eventType") RequestBody eventType,
            @Part("file\";filename=\"image.jpg") RequestBody file
    );

    /**
     * 14.15. 考勤机记录当时接送人的照片,并记录考勤
     *
     * @param schoolId  学校id
     * @param babyId    宝宝id
     * @param qrcode    babyId和qrcode二选一填，如果都没会报错
     * @param eventType 0到校  ，1离校
     * @param imgName   图片名称
     * @return
     */
    @FormUrlEncoded
    @POST("attendanceMachine/uploadNewTimeCardImg")
    Observable<BaseModel<String>> uploadNewTimeCardImg(
            @Field("token") String token,
            @Field("schoolId") String schoolId,
            @Field("babyId") String babyId,
            @Field("userId") String userId,
            @Field("qrcode") String qrcode,
            @Field("eventType") String eventType,
            @Field("imgName") String imgName
    );

    /**
     * 14.16. 考勤机记录教师照片
     *
     * @param schoolId  学校id
     * @param teacherId    宝宝id
     * @param qrcode    babyId和qrcode二选一填，如果都没会报错
     * @param imgName   图片名称
     * @return
     */
    @FormUrlEncoded
    @POST("attendanceMachine/uploadTeacherNewTimeCardImg")
    Observable<BaseModel<String>> uploadTeacherCardImg(
            @Field("schoolId") String schoolId,
            @Field("teacherId") String teacherId,
            @Field("qrcode") String qrcode,
            @Field("imgName") String imgName,
            @Field("eventType") int eventType,
            @Field("time") long time
    );

    /**
     * 一天的考勤可以多次，每次打卡间隔不低于5分钟
     *
     * @param schoolId 学校id
     * @param teacherId 老师ID
     * @param qrcode  二维码数据
     * @param imgName  图片名称
     * @param eventType  到校 或 离校 两个状态
     * @param time  打卡时间
     * @return
     */
    @FormUrlEncoded
    @POST("attendanceMachine/uploadTeacherAttendance")
    Observable<BaseModel<String>> uploadTeacherAttendance(
            @Field("schoolId") String schoolId,
            @Field("teacherId") String teacherId,
            @Field("qrcode") String qrcode,
            @Field("imgName") String imgName,
            @Field("eventType") int eventType,
            @Field("time") long time
    );

    /**
     * 14.16. 班级学生考勤
     */
    @FormUrlEncoded
    @POST("tTimeCard/selectBabyTimeCardByClass")
    Observable<BaseModel<JSONArray>> selectBabyTimeCardByClass(
            @Field("token") String token,
            @Field("classId") String schoolId,
            @Field("searchTime") String searchTime
    );


    //------------------------------------------------考勤机------------------------------------------------

    //  14.2.获取历史记录考勤机数据（展示为时间倒序）
    @FormUrlEncoded
    @POST("tTimeCard/selectHistoryAttendance")
    Observable<BaseModel<JSONArray>> selectHistoryAttendance(@Field("token") String token, @Field("babyId") String babyId);

    /**
     * 14.9 接口功能：编辑接送卡（tPickUp/updatePickUpById）上行：pickUpId:userName：phoneNum：relation：token:
     */
    @FormUrlEncoded
    @POST("tPickUp/updatePickUpById")
    Observable<BaseModel<JSONObject>> updateBabyCard(@Field("pickUpId") int pickUpId,
                                                     @Field("userName") String userName,
                                                     @Field("phoneNum") String phoneNum,
                                                     @Field("relation") String relation,
                                                     @Field("token") String token);


    /**
     * 14.8 接口功能：删除接送卡（tPickUp/deletePickUpById）上行：pickUpId:token:
     */
    @FormUrlEncoded
    @POST("tPickUp/deletePickUpById")
    Observable<BaseModel<JSONObject>> deleteBabyCard(@Field("pickUpId") int pickUpId, @Field("token") String token);

    /**
     * 14.12 接口功能： 查询班级未到和请假情况
     */
    @Deprecated
    @FormUrlEncoded
    @POST("tBabyattendance/selectLeaveByClassId")
    Observable<BaseModel<List<BabyAttendancesModel>>> selectLeaveByClassId(@Field("classId") String classId, @Field("token") String token);

    /**
     * 教师端-班级考勤，获取某个班级考勤列表 跟宝宝评价有关（tBabyattendance/selectLeaveByClassIdWithType）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("tBabyattendance/selectLeaveByClassIdWithType")
    Observable<BaseModel<List<BabyAttendancesModel>>> selectLeaveByClassIdWithType(@Field("classId") String classId, @Field("token") String token);

    /**
     * 15.1 用户创建和关注时为每个用户插入提醒信息
     */
    @FormUrlEncoded
    @POST("/tAlert/insertInitAlert")
    Observable<BaseModel<JSONObject>> insertInitAlert(@Field("token") String token, @Field("tAlert") String tAlert);

    /**
     * 15.2  更新Alert  系统消息、收发件箱、校园、宝宝评语、考勤、教学计划等。
     */
    @FormUrlEncoded
    @POST("/tAlert/updateAlert")
    Observable<BaseModel<JSONObject>> updateAlert(@Field("token") String token, @Field("tAlert") String tAlert);

    /**
     * 15.3 获取Alert  系统消息、收发件箱、校园、宝宝评语、考勤、教学计划等。
     */
    @FormUrlEncoded
    @POST("/tAlert/selectAlert")
    Observable<BaseModel<JSONObject>> selectAlert(@Field("token") String token, @Field("userId") String userId, @Field("classId") String classId, @Field("schoolId") String schoolId, @Field("babyId") String babyId);

    /**
     * 15.4  更新TeacherAlert  系统消息、收发件箱、校园
     */
    @FormUrlEncoded
    @POST("/tTeacherAlert/updateTeacherAlert")
    Observable<BaseModel<JSONObject>> updateTeacherAlert(@Field("token") String token, @Field("tTeacherAlert") String tTeacherAlert);

    /**
     * 15.5 教师端获取提醒信息
     */
    @FormUrlEncoded
    @POST("/tTeacherAlert/selectTeacherAlert")
    Observable<BaseModel<JSONObject>> selectTeacherAlert(@Field("token") String token, @Field("teacherId") String teacherId, @Field("schoolId") String schoolId);


    /**
     * 获得当前用户积分
     *
     * @return
     */
    @FormUrlEncoded
    @POST("IRecord/selectIRecordAll")
    Observable<BaseModel<String>> getScores(@Field("token") String token);


    /**
     * 获得可兑换的商品
     *
     * @return
     */
    @FormUrlEncoded
    @POST("IRecord/selectExchangePoolList")
    Observable<BaseModel<List<PrizeDrawBean>>> getRecordDrawList(@Field("token") String token);

    /**
     * 获得抽奖中奖的奖品信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("IRecord/IRecordLuckDraw")
    Observable<BaseModel<PrizeLuckyModel>> getLuckDraw(@Field("token") String token);


    /**
     * 获得积分记录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("IRecord/selectIRecordLine")
    Observable<BaseModel<List<PrizeHistoryBean>>> getRecordLine(@Field("token") String token);


    /**
     * 获得当前联系人默认收货地址
     *
     * @return
     */
    @FormUrlEncoded
    @POST("IRecord/selectContactInfo")
    Observable<BaseModel<List<PrizeContactInfoModel>>> getContactInfo(@Field("token") String token);

    /**
     * 获得兑换结果
     *
     * @return
     */
    @FormUrlEncoded
    @POST("IRecord/exchangePool")
    Observable<BaseModel<ExchangeModel>> exchangePool(@Field("exchangeId") String exchangeId);

    /**
     * 上传中奖人的详细收货地址
     *
     * @return
     */
    @FormUrlEncoded
    @POST("IRecord/insertContactInfo")
    Observable<BaseModel<PrizeAddressModel>> insertContactInfo(@Field("name") String name,
                                                               @Field("phoneNum") String phoneNum,
                                                               @Field("fullAddress") String fullAddress,
                                                               @Field("locationArea") String locationArea,
                                                               @Field("drawId") String drawId,
                                                               @Field("orderNum") String orderNum,
                                                               @Field("id") String id);


    /**
     * 积分记录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("IRecord/insertIRecord")
    Observable<BaseModel<JSONObject>> insertIRecord(@Field("classId") String classId, @Field("type") String type);


    /**
     * 获得奖品订单记录
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("IRecord/selectDrawRecordLineList")
    Observable<BaseModel<List<PrizeOrderDetailModel>>> getOrderList(@Field("token") String token);

    /**
     * 确认收货
     *
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST("IRecord/updateDrawRecord")
    Observable<BaseModel<JSONObject>> sureGetThings(@Field("id") String orderId);


    /**
     * 家长批量删除发件箱
     *
     * @param messageIds
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("tMessagesignMore/deleteBatchSendMessageOfUser")
    Observable<BaseModel<JSONObject>> deleteAllSendMessageOfUser(@Field("messageIds") String messageIds, @Field("token") String token);

    /**
     * 家长批量删除收件箱
     *
     * @param messageIds
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("tMessagesignMore/deleteBatchReceiveMessageOfUser")
    Observable<BaseModel<JSONObject>> deleteAllReceiveMessageOfUser(@Field("messageIds") String messageIds, @Field("token") String token, @Field("subscriberId") String subscriberId);


    /**
     * 教师批量删除发件箱
     *
     * @param messageIds
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("tMessagesign/deleteBatchSendMessageOfTeacher")
    Observable<BaseModel<JSONObject>> deleteBatchSendMessageOfTeacher(@Field("messageIds") String messageIds, @Field("token") String token);

    /**
     * 教师批量删除收件箱
     *
     * @param messageIds
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("tMessagesign/deleteBatchReceiveMessageOfTeacher")
    Observable<BaseModel<JSONObject>> deleteBatchReceiveMessageOfTeacher(@Field("messageIds") String messageIds, @Field("token") String token, @Field("subscriberId") String subscriberId);

    /**
     * 日程获取月列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("tSchedule/selectSchedule")
    Observable<BaseModel<JSONArray>> selectSchedule(@Field("token") String token, @Field("year") String year, @Field("month") String month);


    /**
     * 获取某天日程具体安排
     *
     * @return
     */
    @FormUrlEncoded
    @POST("tSchedule/selectScheduleDetailsByDay")
    Observable<BaseModel<JSONArray>> selectScheduleDetailsByDay(@Field("token") String token, @Field("time") String time);

    //    2.50 接口功能：相册获取列表相册名的接口
    @FormUrlEncoded
    @POST("/indexCommon/selectAlbumList")
    Observable<BaseModel<JSONObject>> selectAlbumList(@Field("token") String token, @Field("userId") String userId, @Field("babyId") String babyId);

    /**
     * 2.52 插入设备版本信息 /tEquipment/insertEquipment
     * 上行：
     * equipment: {"userId":1,"phoneNum":"13757650433","appVersion":"3.5.2","equipmentVersion":"安卓18.0","createTime":"","uType":1,"type":1}
     * 备注：
     * uType:   1：家长  2：老师
     * type:      1：安卓  2：IOS
     * 下行：
     * {
     * "code": "200",
     * "msg": "ok",
     * "data": null
     * }
     */
    @FormUrlEncoded
    @POST("tEquipment/insertEquipment")
    Observable<BaseModel<JSONObject>> insertEquipment(@Field("token") String token,
                                                      @Field("equipment") String equipment);

    /**
     * 接口功能：列表 、搜索  路径：/classroomAssist/list
     */
    @FormUrlEncoded
    @POST("classroomAssist/list")
    Observable<BaseModel<JSONObject>> classroomAssistList(@Field("token") String token,
                                                  @Field("title") String title,
                                                  @Field("type") int type,//类型  1：健康  2：科学  3：社会 4：语言  5：艺术
                                                  @Field("pageSize") int pageSize,//每页记录数
                                                  @Field("pageNum") int pageNum//当前页码	  必填
    );




    /**
     * 获取咨询发现列表
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("/tInfo/selectByInfoAllNotFollow")
    Observable<BaseModel<List<InfomationModel>>> getTinfoDiscover(@Field("token") String token
            ,@Field("state") String state
            ,@Field("pageNum") int pageNum);

    /**
     * 获取咨询关注列表
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("/tInfo/selectByInfoAllFollow")
    Observable<BaseModel<List<InfomationModel>>> getTinfoCared(@Field("token") String token
            ,@Field("state") String state
            ,@Field("pageNum") int pageNum);

    /**
     * 获取用户自己发的咨询
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("/tInfo/selecttInfoByUserId")
    Observable<BaseModel<InfoPersonMsgModel>> getUserInfoMsg(@Field("userId") String userId
            ,@Field("pageNum") int pageNum,@Field("state") String state,@Field("creatorId") String creatorId);

    /**
     * 获取用户关注的人
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("/tInfo/selectRelationList")
    Observable<BaseModel<List<CareOrCaredModel>>> getMyCaredPersons(@Field("token") String token);

    /**
     * 获取用户关注自己的人
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("/tInfo/selectRelationListToMe")
    Observable<BaseModel<List<CareOrCaredModel>>> getCaredMePersons(@Field("token") String token);


    /**
     * 添加关注的人
     * @param token
     * @param fromState
     * @param toState
     * @param toUserId
     * @return
     */
    @FormUrlEncoded
    @POST("/tInfo/insertTRelation")
    Observable<BaseModel<Integer>> addCarePerson(@Field("token") String token,
                                                    @Field("fromState") String fromState,
                                                    @Field("toState") String toState,
                                                    @Field("toUserId") String toUserId);
    /**
     * 添加关注的人
     * @return
     */
    @FormUrlEncoded
    @POST("/tInfo/deleteTRelation")
    Observable<BaseModel<Integer>> deleteCarePerson(@Field("relationId") String relationId);

    /**
     * 搜索数据
     * @return
     */
    @FormUrlEncoded
    @POST("/tInfo/selecttInfo")
    Observable<BaseModel<List<InfomationModel>>> searchInfoMsg(@Field("condition") String condition,@Field("token") String token);

    /**
     * 点赞
     * @return
     */
    @FormUrlEncoded
    @POST("/tInfo/insertLikes")
    Observable<BaseModel<JSONObject>> addLikeInfo(@Field("token") String token
            ,@Field("topicType") String topicType
            ,@Field("topicId") String topicId);

    /**
     * 获得评论
     * commentUtype=1     评论或回复的人的类型 1：家长 2：老师
     commentContent=123321123123123
     toReplyUid被回复的id
     toReplyType   被回复的类型 1：家长 2：老师
     如果为评论则 toReplyId和toReplyType为空
     * @return
     */
    @FormUrlEncoded
    @POST("/tInfo/selectCommentList")
    Observable<BaseModel<List<CommentModel>>> getCommentList(@Field("token") String token,
                                                             @Field("topicType") String topicType,
                                                             @Field("topicId") String topicId);

    /**
     * 添加评论
     * 评论时toReplyUid和toReplyUtype不传
     *  topicId 资讯id+相册id
     *  topicType  1资讯2相册
     *  replyUtype
     * 1：家长 2：老师
     *  commentContent 回复内容
     *  commentUtype  评论人的类型
     *      1：家长  2：老师
     *  toReplyUid  被回复的id
     *  toReplyUtype  被回复人的类型
     *      1：家长
    2：老师
     * @return
     */
    @FormUrlEncoded
    @POST("/tInfo/insertInfoComment")
    Observable<BaseModel<String>> addComment(@Field("token") String token,
                                                             @Field("topicType") String topicType,
                                                             @Field("topicId") String topicId,
                                                             @Field("commentContent") String commentContent,
                                                             @Field("toReplyUid") String toReplyUid,
                                                             @Field("toReplyUtype") String toReplyUtype,
                                                             @Field("commentUtype") String commentUtype);

    /**
     * 删除评论
     * @return
     */
    @FormUrlEncoded
    @POST("/tInfo/deleteCommentById")
    Observable<BaseModel<String>> deleteComment(@Field("token") String token, @Field("tInfoCommId") String tInfoCommId,
                                                @Field("topicId") String topicId,@Field("topicType") String topicType);

    /**
     * 删除咨询
     * @return
     */
    @FormUrlEncoded
    @POST("/tInfo/deleteInfoById")
    Observable<BaseModel<String>> deleteAlbum(@Field("subAlbumId") String subAlbumId);

    /**
     * 通过id查询咨询详情
     * @return
     */
    @FormUrlEncoded
    @POST("/tInfo/selectInfoDetails")
    Observable<BaseModel<SelectInfoDetailModel>> selectInfoDetails(@Field("topicId") String topicId,
                                                                   @Field("topicType") String topicType,
                                                                   @Field("token") String token);


    /**
     * 获取资讯是否有新的评论或回复（红点）
     * @return
     */
    @FormUrlEncoded
    @POST("/tInfo/selectInfoIsUpdate")
    Observable<BaseModel<String>> selectInfoIsUpdate(@Field("token") String token);


    /**
     * 获取资讯新的评论或回复
     * @return
     */
    @FormUrlEncoded
    @POST("/tInfo/selectInfoUpdateList")
    Observable<BaseModel<List<BaseInfoHintModel>>> selectInfoUpdateList(@Field("token") String token);

    /**
     * 获取资讯旧的评论或回复
     * @return
     */
    @FormUrlEncoded
    @POST("/tInfo/selectCommList")
    Observable<BaseModel<BaseInfoHintOldModel>> selectCommList(@Field("token") String token, @Field("pageNum") String pageNum);

    /**
     * 获取教师考勤记录
     * @param token
     * @param pageNum
     * @return
     */
    @FormUrlEncoded
    @POST("/attendanceMachine/selectTeacherAttendanceList")
    Observable<BaseModel<AttendenceHistoryModel>> selectTeacherAttendanceList(@Field("token") String token, @Field("pageNum") int pageNum);


    /**
     * @param startTime  请假开始时间
     * @param endTime   请假结束时间
     * @param type      请假类型  1 病假 2 事假
     * @param reason    请假原因
     * @return
     */
    @FormUrlEncoded
    @POST("/attendanceMachine/insertLeave")
    Observable<BaseModel<String>> insertLeaveTeacher(@Field("startTime") long startTime,@Field("endTime") long endTime,@Field("type") int type,@Field("leaveReason") String reason);


    /**
     * 教师请假历史
     * @return
     */
    @POST("/attendanceMachine/selectLeave")
    Observable<BaseModel<List<AskForLeaveHistoryModel>>> selectLeave();

    /**
     * 获取教师的历史考勤记录  包括了考勤和请假记录  2017/11/24
     * @return
     */
    @POST("/attendanceMachine/selectHistoryAttendance")
    Observable<BaseModel<AttendenceLeaveHistoryModel>>  selectHistoryAttendance();

    /**
     * 校长获取请假审批数据
     * @return
     */
    @POST("/attendanceMachine/selectApprovalList")
    Observable<BaseModel<List<AskForLeaveModel>>> selectApprovalList();

    /**
     * 校长审批是否同意请假
     * 状态 0:待批准 1：已批准 2：已拒绝"
     * @param leaveId
     * @param state
     * @return
     */
    @FormUrlEncoded
    @POST("/attendanceMachine/updateLeaveState")
    Observable<BaseModel<String>>  updateLeaveState(@Field("leaveId")int leaveId,@Field("state") int state);

    /**
     * 校长查询全校老师考勤记录
     * @return
     */
    @FormUrlEncoded
    @POST("/attendanceMachine/selectLeaveList")
    Observable<BaseModel<SchoolMasterModel>> selectLeaveList(@Field("schoolId") int id);

    /**
     * 作者：ShuWen
     * 日期：2017/12/12  14:55
     * 描述：校长考勤界面请假审批，查看是否有新的请假申请
     *
     * @return [返回类型说明]
     */
    @FormUrlEncoded
    @POST("/tTeacherAlert/selectTeacherLeaveAlert")
    Observable<BaseModel<Integer>> selectTeacherLeaveAlert(@Field("schoolId") int schoolId);

    /**
     * 作者：ShuWen
     * 日期：2017/12/28  16:58
     * 描述：校长获取全校的年纪和班级
     *
     * @return [返回类型说明]
     */
    @FormUrlEncoded
    @POST("tschool/selectClassAndGrade")
    Observable<BaseModel<JSONObject>> selectClassAndGrade(@Field("schoolId")int schoolId);


}
