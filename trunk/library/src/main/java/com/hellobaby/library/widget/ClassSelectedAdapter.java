package com.hellobaby.library.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

import com.hellobaby.library.R;
import com.hellobaby.library.data.model.ClassModel;

import java.util.List;

/**
 * 班级选择的Adapter
 */
public class ClassSelectedAdapter extends BaseAdapter {
    private List<ClassModel> listData;
    private Context mContext;

    public ClassSelectedAdapter(List<ClassModel> list, Context context) {
        listData = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public ClassModel getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item_class, null);
            holder.className = (CheckBoxPlus) convertView.findViewById(R.id.listViewItem_cbp);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.className.setText(listData.get(position).getClassName());
        holder.className.setSelected(listData.get(position).isSelected());
        holder.className.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onCheckedChangeListener != null) {
                    onCheckedChangeListener.onCheckedChanged(position, isChecked);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        private CheckBoxPlus className;
    }

    private OnCheckedChangeListener onCheckedChangeListener;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(int position, boolean isChecked);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }
}