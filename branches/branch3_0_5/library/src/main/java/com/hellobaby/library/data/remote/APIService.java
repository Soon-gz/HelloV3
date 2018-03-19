package com.hellobaby.library.data.remote;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.ClassModel;
import com.hellobaby.library.data.model.ReviewModel;
import com.hellobaby.library.data.model.SchoolModel;
import com.hellobaby.library.data.model.TeacherModel;
import com.hellobaby.library.data.model.TeachingPlanModel;
import com.hellobaby.library.data.model.UserModel;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by zwj on 2016/9/27.
 * description :
 */

public interface APIService {


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
    Observable<BaseModel<String>> subRegister(@Field("phoneNum") String phoneNum, @Field("password") String password, @Field("smsCode") String smsCode);

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
     * 1.2 教师端登录
     *
     * @param phoneNum
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("tTeacher/checkTeacherLogin")
    Observable<BaseModel<String>> checkTeacherLogin(@Field("phoneNum") String phoneNum, @Field("password") String password);

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
     * @param token
     * @param userId
     * @param babyId
     * @return
     */
    @FormUrlEncoded
    @POST("userbaby/insertCareBaby")
    Observable<BaseModel<String>> insertCareBaby(@Field("token") String token,@Field("userId") String userId,@Field("babyId") String babyId);


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
     */
    @Multipart
    @POST("indexCommon/uploadSmallVideo")
    Observable<BaseModel<AlbumModel>> videoUpload(@Part("token") RequestBody token,
                                                  @Part("videoId") RequestBody videoId,
                                                  @Part("videoName") RequestBody videoName,
                                                  @Part("file\";filename=\"video.mp4") RequestBody file);

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
     * @param token
     * @param baby baby的字符串
     * @param phoneNum 手机号
     * @return
     */
    @FormUrlEncoded
    @POST("tbaby/insertTBaby")
    Observable<BaseModel<BabyModel>> createBaby(@Field("token") String token, @Field("userId") String userId, @Field("tBaby") String baby, @Field("phoneNum") String phoneNum);
    /**
     * 2.32 接口功能：家长端，关联幼儿园成功后设置邮件总人数+1
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
    Observable<BaseModel<List<TeachingPlanModel>>> selectTeachingplanByTeacherId(@Field("token") String token, @Field("teacherId") String teacherId);
    //    2.31接口功能：家长-教学计划-获取教学计划列表（tTeachingplan/selectTeachingplanFromUserId）
//    classId:
//    planningTime: 2017-01-01
    @FormUrlEncoded
    @POST("tTeachingplan/selectTeachingplanFromUserId")
    Observable<BaseModel<List<TeachingPlanModel>>> selectTeachingplanFromUserId(@Field("token") String token, @Field("classId") String classId, @Field("planningTime") String planningTime);
//    6.18 接口功能：教师端-教学计划-删除教学计划（tTeachingplan/deleteTeachingplanByTeacherId）
@FormUrlEncoded
@POST("tTeachingplan/deleteTeachingplanByTeacherId")
Observable<BaseModel<JSONObject>> deleteTeachingplanByTeacherId(@Field("token") String token, @Field("teachingplanId") String teacherId);
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
    Observable<BaseModel<JSONObject>> insertTeachingplan(@Part("token") RequestBody token, @Part("teacherId") RequestBody teacherId, @Part("classIds") RequestBody classIds, @Part("planningTime") RequestBody planningTime, @Part("file\";filename=\"image.jpg") RequestBody file1);

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

    // 3.3家长端 参加\取消参加  /tevet/isNotJoinEvent
    @FormUrlEncoded
    @POST("tevet/isNotJoinEvent")
    Observable<BaseModel<JSONArray>> isNotJoinEvent(@Field("token") String token, @Field("babyId") String babyId, @Field("eventId") String eventId, @Field("type") String type);

    // 3.1获取baby所在班级的所有活动   /tevet/selectEventByClassToOneBaby
    @FormUrlEncoded
    @POST("tevet/selectEventByClassToOneBaby")
    Observable<BaseModel<JSONArray>> selectEventByClassToOneBaby(@Field("token") String token, @Field("classId") String classId, @Field("babyId") String babyId, @Field("schoolId") String schoolId, @Field("type") String type);


    // 校园搜索   /tevet/selectEventByClassToOneBaby
    @FormUrlEncoded
    @POST("tevet/selectEventByClassToOneBaby")
    Observable<BaseModel<JSONArray>> selectEventByClassToOneBaby(@Field("token") String token, @Field("classId") String classId, @Field("babyId") String babyId, @Field("schoolId") String schoolId, @Field("type") String type,@Field("conditions") String conditions);

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

    @FormUrlEncoded
    @POST("tRecipe/selectTrecipeTeacher")
    Observable<BaseModel<JSONArray>> selectTrecipeTeacher(@Field("token") String token, @Field("schoolId") String schoolId);

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
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("logout")
    Observable<BaseModel<String>> logout(@Field("token") String token);

    /**
     * 1.16 教师长退出
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("tTeacher/logout")
    Observable<BaseModel<String>> teacherLogout(@Field("token") String token);

    /**
     * 5.3 家长端，取消宝宝关注
     * @param token
     * @param userId 用户的id
     * @param babyId 宝宝的id
     * @return
     */
    @FormUrlEncoded
    @POST("userbaby/cancelCareBabyById")
    Observable<BaseModel<String>> cancelCareBabyById(@Field("token") String token,@Field("userId") String userId,@Field("babyId") String babyId);

}
