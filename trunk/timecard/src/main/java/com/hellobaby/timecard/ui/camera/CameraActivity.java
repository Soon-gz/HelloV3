package com.hellobaby.timecard.ui.camera;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hellobaby.library.data.model.AppVersionModel;
import com.hellobaby.library.data.model.AttendenceTeacherModel;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.SchoolModel;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.timecard.R;
import com.hellobaby.timecard.ZSApp;
import com.hellobaby.timecard.data.model.TCUserModel;
import com.hellobaby.timecard.ui.base.BaseActivity;
import com.hellobaby.timecard.ui.main.MainMvpView;
import com.hellobaby.timecard.ui.main.MainPresenter;
import com.hellobaby.timecard.widget.DigitalClock;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zwj on 2017/3/13.
 * description :
 */

public class CameraActivity extends BaseActivity implements MainMvpView {


    @Inject
    MainPresenter presenter;

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
    TextView tvSchoolName;
    @BindView(R.id.camera_iv_back)
    ImageView ivBack;
    @BindView(R.id.camera_rl_preview)
    RelativeLayout rlPreview;
    private Handler handler;
    private BabyModel babyModel;
    private TCUserModel tcUserModel;
    private DigitalClock dcHhmm;
    private DigitalClock dcDate;

    @Override
    public void showData(Object o) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_camera;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        Log.i("ZLog","CameraActivity--1-->"+System.currentTimeMillis());
        presenter.attachView(this);
        babyModel = getIntent().getParcelableExtra("BabyModel");
        tcUserModel = getIntent().getParcelableExtra("TCUserModel");
        if (tcUserModel == null) {
            tcUserModel = new TCUserModel();
        }
        if (babyModel == null) {
            babyModel = new BabyModel();
        }
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
            ImageLoader.loadHeadTarget(bContext, tcUserModel.getHeadImageurlAbs(), civUserHead);
            tvRelation.setText("关系：" + tcUserModel.getRelation());
        }
        tvUserName.setText("姓名：" + tcUserModel.getUserName());

        if (ZSApp.getInstance().getSchoolModel() != null) {
            tvSchoolName.setText(ZSApp.getInstance().getSchoolModel().getSchoolName());
        }


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dcHhmm = (DigitalClock) findViewById(R.id.camera_dc_hhmm);
        dcDate = (DigitalClock) findViewById(R.id.camera_dc_date);
        dcHhmm.setFormatHHmm();
        dcDate.setFormatyyyyMMddEEEE();



        surfaceView.setWH(rlPreview);

        handler = new Handler();
        handler.postDelayed(runnable, 1000);
        tvTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private TakePictureRunnable runnable = new TakePictureRunnable("准备拍照");

    private class TakePictureRunnable implements Runnable {
        private String txt;

        public TakePictureRunnable(String txt) {
            this.txt = txt;
        }

        @Override
        public void run() {
            if (isFinishing()) {
                return;
            }
            tvTake.setText(txt);
            if ("准备拍照".equals(txt)) {
                txt = "3";
            } else if ("3".equals(txt)) {
                tvTake.setTextSize(99);
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
//                            presenter.uploadTimeCardImg(getIntent().getStringExtra("babyId"), getIntent().getStringExtra("userId"), tcUserModel.getQrcode(), path,bContext);
                        } else {
                            showToast("拍照失败");
                        }
                    }
                });
            }
            if (!"0".equals(txt)) {
                handler.postDelayed(new TakePictureRunnable(txt), 500);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(surfaceView!=null){
            surfaceView.resumeCamera();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
        if(surfaceView!=null){
            surfaceView.pauseCamera();
        }
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showSchoolModel(SchoolModel schoolModel) {

    }

    @Override
    public void showInfo(BabyModel babyModel, TCUserModel userModel, String babyId, String user) {

    }

    @Override
    public void uploadHeadImgResult(boolean isSuccess,String name) {
        if (isSuccess) {
            showToast("操作成功");
            finish();
        }
    }

    @Override
    public void showTeacherInfo(AttendenceTeacherModel teacherModel, String qrCode, String teacherId, String schoolId) {

    }

    @Override
    public void onError(String msg) {

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


    @Override
    public void showError(String err) {
        super.showError(err);
        finish();
    }
}
