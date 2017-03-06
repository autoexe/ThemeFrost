package com.serjiosoft.themefrost.video_player;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.serjiosoft.themefrost.BaseActivity;
import com.serjiosoft.themefrost.R;
import com.serjiosoft.themefrost.builder_intent.ActivityIntentBuilder;
import com.serjiosoft.themefrost.builder_intent.PostActivityStarter;

/**
 * Created by autoexec on 02.03.2017.
 */

public class WebViewPlayer extends BaseActivity {

    public static final String M_URL_EXTRA = "mUrl";
    private String mUrl;
    protected WebView mWebPlayer;


    public static class IntentBuilder_ extends ActivityIntentBuilder<IntentBuilder_> {

        private Fragment fragmentSupport;
        private android.app.Fragment fragment;


        public IntentBuilder_(Context context) {
            super(context, WebViewPlayer.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            super(fragment.getActivity(), WebViewPlayer.class);
            this.fragment = fragment;
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            super(fragment.getActivity(), WebViewPlayer.class);
            this.fragmentSupport = fragment;
        }


        @Override
        public PostActivityStarter startForResult(int requestCode) {
            return null;
        }

        public IntentBuilder_ mUrl(String mUrl) {
            return  super.extra(WebViewPlayer.M_URL_EXTRA, mUrl);
        }
    }

    public static IntentBuilder_ intent(Context context) {
        return new IntentBuilder_(context);
    }

    public static IntentBuilder_ intent(android.app.Fragment fragment) {
        return new IntentBuilder_(fragment);
    }

    public static IntentBuilder_ intent(Fragment supportFragment) {
        return new IntentBuilder_(supportFragment);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        injectExtras();
        initImmersiveMode();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_player);

        mWebPlayer = (WebView) findViewById(R.id.wvPlayerWeb_AW);

        startConfig();
    }


    private void initImmersiveMode() {
        getWindow().getDecorView().setSystemUiVisibility(5894);
    }



    private void startConfig() {
        setUpWebViewDefaults(this.mWebPlayer);
        this.mWebPlayer.setOverScrollMode(2);
        this.mWebPlayer.setWebChromeClient(new WebChromeClient());
        this.mWebPlayer.loadUrl(this.mUrl);
    }

    @TargetApi(11)
    private void setUpWebViewDefaults(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT > 11) {
            settings.setDisplayZoomControls(false);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        webView.setWebViewClient(new WebViewClient());
    }

    @Override
    protected void onStop() {
        super.onStop();
        mWebPlayer.destroy();
    }

    private void injectExtras() {
        Bundle extras_ = getIntent().getExtras();
        if (extras_ != null && extras_.containsKey(M_URL_EXTRA)) {
            this.mUrl = extras_.getString(M_URL_EXTRA);
        }
    }
}
