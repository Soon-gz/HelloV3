package com.abings.baby.ui.main.fm.aboutme;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.base.BaseFragment;
import com.abings.baby.ui.main.fm.MeMvpView;
import com.abings.baby.ui.pay.SelectdateActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.ui.common.CommAlterActivity;
import com.hellobaby.library.ui.common.CommAlterBean;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.BottomPickerDateDialog;

import butterknife.BindView;
import butterknife.OnClick;

import static android.R.attr.duration;
import static android.R.attr.startDelay;


@SuppressLint("ValidFragment")
public class AboutMeBabyFragment extends BaseFragment {

    @BindView(R.id.aboutmeBaby_tv_name)
    TextView tvName;//姓名
    @BindView(R.id.aboutmeBaby_tv_address)
    TextView tvAddress;//出生地
    @BindView(R.id.aboutmeBaby_tv_sex)
    TextView tvSex;//性别标题
    @BindView(R.id.aboutmeBaby_iv_sex)
    ImageView ivSex;//性别展开图标
    @BindView(R.id.aboutmeBaby_tv_sex1)
    TextView tvSex1;//男
    @BindView(R.id.aboutmeBaby_tv_sex2)
    TextView tvSex2;//女
    @BindView(R.id.aboutmeBaby_tv_birthday)
    TextView tvBirthday;//生日
    @BindView(R.id.aboutmeBaby_tv_kindergarten)
    TextView tvKindergarten;//幼儿园
    @BindView(R.id.aboutmeBaby_tv_cancelCareBaby)
    TextView tvCancelCareBaby;//取消关注宝宝
    @BindView(R.id.aboutmeBaby_tv_gkk)
    TextView tvGkk;//公开课
    Boolean isShowSex = true;
    String oldSex;

    private MeMvpView meMvpView;
    private BabyModel babyModel;
    public AboutMeBabyFragment(){}
    public AboutMeBabyFragment(MeMvpView mvpView) {
        this.meMvpView = mvpView;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_aboutme_baby;
    }

    @Override
    protected void initViewsAndEvents() {

        setBabyInfo();
    }

    public void setBabyInfo() {
        babyModel = ZSApp.getInstance().getBabyModel();
        tvName.setText(babyModel.getBabyName());
        tvSex.setText(babyModel.getBabyGenderName());
        tvSex.setAnimation(new Animation() {
        });
        if (babyModel.getBirthday() != null) {
            tvBirthday.setText(DateUtil.ServerDate2NYRFormat(babyModel.getBirthday()));
        } else {
            tvBirthday.setText("");
        }
        tvAddress.setText(babyModel.getAddress());
        String className = babyModel.getSchoolName()+
                ((babyModel.getGradeName()==null||babyModel.getGradeName().isEmpty())?"":"/"+babyModel.getGradeName())+
                ((babyModel.getClassName()==null||babyModel.getClassName().isEmpty())?"":"/"+babyModel.getClassName());
        tvKindergarten.setText(className);
        if (ZSApp.getInstance().getBabyModel().isCreator()) {
            //是主联系人,可以编辑
            tvName.setEnabled(true);
            tvSex.setEnabled(true);
            tvBirthday.setEnabled(true);
            tvAddress.setEnabled(true);
//            setDrawable(tvName, R.drawable.et_icon);
            ivSex.setVisibility(View.VISIBLE);
//            setDrawable(tvSex, R.drawable.et_openicon);
            setDrawable(tvBirthday, R.drawable.et_icon);
            setDrawable(tvAddress, R.drawable.et_icon);
            tvCancelCareBaby.setVisibility(View.GONE);
            tvCancelCareBaby.setEnabled(false);
            tvGkk.setVisibility(View.VISIBLE);
        } else {
            tvName.setEnabled(false);
            tvSex.setEnabled(false);
            ivSex.setVisibility(View.INVISIBLE);
            tvBirthday.setEnabled(false);
            tvAddress.setEnabled(false);
            tvName.setCompoundDrawables(null, null, null, null);
            tvSex.setCompoundDrawables(null, null, null, null);
            tvBirthday.setCompoundDrawables(null, null, null, null);
            tvAddress.setCompoundDrawables(null, null, null, null);
            tvCancelCareBaby.setVisibility(View.VISIBLE);
            tvCancelCareBaby.setEnabled(true);
            tvGkk.setVisibility(View.INVISIBLE);
        }
        tvGkk.setVisibility(View.GONE);
    }

    private void setDrawable(TextView tv, int drawableRes) {
        Drawable drawable = getActivity().getResources().getDrawable(drawableRes);
        drawable.setBounds(0, 0, drawable.getMinimumWidth() / 3 * 2, drawable.getMinimumHeight() / 3 * 2);
        tv.setCompoundDrawables(null, null, drawable, null);
    }

    @OnClick({R.id.aboutmeBaby_tv_name, R.id.aboutmeBaby_tv_address})
    public void nameClick(View view) {
        int id = view.getId();
        Intent intent = new Intent(getContext(), CommAlterActivity.class);
        if (R.id.aboutmeBaby_tv_name == id) {
            return;
//            CommAlterBean commAlterBean = new CommAlterBean(id);
//            commAlterBean.setOldValue(tvName.getText().toString());
//            commAlterBean.setInputLength(16);
//            intent.putExtra(CommAlterBean.kName, commAlterBean);
        } else if (R.id.aboutmeBaby_tv_address == id) {
            CommAlterBean commAlterBean = new CommAlterBean(id);
            commAlterBean.setOldValue(tvAddress.getText().toString());
            commAlterBean.setInputLength(50);
            intent.putExtra(CommAlterBean.kName, commAlterBean);
        }
        //这里要用bean里获取id
        startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }

