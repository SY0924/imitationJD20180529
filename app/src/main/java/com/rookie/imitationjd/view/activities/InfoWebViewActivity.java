package com.rookie.imitationjd.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.rookie.imitationjd.R;

public class InfoWebViewActivity extends AppCompatActivity {

    private WebView info_webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_web_view);

        info_webView = (WebView) findViewById(R.id.info_webView);
        Intent intent = getIntent();
        String webUrl = intent.getStringExtra("webUrl");

        WebSettings webSettings = info_webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        info_webView.loadUrl(webUrl);
    }
}
