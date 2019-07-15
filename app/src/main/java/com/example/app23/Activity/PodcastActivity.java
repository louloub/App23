package com.example.app23.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.app23.R;

public class PodcastActivity extends OptionMenuActivity {

    private static final String NAME_FOR_ACTIONBAR = "Podcast";
    private WebView webView;
    final String URL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);

        // loadingWebView();
        loadSoundCloudWebView();
        webView = (WebView) findViewById(R.id.WebViewSoundCloud);

        getSupportActionBar().setTitle(NAME_FOR_ACTIONBAR);
    }

    //------------------------------
    // INTEGRATION SOUNDCLOUD IFRAME
    //------------------------------
    public void loadingWebView ()
    {

        String streamUrl = "https%3A//api.soundcloud.com/playlists/148369466&color=%23a1c332";
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
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.requestLayout();
        webView.getSettings().setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
        webView.loadDataWithBaseURL("", iframe, "text/html", "UTF-8", "");
    }

    public void loadSoundCloudWebView()
    {
        String streamUrl = "https%3A//api.soundcloud.com/playlists/148369466&color=%23a1c332&auto_play=false&hide_related=true&show_comments=true&show_user=true&show_reposts=false&show_teaser=false";
        //String streamUrl = "https://soundcloud.com/yourdjmusic/sets/yourdj-podcast&color=%23a1c332";
        String iframe = "<!DOCTYPE html>" +
                "<html>" +
                    "<body>" +
                        "<iframe width=\"100%\" height=\"1000\" id=\"sc-widget\" scrolling=\"yes\" frameborder=\"yes\" " +
                            "src=\"https://w.soundcloud.com/player/?url="+streamUrl+"\">" +
                        "</iframe>" +
                    "</body>" +
                "</html>";
        WebView webView = (WebView) findViewById(R.id.WebViewSoundCloud);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebViewResizer(), "WebViewResizer");
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return loadUrl(view, url);
            }

            @Override
            public void onPageFinished(WebView webView, String url) {
                // webView.loadUrl("javascript:window.WebViewResizer.processHeight(document.querySelector('body').offsetHeight);");
                super.onPageFinished(webView, url);
            }

            private boolean loadUrl(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                    return false;
                }
                // Otherwise allow the OS to handle it
                else if (url.startsWith("tel:")) {
                    Intent tel = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(tel);
                    return true;
                }
                return true;
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