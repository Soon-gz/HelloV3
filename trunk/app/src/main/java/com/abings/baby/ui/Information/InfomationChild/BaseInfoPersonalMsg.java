package com.abings.baby.ui.Information.InfomationChild;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abings.baby.ZSApp;
import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.Information.infomationNew.BaseCareOrCaredActivity;
import com.abings.baby.ui.Information.infomationNew.BaseInfoDetailActivity;
import com.abings.baby.ui.publishvideo.VideoPlayActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.BaseInfoDetailEventModel;
import com.hellobaby.library.data.model.InfoChildHeartModel;
import com.hellobaby.library.data.model.InfoPersonMsgModel;
import com.hellobaby.library.data.model.InfomationModel;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;
import com.hellobaby.library.ui.slide.SlideActivity;
import com.hellobaby.library.ui.slide.SlideBean;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.widget.ToastUtils;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.OnLoadMoreListener;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;


public class BaseInfoPersonalMsg extends BaseLibTitleActivity implements BaseInfoPersonMvp{

    protected RecyclerView personal_info_rv;
    protected BaseAdapter<InfoPersonMsgModel.ListBean> baseAdapter;
    protected List<InfoPersonMsgModel.ListBean> detailModelList;
    protected InfoPersonMsgModel infoPersonMsgModel;
    private ImageView imageView;
    private InfoPersonMsgModel.ListBean headBean = new InfoPersonMsgModel.ListBean();

    private boolean isCanClick = true;
    private String isOther;
    private  String state;

    private int pageNum = 1;
    private boolean isLastPage = false;
    private boolean needClearData = false;
    private boolean isHasSetAdapter = false;

    private String userId;

    @BindView(R.id.lib_baseinfomation_school_swipeRefresh)
    SwipeRefreshLayout lib_baseinfomation_school_swipeRefresh;

    @Inject
    BaseInfoPersonPresenter presenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_base_info_personal_msg;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(getActivityComponent(),this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        setTitleText("");
        setTitleBackground(R.color.white);
        setBtnLeftClickFinish();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }


        //other 是否从主页头像跳转  true为是  false 为否
        isOther = getIntent().getStringExtra("other");
        // 1 表示校园发布  2 表示家长发布
        state = getIntent().getStringExtra("state");

        personal_info_rv = (RecyclerView) findViewById(R.id.personal_info_rv);
        detailModelList = new ArrayList<>();

