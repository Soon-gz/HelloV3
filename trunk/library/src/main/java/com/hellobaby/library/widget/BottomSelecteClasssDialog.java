package com.hellobaby.library.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.ClassModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwj on 2017/01/10.
 * 选择class
 */
public class BottomSelecteClasssDialog extends BottomBaseDialog<BottomSelecteClasssDialog> {
    private Context mContext;
    private List<ClassModel> classModelList;
    private ClassSelectedAdapter classSelectedAdapter;
    private SelectedClassListener listener;


    public BottomSelecteClasssDialog(Context context,  List<ClassModel> classModelList,SelectedClassListener listener) {
        super(context, null);
        this.mContext = context;
        this.classModelList = classModelList;
        this.listener = listener;
    }


    @Override
    public View onCreateView() {

        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_selected_classs, null);
        TextView tvCancel = (TextView) view.findViewById(R.id.customSelectedClasss_tv_cancel);
        TextView tvOk = (TextView) view.findViewById(R.id.customSelectedClasss_tv_ok);
        ListView listView = (ListView) view.findViewById(R.id.customSelectedClasss_listView);
        classSelectedAdapter = new ClassSelectedAdapter(classModelList,mContext);
        listView.setAdapter(classSelectedAdapter);
        classSelectedAdapter.setOnCheckedChangeListener(new ClassSelectedAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(int position, boolean isChecked) {
                classModelList.get(position).setSelected(isChecked);
                classSelectedAdapter.notifyDataSetChanged();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ClassModel> selectedClass = new ArrayList<>();
                for (ClassModel classModel:classModelList) {
                    if(classModel.isSelected()){
                        selectedClass.add(classModel);
                    }
                }
                if(listener!=null){
                    listener.onSelectedClass(selectedClass);
                }
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void setUiBeforShow() {

    }


    public interface SelectedClassListener {
        void onSelectedClass(List<ClassModel> selectedClass);
    }

}
