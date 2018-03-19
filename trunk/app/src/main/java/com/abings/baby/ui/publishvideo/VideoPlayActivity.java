package com.abings.baby.ui.publishvideo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.abings.baby.ZSApp;
import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.main.MainActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.ui.publish.video.BaseVideoPlayActivity;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.custom.ShareBottomDialog;

import javax.inject.Inject;

/**
 * Created by zwj on 2017/1/8.
 * description :
 */

public class VideoPlayActivity extends BaseVideoPlayActivity implements VideoPlayMvpView {

    @Inject
    VideoPlayPresenter presenter;
    AlbumModel albumModel;


    @Override
    protected void initDaggerInject() {
        super.initDaggerInject();
        DaggerUtils.getActivityComponent(bActivityComponent, this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        albumModel = (AlbumModel) getIntent().getSerializableExtra("AlbumModel");
        titleText.setText(albumModel.getContent());
        LogZS.i(albumModel.toString());
        if (albumModel.getUserName() == null || albumModel.getUserName().isEmpty() || albumModel.getLastmodifyTime() == null || albumModel.getLastmodifyTime().isEmpty()) {
            //时间或者创建者为空，不显示底部的菜单
            llBottom.setVisibility(View.GONE);
        } else {
//            llBottom.setVisibility(View.VISIBLE);
            try {
                long time = Long.parseLong(albumModel.getLastmodifyTime());
                tvTime.setText(DateUtil.getFormatTimeFromTimestamp(time,"yyyy-MM-dd HH:mm"));
            }catch (NumberFormatException e){
                tvTime.setText(albumModel.getLastmodifyTime());
            }

            tvUserName.setText(albumModel.getUserName());
        }
        imMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ZSApp.getInstance().getBabyModel().getIsCreator().equals(ZSApp.getInstance().getUserId()) || albumModel.getUserId().equals(ZSApp.getInstance().getUserId())) {
                    final String[] items = {"保存到本地", "分享", "删除", "取消"};
                    BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                            if (position == 0) {
                                saveVideo();
                            } else if (position == 1) {
                                String VideoUrl = getIntent().getStringExtra(kVideoUrl);
                                String imgUrl = albumModel.getVideoImageUrlAbs();
                                if (VideoUrl.contains("120.27.144.211")) {
                                    VideoUrl = VideoUrl.replace("120.27.144.211", "www.hellobaobei.com.cn");
                                } else if (VideoUrl.contains(Const.baseImgUrl)) {
                                    VideoUrl = VideoUrl.replace(Const.baseImgUrl, "http://www.hellobaobei.com.cn:8899/");
//                                    imgUrl = albumModel.getVideoImageUrlAbs().replace(Const.baseImgUrl, "http://www.hellobaobei.com.cn:8899/");
                                }
                                ShareBottomDialog.getShareVideoBottomDialog(bContext, imgUrl, VideoUrl, "", albumModel.getContent());
//                                ShareBottomDialog.getShareUrlBottomDialog(bContext, null, ((AlbumModel) getIntent().getSerializableExtra("AlbumModel")).getContent(), "来自哈喽宝贝", new UMImage(bContext, ((AlbumModel) getIntent().getSerializableExtra("AlbumModel")).getVideoImageUrlAbs()), VideoUrl);
                            } else if (position == 2) {
                                videoDel(albumModel);
                            } else {
                                finish();
                            }
                        }
                    });
                } else {
                    final String[] items = {"分享", "取消"};
                    BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                            if (position == 0) {
                                String VideoUrl = getIntent().getStringExtra(kVideoUrl);
                                String imgUrl = albumModel.getVideoImageUrlAbs();
                                if (VideoUrl.contains("120.27.144.211")) {
                                    VideoUrl = VideoUrl.replace("120.27.144.211", "www.hellobaobei.com.cn");
                                } else if (VideoUrl.contains(Const.baseImgUrl)) {
                                    VideoUrl = VideoUrl.replace(Const.baseImgUrl, "http://www.hellobaobei.com.cn:8899/");
//                                    imgUrl = albumModel.getVideoImageUrlAbs().replace(Const.baseImgUrl, "http://www.hellobaobei.com.cn:8899/");
                                }
                                ShareBottomDialog.getShareVideoBottomDialog(bContext, imgUrl, VideoUrl, "来自哈喽宝贝家长端", albumModel.getContent());
//                                ShareBottomDialog.getShareUrlBottomDialog(bContext, null, ((AlbumModel) getIntent().getSerializableExtra("AlbumModel")).getContent(), "来自哈喽宝贝", new UMImage(bContext, , VideoUrl);
                            } else {
                                finish();
                            }
                        }
                    });
                }
            }
        });
        presenter.attachView(this);
    }


    @Override
    protected void VideoDelFromAlbum(AlbumModel albumModel) {
        presenter.videoDel(albumModel);
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void delSuccess() {
        setResult(MainActivity.BabyFragmentEditResultCode);
        finish();
    }
}
