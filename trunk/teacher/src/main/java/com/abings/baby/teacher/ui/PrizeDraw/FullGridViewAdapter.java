package com.abings.baby.teacher.ui.PrizeDraw;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ui.PrizeDraw.PrizeDetail.PrizeDetailActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.PrizeDrawBean;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.ToastUtils;

import java.util.List;

/**
 * Created by ShuWen on 2017/5/8.
 */

public class FullGridViewAdapter extends BaseAdapter {

    private List<PrizeDrawBean> prizeDrawBeenS;
    private Context context;

    public FullGridViewAdapter(List<PrizeDrawBean> prizeDrawBeenS, Context context) {
        this.prizeDrawBeenS = prizeDrawBeenS;
        this.context = context;
    }

    @Override
    public int getCount() {
        return prizeDrawBeenS.size();
    }

    @Override
    public Object getItem(int i) {
        return prizeDrawBeenS.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_prize_draw,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image_prize);
            viewHolder.duihuan = (TextView) view.findViewById(R.id.duihuan);
            viewHolder.prize_draw_name = (TextView) view.findViewById(R.id.prize_draw_name);
            viewHolder.price_number = (TextView) view.findViewById(R.id.price_number);
            viewHolder.rest_number = (TextView) view.findViewById(R.id.rest_number);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        final PrizeDrawBean drawBean = prizeDrawBeenS.get(i);
        ImageLoader.loadImg(context, Const.URL_prizeDrawImg + drawBean.getImgurl(),viewHolder.imageView);
        viewHolder.prize_draw_name.setText(drawBean.getName());
        viewHolder.price_number.setText(drawBean.getPrice()+"");
        viewHolder.rest_number.setText(drawBean.getRest()+"");


        if(drawBean.getRest() > 0){
            viewHolder.duihuan.setBackgroundResource(R.drawable.btn_bg_del_small);
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PrizeDetailActivity.class);
                    intent.putExtra("name",drawBean.getName());
                    intent.putExtra("imageUrl",drawBean.getImgurl());
                    intent.putExtra("rest",drawBean.getRest()+"");
                    intent.putExtra("price",drawBean.getPrice()+"");
                    intent.putExtra("explain",drawBean.getExplicate());
                    intent.putExtra("exchangeId",drawBean.getExchangeId()+"");
                    context.startActivity(intent);
                }
            });
        }else{
            viewHolder.duihuan.setBackgroundResource(R.drawable.btn_bg_unable_small);
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showNormalToast(context,"不好意思,来晚一步~");
                }
            });
        }

        return view;
    }


    class ViewHolder{
        private ImageView imageView;
        private TextView duihuan;
        private TextView prize_draw_name;
        private TextView price_number;
        private TextView rest_number;



    }
}
