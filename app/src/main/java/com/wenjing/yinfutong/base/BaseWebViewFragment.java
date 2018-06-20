package com.wenjing.yinfutong.base;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ZoomButtonsController;


public class BaseWebViewFragment extends BaseFragment {


    protected WebViewClient mWebviewClient = new WebViewClient() {
        private boolean receivedError = false;

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//            super.onReceivedSslError(view, handler, error);
            handler.proceed();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            receivedError = false;
            onWebViewStart();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return shouldOverrideUrl(view, url);
//            return true;//true表示是按照程序来执行，false表示webView处理url是在webView内部执行
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            onWebViewSuccess();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
//            TLog.error("html5报错", errorCode + "错误码");
            receivedError = true;
            onWebViewFailure();
        }
    };

    protected boolean shouldOverrideUrl(WebView view, String url) {
        return true;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void initWebView(WebView webView) {

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);// 设置是否支持缩放
        webSettings.setSaveFormData(false);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setLoadsImagesAutomatically(true);    //支持自动加载图片
        webSettings.setUseWideViewPort(true);    //设置webview推荐使用的窗口，使html界面自适应屏幕
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAppCacheEnabled(true);
        String ua = webSettings.getUserAgentString();
//        TLog.error("原始useragent", ua);
//        webSettings.setUserAgentString(ua + "/" + UserAgent.getUserAgent(AppContext.instance()));//設置user_agent
//        TLog.error("拼接后useragent", webSettings.getUserAgentString());
//        TLog.error("opopopopopopo===========", String.valueOf(webSettings.getUserAgentString().lastIndexOf("silver.fox")));
        webSettings.setSaveFormData(true);    //设置webview保存表单数据
        webSettings.setSavePassword(true);    //设置webview保存密码
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);    //设置中等像素密度，medium=160dpi
        webSettings.setSupportMultipleWindows(true);
        webView.setLongClickable(true);
        webView.setScrollbarFadingEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setDrawingCacheEnabled(true);

        webView.requestFocus();
        webView.setWebViewClient(mWebviewClient);
        int sysVersion = Build.VERSION.SDK_INT;
        if (sysVersion >= 11) {
            webSettings.setDisplayZoomControls(false);//隐藏缩放按钮
        } else {
            ZoomButtonsController zbc = new ZoomButtonsController(webView);
            zbc.getZoomControls().setVisibility(View.GONE);
        }
    }

    protected void refresh() {
    }


    protected void onWebViewSuccess() {
    }

    protected void onWebViewFailure() {
    }

    protected void onWebViewStart() {

    }

    @Override
    protected void initView(View view) {

    }
}
