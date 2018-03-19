package com.hellobaby.library.ui.msg.fm;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hellobaby.library.Const;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.MessageModel;
import com.hellobaby.library.ui.base.BaseLibFragment;
import com.hellobaby.library.ui.msg.AllDeleteChangeStateInterface;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.SwipeItemLayout;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by zwj on 2016/11/28.
 * description : 消息列表
 */

public abstract class BaseMsgListFragment<T> extends BaseLibFragment<T> implements deleteAllClickInterface {

    protected BaseAdapter<MessageModel> bAdapter;

    TextView unread_all;
    ImageView delete_msg;


    protected FrameLayout bottom_check_frame;
    protected TextView list_is_null_tv;

    //删除邮件
    boolean isCandelete = false;
    boolean isSelelctedAll = false;


    boolean showAll = true;
    protected HashSet<RelativeLayout> radioButtonList;
    protected HashSet<SwipeItemLayout> swipeItemLayouts;
    protected HashSet<CheckBox> checkBoxes;

    protected ImageView delete_all_msg_image;
    protected AllDeleteChangeStateInterface anInterface;
    protected TextView update_date;
    protected TextView unread_number;
    protected TextView date_text;

    public BaseMsgListFragment(AllDeleteChangeStateInterface anInterface) {
        this.anInterface = anInterface;
    }

    @Override
    protected void initDaggerInject() {

    }

    public void setOutUpDate(){
        String date =  DateUtil.getFormatTimeFromTimestamp(System.currentTimeMillis(),"yyyy/MM/dd");
        update_date.setText(date);
    }

    public void setUpdate(MessageModel update) {
        if (update != null){
            unread_number.setVisibility(View.VISIBLE);
        }else {
            unread_number.setVisibility(View.GONE);
        }
        if (!showAll){
            showAll = true;
            date_text.setText("更新日期：");
            update_date.setVisibility(View.VISIBLE);
        }
        String date =  DateUtil.getFormatTimeFromTimestamp(System.currentTimeMillis(),"yyyy/MM/dd");
        update_date.setText(date);
    }

    public void setUnreadNumber(int number) {
        if (number <= 0){
            unread_number.setVisibility(View.GONE);
        }else {
            unread_number.setVisibility(View.VISIBLE);
            unread_number.setText(number + "封未读");
        }

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
        radioButtonList = new HashSet<>();
        swipeItemLayouts = new HashSet<>();
        checkBoxes = new HashSet<>();

        bottom_check_frame = (FrameLayout) mContentView.findViewById(R.id.bottom_check_frame);
        unread_all = (TextView) mContentView.findViewById(R.id.unread_all);
        delete_msg = (ImageView) mContentView.findViewById(R.id.delete_msg);
        delete_all_msg_image = (ImageView) mContentView.findViewById(R.id.delete_all_msg_image);
        update_date = (TextView) mContentView.findViewById(R.id.update_date);
        unread_number = (TextView) mContentView.findViewById(R.id.unread_number);
        date_text = (TextView) mContentView.findViewById(R.id.date_text);
        list_is_null_tv = (TextView) mContentView.findViewById(R.id.list_is_null_tv);

        initClick();

    }


