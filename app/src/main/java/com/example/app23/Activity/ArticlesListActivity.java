package com.example.app23.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.example.app23.R;

public class ArticlesListActivity extends OptionMenuActivity {

    private WebView webView;
    final String URL_ARTICLES_LIST = "https://www.yourdj.fr/tags/articles/toulouse/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        // DISABLE TILE TOOLBAR
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // loadingWebView();
        loadArticlesWebView();
    }

    private void loadArticlesWebView() {
        // webView.getSettings().setJavaScriptEnabled(true);
        webView = (WebView) findViewById(R.id.WebViewArticles);
        webView.loadUrl(URL_ARTICLES_LIST);
    }

    //------------
    // OPTION MENU
    //------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    //------------
    // WEB RESIZER
    //------------
    private class WebViewResizer {
        @JavascriptInterface
        public void processHeight(String height) {
            // height is in DP units. Convert it to PX if you are adjusting the WebView's height.
            // height could be 0 if WebView visibility is Visibility.GONE.
            // If changing the WebView height, do it on the main thread!
        }
    }

}