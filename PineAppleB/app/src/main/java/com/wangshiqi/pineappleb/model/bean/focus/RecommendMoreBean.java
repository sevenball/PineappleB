package com.wangshiqi.pineappleb.model.bean.focus;

/**
 * Created by dllo on 16/10/22.
 */
public class RecommendMoreBean {

    /**
     * videoId : 14767845495501
     * title : 雷米比你快-刺客改动修改
     * cover : http://bobo-public.nosdn.127.net/bobo_1476983244109_44740691.jpg
     * duration : 1666
     * isLock : 0
     * previewDuration : 0
     * unlockSeed : 0.0
     * unlockCount : null
     */

    private String videoId;
    private String title;
    private String cover;
    private int duration;
    private int isLock;
    private int previewDuration;
    private double unlockSeed;
    private Object unlockCount;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getIsLock() {
        return isLock;
    }

    public void setIsLock(int isLock) {
        this.isLock = isLock;
    }

    public int getPreviewDuration() {
        return previewDuration;
    }

    public void setPreviewDuration(int previewDuration) {
        this.previewDuration = previewDuration;
    }

    public double getUnlockSeed() {
        return unlockSeed;
    }

    public void setUnlockSeed(double unlockSeed) {
        this.unlockSeed = unlockSeed;
    }

    public Object getUnlockCount() {
        return unlockCount;
    }

    public void setUnlockCount(Object unlockCount) {
        this.unlockCount = unlockCount;
    }
}
