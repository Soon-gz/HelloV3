package com.abings.baby.teacher.ui.main.fm.school;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.base.BaseActivity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.hellobaby.library.data.model.SchoolGradeModel;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SchoolMasterChooseActivity extends BaseActivity {

    public static final int REQUEST_CODE = 2300;
    public static final int RESULT_CODE = 2400;
    public static final String TO_WHERE = "school";
    public static final String RESULT_DATA = "resultData";
    public static final String CLASS_IDS = "classIds";

    @BindView(R.id.master_school_left_rv)
    RecyclerView leftRecycler;
    @BindView(R.id.master_school_right_rv)
    RecyclerView rightRecycler;

    List<SchoolGradeModel> gradeModels;
    List<SchoolGradeModel.ChildrenBean> childrenModels;

    BaseAdapter<SchoolGradeModel>gradeAdapter;
    BaseAdapter<SchoolGradeModel.ChildrenBean> childrenAdapter;
    private String classIds = "";


    @Inject
    SchoolMasterPresenter presenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_school_master_choose;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent,bContext).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        gradeModels = new ArrayList<>();
        childrenModels = new ArrayList<>();
        classIds = getIntent().getStringExtra(CLASS_IDS);

        gradeAdapter = new BaseAdapter<SchoolGradeModel>(bContext,gradeModels,false) {
            @Override
            protected void convert(ViewHolder holder, SchoolGradeModel data) {
                TextView textView = holder.getView(R.id.item_school_master_grade_tx);
                textView.setText(data.getName());
                LinearLayout linearLayout = holder.getView(R.id.item_school_master_grade_ll);
                TextPaint tp = textView.getPaint();
                if (data.isSelect()){
                    linearLayout.setBackgroundColor(Color.parseColor("#F9AF3B"));
                    tp.setFakeBoldText(true);
                }else {
                    linearLayout.setBackgroundColor(Color.parseColor("#F1F1F1"));
                    tp.setFakeBoldText(false);
                }
            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.item_school_master_grade;
            }
        };

        gradeAdapter.setOnItemClickListener(new OnItemClickListeners<SchoolGradeModel>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, SchoolGradeModel data, int position) {
                for (int i = 0; i < gradeModels.size(); i++) {
                    if (i == position){
                        gradeModels.get(i).setSelect(true);
                    }else {
                        gradeModels.get(i).setSelect(false);
                    }
                }
                childrenModels.clear();
                childrenModels.add(new SchoolGradeModel.ChildrenBean());
                childrenModels.addAll(data.getChildren());
                childrenAdapter.notifyDataSetChanged();
                gradeAdapter.notifyDataSetChanged();
            }
        });

        childrenAdapter = new BaseAdapter<SchoolGradeModel.ChildrenBean>(bContext,childrenModels,false) {
            @Override
            protected void convert(ViewHolder holder, SchoolGradeModel.ChildrenBean data) {
                TextView textView = holder.getView(R.id.item_school_master_grade_child_tx);
                textView.setText(data.getName());
                ImageView imageView = holder.getView(R.id.item_school_master_grade_child_img);
                TextPaint tp = textView.getPaint();
                if (data.isSelect()){
                    tp.setFakeBoldText(true);
                    imageView.setVisibility(View.VISIBLE);
                }else {
                    tp.setFakeBoldText(false);
                    imageView.setVisibility(View.GONE);
                }
            }

            @Override
            protected void convertHead(ViewHolder holder, SchoolGradeModel.ChildrenBean data, int position) {
                TextView textView = holder.getView(R.id.item_school_master_grade_child_tx_head);
                TextPaint tp = textView.getPaint();
                if (data.isSelect()){
                    tp.setFakeBoldText(true);
                }else {
                    tp.setFakeBoldText(false);
                }
            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.item_school_master_grade_child;
            }

            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.item_school_master_grade_child_head;
            }

            @Override
            public int getItemViewType(int position) {
                if (position == 0){
                    return TYPE_HEAD_VIEW;
                }else {
                    return TYPE_COMMON_VIEW;
                }
            }
        };

        childrenAdapter.setOnItemClickListener(new OnItemClickListeners<SchoolGradeModel.ChildrenBean>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, SchoolGradeModel.ChildrenBean data, int position) {
                boolean isSelect = data.isSelect() ? false:true;
                data.setSelect(isSelect);
                childrenAdapter.notifyDataSetChanged();
            }
        });

        leftRecycler.setLayoutManager(new LinearLayoutManager(this));
        leftRecycler.setHasFixedSize(true);
        leftRecycler.setItemAnimator(new DefaultItemAnimator());
        leftRecycler.setAdapter(gradeAdapter);

        rightRecycler.setLayoutManager(new LinearLayoutManager(this));
        rightRecycler.setHasFixedSize(true);
        rightRecycler.setItemAnimator(new DefaultItemAnimator());
        rightRecycler.setAdapter(childrenAdapter);

        presenter.selectClassAndGrade();
    }

    @OnClick({R.id.look_all_school, R.id.look_school_sure})
    public void clickbtn(View view) {
        switch (view.getId()) {
            case R.id.look_all_school:
                Intent intent = getIntent();
                intent.putExtra(RESULT_DATA,RESULT_DATA);
                setResult(RESULT_CODE,intent);
                finish();
                break;
            case R.id.look_school_sure:
                String ids = getClassIDs();
                Intent intent2 = getIntent();
                intent2.putExtra(RESULT_DATA,ids);
                setResult(RESULT_CODE,intent2);
                finish();
                break;
        }
    }

    @Override
    public void showData(Object o) {
        if (o != null){
            JSONObject jsonObject = (JSONObject) o;
            JSONArray jsonArray = jsonObject.getJSONArray("children");
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.size(); i++) {
                SchoolGradeModel gradeModel = gson.fromJson(jsonArray.get(i).toString(),SchoolGradeModel.class);
                gradeModels.add(gradeModel);
            }
            gradeModels.get(0).setSelect(true);
            gradeAdapter.notifyDataSetChanged();

            childrenModels.clear();
            childrenModels.add(new SchoolGradeModel.ChildrenBean());
            childrenModels.addAll(gradeModels.get(0).getChildren());
            if (!classIds.equals(RESULT_DATA) && !classIds.equals("")){
                LogZS.i("ids:"+classIds);
                String[]ids = classIds.split(",");
                for (int i = 0; i < ids.length; i++) {
                    int classId = Integer.parseInt(ids[i]);
                    for (int j = 0; j < childrenModels.size(); j++) {
                        if (classId == childrenModels.get(j).getClassId()){
                            childrenModels.get(j).setSelect(true);
                        }
                    }
                }
            }
            childrenAdapter.notifyDataSetChanged();
        }
    }

    public String getClassIDs() {
        StringBuilder classIDs = new StringBuilder("");
        for (int i = 0; i < gradeModels.size(); i++) {
            for (int j = 0; j < gradeModels.get(i).getChildren().size(); j++) {
                if (gradeModels.get(i).getChildren().get(j) != null
                        && gradeModels.get(i).getChildren().get(j).isSelect()){
                    String appendStr = i != (gradeModels.size()-1)?gradeModels.get(i).getChildren().get(j).getClassId()+",":gradeModels.get(i).getChildren().get(j).getClassId()+"";
                    classIDs.append(appendStr);
                }
            }
        }
        return classIDs.toString();
    }
}
