package com.abings.baby.teacher.ui.main.fm;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.ui.base.BaseFragment;
import com.abings.baby.teacher.ui.msg.MsgActivity;
import com.abings.baby.teacher.ui.scan.ScanActivity;
import com.abings.baby.teacher.ui.search.SearchActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.SchoolModel;
import com.hellobaby.library.data.model.TeacherAlertBooleanModel;
import com.hellobaby.library.utils.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by zwj on 2016/12/23.
 * description : teacher和school的基类，因为他们有共同的title
 */

public abstract class BaseMainTitleFragment extends BaseFragment {
    @BindView(R.id.mainTitle_civ_schoolHeader)
    CircleImageView civSchoolHeader;
    @BindView(R.id.mainTitle_tv_schoolName)
    TextView tvSchoolName;
    @BindView(R.id.mainTitle_iv_scan)
    ImageView ivScan;
    @BindView(R.id.mainTitle_iv_message)
    ImageView ivMessage;
    @BindView(R.id.mainTitle_iv_search)
    ImageView ivSearch;
    @BindView(R.id.mainTitle_content)
    FrameLayout flContent;
    @BindView(R.id.mainTitle_iv_select_school)
    ImageView selectSchool;
    public Badge messageBadgeView;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_maintitle;
    }

    @Override
    protected void initViewsAndEvents() {
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if(ZSApp.getInstance().getTeacherAlertBooleanModel()!=null&&ZSApp.getInstance().getTeacherAlertBooleanModel().getMsg()==0){
            messageBadgeView = new QBadgeView(getActivity()).bindTarget(ivMessage).setGravityOffset(0f, 1f, true).setBadgeNumber(-1).setShowShadow(false);
        }else {
            messageBadgeView = new QBadgeView(getActivity()).bindTarget(ivMessage).setGravityOffset(0f, 1f, true).setBadgeNumber(0).setShowShadow(false);
        }
        View contentView = View.inflate(getContext(), getContentViewID(), null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams(layoutParams);
        flContent.addView(contentView);

        {
            SchoolModel schoolModel = ZSApp.getInstance().getSchoolModel();
            tvSchoolName.setText(schoolModel.getSchoolName());
            ImageLoader.loadHeadTarget(getActivity(),schoolModel.getHeadImageurlAbs(),civSchoolHeader);
        }
        ivScan.setVisibility(View.GONE);
        ivScan.setEnabled(false);
        ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //扫描界面
                startActivity(new Intent(getContext(), ScanActivity.class));
            }
        });
        ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入消息中心
//                setMsgBadgeViewShow(false);
                Intent intent = new Intent(getContext(), MsgActivity.class);
                startActivityForResult(intent, Const.NORMAL_ACTIVITY_RESULT);
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 底部布局的内容id
     * @return
     */
    protected abstract int getContentViewID();

    public void setMsgBadgeViewShow(boolean isMain) {
        if(isMain){
            messageBadgeView.setBadgeNumber(-1);
        }else {
            messageBadgeView.setBadgeNumber(0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    @Subscribe
    public void onEvent(TeacherAlertBooleanModel tAlertBooleanModel){
        if(tAlertBooleanModel.getMsg()==0){
            setMsgBadgeViewShow(true);
        }else{
            setMsgBadgeViewShow(false);
        }
    }

}
