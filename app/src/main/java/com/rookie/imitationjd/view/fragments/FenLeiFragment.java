package com.rookie.imitationjd.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.rookie.imitationjd.R;
import com.rookie.imitationjd.adapter.LeftAdapter;
import com.rookie.imitationjd.adapter.RightAdapter;
import com.rookie.imitationjd.bean.ClassBean;
import com.rookie.imitationjd.bean.SYNavBean;
import com.rookie.imitationjd.model.FLModel;
import com.rookie.imitationjd.presenter.FLPresenter;
import com.rookie.imitationjd.view.activities.SearchActivity;
import com.rookie.imitationjd.view.interfaces.IFLView;

import java.util.List;

/**
 * Created by 暗夜 on 2018/5/21.
 */

public class FenLeiFragment extends Fragment implements IFLView{
    private static final String TAG = "FenLeiFragment----";
    private ListView fl_left,fl_right;
    private FLPresenter flPresenter;
    private List<SYNavBean.DataBean> list;

    private String cid;
    private View view;
    private EditText search_content;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_layout_fl, null);

        initView();
        //调用分类页面的presenter层
        flPresenter = new FLPresenter();
        flPresenter.showLeftToView(new FLModel(),this);


        return view;
    }

    private void initView() {
        fl_left = view.findViewById(R.id.fl_left);
        fl_right = view.findViewById(R.id.fl_right);


        search_content = view.findViewById(R.id.search_content);
        search_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });


        fl_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = list.get(i).getCid();
                FenLeiFragment.this.cid=id+"";

              flPresenter.showRightToView(new FLModel(),FenLeiFragment.this);

            }
        });
    }

    @Override
    public void showFLLeftData(List<SYNavBean.DataBean> data) {
        this.list=data;
        LeftAdapter leftAdapter=new LeftAdapter(getContext(),data);
        fl_left.setAdapter(leftAdapter);


        int cid = data.get(0).getCid();
        this.cid=cid+"";

        flPresenter.showRightToView(new FLModel(),this);

    }

    @Override
    public void showFLRightData(List<ClassBean.DataBean> data) {
        Log.d(TAG, "showFLRightData: "+data);

        RightAdapter adapter=new RightAdapter(getContext(),data);
        fl_right.setAdapter(adapter);

    }

    @Override
    public String getCid() {
        return this.cid;
    }
}
