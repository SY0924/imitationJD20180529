package com.rookie.imitationjd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rookie.imitationjd.R;
import com.rookie.imitationjd.bean.SYBannerBean;
import com.rookie.imitationjd.view.activities.GoodsListActivity;

import java.util.List;

/**
 * Created by 暗夜 on 2018/5/21.
 */

public class SYTJAdapter extends RecyclerView.Adapter<SYTJAdapter.TJViewHolder> {
    private Context context;
    private List<SYBannerBean.TuijianBean.ListBean> list;

    public SYTJAdapter(Context context, List<SYBannerBean.TuijianBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public TJViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.layout_sy_tj_item, null);
        TJViewHolder TJViewHolder=new TJViewHolder(view);
        return TJViewHolder;
    }

    @Override
    public void onBindViewHolder(TJViewHolder holder, final int position) {
        String images = list.get(position).getImages();
        String[] split = images.split(".jpg");
        holder.sy_tj_pic.setImageURI(split[0]+".jpg");
        holder.sy_tj_title.setText(list.get(position).getTitle());
        holder.sy_tj_price.setText(list.get(position).getPrice()+"¥");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GoodsListActivity.class);
                int pscid = list.get(position).getPscid();
                intent.putExtra("pscid",pscid+"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TJViewHolder extends RecyclerView.ViewHolder{
        public SimpleDraweeView sy_tj_pic;
        public TextView sy_tj_title,sy_tj_price;
        public TJViewHolder(View itemView) {
            super(itemView);
            this.sy_tj_pic=itemView.findViewById(R.id.sy_tj_pic);
            this.sy_tj_title=itemView.findViewById(R.id.sy_tj_title);
            this.sy_tj_price=itemView.findViewById(R.id.sy_tj_price);
        }
    }
}