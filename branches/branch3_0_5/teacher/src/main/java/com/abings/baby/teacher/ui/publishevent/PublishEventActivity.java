package com.abings.baby.teacher.ui.publishevent;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.abings.baby.teacher.ui.main.MainActivity;
import com.hellobaby.library.data.model.ClassModel;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.ui.LoginUtils;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.BottomPickerDateDialog;
import com.hellobaby.library.widget.ClassSelectedAdapter;
import com.hellobaby.library.widget.ToastUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.abings.baby.teacher.R.id.aboutme_lv_class;
import static com.abings.baby.teacher.R.id.aboutme_tv_class;

/**
 * 发布消息
 */
public class PublishEventActivity extends BaseTitleActivity implements PublishEventMvpView {
    @Inject
    PublishEventPresenter presenter;
    @BindView(aboutme_tv_class)
    TextView tvClass;
    @BindView(aboutme_lv_class)
    ExpandListView lvClass;
    @BindView(R.id.event_et_title)
    EditText etTitle;
    @BindView(R.id.event_tv_stime)
    TextView tvSTime;
    @BindView(R.id.event_tv_etime)
    TextView tvETime;
    @BindView(R.id.event_et_address)
    EditText etAddress;
    @BindView(R.id.event_et_fee)
    EditText etFee;
    @BindView(R.id.event_et_pcount)
    EditText etPcount;
    @BindView(R.id.event_tv_deadline)
    TextView tvDeadline;
    @BindView(R.id.event_et_content)
    EditText etContent;
    private ClassSelectedAdapter classSelectedAdapter;
    private List<ClassModel> listClass;
    private Boolean isExpanded = false;
    private EventModel eventModel;
    private String classIds = "";//班级的ids格式为1，2

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_publishevent;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }


    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        setBtnRightDrawableRes(R.drawable.commit_icon);
        bIvRight.setVisibility(View.GONE);
        setBtnLeftClickFinish();
        presenter.attachView(this);
        listClass = new ArrayList<>();
        listClass.addAll(ZSApp.getInstance().getClassModelList());
        classSelectedAdapter = new ClassSelectedAdapter(listClass, bContext);
        lvClass.setAdapter(classSelectedAdapter);
        lvClass.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        classSelectedAdapter.setOnCheckedChangeListener(new ClassSelectedAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(int position, boolean isChecked) {
                listClass.get(position).setSelected(isChecked);
                classSelectedAdapter.notifyDataSetChanged();
                StringBuffer classSb = new StringBuffer();
                boolean isHaveSelectedClass = false;//是否选中了一个班级
                for (ClassModel classM : listClass) {
                    if (classM.isSelected()) {
                        classSb.append(classM.getClassId()).append(",");
                        isHaveSelectedClass = true;
                    }
                }
                if (isHaveSelectedClass) {
                    //有选中班级
                    String clstr = classSb.toString();
                    classIds = clstr.substring(0, clstr.length() - 1);
                } else {
                    //无
                    classIds = "";
                }
                setRightBtnVisible();
            }
        });

        bIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackListen();
            }
        });

    }


    @Override
    public void showData(Object o) {

    }

    @Override
    protected void btnRightOnClick(View v) {
        eventModel = new EventModel();
        eventModel.setCreatorId(Integer.valueOf(ZSApp.getInstance().getTeacherId()));
        eventModel.setEventTitle(etTitle.getText().toString().trim());
        eventModel.setEventAddress(etAddress.getText().toString().trim());
        eventModel.setEventFee(Double.valueOf(etFee.getText().toString().trim()));
        eventModel.setEventPeople(Integer.valueOf(etPcount.getText().toString().trim()));
        try {
            eventModel.setEventDeadlineTime(DateUtil.day2time(tvDeadline.getHint().toString().trim()));
            eventModel.setEventStartTime(DateUtil.day2time(tvSTime.getHint().toString().trim()));
            eventModel.setEventEndTime(DateUtil.day2time(tvETime.getHint().toString().trim()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        eventModel.setEventDetails(etContent.getText().toString().trim());
        eventModel.setCreatorId(Integer.valueOf(ZSApp.getInstance().getTeacherId()));
        eventModel.setContactorId(Integer.valueOf(ZSApp.getInstance().getTeacherId()));
        if(eventModel.getEventStartTime()>eventModel.getEventEndTime()){//活动起始日期小于早于活动结束日期
            ToastUtils.showNormalToast(bContext,"活动结束日期不能早于活动起始日期");
            return;
        }
        if(eventModel.getEventStartTime()<eventModel.getEventDeadlineTime()){//活动起始时间大于活动报名截止时间
            ToastUtils.showNormalToast(bContext,"活动起始日期不能早于报名截止日期");
            return;
        }
        if(eventModel.getEventEndTime()<eventModel.getEventDeadlineTime()){//活动结束时间小于报名截止时间
            ToastUtils.showNormalToast(bContext,"活动结束日期不能早于报名截止日期");
            return;
        }
        presenter.insertEvents(eventModel, classIds);
    }

    @OnClick(R.id.aboutme_tv_class)
    public void onClassClick() {
        if (!isExpanded) {
            Drawable drawable = getResources().getDrawable(R.drawable.et_closeicon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth() / 3 * 2, drawable.getMinimumHeight() / 3 * 2);
            tvClass.setCompoundDrawables(null, null, drawable, null);
            lvClass.setVisibility(View.VISIBLE);
        } else {
            Drawable drawable = getResources().getDrawable(R.drawable.et_openicon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth() / 3 * 2, drawable.getMinimumHeight() / 3 * 2);
            tvClass.setCompoundDrawables(null, null, drawable, null);
            lvClass.setVisibility(View.GONE);
        }
        isExpanded = !isExpanded;
    }

    @OnClick({R.id.event_tv_stime, R.id.event_tv_etime, R.id.event_tv_deadline})
    public void onTimeClick(final View view) {
        BottomDialogUtils.getEvnetBottomDatePickerDialog(this, null, false, new BottomPickerDateDialog.BottomOnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view2, int year, int monthOfYear, int dayOfMonth, String showData) {
                switch (view.getId()) {
                    case R.id.event_tv_stime:
                        tvSTime.setText("起始时间 " + showData);
                        tvSTime.setHint(showData);
                        setRightBtnVisible();
                        break;
                    case R.id.event_tv_etime:
                        tvETime.setText("结束时间 " + showData);
                        tvETime.setHint(showData);
                        setRightBtnVisible();
                        break;
                    case R.id.event_tv_deadline:
                        tvDeadline.setText("报名截止时间 " + showData);
                        tvDeadline.setHint(showData);
                        setRightBtnVisible();
                        break;
                }
            }
        });
    }

    @OnTextChanged(value = R.id.event_et_title, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void otcTitleAfter(CharSequence charSequence) {
        setRightBtnVisible();
    }

    @OnTextChanged(value = R.id.event_et_address, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void otcAddressAfter(CharSequence charSequence) {
        setRightBtnVisible();
    }

    @OnTextChanged(value = R.id.event_et_fee, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void otcFeeAfter(CharSequence charSequence) {
        setRightBtnVisible();
    }

    @OnTextChanged(value = R.id.event_et_pcount, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void otcPcountAfter(CharSequence charSequence) {
        setRightBtnVisible();
    }

    @OnTextChanged(value = R.id.event_et_content, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void otcContentAfter(CharSequence charSequence) {
        setRightBtnVisible();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setBackListen();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 右上角的按钮可见
     */
    private void setRightBtnVisible() {
        String sTime = tvSTime.getText().toString().trim();
        String eTime = tvETime.getText().toString().trim();
        String deadline = tvDeadline.getText().toString().trim();
        if (classIds.length() > 0 && deadline.length() > 0 && sTime.length() > 0 && eTime.length() > 0 && (!LoginUtils.isEmptyEdit(etTitle, etAddress, etFee, etPcount, etContent))) {
            bIvRight.setVisibility(View.VISIBLE);
        } else {
            bIvRight.setVisibility(View.GONE);
        }
    }
    private void setBackListen(){
        String sTime = tvSTime.getText().toString().trim();
        String eTime = tvETime.getText().toString().trim();
        String deadline = tvDeadline.getText().toString().trim();
        if (classIds.length() > 0 || deadline.length() > 0 || sTime.length() > 0 || eTime.length() > 0 || LoginUtils.isInputEdit(etTitle, etAddress, etFee, etPcount, etContent)) {
            BottomDialogUtils.getBottomExitEditDialog(this);
        } else {
            finish();
        }
    }
    @Override
    public void publishSuccess() {
        setResult(MainActivity.mainRequestCode);
        finish();
    }


}
