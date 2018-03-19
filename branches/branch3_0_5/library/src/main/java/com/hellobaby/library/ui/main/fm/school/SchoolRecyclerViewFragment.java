package com.hellobaby.library.ui.main.fm.school;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hellobaby.library.R;
import com.hellobaby.library.ui.base.BaseLibFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwj on 2016/12/20.
 * description :
 */

public abstract class SchoolRecyclerViewFragment<T> extends BaseLibFragment<T> {

    RecyclerView bRecyclerView;
    protected List<SchoolItem> bListData;
    protected BaseSchoolRVAdapter bAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_school_recyclerview;
    }

    @Override
    protected void initViewsAndEvents() {
        bRecyclerView = (RecyclerView) mContentView.findViewById(R.id.schoolRecyclerView);
        bRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bListData = new ArrayList<>();
        initAdapter();
        bRecyclerView.setAdapter(bAdapter);
    }

    public abstract void initAdapter();
}
