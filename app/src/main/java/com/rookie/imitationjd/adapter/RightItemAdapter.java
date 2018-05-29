package com.rookie.imitationjd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rookie.imitationjd.R;
import com.rookie.imitationjd.bean.ClassBean;
import com.rookie.imitationjd.view.activities.GoodsListActivity;

import java.util.List;

/**
 * Created by 暗夜 on 2018/5/3.
 */

public class RightItemAdapter extends RecyclerView.Adapter<RightItemAdapter.ViewHolder>{
    private Context context;
    private List<ClassBean.DataBean.ListBean> list;


    public RightItemAdapter(Context context, List<ClassBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.layout_fl_right_item, null);
         ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemName.setText(list.get(position).getName());
        Glide.with(context).load(list.get(position).getIcon()).into(holder.itemImg);

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

    class ViewHolder extends RecyclerView.ViewHolder{
    private TextView itemName;
    private ImageView itemImg;
    private LinearLayout rightLlt;

    public ViewHolder(View itemView) {
        super(itemView);
        itemName = itemView.findViewById(R.id.item_home_name);
        itemImg = itemView.findViewById(R.id.item_img);
        rightLlt = itemView.findViewById(R.id.right_llt);
    }
}
}
