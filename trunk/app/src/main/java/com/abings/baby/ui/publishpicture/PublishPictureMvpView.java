package com.abings.baby.ui.publishpicture;


import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.ui.base.MvpView;

public interface PublishPictureMvpView extends MvpView {

    /**
     * 上传数据完成
     */
    public void uploadFinish(String albumId);

    /**
     * 上传数据完成
     */
    public void uploadProgress(String text);

    void reflushalbumlist(JSONArray josnarry);
}
