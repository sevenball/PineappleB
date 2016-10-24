package com.wangshiqi.pineappleb.utils;

/**
 * Created by dllo on 16/10/21.
 */
public class ValueTool {
    /**
     *  最火界面卡片的接口
     */
    public static final String HOT_CARD = "http://m.live.netease.com/bolo/api/rank/hotVideo.htm?userId=-2798972347206426236";
    /**
     * 发现界面接口
     */
    // 发现-推荐-轮播图
    public static final String HEAD_VP = "http://m.live.netease.com/bolo/api/index/bannerVideoNew.htm";
    // 发现-推荐-力荐
    public static final String STRONG_RV = "http://m.live.netease.com/bolo/api/index/videoRecommend.htm?pageNum=";
    // 发现-推荐-周榜
    public static final String RANK_RV = "http://m.live.netease.com/bolo/api/index/dailyPopList.htm?pageNum=1&pageSize=6";
    // 发现-必看
    public static final String MUST_WATCH = "http://m.live.netease.com/bolo/api/index/headPic.htm";

    /**
     * 关注界面的接口
     */
    // 更多推荐接口
    public static final String RECOMMENDMOREURLLEFT = "http://m.live.netease.com/bolo/api/video/relations.htm?videoId=";
    // 分集接口
    public static final String SORTSETURL = "http://m.live.netease.com/bolo/api/channel/setVideoList.htm?pageNum=1&sid=14642625033311&pageSize=-1";
    // 评论接口
    public static final String DISCUSSURLLEFT = "http://m.live.netease.com/bolo/api/video/commentList.htm?userId=-2798972347206426236&pageSize=15&pageNum=1&videoId=";
    public static final String DISCUSSURLRIGHT = "&encryptToken=5f724098912f342454785185628447bc&random=0.02496582080295462&type=0&timeStamp=1476793248225";
}
