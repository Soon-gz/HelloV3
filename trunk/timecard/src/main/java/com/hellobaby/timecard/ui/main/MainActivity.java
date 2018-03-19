package com.hellobaby.timecard.ui.main;
/**
 * 版本 1.0.0  屏幕横着  已弃用  存着逻辑以备用
 * 时间 ：2017-06-11
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
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
import com.hellobaby.library.utils.DESUtils;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.utils.PermissionUtils;
import com.hellobaby.library.utils.PhoneUtil;
import com.hellobaby.library.utils.SharedPreferencesUtils;
import com.hellobaby.library.widget.ToastUtils;
import com.hellobaby.timecard.BuildConfig;
import com.hellobaby.timecard.KeyConst;
import com.hellobaby.timecard.R;
import com.hellobaby.timecard.ZSApp;
import com.hellobaby.timecard.data.model.TCUserModel;
import com.hellobaby.timecard.ui.base.BaseActivity;
import com.hellobaby.timecard.ui.camera.PictureSurfaceView;
import com.hellobaby.timecard.ui.setting.login.SettingLoginActivity;
import com.hellobaby.timecard.utils.ScanGun;
import com.hellobaby.timecard.widget.DigitalClock;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.hellobaby.library.utils.SharedPreferencesUtils.getParam;

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject
    MainPresenter presenter;

    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.main_tv_scan)
    ImageView ivScan;
    @BindView(R.id.main_iv_setting)
    ImageView ivSetting;
    //    private DigitalClock tcDate;
//    private DigitalClock tcWeek;
    @BindView(R.id.main_tc_hhmm)
    DigitalClock tcHhmm;
    @BindView(R.id.main_tv_schoolName)
    TextView tvSchoolName;
    @BindView(R.id.main_tv_date)
    TextView tvDate;
    @BindView(R.id.main_tv_week)
    TextView tvWeek;

    @BindView(R.id.main_rl_main)
    RelativeLayout rlMain;
    @BindView(R.id.main_rl_camera)
    RelativeLayout rlCamera;

    @BindView(R.id.camera_tv_take)
    TextView tvTake;

    @BindView(R.id.camera_pictureSurfaceView)
    PictureSurfaceView surfaceView;
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
    @BindView(R.id.camera_tv_schoolName)
    TextView tvCameraSchoolName;
    @BindView(R.id.camera_iv_back)
    ImageView ivBack;
    @BindView(R.id.camera_rl_preview)
    RelativeLayout rlPreview;

    @BindView(R.id.camera_dc_hhmm)
    DigitalClock dcHhmm;
    @BindView(R.id.camera_tv_date)
    TextView tvCameraDate;
    @BindView(R.id.mainCamera_iv_loading)
    ImageView ivLoading;

    @BindView(R.id.timeCard_second_person)
    LinearLayout timeCard_second_person;
    @BindView(R.id.teacher_attend_tishi)
    TextView teacher_attend_tishi;

    private boolean isHandle = false;
    private Handler handler = new Handler();

    private TakePictureRunnable runnable;
    private TakePictureRunnable runnable2;

    private TakePictureRunnableTeacher runnableTeacher;
    private TakePictureRunnableTeacher runnableTeacher2;

    private class TakePictureRunnable implements Runnable {
        private String txt;
        private String babyId;
        private String userId;
        private TCUserModel tcUserModel;

        public TakePictureRunnable(String txt, String babyId, String userId, TCUserModel tcUserModel) {
            this.txt = txt;
            this.babyId = babyId;
            this.userId = userId;
            this.tcUserModel = tcUserModel;
        }

        @Override
        public void run() {
            if (isFinishing()) {
                return;
            }
            tvTake.setText(txt);
            if ("准备拍照".equals(txt)) {
                txt = "2";
            } else if ("2".equals(txt)) {
                tvTake.setTextSize(99);
                txt = "1";
            } else if ("1".equals(txt)) {
                tvTake.setTextSize(99);
                txt = "0";
                surfaceView.takePicture(new PictureSurfaceView.PictureCallback() {
                    @Override
                    public void onPictureTaken(String path) {
                        if (path != null) {
//                            presenter.uploadTimeCardImg(babyId, userId, tcUserModel.getQrcode(), path,bContext);
//                            tvTake.setText("请对准镜头");
                            tvTake.setTextSize(22);

                            showToast("拍照成功");
                            setViewShow(true);
                        } else {
                            showToast("拍照失败");
                        }
                    }
                });
            }
            if (!"0".equals(txt)) {
                runnable2 = new TakePictureRunnable(txt, babyId, userId, tcUserModel);
                handler.postDelayed(runnable2, 330);
                handler.removeCallbacks(this);
            }
        }
    }

    private class TakePictureRunnableTeacher implements Runnable {
        private String txt;
        private String schoolId;
        private String teacherId;
        private String qrCode;

        public TakePictureRunnableTeacher(String txt, String schoolId, String teacherId, String qrCode) {
            this.txt = txt;
            this.schoolId = schoolId;
            this.teacherId = teacherId;
            this.qrCode = qrCode;
        }

        @Override
        public void run() {
            if (isFinishing()) {
                return;
            }
            tvTake.setText(txt);
            if ("准备拍照".equals(txt)) {
                txt = "2";
            } else if ("2".equals(txt)) {
                tvTake.setTextSize(99);
                txt = "1";
            } else if ("1".equals(txt)) {
                tvTake.setTextSize(99);
                txt = "0";
                surfaceView.takePicture(new PictureSurfaceView.PictureCallback() {
                    @Override
                    public void onPictureTaken(String path) {
                        if (path != null) {
//                            presenter.uploadTecherImage(teacherId, schoolId, qrCode, path,bContext);
//                            tvTake.setText("请对准镜头");
                            tvTake.setTextSize(22);

                            setViewShow(true);
                        } else {
                            showToast("拍照失败");
                        }
                    }
                });
            }
            if (!"0".equals(txt)) {
                runnableTeacher2 = new TakePictureRunnableTeacher(txt, schoolId, teacherId, qrCode);
                handler.postDelayed(runnableTeacher2, 330);
                handler.removeCallbacks(this);
            }
        }
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        PermissionUtils.getInstance().requestPermission(this);
//        new CheckVersionThread(this, Const.apkDownPath, KeyConst.url_timeCard).start();
        String imei = PhoneUtil.getDeviceIMEI(bContext);
        presenter.SchoolByMachineCode(imei,bContext);

        tvSchoolName = (TextView) findViewById(R.id.main_tv_schoolName);

        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(bContext, SettingLoginActivity.class));
            }
        });
        if (BuildConfig.DEBUG && Const.baseUrl.contains("192.168.1.")) {
            //TODO 测试按钮
            tvResult.setVisibility(View.VISIBLE);
            ivScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rlMain.setVisibility(View.GONE);
                    rlCamera.setVisibility(View.VISIBLE);
                }
            });
        } else {
            tvResult.setVisibility(View.INVISIBLE);
        }

        tcHhmm = (DigitalClock) findViewById(R.id.main_tc_hhmm);
        tcHhmm.setFormatHHmm();


        // 设置key事件最大间隔，默认20ms，部分低端扫码枪效率低
        ScanGun.setMaxKeysInterval(100);
        mScanGun = new ScanGun(new ScanGun.ScanGunCallBack() {
            @Override
            public void onScanFinish(String scanResult) {
//                showProgress(true);
                endScanTime = System.currentTimeMillis();
                Log.i("ZLog", "end=>" + endScanTime + "  ; start=>" + startScanTime + "  ;扫描间隔" + (endScanTime - startScanTime) + "  ;scanResult=>" + scanResult);
                endScanTime = -1;
                startScanTime = -1;
                dealResult(scanResult);
            }
        });

//        if (!isCameraCanUse()) {
//            showError("连接摄像头失败");
//        }
        setViewShow(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setViewShow(true);
                ivLoading.setVisibility(View.GONE);
            }
        }, 700);

    }

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

        if (ZSApp.getInstance().getSchoolModel() != null) {
            tvCameraSchoolName.setText(ZSApp.getInstance().getSchoolModel().getSchoolName());
        }


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                if (runnable2 != null) {
                    handler.removeCallbacks(runnable2);
                }
                if (runnableTeacher != null){
                    handler.removeCallbacks(runnableTeacher);
                }
                if (runnableTeacher2 != null){
                    handler.removeCallbacks(runnableTeacher2);
                }
                setViewShow(true);
                tvTake.setText("请对准镜头");
                tvTake.setTextSize(22);
            }
        });

        dcHhmm.setFormatHHmm();


        surfaceView.setWH(rlPreview);


    }


//    /**
//     * 测试当前摄像头能否被使用
//     *
//     * @return
//     */
//    public static boolean isCameraCanUse() {
//        boolean canUse = true;
//        Camera mCamera = null;
//        try {
//            mCamera = Camera.open();
//        } catch (Exception e) {
//            canUse = false;
//        }
//        if (canUse) {
//            mCamera.release();
//            mCamera = null;
//        }
//        return canUse;
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PermissionUtils.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults, this)) {
            //成功
        } else {
            showError("获取权限失败");//会对您的使用造成不便
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        isPause = false;
        setRiqi();
        String goWorkTime = (String) SharedPreferencesUtils.getParam(bContext, KeyConst.kTeacherGoWorkTime, "9:00");
        String offWorkTime = (String) SharedPreferencesUtils.getParam(bContext, KeyConst.kTeacherOffWorkTime, "17:00");
        teacher_attend_tishi.setText("当前教师考勤时间为 "+goWorkTime + " - "+ offWorkTime);
        if (surfaceView != null) {
            surfaceView.resumeCamera();
        }
    }

    private void setRiqi() {
        tvDate.setText(DateUtil.getDate("yyyy年MM月dd日"));
        tvWeek.setText(DateUtil.getCurrentWeekDay());
        tvCameraDate.setText(DateUtil.getDate("yyyy年MM月dd日"));
    }


    private boolean isPause = false;

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
        if (handler != null && runnable != null ) {
            handler.removeCallbacks(runnable);
        }
        if (handler != null && runnableTeacher != null) {
            handler.removeCallbacks(runnableTeacher);
        }
        if (surfaceView != null) {
            surfaceView.pauseCamera();
        }
    }

    private void dealResult(String qrCode) {
        // 展示   三种类型，一种是解析的，公司服务器生成二维码
        if (isHandle) {
            showError("正在处理上个数据，请稍后再试！");
            return;
        }
        if (qrCode == null || qrCode.equals("")){
            isHandle = false;
            ToastUtils.showNormalToast(bContext,"二维码解析异常！");
            return;
        }
        if (isPause) {
            return;
        }
        isHandle = true;
        String decode = DESUtils.decodeUrl(qrCode);
        Log.i("ZLog", "解析数据："+decode);

        String babyId = null;
        String userId = null;
        String schoolId = null;
        String teacherId = null;
        if (decode != null) {
            JSONObject jsonObject = JSON.parseObject(decode);
            if (jsonObject.containsKey("babyId")) {
                Log.i("ZLog", "宝宝考勤");
                String name = jsonObject.getString("name");
                String type = jsonObject.getString("type");
                babyId = jsonObject.getString("babyId");
                userId = jsonObject.getString("userId");
                qrCode = null;

                babyAndPersonInfoByQrcode(babyId,userId,qrCode);

            }else if (jsonObject.containsKey("teacherId")){
                Log.i("ZLog", "教师考勤");

                String nameTeacher = jsonObject.getString("name");
                String typeTeacher = jsonObject.getString("type");
                schoolId = jsonObject.getString("schoolId");
                teacherId = jsonObject.getString("teacherId");

                if (schoolId.equals(ZSApp.getInstance().getSchoolModel().getSchoolId()+"")){
                    //用于区分上下午
                    String divierTimeTeacher = "12:00";
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

                    Log.i("Zlog","上班时间："+goWorkTime+" 下班时间："+offWorkTime + " 打卡时间："+nowHHmm);
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
                    }
                    presenter.teacherInfoByQrcode(schoolId, teacherId, decode,bContext);
                }else {
                    isHandle = false;
                    showError("该老师不是本幼儿园的，请联系管理员验证。");
                }

            }
        } else {
            //考勤机
            babyAndPersonInfoByQrcode(babyId,userId,qrCode);
        }
    }

    public void babyAndPersonInfoByQrcode(String babyId, String userId, String qrcode){
        boolean isAutoTime = (boolean) getParam(bContext, KeyConst.kIsAutoTime, true);
        if (isAutoTime) {
            int divierTime = (int) SharedPreferencesUtils.getParam(bContext, KeyConst.kAutoTime, 12);
            int HH = Integer.valueOf(DateUtil.getCurrentTime("HH"));
            if (HH < divierTime) {
                ZSApp.getInstance().setEventTypeSong();
            } else {
                ZSApp.getInstance().setEventTypeJie();
            }
            //显示popupWindow
            presenter.babyAndPersonInfoByQrcode(babyId, userId, qrcode,bContext);
        } else {
            final String finalBabyId = babyId;
            final String finalQrCode = qrcode;
            final String finalUserId = userId;
            PopupWindowAgentSet.getInstance().setClickListener(new PopupWindowAgentSet.AgentOnClickListener() {
                @Override
                public void agentOnclick(String eventType) {
                    PopupWindowAgentSet.getInstance().dismiss();
                    presenter.babyAndPersonInfoByQrcode(finalBabyId, finalUserId, finalQrCode,bContext);
                }
            }).showPPWPublish(bContext);
        }
    }

    private int getTimeTeacherHHmm(String[] divierTimeTeacherSpilt, int i) {
        String HHmm = divierTimeTeacherSpilt[i];
        return Integer.parseInt(HHmm);
    }

    private String[] getStringSpilt(String divierTimeTeacher, String s) {
        return divierTimeTeacher.split(s);
    }

    @Override
    public void showSchoolModel(SchoolModel schoolModel) {
        if (schoolModel != null) {
            tvSchoolName.setText(schoolModel.getSchoolName());
        }
    }

    @Override
    public void showInfo(BabyModel babyModel, TCUserModel userModel, String babyId, String userId) {
        setCameraView(babyModel, userModel);
        runnable = new TakePictureRunnable("准备拍照", babyId, userId, userModel);
        handler.postDelayed(runnable, 1000);
    }

    @Override
    public void uploadHeadImgResult(boolean isSuccess,String name) {
        if (isSuccess){
            switch (ZSApp.getInstance().getTeacherWorkAttendence()){
                case "1":
                    showToast("上班打卡成功！");
                    break;
                case "2":
                    showToast("上班打卡迟到！");
                    break;
                case "3":
                    showToast("下班打卡早退！");
                    break;
                case "4":
                    showToast("下班打卡成功！");
                    break;
            }
        }

    }

    //通过二维码获取教师信息
    @Override
    public void showTeacherInfo(AttendenceTeacherModel teacherModel, String qrCode, String teacherId, String schoolId) {
        setCameraView(teacherModel);
        runnableTeacher = new TakePictureRunnableTeacher("准备拍照", schoolId, teacherId, qrCode);
        handler.postDelayed(runnableTeacher, 1000);
    }

    @Override
    public void onError(String msg) {
        isHandle = false;
    }

    @Override
    public void toUpdate(AppVersionModel model) {

    }

    @Override
    public void inputUUIDClick(String uuid) {

    }

    @Override
    public void updateStateSuccess() {

    }

    @Override
    public void updateError() {

    }


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

        if (ZSApp.getInstance().getSchoolModel() != null) {
            tvCameraSchoolName.setText(ZSApp.getInstance().getSchoolModel().getSchoolName());
        }


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                if (runnable2 != null) {
                    handler.removeCallbacks(runnable2);
                }
                setViewShow(true);
                tvTake.setText("请对准镜头");
                tvTake.setTextSize(22);
            }
        });

        dcHhmm.setFormatHHmm();


        surfaceView.setWH(rlPreview);

    }

    private void setViewShow(boolean showMain) {
        if (showMain) {
            isHandle = false;
            rlCamera.setVisibility(View.GONE);
            rlMain.setVisibility(View.VISIBLE);
        } else {
            isHandle = true;
            rlMain.setVisibility(View.GONE);
            rlCamera.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PopupWindowAgentSet.getInstance().dismiss();
    }

    private ScanGun mScanGun = null;
    long startScanTime = -1;
    long endScanTime = -1;

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

}