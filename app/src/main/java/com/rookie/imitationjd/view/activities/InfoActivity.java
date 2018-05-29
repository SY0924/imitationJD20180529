package com.rookie.imitationjd.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rookie.imitationjd.R;
import com.rookie.imitationjd.bean.AddCarBean;
import com.rookie.imitationjd.bean.InfoBean;
import com.rookie.imitationjd.presenter.InfoPresenter;
import com.rookie.imitationjd.view.interfaces.IInfoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoActivity extends AppCompatActivity implements IInfoView {

    private static final String TAG = "InfoActivity----";
    @BindView(R.id.info_img)
    ImageView mInfoImg;
    @BindView(R.id.info_title)
    TextView mInfoTitle;
    @BindView(R.id.info_more)
    TextView mInfoMore;
    @BindView(R.id.info_oldPrice)
    TextView mInfoOldPrice;
    @BindView(R.id.info_price)
    TextView mInfoPrice;
    @BindView(R.id.info_num)
    TextView mInfoNum;
    @BindView(R.id.info_add)
    Button mInfoAdd;
    @BindView(R.id.info_back)
    ImageView mInfoBack;
    private InfoPresenter infoPresenter;
    private String pid;
    private String detailUrl;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        ButterKnife.bind(this);
        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        Log.d(TAG, "onCreate: " + pid);
        //商品详情，添加购物车 持有的presenter
        infoPresenter = new InfoPresenter(this);
        infoPresenter.infos(pid);

        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        uid = preferences.getString("uid", "");


    }
//商品详情
    @Override
    public void showInfoData(InfoBean infoBean) {
        InfoBean.DataBean data = infoBean.getData();

        if (infoBean != null) {

            String images = data.getImages();
            String[] split = images.split("\\|");
            Glide.with(InfoActivity.this).load(split[0]).into(mInfoImg);
            mInfoTitle.setText(data.getTitle());
            detailUrl = data.getDetailUrl();

            mInfoOldPrice.setText("原价:" + data.getPrice());
            mInfoOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰             mInfoPrice.setText("优惠价:" + data.getBargainPrice());
            mInfoNum.setText("销量：" + data.getSalenum());
            mInfoMore.setText(Html.fromHtml("<a href='detailUrl'>京东自营，闪电配送，更多惊喜，快用手指戳一下>></a>"));


        }


    }
//添加购物车
    @Override
    public void getAddCarData(AddCarBean addCarBean) {
        String code = addCarBean.getCode();
        if (addCarBean != null) {
            if ("0".equals(code)) {
                SharedPreferences preferences = getSharedPreferences("user",MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putBoolean("isEmpty",false);
                edit.commit();
                Toast.makeText(InfoActivity.this, addCarBean.getMsg().toString(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(InfoActivity.this, addCarBean.getMsg().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick({R.id.info_add, R.id.info_more, R.id.info_back})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.info_add:
                SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                boolean isLogin = preferences.getBoolean("isLogin", false);
                if(isLogin){
                    infoPresenter.addCarParams(uid,pid);
                }else{
                    Toast.makeText(InfoActivity.this, "用户未登录或用户id不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.info_more:
               Intent intent=new Intent(InfoActivity.this,InfoWebViewActivity.class);
                intent.putExtra("webUrl",detailUrl);
                startActivity(intent);
                break;
            case R.id.info_back:
                finish();
                break;
        }
    }

    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        infoPresenter.onDestory();
    }
}
