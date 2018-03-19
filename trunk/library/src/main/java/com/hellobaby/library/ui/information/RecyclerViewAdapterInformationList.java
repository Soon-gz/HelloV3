package com.hellobaby.library.ui.information;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hellobaby.library.Const;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.InformationModel;
import com.hellobaby.library.ui.webview.BaseWebViewActivity;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.utils.KeyWordUtils;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.utils.ScreenUtils;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RecyclerViewAdapterInformationList extends BaseAdapter<InformationModel> {
    private InformationRVAdapter rvAdapter;
    private ArrayList<String> imagePaths;
    private final int imageWidth;
    private final int imagePadding;
    protected String keyWords = "";

    public RecyclerViewAdapterInformationList(Context context, List<InformationModel> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        imageWidth = (ScreenUtils.getScreenWidth(mContext) - ScreenUtils.dip2px(mContext, 75 + 45)) / 3;
        imagePadding = ScreenUtils.dip2px(mContext, 1.5f);
    }

    @Override
    protected void convert(ViewHolder holder, final InformationModel data) {
        imagePaths = new ArrayList<>();
        ImageView head_im = holder.getView(R.id.libMsgList_civ_icon);
        RecyclerView recyclerView = holder.getView(R.id.recyclerView_information);
        ImageView im = holder.getView(R.id.im_information);
        ImageView implay = holder.getView(R.id.video_play);
        TextView textView = holder.getView(R.id.libMsgList_tv_subject);
        TextView from = holder.getView(R.id.information_from);
        RelativeLayout rl = holder.getView(R.id.rl_information);
        if (null == data.getAssistImageurls()) {
            //咨询收藏
            from.setText("来自于" + data.getTeacherName() + " " + DateUtil.getDescriptionTimeFromTimestamp(data.getCreateTime()));

            implay.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            String images[] = data.getNewInfoImageurls().split(",");
            Collections.addAll(imagePaths, images);
            if (images.length == 1){
                im.setVisibility(View.VISIBLE);
                rl.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                ImageLoader.loadRoundCenterCropImg(mContext, images[0], im);

            }else {
                im.setVisibility(View.GONE);
                rl.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                rvAdapter = new InformationRVAdapter(this.mContext, imagePaths);
                rvAdapter.setImagePadding(imagePadding);
                rvAdapter.setImageWidth(imageWidth);
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
                recyclerView.setAdapter(rvAdapter);

            }

            ImageLoader.loadHeadTarget(mContext, data.getHeadImageurlAbs(), head_im);


//            rvAdapter.setOnItemClickListener(new OnItemClickListeners<String>() {
//                @Override
//                public void onItemClick(ViewHolder viewHolder, String str, int position) {
//                    clickitemInfomation(data);
//                }
//            });
            setOnItemClickListener(new OnItemClickListeners<InformationModel>() {
                @Override
                public void onItemClick(ViewHolder viewHolder, InformationModel data, int position) {
                    clickitemInfomation(data);
                }
            });
        } else  {
            //课堂助手
            from.setText("来自于" + data.getCreaterName() + " " + DateUtil.getDescriptionTimeFromTimestamp(data.getCreateTime()));
            implay.setVisibility(View.GONE);
            String images[] = data.getAssistImageurls().split(",");
            if (images.length == 1){
                im.setVisibility(View.VISIBLE);
                rl.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                ImageLoader.loadRoundCenterCropImg(mContext,  Const.URL_ClassRoom + images[0], im);

            }else {
                im.setVisibility(View.GONE);
                rl.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                for (int i = 0; i < images.length; i++) {
                    images[i] = Const.URL_ClassRoom + images[i];
                }
                Collections.addAll(imagePaths, images);

                rvAdapter = new InformationRVAdapter(this.mContext, imagePaths);
                rvAdapter.setImagePadding(imagePadding);
                rvAdapter.setImageWidth(imageWidth);
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
                recyclerView.setAdapter(rvAdapter);
                rvAdapter.setOnItemClickListener(new OnItemClickListeners<String>() {
                    @Override
                    public void onItemClick(ViewHolder viewHolder, String str, int position) {
                        clickItemClassAssistant(data);
                    }
                });
            }

            ImageLoader.loadHeadTarget(mContext, data.getHeadImageurlAbs(), head_im);
//            ImageLoader.loadRoundCenterCropImg(mContext, data.getContent(), im);1
//            ImageLoader.loadRoundCenterCropClassAssistantImg(mContext, Const.URL_ClassRoom + data.getAssistImageurls().split(",")[0], im);


            setOnItemClickListener(new OnItemClickListeners<InformationModel>() {
                @Override
                public void onItemClick(ViewHolder viewHolder, InformationModel data, int position) {
                    clickItemClassAssistant(data);
                }
            });
        }
//        else {
//            from.setText("来自于" + data.getTeacherName() + " " + DateUtil.getDescriptionTimeFromTimestamp(data.getCreateTime()));
//            rl.setVisibility(View.VISIBLE);
//            im.setVisibility(View.VISIBLE);
//            implay.setVisibility(View.VISIBLE);
//            ImageLoader.loadHeadTarget(mContext, data.getHeadImageurlAbs(), head_im);
//            ImageLoader.loadRoundCenterCropImg(mContext, data.getContent(), im);
//            setOnItemClickListener(new OnItemClickListeners<InformationModel>() {
//                @Override
//                public void onItemClick(ViewHolder viewHolder, InformationModel data, int position) {
//                    clickitemInfomation(data);
//                }
//            });
//        }
        if (!keyWords.equals("")) {
            textView.setText(KeyWordUtils.matcherSearchTitle(data.getTitle(), keyWords));
        } else {
            textView.setText(data.getTitle());
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.librecyler_item_informationlist;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    private Intent mIntent;

    private void clickitemInfomation(InformationModel data) {
        mIntent.putExtra("favoriteType", "2");
        mIntent.putExtra("favoriteFavoriteid", data.getNewsinfoId());
        mIntent.putExtra(BaseWebViewActivity.kWebUrl, data.getDetailsUrl());
        mIntent.putExtra("infromationdata", data);
        mIntent.putExtra("favoriteId", data.getFavoriteId());
        mContext.startActivity(mIntent);
    }

    private void clickItemClassAssistant(InformationModel data) {
        mIntent.putExtra("favoriteType", "3");
        mIntent.putExtra("favoriteFavoriteid", data.getAssistId());
        mIntent.putExtra(BaseWebViewActivity.kWebUrl, data.getDetailsUrl());
        mIntent.putExtra("infromationdata", data);
        mIntent.putExtra("favoriteId", data.getFavoriteId());
        mContext.startActivity(mIntent);
    }

    /**
     * 设置需要跳转的intent
     *
     * @param intent
     */
    public void setWebViewIntent(Intent intent) {
        mIntent = intent;
    }
}
