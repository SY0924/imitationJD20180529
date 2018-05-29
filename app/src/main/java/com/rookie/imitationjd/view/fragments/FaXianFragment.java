package com.rookie.imitationjd.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.rookie.imitationjd.R;

/**
 * Created by 暗夜 on 2018/5/21.
 */

public class FaXianFragment extends Fragment{

    private WebView web;
    private String url="https://h5.m.jd.com/active/faxian/list/article-list.html";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_layout_fx, null);

        web = view.findViewById(R.id.web);
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        web.loadUrl(url);
        return view;
    }
}
