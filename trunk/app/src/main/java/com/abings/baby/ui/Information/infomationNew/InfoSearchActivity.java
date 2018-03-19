package com.abings.baby.ui.Information.infomationNew;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.abings.baby.data.injection.DaggerUtils;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.InfoChildHeartModel;
import com.hellobaby.library.data.model.InfomationModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.utils.KeyboardChangeListener;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.widget.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class InfoSearchActivity extends BaseLibActivity implements InfoSearchMvp, KeyboardChangeListener.KeyBoardListener {

    protected RecyclerView info_search_rv;
    protected BaseInfoNewsAdapter baseAdapter;
    protected List<InfomationModel> infomationModelList;
    protected ImageView info_libTitle_iv_left;

    private boolean isInputShow = false;

    @BindView(R.id.info_searchcontent_et)
    EditText info_searchcontent_et;

    @Inject
    InfoSearchPresenter presenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_info_search;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(getActivityComponent(),this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);

        info_search_rv = (RecyclerView) findViewById(R.id.info_search_rv);
        infomationModelList = new ArrayList<>();
        info_libTitle_iv_left = (ImageView) findViewById(R.id.info_libTitle_iv_left);

        baseAdapter = new BaseInfoNewsAdapter(this, infomationModelList, false) {
            @Override
            protected void disLikeInfoMsg(InfomationModel data) {
                if (data.getState() == 1){
                    presenter.addLikeInfo(data.getState()+"",data.getInfoId()+"");
                }else {
                    presenter.addLikeInfo(data.getState()+"",data.getSubAlbumId()+"");
                }
            }

            @Override
            protected void addLikeInfoMsg(InfomationModel data) {
                if (data.getState() == 1){
                    presenter.addLikeInfo(data.getState()+"",data.getInfoId()+"");
                }else {
                    presenter.addLikeInfo(data.getState()+"",data.getSubAlbumId()+"");
                }
            }
        };

        info_search_rv.setAdapter(baseAdapter);
        info_search_rv.setLayoutManager(new LinearLayoutManager(bContext));
        info_search_rv.setItemAnimator(new DefaultItemAnimator());
        info_search_rv.setHasFixedSize(true);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        initClick();
    }

    @Subscribe
    public void onEventMainThread(InfoChildHeartModel event) {
        if (event.isLikeClick()){
            infomationModelList.get(event.getPosition()).setIsLike(event.getIsLike());
            infomationModelList.get(event.getPosition()).setLikeNum(event.getLikeNum()+"");
        }else {
            infomationModelList.get(event.getPosition()).setCommentNum(event.getCommentNum());
        }
        baseAdapter.notifyDataSetChanged();
    }

    private void initClick() {
        info_libTitle_iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showNormalToast(bContext,msg);
        infomationModelList.clear();
        baseAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.info_searchtitle_iv_right)
    public void click(View view){
        String condition = info_searchcontent_et.getText().toString().trim();
        presenter.searchInfoMsg(condition);
    }

    @Override
    public void showData(Object o) {
        if (isInputShow){
            changeInputState();
        }
        List<InfomationModel> models = (List<InfomationModel>) o;
        infomationModelList.clear();
        if (models != null && models.size() > 0){
            infomationModelList.addAll(models);
        }
        baseAdapter.notifyDataSetChanged();
    }

    @Override
    public void addLikeSuccess(String s) {
        ToastUtils.showNormalToast(bContext,s);
    }

    @Override
    public void onKeyboardChange(boolean isShow, int keyboardHeight) {
        isInputShow = isShow;
    }

    public void changeInputState(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
