package com.example.app23;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PodcastActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);

        webView = (WebView) findViewById(R.id.WebViewSoundCloud);

        // loadingWebView();
        loadSoundCloudWebView();
    }

    public void loadingWebView ()
    {
        //------------------------------
        // INTEGRATION SOUNDCLOUD IFRAME
        //------------------------------

        // <iframe width="100%" height="450" scrolling="no" frameborder="no" allow="autoplay" src="https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/playlists/148369466&color=%23a1c332&auto_play=false&hide_related=false&show_comments=true&show_user=true&show_reposts=false&show_teaser=true"></iframe>
        // <iframe allow="autoplay" src="https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/playlists/148369466&color=%23a1c332&auto_play=false&hide_related=false&show_comments=true&show_user=true&show_reposts=false&show_teaser=true"></iframe>
        // initial-scale="1.0" maximum-scale="1.0" user-scalable="no"

        String streamUrl = "https%3A//api.soundcloud.com/playlists/148369466";
        // String streamUrl = "https://soundcloud.com/yourdjmusic/sets/yourdj-podcast";
        String iframe = "<!DOCTYPE html>" +
                "<html>" +
                "<body>" +
                "<iframe width=\"100%\" height=\"1000\" id=\"sc-widget\" scrolling=\"yes\" frameborder=\"yes\" " +
                "src=\"https://w.soundcloud.com/player/?url="+streamUrl+"&amp;auto_play=false&amp; hide_related=false&amp; show_comments=true&amp; show_user=true&amp; show_reposts=false&amp; visual=true\">" +
                "</iframe>" +
                "</body>" +
                "</html>";

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
            }
        });

        webView.setNetworkAvailable(true);
        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.clearView();
        webView.requestLayout();

        // webView.measure(1000, 1000);

        // webView.getSettings().setBuiltInZoomControls(true);

        // webView.getSettings().setDisplayZoomControls(true);

        // webView.setInitialScale(1);

        webView.getSettings().setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
        webView.loadDataWithBaseURL("", iframe, "text/html", "UTF-8", "");
    }

    // color=%23a1c332&amp

    public void loadSoundCloudWebView()
    {
        String streamUrl = "https%3A//api.soundcloud.com/playlists/148369466";
        // String streamUrl = "https://soundcloud.com/yourdjmusic/sets/yourdj-podcast";
        String iframe = "<!DOCTYPE html>" +
                "<html>" +
                "<body>" +
                "<iframe width=\"100%\" height=\"1000\" id=\"sc-widget\" scrolling=\"yes\" frameborder=\"yes\" " +
                "src=\"https://w.soundcloud.com/player/?url="+streamUrl+"&amp;auto_play=false&amp; hide_related=false&amp; show_comments=true&amp; show_user=true&amp;  ;show_reposts=false&amp; visual=true\">" +
                "</iframe>" +
                "</body>" +
                "</html>";
        WebView webView = (WebView) findViewById(R.id.WebViewSoundCloud);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebViewResizer(), "WebViewResizer");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String url) {
                webView.loadUrl("javascript:window.WebViewResizer.processHeight(document.querySelector('body').offsetHeight);");
                super.onPageFinished(webView, url);
            }
        });
        webView.loadDataWithBaseURL("", iframe, "text/html", "UTF-8", "");
    }

    private class WebViewResizer {
    @JavascriptInterface
    public void processHeight(String height) {
        // height is in DP units. Convert it to PX if you are adjusting the WebView's height.
        // height could be 0 if WebView visibility is Visibility.GONE.
        // If changing the WebView height, do it on the main thread!
        }
    }
}