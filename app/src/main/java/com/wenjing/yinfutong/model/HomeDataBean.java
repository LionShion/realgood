package com.wenjing.yinfutong.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by wenjing on 2018/3/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeDataBean {


    /**
     * banner : [{"shareContent":"测试1","imageUrl":"http://192.168.1.206/images/banner/B20180412151247197.jpg","name":"测试1","linkUrl":"","remark":"<p>测试1<\/p>","id":15,"category":0},{"shareContent":"南朝四百八十寺","imageUrl":"http://192.168.1.206/images/banner/B20180413114608918.jpg","name":"云想衣裳花想容2","linkUrl":"","remark":"<p><img src=\"https://oss.aliyuncs.com/jiayuanbank-image/2018-04/022deaac5cbf41b7aa9fb4ab417b1970.jpg\" title=\"\" alt=\"touxiang10.jpg\"/><\/p>","id":16,"category":0},{"shareContent":"小美国","imageUrl":"http://192.168.1.206/images/banner/B20180404161251338.jpg","name":"云想衣裳花想容","linkUrl":"https://www.baidu.com/","remark":"/product/detail/f/0252//192.123","id":3,"category":1}]
     * serverTime : 2018-04-18 15:15:34
     * msgCount : 0
     * menu : [{"name":"账单","imageUrl":"http://192.168.1.206/images/homemenu/icon_home_bill@3x.png","linkUrl":"","sort":1,"interfaceEnum":1},{"name":"我的商家","imageUrl":"http://192.168.1.206/images/homemenu/icon_home_business@3x.png","linkUrl":"","sort":2,"interfaceEnum":2},{"name":"1元购","imageUrl":"http://192.168.1.206/images/homemenu/icon_home_yiyuan@3x.png","linkUrl":"","sort":3,"interfaceEnum":0},{"name":"对账","imageUrl":"http://192.168.1.206/images/homemenu/icon_home_duizhang@3x.png","linkUrl":"","sort":4,"interfaceEnum":3},{"name":"佣金","imageUrl":"http://192.168.1.206/images/homemenu/icon_home_yongjin@3x.png","linkUrl":"","sort":5,"interfaceEnum":4},{"name":"更多期待","imageUrl":"http://192.168.1.206/images/homemenu/icon_home_more@3x.png","linkUrl":"","sort":6,"interfaceEnum":0},{"name":"测试2018","imageUrl":"http://192.168.1.206/images/homemenu/B20180413142535978.jpg","linkUrl":"https://bbs.csdn.net/topics/391879989","sort":7,"interfaceEnum":0}]
     * notice : [{"createTime":"2018-04-13 14:34:48","linkUrl":"","linkType":0,"id":6,"title":"后台投放内容","content":"后台投放内容后台投放内容后台投放"},{"createTime":"2018-04-13 09:50:06","linkUrl":"","linkType":0,"id":4,"title":"滚动播放","content":"测试滚动播放"},{"createTime":"2018-04-12 15:49:53","linkUrl":"","linkType":0,"id":7,"title":"测试2号","content":"愚人节送起来"}]
     */

    private String serverTime;
    private int msgCount;
    private int noticeCount;
    private List<BannerBean> banner;
    private List<MenuBean> menu;
    private List<NoticeBean> notice;

    public int getNoticeCount() {
        return noticeCount;
    }

    public void setNoticeCount(int noticeCount) {
        this.noticeCount = noticeCount;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<MenuBean> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuBean> menu) {
        this.menu = menu;
    }

    public List<NoticeBean> getNotice() {
        return notice;
    }

    public void setNotice(List<NoticeBean> notice) {
        this.notice = notice;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BannerBean {
        /**
         * shareContent : 测试1
         * imageUrl : http://192.168.1.206/images/banner/B20180412151247197.jpg
         * name : 测试1
         * linkUrl :
         * remark : <p>测试1</p>
         * id : 15
         * category : 0
         */

        private String shareContent;
        private String imageUrl;
        private String name;
        private String linkUrl;
        private String remark;
        private int id;
        private int category;

        public BannerBean() {
        }

        public String getShareContent() {
            return shareContent;
        }

        public void setShareContent(String shareContent) {
            this.shareContent = shareContent;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MenuBean {
        /**
         * name : 账单
         * imageUrl : http://192.168.1.206/images/homemenu/icon_home_bill@3x.png
         * linkUrl :
         * sort : 1
         * interfaceEnum : 1
         */

        private String name;
        private String imageUrl;
        private String linkUrl;
        private int sort;
        private int interfaceEnum;

        public MenuBean() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getInterfaceEnum() {
            return interfaceEnum;
        }

        public void setInterfaceEnum(int interfaceEnum) {
            this.interfaceEnum = interfaceEnum;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NoticeBean {
        /**
         * createTime : 2018-04-13 14:34:48
         * linkUrl :
         * linkType : 0
         * id : 6
         * title : 后台投放内容
         * content : 后台投放内容后台投放内容后台投放
         */

        private String createTime;
        private String linkUrl;
        private int linkType;
        private int id;
        private String title;
        private String content;

        public NoticeBean() {
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public int getLinkType() {
            return linkType;
        }

        public void setLinkType(int linkType) {
            this.linkType = linkType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