    @OnClick({R.id.aboutmeBaby_tv_sex,R.id.aboutmeBaby_iv_sex})
    public void onSexClick() {
        if (isShowSex) {
            oldSex = tvSex.getText().toString();
            ObjectAnimator oa=ObjectAnimator.ofFloat(ivSex, "rotation", 0,180);
            oa.setDuration(Const.ROAT_TIME);
            oa.start();
//            Drawable drawable = getActivity().getResources().getDrawable(R.drawable.et_closeicon);
//            drawable.setBounds(0, 0, drawable.getMinimumWidth() / 3 * 2, drawable.getMinimumHeight() / 3 * 2);
//            tvSex.setCompoundDrawables(null, null, drawable, null);
            tvSex1.setVisibility(View.VISIBLE);
            tvSex2.setVisibility(View.VISIBLE);
        } else {
            ObjectAnimator oa=ObjectAnimator.ofFloat(ivSex, "rotation", 180,360);
            oa.setDuration(Const.ROAT_TIME);
            oa.start();
            tvSex.setText(oldSex);
            tvSex1.setVisibility(View.GONE);
            tvSex2.setVisibility(View.GONE);
//            Drawable drawable = getActivity().getResources().getDrawable(R.drawable.et_openicon);
//            drawable.setBounds(0, 0, drawable.getMinimumWidth() / 3 * 2, drawable.getMinimumHeight() / 3 * 2);
//            tvSex.setCompoundDrawables(null, null, drawable, null);
        }
        isShowSex = !isShowSex;
    }

    @OnClick({R.id.aboutmeBaby_tv_sex1, R.id.aboutmeBaby_tv_sex2})
    public void onSexItemClick(TextView view) {
        tvSex1.setVisibility(View.GONE);
        tvSex2.setVisibility(View.GONE);
        tvSex.setText(view.getText().toString());
        oldSex = view.getText().toString();
        ObjectAnimator oa=ObjectAnimator.ofFloat(ivSex, "rotation", 180,360);
        oa.setDuration(Const.ROAT_TIME);
        oa.start();
//        Drawable drawable = getActivity().getResources().getDrawable(R.drawable.et_openicon);
//        drawable.setBounds(0, 0, drawable.getMinimumWidth() / 3 * 2, drawable.getMinimumHeight() / 3 * 2);
//        tvSex.setCompoundDrawables(null, null, drawable, null);
        isShowSex = true;
        // 修改
        String sexCode = "男孩".equals(view.getText().toString().trim()) ? "1" : "0";
        babyModel.setBabyGender(sexCode);
        meMvpView.babyUpdateInfo(babyModel);
    }

    @OnClick(R.id.aboutmeBaby_tv_birthday)
    public void birthdayOnClick(View view) {
        String birthday = tvBirthday.getText().toString().trim();
        BottomDialogUtils.getBottomDatePickerDialog(getActivity(), birthday, true, new BottomPickerDateDialog.BottomOnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth, final String showDate) {
                String[] items = {"修改后将清空宝宝的身高和体重数据","是","否"};
                BottomDialogUtils.getBottomListDialog(getActivity(), items, new BottomDialogUtils.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                        if(position==1){
                            tvBirthday.setText(showDate);
                            // 修改
                            String upServerDateFormatBirthday = DateUtil.upServerDateFormat(showDate);
                            babyModel.setBirthday(upServerDateFormatBirthday);
                            meMvpView.babyUpdateInfo(babyModel);
                            meMvpView.deleteHeightWeightByBabyId(String.valueOf(ZSApp.getInstance().getBabyModel().getBabyId()));
                        }
                    }
                });
            }
        });
    }

    @OnClick(R.id.aboutmeBaby_tv_cancelCareBaby)
    public void cancelCareBaby(View view) {
        //取消关注宝宝
        meMvpView.cancelCareBabyClick(String.valueOf(ZSApp.getInstance().getLoginUser().getUserId()),String.valueOf(ZSApp.getInstance().getBabyId()),true);
    }

    @OnClick(R.id.aboutmeBaby_tv_gkk)
    public void goPay(View view) {
       startActivityForResult(new Intent(getContext(), SelectdateActivity.class), Const.NORMAL_ACTIVITY_RESULT);
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 0 && null != data) {
            if (resultCode == (tvName.getId() & 0x0000ffff)) {
                CommAlterBean commAlterBean = (CommAlterBean) data.getSerializableExtra(CommAlterBean.kName);
                tvName.setText(commAlterBean.getNewValue());
                // 修改
                babyModel.setBabyName(tvName.getText().toString().trim());
                meMvpView.babyUpdateInfo(babyModel);
            } else if (resultCode == (tvAddress.getId() & 0x0000ffff)) {
                CommAlterBean commAlterBean = (CommAlterBean) data.getSerializableExtra(CommAlterBean.kName);
                babyModel.setAddress(commAlterBean.getNewValue());
                tvAddress.setText(commAlterBean.getNewValue());
                // 修改
                meMvpView.babyUpdateInfo(babyModel);
            }
        }
    }

    private ObjectAnimator rotation(Object target, int from, int to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(duration);
        animator.setStartDelay(startDelay);
        animator.start();
        return animator;
    }
}
