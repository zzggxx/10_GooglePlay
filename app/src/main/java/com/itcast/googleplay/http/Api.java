package com.itcast.googleplay.http;

/**
 * Created by Lenovo on 2016/7/28.
 */
public interface Api {
    //服务器地址
    String SERVER_HOST = "http://127.0.0.1:8090/";
    //首页的轮播图加载地址
    String IMAGE_PREFIX = SERVER_HOST + "image?name=";
    //首页的数据加载
    String HOME = SERVER_HOST + "home?index=";
    //应用的数据加载
    String APP = SERVER_HOST + "app?index=";
    //游戏的数据加载
    String GAME = SERVER_HOST + "game?index=";
    //主题的数据加载
    String SUBJECT = SERVER_HOST + "subject?index=";
    //推荐的数据加载
    String RECOMMEND = SERVER_HOST + "recommend?index=0";
    //分类的数据加载
    String CATEGORY = SERVER_HOST + "category?index=0";
    //分类的数据加载
    String HOT = SERVER_HOST + "hot?index=0";
    //detail页的url地址
    String Detail = SERVER_HOST + "detail?packageName=%s";
    //app下载的url地址
    String Download = SERVER_HOST + "download?name=%s";
    //app断点下载的url地址
    String Break_Download = SERVER_HOST + "download?name=%s&range=%d";
}
