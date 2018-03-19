package com.hellobaby.library.ui.contact;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellobaby.library.R;
import com.hellobaby.library.data.model.Contact;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.utils.StringUtils;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.List;

public class ActivityRecyclerIndexAdapter extends BaseAdapter<Contact> {
    public ActivityRecyclerIndexAdapter(Context context, List<Contact> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, Contact data) {
        ImageView photo = (ImageView) holder.getView(R.id.photo);
        int section = getSectionForPosition(data);
        if (null != data && data == getPositionForSection(section)) {
            holder.getView(R.id.tv_item_indexview_catalog).setVisibility(View.VISIBLE);
            ((TextView) holder.getView(R.id.tv_item_indexview_catalog)).setText("" + data.getFirstChar());

            if ("#".equals("" + data.getFirstChar())) {
                ((TextView) holder.getView(R.id.tv_item_indexview_catalog)).setText("老师");
            }
        } else {
            holder.getView(R.id.tv_item_indexview_catalog).setVisibility(View.GONE);
        }
        if (data.isTeacher()) {
            ((TextView) holder.getView(R.id.phone)).setText(data.getPhone());
            photo.setImageResource(R.drawable.head_holder);
//            ImageLoader.load(mContext,R.drawable.head_holder,photo);
            if(data.getHeadImageurl()!=null&&!data.getHeadImageurl().isEmpty()){
                ImageLoader.loadHeadTarget(mContext,data.getTeacherHeadImageurlAbs(),photo);
            }
            String ss = StringUtils.getTeacherNamePosition(data.getName(),data.getPosition());
            ((TextView) holder.getView(R.id.name)).setText(ss);
//            ((TextView) holder.getView(R.id.name)).setText(data != null ? data.getName()+(data.getPosition()==null||data.getPosition().equals("")?"(老师)":"("+data.getPosition()+")") : null);
        } else {
            ((TextView) holder.getView(R.id.name)).setText(data != null ? data.getName():"");
            ((TextView) holder.getView(R.id.phone)).setText("");
            photo.setImageResource(R.drawable.head_holder);
//            ImageLoader.load(mContext,R.drawable.head_holder,photo);
            if(data.getHeadImageurl()!=null&&!data.getHeadImageurl().isEmpty()){
                ImageLoader.loadHeadTarget(mContext,data.getBabyHeadImageurlAbs(),photo);
            }
        }

    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.recyler_item_indexview;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(Contact contact) {
        return contact.getFirstChar();
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public Contact getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            char firstChar = mDatas.get(i).getFirstChar();
            if (firstChar == section) {
                return mDatas.get(i);
            }
        }
        return null;
    }

    public int getPositionForSection2(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            char firstChar = mDatas.get(i).getFirstChar();
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }
}