package com.rookie.imitationjd.view.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.rookie.imitationjd.R;
import com.rookie.imitationjd.adapter.GWCAdapter;
import com.rookie.imitationjd.bean.ShopCarBean;
import com.rookie.imitationjd.bean.customclass.MessageEvent;
import com.rookie.imitationjd.bean.customclass.PriceAndNum;
import com.rookie.imitationjd.bean.customclass.SomeId;
import com.rookie.imitationjd.presenter.GWCPresenter;
import com.rookie.imitationjd.view.activities.LoginActivity;
import com.rookie.imitationjd.view.interfaces.IGWCView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 暗夜 on 2018/5/21.
 */

public class GouWuCheFragment extends Fragment implements IGWCView,View.OnClickListener{

    private ExpandableListView mElv;
    /**
     * 全选
     */
    private CheckBox mAllChekbox;
    /**
     * ￥0.00
     */
    private TextView mTvTotalPrice;
    /**
     * 结算(0)
     */
    private TextView mTvGoToPay;
    private List<ShopCarBean.DataBean> groupList= new ArrayList<>();
    private  List<List<ShopCarBean.DataBean.ListBean>> childList= new ArrayList<>();
    private GWCAdapter adapter;
    private TextView tvCount;
    private TextView tvEdit;
    private LinearLayout llShar;
    private boolean flag=true;
    private LinearLayout llInfo;
    private TextView tvCommit;
    private TextView tvDelete;
    private View view;
    private LinearLayout cart_empty;

    private SharedPreferences preferences;
    private int size;
    private LinearLayout cart_islogin;
    private Button cartsLogin;

    private GWCPresenter gwcPresenter;
    private String uid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_layout_gwc, null);


        initView();
        EventBus.getDefault().register(this);
        preferences = getContext().getSharedPreferences("user", MODE_PRIVATE);

        adapter = new GWCAdapter(getContext(),cart_empty,groupList,childList);
        PriceAndNum priceAndNum = new PriceAndNum();

        mElv.setAdapter(adapter);
        mElv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
        adapter.notifyDataSetChanged();



        return view;
    }



    @Override
    public void showCarData(List<ShopCarBean.DataBean> data) {
        if(data!=null){
            groupList.addAll(data);
            for (int i = 0; i < data.size(); i++) {
                List<ShopCarBean.DataBean.ListBean> datas = data.get(i).getList();
                childList.add(datas);
            }
            mElv.setGroupIndicator(null);
            for(int i = 0; i < groupList.size(); i++){
                mElv.expandGroup(i);
            }
        }else{
            cart_empty.setVisibility(View.VISIBLE);

        }

        adapter.notifyDataSetChanged();
    }


    private void goneAndVisibility() {
        if(flag==false){
            llShar.setVisibility(View.VISIBLE);
            tvCommit.setVisibility(View.VISIBLE);
            tvEdit.setVisibility(View.GONE);
            llInfo.setVisibility(View.GONE);
        }else{
            llShar.setVisibility(View.GONE);
            tvCommit.setVisibility(View.GONE);
            tvEdit.setVisibility(View.VISIBLE);
            llInfo.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        cartsLogin = view.findViewById(R.id.carts_login);
        cart_islogin = view.findViewById(R.id.layout_cart_islogin);
        cart_empty = view.findViewById(R.id.layout_cart_empty);
        mElv =  view.findViewById(R.id.exListView);
        mAllChekbox =  view.findViewById(R.id.all_chekbox);
        mTvTotalPrice =  view.findViewById(R.id.tv_total_price);
        mTvGoToPay =  view.findViewById(R.id.tv_go_to_pay);
        tvEdit = view.findViewById(R.id.tv_edit);
        llShar =  view.findViewById(R.id.ll_shar);
        llInfo =  view.findViewById(R.id.ll_info);
        tvCommit =  view.findViewById(R.id.tv_commit);
        tvDelete =  view.findViewById(R.id.tv_delete);
        tvDelete.setOnClickListener(this);
        tvCommit.setOnClickListener(this);
        mAllChekbox.setOnClickListener(this);
        tvEdit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_chekbox:
                adapter.changeAllListCbState(mAllChekbox.isChecked());
                break;
            case R.id.tv_edit:
                flag=false;
                goneAndVisibility();
                break;
            case R.id.tv_commit:
                flag=true;
                goneAndVisibility();
                break;
            case R.id.carts_login:
           Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(),"先登录",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @Override
    public void onStart() {
        super.onStart();
    }


    @Subscribe
    public void onMessageEvent(PriceAndNum event) {
        mTvGoToPay.setText("结算(" + event.getNum() + ")");
        mTvTotalPrice.setText("￥"+event.getPrice() );
    }
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        mAllChekbox.setChecked(event.isChecked());
    }
    @Subscribe
    public void onMessageEvent(SomeId event) {

        size = event.getSize();

        int pid = event.getPid();
        if(size==0){
            SharedPreferences.Editor edit = preferences.edit();
            edit.putBoolean("isEmpty",true);
            edit.commit();
        }
        Log.e("zxz",pid+"我要删除了"+ size);


        gwcPresenter.getDelCar(uid,pid+"");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        gwcPresenter.onDestory();

    }

    @Override
    public void onResume() {
        super.onResume();

        //从sharedPrefences中获取值
      uid = preferences.getString("uid", "");
        boolean isLogin = preferences.getBoolean("isLogin", false);
        boolean isEmpty = preferences.getBoolean("isEmpty", false);

        if(groupList.size()!=0||childList.size()!=0){
            groupList.clear();
            childList.clear();
        }

        if(isEmpty==false||childList.size()!=0){
            cart_empty.setVisibility(View.GONE);
        }else {
            cart_empty.setVisibility(View.VISIBLE);

        }
        if(isLogin==true){
            cart_islogin.setVisibility(View.GONE);


            gwcPresenter= new GWCPresenter(this);
            gwcPresenter.getCarUid(uid);

        }else{
            cart_islogin.setVisibility(View.VISIBLE);
            cartsLogin.setOnClickListener(this);
        }


    }

    //删除购物车
    @Override
    public void delCarData(String msg) {
        Toast.makeText(getContext(),msg.toString(),Toast.LENGTH_SHORT).show();
    }

}
