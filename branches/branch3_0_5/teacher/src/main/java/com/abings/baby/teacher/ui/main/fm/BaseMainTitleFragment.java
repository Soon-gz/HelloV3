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
import com.hellobaby.library.data.model.SchoolModel;
import com.hellobaby.library.utils.ImageLoader;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

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

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_maintitle;
    }

    @Override
    protected void initViewsAndEvents() {
        View contentView = View.inflate(getContext(), getContentViewID(), null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams(layoutParams);
        flContent.addView(contentView);

        {
            SchoolModel schoolModel = ZSApp.getInstance().getSchoolModel();
            tvSchoolName.setText(schoolModel.getSchoolName());
            ImageLoader.load(getActivity(),schoolModel.getHeadImageurlAbs(),civSchoolHeader);
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
                Intent intent = new Intent(getContext(), MsgActivity.class);
                startActivity(intent);
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

}
