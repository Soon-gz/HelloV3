package com.abings.baby.teacher.ui.Information;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abings.baby.teacher.ui.publishpicture.VideoPlayActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.InfomationModel;
import com.hellobaby.library.ui.publish.video.BaseVideoPlayActivity;
import com.hellobaby.library.ui.slide.SlideActivity;
import com.hellobaby.library.ui.slide.SlideBean;
import com.hellobaby.library.ui.webview.BaseWebViewActivity;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.utils.StringUtils;
import com.hellobaby.library.widget.ToastUtils;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by ShuWen on 2017/6/2.
 */

public class BaseInfoNewsAdapter extends BaseAdapter<InfomationModel> {


    public BaseInfoNewsAdapter(Context context, List<InfomationModel> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, final InfomationModel data) {

        LogZS.i("每一项数据："+data.toString());

        FrameLayout frameLayout = holder.getView(R.id.item_info_show_fl);
        //发布者头像
        CircleImageView headImage = holder.getView(R.id.lib_news_infomation_head);
        //发布者姓名
        TextView userName = holder.getView(R.id.lib_news_infomation_name);
        //点赞数量
        TextView like_number = holder.getView(R.id.like_number);
        //评论数量
        TextView commension_number = holder.getView(R.id.commension_number);
        //发布日期
        TextView news_info_date = holder.getView(R.id.news_info_date);
        //内容
        TextView news_content = holder.getView(R.id.news_content);
        //标题
        TextView news_title = holder.getView(R.id.news_title);
        // 点赞和评论
        LinearLayout news_zan_ll=holder.getView(R.id.news_ll_zan);
        LinearLayout item_info_head_ll = holder.getView(R.id.item_info_head_ll);

//        item_info_head_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, TeacherInfoPersonalMsg.class);
//                intent.putExtra("other","true");
//                intent.putExtra("userId",data.getCreatorId()+"");
//                mContext.startActivity(intent);
//            }
//        });
        news_zan_ll.setVisibility(View.INVISIBLE);
        news_info_date.setText(DateUtil.getFormatTimeFromTimestamp(data.getCreateTime(),"yyyy年MM月dd日 HH:MM"));
        commension_number.setText(data.getCommentNum()+"");
        like_number.setText(data.getLikeNum()+"");
        if(!data.getContent().equals("")) {
            news_content.setText(data.getContent());
        }
        else{
            news_content.setVisibility(View.GONE);
        }
        if(!data.getTitle().equals("")) {
            news_title.setText(data.getTitle());
        }
        else{
            news_title.setVisibility(View.GONE);
        }
        if (data.getHeadImageurl() != null){
            String headUrl ;
            if (data.getState() == 1){
                headUrl = Const.URL_schoolHead+ data.getHeadImageurl();
            }else {
                headUrl = Const.URL_userHead+ data.getHeadImageurl();
            }
            ImageLoader.loadHeadTarget(mContext,headUrl,headImage);
        }else {
            headImage.setImageResource(R.drawable.head_holder);
        }
        userName.setText(data.getName());

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
            initPicturesSchool(holder,data,frameLayout);
        }

