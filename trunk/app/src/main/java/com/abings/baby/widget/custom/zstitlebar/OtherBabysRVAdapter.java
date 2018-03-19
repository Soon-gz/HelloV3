package com.abings.baby.widget.custom.zstitlebar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abings.baby.R;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.utils.ImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zwj on 2016/10/21.
 * description : 横向的适配器
 */
public class OtherBabysRVAdapter extends RecyclerView.Adapter<OtherBabysRVAdapter.ViewHolder> {


    private LayoutInflater mInflater;
    private List<BabyModel> mDatas;
    private Context mContext;

    public OtherBabysRVAdapter(Context context, List<BabyModel> datats) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mDatas = datats;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        CircleImageView mImg;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = mInflater.inflate(R.layout.item_main_title_bar,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mImg = (CircleImageView) view.findViewById(R.id.itemMainTitleBar_iv);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.mImg.setImageResource(R.drawable.head_holder);
        ImageLoader.loadHeadTarget(mContext, mDatas.get(i).getHeadImgUrlAbs(), viewHolder.mImg);
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = viewHolder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(viewHolder.itemView, pos);
                }
            });

            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = viewHolder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(viewHolder.itemView, pos);
                    return false;
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
