package com.march.webkit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.march.webkit.js.JsBridge;
import com.march.uikit.R;

/**
 * CreateAt : 2017/11/6
 * Describe :
 * 封装 WebView
 * How to Use:
 * MyWebView webview = (MyWebView)findById(R.id.webview);
 * webview.init(this);
 *
 * @author chendong
 */
public class XWebView extends android.webkit.WebView {

    public static final int WEB_REQ_CODE = 0x123;
    public static final int PROGRESS_HEIGHT = 3;
    public static final String DEF_INVOKE_KEY = "native";

    public XWebView(Context context) {
        this(context, null);
    }

    public XWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private Activity mActivity;

    ProgressBar mProgressBar;
    ValueCallback<Uri[]> mFilePathCallback;

    private void initView() {
        if (isInEditMode())
            return;
        mProgressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, PROGRESS_HEIGHT));
        mProgressBar.setProgressDrawable(ContextCompat.getDrawable(getContext(), R.drawable.webview_progress_drawable));
        mProgressBar.setIndeterminate(false);
        mProgressBar.setProgress(0);
        addView(mProgressBar);
    }

    public void init(Activity activity) {
        mActivity = activity;
        setDefaultDownloadListener();
        setDefaultSettings();
        setDefaultWebClient();
        addJsBridge(new JsBridge(), null);
    }

    /**
     * 加载文件夹中的 html 文件
     *
     * @param filePath 文件路径
     */
    public void loadLocalFile(String filePath) {
        loadUrl("file://" + filePath);
    }

    /**
     * 加载 assert 中的 html 文件
     *
     * @param filePath 文件路径
     */
    public void loadAssertFile(String filePath) {
        loadUrl("file:///android_asset/" + filePath);
    }


    /**
     * 设置为默认的 settings
     */
    private void setDefaultSettings() {
        //WebViewUtil.initWebViewSettings(this);
    }


    /**
     * 设置为默认的 WebViewClient 和 WebChromeClient
     */
    private void setDefaultWebClient() {
        setWebViewClient(new XWebViewClient());
        setWebChromeClient(new XWebChromeClient());
    }

    /**
     * 设置下载监听器，默认使用浏览器下载
     */
    private void setDefaultDownloadListener() {
        setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,
                    String contentDisposition,
                    String mimeType,
                    long contentLength) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String downLoadUrl = url;
                if (!downLoadUrl.contains("http://")) {
                    downLoadUrl = "http://" + downLoadUrl;
                }
                intent.setData(Uri.parse(downLoadUrl));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });
    }

    /**
     * 返回键逻辑
     *
     * @return 是不是截断返回事件
     */
    public boolean onBackPressed() {
        if (canGoBack()) {
            goBack();
            return true;
        }
        return false;
    }

    /**
     * 接收结果
     *
     * @param requestCode req code
     * @param resultCode  rst code
     * @param data        intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != WEB_REQ_CODE)
            return;
        Uri[] uris;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            uris = WebChromeClient.FileChooserParams.parseResult(resultCode, data);
        } else {
            uris = new Uri[]{data.getData()};
        }
        mFilePathCallback.onReceiveValue(uris);
        mFilePathCallback = null;
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    public void addJsBridge(JsBridge jsBridge, String name) {
        getSettings().setJavaScriptEnabled(true);
        jsBridge.init(this, mActivity);
        if (TextUtils.isEmpty(name)) {
            name = DEF_INVOKE_KEY;
        }
        addJavascriptInterface(jsBridge, name);
    }

    @Override
    public void setWebChromeClient(WebChromeClient client) {
        if (client instanceof XWebChromeClient) {
            ((XWebChromeClient) client).init(mActivity, this);
        }
        super.setWebChromeClient(client);
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        if (client instanceof XWebViewClient) {
            ((XWebViewClient) client).init(mActivity, this);
        }
        super.setWebViewClient(client);
    }
}
