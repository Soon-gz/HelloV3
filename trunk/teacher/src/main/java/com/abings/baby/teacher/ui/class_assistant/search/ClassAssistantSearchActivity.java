package com.abings.baby.teacher.ui.class_assistant.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ui.base.BaseActivity;
import com.abings.baby.teacher.ui.class_assistant.ClassAssistantActivity;
import com.abings.baby.teacher.ui.class_assistant.ClassAssistantFragment;
import com.abings.baby.teacher.ui.class_assistant.ClassAssistantFragmentMvpView;
import com.abings.baby.teacher.ui.class_assistant.ClassAssistantFragmentPresenter;
import com.abings.baby.teacher.ui.class_assistant.adapter.ClassAssistantAdapter;
import com.hellobaby.library.data.model.ClassAssistantModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.utils.AppUtils;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.umeng.socialize.utils.ContextUtil.getContext;

/**
 * Created by zwj on 2017/6/5.
 * 课堂助手搜索
 */

public class ClassAssistantSearchActivity extends BaseActivity implements ClassAssistantFragmentMvpView{

    @Inject
    ClassAssistantFragmentPresenter presenter;

    @BindView(R.id.classAssistantSearch_iv_left)
    ImageView ivLeft;
    @BindView(R.id.classAssistantSearch_et_content)
    EditText etContent;
    @BindView(R.id.classAssistantSearch_iv_right)
    TextView ivRight;
    @BindView(R.id.classAssistantSearch_rv)
    RecyclerView recyclerView;
    private List<ClassAssistantModel> list;
    private ClassAssistantAdapter adapter;

    @Override
    public void showData(Object o) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_class_assistant_search;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(ClassAssistantSearchActivity.this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        adapter = new ClassAssistantAdapter(getContext(), list, true);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = etContent.getText().toString();
                presenter.classroomAssistSearchList(keyword);
                adapter.setKeyWord(keyword);
                hintKb();
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(bContext));
        list = new ArrayList<>();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showClassAssistantList(List list, PageModel pageModel) {
        if (list != null) {
            adapter.setNewData(list);
            adapter.notifyDataSetChanged();
            if(list.size()<=0){
                showError("没有搜索到你想要的内容");
            }
        }
    }

    @Override
    public void showClassAssistantLoadMoreList(List list, PageModel pageModel) {

    }

    protected void hintKb() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
