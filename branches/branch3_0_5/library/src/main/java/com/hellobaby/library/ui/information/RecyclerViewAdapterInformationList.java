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
import com.hellobaby.library.utils.ScreenUtils;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.tencent.open.utils.Global.getContext;

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
        if (null == data.getContent()) {
            rl.setVisibility(View.GONE);
            im.setVisibility(View.GONE);
            implay.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            String images[] = data.getNewInfoImageurls().split(",");
            Collections.addAll(imagePaths, images);
            rvAdapter = new InformationRVAdapter(this.mContext, imagePaths);
            rvAdapter.setImagePadding(imagePadding);
            rvAdapter.setImageWidth(imageWidth);
            ImageLoader.loadHeadTarget(mContext, Const.URL_TeacherHead + data.getHeadImageurl(), head_im);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            recyclerView.setAdapter(rvAdapter);
            rvAdapter.setOnItemClickListener(new OnItemClickListeners<String>() {
                @Override
                public void onItemClick(ViewHolder viewHolder, String str, int position) {
                    //TODO
                    clickitem(data);
                }
            });
        } else {
            rl.setVisibility(View.VISIBLE);
            im.setVisibility(View.VISIBLE);
            implay.setVisibility(View.VISIBLE);
            ImageLoader.loadHeadTarget(mContext, Const.URL_TeacherHead + data.getHeadImageurl(), head_im);
            ImageLoader.loadRoundCenterCropImg(mContext, data.getContent(), im);
        }
        if (!keyWords.equals("")) {
            textView.setText(KeyWordUtils.matcherSearchTitle(data.getTitle(), keyWords));
//            from.setText(KeyWordUtils.matcherSearchTitle("来自于"+data.getTeacherName()+" "+ DateUtil.getDescriptionTimeFromTimestamp(data.getCreateTime()),keyWords));
        } else {
            textView.setText(data.getTitle());
//            from.setText("来自于"+data.getTeacherName()+" "+ DateUtil.getDescriptionTimeFromTimestamp(data.getCreateTime()));
        }
        from.setText("来自于" + data.getTeacherName() + " " + DateUtil.getDescriptionTimeFromTimestamp(data.getCreateTime()));

        setOnItemClickListener(new OnItemClickListeners<InformationModel>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, InformationModel data, int position) {
                clickitem(data);
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.librecyler_item_informationlist;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    private Intent mIntent;
    private void clickitem(InformationModel data) {
        mIntent.putExtra("favoriteType","2");
        mIntent.putExtra("favoriteFavoriteid",data.getNewsinfoId());
        mIntent.putExtra(BaseWebViewActivity.kWebUrl, data.getDetailsUrl());
        mIntent.putExtra("infromationdata",data);
        mIntent.putExtra("favoriteId",data.getFavoriteId());
        mContext.startActivity(mIntent);
    }

    /**
     * 设置需要跳转的intent
     * @param intent
     */
    public void setWebViewIntent(Intent intent) {
        mIntent = intent;
    }
}
