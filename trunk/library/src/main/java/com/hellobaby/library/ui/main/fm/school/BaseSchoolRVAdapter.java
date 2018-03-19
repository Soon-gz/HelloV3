package com.hellobaby.library.ui.main.fm.school;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hellobaby.library.Const;
import com.hellobaby.library.R;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.slide.SlideActivity;
import com.hellobaby.library.ui.slide.SlideBean;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.utils.KeyWordUtils;
import com.hellobaby.library.utils.ScreenUtils;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;


/**
 * Created by zwj on 2016/12/20.
 * description : 校园里的adapter
 */

public abstract class BaseSchoolRVAdapter extends BaseAdapter<SchoolItem> {

    private boolean isAll = false;
    private final int imageWidth;
    private final int imagePadding;
    protected String keyWords = "";
    private Class clzz;

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    //    public SchoolRVAdapter(Context context, List<SchoolItem> datas) {
//        super(context, datas, false);
//        imageWidth = (ScreenUtils.getScreenWidth(mContext) - ScreenUtils.dip2px(mContext, 75)) / 3;
//        imagePadding = ScreenUtils.dip2px(mContext, 1.5f);
//    }
    public BaseSchoolRVAdapter(Context context, List<SchoolItem> datas, Class clzz) {
        super(context, datas, true);
        imageWidth = (ScreenUtils.getScreenWidth(mContext) - ScreenUtils.dip2px(mContext, 20+2f*3)) / 3;
        imagePadding = ScreenUtils.dip2px(mContext, 2.5f);
        this.clzz = clzz;
    }

