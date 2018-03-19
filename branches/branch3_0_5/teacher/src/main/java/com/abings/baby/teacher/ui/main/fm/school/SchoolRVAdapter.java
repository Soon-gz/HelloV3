package com.abings.baby.teacher.ui.main.fm.school;

import android.content.Context;
import android.content.Intent;

import com.abings.baby.teacher.ui.Information.WebViewActivity;
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
        intent.putExtra(WebViewActivity.kWebUrl, item.getNewsUrl());
        return intent;
    }
}
