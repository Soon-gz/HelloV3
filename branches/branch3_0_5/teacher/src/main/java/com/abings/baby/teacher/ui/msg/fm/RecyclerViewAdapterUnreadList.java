package com.abings.baby.teacher.ui.msg.fm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.hellobaby.library.data.model.UnreadModel;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.List;

public class RecyclerViewAdapterUnreadList extends BaseAdapter<UnreadModel> {

    public RecyclerViewAdapterUnreadList(Context context, List<UnreadModel> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, final UnreadModel data) {
        LinearLayout ll=(LinearLayout)holder.getView(R.id.ureadlist_rl_root);
        ImageView head_photo=(ImageView)holder.getView(R.id.photo);
        TextView name=(TextView)holder.getView(R.id.name);
        TextView phone=(TextView)holder.getView(R.id.phone);
        ImageLoader.loadHeadTarget(mContext,data.getHeadImgUrlAbs(),head_photo);
        name.setText(data.getUserName());
        phone.setText(data.getPhoneNum());
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri phone = Uri.parse("tel:" + data.getPhoneNum());
                intent.setData(phone);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return  R.layout.recyler_item_unreadlist;
    }
}
