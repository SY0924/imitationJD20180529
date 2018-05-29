package com.rookie.imitationjd.httpconfig;

/**
 * Created by 暗夜 on 2018/4/17.
 */

public class HttpApi {
        //公共接口
    public static final String BaseUrl="https://www.zhaoapi.cn/";

    //登录
    public static final String LoginUrl="user/login";

    //注册
    public static final String RegisterUrl="user/reg";

    //用户信息
    public static final String UserInfoUrl="user/getUserInfo";

    //首页轮播图数据（轮播图+京东秒杀+最底部的为你推荐）
    public static final String BannerUrl="https://www.zhaoapi.cn/ad/getAd";
    //首页导航栏数据(商品分类接口（此接口用于首页九宫格，和底部页签分类页）)
    public static final String NavUrl="https://www.zhaoapi.cn/product/getCatagory";

    //分类页面子分类接口
    public static final String ClassUrl="https://www.zhaoapi.cn/product/getProductCatagory";


    //查询购物车
    public static final String CheckCarUrl="product/getCarts";

    //删除购物车（新增）
    public static final String DelCarUrl="product/deleteCart";

    //上传头像
    public static final String UploadUrl="file/upload";

    //修改昵称
    public static final String UpdateNameUrl="user/updateNickName";

    //当前子分类下的商品列表
    public static final String GoodsUrl="product/getProducts";

    //商品详情
    public static final String InfoUrl="product/getProductDetail";

    //添加购物车
    public static final String AddCarUrl="product/addCart";

    //根据关键词搜索商品
    public static final String SearchUrl="product/searchProducts";
}
