package com.rookie.imitationjd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rookie.imitationjd.R;
import com.rookie.imitationjd.gen.Record;

import java.util.List;

/**
 * Created by 暗夜 on 2018/5/26.
 */

public class SearchRecoedAdapter extends RecyclerView.Adapter<SearchRecoedAdapter.MyViewHolder>{

    private List<Record> list;
    private Context context;

    public SearchRecoedAdapter(List<Record> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public SearchRecoedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.search_history_item,null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SearchRecoedAdapter.MyViewHolder holder, int position) {
        holder.tv_content.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_content;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }
}
