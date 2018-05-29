package com.rookie.imitationjd.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rookie.imitationjd.R;
import com.rookie.imitationjd.bean.SYNavBean;
import com.rookie.imitationjd.view.activities.GoodsListActivity;

import java.util.List;

/**
 * Created by 暗夜 on 2018/5/21.
 */
public class SYNavAdapter extends RecyclerView.Adapter<SYNavAdapter.MyNavViewHolder>{
    private Context context;
    private List<SYNavBean.DataBean> list;

    public SYNavAdapter(Context context, List<SYNavBean.DataBean>  list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public MyNavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.layout_sy_nav_item, null);
        MyNavViewHolder MyNavViewHolder=new MyNavViewHolder(inflate);
        return MyNavViewHolder;
    }

    @Override
    public void onBindViewHolder(MyNavViewHolder holder, int position) {
        holder.sy_nav_pic.setImageURI(Uri.parse(list.get(position).getIcon()));
        holder.sy_nav_title.setText(list.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return 19;
    }

    class MyNavViewHolder extends RecyclerView.ViewHolder{
        public SimpleDraweeView sy_nav_pic;
        public TextView sy_nav_title;
        public MyNavViewHolder(View itemView) {
            super(itemView);
            this.sy_nav_pic=itemView.findViewById(R.id.sy_nav_pic);
            this.sy_nav_title=itemView.findViewById(R.id.sy_nav_title);
        }
    }
}