    protected void deleteClick() {
        isCandelete = true;
        Iterator<RelativeLayout> buttonIterator = radioButtonList.iterator();
        while (buttonIterator.hasNext()) {
            RelativeLayout button = buttonIterator.next();
            button.setVisibility(View.VISIBLE);
        }
        Iterator<SwipeItemLayout> swipeItemLayoutIterator = swipeItemLayouts.iterator();
        while (swipeItemLayoutIterator.hasNext()) {
            SwipeItemLayout button = swipeItemLayoutIterator.next();
            button.setSwipeAble(false);
            if (button.isOpened()) {
                button.closeWithAnim();
            }
        }

        bottom_check_frame.getChildAt(0).setVisibility(View.GONE);
        bottom_check_frame.getChildAt(1).setVisibility(View.VISIBLE);


        //更改状态栏
        anInterface.setDeletState(View.VISIBLE);
        anInterface.setAddMsgState(View.GONE);

        bAdapter.setOnItemClickListener(new OnItemClickListeners<MessageModel>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, MessageModel data, int position) {
                CheckBox check_box_circle = viewHolder.getView(R.id.check_box_circle);
                if (!data.isSelected()) {
                    data.setSelected(true);
                    check_box_circle.setChecked(true);
                    for (int i = 0; i < getData().size(); i++) {
                        if (getData().get(i).getMessageId() == data.getMessageId()) {
                            getData().get(i).setSelected(true);
                            break;
                        }
                    }
                } else {
                    check_box_circle.setChecked(false);
                    data.setSelected(false);
                    for (int i = 0; i < getData().size(); i++) {
                        if (getData().get(i).getMessageId() == data.getMessageId()) {
                            getData().get(i).setSelected(false);
                            break;
                        }
                    }
                }
            }
        });

        Iterator<TextView> textViewIterator = getTextveiws().values().iterator();
        while (textViewIterator.hasNext()){
            textViewIterator.next().setClickable(false);
        }

    }

    //获得教师端已读人数的实例
    public HashMap<String,TextView> getTextveiws(){
        return new HashMap<>();
    }

    public abstract void resetAdapterItemClick();

    protected void initRelativeState(ViewHolder holder, final MessageModel data) {
        RelativeLayout button = holder.getView(com.hellobaby.library.R.id.delete_radio_btn);

        CheckBox check_box_circle = holder.getView(R.id.check_box_circle);
        if (isCandelete) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }
        if (data.isSelected()) {
            check_box_circle.setChecked(true);
        } else {
            check_box_circle.setChecked(false);
        }


        check_box_circle.setClickable(false);

        ImageView imageView = holder.getView(R.id.item_msg_delete);
        final SwipeItemLayout itemLayout = holder.getView(R.id.libMsgList_rl_root_delete);
        if (isCandelete) {
            itemLayout.setSwipeAble(false);
        } else {
            itemLayout.setSwipeAble(true);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemLayout.closeWithAnim();
                BottomDialogUtils.getBottomdeleteMsgDialog(getActivity(), "删除邮件", "取消", new BottomDialogUtils.onSureAndCancelClick() {
                    @Override
                    public void onItemClick() {
                        singleDeleteMsg(data);
                    }

                    @Override
                    public void onCancel() {
                    }
                });

            }
        });

    }

    //点击单条删除按钮
    protected abstract void singleDeleteMsg(MessageModel messageModel);

    //全选选中的回调
    @Override
    public void onItemClick(View view) {
        if (!isSelelctedAll) {
            isSelelctedAll = true;
            ((TextView)view).setText("取消全选");
            for (MessageModel messageModel : getData()) {
                messageModel.setSelected(true);
            }
            Iterator<CheckBox> checkBoxIterator = checkBoxes.iterator();
            while (checkBoxIterator.hasNext()) {
                CheckBox button = checkBoxIterator.next();
                button.setChecked(true);
            }
        } else {
            ((TextView)view).setText("全选");
            isSelelctedAll = false;
            for (MessageModel messageModel : getData()) {
                messageModel.setSelected(false);
            }
            Iterator<CheckBox> checkBoxIterator = checkBoxes.iterator();
            while (checkBoxIterator.hasNext()) {
                CheckBox button = checkBoxIterator.next();
                button.setChecked(false);
            }
        }
    }

    @Override
    public void resetAllState() {
        isCandelete = false;
        bottom_check_frame.getChildAt(0).setVisibility(View.VISIBLE);
        bottom_check_frame.getChildAt(1).setVisibility(View.GONE);
        Iterator<RelativeLayout> buttonIterator = radioButtonList.iterator();
        while (buttonIterator.hasNext()) {
            RelativeLayout button = buttonIterator.next();
            button.setVisibility(View.GONE);
        }
        Iterator<SwipeItemLayout> swipeItemLayoutIterator = swipeItemLayouts.iterator();
        while (swipeItemLayoutIterator.hasNext()) {
            SwipeItemLayout button = swipeItemLayoutIterator.next();
            button.setSwipeAble(true);
        }
        //更改状态栏
        anInterface.setDeletState(View.GONE);
        anInterface.setAddMsgState(View.VISIBLE);

        resetAdapterItemClick();
    }

    private void initClick() {

        unread_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showAll) {
                    showAll = false;
                    unread_all.setText("全部");
                    unread_number.setVisibility(View.VISIBLE);
                    unread_number.setText("未读");
                    date_text.setText("过滤方式：");
                    update_date.setVisibility(View.GONE);
                    showUnreadMsg();
                } else {
                    showAll = true;
                    unread_all.setText("未读");
                    if (getUnreadData().size() > 0){
                        unread_number.setVisibility(View.VISIBLE);
                        unread_number.setText(getUnreadData().size()+"封未读");
                    }else {
                        unread_number.setVisibility(View.GONE);
                    }
                    update_date.setVisibility(View.VISIBLE);
                    date_text.setText("更新日期：");

                    setOutUpDate();
                    showAllMsg();
                }
            }
        });

        delete_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteClick();
            }
        });

        delete_all_msg_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAllMsgImageClick();
            }
        });

    }

    protected void showAllMsg() {
        bAdapter.setNewData(getData());
    }

    protected void showUnreadMsg() {
        bAdapter.setNewData(getUnreadData());
    }

    public void reset() {
        //删除成功处理
        isCandelete = false;
        bottom_check_frame.getChildAt(0).setVisibility(View.VISIBLE);
        bottom_check_frame.getChildAt(1).setVisibility(View.GONE);
        Iterator<RelativeLayout> buttonIterator = radioButtonList.iterator();
        while (buttonIterator.hasNext()) {
            RelativeLayout button = buttonIterator.next();
            button.setVisibility(View.GONE);
        }
        Iterator<SwipeItemLayout> swipeItemLayoutIterator = swipeItemLayouts.iterator();
        while (swipeItemLayoutIterator.hasNext()) {
            SwipeItemLayout button = swipeItemLayoutIterator.next();
            button.setSwipeAble(true);
        }
        //更改状态栏
        anInterface.setDeletState(View.GONE);
        anInterface.setAddMsgState(View.VISIBLE);

        resetAdapterItemClick();
    }

    protected void deleteAllMsgImageClick() {
        BottomDialogUtils.getBottomdeleteMsgDialog(getActivity(), "删除邮件", "取消", new BottomDialogUtils.onSureAndCancelClick() {
            @Override
            public void onItemClick() {
                reset();
                delteAllMsg();
            }

            @Override
            public void onCancel() {
                reset();
            }
        });
    }

    protected abstract void delteAllMsg();


    @Override
    public void showData(T t) {

    }


    public BaseAdapter getRecyclerAdapter(Context context) {
        bAdapter = new BaseAdapter<MessageModel>(context, getData(), false) {
            @Override
            protected void convert(ViewHolder holder, MessageModel data) {
                recyclerViewConvert(holder, data);
                RelativeLayout button = holder.getView(R.id.delete_radio_btn);
                SwipeItemLayout swipeItemLayout = holder.getView(R.id.libMsgList_rl_root_delete);
                CheckBox checkBox = holder.getView(R.id.check_box_circle);

                checkBoxes.add(checkBox);
                radioButtonList.add(button);
                swipeItemLayouts.add(swipeItemLayout);
                initRelativeState(holder, data);
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

    //全部
    protected abstract List<MessageModel> getData();

    //未读
    protected abstract List<MessageModel> getUnreadData();

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

        if (null != data.getuName() && !data.getuName().equals("")) {
            name.setText(data.getuName());
        } else {
            name.setText(data.getTeacherName());
        }
        title.setText(data.getTitle());
        content.setText(data.getContent());
        try {
            time.setText(DateUtil.getDescriptionTimeFromTimestamp(DateUtil.longDay2time(data.getSendTime())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String url = getHeadUrl(data);
        icon.setImageResource(R.drawable.head_holder);
        if (url.isEmpty()) {
            //展示默认头像
            icon.setImageResource(R.drawable.head_holder);
//            ImageLoader.load(getContext(), R.drawable.head_holder, icon);
        } else {
            ImageLoader.loadHeadTarget(getContext(), url, icon);
        }
        if (data.getIsRead() == 1) {
//            已读
            icon.setBorderWidth(0);
        } else {
//            未读
            icon.setBorderColor(0xFFF66969);
            icon.setBorderWidth(9);
        }

    }

    private String getHeadUrl(MessageModel data) {
        if (!data.isEmptyHeadImageurl()) {
            String url = "";
            if (data.getSendTypeUser()) {
                url = Const.URL_userHead + data.getHeadImageurl();
            } else if (data.getSendTypeTeacher()) {
                url = Const.URL_TeacherHead + data.getHeadImageurl();
            } else if (data.getSendTypeSchool()) {
                url = Const.URL_schoolHead + data.getHeadImageurl();
            }
            return url;
        }
        return "";
    }
}
