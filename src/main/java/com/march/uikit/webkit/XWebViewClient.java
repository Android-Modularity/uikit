package com.march.uikit.webkit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.march.common.utils.CheckUtils;


/**
 * CreateAt : 2017/11/6
 * Describe :
 *
 * @author luyuan
 */
public class XWebViewClient extends WebViewClient {

    private XWebView mMyWebView;
    private Activity mActivity;

    /*default*/ void init(Activity activity, XWebView myWebView) {
        mMyWebView = myWebView;
        mActivity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (!handleBySystemIntent(url)) {
            view.loadUrl(url);
        }
        return true;
    }

    private boolean handleBySystemIntent(String link) {
        try {
            String url = link.replace("//","");
            Uri uri = Uri.parse(url);
            String scheme = uri.getScheme();
            if (CheckUtils.isEmpty(scheme))
                return false;
            if (scheme.startsWith("tel")
                    || scheme.startsWith("sms")
                    || scheme.startsWith("mailto")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                mActivity.startActivity(intent);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        mMyWebView.mProgressBar.setVisibility(View.GONE);
    }
}
