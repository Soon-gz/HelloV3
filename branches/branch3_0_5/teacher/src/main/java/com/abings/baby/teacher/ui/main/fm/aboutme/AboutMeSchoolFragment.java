package com.abings.baby.teacher.ui.main.fm.aboutme;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.ui.base.BaseFragment;
import com.hellobaby.library.data.model.ClassModel;
import com.hellobaby.library.data.model.SchoolModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

import static com.abings.baby.teacher.R.id.aboutmeSchool_lv_class;


public class AboutMeSchoolFragment extends BaseFragment {
    @BindView(R.id.aboutmeSchool_tv_classs)
    TextView tvClasss;
    @BindView(R.id.aboutmeSchool_lv_class)
    ListView listViewClass;
    @BindView(R.id.aboutmeSchool_tv_schoolName)
    TextView tvSchoolName;//学校名称
    @BindView(R.id.aboutmeSchool_tv_schoolAddr)
    TextView tvSchoolAddr;//地址
    @BindView(R.id.aboutmeSchool_tv_schoolPhone)
    TextView tvSchoolPhone;//电话

    Boolean isExpanded = false;


    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_aboutme_school;
    }

    @Override
    protected void initViewsAndEvents() {

        {
            SchoolModel schoolModel = ZSApp.getInstance().getSchoolModel();
            tvSchoolName.setText(schoolModel.getSchoolName());
            tvSchoolPhone.setText(schoolModel.getSchoolPhone());
            tvSchoolAddr.setText(schoolModel.getSchoolAddress());
            List<ClassModel> classModelList = ZSApp.getInstance().getClassModelList();
            List<Map<String, Object>> listems = new ArrayList<>();
            int size = classModelList.size();
            for (int i = 0; i < size; i++) {
                HashMap<String, Object> classdata = new HashMap<>();
                classdata.put("class", classModelList.get(i).getClassName());
                listems.add(classdata);
            }
            listViewClass.setAdapter(new SimpleAdapter(this.getContext(), listems, R.layout.aboutme_fragment_classitem, new String[]{"class"}, new int[]{R.id.tv_classitem}));
        }
    }

    @Override
    public void showData(Object o) {
    }

    @OnClick(R.id.aboutmeSchool_tv_classs)
    public void onClassClick() {
        if (!isExpanded) {
            Drawable drawable = getActivity().getResources().getDrawable(R.drawable.et_closeicon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth() / 3 * 2, drawable.getMinimumHeight() / 3 * 2);
            tvClasss.setCompoundDrawables(null, null, drawable, null);
            listViewClass.setVisibility(View.VISIBLE);
        } else {
            Drawable drawable = getActivity().getResources().getDrawable(R.drawable.et_openicon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth() / 3 * 2, drawable.getMinimumHeight() / 3 * 2);
            tvClasss.setCompoundDrawables(null, null, drawable, null);
            listViewClass.setVisibility(View.GONE);
        }
        isExpanded = !isExpanded;
    }

    @OnItemClick(aboutmeSchool_lv_class)
    public void onItemClick() {
//        listViewClass.setVisibility(View.GONE);
//        Drawable drawable = getActivity().getResources().getDrawable(R.drawable.et_openicon);
//        drawable.setBounds(0, 0, drawable.getMinimumWidth() / 3 * 2, drawable.getMinimumHeight() / 3 * 2);
//        isExpanded = false;
    }
}
