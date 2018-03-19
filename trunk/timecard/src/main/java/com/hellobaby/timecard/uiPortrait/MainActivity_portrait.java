package com.hellobaby.timecard.uiPortrait;

/**
 * 大版本 1.0.3  屏幕竖着   时间 ：2017-07-11
 * 大版本 1.0.5  主页显示更改，打卡逻辑更新  时间 ：2017-11-16
 */

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.AppVersionModel;
import com.hellobaby.library.data.model.AttendenceTeacherModel;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.SchoolModel;
import com.hellobaby.library.data.model.TeacherModel;
import com.hellobaby.library.ui.upapp.UpAppDialogActivity;
import com.hellobaby.library.utils.AppUtils;
import com.hellobaby.library.utils.DESUtils;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.utils.PermissionUtils;
import com.hellobaby.library.utils.SharedPreferencesUtils;
import com.hellobaby.library.utils.StringUtils;
import com.hellobaby.library.widget.ToastUtils;
import com.hellobaby.timecard.KeyConst;
import com.hellobaby.timecard.R;
import com.hellobaby.timecard.ZSApp;
import com.hellobaby.timecard.data.model.TCUserModel;
import com.hellobaby.timecard.ui.base.BaseActivity;
import com.hellobaby.timecard.ui.camera.PictureSurfaceViewHalf;
import com.hellobaby.timecard.ui.main.MainMvpView;
import com.hellobaby.timecard.ui.main.MainPresenter;
import com.hellobaby.timecard.utils.ScanGunZS;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.sunflower.FlowerCollector;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.hellobaby.library.utils.SharedPreferencesUtils.getParam;

public class MainActivity_portrait extends BaseActivity implements MainMvpView {

    @Inject
    MainPresenter presenter;

    @BindView(R.id.main_tv_date)
    TextView tvDate;
    @BindView(R.id.main_tv_week)
    TextView tvWeek;
    @BindView(R.id.main_tv_schoolName)
    TextView tvSchoolName;
    @BindView(R.id.camera_pictureSurfaceView)
    PictureSurfaceViewHalf surfaceView;
    @BindView(R.id.camera_civ_babyHead)
    CircleImageView civBabyHead;
    @BindView(R.id.camera_tv_babyName)
    TextView tvBabyName;
    @BindView(R.id.camera_tv_class)
    TextView tvClass;
    @BindView(R.id.camera_civ_userHead)
    CircleImageView civUserHead;
    @BindView(R.id.camera_tv_userName)
    TextView tvUserName;
    @BindView(R.id.camera_tv_relation)
    TextView tvRelation;
    @BindView(R.id.camera_tv_take)
    TextView tvTake;
    //    首页注释掉  需要时在放开  17-11-6
//    @BindView(R.id.main_rl_main)
//    RelativeLayout rlMain;
    @BindView(R.id.main_rl_camera_msg)
    LinearLayout rlCamera;
    @BindView(R.id.main_ll_home)
    LinearLayout rlHome;
    @BindView(R.id.timeCard_second_person)
    LinearLayout timeCard_second_person;
    @BindView(R.id.mainCamera_iv_loading)
    RelativeLayout ivLoading;
    @BindView(R.id.main_teacher_ts)
    TextView mainTeacherTs;

    //当前相机服务是否暂停
    private boolean isPause = false;
    //是否是正在处理二维码信息
    private boolean isHandle = false;

    private Handler handler = new Handler();
    //扫描枪
    private ScanGunZS mScanGun = null;
    long startScanTime = -1;
    long endScanTime = -1;

    //播放语音
//    private MediaPlayer mediaPlayer;
//    private boolean createState = false;

    // 语音合成对象
    private SpeechSynthesizer mTts;
    // 默认发音人
    private String voicer = "xiaoyan";
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    // 小朋友接送语音判别标志
    private boolean isSong = false;

