package com.abings.baby.ui.onlytext;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.base.BaseActivity;
import com.abings.baby.ui.main.MainActivity;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.utils.ScreenUtils;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.custom.ShareBottomDialog;
import com.umeng.socialize.media.UMImage;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * x消息展示
 */
public class TextActivity extends BaseActivity implements TextMvpView {
    public static final int IMAGE_SIZE = 32768;//微信分享图片大小限制
    @Inject
    TextPresenter presenter;
    @BindView(R.id.libTitle_iv_right)
    ImageView bIvRight;
    @BindView(R.id.libTitle_tv_title)
    TextView TextTitle;
    @BindView(R.id.input_text)
    TextView TextContent;
    private LinearLayout llBottom;
    private TextView tvTime;
    private TextView tvUserName;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_text;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }


    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        presenter.attachView(this);

//        bTvTitle.setTextColor(getResources().getColor(R.color.white));
//        setTitleBackground(R.color.publish_background);
//        setBtnLeftClickFinish();
//        setBtnLeftDrawable(getResources().getDrawable(R.drawable.title_left_arrow_white));
//        setBtnRightDrawable(getResources().getDrawable(R.drawable.title_more_white));
        final ScrollView sc = (ScrollView) findViewById(R.id.textactivity_sv);
        final AlbumModel albumModel = (AlbumModel) getIntent().getSerializableExtra("AlbumModel");
        TextTitle.setText(albumModel.getTitle());
        TextContent.setText(albumModel.getContent());

        {
            //底部的创建者和时间
            llBottom = (LinearLayout) findViewById(R.id.lib_bottom_LL);
            tvTime = (TextView) findViewById(R.id.lib_bottom_tv_time);
            tvUserName = (TextView) findViewById(R.id.lib_bottom_tv_userName);
            if (albumModel.getUserName() == null || albumModel.getUserName().isEmpty() || albumModel.getLastmodifyTime() == null || albumModel.getLastmodifyTime().isEmpty()) {
                //时间或者创建者为空，不显示底部的菜单
                llBottom.setVisibility(View.GONE);
            } else {
                llBottom.setVisibility(View.VISIBLE);
                tvTime.setText(albumModel.getLastmodifyTimeNYRHm());
                tvUserName.setText(albumModel.getUserName());
            }

        }


        bIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ZSApp.getInstance().getBabyModel().getIsCreator().equals(ZSApp.getInstance().getUserId())||albumModel.getUserId().equals(ZSApp.getInstance().getUserId())) {
                    final String[] items = {"编辑", "删除", "分享", "取消"};
                    BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                            if (position == 0) {
                                Bundle b = new Bundle();
                                b.putSerializable("AlbumModel", albumModel);
                                Intent intent = new Intent(TextActivity.this, EditTextActivity.class);
                                intent.putExtras(b);
                                startActivityForResult(intent, MainActivity.mainRequestCode);
                            } else if (position == 1) {
                                presenter.logDel(albumModel);
                                setResult(MainActivity.BabyFragmentEditResultCode);
                            } else if (position == 2) {
                                Bitmap bmp = ScreenUtils.getBitmapByView(sc);
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
                                UMImage image = new UMImage(bContext, bmp);
//                                ShareBottomDialog.getShareTextBottomDialog(bContext, null, image);
                                ShareBottomDialog.getSharePicBottomDialog(bContext,bmp);
                            }
                        }
                    });
                } else {
                    final String[] items = {"分享", "取消"};
                    BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                            if (position == 0) {
                                Bitmap bmp = ScreenUtils.getBitmapByView(sc);
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
                                UMImage image = new UMImage(bContext, bmp);
//                                ShareBottomDialog.getShareTextBottomDialog(bContext, null, image);
                                ShareBottomDialog.getSharePicBottomDialog(bContext,bmp);
                            }
                        }
                    });
                }
            }
        });
    }


    @Override
    public void showData(Object o) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MainActivity.BabyFragmentEditResultCode) {
            setResult(MainActivity.BabyFragmentEditResultCode);
            finish();
        }
    }

    @Override
    public void uploadSuccess() {
        setResult(MainActivity.BabyFragmentEditResultCode);
        finish();
    }

    @OnClick(R.id.libTitle_iv_left)
    public void clickFinish() {
        finish();
    }
}
