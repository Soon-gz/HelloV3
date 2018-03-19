package com.abings.baby.teacher.ui.class_assistant;

import com.hellobaby.library.data.model.ClassAssistantModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.ui.base.MvpView;

import java.util.List;

/**
 * Created by zwj on 2017/5/3.
 * description :
 */

public interface ClassAssistantFragmentMvpView<T> extends MvpView<T> {
    void showClassAssistantList(List<ClassAssistantModel> list, PageModel pageModel);
    void showClassAssistantLoadMoreList(List<ClassAssistantModel> list, PageModel pageModel);
}
