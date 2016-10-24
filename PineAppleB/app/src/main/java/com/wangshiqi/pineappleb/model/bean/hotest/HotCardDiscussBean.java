package com.wangshiqi.pineappleb.model.bean.hotest;

import java.util.List;

/**
 * Created by dllo on 16/10/24.
 */
public class HotCardDiscussBean {

    /**
     * status : 1
     * msg :
     * success : true
     * totalCommentCount : 6
     * data : [{"id":14770368439621,"roomId":0,"userId":5785949870117162348,"userIdStr":"5785949870117162348","nick":"王尼玛王尼玛","avatar":"http://wx.qlogo.cn/mmopen/icRsVZqfumBw9CEBgruS5X4QiaJuyjP5vyJdY1MTFnZx7v94sfaSicqYicnkzGkp0mLUYxzcMy2GEEwRcGhuPcktMCcFca8YibdOd/0","content":"app太水，快进都不好","supportCount":0,"commentCount":0,"createTime":1477236419000,"hasSupport":false,"images":null,"commentFrom":0,"intro":"主人犯懒，什么都没写"},{"id":14770368428911,"roomId":0,"userId":-4403766275358812791,"userIdStr":"-4403766275358812791","nick":"鹿止魚","avatar":"http://q.qlogo.cn/qqapp/100374397/087ECC98D709602026C5D0D4CA3C2E7E/100","content":"1","supportCount":1,"commentCount":0,"createTime":1477208053000,"hasSupport":false,"images":null,"commentFrom":0,"intro":"主人犯懒，什么都没写"},{"id":14770368428901,"roomId":0,"userId":-4403766275358812791,"userIdStr":"-4403766275358812791","nick":"鹿止魚","avatar":"http://q.qlogo.cn/qqapp/100374397/087ECC98D709602026C5D0D4CA3C2E7E/100","content":"1","supportCount":1,"commentCount":0,"createTime":1477208048000,"hasSupport":false,"images":null,"commentFrom":0,"intro":"主人犯懒，什么都没写"},{"id":14770368428891,"roomId":0,"userId":-4403766275358812791,"userIdStr":"-4403766275358812791","nick":"鹿止魚","avatar":"http://q.qlogo.cn/qqapp/100374397/087ECC98D709602026C5D0D4CA3C2E7E/100","content":"1","supportCount":1,"commentCount":0,"createTime":1477208043000,"hasSupport":false,"images":null,"commentFrom":0,"intro":"主人犯懒，什么都没写"},{"id":14770369893831,"roomId":0,"userId":-4403766275358812791,"userIdStr":"-4403766275358812791","nick":"鹿止魚","avatar":"http://q.qlogo.cn/qqapp/100374397/087ECC98D709602026C5D0D4CA3C2E7E/100","content":"1","supportCount":1,"commentCount":0,"createTime":1477208038000,"hasSupport":false,"images":null,"commentFrom":0,"intro":"主人犯懒，什么都没写"},{"id":14770369893821,"roomId":0,"userId":-4403766275358812791,"userIdStr":"-4403766275358812791","nick":"鹿止魚","avatar":"http://q.qlogo.cn/qqapp/100374397/087ECC98D709602026C5D0D4CA3C2E7E/100","content":"1","supportCount":2,"commentCount":0,"createTime":1477208033000,"hasSupport":false,"images":null,"commentFrom":0,"intro":"主人犯懒，什么都没写"}]
     */

    private int status;
    private String msg;
    private boolean success;
    private int totalCommentCount;
    /**
     * id : 14770368439621
     * roomId : 0
     * userId : 5785949870117162348
     * userIdStr : 5785949870117162348
     * nick : 王尼玛王尼玛
     * avatar : http://wx.qlogo.cn/mmopen/icRsVZqfumBw9CEBgruS5X4QiaJuyjP5vyJdY1MTFnZx7v94sfaSicqYicnkzGkp0mLUYxzcMy2GEEwRcGhuPcktMCcFca8YibdOd/0
     * content : app太水，快进都不好
     * supportCount : 0
     * commentCount : 0
     * createTime : 1477236419000
     * hasSupport : false
     * images : null
     * commentFrom : 0
     * intro : 主人犯懒，什么都没写
     */

    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTotalCommentCount() {
        return totalCommentCount;
    }

    public void setTotalCommentCount(int totalCommentCount) {
        this.totalCommentCount = totalCommentCount;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private long id;
        private int roomId;
        private long userId;
        private String userIdStr;
        private String nick;
        private String avatar;
        private String content;
        private int supportCount;
        private int commentCount;
        private long createTime;
        private boolean hasSupport;
        private Object images;
        private int commentFrom;
        private String intro;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getRoomId() {
            return roomId;
        }

        public void setRoomId(int roomId) {
            this.roomId = roomId;
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

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getSupportCount() {
            return supportCount;
        }

        public void setSupportCount(int supportCount) {
            this.supportCount = supportCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public boolean isHasSupport() {
            return hasSupport;
        }

        public void setHasSupport(boolean hasSupport) {
            this.hasSupport = hasSupport;
        }

        public Object getImages() {
            return images;
        }

        public void setImages(Object images) {
            this.images = images;
        }

        public int getCommentFrom() {
            return commentFrom;
        }

        public void setCommentFrom(int commentFrom) {
            this.commentFrom = commentFrom;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }
    }
}
