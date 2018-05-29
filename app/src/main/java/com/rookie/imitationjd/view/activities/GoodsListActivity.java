package com.rookie.imitationjd.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.rookie.imitationjd.R;
import com.rookie.imitationjd.adapter.GoodsAdapter;
import com.rookie.imitationjd.bean.GoodsBean;
import com.rookie.imitationjd.presenter.GoodsPresenter;
import com.rookie.imitationjd.view.interfaces.IGoodsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsListActivity extends AppCompatActivity implements IGoodsView, XRecyclerView.LoadingListener {

    private static final String TAG = "GoodsListActivity---";
    private boolean flag = true;
    private GoodsAdapter adapter;
    private List<GoodsBean.DataBean> data = new ArrayList<>();
    private boolean isFresh = true;
    private boolean isSort= false;
    private int page = 1;
    private int sorts = 0;

    @BindView(R.id.shop_back)
    ImageView mShopBack;
    @BindView(R.id.shop_pic)
    ImageView mShopPic;
    @BindView(R.id.shop_zong)
    RadioButton mShopZong;
    @BindView(R.id.shop_count)
    RadioButton mShopCount;
    @BindView(R.id.shop_price)
    RadioButton mShopPrice;
    @BindView(R.id.shop_xRcy)
    XRecyclerView mShopXRcy;
    private String pscid;
    private EditText search_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        ButterKnife.bind(this);
        initData();
        Intent intent = getIntent();
        pscid = intent.getStringExtra("pscid");

        /*Toast.makeText(GoodsListActivity.this,pscid+"",Toast.LENGTH_SHORT).show();*/

        Log.d(TAG, "onCreate: "+pscid);

        search_content = (EditText) findViewById(R.id.search_content);
        search_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GoodsListActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initData() {
        adapter = new GoodsAdapter(GoodsListActivity.this, data, 1);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mShopXRcy.setLayoutManager(manager);
        mShopXRcy.setLoadingMoreEnabled(true);
        mShopXRcy.setLoadingListener(this);
        mShopXRcy.setAdapter(adapter);
        loadData(sorts);
    }

    @Override
    protected void onStart() {
        super.onStart();
       onRefresh();
    }

    private void loadData(int sort) {

        GoodsPresenter presenter = new GoodsPresenter(this);
        presenter.getParams(pscid, page+"", sort+"");

    }


    @Override
    public void onRefresh() {
        isFresh = true;
        page = 1;
        loadData(sorts);
    }

    @Override
    public void onLoadMore() {
        isFresh = false;
        if (page != 3) {
            page++;
            loadData(sorts);

        }

    }


    @Override
    public void showGoodsData(List<GoodsBean.DataBean> newslist) {
        if (isFresh) {
            mShopXRcy.setLoadingMoreEnabled(true);
            mShopXRcy.refreshComplete();

        } else {
            mShopXRcy.loadMoreComplete();

        }
        if (newslist != null) {
            if (isFresh) {
                data.clear();
            }

            data.addAll(newslist);
            adapter.notifyDataSetChanged();
        }
        if (page == 3) {
            Toast.makeText(this, "没有更多数据啦!", Toast.LENGTH_LONG).show();
            mShopXRcy.loadMoreComplete();
            mShopXRcy.setLoadingMoreEnabled(false);


        }

    }


    @OnClick({R.id.shop_pic, R.id.shop_back, R.id.shop_zong, R.id.shop_count, R.id.shop_price})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.shop_pic:

                if (flag) {
                    adapter.notifyDataSetChanged();
                    adapter = new GoodsAdapter(GoodsListActivity.this, data, 2);
                    mShopPic.setImageDrawable(getResources().getDrawable(R.drawable.kind_liner));
                    GridLayoutManager manager = new GridLayoutManager(GoodsListActivity.this, 2);
                    mShopXRcy.setLayoutManager(manager);
                    mShopXRcy.setAdapter(adapter);
                    flag = false;
                } else {
                    adapter.notifyDataSetChanged();
                    adapter = new GoodsAdapter(GoodsListActivity.this, data, 1);
                    mShopPic.setImageDrawable(getResources().getDrawable(R.drawable.kind_grid));
                    LinearLayoutManager manager = new LinearLayoutManager(GoodsListActivity.this);
                    mShopXRcy.setLayoutManager(manager);
                    mShopXRcy.setAdapter(adapter);
                    flag = true;
                }
                break;
            case R.id.shop_back:
                finish();
                break;
            case R.id.shop_zong:
                sorts=0;
                isSort=true;
                loadData(sorts);
                adapter.notifyDataSetChanged();
                break;
            case R.id.shop_count:
                sorts=1;
                isSort=true;

                loadData(sorts);
                adapter.notifyDataSetChanged();
                break;
            case R.id.shop_price:
                sorts=2;
                isSort=true;

                loadData(sorts);
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
