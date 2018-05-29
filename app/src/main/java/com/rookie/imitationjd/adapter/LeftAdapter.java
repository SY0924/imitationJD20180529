package com.rookie.imitationjd.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.rookie.imitationjd.R;
import com.rookie.imitationjd.bean.SYNavBean;

import java.util.List;

/**
 * Created by 暗夜 on 2018/4/28.
 */

public class LeftAdapter extends BaseAdapter {
    private Context context;
    private List<SYNavBean.DataBean> list;


    public LeftAdapter(Context context, List<SYNavBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
       ViewHolder viewHolder=null;
        if(view==null){
            view = View.inflate(context, R.layout.layout_fl_left, null);
            TextView textView = view.findViewById(R.id.fl_left_tv);

            viewHolder = new ViewHolder(textView);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }

        viewHolder.getTextView().setText(list.get(position).getName());


        return view;
    }

    //适配器
    class ViewHolder{
        private TextView textView;

        public ViewHolder(TextView textView) {
            this.textView = textView;
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }
}
