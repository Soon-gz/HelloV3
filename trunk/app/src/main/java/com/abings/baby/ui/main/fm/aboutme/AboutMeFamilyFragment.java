package com.abings.baby.ui.main.fm.aboutme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.base.BaseFragment;
import com.abings.baby.ui.changephone.ChangePhoneActivity;
import com.abings.baby.ui.main.fm.MeMvpView;
import com.abings.baby.ui.scan.ScanActivity;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.ui.common.CommAlterActivity;
import com.hellobaby.library.ui.common.CommAlterBean;
import com.hellobaby.library.ui.crop.SinglePhotoActivity;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.ToggleButton;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;
import com.hellobaby.library.widget.crop.FileUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_FIRST_USER;


@SuppressLint("ValidFragment")
public class AboutMeFamilyFragment extends BaseFragment {
    @BindView(R.id.aboutmeFamily_tv_cancelRel)
    TextView tvCancelRel;
    @BindView(R.id.aboutmeFamily_tv_name)
    TextView tvName;
    @BindView(R.id.aboutmeFamily_tv_rel)
    TextView tvRel;
    @BindView(R.id.aboutmeFamily_tv_email)
    TextView tvEmail;
    @BindView(R.id.aboutmeFamily_tv_phone)
    TextView tvPhone;
    @BindView(R.id.aboutmeFamily_rv_relation)
    RecyclerView rvRelation;
    @BindView(R.id.aboutmeFamily_civ)
    CircleImageView civHead;
    @BindView(R.id.aboutmeFamily_tb_isPick)
    ToggleButton tbIsPick;
    @BindView(R.id.aboutmeFamily_ll_isPick)
    LinearLayout llIsPick;
    @BindView(R.id.aboutmeFamily_tv_pick)
    TextView tvPick;
    private RelationRVAdapter relationAdapter;
    private float item_selected_alpha;
    private float item_unSelected_alpha;
    /**
     * 登录用户的信息
     */
    private UserModel loginUserModel;

    private MeMvpView meMvpView;
    public static final int ChangePhoneResultCode = 409;
    public AboutMeFamilyFragment(){}
    public AboutMeFamilyFragment(MeMvpView meMvpView) {
        this.meMvpView = meMvpView;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_aboutme_family;
    }


