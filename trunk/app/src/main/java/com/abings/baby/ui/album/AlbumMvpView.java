package com.abings.baby.ui.album;

import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.ui.base.MvpView;

/**
 * Created by zwj on 2016/10/10.
 * description :
 */

public interface AlbumMvpView<T> extends MvpView<T> {

    /**
     * 获取图片列表
     * @param albumModel
     */
    public void initAlbumImgs(AlbumModel albumModel);

    /**
     * 删除
     * @param imageIds
     * @param imageNames
     */
    public void albumDelImgs(String imageIds, String imageNames);

    public void albumOptFinish();
    public void albumDelFinish();

    public void showDelOneImg(String fileName);
}
