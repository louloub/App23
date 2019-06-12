package com.example.app23;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class PodcastActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);

        webView = (WebView) findViewById(R.id.WebView);

        String streamUrl = "https://soundcloud.com/yourdjmusic/sets/yourdj-podcast";
        String iframe = "<!DOCTYPE html>" +
                "<html>" +
                    "<body>" +
                        "<iframe width=\"100%\" height=\"1000\" id=\"sc-widget\" scrolling=\"no\" frameborder=\"no\" " +
                            "src=\"https://w.soundcloud.com/player/?url="+streamUrl+"&amp;auto_play=false&amp;hide_related=false&amp;show_comments=true&amp;show_user=true&amp;show_reposts=false&amp;visual=true\">" +
                        "</iframe>" +

                        "<script src=\"https://w.soundcloud.com/player/api.js\" type=\"text/javascript\"></script>" +

                        "<script type=\"text/javascript\">" +
                        "(function(){" +
                        "var widgetIframe = document.getElementById(\"sc-widget\")," +
                        "widget       = SC.Widget(widgetIframe);" +
                        "window.widget = widget;" +
                        "window.widget.bind(SC.Widget.Events.READY, function() {" +
                        "Musejam.onReady();" +
                        "});" +
                        "}());" +
                        "</script>" +
                    "</body>" +
                "</html>";
        Log.d("Iframe ", "onCreate: " + iframe);

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
        webView.getSettings().setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
        webView.loadDataWithBaseURL("", iframe, "text/html", "UTF-8", "");



        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //            webView.createWebMessageChannel();
        //        }

        //TEST 1
        /*// WebView
        webView = (WebView) findViewById(R.id.WebView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.yourdj.fr/page/podcasts-yourdj/");*/


        // TEST 2
        /*webView = (WebView) findViewById(R.id.WebView);

        String VIDEO_URL = "<iframe width=\"100%\" height=\"450\" scrolling=\"no\" frameborder=\"no\" allow=\"autoplay\" src=\"https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/playlists/148369466&color=%23a1c332&auto_play=false&hide_related=false&show_comments=true&show_user=true&show_reposts=false&show_teaser=true\"></iframe>";

        String html = "<!DOCTYPE html><html> <head> <meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"target-densitydpi=high-dpi\" /> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> <link rel=\"stylesheet\" media=\"screen and (-webkit-device-pixel-ratio:1.5)\" href=\"hdpi.css\" /></head> <body style=\"background:black;margin:0 0 0 0; padding:0 0 0 0;\"> <iframe id=\"sc-widget " +
                "\" width=\"100%\" height=\"100%\"" + // Set Appropriate Width and Height that you want for SoundCloud Player
                " src=\"" + VIDEO_URL   // Set Embedded url
                + "\" frameborder=\"no\" scrolling=\"no\"></iframe>" +
                "<script src=\"https://w.soundcloud.com/player/api.js\" type=\"text/javascript\"></script> </body> </html> ";

        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadDataWithBaseURL("",html,"text/html", "UTF-8", "");*/


    }
}



