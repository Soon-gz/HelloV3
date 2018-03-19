package com.abings.baby.teacher.ui.PrizeDraw.sharePrize;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.ui.PrizeDraw.LuckyDrawActivity;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.utils.ScreenUtils;
import com.hellobaby.library.widget.ToastUtils;
import com.hellobaby.library.widget.custom.ShareBottomDialog;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SharePrizeActivity extends BaseTitleActivity {

    public static final int IMAGE_SIZE = 32768;//微信分享图片大小限制

    @BindView(R.id.share_image)
    ImageView share_image;
    @BindView(R.id.share_head_img)
    CircleImageView share_head_img;
//    @BindView(R.id.userName)
//    TextView userName;
//    @BindView(R.id.goodsName)
//    TextView goodsNameTv;
//    @BindView(R.id.content_text)
//    TextView content_text;
    @BindView(R.id.share_all_image)
    ScrollView share_all_image;

    Typeface typeface;
    boolean isFirstIn = true;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_share_prize;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isFirstIn){
            isFirstIn = false;
            return;
        }
        ToastUtils.showNormalToast(this,"领取成功，您的订单已开始处理~");
        finish();
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {

        typeface =  Typeface.createFromAsset(getAssets(), "fonts/Action-Man/Action_Man_Bold_Italic.ttf");
        bIvLeft.setVisibility(View.GONE);
        setTitleText("分享商品");


        String goodsName = getIntent().getStringExtra("goodsName");
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String headImage = getIntent().getStringExtra("headImage");

        ImageLoader.load(this, Const.URL_prizeDrawImg+imageUrl,share_image);
        ImageLoader.loadHeadTarget(this, Const.URL_TeacherHead+headImage ,share_head_img);
//        content_text.setTypeface(typeface);
//        userName.setTypeface(typeface);
//        goodsNameTv.setTypeface(typeface);

//        userName.setText(ZSApp.getInstance().getTeacherModel().getTeacherName());
//        goodsNameTv.setText(goodsName);
    }

    @OnClick(R.id.share_prize_btn)
    public void click(View view){
        Bitmap bmp = ScreenUtils.getBitmapByView(share_all_image);
        int options = 100;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);
        int options2 = 100;
        while (output.toByteArray().length > IMAGE_SIZE && options2 != 10) {
            output.reset(); //清空baos
            bmp.compress(Bitmap.CompressFormat.JPEG, options2, output);//这里压缩options%，把压缩后的数据存放到baos中
            options2 -= 10;
        }
        bmp = BitmapFactory.decodeByteArray(output.toByteArray(), 0, output.toByteArray().length);
        ShareBottomDialog.getSharePicBottomDialogPrize(this,bmp, LuckyDrawActivity.class);
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
