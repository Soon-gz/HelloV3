package com.abings.baby.ui.main.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.abings.baby.R;

import java.util.List;

/**
 * Created by zwj on 2016/12/13.
 * description :
 */

public class DateExpandableListViewAdapter extends BaseExpandableListAdapter {
    private List<String> groupList;
    private List<List<String>> childList;
    private Context mContext;
    private int selectedGroup;
    private int selectedChild;

    public DateExpandableListViewAdapter(Context context, List<String> groupList, List<List<String>> childList) {
        this.groupList = groupList;
        this.childList = childList;
        this.mContext = context;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.elistview_item_group, null);
            holder = new GroupHolder();
            holder.txt = (TextView) convertView.findViewById(R.id.itemEListViewGroup_tv_year);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.txt.setText(groupList.get(groupPosition));
        if (groupPosition == selectedGroup) {
            holder.txt.setTextColor(Color.WHITE);
        } else {
            holder.txt.setTextColor(Color.GRAY);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ItemHolder holder = null;
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.elistview_item_child, null);

            holder = new ItemHolder();

            holder.txt = (TextView) convertView.findViewById(R.id.itemEListViewChild_tv_month);
            convertView.setTag(holder);
        } else {
            holder = (ItemHolder) convertView.getTag();
        }
        holder.txt.setText(childList.get(groupPosition).get(childPosition));
        if ((groupPosition == selectedGroup) && (childPosition == selectedChild)) {
            holder.txt.setTextColor(Color.WHITE);
        } else {
            holder.txt.setTextColor(Color.GRAY);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class GroupHolder {
        TextView txt;
    }

    private class ItemHolder {
        TextView txt;
    }

    /**
     * 选中月份后，添加选中效果
     *
     * @param groupPosition 选中的组id
     * @param childPosition 选中的子id
     */
    public void setOnChildClickListener(ExpandableListView parent, int groupPosition, int childPosition) {
        selectedGroup = groupPosition;
        selectedChild = childPosition;

        parent.collapseGroup(selectedGroup);
        parent.expandGroup(selectedGroup);
        notifyDataSetChanged();
    }
}
