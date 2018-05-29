package com.rookie.imitationjd.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rookie.imitationjd.R;
import com.rookie.imitationjd.bean.SYBannerBean;
import com.rookie.imitationjd.bean.SYNavBean;
import com.rookie.imitationjd.view.customview.NoticeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 暗夜 on 2018/5/21.
 */

public class SYAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
   private   List<SYNavBean.DataBean>  data ;
    private SYBannerBean shouye_bean;
    private List<String> notices;
    private Handler timeHandler;

    public SYAdapter(Context context, List<SYNavBean.DataBean> data , SYBannerBean shouye_bean) {
        this.context = context;
        this.data = data;
        this.shouye_bean = shouye_bean;

    }
    private long mHour = 02;
    private long mMin = 15;
    private long mSecond = 36;
    private boolean isRun = true;

    //定义常量  确定多条目加载类型
    final static int TYPE_ONE=1;
    final static int TYPE_TWO=2;
    final static int TYPE_THREE=3;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ONE:
                View inflate = View.inflate(context, R.layout.layout_sy_nav, null);
                Nav Nav=new Nav(inflate);
                return Nav;
            case TYPE_TWO:
                View inflate2 = View.inflate(context, R.layout.fg_sy_timer, null);
                Miaosha miaosha=new Miaosha(inflate2);
                return miaosha;
            case TYPE_THREE:
            View inflate1 = View.inflate(context, R.layout.layout_sy_tj, null);
            Tuijian tuijian=new Tuijian(inflate1);
            return tuijian;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Nav){
            SYNavAdapter adapter =new SYNavAdapter(context,data);
            ((Nav) holder).shouye_rc.setAdapter(adapter);
            ((Nav) holder).shouye_rc.setLayoutManager(new GridLayoutManager(context,2,GridLayoutManager.HORIZONTAL,false));
            notices = new ArrayList<>();
            notices.add("大促销下单拆福袋，亿万新年红包随便拿");
            notices.add("家电五折团，抢十亿无门槛现金红包");
            notices.add("星球大战剃须刀首发送200元代金券");
            ((Nav) holder).notice_view.addNotice(notices);
            ((Nav) holder).notice_view.startFlipping();
            ((Nav) holder).notice_view.setOnNoticeClickListener(new NoticeView.OnNoticeClickListener() {
                @Override
                public void onNotieClick(int position, String notice) {
                    Toast.makeText(context, notices.get(position), Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (holder instanceof Tuijian){
            ((Tuijian) holder).shouye_tuijian_tv.setText("京东推荐");
            List<SYBannerBean.TuijianBean.ListBean> list = shouye_bean.getTuijian().getList();
            SYTJAdapter SYTJAdapter =new SYTJAdapter(context,list);
            ((Tuijian) holder).shouye_tuijian_rc.setAdapter(SYTJAdapter);
            ((Tuijian) holder).shouye_tuijian_rc.setLayoutManager(new GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false));
            //设置默认分隔线
            DividerItemDecoration dec = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
            DividerItemDecoration decs = new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL);

            ((Tuijian) holder).shouye_tuijian_rc.addItemDecoration(dec);
            ((Tuijian) holder).shouye_tuijian_rc.addItemDecoration(decs);
        }

        if (holder instanceof Miaosha){
            ((Miaosha) holder).shouue_miaosha_tv.setText("京东秒杀");
            ((Miaosha) holder).shouue_miaosha_tv1.setText("16点场");
            startRun();
            timeHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what==1) {
                        computeTime();
                        if (mHour<10){
                            ((Miaosha) holder).tv_hour.setText("0"+mHour+"");
                        }else {
                            ((Miaosha) holder).tv_hour.setText("0"+mHour+"");
                        }
                        if (mMin<10){
                            ((Miaosha) holder).tv_minute.setText("0"+mMin+"");
                        }else {
                            ((Miaosha) holder).tv_minute.setText(mMin+"");
                        }
                        if (mSecond<10){
                            ((Miaosha) holder).tv_second.setText("0"+mSecond+"");
                        }else {
                            ((Miaosha) holder).tv_second.setText(mSecond+"");
                        }
                    }
                }
            };

            List<SYBannerBean.MiaoshaBean.ListBeanX> miaoshalist = shouye_bean.getMiaosha().getList();
            SYMSAdapter miaosha=new SYMSAdapter(context,miaoshalist);
            ((Miaosha) holder).miaosha_rv.setAdapter(miaosha);
            ((Miaosha) holder).miaosha_rv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

        }
    }

    /**
     * 开启倒计时
     */
    private void startRun() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (isRun) {
                    try {
                        Thread.sleep(1000); // sleep 1000ms
                        Message message = Message.obtain();
                        message.what = 1;
                        timeHandler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * 倒计时计算
     */
    private void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
            }
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return TYPE_ONE;
        }
        if (position==1){
            return TYPE_TWO;
        }
        if (position==2){
            return TYPE_THREE;
        }
        return 0;
    }
    class Miaosha extends RecyclerView.ViewHolder{
        public TextView shouue_miaosha_tv;
        public TextView shouue_miaosha_tv1;
        public TextView tv_hour;
        public TextView tv_minute;
        public TextView tv_second;
        public RecyclerView miaosha_rv;
        public Miaosha(View itemView) {
            super(itemView);
            this.shouue_miaosha_tv=itemView.findViewById(R.id.shouue_miaosha_tv);
            this.shouue_miaosha_tv1=itemView.findViewById(R.id.shouue_miaosha_tv1);
            this.tv_hour=itemView.findViewById(R.id.tv_hour);
            this.tv_minute=itemView.findViewById(R.id.tv_minute);
            this.tv_second=itemView.findViewById(R.id.tv_second);
            this.miaosha_rv=itemView.findViewById(R.id.miaosha_rv);
        }
    }

    class Tuijian extends RecyclerView.ViewHolder{
        public TextView shouye_tuijian_tv;
        public RecyclerView shouye_tuijian_rc;
        public Tuijian(View itemView) {
            super(itemView);
            this.shouye_tuijian_rc=itemView.findViewById(R.id.shouye_tuijian_rc);
            this.shouye_tuijian_tv=itemView.findViewById(R.id.shouye_tuijian_tv);
        }
    }

    class Nav extends RecyclerView.ViewHolder{
        public RecyclerView shouye_rc;
        public NoticeView notice_view;
        public Nav(View itemView) {
            super(itemView);
            this.shouye_rc=itemView.findViewById(R.id.shouye_rc);
            this.notice_view=itemView.findViewById(R.id.notice_view);
        }
    }
}