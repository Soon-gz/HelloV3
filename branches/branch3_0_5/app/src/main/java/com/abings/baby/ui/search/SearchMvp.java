package com.abings.baby.ui.search;

import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.InformationModel;
import com.hellobaby.library.ui.base.MvpView;

import java.util.List;

public interface SearchMvp extends MvpView {
    void showListData(List<AlbumModel> lists);
    void showSchoolListData(JSONArray lists);
    void showInformationListData(List<InformationModel> lists);
}
