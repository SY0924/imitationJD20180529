package com.rookie.imitationjd.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.hjm.bottomtabbar.BottomTabBar;
import com.rookie.imitationjd.R;
import com.rookie.imitationjd.utils.ThemeChangeUtil;
import com.rookie.imitationjd.view.fragments.FaXianFragment;
import com.rookie.imitationjd.view.fragments.FenLeiFragment;
import com.rookie.imitationjd.view.fragments.GouWuCheFragment;
import com.rookie.imitationjd.view.fragments.ShouYeFragment;
import com.rookie.imitationjd.view.fragments.WoDeFragment;

public class MainActivity extends AppCompatActivity {


    private BottomTabBar tabBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeChangeUtil.changeTheme(this);
        //去掉标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabBar= (BottomTabBar) findViewById(R.id.bottom_tab_bar);
        //这个init ( getSupportFragmentManager() )方法一定要第一个调用，没有//这个初始化，后边什么也做不了。
        tabBar.init(getSupportFragmentManager())
                .addTabItem("首页",R.drawable.a1, R.drawable.a9, ShouYeFragment.class)
                .addTabItem("分类", R.drawable.a2, R.drawable.a7,FenLeiFragment.class)
                .addTabItem("发现",R.drawable.a4, R.drawable.a5, FaXianFragment.class)
                .addTabItem("购物车",  R.drawable.a8,  R.drawable.a6,GouWuCheFragment.class)
                .addTabItem("我的",R.drawable.a10, R.drawable.a3, WoDeFragment.class)
                .isShowDivider(true);
    }
}
