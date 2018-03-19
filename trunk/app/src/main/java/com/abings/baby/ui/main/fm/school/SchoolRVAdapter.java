package com.abings.baby.ui.main.fm.school;

import android.content.Context;
import android.content.Intent;

import com.abings.baby.ui.Information.WebViewActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.ui.main.fm.school.BaseSchoolRVAdapter;
import com.hellobaby.library.ui.main.fm.school.SchoolItem;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */

public class SchoolRVAdapter extends BaseSchoolRVAdapter {
    public SchoolRVAdapter(Context context, List<SchoolItem> datas, Class clzz) {
        super(context, datas, clzz);
    }

    @Override
    public Intent getWebViewIntent(SchoolItem item) {
        Intent intent = new Intent(mContext,WebViewActivity.class);
        intent.putExtra("favoriteType","1");
        intent.putExtra("favoriteFavoriteid",item.getId());
        intent.putExtra("data",item);
        intent.putExtra(WebViewActivity.kWebUrl, Const.URL_news+item.getNewsUrl());
        return intent;
    }

    @Override
    public Intent getVideoPlayIntent(SchoolItem item) {
        Intent intent = new Intent(mContext, SchoolVideoPlayActivity.class);
        intent.putExtra(SchoolVideoPlayActivity.kVideoUrl, Const.URL_dynamicSmallVideo + item.getVideoUrl());
        intent.putExtra(SchoolVideoPlayActivity.kVideoImageUrl,item.getVideoImageUrl());//第一帧
        intent.putExtra(SchoolVideoPlayActivity.kVideoTitle,item.getName());//内容

        intent.putExtra(SchoolVideoPlayActivity.kVideoName, item.getVideoUrl());
        intent.putExtra(SchoolVideoPlayActivity.kVideoFile, Const.videoPath);
        intent.putExtra(SchoolVideoPlayActivity.kMoreType, SchoolVideoPlayActivity.MORE_TYPE_DYNAMIC);
        return intent;
    }
}
