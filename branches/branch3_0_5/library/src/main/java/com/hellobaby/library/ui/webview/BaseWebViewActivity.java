package com.hellobaby.library.ui.webview;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hellobaby.library.R;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;

import butterknife.BindView;

/**
 * Created by zwj on 2016/11/7.
 * description :纯网页
 */

public class BaseWebViewActivity extends BaseLibTitleActivity {
    public static final String kWebUrl = "webUrl";
    WebView mWebView;

    private boolean blockLoadingNetworkImage = false;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        setBtnRightDrawableRes(R.drawable.title_more_black);
        setBtnLeftClickFinish();
        mWebView=(WebView)findViewById(R.id.webview_wv);
        mWebView.setWebChromeClient(new WebChromeClient());
        WebViewClient webViewClient = new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }
        };
        mWebView.setWebViewClient(webViewClient);
        String webUrl = getIntent().getStringExtra(kWebUrl);
        final WebSettings webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSetting.setAllowFileAccess(true); // 允许访问文件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setBlockNetworkImage(true);
        blockLoadingNetworkImage = true;
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress >= 100) {
//                    dissmissLoading();
                    if (blockLoadingNetworkImage) {
                        webSetting.setBlockNetworkImage(false);
                        blockLoadingNetworkImage = false;
                    }
                }
            }
        });
        mWebView.loadUrl(webUrl);
    }

    @Override
    public void showData(Object o) {

    }
}
