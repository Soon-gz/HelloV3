package com.hellobaby.library.ui.msg.fm;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.hellobaby.library.R;
import com.hellobaby.library.data.model.MessageModel;
import com.hellobaby.library.ui.base.BaseLibFragment;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.text.ParseException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by zwj on 2016/11/28.
 * description : 消息列表
 */

public abstract class BaseMsgListFragment<T> extends BaseLibFragment<T> {

    protected BaseAdapter<MessageModel> bAdapter;

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libfragment_base_msglist;
    }

    @Override
    protected void initViewsAndEvents() {
        RecyclerView recyclerView = (RecyclerView) mContentView.findViewById(R.id.baseMsgBox_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(getRecyclerAdapter(getContext()));
    }

    @Override
    public void showData(T t) {

    }


    public BaseAdapter getRecyclerAdapter(Context context) {
        bAdapter = new BaseAdapter<MessageModel>(context, getData(), false) {
            @Override
            protected void convert(ViewHolder holder, MessageModel data) {
                recyclerViewConvert(holder, data);
            }

            @Override
            protected int getItemLayoutId() {
                if (getRecyclerItemLayoutId() < 0) {
                    return R.layout.librecyler_item_msglist;
                }
                return getRecyclerItemLayoutId();
            }
        };
        return bAdapter;
    }

    protected abstract List<MessageModel> getData();

    /**
     * 执行了这个，必须重写converted
     */
    protected int getRecyclerItemLayoutId() {
        return -1;
    }

    protected void recyclerViewConvert(ViewHolder holder, MessageModel data) {
        CircleImageView icon = holder.getView(R.id.libMsgList_civ_icon);
        TextView name = holder.getView(R.id.libMsgList_tv_person);//来自于谁或者发给谁
        TextView time = holder.getView(R.id.libMsgList_tv_time);//创建时间
        TextView title = holder.getView(R.id.libMsgList_tv_subject);//创建标题
        TextView content = holder.getView(R.id.libMsgList_tv_content);//消息内容
        if (null!=data.getuName()&&!data.getuName().equals(""))
            name.setText(data.getuName());
        else
            name.setText(data.getTeacherName());
        title.setText(data.getTitle());
        content.setText(data.getContent());
        try {
            time.setText(DateUtil.getDescriptionTimeFromTimestamp(DateUtil.longDay2time(data.getSendTime())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (data.getIsRead() == 1) {
            icon.setImageResource(R.drawable.msgbox_icon_readed);
        } else {
            icon.setImageResource(R.drawable.msgbox_icon_unread);
        }
    }
}
