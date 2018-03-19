package com.abings.baby.ui.main.fm;


import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.ui.base.MvpView;

import java.util.List;

public interface BabyFragmentMvpView extends MvpView {

    /**
     * 刷新首页
     */
    public void refershIndexCommon(List<AlbumModel> contactModels);

    /**
     * 刷新首页
     */
    public void addIndexCommon(List<AlbumModel> contactModels, PageModel page);
    /**
     * 刷新首页日期
     */
    public void refershIndexDate(JSONArray jsonArray);

    /**
     * 上传数据完成
     */
    public void uploadFinish(String albumId);

    /**
     * 上传数据完成
     */
    public void uploadProgress(double text);
}