    private MainActivity_portrait.TakePictureRunnable runnable;
    private MainActivity_portrait.TakePictureRunnable runnableTeacher;

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        PermissionUtils.getInstance().requestPermission(this);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=59dc3665");
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(MainActivity_portrait.this, mTtsInitListener);
        //通过xml自动更新
//        new CheckVersionThread(this, Const.apkDownPath, KeyConst.url_timeCard).start();
        presenter.appVersionGet();

        // 设置key事件最大间隔，默认20ms，部分低端扫码枪效率低
        ScanGunZS.setMaxKeysInterval(100);
        mScanGun = new ScanGunZS(new ScanGunZS.ScanGunCallBack() {
            @Override
            public void onScanFinish(String scanResult) {
                endScanTime = System.currentTimeMillis();
                Log.i("ZLog", "end=>" + endScanTime + "  ; start=>" + startScanTime + "  ;扫描间隔" + (endScanTime - startScanTime) + "  ;scanResult=>" + scanResult);
                endScanTime = -1;
                startScanTime = -1;
                dealResult(scanResult);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ivLoading.setVisibility(View.GONE);
            }
        }, 2000);




    }

    @OnClick(R.id.main_iv_setting)
    public void clickListener(View view) {
        startActivity(new Intent(bContext, SettingLoginActivity_portrait.class));
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main_portrait;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPause = false;
        setRiqi();

    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
        if (handler != null && runnableTeacher != null) {
            handler.removeCallbacks(runnableTeacher);
        }
        if (mTts != null && mTts.isSpeaking()) {
            mTts.stopSpeaking();
            mTts.destroy();
            mTts = null;
        }
    }


    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
