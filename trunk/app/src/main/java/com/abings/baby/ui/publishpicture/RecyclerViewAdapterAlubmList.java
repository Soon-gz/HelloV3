package com.abings.baby.ui.publishpicture;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.R;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.AlubmListItemModel;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.List;

public class RecyclerViewAdapterAlubmList extends BaseAdapter<AlubmListItemModel> {

    public RecyclerViewAdapterAlubmList(Context context, List<AlubmListItemModel> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, final AlubmListItemModel data) {
        if(data.isSelected()){
            ((TextView)holder.getView(R.id.rv_album_name)).setTextColor(Color.parseColor("#FF000000"));
        }else {
            ((TextView)holder.getView(R.id.rv_album_name)).setTextColor(Color.parseColor("#FFCCCCCC"));
        }
        ((TextView) holder.getView(R.id.rv_album_name)).setText(data.getTitle());
        ImageLoader.loadHeadTarget(mContext, Const.URL_Album+data.getImageurl(),(ImageView) holder.getView(R.id.albumlist_im));
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.rv_albumlist;
    }
}
