package com.abings.baby.ui.album;

import android.os.Bundle;

import com.abings.baby.R;
import com.abings.baby.ui.base.BaseFragment;
import com.hellobaby.library.utils.ImageLoader;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by zwj on 2016/10/30.
 * description: 单张图片显示的时候，是viewpager的item
 */

public class AlbumDetailFragment extends BaseFragment {

    @BindView(R.id.fragmentAlbumDetail_photoView)
    PhotoView mPhotoView;

    public static AlbumDetailFragment newInstance(WaterFallItem item){
        AlbumDetailFragment fragment = new AlbumDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(WaterFallItem.kName,item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initDaggerInject() {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_album_detail;
    }

    @Override
    protected void initViewsAndEvents() {

        Bundle arguments = getArguments();
        WaterFallItem item = arguments.getParcelable(WaterFallItem.kName);
        ImageLoader.load(getContext(), item != null ? item.getUrl() : null,mPhotoView);
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showError(String err) {

    }
}
