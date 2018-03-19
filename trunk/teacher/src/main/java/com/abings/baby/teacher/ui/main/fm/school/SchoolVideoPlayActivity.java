package com.abings.baby.teacher.ui.main.fm.school;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.hellobaby.library.Const;
import com.hellobaby.library.ui.publish.video.BaseVideoPlayActivity;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.custom.ShareBottomDialog;

/**
 * Created by zwj on 2017/3/1.
 * description : 校园内的小视频播放
 */

public class SchoolVideoPlayActivity extends BaseVideoPlayActivity {

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        imMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] items = {"保存到本地", "分享","取消"};
                BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                        if (position == 0) {
                            saveVideo();
                        } else if (position == 1) {
                            String VideoUrl = getIntent().getStringExtra(kVideoUrl);
                            String imgUrl = getIntent().getStringExtra(kVideoImageUrl);
                            if (VideoUrl.contains("120.27.144.211")) {
                                VideoUrl = VideoUrl.replace("120.27.144.211", "www.hellobaobei.com.cn");
                            } else if (VideoUrl.contains(Const.baseImgUrl)) {
                                VideoUrl = VideoUrl.replace(Const.baseImgUrl, "http://www.hellobaobei.com.cn:8899/");
//                                imgUrl = imgUrl.replace(Const.baseImgUrl, "http://www.hellobaobei.com.cn:8899/");
                            }
                            String title = getIntent().getStringExtra(kVideoTitle);

                            ShareBottomDialog.getShareVideoBottomDialog(bContext, imgUrl, VideoUrl, "来自哈喽宝贝家长端", title);
                        }else{

                        }
                    }
                });
            }
        });
    }
}
