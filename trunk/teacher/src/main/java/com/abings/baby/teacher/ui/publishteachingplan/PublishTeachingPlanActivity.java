package com.abings.baby.teacher.ui.publishteachingplan;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.abings.baby.teacher.ui.publishpicture.GridAdapter;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;
import com.google.gson.Gson;
import com.hellobaby.library.data.model.ClassModel;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.BottomPickerDateDialog;
import com.hellobaby.library.widget.ClassSelectedAdapter;
import com.hellobaby.library.widget.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 教学计划
 */
public class PublishTeachingPlanActivity extends BaseTitleActivity implements TeachingPlanMvpView {
    @Inject
    TeachingPlanPresenter presenter;
    @BindView(R.id.ptp_tv_date)
    TextView date;
    @BindView(R.id.gridView)
    GridView gridView;
    private GridAdapter gridAdapter;
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();
    @BindView(R.id.aboutme_tv_class)
    TextView aboutme_tv_class;
    @BindView(R.id.aboutme_lv_class)
    ListView aboutme_lv_class;
    Boolean isExpanded = false;
    List<ClassModel> listems = new ArrayList<>();
    String classstr = "";
    private ClassSelectedAdapter adapter;
    private Gson gson = new Gson();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_publishteachingplan;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }


    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        presenter.attachView(this);
        setBtnRightDrawableRes(R.drawable.title_update);
        bIvRight.setVisibility(View.GONE);
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 1 ? 1 : cols;
        gridView.setNumColumns(3);
        // preview
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imgs = (String) parent.getItemAtPosition(position);
                images = new ArrayList<>();
                images.addAll(imagePaths);
                if (images.contains(GridAdapter.ADD) || images.size() >= 1) {
                    images.remove(GridAdapter.ADD);
                }
                if (GridAdapter.ADD.equals(imgs)) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(PublishTeachingPlanActivity.this);
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(true); // 是否显示拍照
                    intent.setMaxTotal(1); // 最多选择照片数量，默认为6
                    intent.setSelectedPaths(images); // 已选中的照片地址， 用于回显选中状态
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                } else {
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(PublishTeachingPlanActivity.this);
                    intent.setCurrentItem(position);
                    intent.setPhotoPaths(images);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }
        });
        if (images.size() < 1) {
            imagePaths.add(GridAdapter.ADD);
        }
        gridAdapter = new GridAdapter(this, imagePaths);
        gridView.setAdapter(gridAdapter);
        //


        listems.addAll(ZSApp.getInstance().getClassModelList());
        adapter = new ClassSelectedAdapter(listems, bContext);
        aboutme_lv_class.setAdapter(adapter);
        aboutme_lv_class.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapter.setOnCheckedChangeListener(new ClassSelectedAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(int position, boolean isChecked) {
                listems.get(position).setSelected(isChecked);
                adapter.notifyDataSetChanged();
                List<ClassModel> listSelected = new ArrayList<>();
                for (ClassModel clsm : listems) {
                    if (clsm.isSelected()) {
                        //有班级被选中
                        listSelected.add(clsm);
                    }
                }
                int selectedCount = listSelected.size();
                if(selectedCount>0){
                    String[] classIds = new String[selectedCount];
                    for (int i = 0; i <selectedCount ; i++) {
                        classIds[i] = listSelected.get(i).getClassId();
                    }
                    //产生班级的数组
                    classstr = gson.toJson(classIds);
                }else {
                    classstr= "";
                }
                setBtnRightListener();
            }
        });

        bIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backListener();
            }
        });
    }


    @Override
    public void showData(Object o) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    loadAdpater(list);
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    loadAdpater(ListExtra);
                    break;
            }
        }
    }

    private void loadAdpater(ArrayList<String> paths) {
        if (imagePaths != null && imagePaths.size() > 0) {
            imagePaths.clear();
        }
        if (paths.contains(GridAdapter.ADD)) {
            paths.remove(GridAdapter.ADD);
        }
        if (paths.size() < 1)
            paths.add(GridAdapter.ADD);
        imagePaths.addAll(paths);
        gridAdapter = new GridAdapter(this, imagePaths);
        gridView.setAdapter(gridAdapter);
        setBtnRightListener();
    }

    @OnClick(R.id.aboutme_tv_class)
    public void onClassClick() {
        if (!isExpanded) {
            Drawable drawable = getResources().getDrawable(R.drawable.et_closeicon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth() / 3 * 2, drawable.getMinimumHeight() / 3 * 2);
            aboutme_tv_class.setCompoundDrawables(null, null, drawable, null);
            aboutme_lv_class.setVisibility(View.VISIBLE);
        } else {
            Drawable drawable = getResources().getDrawable(R.drawable.et_openicon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth() / 3 * 2, drawable.getMinimumHeight() / 3 * 2);
            aboutme_tv_class.setCompoundDrawables(null, null, drawable, null);
            aboutme_lv_class.setVisibility(View.GONE);
        }
        isExpanded = !isExpanded;
    }

    @OnClick(R.id.ptp_tv_date)
    public void birthdayOnClick() {
        String birthday = date.getText().toString().trim();
//        BottomDialogUtils.getBottomDatePickerDialog(this, birthday, false, new BottomPickerDateDialog.BottomOnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth, String showDate) {
//                date.setText(showDate);
//                setBtnRightListener();
//            }
//        });
        String []weekTime=new String[4];
        weekTime=DateUtil.getWeekList(4).toArray(new String[4]);
        final String[] finalWeekTime = weekTime;
        BottomDialogUtils.getBottomWeekDialog(this, weekTime, new BottomDialogUtils.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                date.setText(finalWeekTime[position]);
                setBtnRightListener();
            }
        });
    }

    @Override
    protected void btnRightOnClick(View v) {
        List<String> listImage = new ArrayList<>();
        //创建的时候，不提示
        if (imagePaths.contains(GridAdapter.ADD)) {
            listImage = imagePaths.subList(0, imagePaths.size() - 1);
        } else {
            listImage.addAll(imagePaths);
        }
        String time;
        String time2;
        if (listImage.size() == 0) {
            showToast("请添加教学计划图片");
            return;
        }

        time = DateUtil.upServerDateFormat(date.getText().toString().split("-")[0]);
        time2 = DateUtil.upServerDateFormat(date.getText().toString().split("-")[1]);
        presenter.createTeacherPlan(classstr, time,time2,listImage.get(0));
//        finish();
    }

    @Override
    public void showTeachingPlanList(List list) {

    }

    @Override
    public void publishPlanSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void publishDeleteSuccess(int posion) {

    }

    /**
     * 右上角的监听
     */
    private void setBtnRightListener() {
        String dateStr = date.getText().toString().trim();
        if ((!imagePaths.get(0).equals(GridAdapter.ADD)) && (classstr.length() > 0) && (dateStr.length() > 0)) {
            //图片还为加入,班级是否有选择，日期是否已经选择
            bIvRight.setVisibility(View.VISIBLE);
        } else {
            bIvRight.setVisibility(View.GONE);
        }
    }

    /**
     * 返回监听
     */
    private void backListener() {
        String dateStr = date.getText().toString().trim();
        if ((!imagePaths.get(0).equals(GridAdapter.ADD)) || (classstr.length() > 0) || (dateStr.length() > 0)) {
            //图片还为加入,班级是否有选择，日期是否已经选择
            BottomDialogUtils.getBottomExitEditDialog(this);
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            backListener();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
