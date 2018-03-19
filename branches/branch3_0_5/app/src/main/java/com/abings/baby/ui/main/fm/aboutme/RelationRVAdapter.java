package com.abings.baby.ui.main.fm.aboutme;


import android.content.Context;

import com.abings.baby.R;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zwj on 2016/10/21.
 * description : 横向的适配器
 */
public class RelationRVAdapter extends BaseAdapter<UserModel> {

    private float item_selected_alpha;
    private float item_unSelected_alpha;

    public RelationRVAdapter(Context context, List<UserModel> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        item_selected_alpha = context.getResources().getFraction(R.fraction.relationHead_selected_alpha, 1, 1);
        item_unSelected_alpha = context.getResources().getFraction(R.fraction.relationHead_unselected_alpha, 1, 1);
    }

    @Override
    protected void convert(ViewHolder holder, UserModel data) {
        CircleImageView circleImageView =   holder.getView(R.id.recyclerItem_civ);
        if(data.getHeadImageurl()==null||"".equals(data.getHeadImageurl())){
            circleImageView.setImageResource(R.drawable.head_holder);
        }else {
            ImageLoader.loadHeadTarget(mContext, data.getHeadImageurlAbs(), circleImageView);
        }
        if (data.isSelected()) {
            circleImageView.setAlpha(item_selected_alpha);
        } else {
            circleImageView.setAlpha(item_unSelected_alpha);
        }
    }


    @Override
    protected int getItemLayoutId() {
        return R.layout.recycler_item_relation;
    }

    public List<UserModel> getDatas(){
        return mDatas;
    }
//    private LayoutInflater mInflater;
//    private List<UserModel> mDatas;
//    private Context mContext;
//
//    private float item_selected_alpha;
//    private float item_unSelected_alpha;
//
//    public RelationRVAdapter(Context context, List<UserModel> datats) {
//        mInflater = LayoutInflater.from(context);
//        mContext = context;
//        mDatas = datats;
//
//        item_selected_alpha = context.getResources().getFraction(R.fraction.relationHead_selected_alpha, 1, 1);
//        item_unSelected_alpha = context.getResources().getFraction(R.fraction.relationHead_unselected_alpha, 1, 1);
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public ViewHolder(View arg0) {
//            super(arg0);
//        }
//
//        CircleImageView mImg;
//    }
//
//    @Override
//    public int getItemCount() {
//        return mDatas.size();
//    }
//
//    /**
//     * 创建ViewHolder
//     */
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//
//        View view = mInflater.inflate(R.layout.recycler_item_relation, viewGroup, false);
//        ViewHolder viewHolder = new ViewHolder(view);
//        viewHolder.mImg = (CircleImageView) view.findViewById(R.id.recyclerItem_civ);
//        return viewHolder;
//    }
//
//    /**
//     * 设置值
//     */
//    @Override
//    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
//        UserModel userModel = mDatas.get(i);
//
//        ImageLoader.loadHeadTarget(mContext, mDatas.get(i).getHeadImageurlAbs(), viewHolder.mImg);
//        // 如果设置了回调，则设置点击事件
//        if (mOnItemClickListener != null) {
//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = viewHolder.getLayoutPosition();
//                    mOnItemClickListener.onItemClick(viewHolder.itemView, pos);
//                }
//            });
//
//            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    int pos = viewHolder.getLayoutPosition();
//                    mOnItemClickListener.onItemLongClick(viewHolder.itemView, pos);
//                    return false;
//                }
//            });
//        }
//
//        if (userModel.isSelected()) {
//            viewHolder.mImg.setAlpha(item_selected_alpha);
//        } else {
//            viewHolder.mImg.setAlpha(item_unSelected_alpha);
//        }
//    }
//
//    public void setNewData(List<UserModel> list) {
//        mDatas.clear();
//        mDatas.addAll(list);
//        notifyDataSetChanged();
//    }
//
//
//    public interface OnItemClickListener {
//        void onItemClick(View view, int position);
//
//        void onItemLongClick(View view, int position);
//    }
//
//
//    private OnItemClickListener mOnItemClickListener;
//
//    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
//        this.mOnItemClickListener = mOnItemClickListener;
//    }
//
//    public List<UserModel> getDatas() {
//        return mDatas;
//    }
}
