package com.march.webkit.js;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.march.UIKit;
import com.march.webkit.XWebView;


/**
 * CreateAt : 2017/6/27
 * Describe :
 *
 * @author chendong
 */
public class JsBridge implements IJsBridge {

    public static final String TAG = JsBridge.class.getSimpleName();

    private WebView mWebView;
    private Activity mActivity;

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    public void init(XWebView webView, Activity activity) {
        this.mWebView = webView;
        this.mActivity = activity;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 公用方法
    ///////////////////////////////////////////////////////////////////////////
    @JavascriptInterface
    @Override
    public void toast(String msg) {
        UIKit.getUIKitService().toast(msg, false);
    }

    @JavascriptInterface
    @Override
    public void log(String msg) {
        Log.e(TAG, msg);
    }

    @JavascriptInterface
    @Override
    public void finish() {
        mActivity.finish();
    }

    @JavascriptInterface
    @Override
    public void reload() {
        mWebView.reload();
    }

    @JavascriptInterface
    @Override
    public String call(String jsonParam) {
        return null;
    }

    @JavascriptInterface
    @Override
    public boolean openPage(String url, String jsonParam) {
        return false;
    }
}
