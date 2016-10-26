package com.wangshiqi.pineappleb.ui.activity.focus;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.ui.activity.AbsBaseActivity;

/**
 * Created by dllo on 16/10/26.
 */
public class ShowActivity extends AbsBaseActivity{
    private WebView webView;
    private String url ;
    @Override
    protected int setLayout() {
        return R.layout.activity_show;
    }

    @Override
    protected void initViews() {
        webView =byView(R.id.show_webview);
    }

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        url = intent.getStringExtra("result");
        // 设置不跳转浏览器,在当前Activity上显示
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                return false;
            }
        });
        // 设置WebView加载网页的属性
        WebSettings webSettings = webView.getSettings();
        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        // 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缓存
        webSettings.setAppCacheEnabled(true);
        // 设置缓存模式,一共有四种模式
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 设置缓存路径
//        webSettings.setAppCachePath("");
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);
        // 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(true);
        // 设置默认字体大小
        webSettings.setDefaultFontSize(12);
        webView.loadUrl(url);
    }
}
