package com.abings.baby.ui.main.fm;


import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.ui.base.MvpView;

import java.util.List;

public interface BabyFragmentMvpView extends MvpView {

    /**
     * 刷新首页
     */
    public void refershIndexCommon(List<AlbumModel> contactModels);
    /**
     * 刷新首页日期
     */
    public void refershIndexDate(JSONArray jsonArray);
}
