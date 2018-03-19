package com.abings.baby.teacher.ui.main.fm.aboutme;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.ui.PrizeDraw.LuckyDrawActivity;
import com.abings.baby.teacher.ui.PrizeDraw.PrizeDrawHtmlActivity;
import com.abings.baby.teacher.ui.base.BaseFragment;
import com.abings.baby.teacher.ui.changephone.ChangePhoneActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.TeacherModel;
import com.hellobaby.library.ui.common.CommAlterActivity;
import com.hellobaby.library.ui.common.CommAlterBean;
import com.hellobaby.library.ui.crop.SinglePhotoActivity;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.utils.SharedPreferencesUtils;
import com.hellobaby.library.widget.ToastUtils;
import com.hellobaby.library.widget.crop.FileUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 老师的个人信息
 */
public class AboutMyselfFragment extends BaseFragment implements AboutMyselfMvpView{
    @BindView(R.id.aboutmeMySelf_tv_name)
    TextView tvName;
    @BindView(R.id.aboutmeMySelf_tv_position)
    TextView tvPosition;//职位
    @BindView(R.id.aboutmeMySelf_tv_email)
    TextView tvEmail;
    @BindView(R.id.aboutmeMySelf_tv_phone)
    TextView tvPhone;
    @BindView(R.id.aboutmeMySelf_civ_teahcerHead)
    CircleImageView civTeacherHead;
    public static final int ChangePhoneResultCode = 409;



    @Inject
    AboutMyselfPresenter presenter;
    public AboutMyselfFragment() {
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_aboutme_myself;
    }

    @Override
    protected void initViewsAndEvents() {
        presenter.attachView(this);
        TeacherModel teacherModel = ZSApp.getInstance().getTeacherModel();
        tvName.setText(teacherModel.getTeacherName());
        tvPosition.setText(teacherModel.getPosition());
        tvEmail.setText(teacherModel.getTeacherEmail());
        tvPhone.setText(teacherModel.getPhoneNum());
        ImageLoader.loadHeadTarget(getActivity(),ZSApp.getInstance().getTeacherModel().getHeadImageurlAbs(),civTeacherHead);
        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到新界面
                Intent intent = new Intent(getActivity(), ChangePhoneActivity.class);
                startActivityForResult(intent,ChangePhoneResultCode);
            }
        });
    }


    @Override
    public void showData(Object o) {
    }

    @OnClick(R.id.aboutmeMySelf_civ_teahcerHead)
    public void headImageClick(View view) {
        //已经选中的用户,切换头像
        Intent intent = new Intent(getContext(), SinglePhotoActivity.class);
        intent.putExtra("isCreate",true);
        intent.putExtra("bitmap", ZSApp.getInstance().getTeacherModel().getHeadImageurlAbs());//展示，显示大头像
        startActivityForResult(intent, 200);
    }

    @OnClick(R.id.aboutMe_myScore)
    public void myScoreClick(View view){
        if("1".equals(ZSApp.getInstance().getSchoolId())){
            startActivity(new Intent(getActivity(), LuckyDrawActivity.class));
        }else {
            ToastUtils.showNormalToast(getActivity(),"正在研发中...");
        }

    }

    @OnClick({R.id.aboutmeMySelf_tv_name, R.id.aboutmeMySelf_tv_email})
    public void nameClick(View view) {
        int id = view.getId();
        CommAlterBean commAlterBean = new CommAlterBean(id);
        Intent intent = new Intent(getContext(), CommAlterActivity.class);
        commAlterBean.setOldValue(((TextView) view).getText().toString());
        if(id == R.id.aboutmeMySelf_tv_email){
            commAlterBean.setInputTypeEmail();
        }else if(id==R.id.aboutmeMySelf_tv_name){
            commAlterBean.setInputLength(16);
        }
        intent.putExtra(CommAlterBean.kName, commAlterBean);
        //这里要用bean里获取id
        startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == -1) {
            //用户的登录头像更换
            String headImgPath = FileUtils.getFilePathFromUri(getContext(), data.getData());
            civTeacherHead.setImageURI(data.getData());
            presenter.tChangeHeadImgById(headImgPath);
        } else if (resultCode == 1000) {
//            //扫描结果
//            String result = data.getStringExtra(ScanActivity.kSCAN_RESULT);
//            showToast("扫描结果=" + result);
        } else if (requestCode != 0 && null != data) {
            CommAlterBean commAlterBean = (CommAlterBean) data.getSerializableExtra(CommAlterBean.kName);
            TeacherModel teacherModel=ZSApp.getInstance().getTeacherModel();
            if (resultCode == (tvName.getId() & 0x0000ffff)) {
                tvName.setText(commAlterBean.getNewValue());
                teacherModel.setTeacherName(commAlterBean.getNewValue());
            } else if (resultCode == (tvEmail.getId() & 0x0000ffff)) {
                tvEmail.setText(commAlterBean.getNewValue());
                teacherModel.setTeacherEmail(commAlterBean.getNewValue());
            }
            presenter.updateTeacherInfo(teacherModel);
        }else if(resultCode==ChangePhoneResultCode){
            showToast("请使用新手机号登录");
            logoutSuccess();
        }
    }


    @Override
    public void logoutSuccess() {
        SharedPreferencesUtils.setParam(getActivity(), Const.keyPhoneNum,"");
        SharedPreferencesUtils.setParam(getActivity(),Const.keyPwd,"");
        getActivity().finish();
        //关闭推送
        JPushInterface.stopPush(getActivity());
    }
}
