package com.abings.baby.teacher.ui.attendanceManager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.hellobaby.library.data.model.SchoolMasterAllChildModel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ShuWen on 2017/11/30.
 */

public class SchoolAllAttendenceAdapter extends BaseExpandableListAdapter {
    private HashMap<String,HashMap<String,SchoolMasterAllChildModel>> parentsMaps;
    private List<String> parentLists;
    private HashMap<String,List<String>> childrenNames;
    private Context context;

    public SchoolAllAttendenceAdapter(Context context) {
        this.context = context;
    }

    public SchoolAllAttendenceAdapter setParentLists(List<String> parentLists) {
        this.parentLists = parentLists;
        return this;
    }

    public SchoolAllAttendenceAdapter setParentsMaps(HashMap<String, HashMap<String, SchoolMasterAllChildModel>> parentsMaps) {
        this.parentsMaps = parentsMaps;
        return this;
    }

    public SchoolAllAttendenceAdapter setChildrenNames(HashMap<String,List<String>> childrenNames) {
        this.childrenNames = childrenNames;
        return this;
    }

    @Override
    public int getGroupCount() {
        return parentsMaps.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return parentsMaps.get(parentLists.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return parentsMaps.get(parentLists.get(i));
    }

    @Override
    public Object getChild(int i, int i1) {
        return parentsMaps.get(parentLists.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_attend_school_all_group, null);
        }
        view.setTag(R.layout.item_attend_school_all_group, i);
        view.setTag(R.layout.item_school_all_child, -1);
        TextView textView = (TextView) view.findViewById(R.id.item_group_school_date);
        textView.setText(parentLists.get(i));
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String userName = childrenNames.get(parentLists.get(i)).get(i1);
        final SchoolMasterAllChildModel childModel = parentsMaps.get(parentLists.get(i)).get(userName);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_school_all_child, null);
        }
        view.setTag(R.layout.item_attend_school_all_group, i);
        view.setTag(R.layout.item_school_all_child, i1);

        view.findViewById(R.id.item_school_all_child_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SchoolPersonDetailActivity.class);
                intent.putExtra(SchoolPersonDetailActivity.TEACHER_MODEL,childModel);
                context.startActivity(intent);
            }
        });
        ((TextView) view.findViewById(R.id.item_school_child_name)).setText(userName);
        int viewVisible = childModel.hasLeaveRequest() ? View.VISIBLE:View.INVISIBLE;
        view.findViewById(R.id.item_school_child_jia).setVisibility(viewVisible);
        ((TextView) view.findViewById(R.id.item_school_child_arrive)).setText(childModel.getArriveTime());
        ((TextView) view.findViewById(R.id.item_school_child_leave)).setText(childModel.getLeaveTime());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
