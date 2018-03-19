package com.abings.baby.ui.main.fm.aboutme;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.base.BaseFragment;
import com.abings.baby.ui.main.fm.MeMvpView;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.ui.common.CommAlterActivity;
import com.hellobaby.library.ui.common.CommAlterBean;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.BottomPickerDateDialog;

import butterknife.BindView;
import butterknife.OnClick;


@SuppressLint("ValidFragment")
public class AboutMeBabyFragment extends BaseFragment {

    @BindView(R.id.aboutmeBaby_tv_name)
    TextView tvName;//姓名
    @BindView(R.id.aboutmeBaby_tv_address)
    TextView tvAddress;//出生地
    @BindView(R.id.aboutmeBaby_tv_sex)
    TextView tvSex;//性别标题
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
    Boolean isShowSex = true;
    String oldSex;

    private final MeMvpView meMvpView;
    private BabyModel babyModel;

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
        if (babyModel.getBirthday() != null) {
            tvBirthday.setText("出生 " + DateUtil.ServerDate2NYRFormat(babyModel.getBirthday()));
        } else {
            tvBirthday.setText("出生 ");
        }
        tvAddress.setText("出生地 " + babyModel.getAddress());
        tvKindergarten.setText(babyModel.getSchoolName());
        if (ZSApp.getInstance().getBabyModel().isCreator()) {
            //是主联系人,可以编辑
            tvName.setEnabled(true);
            tvSex.setEnabled(true);
            tvBirthday.setEnabled(true);
            tvAddress.setEnabled(true);
            setDrawable(tvName, R.drawable.et_icon);
            setDrawable(tvSex, R.drawable.et_openicon);
            setDrawable(tvBirthday, R.drawable.et_icon);
            setDrawable(tvAddress, R.drawable.et_icon);
            tvCancelCareBaby.setVisibility(View.GONE);
            tvCancelCareBaby.setEnabled(false);
        } else {
            tvName.setEnabled(false);
            tvSex.setEnabled(false);
            tvBirthday.setEnabled(false);
            tvAddress.setEnabled(false);
            tvName.setCompoundDrawables(null, null, null, null);
            tvSex.setCompoundDrawables(null, null, null, null);
            tvBirthday.setCompoundDrawables(null, null, null, null);
            tvAddress.setCompoundDrawables(null, null, null, null);
            tvCancelCareBaby.setVisibility(View.VISIBLE);
            tvCancelCareBaby.setEnabled(true);
        }

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
            CommAlterBean commAlterBean = new CommAlterBean(id);
            commAlterBean.setOldValue(tvName.getText().toString());
            intent.putExtra(CommAlterBean.kName, commAlterBean);
        } else if (R.id.aboutmeBaby_tv_address == id) {
            CommAlterBean commAlterBean = new CommAlterBean(id);
            commAlterBean.setOldValue(tvAddress.getText().toString().replace("出生地 ", ""));
            intent.putExtra(CommAlterBean.kName, commAlterBean);
        }
        //这里要用bean里获取id
        startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }

    @OnClick(R.id.aboutmeBaby_tv_sex)
    public void onSexClick() {
        if (isShowSex) {
            oldSex = tvSex.getText().toString();
            tvSex.setText("性别");
            Drawable drawable = getActivity().getResources().getDrawable(R.drawable.et_closeicon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth() / 3 * 2, drawable.getMinimumHeight() / 3 * 2);
            tvSex.setCompoundDrawables(null, null, drawable, null);
            tvSex1.setVisibility(View.VISIBLE);
            tvSex2.setVisibility(View.VISIBLE);
        } else {
            tvSex.setText(oldSex);
            tvSex1.setVisibility(View.GONE);
            tvSex2.setVisibility(View.GONE);
            Drawable drawable = getActivity().getResources().getDrawable(R.drawable.et_openicon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth() / 3 * 2, drawable.getMinimumHeight() / 3 * 2);
            tvSex.setCompoundDrawables(null, null, drawable, null);
        }
        isShowSex = !isShowSex;
    }

    @OnClick({R.id.aboutmeBaby_tv_sex1, R.id.aboutmeBaby_tv_sex2})
    public void onSexItemClick(TextView view) {
        tvSex1.setVisibility(View.GONE);
        tvSex2.setVisibility(View.GONE);
        tvSex.setText(view.getText().toString());
        oldSex = view.getText().toString();
        Drawable drawable = getActivity().getResources().getDrawable(R.drawable.et_openicon);
        drawable.setBounds(0, 0, drawable.getMinimumWidth() / 3 * 2, drawable.getMinimumHeight() / 3 * 2);
        tvSex.setCompoundDrawables(null, null, drawable, null);
        isShowSex = true;
        // 修改
        String sexCode = "男孩".equals(view.getText().toString().trim()) ? "1" : "0";
        babyModel.setBabyGender(sexCode);
        meMvpView.babyUpdateInfo(babyModel);
    }

    @OnClick(R.id.aboutmeBaby_tv_birthday)
    public void birthdayOnClick(View view) {
        String birthday = tvBirthday.getText().toString().trim().replace("出生 ", "");
        BottomDialogUtils.getBottomDatePickerDialog(getActivity(), birthday, true, new BottomPickerDateDialog.BottomOnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth, String showDate) {
                tvBirthday.setText("出生 " + showDate);
                // 修改
                String upServerDateFormatBirthday = DateUtil.upServerDateFormat(showDate);
                babyModel.setBirthday(upServerDateFormatBirthday);
                meMvpView.babyUpdateInfo(babyModel);
            }
        });
    }

    @OnClick(R.id.aboutmeBaby_tv_cancelCareBaby)
    public void cancelCareBaby(View view) {
        //取消关注宝宝
        meMvpView.cancelCareBabyClick(String.valueOf(ZSApp.getInstance().getLoginUser().getUserId()),String.valueOf(ZSApp.getInstance().getBabyId()),true);
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
                tvAddress.setText("出生地 " + commAlterBean.getNewValue());
                // 修改
                meMvpView.babyUpdateInfo(babyModel);
            }
        }
    }
}
