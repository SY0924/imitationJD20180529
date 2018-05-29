package com.rookie.imitationjd.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.rookie.imitationjd.R;
import com.rookie.imitationjd.bean.ClassBean;

import java.util.List;

/**
 * Created by 暗夜 on 2018/5/3.
 */

public class RightAdapter extends BaseAdapter{
    private Context context;
    private List<ClassBean.DataBean> data;

    public RightAdapter(Context context, List<ClassBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;

        List<ClassBean.DataBean.ListBean> list = this.data.get(i).getList();
        if(view==null){
            view=View.inflate(context, R.layout.layout_fl_right,null);
            holder=new ViewHolder();
            holder.tvRight=view.findViewById(R.id.fl_right_tv);
            holder.rightRcy=view.findViewById(R.id.right_rcy);
            view.setTag(holder);
        }else{
           holder= (ViewHolder) view.getTag();
        }
        holder.tvRight.setText(list.get(i).getName());
        RightItemAdapter adapter=new RightItemAdapter(context,list);
        holder.rightRcy.setLayoutManager(new GridLayoutManager(context,3));
        holder.rightRcy.setAdapter(adapter);
        return view;
    }

    class ViewHolder{
        TextView tvRight;
        RecyclerView rightRcy;
    }
}
