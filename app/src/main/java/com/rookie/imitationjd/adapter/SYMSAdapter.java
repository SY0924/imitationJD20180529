package com.rookie.imitationjd.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
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

public class SYMSAdapter extends RecyclerView.Adapter<SYMSAdapter.MSViewHolder> {
    private Context context;
    private List<SYBannerBean.MiaoshaBean.ListBeanX>  miaoshalist;

    public SYMSAdapter(Context context, List<SYBannerBean.MiaoshaBean.ListBeanX> miaoshalist) {
        this.context = context;
        this.miaoshalist = miaoshalist;
    }

    @Override
    public MSViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.layout_sy_ms_item, null);
        MSViewHolder MSViewHolder=new MSViewHolder(inflate);
        return MSViewHolder;
    }

    @Override
    public void onBindViewHolder(MSViewHolder holder, final int position) {
        holder.sy_ms_oldprice.setText("原价：¥"+miaoshalist.get(position).getBargainPrice()+"¥");
        holder.sy_ms_oldprice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        holder.sy_ms_price.setText("优惠价：¥"+miaoshalist.get(position).getPrice()+"¥");
        String images = miaoshalist.get(position).getImages();
        String[] split = images.split(".jpg");
        holder.sy_ms_pic.setImageURI(Uri.parse(split[0]+".jpg"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GoodsListActivity.class);
                int pscid = miaoshalist.get(position).getPscid();
                intent.putExtra("pscid",pscid+"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return miaoshalist.size();
    }

    class MSViewHolder extends RecyclerView.ViewHolder{
        public SimpleDraweeView sy_ms_pic;
        public TextView sy_ms_oldprice,sy_ms_price;
        public MSViewHolder(View itemView) {
            super(itemView);
            this.sy_ms_pic=itemView.findViewById(R.id.sy_ms_pic);
            this.sy_ms_oldprice=itemView.findViewById(R.id.sy_ms_oldprice);
            this.sy_ms_price=itemView.findViewById(R.id.sy_ms_price);
        }
    }
}