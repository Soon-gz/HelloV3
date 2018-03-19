package com.abings.baby.teacher.ui.PrizeDraw.prizeRule;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hellobaby.library.ui.webview.BaseWebViewActivity;

public class PrizeRuleActivity extends BaseWebViewActivity {
    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        bIvRight.setVisibility(View.GONE);
        setBtnLeftClickFinish();
    }
}
