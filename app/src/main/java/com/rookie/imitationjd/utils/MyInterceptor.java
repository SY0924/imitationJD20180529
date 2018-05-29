package com.rookie.imitationjd.utils;

import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 暗夜 on 2018/5/21.
 */

public class MyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //得到原始的请求对象
        Request request = chain.request();


        //得到用户所使用的请求方式
        String method = request.method();
        if("GET".equals(method)){
            //原始的请求接口
            String oldUrl = request.url().toString();
            //拼接一个新的接口
            String newUrl = oldUrl + "&source=android";
            //重新构建请求体
            request =new Request.Builder()
                    .url(newUrl)
                    .build();
        }else if ("POST".equals(method)) {
            //得到原始的url
            String oldUrl = request.url().toString();
            //得到原有的请求参数
            FormBody oldBody = (FormBody) request.body();
            //新的构建项
            FormBody.Builder builder = new FormBody.Builder();
            for (int i = 0; i < oldBody.size(); i++) {
                //取出相关请求参数(原有的)
                String name = oldBody.name(i);
                String value = oldBody.value(i);
                //将原始的参数拼装到新的构建体当中
                builder.add(name, value);
            }
            //拼装我们的公共参数
            builder.add("source", "android");
            request=new Request.Builder()
                    .url(oldUrl)
                    .post(builder.build())
                    .build();


        }

        //重新发送请求
        return chain.proceed(request);
    }
}
