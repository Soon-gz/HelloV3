package com.hellobaby.library.ui.msg.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.hellobaby.library.R;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;

/**
 * Created by zwj on 2016/11/29.
 * description : 消息详情，系统消息详情
 * TODO subMsgTitle需要特殊处理
 */

public abstract class BaseMsgDetailActivity<T> extends BaseLibTitleActivity<T> {

    /**
     * 消息标题
     */
    protected TextView bTvMsgTitle;
    /**
     * 消息副标题
     */
    protected TextView bTvSubMsgTitle;
    /**
     * 消息内容
     */
    protected TextView bTvMsgContent;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_msg_detail;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        setBtnLeftClickFinish();
        bTvMsgTitle = (TextView) findViewById(R.id.libMsgDetail_tv_title);
        bTvSubMsgTitle = (TextView) findViewById(R.id.libMsgDetail_tv_subTitle);
        bTvMsgContent = (TextView) findViewById(R.id.libMsgDetail_tv_content);
        bTvMsgContent.setMovementMethod(LinkMovementMethod.getInstance());
    }


    @Override
    public void showData(T t) {

    }

    /**
     * 修改副标题的样式
     *
     * @param prefix 收件人：or发件人：
     * @param content 全部内容
     */
    protected void setInOutBoxMsgDetailSubTitle(String prefix, String content) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        //收件人，发件人
        ForegroundColorSpan spanBlack = new ForegroundColorSpan(Color.BLACK);
        //后面的时间
        ForegroundColorSpan spanGray = new ForegroundColorSpan(getResources().getColor(android.R.color.darker_gray));
        int length = prefix.length();
        builder.setSpan(spanBlack, 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(spanGray, length, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        bTvSubMsgTitle.setText(builder);
    }
}
