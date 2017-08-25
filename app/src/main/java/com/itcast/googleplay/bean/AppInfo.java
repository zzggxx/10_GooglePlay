package com.itcast.googleplay.bean;

import java.util.ArrayList;

public class AppInfo {

    private long id;            //唯一的标识
    private String des;         //app的描述
    private String iconUrl;     //app的图标的url后缀
    private String name;        //app的名字
    private String packageName; //app的包名
    private long size;          //app的大小
    private float stars;        //app的星级
    private String downloadUrl; //下载的地址

    public String getDownloadURl() {
        return downloadUrl;
    }

    public void setDownloadURl(String downloadURl) {
        this.downloadUrl = downloadUrl;
    }


    private String author;//作者
    private String date;//日期
    private String downloadNum;//下载数量
    private String version;//版本
    private ArrayList<String> screen;//app的截图
    private ArrayList<SafeInfo> safe;//安全信息

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(String downloadNum) {
        this.downloadNum = downloadNum;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ArrayList<String> getScreen() {
        return screen;
    }

    public void setScreen(ArrayList<String> screen) {
        this.screen = screen;
    }

    public ArrayList<SafeInfo> getSafe() {
        return safe;
    }

    public void setSafe(ArrayList<SafeInfo> safe) {
        this.safe = safe;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }


}
