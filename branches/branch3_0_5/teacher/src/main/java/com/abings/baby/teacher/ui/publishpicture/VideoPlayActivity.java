package com.abings.baby.teacher.ui.publishpicture;

import android.os.Bundle;

import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.ui.publish.video.BaseVideoPlayActivity;

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
        presenter.attachView(this);
    }


    @Override
    public void showData(Object o) {

    }

    @Override
    public void delSuccess() {
        finish();
    }
}