        setOnItemClickListener(new OnItemClickListeners<InfomationModel>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, InfomationModel data, int position) {
                if(!data.getInfoImageurls().equals("")) {
                    Intent intent = new Intent(mContext, InformationWebViewActivity.class);
                    intent.putExtra(BaseWebViewActivity.kWebUrl, data.getDetailsUrl());
                    intent.putExtra("InfomationModel", data);
                    mContext.startActivity(intent);
                }else {
                    return;
                }
            }
        });


    }

    //展示校园图片
    private void initPicturesSchool(ViewHolder holder, final InfomationModel data, FrameLayout frameLayout) {
        String[] images = data.getInfoImageurls().split(",");

        if (images.length == 1){
            //单张图片
            showFrameLayoutChild(0,frameLayout);
            ImageView imageView = holder.getView(R.id.news_image_big);

            ImageLoader.loadRoundCenterCropInfomationImg(mContext,images[0],imageView);

            ImageView item_info_video_play = holder.getView(R.id.item_info_video_play);
            item_info_video_play.setVisibility(View.GONE);

            final List<SlideBean> itemModels = new ArrayList<>();
            SlideBean itemModel = new SlideBean();
            itemModel.setUrl(images[0]);
            itemModels.add(itemModel);

//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(mContext, SlideActivity.class);
//                    intent.putParcelableArrayListExtra(SlideActivity.kDatas, (ArrayList<? extends Parcelable>) itemModels);
//                    intent.putExtra(SlideActivity.kCurrentPosition, 0);// 这个值需要改成点击图片的值
//                    mContext.startActivity(intent);
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

            BaseAdapter<SlideBean> baseAdapter = new BaseAdapter<SlideBean>(mContext,itemModels,false) {
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
                public void onItemClick(ViewHolder viewHolder, SlideBean mdata, int position) {
//                    Intent intent = new Intent(mContext, SlideActivity.class);
//                    intent.putParcelableArrayListExtra(SlideActivity.kDatas, (ArrayList<? extends Parcelable>) itemModels);
//                    intent.putExtra(SlideActivity.kCurrentPosition, 0);// 这个值需要改成点击图片的值
//                    mContext.startActivity(intent);

                    if(!data.getInfoImageurls().equals("")) {
                        Intent intent = new Intent(mContext, InformationWebViewActivity.class);
                        intent.putExtra(BaseWebViewActivity.kWebUrl, data.getDetailsUrl());
                        intent.putExtra("InfomationModel", data);
                        mContext.startActivity(intent);
                    }else {
                        return;
                    }
                }
            });


            RecyclerView recyclerView = holder.getView(R.id.ItemInfo_recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
            recyclerView.setAdapter(baseAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

        }
    }

    //展示视频
    private void initVideo(ViewHolder holder, InfomationModel data, FrameLayout frameLayout) {
        final String videoUrl = data.getImageurl();
        showFrameLayoutChild(0,frameLayout);

        ImageView imageView = holder.getView(R.id.news_image_big);
        //加载单张图片
        ImageLoader.loadRoundCenterCropInfomationImg(mContext,Const.URL_VideoFirstFrame+data.getCoverImageurl(),imageView);

        ImageView item_info_video_play = holder.getView(R.id.item_info_video_play);
        item_info_video_play.setVisibility(View.VISIBLE);

        final AlbumModel albumModel = new AlbumModel();
        albumModel.setUserName(data.getName());
        albumModel.setLastmodifyTime(data.getCreateTime()+"");
        albumModel.setUserId(data.getCreatorId()+"");
        albumModel.setVideoImageUrl(data.getCoverImageurl());
        albumModel.setContent(data.getContent());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = VideoPlayActivity.getIntentVideoPlayNet(Const.videoPath,Const.URL_Video+videoUrl,videoUrl, VideoPlayActivity.MORE_TYPE_ALBUM);
                intent.putExtra(BaseVideoPlayActivity.kMoreType,BaseVideoPlayActivity.MORE_TYPE_INFORMATION);
                intent.putExtra("AlbumModel", albumModel);
                intent.setClass(mContext, VideoPlayActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    //展示图片
    private void initPictures(ViewHolder holder, InfomationModel data,FrameLayout frameLayout) {
        String[] images = data.getImageurl().split(",");

        if (images.length == 1){
            //单张图片
            showFrameLayoutChild(0,frameLayout);
            ImageView imageView = holder.getView(R.id.news_image_big);
            //加载单张图片
            ImageLoader.loadRoundCenterCropInfomationImg(mContext,Const.URL_Album+images[0],imageView);

            ImageView item_info_video_play = holder.getView(R.id.item_info_video_play);
            item_info_video_play.setVisibility(View.GONE);
            final List<SlideBean> itemModels = new ArrayList<>();
            SlideBean itemModel = new SlideBean();
            itemModel.setUrl(Const.URL_Album+images[0]);
            itemModels.add(itemModel);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, SlideActivity.class);
                    intent.putParcelableArrayListExtra(SlideActivity.kDatas, (ArrayList<? extends Parcelable>) itemModels);
                    intent.putExtra(SlideActivity.kCurrentPosition, 0);// 这个值需要改成点击图片的值
                    mContext.startActivity(intent);
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

            BaseAdapter<SlideBean> baseAdapter = new BaseAdapter<SlideBean>(mContext,itemModels,false) {
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
                    Intent intent = new Intent(mContext, SlideActivity.class);
                    intent.putParcelableArrayListExtra(SlideActivity.kDatas, (ArrayList<? extends Parcelable>) itemModels);
                    intent.putExtra(SlideActivity.kCurrentPosition, 0);// 这个值需要改成点击图片的值
                    mContext.startActivity(intent);
                }
            });


            RecyclerView recyclerView = holder.getView(R.id.ItemInfo_recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
            recyclerView.setAdapter(baseAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

//            setOnItemClickListener(new OnItemClickListeners<InfomationModel>() {
//                @Override
//                public void onItemClick(ViewHolder viewHolder, InfomationModel data, int position) {
//                    if (StringUtils.isEmpty(data.getDetailsUrl())){
//                        mContext.startActivity(new Intent(mContext,WebViewActivity.class));
//                    }else {
//                        ToastUtils.showNormalToast(mContext,"详情是网页");
//                    }
//                }
//            });
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
    protected int getItemLayoutId() {
        return R.layout.lib_news_infomation_item;
    }
}
