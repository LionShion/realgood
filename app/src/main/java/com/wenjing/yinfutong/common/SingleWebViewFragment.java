package com.wenjing.yinfutong.common;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseActivity;
import com.wenjing.yinfutong.base.BaseWebViewFragment;
import com.wenjing.yinfutong.utils.TDevice;

import java.util.ArrayList;

public class SingleWebViewFragment extends BaseWebViewFragment {
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private String url = "";
    private String title = "";
    private String shareSp;
    private String shareContent;
    private ArrayList<String> loadHistoryUrls = new ArrayList<String>();

    public SingleWebViewFragment() {
    }

    private Bundle inputBundle;

    @SuppressLint("ValidFragment")
    public SingleWebViewFragment(Bundle bundle) {
        this.inputBundle = bundle;
    }

    @Override
    protected boolean shouldOverrideUrl(WebView view, String url) {
        if (url.startsWith("tel:")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } else if (url.startsWith("http:") || url.startsWith("https:")) {
            view.loadUrl(url);
        }
        view.loadUrl(url);
        return false;//这里
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null && inputBundle != null) {
            bundle = inputBundle;
        }
        url = bundle.getString(UIHelper.HF_URL_KEY);
        title = bundle.getString(UIHelper.HF_TITLE_KEY);
        //设置数据的操作
        BaseActivity activity = (BaseActivity) getCurActivity();
        activity.setActionBarTitle(title);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.webview_single_page;
    }

    @Override
    protected void initView(View view) {
        mWebView = (WebView) view.findViewById(R.id.webView);
        mProgressBar = (ProgressBar) view.findViewById(R.id.myProgressBar);
        setWebView();
    }


    @JavascriptInterface
    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void setWebView() {
        initWebView(mWebView);
//        mWebView.addJavascriptInterface(getHtmlObject(), "jyApp");
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (View.GONE == mProgressBar.getVisibility()) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                //h5 target为blank时生效
                WebView newWebView = new WebView(getContext());
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                newWebView.setLayoutParams(params);
                WebSettings webSettings = newWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setSupportZoom(false);
                webSettings.setBuiltInZoomControls(false);// 设置是否支持缩放
                webSettings.setSaveFormData(false);
                webSettings.setDefaultTextEncodingName("utf-8");
                webSettings.setLoadsImagesAutomatically(true);    //支持自动加载图片
                webSettings.setUseWideViewPort(true);    //设置webview推荐使用的窗口，使html界面自适应屏幕
                webSettings.setLoadWithOverviewMode(true);
                webSettings.setAppCacheEnabled(true);
                webSettings.setSupportMultipleWindows(true);
                view.addView(newWebView);
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();
                newWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                return true;
            }
        });
        mWebView.loadUrl(url);

    }

    @Override
    protected void onWebViewFailure() {
        super.onWebViewFailure();
        mWebView.loadUrl("file:///android_asset/app_not_fd.html");
    }

    @Override
    protected void onWebViewSuccess() {
        super.onWebViewSuccess();
    }

    @Override
    public boolean onBackPressed() {
        if (mWebView.canGoBack() && TDevice.hasInternet()) {
            mWebView.goBack();
            return false;//以前是true
        }
        return super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}