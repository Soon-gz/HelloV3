package com.abings.baby.teacher.ui.PrizeDraw.PrizeDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.abings.baby.teacher.R;

/**
 * Created by ShuWen on 2017/5/8.
 */

public class FullListViewAdapter extends BaseAdapter {

    private String[]explains;
    private Context context;

    public FullListViewAdapter(String[] explains, Context context) {
        this.explains = explains;
        this.context = context;
    }

    @Override
    public int getCount() {
        return explains.length;
    }

    @Override
    public Object getItem(int i) {
        return explains[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_explain,null);
            viewHolder = new ViewHolder();
            viewHolder.explainId = (TextView) view.findViewById(R.id.explain_id);
            viewHolder.explainText = (TextView) view.findViewById(R.id.explain_text);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.explainId.setText((i+1)+".");
        viewHolder.explainText.setText(explains[i]);

        return view;
    }

    class ViewHolder{
        private TextView explainId;
        private TextView explainText;
    }
}
