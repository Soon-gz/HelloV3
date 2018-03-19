package com.abings.baby.ui.publishvideo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.main.MainActivity;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.ui.publish.video.BaseVideoPlayActivity;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.custom.ShareBottomDialog;
import com.umeng.socialize.media.UMImage;

import javax.inject.Inject;

/**
 * Created by zwj on 2017/1/8.
 * description :
 */

public class VideoPlayActivity extends BaseVideoPlayActivity implements VideoPlayMvpView{

    @Inject
    VideoPlayPresenter presenter;


    @Override
    protected void initDaggerInject() {
        super.initDaggerInject();
        DaggerUtils.getActivityComponent(bActivityComponent,this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        imMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = {"分享","删除", "取消"};
                BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                        if(position==0){
                            String VideoUrl=getIntent().getStringExtra(kVideoUrl);
                            if(VideoUrl.contains("120.27.144.211")){
                                VideoUrl = VideoUrl.replace("120.27.144.211","www.hellobaobei.com.cn");
                            }
                            ShareBottomDialog.getShareUrlBottomDialog(bContext,null, ((AlbumModel) getIntent().getSerializableExtra("AlbumModel")).getContent(),"来自哈喽宝贝",new UMImage(bContext,((AlbumModel)getIntent().getSerializableExtra("AlbumModel")).getVideoImageUrlAbs()),VideoUrl);
                        }
                        else if (position == 1) {
                            AlbumModel albumModel = (AlbumModel) getIntent().getSerializableExtra("AlbumModel");
                            videoDel(albumModel);
                        } else {
                            finish();
                        }
                    }
                });
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
