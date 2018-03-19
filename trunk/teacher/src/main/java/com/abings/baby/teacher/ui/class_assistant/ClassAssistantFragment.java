package com.abings.baby.teacher.ui.class_assistant;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.class_assistant.adapter.ClassAssistantAdapter;
import com.hellobaby.library.data.model.ClassAssistantModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.base.BaseLibFragment;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.OnLoadMoreListener;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by zwj on 2016/12/20.
 * description :
 */

public class ClassAssistantFragment extends BaseLibFragment implements ClassAssistantFragmentMvpView {
    @Inject
    ClassAssistantFragmentPresenter presenter;

    private int type = 0;
    private static int base = 0;
    public static int TYPE_ALL = base;
    public static int TYPE_HEALTHY = base + 1;
    public static int TYPE_SCIENCE = base + 2;
    public static int TYPE_SOCIETY = base + 3;
    public static int TYPE_LANGUAGE = base + 4;
    public static int TYPE_ART = base + 5;
    private BaseAdapter<ClassAssistantModel> baseAdapter;
    private PageModel pageModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ClassAssistantFragment() {
    }

    public ClassAssistantFragment(int type) {
        this.type = type;
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(((BaseLibActivity) getActivity()).getActivityComponent(), getActivity()).inject(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_class_assistant;
    }

    @Override
    protected void initViewsAndEvents() {
        presenter.attachView(this);
        RecyclerView recyclerView = (RecyclerView) mContentView.findViewById(R.id.classAssistant_rv);
        swipeRefreshLayout = (SwipeRefreshLayout) mContentView.findViewById(R.id.classAssistant_swipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.classroomAssistList(type);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        baseAdapter = new ClassAssistantAdapter(getContext(), new ArrayList<ClassAssistantModel>(), true);
        baseAdapter.setLoadingView(R.layout.footer_more);
        baseAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (pageModel.getPageNum() != pageModel.getPages()) {
                    presenter.classroomAssistLoadMoreList(type,pageModel.getPageNum()+1);
                }else {
                    baseAdapter.setLoadEndView(R.layout.footer_loadend);
                }
            }
        });


        recyclerView.setAdapter(baseAdapter);
        String testTitle = "";
        if (type == TYPE_ALL) {
//            tvTest.setText("全部");
            testTitle = "全部";
            presenter.classroomAssistList(TYPE_ALL);
        } else if (type == TYPE_HEALTHY) {
//            tvTest.setText("健康");
            testTitle = "健康";
            presenter.classroomAssistList(TYPE_HEALTHY);
        } else if (type == TYPE_SCIENCE) {
//            tvTest.setText("科学");
            testTitle = "科学";
            presenter.classroomAssistList(TYPE_SCIENCE);
        } else if (type == TYPE_SOCIETY) {
//            tvTest.setText("社会");
            testTitle = "社会";
            presenter.classroomAssistList(TYPE_SOCIETY);
        } else if (type == TYPE_LANGUAGE) {
//            tvTest.setText("语言");
            testTitle = "语言";
            presenter.classroomAssistList(TYPE_LANGUAGE);
        } else if (type == TYPE_ART) {
//            tvTest.setText("艺术");
            testTitle = "艺术";
            presenter.classroomAssistList(TYPE_ART);
        }
    }

    @Override
    public void showClassAssistantList(List list, PageModel pageModel) {
        swipeRefreshLayout.setRefreshing(false);
        if (pageModel != null) {
            this.pageModel = pageModel;
        }
        if (list != null) {
            baseAdapter.setNewData(list);
        }
    }

    @Override
    public void showError(String err) {
//        super.showError(err);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showClassAssistantLoadMoreList(List list, PageModel pageModel) {
        if (list != null) {
//            this.list.addAll(list);
            baseAdapter.setLoadMoreData(list);
        }
        if (pageModel != null) {
            this.pageModel = pageModel;
        }
    }
}
