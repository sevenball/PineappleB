package com.wangshiqi.pineappleb.model.bean.dicovery;

/**
 * Created by dllo on 16/10/24.
 */
public class RecommendRankBean {
    /**
     * name : 大力金刚 第一季
     * cover : http://bobo-public.nosdn.127.net/bobo_1467962319716_42300611.png
     * intro :
     * channelName : 大力金刚
     * videoCount : 153
     * playCount : 6277855
     * degree : 9124075
     * userId : -3664622527285983253
     * userIdStr : -3664622527285983253
     * avatar : http://bobo-public.nosdn.127.net/bobo_1462531322444_99063589
     * sid : 14622654722211
     */

    private String name;
    private String cover;
    private String intro;
    private String channelName;
    private int videoCount;
    private int playCount;
    private int degree;
    private long userId;
    private String userIdStr;
    private String avatar;
    private long sid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserIdStr() {
        return userIdStr;
    }

    public void setUserIdStr(String userIdStr) {
        this.userIdStr = userIdStr;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }
}
