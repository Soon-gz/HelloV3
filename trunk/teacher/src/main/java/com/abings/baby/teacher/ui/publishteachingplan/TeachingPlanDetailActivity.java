package com.abings.baby.teacher.ui.publishteachingplan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.data.injection.component.ActivityComponent;
import com.hellobaby.library.data.model.TeachingPlanModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.BottomDialogUtils;

import java.util.List;

import javax.inject.Inject;

import uk.co.senab.photoview.PhotoView;

/**
 * 教学计划列表
 */
public class TeachingPlanDetailActivity extends BaseLibActivity implements TeachingPlanMvpView {
    @Inject
    TeachingPlanPresenter presenter;
    TeachingPlanModel teachingPlanModel = new TeachingPlanModel();

    private TextView tvTitle;
    private ImageView ivLeft;
    private ImageView ivRight;
    private RelativeLayout rlTitle;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.teachingplandetailactivity;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public ActivityComponent getActivityComponent() {
        return DaggerUtils.getActivityComponent(bActivityComponent, this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        teachingPlanModel = (TeachingPlanModel) getIntent().getSerializableExtra("tp_model");
        PhotoView imageView = (PhotoView) findViewById(R.id.tp_im);

        tvTitle = (TextView) findViewById(R.id.tpTitle_tv_title);
        rlTitle = (RelativeLayout) findViewById(R.id.tpTitle_RL);
        ivLeft = (ImageView) findViewById(R.id.tpTitle_iv_left);
        ivRight = (ImageView) findViewById(R.id.tpTitle_iv_right);

        tvTitle.setText("教学计划");
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageLoader.loadImg1080(this, teachingPlanModel.getImageurlAbs(), imageView);
        final String items[] = {"删除教学计划", "取消"};
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                        if (position == 0) {
                            presenter.deleteTeachingplanByTeacherId(teachingPlanModel.getTeachingplanId() + "");
                            finish();
                        }
                    }
                });
            }
        });
        TextView tvTime = (TextView) findViewById(R.id.lib_bottom_tv_time);
        TextView tvUserName = (TextView) findViewById(R.id.lib_bottom_tv_userName);
        tvTime.setText(DateUtil.timestamp2NYRFormat(teachingPlanModel.getPlanningTime()) + "-" + DateUtil.timestamp2NYRFormat(teachingPlanModel.getPlanningEndtime()));
        tvUserName.setText(teachingPlanModel.getTeacherName());
    }

    @Override
    public void showTeachingPlanList(List list) {

    }

    @Override
    public void publishPlanSuccess() {

    }

    @Override
    public void publishDeleteSuccess(int posion) {

    }

    @Override
    public void showData(Object o) {

    }
}
