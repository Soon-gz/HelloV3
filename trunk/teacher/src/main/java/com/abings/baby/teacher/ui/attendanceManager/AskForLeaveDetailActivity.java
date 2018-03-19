package com.abings.baby.teacher.ui.attendanceManager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.hellobaby.library.data.model.AskForLeaveHistoryModel;
import com.hellobaby.library.data.model.AskForLeaveModel;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.BottomDialogUtils;

import org.feezu.liuli.timeselector.TimeSelector;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class AskForLeaveDetailActivity extends BaseLibTitleActivity<String>  {

    public static final String IS_WAIT_DETAIL = "isWaitDetail"; //1 请假申请  2 查看请假信息  3 审批请假
    public static final String HISTORY_MODEL = "historyModel";

    @Inject
    AskForLeavePresenter presenter;

    @BindView(R.id.compassionate_sick_leave)
    RadioGroup compassionateSickLeave;
    @BindView(R.id.choose_start_date)
    RelativeLayout chooseStartDate;
    @BindView(R.id.choose_end_date)
    RelativeLayout chooseEndDate;
    @BindView(R.id.content_description)
    EditText contentDescription;
    @BindView(R.id.end_state_text)
    TextView endStateText;
    @BindView(R.id.start_date_text)
    TextView startDateText;
    @BindView(R.id.sick_leave)
    RadioButton sickLeave;
    @BindView(R.id.compassionate_leave)
    RadioButton compassionateLeave;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.btn_agree)
    Button btn_agree;
    @BindView(R.id.btn_disagree)
    Button btn_disagree;

    //时间选择器
    TimeSelector timeSelector;
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    private  String startDate = DateUtil.getCurrentTime(DATE_FORMAT);

    private int type = 2; //请假类型  1 病假  2 事假
    private String reason;//请假原因
    private long startTimeLong = -1;//请假开始时间
    private long endTimeLong = -1;//请假结束时间

    private int detailType = 1;//两个界面合一  申请界面和请假详情
    private AskForLeaveModel leaveModel;//校长审批界面携带的参数

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_ask_for_leave_detail;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent, this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);

        bTitleBaseRL.setBackgroundResource(R.color.white);

        detailType = getIntent().getIntExtra(IS_WAIT_DETAIL,1);
        switch (detailType){
            case 1:
                //请假
                initAskforLeave();
                break;
            case 2:
                //查看请假信息
                initAskForLeaveDeatil();
                break;
            case 3:
                //审批请假
                initApprovalLeave();
                break;
        }

    }

    /**
     * 初始化请假审批
     */
    private void initApprovalLeave() {
        setBtnLeftClickFinish();
        chooseStartDate.setClickable(false);
        chooseEndDate.setClickable(false);
        sickLeave.setClickable(false);
        compassionateLeave.setClickable(false);
        contentDescription.setClickable(false);
        contentDescription.setFocusable(false);
        contentDescription.setFocusableInTouchMode(false);
        contentDescription.setKeyListener(null);
        btnCommit.setVisibility(View.GONE);
        btn_agree.setVisibility(View.VISIBLE);
        btn_disagree.setVisibility(View.VISIBLE);
        leaveModel = (AskForLeaveModel) getIntent().getSerializableExtra(HISTORY_MODEL);
        if (leaveModel != null){
            String startDateStr = DateUtil.getFormatTimeFromTimestamp(leaveModel.getStartTime(),"yyyy-MM-dd HH:mm");
            String endDateStr = DateUtil.getFormatTimeFromTimestamp(leaveModel.getEndTime(),"yyyy-MM-dd HH:mm");
            setTitleText(leaveModel.getTeacherName());
            startDateText.setText(startDateStr);
            endStateText.setText(endDateStr);
            contentDescription.setText(leaveModel.getLeaveReason());
        }
    }

    /**
     * 初始化发起申请的界面
     */
    public void initAskforLeave(){
        setTitleText("请假");
        setBtnLeftClickFinish();
        btnCommit.setVisibility(View.GONE);
        compassionateSickLeave.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case 0:
                        type = 1;
                        break;
                    case 1:
                        type = 2;
                        break;
                }
            }
        });
    }

    /**
     * 初始化请假详情界面
     */
    public void initAskForLeaveDeatil(){
        setBtnLeftClickFinish();
        chooseStartDate.setClickable(false);
        chooseEndDate.setClickable(false);
        sickLeave.setClickable(false);
        compassionateLeave.setClickable(false);
        btnCommit.setClickable(false);
        contentDescription.setClickable(false);
        contentDescription.setFocusable(false);
        contentDescription.setFocusableInTouchMode(false);
        contentDescription.setKeyListener(null);
        AskForLeaveHistoryModel historyModel = (AskForLeaveHistoryModel) getIntent().getSerializableExtra(HISTORY_MODEL);
        if (historyModel == null){
            showError("请假详情为空数据！");
            finish();
        }else {
           switch (historyModel.getType()){
               case 1:
                   sickLeave.setChecked(true);
                   compassionateLeave.setChecked(false);
                   break;
               case 2:
                   sickLeave.setChecked(false);
                   compassionateLeave.setChecked(true);
                   break;
           }
            contentDescription.setText(historyModel.getLeaveReason());
            btnCommit.setBackgroundResource(R.drawable.btn_bg_unable);
            String state = historyModel.getState() == 0 ? "待处理" : historyModel.getState() == 1 ? "已批准" : "已拒绝";
            btnCommit.setText(state);
            String startDateStr = DateUtil.getFormatTimeFromTimestamp(historyModel.getStartTime(),"yyyy-MM-dd HH:mm");
            String endDateStr = DateUtil.getFormatTimeFromTimestamp(historyModel.getEndTime(),"yyyy-MM-dd HH:mm");
            startDateText.setText(startDateStr);
            endStateText.setText(endDateStr);
            String createTime = DateUtil.getFormatTimeFromTimestamp(historyModel.getCreateTime(),"yyyy.MM.dd");
            setTitleText(createTime);
        }
    }

    @OnClick({R.id.choose_start_date,R.id.choose_end_date,R.id.btn_commit,R.id.btn_agree,R.id.btn_disagree})
    public void chooseClick(View view){
        switch (view.getId()){
            case R.id.choose_start_date:
                String startDateEnd = getNextYearDate();
                timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        startDate = time;
                        Date date = DateUtil.getTimeFromString(time,DATE_FORMAT);
                        startTimeLong = date.getTime();
                        if (endTimeLong > 0){
                            btnCommit.setVisibility(View.VISIBLE);
                        }
                        startDateText.setText(time);
                    }
                },startDate,startDateEnd);
                timeSelector.setIsLoop(false);
                timeSelector.show();
                break;
            case R.id.choose_end_date:
                String endDateEnd = getNextYearDate();
                timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        endStateText.setText(time);
                        Date date = DateUtil.getTimeFromString(time,DATE_FORMAT);
                        endTimeLong = date.getTime();
                        if (startTimeLong > 0){
                            btnCommit.setVisibility(View.VISIBLE);
                        }
                    }
                },startDate,endDateEnd);
                timeSelector.setIsLoop(false);
                timeSelector.show();
                break;
            case R.id.btn_commit:
                reason = contentDescription.getText().toString().trim();
                if (startTimeLong > endTimeLong){
                    showError("请假的结束时间必须大于起始时间！");
                }else {
                    presenter.insertLeaveTeacher(startTimeLong,endTimeLong,type,reason);
                }
                break;
            case R.id.btn_agree:
                if (leaveModel != null){
                    BottomDialogUtils.getBottomPrizeDrawDialog(this, "是否同意" + leaveModel.getTeacherName() + "的请假申请？", "确认", "取消", new BottomDialogUtils.onSureAndCancelClick() {
                        @Override
                        public void onItemClick() {
                            presenter.updateLeaveState(leaveModel.getLeaveId(),1);
                        }

                        @Override
                        public void onCancel() {
                        }
                    });
                }else{
                    showError("请假数据为空，出现异常！");
                }
                break;
            case R.id.btn_disagree:
                if (leaveModel != null){
                    BottomDialogUtils.getBottomPrizeDrawDialog(this, "是否拒绝" + leaveModel.getTeacherName() + "的请假申请？", "确认", "取消", new BottomDialogUtils.onSureAndCancelClick() {
                        @Override
                        public void onItemClick() {
                            presenter.updateLeaveState(leaveModel.getLeaveId(),2);
                        }

                        @Override
                        public void onCancel() {
                        }
                    });
                }else{
                    showError("请假数据为空，出现异常！");
                }
                break;
        }
    }

    private String getNextYearDate() {
        Date currentDate = new Date();
        currentDate.setYear(currentDate.getYear()+1);
        String nextYearDate = DateUtil.getFormatTimeFromTimestamp(currentDate.getTime(),DATE_FORMAT);
        return nextYearDate;
    }

    @Override
    public void showData(String o) {
        switch (detailType){
            case 1:
                showToast("请假申请提交成功，请等待审核。");
                finish();
                break;
            case 3:
                showToast("请假审批完成！");
                setResult(AskForLeaveManagerActivity.RESULT_MANAGER_CODE);
                finish();
                break;
        }
    }
}
