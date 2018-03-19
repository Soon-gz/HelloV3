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
        if(data.isEmptyHeadImageurl()){
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
}
