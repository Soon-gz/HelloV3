package com.abings.baby.ui.main.fm;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.base.BaseFragment;
import com.abings.baby.widget.custom.zstitlebar.IZSMainTitleBarListener;
import com.abings.baby.widget.custom.zstitlebar.ZSMainTitleBar;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.utils.LogZS;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zwj on 2016/12/23.
 * description : baby&school的标题部分
 */

public abstract class BaseMainTitleFragment extends BaseFragment {
    protected FrameLayout flContent;


    protected ZSMainTitleBar zsMainTitleBar;
    protected boolean isChangeBaby = false;
    @Override
    protected void initViewsAndEvents() {
        isChangeBaby = false;
        flContent = (FrameLayout) mContentView.findViewById(R.id.mainTitle_content);
        zsMainTitleBar = (ZSMainTitleBar) mContentView.findViewById(R.id.zsMainTitleBar);
        zsMainTitleBar.setFlContent(flContent);
        //初始化其他宝宝
        BabyModel babyModel = ZSApp.getInstance().getBabyModel();
        List<BabyModel> list = new ArrayList<>();
        list.addAll(ZSApp.getInstance().getOtherListBaby());
        if (babyModel == null) {
            LogZS.e("服务器接收到的宝宝model为null");
            babyModel = new BabyModel();
        }
        zsMainTitleBar.setGaussLayout(getActivity().findViewById(R.id.activity_main));
        zsMainTitleBar.setCurrentBaby(babyModel);
        if (list != null && list.size() > 0) {
            //多个宝宝的时候
            zsMainTitleBar.setOtherBabys(list);
            zsMainTitleBar.setOpenEnable(true);
        } else {
            zsMainTitleBar.setOpenEnable(false);
        }
        zsMainTitleBar.setListener(new IZSMainTitleBarListener() {
            @Override
            public void clickOtherBaby(BabyModel babyBean) {
                isChangeBaby = true;
                zsMainTitleBar.setCurrentBaby(babyBean);
                ZSApp.getInstance().setBabyModel(babyBean);
                changeBaby();
            }
        });

        View contentView = View.inflate(getContext(), getContentViewID(), null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams(layoutParams);
        flContent.addView(contentView);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_maintitle;
    }

    /**
     * 底部布局的内容id
     *
     * @return
     */
    protected abstract int getContentViewID();

    /**
     * 切换宝宝的详情
     */
    protected abstract void changeBaby();

}
