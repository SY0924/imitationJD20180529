package com.rookie.imitationjd.utils;

import android.os.Handler;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 暗夜 on 2018/5/21.
 */

public class OkHttpUtils {
    private static Handler handler=new Handler();
    //单例模式
    private static OkHttpUtils OK_HTTP_UTILS=null;

    /**
     * 得到OkHttpUtils实例对象
     *
     * @return
     */
    private OkHttpUtils(){}
    public static OkHttpUtils getInstance(){
        if(null==OK_HTTP_UTILS){
            synchronized (OkHttpUtils.class){
                if(null==OK_HTTP_UTILS){
                    OK_HTTP_UTILS=new OkHttpUtils();
                }
            }
        }
        return OK_HTTP_UTILS;
    }

    /**
     * Get请求
     * @param onFinishListener
     */

    public void doGet(String path, Map<String,String> map, final OnFinishListener onFinishListener){
        StringBuffer sb = new StringBuffer();
        sb.append(path);
        //判断path是否包含一个
        if(sb.indexOf("?")!=-1){
            //判断"?"是否在最后一个
            if(sb.indexOf("?")!=sb.length()-1){
                sb.append("&");
            }

        }else{
            sb.append("?");
        }
        //遍历map集合中所有请求参数
        for(Map.Entry<String, String> entry:map.entrySet()){
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        //判断get请求路径最后是否包含一个"&"
        if(sb.lastIndexOf("&")!=-1){
            sb.deleteCharAt(sb.length()-1);
        }

        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                //添加应用拦截器
                .addInterceptor(new CommonInterceptor())
                //.addNetworkInterceptor() //添加网络拦截器
                .build();
        //构建请求项
        Request request=new Request.Builder()
                .get()
                .url(sb.toString())
                .build();
        Call call =okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                //请求失败
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onFinishListener.onFailed(e.getMessage());

                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(response.isSuccessful()){
                    //得到服务器的响应结果
                    final String result = response.body().string();
                    //请求成功
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //主线程当中执行
                            onFinishListener.onSuccess(result);
                        }
                    });

                }
            }
        });


    }
    /**
     * post请求
     *
     * @param path
     * @param map
     * @param onFinishListener
     */

    public void doPost(String path,Map<String,String> map,final OnFinishListener onFinishListener){
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                //添加应用拦截器 公共参数
                .addInterceptor(new CommonInterceptor())
                .build();
        //构建参数的对象
        FormBody.Builder builder = new FormBody.Builder();
        //遍历map集合，获取用户的key/value
        for(String key:map.keySet()){
            builder.add(key,map.get(key));
        }
        //构建请求项
        Request request=new Request.Builder()
                .post(builder.build())
                .url(path)
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onFinishListener.onFailed(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    final String result = response.body().string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onFinishListener.onSuccess(result);
                        }
                    });

                }
            }
        });
    }

}