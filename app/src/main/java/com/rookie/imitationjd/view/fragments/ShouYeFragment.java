package com.rookie.imitationjd.view.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.rookie.imitationjd.R;
import com.rookie.imitationjd.adapter.SYAdapter;
import com.rookie.imitationjd.bean.SYBannerBean;
import com.rookie.imitationjd.bean.SYNavBean;
import com.rookie.imitationjd.model.SYModel;
import com.rookie.imitationjd.presenter.SYPresenter;
import com.rookie.imitationjd.qrcode.android.CaptureActivity;
import com.rookie.imitationjd.qrcode.bean.ZxingConfig;
import com.rookie.imitationjd.qrcode.common.Constant;
import com.rookie.imitationjd.view.activities.MapActivity;
import com.rookie.imitationjd.view.activities.SearchActivity;
import com.rookie.imitationjd.view.customview.MySearchView;
import com.rookie.imitationjd.view.interfaces.ISYView;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 暗夜 on 2018/5/21.
 */

public class ShouYeFragment extends Fragment implements ISYView, View.OnClickListener {

    private static final String TAG ="ShouYeFragment---" ;
    private XRecyclerView xRcy;
    private View inflate;
    List<String> lunbolist=new ArrayList<>();
    private List<SYNavBean.DataBean> datas;
    private SYAdapter SYAdapter;
    Handler handler=new Handler();
    private View view;
    private int mDistanceY;
    private EditText search_content;

    //二维码扫描功能
    private LinearLayout scan;
    private int REQUEST_CODE_SCAN = 111;
    private LinearLayout map_location;
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_layout_sy, container, false);

        initView();
        xRcy = view.findViewById(R.id.xrcy);
        SYPresenter syPresenter = new SYPresenter();
        syPresenter.showBannerToView(new SYModel(),this);
        syPresenter.showNavToView(new SYModel(),this);
        inflate = View.inflate(getContext(), R.layout.fg_sy_banner, null);
        xRcy.addHeaderView(inflate);


        map_location = view.findViewById(R.id.map_location);
        map_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
            }
        });


        return view;

    }

    private void initView() {
        //搜索框中的EditText控件
        search_content = view.findViewById(R.id.search_content);
        //设置首页自定义搜索框的EditText中的hint字体的大小
        SpannableString ss = new SpannableString("京东超级品牌日");//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(15, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        search_content.setHint(new SpannedString(ss));

        search_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        //二维码扫描功能
        scan = view.findViewById(R.id.scan);
        scan.setOnClickListener(this);
    }


    @Override
    public void showSYNavData(List<SYNavBean.DataBean> data) {

        Log.d(TAG, "showSYNavData: "+data);
        ShouYeFragment.this.datas=data;
    }

    @Override
    public void showSYBannerData(SYBannerBean syBannerBean) {
        List<SYBannerBean.DataBean> data1 = syBannerBean.getData();
        for (int i = 0; i < data1.size(); i++) {
            lunbolist.add(data1.get(i).getIcon());
        }
        XBanner xBanner = inflate.findViewById(R.id.xbanner);
        xBanner.setData(lunbolist, null);
        xBanner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(getContext()).load(lunbolist.get(position)).into((ImageView) view);
            }

        });

        Log.d(TAG, "showSYBannerData: "+ShouYeFragment.this.datas);
        SYAdapter = new SYAdapter(getActivity(), ShouYeFragment.this.datas, syBannerBean);
        xRcy.setAdapter(SYAdapter);
        xRcy.setLayoutManager(new LinearLayoutManager(getActivity()));
        xRcy.setLoadingMoreEnabled(false);
        xRcy.setLoadingListener(new XRecyclerView.LoadingListener() {

            private FrameLayout fg;

            @Override
            public void onRefresh() {
                fg = view.findViewById(R.id.fg);
                fg.setVisibility(View.GONE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fg.setVisibility(View.VISIBLE);
                        xRcy.refreshComplete();
                    }
                },1000);
            }

            @Override
            public void onLoadMore() {
                xRcy.loadMoreComplete();

            }
        });

        xRcy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //滑动的距离
                mDistanceY += dy;
                //toolbar的高度
                FrameLayout f1_frag = view.findViewById(R.id.fg);
                int toolbarHeight = f1_frag.getBottom();

                //当滑动的距离 <= toolbar高度的时候，改变Toolbar背景色的透明度，达到渐变的效果
                if (mDistanceY <= 300) {
                    float scale = (float) mDistanceY / 300;
                    float alpha = scale * 255;
                    f1_frag.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));

                } else {
                    //将标题栏的颜色设置为完全不透明状态
                    f1_frag.setBackgroundResource(R.color.sy_title_color);

                }
            }
        });

    }

    //二维码点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scan:

                checkPermission();
                break;
        }

    }

    private void QRcode() {

        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
        ZxingConfig config = new ZxingConfig();
        config.setShowbottomLayout(true);//底部布局（包括闪光灯和相册）
        config.setPlayBeep(true);//是否播放提示音
        config.setShake(true);//是否震动
        config.setShowAlbum(true);//是否显示相册
        config.setShowFlashLight(true);//是否显示闪光灯
        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Toast.makeText(getActivity(), content + "", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //动态申请权限
    public void checkPermission() {
        /**
         * 第 1 步: 检查是否有相应的权限
         */
        boolean isAllGranted = checkPermissionAllGranted(
                new String[] {

                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE

                }
        );
        // 如果这3个权限全都拥有, 则直接执行读取短信代码
        if (isAllGranted) {
            QRcode();
            toast("所有权限已经授权！");
            return;
        }

        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(getActivity(),
                new String[] {

                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                MY_PERMISSION_REQUEST_CODE
        );
    }
    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                toast("检查权限");
                return false;
            }
        }
        return true;
    }

    /**
     * 第 3 步: 申请权限结果返回处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行读取短信代码
                QRcode();


            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
//                openAppDetails();
                toast("需要授权！");

            }
        }
    }
    public void toast(String content){
        Toast.makeText(getActivity(),content,Toast.LENGTH_SHORT).show();
    }
}


