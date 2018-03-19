package com.abings.baby.ui.Information.InfomationChild;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.Information.infomationNew.BaseInfoDetailActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.BaseInfoHintModel;
import com.hellobaby.library.data.model.BaseInfoHintOldModel;
import com.hellobaby.library.data.model.InfomationModel;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.OnLoadMoreListener;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class BaseInfoHintActivity extends BaseLibTitleActivity implements BaseInfoHintMvpView {

    @BindView(R.id.info_hint_info_rv)
    RecyclerView recyclerView;
    @BindView(R.id.info_hint_swipeRefresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.info_hint_more)
    TextView info_hint_more;
    @BindView(R.id.ingo_hint_more_line)
    View ingo_hint_more_line;

    private BaseAdapter<BaseInfoHintModel> baseAdapter;
    private List<BaseInfoHintModel> baseHintModels;
    private int pageNum = 1;
    private BaseInfoHintOldModel baseInfoHintOldModel;
    private boolean isNeedClear = false;

    @Inject
    BaseInfoHintPresenter presenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_base_info_hint;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(getActivityComponent(),this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        setBtnLeftClickFinish();
        setTitleText("消息");
        setTitleBackground(R.color.white);

        baseHintModels = new ArrayList<>();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                isNeedClear = true;
                info_hint_more.setVisibility(View.GONE);
                baseAdapter.setLoadingView(R.layout.footer_more);
                ingo_hint_more_line.setVisibility(View.GONE);
                presenter.loadOldreadedMsgList(pageNum+"");
            }
        });

        baseAdapter = new BaseAdapter<BaseInfoHintModel>(this,baseHintModels,true) {
            @Override
            protected void convert(ViewHolder holder, BaseInfoHintModel data) {
                CircleImageView head_img = holder.getView(R.id.item_info_hint_head_img);
                String headImgUrl;

                TextView userName = holder.getView(R.id.item_info_hint_userName);
                userName.setText(data.getName());

                TextView hint_content = holder.getView(R.id.item_info_hint_content);
                TextView content_toName = holder.getView(R.id.item_info_hint_content_toName);
                TextView hint_content_ts = holder.getView(R.id.item_info_hint_content_ts);
                if (!isEmpty(data.getToReplyUtype()) && !isEmpty(data.getToReplyUid())){
                    hint_content_ts.setVisibility(View.VISIBLE);
                    content_toName.setVisibility(View.VISIBLE);
                    content_toName.setText(data.getToName());
                    hint_content.setText(": "+data.getCommentContent());
                }else {
                    content_toName.setVisibility(View.GONE);
                    hint_content_ts.setVisibility(View.GONE);
                    hint_content.setText(data.getCommentContent());
                }

                TextView item_info_time = holder.getView(R.id.item_info_time);
                item_info_time.setText(DateUtil.getDescriptionTimeFromTimestampInSchool(data.getCreateTime()));

                ImageView video_play_iv = holder.getView(R.id.video_play_iv);

                ImageView hint_conImg = holder.getView(R.id.item_info_hint_conImg);
                String imageUrl = "";
                switch (data.getType()){
                    case 1:
                        imageUrl = data.getCoverImageUrl();
                        headImgUrl = Const.URL_userHead + data.getHeadImageurl();
                        video_play_iv.setVisibility(View.GONE);
                        break;
                    case 2:
                        imageUrl = Const.URL_Album+data.getCoverImageUrl();
                        headImgUrl = Const.URL_userHead + data.getHeadImageurl();
                        video_play_iv.setVisibility(View.GONE);
                        break;
                    case 3:
                        imageUrl = Const.URL_VideoFirstFrame+data.getCoverImageUrl();
                        headImgUrl = Const.URL_userHead + data.getHeadImageurl();
                        video_play_iv.setVisibility(View.VISIBLE);
                        break;
                    default:
                        headImgUrl = Const.URL_userHead + data.getHeadImageurl();
                        video_play_iv.setVisibility(View.GONE);
                        break;
                }

                ImageLoader.loadHeadTarget(bContext, headImgUrl,head_img);
                ImageLoader.load(bContext,imageUrl,hint_conImg);
            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.item_info_hint;
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        baseAdapter.setLoadingView(com.hellobaby.library.R.layout.footer_more);
        baseAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (baseInfoHintOldModel != null && baseInfoHintOldModel.getPage().getPages() != baseInfoHintOldModel.getPage().getPageNum()){
                    pageNum++;
                    info_hint_more.setVisibility(View.GONE);
                    ingo_hint_more_line.setVisibility(View.GONE);
                    presenter.loadOldreadedMsgList(pageNum+"");
                    refreshLayout.setRefreshing(true);
                }
            }
        });
        baseAdapter.setOnItemClickListener(new OnItemClickListeners<BaseInfoHintModel>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, BaseInfoHintModel data, int position) {
                InfomationModel infomationModel = new InfomationModel();
                infomationModel.setInfoId(data.getTopicId());
                infomationModel.setSubAlbumId(data.getTopicId());
                if (data.getType() == 1){
                    infomationModel.setState(1);
                }else {
                    infomationModel.setState(2);
                }
                startActivity(new Intent(bContext, BaseInfoDetailActivity.class).putExtra("InfomationModel",infomationModel));
            }
        });

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(baseAdapter);


        if (ZSApp.getInstance().gettAlertBooleanModel().getInfomsg() == 0){
            presenter.loadUnreadMsgList();
        }else {
            info_hint_more.setVisibility(View.GONE);
            ingo_hint_more_line.setVisibility(View.GONE);
            presenter.loadOldreadedMsgList(pageNum+"");
        }

    }

    private boolean isEmpty(String s){
        if (s == null){
            return true;
        }
        if (s.equals("")){
            return true;
        }
        return false;
    }

    @Override
    public void showData(Object o) {

    }

    @OnClick(R.id.info_hint_more)
    public void onItemClick(View view){
        pageNum = 1;
        isNeedClear = true;
        baseAdapter.setLoadingView(R.layout.footer_more);
        info_hint_more.setVisibility(View.GONE);
        ingo_hint_more_line.setVisibility(View.GONE);
        presenter.loadOldreadedMsgList(pageNum+"");
    }

    @Override
    public void getUnreadMsgList(List<BaseInfoHintModel> hintModels) {
        if (hintModels.size() > 0){
            baseHintModels.addAll(hintModels);
            baseAdapter.setLoadingView(R.layout.footer_loadend);
        }
        baseAdapter.notifyDataSetChanged();
    }

    @Override
    public void getOldreadedMsgList(BaseInfoHintOldModel hintModels) {
        if (refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        if (isNeedClear){
            isNeedClear = false;
            baseHintModels.clear();
        }
        baseInfoHintOldModel = hintModels;
        if (hintModels != null && hintModels.getCommList() != null){
            if (hintModels.getCommList().size() > 0){
                baseHintModels.addAll(hintModels.getCommList());
                if (baseInfoHintOldModel.getPage().getPageNum() == baseInfoHintOldModel.getPage().getPages()){
                    baseAdapter.setLoadingView(R.layout.footer_loadend);
                }
            }
            baseAdapter.notifyDataSetChanged();
        }
    }
}
