package com.abings.baby.ui.babyCard;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ui.babyCard.babyCardRelations.BabyCardRelationActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.BabyRelationModel;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ShuWen on 2017/3/13.
 */

public class BabycardAdapter extends BaseAdapter<BabyRelationModel> {

    public BabycardAdapter(Context context, List<BabyRelationModel> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, final BabyRelationModel data) {
        TextView cardName = holder.getView(R.id.babycard_list_item_name);
        TextView relationshaip = holder.getView(R.id.babycard_list_item_relationship);
        TextView phoneNum = holder.getView(R.id.babycard_list_item_phone);
        CircleImageView imageView = holder.getView(R.id.babycard_list_image);
        cardName.setText(data.getUserName());
        phoneNum.setText(data.getPhoneNum());
        relationshaip.setText(data.getRelation());

        ImageLoader.loadHeadTarget(mContext,Const.URL_pickHead + data.getHeadImageurl(),imageView);

        ImageView editImage = holder.getView(R.id.babycard_list_edit);
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, BabyCardRelationActivity.class).putExtra(Const.BABY_CARD_RELATION,data));
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.baby_card_list_item;
    }
}
