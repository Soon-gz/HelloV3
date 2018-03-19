package com.abings.baby.ui.measuredata;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.attendance.AttendanceActivity;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.abings.baby.ui.measuredata.remark.ReMarkActivity;
import com.abings.baby.ui.measuredata.teachingplan.TeachingPlanActivity;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.model.MeasureModel;
import com.hellobaby.library.data.model.TAlertBooleanModel;
import com.hellobaby.library.data.model.TAlertModel;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

import static com.hellobaby.library.Const.NORMAL_ACTIVITY_RESULT;

/**
 * 数据界面
 */
public class MeasureActivity extends BaseTitleActivity implements LineCharMvpView{
    @Inject
    LineCharPresenter presenter;
    @BindView(R.id.measure_tv_weight)
    TextView tv_weight;
    @BindView(R.id.measure_tv_height)
    TextView tv_height;
    @BindView(R.id.measure_attendance)
    LinearLayout measure_attendance;
    Badge measure_attendanceBadge;
    @BindView(R.id.measure_attendanceicon)
    CircleImageView measure_attendanceicon;
    @BindView(R.id.measure_teachingplan)
    LinearLayout measure_teachingplan;
    Badge measure_teachingplanBadge;
    @BindView(R.id.measure_teachingplanicon)
    CircleImageView measure_teachingplanicon;
    @BindView(R.id.measure_remark)
    LinearLayout measure_remark;
    Badge measure_remarkBadge;
    @BindView(R.id.measure_remarkicon)
    CircleImageView measure_remarkicon;
    boolean isNewHeight=false;
    boolean isNewWeight=false;
    TAlertModel alertModel= new TAlertModel();
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_measure;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        presenter.selectLatestHeight(ZSApp.getInstance().getBabyId());
        presenter.selectAlert();
        setBtnLeftClickFinish();
        if(ZSApp.getInstance().getBabyModel().isCreator()&&!ZSApp.getInstance().getClassId().equals("0")){
            measure_attendance.setVisibility(View.VISIBLE);
            measure_attendanceBadge = new QBadgeView(this).bindTarget(measure_attendance).setGravityOffset(20f, 45f, true).setBadgePadding(6,true).setShowShadow(false);
        }else {
            measure_attendance.setVisibility(View.GONE);
        }
        if(ZSApp.getInstance().getClassId().equals("0")){
            measure_teachingplan.setVisibility(View.GONE);
            measure_remark.setVisibility(View.GONE);
        }else {
            measure_teachingplanBadge = new QBadgeView(this).bindTarget(measure_teachingplan).setGravityOffset(20f, 45f, true).setBadgePadding(6,true).setShowShadow(false);
            measure_remarkBadge = new QBadgeView(this).bindTarget(measure_remark).setGravityOffset(20f, 45f, true).setBadgePadding(6,true).setShowShadow(false);
            measure_teachingplan.setVisibility(View.VISIBLE);
            measure_remark.setVisibility(View.VISIBLE);
        }
        if(ZSApp.getInstance().gettAlertBooleanModel()!=null&&ZSApp.getInstance().gettAlertBooleanModel().getTeaching()==0){
            setTeachingplanRead(false);
        }else {
            setTeachingplanRead(true);
        }
        if(ZSApp.getInstance().gettAlertBooleanModel()!=null&&ZSApp.getInstance().gettAlertBooleanModel().getAttendance()==0){
            setAttendanceRead(false);
        }else {
            setAttendanceRead(true);
        }
        if(ZSApp.getInstance().gettAlertBooleanModel()!=null&&ZSApp.getInstance().gettAlertBooleanModel().getEvaluation()==0){
            setRemarkRead(false);
        }else {
            setRemarkRead(true);
        }
        //统计测试下
        MobclickAgent.setDebugMode(true);
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
        // MobclickAgent.setAutoLocation(true);
        // MobclickAgent.setSessionContinueMillis(1000);
        // MobclickAgent.startWithConfigure(
        // new UMAnalyticsConfig(mContext, "4f83c5d852701564c0000011", "Umeng",
        // EScenarioType.E_UM_NORMAL));
        MobclickAgent.setScenarioType(bContext, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showError(String err) {

    }

    @OnClick({R.id.measure_weight, R.id.measure_height, R.id.measure_remark,R.id.measure_teachingplan,R.id.measure_attendance})
    public void ItemClick(View view) {
        Intent intent = new Intent(this, LineCharActivity.class);
        switch (view.getId()) {
            case R.id.measure_weight:
                MobclickAgent.onEvent(bContext,"measure_weight");
                intent.putExtra("isHeight", "0");
                intent.putExtra("isNew",isNewWeight?"1":"0");
                startActivityForResult(intent,NORMAL_ACTIVITY_RESULT);
                break;
            case R.id.measure_height:
                intent.putExtra("isHeight", "1");
                intent.putExtra("isNew",isNewHeight?"1":"0");
                startActivityForResult(intent,NORMAL_ACTIVITY_RESULT);
                break;
            case R.id.measure_remark:
                setRemarkRead(true);
                alertModel=new TAlertModel();
                alertModel.setEvaluationLastMaxTime(System.currentTimeMillis());
                presenter.updateAlert(alertModel);
                Intent intentRemark = new Intent(bContext, ReMarkActivity.class);
                startActivityForResult(intentRemark,NORMAL_ACTIVITY_RESULT);
                break;
            case R.id.measure_teachingplan:
                alertModel=new TAlertModel();
                alertModel.setTeachingLastMaxTime(System.currentTimeMillis());
                presenter.updateAlert(alertModel);
                setTeachingplanRead(true);
                Intent intentplan = new Intent(bContext, TeachingPlanActivity.class);
                startActivityForResult(intentplan,NORMAL_ACTIVITY_RESULT);
                break;
            case R.id.measure_attendance:
                alertModel=new TAlertModel();
                alertModel.setAttendanceLastMaxTime(System.currentTimeMillis());
                presenter.updateAlert(alertModel);
                setAttendanceRead(true);
                Intent attendance = new Intent(bContext, AttendanceActivity.class);
                startActivityForResult(attendance,NORMAL_ACTIVITY_RESULT);
                break;
        }
    }

    @Override
    public void setLastData(JSONObject jsonObject) {
        String weight = jsonObject.getString("weight");
        if(!weight.equals("0.0")) {
            tv_weight.setText(weight + "kg");
            tv_weight.setVisibility(View.VISIBLE);
            isNewWeight = false;
        }else {
            isNewWeight = true;
            tv_weight.setVisibility(View.GONE);
        }
        String height = jsonObject.getString("height");
        if(!height.equals("0.0")) {
            tv_height.setText(height + "cm");
            tv_height.setVisibility(View.VISIBLE);
            isNewHeight=false;
        }else {
            isNewHeight=true;
            tv_height.setVisibility(View.GONE);
        }
    }

    @Override
    public void selectHisHeight(List<MeasureModel> models) {

    }

    @Override
    public void showBadgeView(TAlertBooleanModel tAlertBooleanModel) {
       if(tAlertBooleanModel.getAttendance()==0){
           setAttendanceRead(false);
       }
        if(tAlertBooleanModel.getTeaching()==0){
            setTeachingplanRead(false);
        }
        if(tAlertBooleanModel.getEvaluation()==0){
            setRemarkRead(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        presenter.selectLatestHeight(ZSApp.getInstance().getBabyId());
        presenter.selectAlert();
    }

    public void setTeachingplanRead(boolean b){
        if(b==true&&measure_teachingplanBadge!=null){
            measure_teachingplanBadge.setBadgeNumber(0);
        }else {
            if(measure_teachingplanBadge!=null) {
                measure_teachingplanBadge.setBadgeNumber(-1);
            }
        }
    }
    public void setRemarkRead(boolean b){
        if(b==true&&measure_remarkBadge!=null){
            measure_remarkBadge.setBadgeNumber(0);
        }else {
            if(measure_remarkBadge!=null) {
                measure_remarkBadge.setBadgeNumber(-1);
            }
        }
    }
    public void setAttendanceRead(boolean b){
        if(b==true&&measure_attendanceBadge!=null){
            measure_attendanceBadge.setBadgeNumber(0);
        }else {
            if(measure_attendanceBadge!=null) {
                measure_attendanceBadge.setBadgeNumber(-1);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(bContext.toString().substring(bContext.toString().lastIndexOf(".") + 1, bContext.toString().indexOf("@")));
        MobclickAgent.onResume(bContext);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(bContext.toString().substring(bContext.toString().lastIndexOf(".") + 1, bContext.toString().indexOf("@")));
        MobclickAgent.onPause(bContext);
    }
}
