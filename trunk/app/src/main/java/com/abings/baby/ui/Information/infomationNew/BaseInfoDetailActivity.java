package com.abings.baby.ui.Information.infomationNew;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abings.baby.ZSApp;
import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.Information.InfomationChild.BaseInfoPersonalMsg;
import com.abings.baby.ui.publishvideo.VideoPlayActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.BaseInfoDetailEventModel;
import com.hellobaby.library.data.model.CommentModel;
import com.hellobaby.library.data.model.InfoChildHeartModel;
import com.hellobaby.library.data.model.InfomationModel;
import com.hellobaby.library.data.model.SelectInfoDetailModel;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;
import com.hellobaby.library.ui.slide.SlideActivity;
import com.hellobaby.library.ui.slide.SlideBean;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.utils.KeyboardChangeListener;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.utils.StringUtils;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.ProgressDialogHelper;
import com.hellobaby.library.widget.ToastUtils;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;
import com.hellobaby.library.widget.custom.ShareBottomDialog;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class BaseInfoDetailActivity extends BaseLibTitleActivity implements KeyboardChangeListener.KeyBoardListener,BaseInfoDetailMVP {

    protected RecyclerView info_detail_rv;
    protected List<CommentModel> detailModelList;
    protected BaseAdapter<CommentModel> baseAdapter;
    protected TextView btn_detail_send;

    private KeyboardChangeListener changeListener;

    private InfomationModel infomationModel = new InfomationModel();

    private String topicType;
    private String topicId;
    private String commentContent;
    private String commentUtype;
    private String toReplyName;

    private String toReplyUid;
    private String toReplyUtype;

    private boolean isRetoSombody = false; //是否恢复某人

    private boolean isInputShow = false; //键盘是否显示

    private int myPosition = 0;

    private boolean isNeedClear = false;

    @BindView(R.id.commension_edit)
    EditText commension_edit;

    @Inject
    BaseInfoDetailPresenter presenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_base_info_detail;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(getActivityComponent(),this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);

        initViews();
        setBtnLeftClickFinish();
        setTitleText("正文");
        setTitleBackground(R.color.white);


        changeListener = new KeyboardChangeListener(this);
        changeListener.setKeyBoardListener(this);

        infomationModel = (InfomationModel) getIntent().getSerializableExtra("InfomationModel");

        myPosition = getIntent().getIntExtra("position",0);

        init();
    }


    public void init(){

        if (infomationModel.getState() == 2){
            presenter.selectInfoDetails(infomationModel.getSubAlbumId()+"","2");
            topicId = infomationModel.getSubAlbumId()+"";
            topicType = "2";
        }else {
            presenter.selectInfoDetails(infomationModel.getInfoId()+"","1");
            topicId = infomationModel.getInfoId()+"";
            topicType = "1";
        }
        commentUtype = "1";//1 是家长
    }

    private void initViews() {
        info_detail_rv = (RecyclerView) findViewById(R.id.info_detail_rv);
        detailModelList = new ArrayList<>();
        btn_detail_send = (TextView) findViewById(R.id.btn_detail_send);
        btn_detail_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentContent = commension_edit.getText().toString();
                if (!isAllMsgEmpty()){
                    presenter.addComment(topicType,topicId,commentContent,toReplyUid,toReplyUtype,commentUtype);
                    commension_edit.setText("");
                    //键盘若是展开则关闭
                    if (isInputShow){
                        changeInputState();
                    }
                }
            }
        });


        baseAdapter = new BaseAdapter<CommentModel>(bContext,detailModelList,false) {
            @Override
            protected void convert(ViewHolder holder, CommentModel data) {
                LogZS.i("评论每一项数据："+data.toString());

                CircleImageView head_Img = holder.getView(R.id.head_Img);
                ImageLoader.loadHeadTarget(bContext,Const.URL_userHead+data.getHeadImageurl(),head_Img);

                TextView info_detail_comment_name = holder.getView(R.id.info_detail_comment_name);
                info_detail_comment_name.setText(data.getName());

                TextView info_detail_comment_date = holder.getView(R.id.info_detail_comment_date);
                info_detail_comment_date.setText(DateUtil.getFormatTimeFromTimestamp(data.getCreateTime(),"MM月dd日 HH:mm"));

                TextView info_decontent_return_name = holder.getView(R.id.info_decontent_return_name);
                TextView info_decontent_return_ts = holder.getView(R.id.info_decontent_return_ts);
                TextView info_decontent_return_content = holder.getView(R.id.info_decontent_return_content);

                if (!isEmpty(data.getToReplyUtype()) && !isEmpty(data.getToReplyUid())){
                    info_decontent_return_ts.setVisibility(View.VISIBLE);
                    info_decontent_return_name.setVisibility(View.VISIBLE);
                    info_decontent_return_name.setText(data.getToName());
                    info_decontent_return_content.setText("："+data.getCommentContent());
                }else {
                    info_decontent_return_ts.setVisibility(View.GONE);
                    info_decontent_return_name.setVisibility(View.GONE);
                    info_decontent_return_content.setText(data.getCommentContent());
                }

            }

            @Override
            protected void convertHead(ViewHolder holder, final CommentModel data, int position) {
                LinearLayout linearLayout = holder.getView(R.id.item_info_detail_head_ll);
                FrameLayout frameLayout = holder.getView(R.id.info_detail_show_fl);
                if (infomationModel != null){
                    //头像
                    CircleImageView info_detail_head_img = holder.getView(R.id.info_detail_head_img);
                    if (infomationModel.getHeadImageurl() != null && !infomationModel.getHeadImageurl().equals("")){
                        ImageLoader.loadHeadTarget(bContext, Const.URL_userHead + infomationModel.getHeadImageurl(),info_detail_head_img);
                    }else {
                        info_detail_head_img.setImageResource(R.drawable.head_holder);
                    }

                    //姓名
                    TextView info_detail_username = holder.getView(R.id.info_detail_username);
                    info_detail_username.setText(infomationModel.getName());

                    //标题
                    TextView info_detail_title = holder.getView(R.id.info_detail_title);
                    if (!isEmpty(infomationModel.getTitle())){
                        info_detail_title.setVisibility(View.VISIBLE);
                        info_detail_title.setText(infomationModel.getTitle());
                    }else {
                        info_detail_title.setVisibility(View.GONE);
                    }

                    //内容
                    TextView info_detail_content = holder.getView(R.id.info_detail_head_content);
                    if (!isEmpty(infomationModel.getContent())){
                        info_detail_content.setVisibility(View.VISIBLE);
                        info_detail_content.setText(infomationModel.getContent());
                    }else {
                        info_detail_content.setVisibility(View.GONE);
                    }

                    //赞数量
                    final TextView person_like_number = holder.getView(R.id.person_like_number);
                    person_like_number.setText(infomationModel.getLikeNum());

                    //评论数量
                    TextView info_detail_commension_number = holder.getView(R.id.info_detail_commension_number);
                    info_detail_commension_number.setText(infomationModel.getCommentNum());

                    //创建日期
                    TextView info_detail_date = holder.getView(R.id.info_detail_date);
                    info_detail_date.setText(DateUtil.getFormatTimeFromTimestamp(infomationModel.getCreateTime(),"MM月dd日 HH:MM"));

                    final CheckBox heart_like_checkbox = holder.getView(R.id.info_detail_like_checkbox);
                    if (infomationModel.getIsLike() > 0){
                        heart_like_checkbox.setChecked(true);
                    }else {
                        heart_like_checkbox.setChecked(false);
                    }


                    heart_like_checkbox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String number = person_like_number.getText().toString();
                            int numberInt = Integer.parseInt(number);
                            if (heart_like_checkbox.isChecked()){
                                numberInt++;
                                infomationModel.setIsLike(1);
                                EventBus.getDefault().post(new InfoChildHeartModel(myPosition,1,numberInt,true));
                            }else {
                                numberInt--;
                                infomationModel.setIsLike(0);
                                EventBus.getDefault().post(new InfoChildHeartModel(myPosition,0,numberInt,true));
                            }
                            if (infomationModel.getState() == 1){
                                presenter.addLikeInfo(infomationModel.getState()+"",infomationModel.getInfoId()+"");
                            }else {
                                presenter.addLikeInfo(infomationModel.getState()+"",infomationModel.getSubAlbumId()+"");
                            }
                            infomationModel.setLikeNum(numberInt+"");
                            person_like_number.setText(numberInt+"");
                        }
                    });


                    //家长发布的
                    if (infomationModel.getState() == 2){
                        switch (infomationModel.getType()){
                            case 2:
                                initPictures(holder,frameLayout);
                                break;
                            case 3:
                                initVideo(holder,frameLayout);
                                break;
                            default:
                                showFrameLayoutChild(2,frameLayout);
                                break;
                        }
                        linearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(bContext, BaseInfoPersonalMsg.class);
                                intent.putExtra("other","true");
                                intent.putExtra("userId",infomationModel.getCreatorId()+"");
                                intent.putExtra("state",infomationModel.getState()+"");
                                startActivity(intent);
                            }
                        });
                        //校园发的
                    }else {
                        linearLayout.setVisibility(View.GONE);
                        showFrameLayoutChild(0,frameLayout);
                        initPicturesSchool(holder,frameLayout);
                    }
                }

            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.item_info_commension;
            }

            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.item_info_detail_head;
            }

            @Override
            public int getItemViewType(int position) {
                if (position == 0){
                    return TYPE_HEAD_VIEW;
                }else {
                    return TYPE_COMMON_VIEW;
                }
            }
        };

        info_detail_rv.setLayoutManager(new LinearLayoutManager(this));
        info_detail_rv.setItemAnimator(new DefaultItemAnimator());
        info_detail_rv.setHasFixedSize(true);
        info_detail_rv.setAdapter(baseAdapter);

        baseAdapter.setOnItemClickListener(new OnItemClickListeners<CommentModel>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, final CommentModel data, int position) {

                if (ZSApp.getInstance().getUserId().equals(data.getCommentUid()+"")){
                    BottomDialogUtils.getBottomdeleteMsgDialog(bContext, "删除评论", "取消", new BottomDialogUtils.onSureAndCancelClick() {
                        @Override
                        public void onItemClick() {
                            presenter.deleteComment(data.getTInfoCommId()+"",data.getTopicId()+"",data.getTopicType()+"");
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                }else {
                    changeInputState();
                    commension_edit.setFocusable(true);
                    commension_edit.setFocusableInTouchMode(true);
                    commension_edit.requestFocus();
                    isRetoSombody = true;
                    toReplyName = data.getName();
                    toReplyUid = data.getCommentUid()+"";
                    toReplyUtype = data.getCommentUtype()+"";
                }

            }
        });
    }

    private boolean isEmpty(String s){
        if (s == null){
            return true;
        }
        if (s.equals("")){
            return true;
        }
        if (s.equals("0")){
            return true;
        }
        return false;
    }

    public void changeInputState(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //展示校园图片
    private void initPicturesSchool(ViewHolder holder, FrameLayout frameLayout) {
        WebView webView = holder.getView(R.id.info_detail_webview);

        ImageView info_detail_big_img = holder.getView(R.id.info_detail_big_img);
        info_detail_big_img.setVisibility(View.GONE);

        ImageView info_detail_video_play = holder.getView(R.id.info_detail_video_play);
        info_detail_video_play.setVisibility(View.GONE);

        LogZS.i("网址："+infomationModel.getDetailsUrl());
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        webView.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        webView.getSettings().setSupportZoom(true);//是否可以缩放，默认true
        webView.getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        webView.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        webView.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        webView.getSettings().setAppCacheEnabled(true);//是否使用缓存
        webView.getSettings().setDomStorageEnabled(true);//DOM Storage
        webView.getSettings().setBlockNetworkImage(false);//解决图片显示不正常
        webView.clearCache(true);
        webView.clearHistory();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        WebViewClient webViewClient = new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }
            //过滤掉电信广告
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (url.contains("adpro.cn")) {
                    return new WebResourceResponse(null, null, null);
                }
                return null;
            }
        };
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    ProgressDialogHelper.getInstance().hideProgressDialog();
                } else{
                    ProgressDialogHelper.getInstance().showProgressDialog(BaseInfoDetailActivity.this,"正在加载中...");
                }

            }
        });
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(infomationModel.getDetailsUrl());
    }

    //展示图片
    private void initPictures(ViewHolder holder,FrameLayout frameLayout) {
        String[] images = infomationModel.getImageurl().split(",");

        if (images.length == 1){
            //单张图片
            showFrameLayoutChild(0,frameLayout);
            ImageView imageView = holder.getView(R.id.info_detail_big_img);

            //家长发的 加载单张图片
            ImageLoader.loadRoundCenterCropInfomationImg(bContext,Const.URL_Album+images[0],imageView);

            WebView info_detail_webview = holder.getView(R.id.info_detail_webview);
            info_detail_webview.setVisibility(View.GONE);

            ImageView item_info_video_play = holder.getView(R.id.info_detail_video_play);
            item_info_video_play.setVisibility(View.GONE);

            final List<SlideBean> itemModels = new ArrayList<>();
            SlideBean itemModel = new SlideBean();
            itemModel.setUrl(Const.URL_Album+images[0]);

            itemModels.add(itemModel);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(bContext, SlideActivity.class);
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

            BaseAdapter<SlideBean> baseAdapter = new BaseAdapter<SlideBean>(bContext,itemModels,false) {
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
                    Intent intent = new Intent(bContext, SlideActivity.class);
                    intent.putParcelableArrayListExtra(SlideActivity.kDatas, (ArrayList<? extends Parcelable>) itemModels);
                    intent.putExtra(SlideActivity.kCurrentPosition, 0);// 这个值需要改成点击图片的值
                    startActivity(intent);
                }
            });


            RecyclerView recyclerView = holder.getView(R.id.ItemInfo_recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(bContext, 3));
            recyclerView.setAdapter(baseAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

        }
    }

    //展示视频
    private void initVideo(ViewHolder holder,  FrameLayout frameLayout) {
        final String videoUrl = infomationModel.getImageurl();
        showFrameLayoutChild(0,frameLayout);

        ImageView imageView = holder.getView(R.id.info_detail_big_img);
        //加载单张图片
        ImageLoader.loadRoundCenterCropInfomationImg(bContext,Const.URL_VideoFirstFrame+infomationModel.getCoverImageurl(),imageView);

        ImageView item_info_video_play = holder.getView(R.id.info_detail_video_play);
        item_info_video_play.setVisibility(View.VISIBLE);

        final AlbumModel albumModel = new AlbumModel();
        final Bundle b = new Bundle();

        albumModel.setUserName(infomationModel.getName());
        albumModel.setLastmodifyTime(infomationModel.getCreateTime()+"");
        albumModel.setUserId(infomationModel.getCreatorId()+"");
        albumModel.setVideoImageUrl(infomationModel.getCoverImageurl());
        albumModel.setContent(infomationModel.getContent());

        item_info_video_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = VideoPlayActivity.getIntentVideoPlayNet(Const.videoPath,Const.URL_Video+videoUrl,videoUrl,VideoPlayActivity.MORE_TYPE_ALBUM);
                intent.setClass(bContext, VideoPlayActivity.class);
                b.putSerializable("AlbumModel", albumModel);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
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
    protected void btnRightOnClick(View v) {

        if (infomationModel.getState() == 2 && ZSApp.getInstance().getUserId().equals(infomationModel.getCreatorId()+"")){
            BottomDialogUtils.getBottomdeleteDialog(bContext, "删除", new BottomDialogUtils.onSureClick() {
                @Override
                public void onItemClick() {
                    presenter.deleteAlbum(infomationModel.getSubAlbumId()+"");
                }

            });
        }else if (infomationModel.getState() == 1){
            BottomDialogUtils.getBottomdeleteDialog(bContext, "分享", new BottomDialogUtils.onSureClick() {
                @Override
                public void onItemClick() {
                    if (infomationModel.getDetailsUrl() != null && !infomationModel.getDetailsUrl().equals("")){
                        ShareBottomDialog.getShareUrlBottomDialog(bContext,infomationModel.getInfoImageurls().split(",")[0], infomationModel.getDetailsUrl(),infomationModel.getContent(), infomationModel.getTitle());
                    }else {
                        ToastUtils.showNormalToast(bContext,"该网址不正确，无法分享。");
                    }
                }

            });
        }

    }

    @Override
    public void showData(Object o) {
//        List<CommentModel> commentModels = (List<CommentModel>) o;
//        if (detailModelList.size() == 0){
//            detailModelList.add(new CommentModel());
//        }
//        if (commentModels != null && commentModels.size() > 0){
//            detailModelList.addAll(commentModels);
//        }
//        baseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onKeyboardChange(boolean isShow, int keyboardHeight) {

        isInputShow = isShow;

        if (isShow && isRetoSombody){
            commension_edit.setHint("回复 "+toReplyName+"：");
        }else {
            isRetoSombody = false;
            commension_edit.setHint("评论");
        }
    }

    public boolean isAllMsgEmpty(){
        if (StringUtils.isEmpty(topicType)){
            return true;
        }
        if (StringUtils.isEmpty(topicId)){
            return true;
        }
        if (StringUtils.isEmpty(commentContent) || commentContent.equals("")){
            return true;
        }
        if (StringUtils.isEmpty(commentUtype)){
            return true;
        }
        return false;
    }

    @Override
    public void addCommentSuccess() {

        ToastUtils.showNormalToast(bContext,"评论成功。");
        int commensionNum =  Integer.parseInt(infomationModel.getCommentNum())+1;
        infomationModel.setCommentNum(commensionNum+"");
        EventBus.getDefault().post(new InfoChildHeartModel(myPosition,infomationModel.getCommentNum(),false));

        isNeedClear = true;
        init();
    }

    @Override
    public void deleteCommentSuccess() {
        ToastUtils.showNormalToast(bContext,"删除评论成功。");
        int commensionNum =  Integer.parseInt(infomationModel.getCommentNum())-1;
        infomationModel.setCommentNum(commensionNum+"");

        EventBus.getDefault().post(new InfoChildHeartModel(myPosition,infomationModel.getCommentNum(),false));
        isNeedClear = true;
        init();
    }

    @Override
    public void deleteAlbumSuccess() {
        ToastUtils.showNormalToast(bContext,"删除咨询成功。");
        EventBus.getDefault().post(new BaseInfoDetailEventModel("刷新数据。"));
        finish();
    }

    @Override
    public void selectInfoDetails(SelectInfoDetailModel detailModel) {
        LogZS.i("评论数据："+detailModel.toString());

        if (isNeedClear){
            detailModelList.clear();
        }

        if (detailModelList.size() == 0){
            detailModelList.add(new CommentModel());
        }
        if (detailModel.getCommentList()!= null && detailModel.getCommentList().size() > 0){
            detailModelList.addAll(detailModel.getCommentList());
        }
        if (detailModel != null){
            infomationModel.setCommentNum(detailModel.getCommentNum());
            infomationModel.setContent(detailModel.getContent());
            infomationModel.setInfoImageurls(detailModel.getInfoImageurls());
            infomationModel.setSubAlbumId(detailModel.getSubAlbumId());
            infomationModel.setState(detailModel.getState());
            infomationModel.setCommonId(detailModel.getCommonId());
            infomationModel.setCoverImageurl(detailModel.getCoverImageurl());
            infomationModel.setCreateTime(detailModel.getCreateTime());
            infomationModel.setCreatorId(detailModel.getCreatorId());
            infomationModel.setDetailsUrl(detailModel.getDetailsUrl());
            infomationModel.setHeadImageurl(detailModel.getHeadImageurl());
            infomationModel.setImageId(detailModel.getImageId());
            infomationModel.setImageurl(detailModel.getImageurl());
            infomationModel.setInfoId(detailModel.getInfoId());
            infomationModel.setTitle(detailModel.getTitle());
            infomationModel.setType(detailModel.getType());
            infomationModel.setName(detailModel.getName());
            infomationModel.setLikeNum(detailModel.getLikeNum());
            infomationModel.setIsLike(detailModel.getIsLike());
        }
        //当自己发的正文时，可以删除   当打开正文是网页的时候就可以分享
        if (ZSApp.getInstance().getUserId().equals(infomationModel.getCreatorId()+"") || infomationModel.getState() == 1){
            setBtnRightDrawableRes(R.drawable.title_more_black);
        }
        baseAdapter.notifyDataSetChanged();
        if (isNeedClear){
            isNeedClear = false;
            LogZS.i("需要滑动到底。");
            info_detail_rv.scrollToPosition(detailModelList.size() - 1);
        }
    }

    @Override
    public void noContent() {
        finish();
    }
}
