package com.abings.baby.teacher.ui.publishpicture;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.ui.publish.video.BaseVideoPlayActivity;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.custom.ShareBottomDialog;

import javax.inject.Inject;

/**
 * Created by zwj on 2017/1/8.
 * description :
 */

public class VideoPlayActivity extends BaseVideoPlayActivity {

    private AlbumModel albumModel;

    @Override
    protected void initDaggerInject() {
        super.initDaggerInject();
        DaggerUtils.getActivityComponent(bActivityComponent, this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        String delType = getIntent().getStringExtra(kMoreType);
        if (MORE_TYPE_INFORMATION.equals(delType)) {
            albumModel = (AlbumModel) getIntent().getSerializableExtra("AlbumModel");
            if (albumModel != null) {
                tvUserName.setText(albumModel.getUserName());
                tvTime.setText(DateUtil.getFormatTimeFromTimestamp(Long.parseLong(albumModel.getLastmodifyTime()),"yyyy-MM-dd HH:mm"));
                titleText.setText(albumModel.getContent());
                imMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String[] items = {"保存到本地", "分享", "取消"};
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
                                } else {
                                }
                            }
                        });
                    }
                });
            } else {
                imMore.setVisibility(View.GONE);
            }
        }
    }
}
