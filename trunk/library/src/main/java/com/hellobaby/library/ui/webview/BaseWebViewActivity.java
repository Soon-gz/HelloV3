package com.hellobaby.library.ui.webview;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hellobaby.library.R;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;
import com.hellobaby.library.widget.ProgressDialogHelper;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;

/**
 * Created by zwj on 2016/11/7.
 * description :纯网页
 */

public class BaseWebViewActivity extends BaseLibTitleActivity {
    public static final String kWebUrl = "webUrl";
    public static final String isHasRightBtn = "rightBtn";
    protected WebView mWebView;

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
        if (getIntent().getStringExtra(isHasRightBtn) != null && "yes".equals(getIntent().getStringExtra(isHasRightBtn))){
            bIvRight.setVisibility(View.GONE);
            bTvTitle.setText("用户服务条款");
        }
        setBtnLeftClickFinish();
        mWebView=(WebView)findViewById(R.id.webview_wv);
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

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);//在2.3上面不加这句话，可以加载出页面，在4.0上面必须要加入，不然出现白屏
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                ProgressDialogHelper.getInstance().hideProgressDialog();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                ProgressDialogHelper.getInstance().showProgressDialog(BaseWebViewActivity.this,"正在加载中...");
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

        webSetting.setBlockNetworkImage(true);

        webSetting.setBuiltInZoomControls(true);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setUseWideViewPort(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setSupportZoom(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.clearCache(true);
        mWebView.clearHistory();

        blockLoadingNetworkImage = true;
        mWebView.getSettings().setBlockNetworkImage(false);//解决图片显示不正常
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 100) {
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
    protected void onPause() {
        super.onPause();
        mWebView.loadUrl("about:blank");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();

        mWebView.setWebChromeClient(null);
        mWebView.setWebViewClient(null);
        mWebView.getSettings().setJavaScriptEnabled(false);
        mWebView.clearCache(true);
    }

    @Override
    public void showData(Object o) {

    }
}