//            LogZS.i("InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showToast("初始化失败,错误码：" + code);
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    /**
     * 参数设置
     *
     * @return
     */
    private void setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, "65");
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, "50");
        } else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");
            /**
             * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
             * 开发者如需自定义参数，请参考在线合成参数设置
             */
        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.wav");
    }

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
//            showToast("开始播放");
        }

        @Override
        public void onSpeakPaused() {
//            showToast("暂停播放");
        }

        @Override
        public void onSpeakResumed() {
//            showToast("继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {

        }

        @Override
        public void onCompleted(SpeechError error) {
            if (isHandle) {
                isHandle = false;
            }
            if (error == null) {
//                showToast("播放完成");
            } else if (error != null) {
                showToast(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };


    /**
     * 创建本地MP3
     *
     * @return
     */
    public MediaPlayer createLocalMp3() {
        /**
         * 创建音频文件的方法：
         * 1、播放资源目录的文件：MediaPlayer.create(MainActivity.this,R.raw.beatit);//播放res/raw 资源目录下的MP3文件
         * 2:播放sdcard卡的文件：mediaPlayer=new MediaPlayer();
         *   mediaPlayer.setDataSource("/sdcard/beatit.mp3");//前提是sdcard卡要先导入音频文件
         */
        MediaPlayer mp = MediaPlayer.create(this, R.raw.a);
        mp.stop();
        return mp;
    }

    /**
     * 设置日期
     */
    private void setRiqi() {
        tvDate.setText(DateUtil.getDate("yyyy年MM月dd日"));
        tvWeek.setText(DateUtil.getCurrentWeekDay());

        String goWorkTime = (String) SharedPreferencesUtils.getParam(bContext, KeyConst.kTeacherGoWorkTime, "9:00");
        String offWorkTime = (String) SharedPreferencesUtils.getParam(bContext, KeyConst.kTeacherOffWorkTime, "17:00");
        String ts = "当前教师考勤时间设置为：" + goWorkTime + " ~ " + offWorkTime;
        mainTeacherTs.setText(ts);
    }

    /**
     * 是否显示拍照界面
     *
     * @param showMain
     */
    private void setViewShow(boolean showMain) {
        if (showMain) {
            rlCamera.setVisibility(View.INVISIBLE);
            rlHome.setVisibility(View.VISIBLE);
        } else {
            rlCamera.setVisibility(View.VISIBLE);
            rlHome.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 处理二维码字符串数据
     *
     * @param qrCode
     */
    private void dealResult(String qrCode) {
        // 展示   三种类型，一种是解析的，公司服务器生成二维码
        if (isHandle) {
            showError("正在处理上个数据，请稍后再试！");
            return;
        }
        if (qrCode == null || qrCode.equals("")) {
            isHandle = false;
            ToastUtils.showNormalToast(bContext, "二维码解析异常！");
            return;
        }
        if (isPause) {
            return;
        }
        isHandle = true;
        String decode = DESUtils.decodeUrl(qrCode);
        Log.i("ZLog", "解析数据：" + decode);

        String babyId = null;
        String userId = null;
        String schoolId = null;
        String teacherId = null;
        String teacherState = null;
        if (decode != null) {
            JSONObject jsonObject = JSON.parseObject(decode);
            if (jsonObject.containsKey("babyId")) {
                Log.i("ZLog", "宝宝考勤");

                babyId = jsonObject.getString("babyId");
                userId = jsonObject.getString("userId");
                qrCode = null;

                babyAndPersonInfoByQrcode(babyId, userId, qrCode);

            } else if (jsonObject.containsKey("teacherId")) {
                Log.i("ZLog", "教师考勤");

                schoolId = jsonObject.getString("schoolId");
                teacherId = jsonObject.getString("teacherId");

                if (schoolId.equals(ZSApp.getInstance().getSchoolModel().getSchoolId() + "")) {

                    teacherState = jsonObject.getString("teacherState");
                    if (StringUtils.isEmpty(teacherState)){
                        showError("当前手机版本太低，请及时更新，避免无法使用！");
                        teacherState = "1";
                    }
                    ZSApp.getInstance().setTeacherWorkAttendence(teacherState);
                    /**
                     * 2017/11/21 之前打卡逻辑  每天只有两次考勤  上午和下午  可以记录判断是否迟到
                     * 逻辑修改之后，多次打卡，无法判断是否迟到早退。 以前逻辑保留
                     */
                    //用于区分上下午
                    /*String divierTimeTeacher = "12:00";
                    String[] divierTimeTeacherSpilt = getStringSpilt(divierTimeTeacher,":");
                    int divierTimeTeacherHH = getTimeTeacherHHmm(divierTimeTeacherSpilt,0);

                    //上班时间
                    String goWorkTime = (String) SharedPreferencesUtils.getParam(bContext, KeyConst.kTeacherGoWorkTime, "9:00");
                    String[] goWorkTimeTeacherSpilt = getStringSpilt(goWorkTime,":");
                    int goWorkTimeTeacherHH = getTimeTeacherHHmm(goWorkTimeTeacherSpilt,0);
                    int goWorkTimeTeachermm = getTimeTeacherHHmm(goWorkTimeTeacherSpilt,1);

                    //下班时间
                    String offWorkTime = (String) SharedPreferencesUtils.getParam(bContext, KeyConst.kTeacherOffWorkTime, "17:00");
                    String[] offWorkTimeTeacherSpilt = getStringSpilt(offWorkTime,":");
                    int offWorkTimeTeacherHH = getTimeTeacherHHmm(offWorkTimeTeacherSpilt,0);
                    int offWorkTimeTeachermm = getTimeTeacherHHmm(offWorkTimeTeacherSpilt,1);

                    //当前打卡时间
                    String nowHHmm = DateUtil.getCurrentTime("HH:mm");
                    String[] nowHHmmSpilt = getStringSpilt(nowHHmm,":");
                    int nowHH = getTimeTeacherHHmm(nowHHmmSpilt,0);
                    int nowmm = getTimeTeacherHHmm(nowHHmmSpilt,1);

//                    Log.i("Zlog","上班时间："+goWorkTime+" 下班时间："+offWorkTime + " 打卡时间："+nowHHmm);
                    //当前是上午
                    if (nowHH < divierTimeTeacherHH){

                        //当前时间小于上班时间
                        if (nowHH < goWorkTimeTeacherHH){
                            ZSApp.getInstance().setTeacherWorkAttendence("1");
                            //上班时间为9:30  那么9:27是正常打卡
                        }else if (nowHH == goWorkTimeTeacherHH && nowmm <= goWorkTimeTeachermm){
                            ZSApp.getInstance().setTeacherWorkAttendence("1");
                            //上午迟到
                        }else {
                            ZSApp.getInstance().setTeacherWorkAttendence("2");
                        }
                    }
                    //当前是下午
                    else {
                        //当前时间小于于下班时间  早退
                        if (nowHH < offWorkTimeTeacherHH){
                            ZSApp.getInstance().setTeacherWorkAttendence("3");
                            //5点30 正常下班 5点25 算早退
                        }else if (nowHH == offWorkTimeTeacherHH && nowmm < offWorkTimeTeachermm){
                            ZSApp.getInstance().setTeacherWorkAttendence("3");
                        }else {
                            //时间大于下班时间 就是正常下班
                            ZSApp.getInstance().setTeacherWorkAttendence("4");
                        }
                    }*/
                    presenter.teacherInfoByQrcode(schoolId, teacherId, decode, bContext);
                } else {
                    isHandle = false;
                    showError("该老师不是本幼儿园的，请联系管理员验证。");
                }

            }
        } else {
            babyAndPersonInfoByQrcode(babyId, userId, qrCode);
        }
    }

    public void babyAndPersonInfoByQrcode(String babyId, String userId, String qrcode) {
        boolean isAutoTime = (boolean) getParam(bContext, KeyConst.kIsAutoTime, true);
        if (isAutoTime) {
            int divierTime = (int) SharedPreferencesUtils.getParam(bContext, KeyConst.kAutoTime, 12);
            int HH = Integer.valueOf(DateUtil.getCurrentTime("HH"));
            if (HH < divierTime) {
                isSong = true;
                ZSApp.getInstance().setEventTypeSong();
            } else {
                isSong = false;
                ZSApp.getInstance().setEventTypeJie();
            }
            //显示popupWindow
            presenter.babyAndPersonInfoByQrcode(babyId, userId, qrcode, bContext);
        } else {
            final String finalBabyId = babyId;
            final String finalQrCode = qrcode;
            final String finalUserId = userId;
            PopupWindowAgentSet_portrait.getInstance().setClickListener(new PopupWindowAgentSet_portrait.AgentOnClickListener() {
                @Override
                public void agentOnclick(String eventType) {
                    if (eventType.equals("1")) {
                        isSong = false;
                    } else {
                        isSong = true;
                    }
                    PopupWindowAgentSet_portrait.getInstance().dismiss();
                    presenter.babyAndPersonInfoByQrcode(finalBabyId, finalUserId, finalQrCode, bContext);
                }
            }).showPPWPublish(bContext);
        }
    }

    /**
     * 根据分隔符获取时间
     *
     * @param divierTimeTeacherSpilt
     * @param i
     * @return
     */
    private int getTimeTeacherHHmm(String[] divierTimeTeacherSpilt, int i) {
        String HHmm = divierTimeTeacherSpilt[i];
        return Integer.parseInt(HHmm);
    }

    private String[] getStringSpilt(String divierTimeTeacher, String s) {
        return divierTimeTeacher.split(s);
    }

    /**
     * 教师拍照
     *
     * @param teacherModel
     */
    private void setCameraView(AttendenceTeacherModel teacherModel) {
        setViewShow(false);
        if (teacherModel == null) {
            teacherModel = new AttendenceTeacherModel();
        }

        ImageLoader.load(bContext, R.drawable.head_holder, civBabyHead);
        ImageLoader.loadHeadTarget(bContext, Const.URL_TeacherHead + teacherModel.getTeacher().getHeadImageurl(), civBabyHead);
        tvBabyName.setText("姓名：" + teacherModel.getTeacher().getTeacherName());
        String str = "手机：" + teacherModel.getTeacher().getPhoneNum();
        SpannableString spanText = new SpannableString(str);
        spanText.setSpan(new RelativeSizeSpan(0.7f), 3, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvClass.setText(spanText);
        timeCard_second_person.setVisibility(View.GONE);
    }

    /**
     * 家长拍照
     *
     * @param babyModel
     * @param tcUserModel
     */
    private void setCameraView(BabyModel babyModel, TCUserModel tcUserModel) {
        setViewShow(false);
        if (tcUserModel == null) {
            tcUserModel = new TCUserModel();
        }
        if (babyModel == null) {
            babyModel = new BabyModel();
        }
        timeCard_second_person.setVisibility(View.VISIBLE);
        ImageLoader.load(bContext, R.drawable.head_holder, civBabyHead);
        ImageLoader.loadHeadTarget(bContext, babyModel.getHeadImgUrlAbs(), civBabyHead);
        tvBabyName.setText("姓名：" + babyModel.getBabyName());
        tvClass.setText("班级：" + babyModel.getClassName());

        if (tcUserModel.isPersonTypeAgent()) {
            civUserHead.setImageResource(R.drawable.agent_head);
            String str = "手机：" + tcUserModel.getPhoneNum();
            SpannableString spanText = new SpannableString(str);
            spanText.setSpan(new RelativeSizeSpan(0.7f), 3, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvRelation.setText(spanText);
        } else {
            ImageLoader.load(bContext, R.drawable.head_holder, civUserHead);
            ImageLoader.loadHeadTarget(bContext, tcUserModel.getHeadImageurlAbs(), civUserHead);
            tvRelation.setText("关系：" + tcUserModel.getRelation());
        }
        tvUserName.setText("姓名：" + tcUserModel.getUserName());

    }


    @Override
    public void showSchoolModel(SchoolModel schoolModel) {
        if (schoolModel != null) {
            tvSchoolName.setText(schoolModel.getSchoolName());
            if (schoolModel.getState() == 0) {
                String imei = Settings.System.getString(getContentResolver(), KeyConst.kUUID);
//                String imei = (String) SharedPreferencesUtils.getParam(bContext,KeyConst.kUUID,"");
                presenter.updateState(imei);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PermissionUtils.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults, this)) {
            //成功
        } else {
            showError("获取权限失败");//会对您的使用造成不便
        }
    }

    /**
     * 初始化baby语音
     *
     * @param babyModel
     */
    public void initBabyAudioPlay(BabyModel babyModel) {
        if (null == mTts) {
            if (isHandle){
                isHandle = false;
            }
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            showError("语音初始化失败");
        } else {
            if (mTts.isSpeaking()) {
                mTts.stopSpeaking();
                mTts.destroy();
            }
            FlowerCollector.onEvent(MainActivity_portrait.this, "tts_play");
            String text = "";

            if (babyModel != null) {
                if (isSong) {
                    text = babyModel.getBabyName() + "小朋友，早上好！";
                } else {
                    text = babyModel.getBabyName() + "小朋友，再见！";
                }
            } else {
                text = "小朋友，欢迎你！";
            }
            setParam();
            int code = mTts.startSpeaking(text, mTtsListener);
            if (code != ErrorCode.SUCCESS) {
                showToast("语音合成失败,错误码: " + code);
            }
        }
    }

    @Override
    public void showInfo(BabyModel babyModel, TCUserModel userModel, String babyId, String userId) {
        setCameraView(babyModel, userModel);
        runnable = new MainActivity_portrait.TakePictureRunnable("准备拍照", babyId, userId, false, userModel.getQrcode(),babyModel.getBabyName());
        handler.postDelayed(runnable, 300);
        initBabyAudioPlay(babyModel);
    }

    @Override
    public void uploadHeadImgResult(boolean isSuccess,String name) {
        if (isSuccess){
            switch (ZSApp.getInstance().getTeacherWorkAttendence()) {
                case "1":
                    showToast(name+"老师，到校打卡成功！");
                    break;
                case "2":
                    showToast(name+"老师，离校打卡成功！");
                    break;
            }
        }else {
            if (isSong) {
                showToast(name + "小朋友，早上好！");
            } else {
                showToast(name + "小朋友，再见！");
            }
        }

        if (isHandle){
            isHandle = false;
        }
//            //初始化音频播放  最开始mp3播放  逻辑保留
//            if(mediaPlayer==null){
//                mediaPlayer=createLocalMp3();
//                createState = true;
//            }else if(mediaPlayer.isPlaying()){
//                mediaPlayer.stop();
//                mediaPlayer.release();
//                mediaPlayer = null;
//                mediaPlayer=createLocalMp3();
//            }
//            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    mp.release();//释放音频资源
//                }
//            });
//            if(createState){
//                try {
//                    mediaPlayer.prepare();
//                    mediaPlayer.start();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (surfaceView != null) {
            surfaceView.resumeCamera();
        }
        if (ZSApp.getInstance().isChangeUUID()) {
            ZSApp.getInstance().setChangeUUID(false);
            String uuid = Settings.System.getString(getContentResolver(), KeyConst.kUUID);
            presenter.SchoolByMachineCode(uuid, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mediaPlayer != null){
//            mediaPlayer.stop();
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
        if (mTts != null && mTts.isSpeaking()) {
            mTts.stopSpeaking();
            mTts.destroy();
            mTts = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (mediaPlayer != null){
//            mediaPlayer.stop();
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
        if (surfaceView != null) {
            surfaceView.pauseCamera();
        }
        if (mTts != null && mTts.isSpeaking()) {
            mTts.stopSpeaking();
            mTts.destroy();
            mTts = null;
        }
    }


    @Override
    public void showTeacherInfo(AttendenceTeacherModel teacherModel, String qrCode, String teacherId, String schoolId) {
        setCameraView(teacherModel);
        runnableTeacher = new MainActivity_portrait.TakePictureRunnable("准备拍照", teacherId, schoolId, true, qrCode,teacherModel.getTeacher().getTeacherName());
        handler.postDelayed(runnableTeacher, 300);
        if (null == mTts) {
            if (isHandle){
                isHandle = false;
            }
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            showError("语音初始化失败");
        } else {
            if (mTts.isSpeaking()) {
                mTts.stopSpeaking();
                mTts.destroy();
            }
            String text = "";
            switch (ZSApp.getInstance().getTeacherWorkAttendence()) {
                case "1":
                    text = teacherModel.getTeacher().getTeacherName()+"老师，到校打卡成功！";
                    break;
                case "2":
                    text = teacherModel.getTeacher().getTeacherName()+"老师，离校打卡成功！";
                    break;
            }

            FlowerCollector.onEvent(MainActivity_portrait.this, "tts_play");
            setParam();
            int code = mTts.startSpeaking(text, mTtsListener);
            if (code != ErrorCode.SUCCESS) {
                showToast("语音合成失败,错误码: " + code);
            }
        }
    }

    @Override
    public void onError(String msg) {
        isHandle = false;
    }

    @Override
    public void toUpdate(AppVersionModel model) {
        if (model != null) {
            String nowVersion = AppUtils.getVersionName(bContext);
            if (!nowVersion.equals(model.getVersion())) {
                UpAppDialogActivity.startMustAppDialog(bContext, ZSApp.getInstance().getAppVersionModel(), AppUtils.getVersionName(bContext));
            } else {
                String imei = Settings.System.getString(getContentResolver(), KeyConst.kUUID);
//                String imei = (String) SharedPreferencesUtils.getParam(bContext,KeyConst.kUUID,"");
                presenter.SchoolByMachineCode(imei, bContext);
            }
        } else {
            String imei = Settings.System.getString(getContentResolver(), KeyConst.kUUID);
//            String imei = (String) SharedPreferencesUtils.getParam(bContext,KeyConst.kUUID,"");
            presenter.SchoolByMachineCode(imei, bContext);
        }
    }

    @Override
    public void inputUUIDClick(String uuid) {
        Intent intent = new Intent(bContext, DialogUUIDActivity.class);
        startActivityForResult(intent, KeyConst.kUUIDRequest);
    }

    @Override
    public void updateStateSuccess() {
        showToast("机器码绑定成功！欢迎使用。");
    }

    @Override
    public void updateError() {
        String imei = Settings.System.getString(getContentResolver(), KeyConst.kUUID);
//        String imei = (String) SharedPreferencesUtils.getParam(bContext,KeyConst.kUUID,"");
        presenter.SchoolByMachineCode(imei, bContext);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == KeyConst.kUUIDRequest) {
            String uuid = Settings.System.getString(getContentResolver(), KeyConst.kUUID);
//            String uuid = (String) SharedPreferencesUtils.getParam(bContext,KeyConst.kUUID,"");
            presenter.SchoolByMachineCode(uuid, bContext);
        }
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode <= 6) {
            return super.onKeyDown(keyCode, event);
        }
        if (mScanGun.isMaybeScanning(keyCode, event)) {
            if (startScanTime < 0) {
                startScanTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // 拦截物理键盘事件
        if (event.getKeyCode() > 6) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                this.onKeyDown(event.getKeyCode(), event);
                return true;
            } else if (event.getAction() == KeyEvent.ACTION_UP) {
                this.onKeyUp(event.getKeyCode(), event);
                return true;
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 拍照 Runnable
     */
    private class TakePictureRunnable implements Runnable {
        private String txt;
        private String babyId_teacherId;//teacherId
        private String userId_schoolId;
        private String qrCode;
        private boolean isTeacher;
        private String name;

        public TakePictureRunnable(String txt, String babyId_teacherId, String userId_schoolId, boolean isTeacher, String qrCode,String name) {
            this.txt = txt;
            this.babyId_teacherId = babyId_teacherId;
            this.userId_schoolId = userId_schoolId;
            this.isTeacher = isTeacher;
            this.qrCode = qrCode;
            this.name = name;
        }

        @Override
        public void run() {
            if (isFinishing()) {
                return;
            }
            tvTake.setText(txt);
            if ("准备拍照".equals(txt)) {
//                txt = "0";
                surfaceView.takePicture(new PictureSurfaceViewHalf.PictureCallback() {
                    @Override
                    public void onPictureTaken(String path) {
                        if (path != null) {
                            if (!isTeacher) {
                                presenter.uploadTimeCardImg(name,babyId_teacherId, userId_schoolId, qrCode, path, bContext);
                                isHandle = false;
                            } else {
                                presenter.uploadTecherImage(name,babyId_teacherId, userId_schoolId, qrCode, path, bContext);
                            }

                            tvTake.setText("请对准镜头");
                            tvTake.setTextSize(50);

//                            showToast("拍照成功");
                            setViewShow(true);
                        } else {
                            showToast("拍照失败");
                        }
                    }
                });
            }
//            最开始需求为  等待3秒拍照  现取消这个需求  代码保留
//            else if ("2".equals(txt)) {
//                tvTake.setTextSize(99);
//                txt = "1";
//            } else if ("1".equals(txt)) {
//                tvTake.setTextSize(99);
//                txt = "0";
//                surfaceView.takePicture(new PictureSurfaceViewHalf.PictureCallback() {
//                    @Override
//                    public void onPictureTaken(String path) {
//                        if (path != null) {
//                            presenter.uploadTimeCardImg(babyId, userId, tcUserModel.getQrcode(), path,bContext);
//                            tvTake.setText("请对准镜头");
//                            tvTake.setTextSize(99);
//
//                            showToast("拍照成功");
////                            setViewShow(true);
//                        } else {
//                            showToast("拍照失败");
//                        }
//                    }
//                });
//            }
//            if (!"0".equals(txt)) {
//                runnable2 = new MainActivity_portrait.TakePictureRunnable(txt, babyId, userId, tcUserModel);
//                handler.postDelayed(runnable2, 300);
//                handler.removeCallbacks(this);
//            }
        }
    }
}
