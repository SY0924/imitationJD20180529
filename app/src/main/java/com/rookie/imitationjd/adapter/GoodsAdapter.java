package com.rookie.imitationjd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.rookie.imitationjd.R;
import com.rookie.imitationjd.bean.GoodsBean;
import com.rookie.imitationjd.view.activities.InfoActivity;

import java.util.List;

/**
 * Created by 暗夜 on 2018/5/25.
 */

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsViewHolder> {

    private Context context;
    private List<GoodsBean.DataBean> list;
    private int i ;
    public GoodsAdapter(Context context, List<GoodsBean.DataBean> list, int i) {
        this.context = context;
        this.list = list;
        this.i=i;
    }


    @Override
    public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (i==1){
            View v = View.inflate(context, R.layout.layout_goods_list_item, null);
            GoodsViewHolder holder = new GoodsViewHolder(v);
            return holder;
        }else if (i==2){
            View v = View.inflate(context, R.layout.layout_goods_grid_item, null);
            GoodsViewHolder holder = new GoodsViewHolder(v);
            return holder;

        }
        return null;
    }

    @Override
    public void onBindViewHolder(GoodsViewHolder holder, final int position) {
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.price.setText("￥"+list.get(position).getPrice());
   holder.num.setText("销量:"+list.get(position).getSalenum());
        String images = list.get(position).getImages();
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(holder.img);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoActivity.class);
                intent.putExtra("pid",list.get(position).getPid()+"");
                context.startActivity(intent);
            }
        });
    }





    @Override
    public int getItemCount() {
        return list.size();
    }

    class GoodsViewHolder extends XRecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView price;
        private ImageView img;
        private TextView num;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.gd_title);
            price = itemView.findViewById(R.id.gd_price);
            num = itemView.findViewById(R.id.gd_count);
            img = itemView.findViewById(R.id.gd_img);

        }
    }
}