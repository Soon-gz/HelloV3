package com.hellobaby.library.ui.contact;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hellobaby.library.R;
import com.hellobaby.library.data.model.Contact;
import com.hellobaby.library.ui.base.BaseLibFragment;
import com.hellobaby.library.widget.DividerItemDecoration;
import com.hellobaby.library.widget.IndexView;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * 通讯录的基准
 */
public abstract class BaseContactsFragment extends BaseLibFragment {


    protected TextView mTipTv;
    protected IndexView mIndexView;

    protected View content_layout;


    private ActivityRecyclerIndexAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_contacts;
    }

    @Override
    protected void initViewsAndEvents() {
        RecyclerView mDataRv = (RecyclerView) mContentView.findViewById(R.id.user_recyclerview);
        mTipTv = (TextView) mContentView.findViewById(R.id.tv_recyclerindexview_tip);
        mIndexView = (IndexView) mContentView.findViewById(R.id.indexview);
        mIndexView.setTipTv(mTipTv);
        mDataRv.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayoutManager.VERTICAL));
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(mLayoutManager);
        mIndexView.setOnChangedListener(new IndexView.OnChangedListener() {
            @Override
            public void onChanged(String text) {
                int position = mAdapter.getPositionForSection2(text.charAt(0));
                if (position != -1) {
                    // position的item滑动到RecyclerView的可见区域，如果已经可见则不会滑动
                    mLayoutManager.scrollToPosition(position);
                }
            }
        });

        mAdapter = new ActivityRecyclerIndexAdapter(this.getActivity(), new ArrayList<Contact>(), false);
        mAdapter.setOnItemClickListener(new OnItemClickListeners<Contact>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, Contact contact, int position) {
                if (mAdapter.getItem(position).isTeacher()) {
                    //老师直接打电话
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + mAdapter.getItem(position).getPhone());
                    intent.setData(data);
                    startActivity(intent);
                    return;
                }
                onItemClickUser(contact, position);
            }
        });
        mDataRv.setAdapter(mAdapter);
    }

    protected void insertContacts(List<Contact> list) {
        mAdapter.setData(list);
    }

    /**
     * 获取相关联系人列表
     *
     * @return
     */
    protected void onItemClickUser(Contact contact, int position) {

    }

}