    @Override
    protected void convert(ViewHolder holder, final SchoolItem data) {
        TextView publishtitle = holder.getView(R.id.ItemSchool_tv_title);
        if (!keyWords.equals("")) {
            publishtitle.setText(KeyWordUtils.matcherSearchTitle(data.getName(), keyWords));
        } else {
            publishtitle.setText(data.getName());
        }

        RecyclerView recyclerView = holder.getView(R.id.ItemSchool_recyclerView);
        TextView tvContent = holder.getView(R.id.ItemSchool_tv_content);
        TextView publishtime = holder.getView(R.id.publish_time);
        TextView publisname = holder.getView(R.id.from_name);
        TextView del_tv = holder.getView(R.id.publish_candel);
        ImageView video_play = holder.getView(R.id.video_play);
        RelativeLayout rlVideo = holder.getView(R.id.ItemSchool_rl_video);

        recyclerView.setVisibility(View.GONE);
        tvContent.setVisibility(View.GONE);
        rlVideo.setVisibility(View.GONE);

        String type = data.getType();
        publishtime.setText(data.getPublish());
        publisname.setText("来自于 " + data.getFromname());
        del_tv.setVisibility(View.GONE);
        if (isNews(type)) {
            //新闻
            setRV(recyclerView, holder, data,rlVideo,video_play);
            if (data.getPhoto()!=null&&!data.getPhoto().trim().equals("")){
                ImageLoader.loadHeadTarget(mContext, Const.URL_schoolHead + data.getPhoto(), (ImageView) holder.getView(R.id.ItemSchool_im));
            } else {
                ((ImageView)holder.getView(R.id.ItemSchool_im)).setImageResource(R.drawable.head_holder);
            }
        } else if (isDynamic(type)) {
            if (data.getPhoto()!=null&&!data.getPhoto().trim().equals("")){
                ImageLoader.loadHeadTarget(mContext, Const.URL_TeacherHead + data.getPhoto(), (ImageView) holder.getView(R.id.ItemSchool_im));
            } else {
                ((ImageView)holder.getView(R.id.ItemSchool_im)).setImageResource(R.drawable.head_holder);
            }
            //动态
            if (null == data.getVideoUrl()) {
                //图片
                setRV(recyclerView, holder, data,rlVideo,video_play);
            } else {
                //小视频
                ImageView videoImage = holder.getView(R.id.ItemSchool_iv_video);
                video_play.setVisibility(View.VISIBLE);
                ImageLoader.loadRoundCenterCropImg(mContext, data.getVideoImageUrl(), videoImage);
                rlVideo.setVisibility(View.VISIBLE);
            }
            if(null!=data.getCreator()&&data.getCreator()){
                del_tv.setVisibility(View.VISIBLE);
                del_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        delDynamic(data.getId());
                    }
                });
            }else {
                del_tv.setVisibility(View.GONE);
            }
        } else if (isCookbook(type)) {
            //食谱
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(data.getUrl().trim());
            if (data.getPhoto()!=null&&!data.getPhoto().trim().equals("")){
                ImageLoader.loadHeadTarget(mContext, Const.URL_schoolHead + data.getPhoto(), (ImageView) holder.getView(R.id.ItemSchool_im));
            } else {
                ((ImageView)holder.getView(R.id.ItemSchool_im)).setImageResource(R.drawable.head_holder);
            }
        } else if (isEvent(type)) {
            if (data.getPhoto()!=null&&!data.getPhoto().trim().equals("")){
                ImageLoader.loadHeadTarget(mContext, Const.URL_TeacherHead + data.getPhoto(), (ImageView) holder.getView(R.id.ItemSchool_im));
            } else {
                ((ImageView)holder.getView(R.id.ItemSchool_im)).setImageResource(R.drawable.head_holder);
            }
            //活动
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(data.getUrl().trim());
        }

        setOnItemClickListener(new OnItemClickListeners<SchoolItem>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, SchoolItem data, int position) {
                clickItem(data);
            }
        });

        //右下角的tag
        ImageView ivTag = holder.getView(R.id.ItemSchool_iv_tag);
        if (isAll) {
            if (isNews(type)) {
                if (data.getPhoto()!=null&&!data.getPhoto().trim().equals("")){
                    ImageLoader.loadHeadTarget(mContext, Const.URL_schoolHead + data.getPhoto(), (ImageView) holder.getView(R.id.ItemSchool_im));
                } else {
                    ((ImageView)holder.getView(R.id.ItemSchool_im)).setImageResource(R.drawable.head_holder);
                }
                ivTag.setImageResource(R.drawable.school_icon_news);
            } else if (isDynamic(type)) {
                if (data.getPhoto()!=null&&!data.getPhoto().trim().equals("")){
                    ImageLoader.loadHeadTarget(mContext, Const.URL_TeacherHead + data.getPhoto(), (ImageView) holder.getView(R.id.ItemSchool_im));
                } else {
                    ((ImageView)holder.getView(R.id.ItemSchool_im)).setImageResource(R.drawable.head_holder);
                }
                ivTag.setImageResource(R.drawable.school_icon_dynamic);
            } else if (isCookbook(type)) {
                if (data.getPhoto()!=null&&!data.getPhoto().trim().equals("")){
                    ImageLoader.loadHeadTarget(mContext, Const.URL_schoolHead + data.getPhoto(), (ImageView) holder.getView(R.id.ItemSchool_im));
                } else {
                    ((ImageView)holder.getView(R.id.ItemSchool_im)).setImageResource(R.drawable.head_holder);
                }
                ivTag.setImageResource(R.drawable.school_icon_cookbook);
            } else if (isEvent(type)) {
                if (data.getPhoto()!=null&&!data.getPhoto().trim().equals("")){
                    ImageLoader.loadHeadTarget(mContext, Const.URL_TeacherHead + data.getPhoto(), (ImageView) holder.getView(R.id.ItemSchool_im));
                } else {
                    ((ImageView)holder.getView(R.id.ItemSchool_im)).setImageResource(R.drawable.head_holder);
                }
                ivTag.setImageResource(R.drawable.school_icon_event);
            }
        } else {
            ivTag.setVisibility(View.GONE);
        }
    }


    protected  void delDynamic(int id){

    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.recycler_item_school;
    }

    public void setIsAll(boolean isAll) {
        this.isAll = isAll;
    }


    private void setRV(RecyclerView recyclerView, ViewHolder holder, final SchoolItem data,RelativeLayout layout,ImageView video_play) {

        video_play.setVisibility(View.GONE);
        ImageView ItemSchool_iv_video = holder.getView(R.id.ItemSchool_iv_video);

        if (data.getListUrl().size() == 1){
            recyclerView.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
            ImageLoader.loadRoundCenterCropImg(mContext,data.getListUrl().get(0),ItemSchool_iv_video);

        }else {
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
            List<SchoolItem> list = new ArrayList<>();

            List<String> urls = data.getListUrl();
            if (null != urls) {
                for (int i = 0; i < urls.size(); i++) {
                    SchoolItem item = new SchoolItem();
                    item.setType(data.getType());
                    item.setUrl(urls.get(i));
                    item.setListUrl(urls);
                    item.setObject(data.getObject());
                    item.setId(data.getId());
                    item.setNewsUrl(data.getNewsUrl());
                    item.setPublish(data.getPublish());
                    item.setFromname(data.getFromname());
                    item.setVideoUrl(data.getVideoUrl());
                    list.add(item);
                }
            }

            BaseAdapter<SchoolItem> adapter = new BaseAdapter<SchoolItem>(mContext, list, false) {
                @Override
                protected void convert(ViewHolder holder, SchoolItem data) {
                    ImageView imageView = holder.getView(R.id.ItemSchoolRv_iv);
                    LinearLayout linearLayout = holder.getView(R.id.ItemSchoolRv_ll);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(imageWidth, imageWidth);
                    linearLayout.setPadding(imagePadding, imagePadding, imagePadding, imagePadding);
                    linearLayout.setLayoutParams(params);

                    if(data.getUrl()==null){
//                    ImageLoader.load(mContext,R.drawable.head_holder,imageView);
                        imageView.setImageResource(R.drawable.head_holder);
                    }else{
                        ImageLoader.loadRoundCenterCropImg(mContext, data.getUrl(), imageView);
                    }
                }

                @Override
                protected int getItemLayoutId() {
                    return R.layout.recycler_item_school_rvitem;
                }
            };
            recyclerView.setAdapter(adapter);


            adapter.setOnItemClickListener(new OnItemClickListeners<SchoolItem>() {
                @Override
                public void onItemClick(ViewHolder viewHolder, SchoolItem data2, int position) {
                    clickItem(data);
                }
            });
        }

    }

    private void clickItem(final SchoolItem item) {
        String type = item.getType();
        if (isNews(type)) {
//            //新闻
            mContext.startActivity(getWebViewIntent(item));
        } else if (isDynamic(type)) {
            //动态图片
            if (null == item.getVideoUrl()) {
                Observable.just("")
                        .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        // 图片选择器
                        List<SlideBean> slideBeans = new ArrayList<>();
                        List<String> listUrl = item.getListUrl();
                        for (int i = 0; i < listUrl.size(); i++) {
                            Bitmap bitmap = ImageLoader.load(mContext, listUrl.get(i));
                            SlideBean slideBean = new SlideBean();
                            slideBean.setUrl(listUrl.get(i));
                            slideBean.setWidth(bitmap.getWidth());
                            slideBean.setHeight(bitmap.getHeight());
                            slideBeans.add(slideBean);
                        }
                        Intent intent = new Intent(mContext, SlideActivity.class);
                        intent.putParcelableArrayListExtra(SlideActivity.kDatas, (ArrayList<? extends Parcelable>) slideBeans);
                        intent.putExtra(SlideActivity.kCurrentPosition, 0);// 这个值需要改成点击图片的值
                        mContext.startActivity(intent);
                        return null;
                    }
                })
                        .compose(RxThread.<String>subscribe_Io_Observe_On())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(String s) {

                            }
                        });

            } else {
//视频
                Intent intent = getVideoPlayIntent(item);
                mContext.startActivity(intent);
            }
        } else if (isCookbook(type)) {
            //食谱
        } else if (isEvent(type)) {
            //活动
            Bundle b = new Bundle();
            b.putSerializable("EventModel", item.getObject());
            Intent intent = new Intent(mContext, clzz);
            intent.putExtras(b);
            ((BaseLibActivity)mContext).startActivityForResult(intent,777);
        }
    }

    //新闻
    private boolean isNews(String type) {
        return "1".equals(type);
    }

    /**
     * 动态
     *
     * @param type
     * @return
     */
    private boolean isDynamic(String type) {
        return "2".equals(type);
    }

    /**
     * 食谱
     *
     * @param type
     * @return
     */
    private boolean isCookbook(String type) {
        return "3".equals(type);
    }

    /**
     * 活动
     *
     * @param type
     * @return
     */
    private boolean isEvent(String type) {
        return "4".equals(type);
    }

    public abstract Intent getWebViewIntent(SchoolItem item);


    public abstract Intent getVideoPlayIntent(SchoolItem item);
}
