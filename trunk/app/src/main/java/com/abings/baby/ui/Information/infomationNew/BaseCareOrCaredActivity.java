package com.abings.baby.ui.Information.infomationNew;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.ZSApp;
import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.Information.InfomationChild.BaseInfoPersonalMsg;
import com.hellobaby.library.Const;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.CareOrCaredModel;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.widget.ToastUtils;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

public class BaseCareOrCaredActivity extends BaseLibTitleActivity implements BaseCareOrCareMvp {

    protected RecyclerView base_care_or_cared_rv;
    protected BaseAdapter<CareOrCaredModel> modelBaseAdapter;
    protected List<CareOrCaredModel> caredModelList;

    private String title;

    @Inject
    BaseCareOrCarePresenter presenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_base_care_or_cared;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(getActivityComponent(),this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        setBtnLeftClickFinish();
        title = getIntent().getStringExtra("title");
        setTitleText(title);

        setTitleBackground(R.color.white);

        caredModelList = new ArrayList<>();

        base_care_or_cared_rv = (RecyclerView) findViewById(R.id.base_care_or_cared_rv);

        modelBaseAdapter = new BaseAdapter<CareOrCaredModel>(bContext,caredModelList,false) {
            @Override
            protected void convert(ViewHolder holder, final CareOrCaredModel data) {
                LogZS.i("每一项数据："+data.toString() );
                CircleImageView imageView = holder.getView(R.id.item_care_or_cared_headimg);
                if (data.getToType() == 1){
                    ImageLoader.loadHeadTarget(bContext, Const.URL_schoolHead + data.getHeadImageurl(),imageView);
                }else {
                    ImageLoader.loadHeadTarget(bContext, Const.URL_userHead + data.getHeadImageurl(),imageView);
                }
                TextView care_person_name = holder.getView(R.id.care_person_name);
                care_person_name.setText(data.getName());
                ImageView careOrNot = holder.getView(R.id.add_or_delete_care_iv);

                if (title.equals("关注我的人")){
                    if (data.getState() == 1){
                        careOrNot.setImageResource(R.drawable.care_delete);
                        careOrNot.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                deleteCarePerson(data.getRelationId()+"");
                            }
                        });
                    }else {
                        careOrNot.setImageResource(R.drawable.care_add);
                        careOrNot.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                addCarePerson("2",data.getToType()+"",data.getFromUserId()+"");
                            }
                        });
                    }
                }else {
                    careOrNot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            deleteCarePerson(data.getRelationId()+"");
                        }
                    });
                }

            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.item_care_or_cared;
            }
        };

        modelBaseAdapter.setOnItemClickListener(new OnItemClickListeners<CareOrCaredModel>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, CareOrCaredModel data, int position) {
                Intent intent = new Intent(bContext, BaseInfoPersonalMsg.class);
                intent.putExtra("other","true");
                if (title.equals("关注我的人")){
                    intent.putExtra("userId",data.getFromUserId()+"");
                }else {
                    intent.putExtra("userId",data.getToUserId()+"");
                }
                intent.putExtra("state",data.getToType()+"");
                startActivity(intent);
            }
        });
        base_care_or_cared_rv.setAdapter(modelBaseAdapter);
        base_care_or_cared_rv.setHasFixedSize(true);
        base_care_or_cared_rv.setItemAnimator(new DefaultItemAnimator());
        base_care_or_cared_rv.setLayoutManager(new LinearLayoutManager(bContext));

        init();
    }

    public void init(){
        if (title.equals("我关注的人")){
            presenter.getMyCaredPersons();
        }else {
            presenter.getCaredMePersons();
        }
    }

    private void deleteCarePerson(String s) {
        presenter.deleteCarePerson(s);
    }

    public void addCarePerson(String fromState,String toState,String toUserId){
        presenter.addCarePerson(fromState, toState, toUserId);
    }

    @Override
    public void showData(Object o) {
        List<CareOrCaredModel> careOrCaredModels = (List<CareOrCaredModel>) o;
        if (careOrCaredModels != null){
            caredModelList.clear();
            caredModelList.addAll(careOrCaredModels);
            modelBaseAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addCarePersonSuccess() {
        init();
        ToastUtils.showNormalToast(this,"添加关注成功。");
    }

    @Override
    public void deleteCarepersonSuccess() {
        init();
        ToastUtils.showNormalToast(this,"取消关注成功。");
    }
}
