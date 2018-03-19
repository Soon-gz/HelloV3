package com.abings.baby.teacher.ui.attendanceManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hellobaby.library.Const;
import com.hellobaby.library.utils.DESUtils;
import com.hellobaby.library.utils.ImageLoader;

import java.util.Hashtable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class AttendanceActivity extends BaseTitleActivity implements AttendenceMvpView{

    private final int TEACHER = 0;
    private final int MASTER = 1;
    private final int QRCODE_IMG = 0;
    private final int CHOOSE_IMG = 1;
    private final int CHOOSE_BEFORE = 1;
    private final int CHOOSE_AFTER = 0;
    private final int MAIN_BACKGROUND_COLOR_GO = Color.parseColor("#8EC21F");
    private final int MAIN_BACKGROUND_COLOR_OFF = Color.parseColor("#F7B52D");


    @Inject
    AttendencePresenter presenter;

    @BindView(R.id.teacher_QRCode_iv)
    ImageView qrCode_iv;
    @BindView(R.id.activity_attendance)
    RelativeLayout activityAttendance;
    @BindView(R.id.head_user)
    CircleImageView headUser;
    @BindView(R.id.teacher_name)
    TextView teacherName;

    @BindView(R.id.teacher_QRCode_detail)
    TextView teacherQRCodeDetail;
    @BindView(R.id.teacher_QRCode_ask_for_leave)
    TextView teacherQRCodeAskForLeave;

    @BindView(R.id.teacher_QRCode_fv)
    FrameLayout teacherQRCodeFv;
    @BindView(R.id.teacher_QRCode_ts)
    FrameLayout teacherQRCodeTs;
    @BindView(R.id.teacher_QRCode_btn)
    FrameLayout teacherQRCodeBtn;
    @BindView(R.id.SlideTitle_iv_right)
    ImageView SlideTitle_iv_right;
    @BindView(R.id.SlideTitle_iv_left)
    ImageView SlideTitle_iv_left;
    @BindView(R.id.msg_red_circle)
    ImageView msgRedCircle;

    private boolean isFirstIn = true;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_attendance;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent,this).inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= 19){
            getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
        }
        if (isFirstIn){
            isFirstIn = false;
            return;
        }
        if (presenter != null){
            presenter.selectTeacherLeaveAlert();
        }
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        setBtnLeftClickFinish();
        presenter.attachView(this);
        bTitleBaseRL.setVisibility(View.GONE);
//      初始化界面
        showFrameLayout(CHOOSE_IMG,teacherQRCodeFv);
        showFrameLayout(CHOOSE_BEFORE,teacherQRCodeTs);
        if (ZSApp.getInstance().isSchoolMaster()){
            showFrameLayout(MASTER,teacherQRCodeBtn);
        }else{
            showFrameLayout(TEACHER,teacherQRCodeBtn);
        }

        if (Build.VERSION.SDK_INT >= 19){
            getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
        }

        ImageLoader.loadHeadTarget(this, Const.URL_TeacherHead+ZSApp.getInstance().getTeacherModel().getHeadImageurl(),headUser);
        teacherName.setText(ZSApp.getInstance().getTeacherModel().getTeacherName());

        presenter.selectTeacherLeaveAlert();

        setLight(this,255);
    }

//    到校离校的选择  1 到校  2 离校
    public void setQRimg(String state){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("schoolId",ZSApp.getInstance().getSchoolId());
        jsonObject.put("teacherId", ZSApp.getInstance().getTeacherId());
        jsonObject.put("teacherState",state);
        String encode = DESUtils.encodeUrl(jsonObject.toJSONString());
        Bitmap bitmap = getQRCode(encode);
        qrCode_iv.setImageBitmap(getRoundedCornerBitmap(bitmap,25));
    }

    @OnClick({R.id.SlideTitle_iv_left,R.id.SlideTitle_iv_right,R.id.teacher_QRCode_off,
            R.id.teacher_QRCode_go,R.id.teacher_QRCode_detail,R.id.teacher_QRCode_ask_for_leave})
    public void attenceClick(View view){
        switch (view.getId()){
            case R.id.SlideTitle_iv_left:
                finish();
                break;
            case R.id.SlideTitle_iv_right:
                if (ZSApp.getInstance().isSchoolMaster()){
                    startActivity(new Intent(this,AttendanceSchoolMasterActivity.class));
                }else {
                    startActivity(new Intent(this, AttenceHistoryActivity.class));
                }
                break;
            case R.id.teacher_QRCode_off:
                activityAttendance.setBackgroundColor(MAIN_BACKGROUND_COLOR_OFF);
                showFrameLayout(QRCODE_IMG,teacherQRCodeFv);
                showFrameLayout(CHOOSE_AFTER,teacherQRCodeTs);
                setQRimg("2");
                teacherQRCodeAskForLeave.setTextColor(getResources().getColor(R.color.white));
                teacherQRCodeDetail.setTextColor(getResources().getColor(R.color.white));
                teacherQRCodeDetail.setBackgroundResource(R.drawable.btn_askfor_leave_white);
                SlideTitle_iv_left.setImageResource(R.drawable.title_left_arrow_white);
                SlideTitle_iv_right.setImageResource(R.drawable.select_date_white);
                teacherName.setTextColor(getResources().getColor(R.color.white));

                break;
            case R.id.teacher_QRCode_go:
                activityAttendance.setBackgroundColor(MAIN_BACKGROUND_COLOR_GO);
                showFrameLayout(QRCODE_IMG,teacherQRCodeFv);
                showFrameLayout(CHOOSE_AFTER,teacherQRCodeTs);
                setQRimg("1");
                teacherQRCodeAskForLeave.setTextColor(getResources().getColor(R.color.white));
                teacherQRCodeDetail.setTextColor(getResources().getColor(R.color.white));
                teacherQRCodeDetail.setBackgroundResource(R.drawable.btn_askfor_leave_white);
                SlideTitle_iv_left.setImageResource(R.drawable.title_left_arrow_white);
                SlideTitle_iv_right.setImageResource(R.drawable.select_date_white);
                teacherName.setTextColor(getResources().getColor(R.color.white));

                break;
            case R.id.teacher_QRCode_detail:
                startActivity(new Intent(this,AskForLeaveManagerActivity.class));
                break;
            case R.id.teacher_QRCode_ask_for_leave:
                startActivity(new Intent(this,AskForLeaveDetailActivity.class).putExtra(AskForLeaveDetailActivity.IS_WAIT_DETAIL,1));
                break;
        }
    }

//    显示
    public void showFrameLayout(int view,FrameLayout fm){
        for (int i = 0; i < fm.getChildCount(); i++) {
            if (view == i){
                fm.getChildAt(view).setVisibility(View.VISIBLE);
            }else {
                fm.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    //获取圆角图片
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx){

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    private void setLight(Activity context, int brightness) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        context.getWindow().setAttributes(lp);
    }


    @Override
    public void showData(Object o) {

    }

    /**
     * 将指定的内容生成成二维码,暂时默认一个值
     *
     * @return 返回生成好的二维码
     */
    private Bitmap getQRCode(String content) {
        try {
            Hashtable hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); //编码
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 800, 800, hints);
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void showRedPoint(Integer integer) {
        if (integer > 0){
            msgRedCircle.setVisibility(View.VISIBLE);
        }else{
            msgRedCircle.setVisibility(View.GONE);
        }
    }
}
