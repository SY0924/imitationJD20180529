package com.rookie.imitationjd.httpconfig;


import com.rookie.imitationjd.bean.AddCarBean;
import com.rookie.imitationjd.bean.DelCarBean;
import com.rookie.imitationjd.bean.GoodsBean;
import com.rookie.imitationjd.bean.InfoBean;
import com.rookie.imitationjd.bean.LoginBean;
import com.rookie.imitationjd.bean.RegisterBean;
import com.rookie.imitationjd.bean.SearchBean;
import com.rookie.imitationjd.bean.ShopCarBean;
import com.rookie.imitationjd.bean.UpdateNameBean;
import com.rookie.imitationjd.bean.UploadBean;
import com.rookie.imitationjd.bean.UserInfoBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by 暗夜 on 2018/5/17.
 */

public interface Api{

    //登录
    @GET(HttpApi.LoginUrl)
    Observable<LoginBean> login(@Query("mobile") String mobile,@Query("password") String password);

    //注册
    @GET(HttpApi.RegisterUrl)
    Observable<RegisterBean> register(@Query("mobile") String mobile,@Query("password") String password);

    //查询个人信息
    @GET(HttpApi.UserInfoUrl)
    Observable<UserInfoBean> userInfo(@Query("uid") String uid);

    //查询购物车
    @GET(HttpApi.CheckCarUrl)
    Observable<ShopCarBean> getCars(@Query("uid") String uid);

    //删除购物车
    @GET(HttpApi.DelCarUrl)
    Observable<DelCarBean> getDels(@Query("uid") String uid,@Query("pid") String pid);

    //上传头像
    @Multipart
    @POST(HttpApi.UploadUrl)
    Observable<UploadBean> upload(@Query("uid") String uid, @Part MultipartBody.Part file);


    //修改昵称
    @POST(HttpApi.UpdateNameUrl)
    Observable<UpdateNameBean> updateName(@Query("uid") String uid, @Query("nickname") String nickname);

    //当前子分类下的商品列表
    @GET(HttpApi.GoodsUrl)
    Observable<GoodsBean> getGoods(@Query("pscid") String pscid, @Query("page") String page,@Query("sort") String sort);

    //商品详情
    @GET(HttpApi.InfoUrl)
    Observable<InfoBean> getInfo(@Query("pid") String pid);

    //添加购物车
    @GET(HttpApi.AddCarUrl)
    Observable<AddCarBean> addCar(@Query("uid") String uid,@Query("pid") String pid);

    //根据关键词搜索商品
    @GET(HttpApi.SearchUrl)
    Observable<SearchBean> search(@Query("keywords") String keywords, @Query("page") String page);

}
