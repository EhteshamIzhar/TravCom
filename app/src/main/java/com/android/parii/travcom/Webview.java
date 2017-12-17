package com.android.parii.travcom;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Webview extends AppCompatActivity {

    WebView Wview;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        url=getIntent().getStringExtra("url");

        Wview = (WebView) findViewById(R.id.web);
        Wview.setWebViewClient(new MyBrowser());
        Wview.getSettings().setJavaScriptEnabled(true);
        Wview.loadUrl(url);

    }






    private class MyBrowser extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;

        }
    }
}

