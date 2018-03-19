package com.abings.baby.teacher.ui.PrizeDraw;

import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.abings.baby.teacher.R;

public class PrizeDrawHtmlActivity extends AppCompatActivity {

    WebView mWebView;
    private boolean blockLoadingNetworkImage = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prize_draw);

        initWebView();
    }

    private void initWebView() {

        mWebView=(WebView)findViewById(R.id.webview_prize);
        mWebView.setWebChromeClient(new WebChromeClient());
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
        };
        mWebView.clearCache(true);

        mWebView.setWebViewClient(webViewClient);
        String webUrl = "http://192.168.1.198:8181/PrizeDraw/";
        final WebSettings webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSetting.setAllowFileAccess(true); // 允许访问文件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSetting.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSetting.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        blockLoadingNetworkImage = true;
        webSetting.setSupportZoom(true); // 支持缩放
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
}