        baseAdapter = new BaseAdapter<InfoPersonMsgModel.ListBean>(bContext,detailModelList,true) {


            @Override
            protected void convert(ViewHolder holder, InfoPersonMsgModel.ListBean data) {}

            @Override
            protected void convert(ViewHolder holder, final InfoPersonMsgModel.ListBean data, final int mPosition) {
                FrameLayout frameLayout = holder.getView(R.id.item_info_show_fl);

                //点赞数量
                final TextView like_number = holder.getView(R.id.person_like_number);
                //评论数量
                TextView commension_number = holder.getView(R.id.person_commension_number);
                //发布日期
                TextView news_info_date = holder.getView(R.id.person_info_date);
                //内容
                TextView news_content = holder.getView(R.id.person_news_content);
                //标题
                TextView news_title = holder.getView(R.id.person_news_title);

                ImageView commension_img = holder.getView(R.id.person_commension_img);

                commension_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(bContext,BaseInfoDetailActivity.class);
                        InfomationModel infomationModel = initInformationModel(data);
                        intent.putExtra("InfomationModel",infomationModel);
                        intent.putExtra("position",mPosition);
                        startActivity(intent);
                    }
                });

                RelativeLayout item_person_ll = holder.getView(R.id.item_person_ll);
                item_person_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

                news_info_date.setText(DateUtil.getFormatTimeFromTimestamp(data.getCreateTime(),"yyyy年MM月dd日 HH:mm"));
                commension_number.setText(data.getCommentNum()+"");
                like_number.setText(data.getLikeNum()+"");


                if (isEmpty(data.getContent())){
                    news_content.setVisibility(View.GONE);
                }else {
                    news_content.setVisibility(View.VISIBLE);
                    news_content.setText(data.getContent());
                }

                if (isEmpty(data.getTitle())){
                    news_title.setVisibility(View.GONE);
                }else {
                    news_title.setVisibility(View.VISIBLE);
                    news_title.setText(data.getTitle());
                }

                final CheckBox heart_like_checkbox = holder.getView(R.id.person_heart_like_checkbox);
                if (data.getIsLike() > 0){
                    heart_like_checkbox.setChecked(true);
                }else {
                    heart_like_checkbox.setChecked(false);
                }

                heart_like_checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String number = like_number.getText().toString();
                        int numberInt = Integer.parseInt(number);
                        if (heart_like_checkbox.isChecked()){
                            numberInt++;
                            if (data.getState() == 1){
                                presenter.addLikeInfo(data.getState()+"",data.getInfoId()+"");
                            }else {
                                presenter.addLikeInfo(data.getState()+"",data.getSubAlbumId()+"");
                            }
                            data.setIsLike(1);
                        }else {
                            numberInt--;
                            data.setIsLike(0);
                            if (data.getState() == 1){
                                presenter.addLikeInfo(data.getState()+"",data.getInfoId()+"");
                            }else {
                                presenter.addLikeInfo(data.getState()+"",data.getSubAlbumId()+"");
                            }
                        }
                        data.setLikeNum(numberInt);
                        like_number.setText(numberInt+"");
                    }
                });


                //家长发布的
                if (data.getState() == 2){
                    switch (data.getType()){
                        case 2:
                            initPictures(holder,data,frameLayout);
                            break;
                        case 3:
                            initVideo(holder,data,frameLayout);
                            break;
                        default:
                            showFrameLayoutChild(2,frameLayout);
                            break;
                    }
                    //校园发的
                }else {
                    initPicturesSchool(holder,data,frameLayout,mPosition);
                }
            }

            @Override
            protected void convertHead(ViewHolder holder, final InfoPersonMsgModel.ListBean data, int position) {
                imageView = holder.getView(R.id.care_add_delete);
                CircleImageView person_big_head_img = holder.getView(R.id.person_big_head_img);

                if (isOther != null && isOther.equals("true") && !ZSApp.getInstance().getUserId().equals(data.getCreatorId()+"")){
                    imageView.setVisibility(View.VISIBLE);
                    if (infoPersonMsgModel.getState() == 1){
                        imageView.setImageResource(R.drawable.care_delete);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                deleteCarePerson(infoPersonMsgModel.getRelationId()+"");
                            }
                        });
                    }else if (infoPersonMsgModel.getState() == 2){
                        imageView.setImageResource(R.drawable.care_add);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (isCanClick){
                                    addCarePerson("2",state,userId);
                                    isCanClick = false;
                                }
                            }
                        });
                    }else {
                        imageView.setVisibility(View.GONE);
                    }

                }else {
                    imageView.setVisibility(View.GONE);
                }

                TextView care_person = holder.getView(R.id.care_person);
                TextView person_userName = holder.getView(R.id.person_userName);
                TextView likes_person = holder.getView(R.id.likes_person);

                if (infoPersonMsgModel != null){
                    person_userName.setText(infoPersonMsgModel.getName());
                    care_person.setText(infoPersonMsgModel.getCareCount()+" 关注");
                    likes_person.setText(infoPersonMsgModel.getFansCount()+" 粉丝");
                    if (state.equals("2")){
                        ImageLoader.loadHeadTarget(BaseInfoPersonalMsg.this, Const.URL_userHead+infoPersonMsgModel.getHeadImageurl(),person_big_head_img);
                    }else {
                        ImageLoader.loadHeadTarget(BaseInfoPersonalMsg.this, Const.URL_schoolHead+infoPersonMsgModel.getHeadImageurl(),person_big_head_img);
                    }
                }

                if (!isOther.equals("true")){
                    care_person.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(bContext, BaseCareOrCaredActivity.class).putExtra("title","我关注的人"));
                        }
                    });

                    likes_person.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(bContext, BaseCareOrCaredActivity.class).putExtra("title","关注我的人"));
                        }
                    });
                }

            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.item_person_msg;
            }

            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.item_person_msg_head;
            }

            @Override
            public int getItemViewType(int position) {
                super.getItemViewType(position);
                if (position == 0){
                    return TYPE_HEAD_VIEW;
                }else if (position > detailModelList.size() - 1){
                    return TYPE_FOOTER_VIEW;
                }else {
                    return TYPE_COMMON_VIEW;
                }
            }
        };

        lib_baseinfomation_school_swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                needClearData = true;
                isLastPage = false;
                init();
            }
        });

        personal_info_rv.setLayoutManager(new LinearLayoutManager(this));
        baseAdapter.setLoadingView(R.layout.footer_more);
        baseAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (!isLastPage){
                    pageNum++;
                    init();
                    lib_baseinfomation_school_swipeRefresh.setRefreshing(true);
                }
            }
        });

        personal_info_rv.setHasFixedSize(true);
        personal_info_rv.setItemAnimator(new DefaultItemAnimator());


        baseAdapter.setOnItemClickListener(new OnItemClickListeners<InfoPersonMsgModel.ListBean>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, InfoPersonMsgModel.ListBean data, int position) {
                if (position > 0){
                    Intent intent = new Intent(bContext,BaseInfoDetailActivity.class);
                    InfomationModel infomationModel = initInformationModel(data);
                    intent.putExtra("InfomationModel",infomationModel);
                    intent.putExtra("position",position);
                    startActivity(intent);
                }
            }
        });

        init();
    }

    @Subscribe
    public void onEventMainThread(InfoChildHeartModel event) {
        if (event.isLikeClick()){
            detailModelList.get(event.getPosition()).setIsLike(event.getIsLike());
            detailModelList.get(event.getPosition()).setLikeNum(event.getLikeNum());
        }else {
            detailModelList.get(event.getPosition()).setCommentNum(Integer.parseInt(event.getCommentNum()));
        }
        baseAdapter.notifyDataSetChanged();
    }

    public boolean isEmpty(String s){
        if (s == null || s.equals("")){
            return true;
        }
        return false;
    }

    private InfomationModel initInformationModel(InfoPersonMsgModel.ListBean data) {
        InfomationModel infomationModel = new InfomationModel();
        infomationModel.setLikeNum(data.getLikeNum()+"");
        infomationModel.setIsLike(data.getIsLike());
        infomationModel.setCoverImageurl(data.getCoverImageurl());
        infomationModel.setImageurl(data.getImageurl());
        infomationModel.setCommentNum(data.getCommentNum()+"");
        infomationModel.setCommonId(data.getCommonId());
        infomationModel.setContent(data.getContent());
        infomationModel.setCreateTime(data.getCreateTime());
        infomationModel.setHeadImageurl(infoPersonMsgModel.getHeadImageurl());
        infomationModel.setName(infoPersonMsgModel.getName());
        infomationModel.setCreatorId(data.getCreatorId());
        infomationModel.setDetailsUrl(data.getDetailsUrl());
        infomationModel.setImageId(data.getImageId());
        infomationModel.setInfoId(data.getInfoId());
        infomationModel.setState(data.getState());
        infomationModel.setType(data.getType());
        infomationModel.setTitle(data.getTitle());
        infomationModel.setSubAlbumId(data.getSubAlbumId());
        infomationModel.setInfoImageurls(data.getInfoImageurls());
        return infomationModel;
    }

    public void init(){
        if (isOther != null && isOther.equals("true")){
            userId = getIntent().getStringExtra("userId");
            presenter.getUserInfoMsg(userId,pageNum,state,ZSApp.getInstance().getUserId());
        }else {
            presenter.getUserInfoMsg(ZSApp.getInstance().getUserId(),pageNum,state,ZSApp.getInstance().getUserId());
        }
    }


    public void addCarePerson(String fromState,String toState,String toUserId){
        presenter.addCarePerson(fromState, toState, toUserId);
    }

    private void deleteCarePerson(String s) {
        presenter.deleteCarePerson(s);
    }

    //展示视频
    private void initVideo(ViewHolder holder, InfoPersonMsgModel.ListBean data, FrameLayout frameLayout) {
        final String videoUrl = data.getImageurl();
        showFrameLayoutChild(0,frameLayout);

        ImageView imageView = holder.getView(R.id.news_image_big);
        //加载单张图片
        ImageLoader.loadRoundCenterCropInfomationImg(BaseInfoPersonalMsg.this,Const.URL_VideoFirstFrame+data.getCoverImageurl(),imageView);

        ImageView item_info_video_play = holder.getView(R.id.item_info_video_play);
        item_info_video_play.setVisibility(View.VISIBLE);

        final AlbumModel albumModel = new AlbumModel();
        final Bundle b = new Bundle();

        albumModel.setUserName(ZSApp.getInstance().getLoginUser().getUserName());
        albumModel.setLastmodifyTime(data.getCreateTime()+"");
        albumModel.setUserId(ZSApp.getInstance().getUserId());
        albumModel.setVideoImageUrl(data.getCoverImageurl());
        albumModel.setContent(data.getContent());

        item_info_video_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = VideoPlayActivity.getIntentVideoPlayNet(Const.videoPath,Const.URL_Video+videoUrl,videoUrl,VideoPlayActivity.MORE_TYPE_ALBUM);
                intent.setClass(BaseInfoPersonalMsg.this, VideoPlayActivity.class);
                b.putSerializable("AlbumModel", albumModel);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    //展示图片
    private void initPictures(ViewHolder holder, InfoPersonMsgModel.ListBean data,FrameLayout frameLayout) {
        String[] images = data.getImageurl().split(",");

        if (images.length == 1){
            //单张图片
            showFrameLayoutChild(0,frameLayout);
            ImageView imageView = holder.getView(R.id.news_image_big);
            //加载单张图片
            ImageLoader.loadRoundCenterCropInfomationImg(BaseInfoPersonalMsg.this,Const.URL_Album+images[0],imageView);

            ImageView item_info_video_play = holder.getView(R.id.item_info_video_play);
            item_info_video_play.setVisibility(View.GONE);

            final List<SlideBean> itemModels = new ArrayList<>();
            SlideBean itemModel = new SlideBean();
            itemModel.setUrl(Const.URL_Album+images[0]);
            itemModels.add(itemModel);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(BaseInfoPersonalMsg.this, SlideActivity.class);
                    intent.putParcelableArrayListExtra(SlideActivity.kDatas, (ArrayList<? extends Parcelable>) itemModels);
                    intent.putExtra(SlideActivity.kCurrentPosition, 0);// 这个值需要改成点击图片的值
                    startActivity(intent);
                }
            });

        }else {
            //多张图片
            showFrameLayoutChild(1,frameLayout);

            final List<SlideBean> itemModels = new ArrayList<>();
            for (int i = 0; i < images.length; i++) {
                SlideBean itemModel = new SlideBean();
                itemModel.setUrl(Const.URL_Album+images[i]);
                itemModels.add(itemModel);
            }

            BaseAdapter<SlideBean> baseAdapter = new BaseAdapter<SlideBean>(BaseInfoPersonalMsg.this,itemModels,false) {
                @Override
                protected void convert(ViewHolder holder, SlideBean data) {
                    ImageView imageView = holder.getView(R.id.ItemSchoolRv_iv);

                    if(data.getUrl()==null){
                        imageView.setImageResource(R.drawable.holder_color_1);
                    }else{
                        ImageLoader.loadRoundCenterCropImg(mContext,data.getUrl(), imageView);
                    }
                }

                @Override
                protected int getItemLayoutId() {
                    return R.layout.recycler_item_info_rvitem;
                }
            };

            baseAdapter.setOnItemClickListener(new OnItemClickListeners<SlideBean>() {
                @Override
                public void onItemClick(ViewHolder viewHolder, SlideBean data, int position) {
                    Intent intent = new Intent(BaseInfoPersonalMsg.this, SlideActivity.class);
                    intent.putParcelableArrayListExtra(SlideActivity.kDatas, (ArrayList<? extends Parcelable>) itemModels);
                    intent.putExtra(SlideActivity.kCurrentPosition, 0);// 这个值需要改成点击图片的值
                    startActivity(intent);
                }
            });


            RecyclerView recyclerView = holder.getView(R.id.ItemInfo_recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(BaseInfoPersonalMsg.this, 3));
//            recyclerView.addItemDecoration(new GridSpacingItemDecoration(3,20,false));
            recyclerView.setAdapter(baseAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

        }
    }

    //展示校园图片
    private void initPicturesSchool(ViewHolder holder, final InfoPersonMsgModel.ListBean data, FrameLayout frameLayout, final int mPosition) {
        String[] images = data.getInfoImageurls().split(",");

        if (images.length == 1){
            //单张图片
            showFrameLayoutChild(0,frameLayout);
            ImageView imageView = holder.getView(R.id.news_image_big);

            ImageLoader.loadRoundCenterCropInfomationImg(BaseInfoPersonalMsg.this,images[0],imageView);

            ImageView item_info_video_play = holder.getView(R.id.item_info_video_play);
            item_info_video_play.setVisibility(View.GONE);

            final List<SlideBean> itemModels = new ArrayList<>();
            SlideBean itemModel = new SlideBean();
            itemModel.setUrl(images[0]);
            itemModels.add(itemModel);
            /**
             * 大当家让改成点图片进入详情    2017/7/17  sw
             */
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(BaseInfoPersonalMsg.this, SlideActivity.class);
//                    intent.putParcelableArrayListExtra(SlideActivity.kDatas, (ArrayList<? extends Parcelable>) itemModels);
//                    intent.putExtra(SlideActivity.kCurrentPosition, 0);// 这个值需要改成点击图片的值
//                    startActivity(intent);
//                }
//            });


        }else {
            //多张图片
            showFrameLayoutChild(1,frameLayout);

            final List<SlideBean> itemModels = new ArrayList<>();
            for (int i = 0; i < images.length; i++) {
                SlideBean itemModel = new SlideBean();
                itemModel.setUrl(images[i]);
                itemModels.add(itemModel);
            }

            BaseAdapter<SlideBean> baseAdapter = new BaseAdapter<SlideBean>(BaseInfoPersonalMsg.this,itemModels,false) {
                @Override
                protected void convert(ViewHolder holder, SlideBean data) {
                    ImageView imageView = holder.getView(R.id.ItemSchoolRv_iv);

                    if(data.getUrl()==null){
                        imageView.setImageResource(R.drawable.holder_color_1);
                    }else{
                        ImageLoader.loadRoundCenterCropImg(mContext,data.getUrl(), imageView);
                    }
                }

                @Override
                protected int getItemLayoutId() {
                    return R.layout.recycler_item_info_rvitem;
                }
            };
            /**
             * 大当家让改成点图片进入详情    2017/7/17  sw
             */
            baseAdapter.setOnItemClickListener(new OnItemClickListeners<SlideBean>() {
                @Override
                public void onItemClick(ViewHolder viewHolder, SlideBean mdata, int position) {
                    //跳转照片详情
//                    Intent intent = new Intent(BaseInfoPersonalMsg.this, SlideActivity.class);
//                    intent.putParcelableArrayListExtra(SlideActivity.kDatas, (ArrayList<? extends Parcelable>) itemModels);
//                    intent.putExtra(SlideActivity.kCurrentPosition, 0);// 这个值需要改成点击图片的值
//                    startActivity(intent);
                    Intent intent = new Intent(BaseInfoPersonalMsg.this,BaseInfoDetailActivity.class);
                    InfomationModel infomationModel = initInformationModel(data);
                    intent.putExtra("InfomationModel",infomationModel);
                    intent.putExtra("position",mPosition);
                    BaseInfoPersonalMsg.this.startActivity(intent);

                }
            });


            RecyclerView recyclerView = holder.getView(R.id.ItemInfo_recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            recyclerView.setAdapter(baseAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

        }
    }

    //展示某个子控件
    private void showFrameLayoutChild(int i,FrameLayout frameLayout) {
        for (int j = 0; j < frameLayout.getChildCount(); j++) {
            if (i == j){
                frameLayout.getChildAt(i).setVisibility(View.VISIBLE);
            }else {
                frameLayout.getChildAt(j).setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void showData(Object o) {
        infoPersonMsgModel = (InfoPersonMsgModel) o;
        List<InfoPersonMsgModel.ListBean> models = infoPersonMsgModel.getList();
        if (lib_baseinfomation_school_swipeRefresh != null && lib_baseinfomation_school_swipeRefresh.isRefreshing()){
            lib_baseinfomation_school_swipeRefresh.setRefreshing(false);
        }
        if (needClearData){
            detailModelList.clear();
            needClearData = false;
        }
        if (detailModelList.size() == 0){
            if (isOther != null && isOther.equals("true")){
                int cresteId = Integer.parseInt(getIntent().getStringExtra("userId"));
                headBean.setCreatorId(cresteId);
            }
            detailModelList.add(headBean);
        }
        if (models != null && models.size() > 0){
            detailModelList.addAll(models);
        }else {
            isLastPage = true;
            baseAdapter.setLoadingView(R.layout.footer_loadend);
        }
        if (!isHasSetAdapter){
            isHasSetAdapter = true;
            personal_info_rv.setAdapter(baseAdapter);
        }else {
            baseAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void addCarePersonSuccess() {
        init();
        isCanClick = true;
        ToastUtils.showNormalToast(bContext,"添加关注成功。");
    }

    @Override
    public void deleteCarepersonSuccess() {
        init();
        ToastUtils.showNormalToast(bContext,"取消关注成功。");
    }

    //接收用户删除咨询，用于更新当前数据
    @Subscribe
    public void onEventMainThread(BaseInfoDetailEventModel event) {
        pageNum = 1;
        isLastPage = false;
        detailModelList.clear();
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