    @Override
    protected void initViewsAndEvents() {

        item_selected_alpha = getContext().getResources().getFraction(R.fraction.relationHead_selected_alpha, 1, 1);
        item_unSelected_alpha = getContext().getResources().getFraction(R.fraction.relationHead_unselected_alpha, 1, 1);
        loginUserModel = ZSApp.getInstance().getLoginUser();

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvRelation.setLayoutManager(linearLayoutManager);
        //设置适配器
        relationAdapter = new RelationRVAdapter(getContext(),new ArrayList<UserModel>(),false);
        rvRelation.setAdapter(relationAdapter);

        //关系人头像
        relationAdapter.setOnItemClickListener(new OnItemClickListeners<UserModel>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, UserModel data, int position) {
                //切换个人信息
                setRightPenDismiss();
                if (data.isSelected()) {
                    //是否已经为选中

                } else {
                    int size = relationAdapter.getDatas().size();
                    for (int i = 0; i < size; i++) {
                        loginUserModel.setSelected(false);
                        civHead.setAlpha(item_unSelected_alpha);
                        if (i == position) {
                            relationAdapter.getDatas().get(i).setSelected(true);
                        } else {
                            relationAdapter.getDatas().get(i).setSelected(false);
                        }
                    }
                    setCancelRel(false);
                    setUserInfo(data,true);
                    relationAdapter.notifyDataSetChanged();
                }
            }
        });

        //头像
        civHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginUserModel.isSelected()) {
                    //已经选中的用户
                    //切换头像
                    Intent intent = new Intent(getContext(), SinglePhotoActivity.class);
                    intent.putExtra("bitmap" ,ZSApp.getInstance().getLoginUser().getHeadImageurlAbs());//大图展示，为大图
                    intent.putExtra("isCreate" ,true);
                    startActivityForResult(intent, 200);
                } else {
                    setLoginUserInfo();
                }
            }
        });
        meMvpView.getRelationsClick();
        setLoginUserInfo();
        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到新界面
                Intent intent = new Intent(getActivity(), ChangePhoneActivity.class);
                startActivityForResult(intent,ChangePhoneResultCode);
            }
        });
    }

    /**
     * 显示登陆者的info
     */
    private void setLoginUserInfo(){
        //其它用户已经选中
        setRightPenShow();
        loginUserModel.setSelected(true);
        civHead.setAlpha(item_selected_alpha);

        int size = relationAdapter.getDatas().size();
        for (int i = 0; i < size; i++) {
            relationAdapter.getDatas().get(i).setSelected(false);
        }
        relationAdapter.notifyDataSetChanged();
        setUserInfo(loginUserModel,false);
        setCancelRel(true);
        ImageLoader.loadHeadTarget(getActivity(), loginUserModel.getHeadImageurlAbs(), civHead);
    }

    private void setUserInfo(final UserModel userInfo, boolean isFromRelation) {
        tvName.setText(userInfo.getUserName());
        tvEmail.setText(userInfo.getUserEmail());
        tvPhone.setText(userInfo.getPhoneNum());
        //这里为修改了宝宝关系人
        if(isFromRelation){
            tvPhone.setEnabled(false);
            tvRel.setText(userInfo.getRelation());
            tvPick.setText("是否允许"+userInfo.getUserName()+"接送");
            llIsPick.setVisibility(View.VISIBLE);
            if(userInfo.isPick()){
                tbIsPick.setToggleOn();
            }else{
                tbIsPick.setToggleOff();
            }
            tbIsPick.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
                @Override
                public void onToggle(boolean on) {
                    meMvpView.updatePickChange(userInfo,ZSApp.getInstance().getBabyModel(),on);
                }
            });
        }else {
            tvRel.setText(ZSApp.getInstance().getBabyModel().getRelation());
            tvPhone.setEnabled(true);
            llIsPick.setVisibility(View.GONE);
        }
    }

    @Override
    public void showData(Object o) {

    }


    @OnClick({R.id.aboutmeFamily_tv_name, R.id.aboutmeFamily_tv_rel, R.id.aboutmeFamily_tv_email})
    public void nameClick(View view) {
        int id = view.getId();
        boolean isLoginUser = loginUserModel.isSelected();
        if (id == R.id.aboutmeFamily_tv_rel) {
            BottomDialogUtils.getBottomRelDialog(getActivity(), new BottomDialogUtils.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                    tvRel.setText(item);

                    if(loginUserModel.isSelected()){
                        //当前用户
                        loginUserModel.setRelation(item);
                        meMvpView.userUpdateInfoClick(loginUserModel);
                        for (int j = 0; j < ZSApp.getInstance().getListBaby().size(); j++) {
                            //改变当前用户的baby的关系
                            if(ZSApp.getInstance().getListBaby().get(j).getBabyId()==ZSApp.getInstance().getBabyModel().getBabyId()){
                                ZSApp.getInstance().getListBaby().get(j).setRelation(item);
                            }
                        }
                    }else{
                        for (int i = 0; i < relationAdapter.getDatas().size(); i++) {
                            if(relationAdapter.getDatas().get(i).isSelected()){
                                relationAdapter.getDatas().get(i).setRelation(item);
                                meMvpView.userUpdateInfoClick(relationAdapter.getDatas().get(i));
                            }
                        }
                    }
                }
            });
        } else if (isLoginUser) {
            //不是登录用户只能改关系
            CommAlterBean commAlterBean = new CommAlterBean(id);
            Intent intent = new Intent(getContext(), CommAlterActivity.class);
            commAlterBean.setOldValue(((TextView) view).getText().toString());
            if(id == R.id.aboutmeFamily_tv_name){
                commAlterBean.setInputLength(16);
            }else if(id == R.id.aboutmeFamily_tv_email){
                commAlterBean.setInputTypeEmail();
            }
            intent.putExtra(CommAlterBean.kName, commAlterBean);
            //这里要用bean里获取id
            startActivityForResult(intent, RESULT_FIRST_USER);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == -1) {
            //用户的登录头像更换
            String headImgPath = FileUtils.getFilePathFromUri(getContext(), data.getData());
            civHead.setImageURI(data.getData());
            meMvpView.userUploadHeadImgClick(headImgPath);
        } else if (resultCode == ScanActivity.kSCAN_RESULT_CODE) {
            //扫描结果
            String result = data.getStringExtra(ScanActivity.kSCAN_RESULT);
            showToast("扫描结果=" + result);
        } else if (requestCode != 0 && null != data) {
            CommAlterBean commAlterBean = (CommAlterBean) data.getSerializableExtra(CommAlterBean.kName);
            if (resultCode == (tvName.getId() & 0x0000ffff)) {
                tvName.setText(commAlterBean.getNewValue());
                loginUserModel.setUserName(commAlterBean.getNewValue());
                meMvpView.userUpdateInfoClick(loginUserModel);
            } else if (resultCode == (tvEmail.getId() & 0x0000ffff)) {
                tvEmail.setText(commAlterBean.getNewValue());
                loginUserModel.setUserEmail(commAlterBean.getNewValue());
                meMvpView.userUpdateInfoClick(loginUserModel);
            }
        }else if(resultCode==ChangePhoneResultCode){
            showToast("请使用新手机号登录");
            meMvpView.logoutSuccess();
        }
    }

    public void setRelations(List<UserModel> list) {
        if (relationAdapter != null) {
            relationAdapter.setNewData(list);
        }
        setLoginUserInfo();
        tvRel.setText(ZSApp.getInstance().getBabyModel().getRelation());
    }


    //显示
    private void setRightPenShow() {
        setRightDrawable(getContext(), tvName, R.drawable.et_icon);
        setRightDrawable(getContext(), tvEmail, R.drawable.et_icon);
        setRightDrawable(getContext(), tvPhone, R.drawable.et_icon);
    }

    //隐藏
    private void setRightPenDismiss() {
        tvName.setCompoundDrawables(null, null, null, null);
        tvEmail.setCompoundDrawables(null, null, null, null);
        tvPhone.setCompoundDrawables(null, null, null, null);
    }

    private void setRightDrawable(Context context, TextView tv, int drawableRes) {
        Drawable drawable = context.getResources().getDrawable(drawableRes);
        int width = (int) getActivity().getResources().getDimension(R.dimen.textview_icon_size);
        int height = (int) getActivity().getResources().getDimension(R.dimen.textview_icon_size);
        drawable.setBounds(0, 0, width, height);
        tv.setCompoundDrawables(null, null, drawable, null);
    }


    @OnClick(R.id.aboutmeFamily_tv_cancelRel)
    public void cancelRelClick(View view) {
        List<UserModel> list = relationAdapter.getDatas();
        for (UserModel userModel : list) {
            if (userModel.isSelected()) {
                meMvpView.cancelCareBabyClick(String.valueOf(userModel.getUserId()), ZSApp.getInstance().getBabyId(), false);
                break;
            }
        }
    }

    /**
     * 刷新关系
     */
    public void refreshRelations() {
        List<UserModel> list = relationAdapter.getDatas();
        int removePosition = -1;//删除已经选中的人物
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).isSelected()){
                removePosition = i;
                break;
            }
        }
        if(removePosition>=0){
            relationAdapter.getDatas().remove(removePosition);
            relationAdapter.notifyItemRemoved(removePosition);
        }
        setLoginUserInfo();
    }

    private void setCancelRel(boolean isLoginUser){
        if(isLoginUser){
            tvCancelRel.setEnabled(false);
            tvCancelRel.setVisibility(View.GONE);
        }else{
            tvCancelRel.setEnabled(true);
            tvCancelRel.setVisibility(View.VISIBLE);
        }
    }
}
