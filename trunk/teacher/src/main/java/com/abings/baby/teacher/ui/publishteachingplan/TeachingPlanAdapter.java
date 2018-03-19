package com.abings.baby.teacher.ui.publishteachingplan;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.hellobaby.library.data.model.TeachingPlanModel;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.ImageLoader;

import java.util.List;

public class TeachingPlanAdapter extends RecyclerView.Adapter<TeachingPlanAdapter.ViewHolder> {
    private boolean isFirst = true;
    private List<TeachingPlanModel> teachingPlanModels;
    private Context mContext;

    private int item_normal_height;
    private int item_max_height;
    private float item_normal_alpha;
    private float item_max_alpha;
    private float item_normal_font_size;
    private float item_max_font_size;
    //    private float item_normal_iv_size;
//    private float item_max_iv_size;
//点击
    private OnItemClickListener mOnItemClickListener;
    //长按
    private OnItemLongClickListener mOnItemLongClickListener;

    //点击
    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    //长按
    public void setmOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    public TeachingPlanAdapter(Context context, List<TeachingPlanModel> teachingPlanModels) {
        this.mContext = context;
        this.teachingPlanModels = teachingPlanModels;
        item_max_height = (int) context.getResources().getDimension(R.dimen.item_max_height);
        item_normal_height = (int) context.getResources().getDimension(R.dimen.item_normal_height);
        item_normal_font_size = context.getResources().getDimension(R.dimen.item_normal_font_size);
        item_max_font_size = context.getResources().getDimension(R.dimen.item_max_font_size);
//        item_normal_iv_size = context.getResources().getDimension(R.dimen.item_normal_font_size);
//        item_max_iv_size = context.getResources().getDimension(R.dimen.item_max_font_size);
        item_normal_alpha = context.getResources().getFraction(R.fraction.item_normal_mask_alpha, 1, 1);
        item_max_alpha = context.getResources().getFraction(R.fraction.item_max_mask_alpha, 1, 1);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wall, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final TeachingPlanModel TeachingPlanModel = teachingPlanModels.get(position);
        final Bundle b = new Bundle();
        b.putSerializable("TeachingPlanModel", TeachingPlanModel);
        if (isFirst && position == 0) {
            isFirst = false;
            holder.mark.setAlpha(item_max_alpha);
            holder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_max_font_size);
            holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, item_max_height));
        } else {
            holder.mark.setAlpha(item_normal_alpha);
            holder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_normal_font_size);
            holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, item_normal_height));
        }
        holder.text.setText(TeachingPlanModel.getClassName());
        holder.timetext.setText(DateUtil.timestamp2NYRFormat(TeachingPlanModel.getPlanningTime()));
        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageLoader.loadImg(mContext, TeachingPlanModel.getImageurlAbs(), holder.imageView);
//        holder.mark.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<SlideBean> waterFallItemList = new ArrayList<>();
//                SlideBean waterFallItem = new SlideBean();
//                waterFallItem.setUrl(TeachingPlanModel.getImageurlAbs());
//                waterFallItemList.add(waterFallItem);
//                Intent intent = new Intent(mContext, SlideActivity.class);
//                intent.putParcelableArrayListExtra(SlideActivity.kDatas, (ArrayList<? extends Parcelable>) waterFallItemList);
//                intent.putExtra(SlideActivity.kCurrentPosition, 0);//TODO 这个值需要改成点击图片的值
//                mContext.startActivity(intent);
//            }
//        });
        if(mOnItemClickListener!=null)
            holder.mark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.imageView, position);
                }
            });
        if(mOnItemLongClickListener!=null)
        holder.mark.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemLongClickListener.onItemLongClick(holder.imageView, position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return teachingPlanModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public View mark;
        public TextView text;
        public TextView timetext;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            mark = itemView.findViewById(R.id.mark);
            text = (TextView) itemView.findViewById(R.id.text);
            timetext = (TextView) itemView.findViewById(R.id.text2);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}
