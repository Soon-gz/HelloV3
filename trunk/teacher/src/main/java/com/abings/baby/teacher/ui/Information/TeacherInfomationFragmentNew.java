package com.abings.baby.teacher.ui.Information;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.teacher.ZSApp;
import com.flyco.tablayout.CommonTabLayout;
import com.hellobaby.library.Const;
import com.hellobaby.library.R;
import com.hellobaby.library.ui.base.BaseLibFragment;
import com.hellobaby.library.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherInfomationFragmentNew extends BaseLibFragment implements View.OnClickListener {

    protected ViewPager news_viewpager;
    protected ImageView news_search;
    protected CommonTabLayout news_iv_center;
    protected CircleImageView news_image_head;
    protected TextView news_image_name;
    protected ImageView news_hint;
//    public String[] mTitles = {"关注", "发现"};
//    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_base_infomation_fragment_new;
    }

    @Override
    protected void initViewsAndEvents() {
        initViews();
    }

    private void initViews() {
        news_viewpager = (ViewPager) mContentView.findViewById(R.id.news_viewpager);
        news_search = (ImageView) mContentView.findViewById(R.id.news_search);
        news_hint = (ImageView) mContentView.findViewById(R.id.news_hint);
        news_hint.setVisibility(View.GONE);
        news_image_head = (CircleImageView) mContentView.findViewById(R.id.news_image_head);
        news_iv_center = (CommonTabLayout) mContentView.findViewById(R.id.news_iv_center);
        news_image_name=(TextView)mContentView.findViewById(R.id.news_image_name);
//        news_image_head.setVisibility(View.GONE);
        ImageLoader.loadHeadTarget(getContext(), Const.URL_schoolHead+ ZSApp.getInstance().getSchoolModel().getHeadImageurl(),news_image_head);
        news_image_name.setText(ZSApp.getInstance().getSchoolModel().getSchoolName());
        news_iv_center.setVisibility(View.GONE);
        news_search.setOnClickListener(this);
        final List<TeacherInfomationChildFg> fragments = new ArrayList<>();
        //关注
        TeacherInfomationChildFg childFg = new TeacherInfomationChildFg();
        Bundle bundle = new Bundle();
        bundle.putString("type","0");
        childFg.setArguments(bundle);

        fragments.add(childFg);
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public TeacherInfomationChildFg getItem(int position) {
                return fragments.get(position);
            }
        };

        news_viewpager.setAdapter(adapter);
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.news_search) {
            startActivity(new Intent(getActivity(),InfoSearchActivity.class));
        }else if (i == R.id.news_image_head){
//            startActivity(new Intent(getActivity(), BaseInfoPersonalMsg.class).putExtra("other","false"));
        }
    }
}
