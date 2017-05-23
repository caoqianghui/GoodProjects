package com.go.cqh.goodprojects.entry;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caoqianghui on 2016/11/17.
 */

public class ProjectsBean {

    /**
     * error : false
     * results : [{"_id":"5829408b421aa911dbc91566","createdAt":"2016-11-14T12:41:47.492Z","desc":"一图详解我的软件世界观与方法论","publishedAt":"2016-11-17T11:32:04.807Z","source":"web","type":"拓展资源","url":"https://www.processon.com/view/link/58288e3be4b00c4fc88dcadf","used":true,"who":"王下邀月熊(Chevalier)"},{"_id":"582a67c2421aa910344b13bb","createdAt":"2016-11-15T09:41:22.418Z","desc":"我的大飞机能停在你的飞机场吗\u2026污得无法无天[哆啦A梦吃惊]","publishedAt":"2016-11-17T11:32:04.807Z","source":"chrome","type":"休息视频","url":"http://www.miaopai.com/show/jluFkzvSIdrmHUQ3K8keMQ__.htm","used":true,"who":"lxxself"},{"_id":"582ab455421aa9198ccf9c7e","createdAt":"2016-11-15T15:08:05.238Z","desc":"Android social login (facebook, google) helper powered by RxJava ","publishedAt":"2016-11-17T11:32:04.807Z","source":"web","type":"Android","url":"https://github.com/jaychang0917/SocialLoginManager","used":true,"who":"Jay"},{"_id":"582c0f44421aa94ffa9f7626","createdAt":"2016-11-16T15:48:20.527Z","desc":"轻量级的material design图片选择器，颜值很高","images":["http://img.gank.io/d4cf06f2-674b-4fcb-bfc4-7a5c80fa87ee","http://img.gank.io/44259d13-f1de-4431-8bec-918936256668"],"publishedAt":"2016-11-17T11:32:04.807Z","source":"web","type":"Android","url":"https://github.com/liuguangqiang/IPicker","used":true,"who":"Eric"},{"_id":"582c409b421aa95006efc052","createdAt":"2016-11-16T19:18:51.833Z","desc":"安卓版圆形进度条","images":["http://img.gank.io/a414e1b8-4965-4cba-8f74-6aa2b1a9cf26"],"publishedAt":"2016-11-17T11:32:04.807Z","source":"web","type":"Android","url":"https://github.com/yingLanNull/CircleAlarmTimerView","used":true,"who":"yingLan"},{"_id":"582cf408421aa95002741a8f","createdAt":"2016-11-17T08:04:24.781Z","desc":"11-17","publishedAt":"2016-11-17T11:32:04.807Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034jw1f9us52puzsj20u00u0jtd.jpg","used":true,"who":"daimajia "},{"_id":"582d1914421aa95002741a93","createdAt":"2016-11-17T10:42:28.444Z","desc":"Katana，一个全新的 iOS 开发框架 ","publishedAt":"2016-11-17T11:32:04.807Z","source":"chrome","type":"iOS","url":"https://bendingspoons.github.io/katana-swift/","used":true,"who":"代码家"},{"_id":"582d1945421aa94ffa9f7639","createdAt":"2016-11-17T10:43:17.475Z","desc":"阮一峰总结的全栈工程师培训手册","publishedAt":"2016-11-17T11:32:04.807Z","source":"chrome","type":"拓展资源","url":"https://github.com/ruanyf/jstraining","used":true,"who":"代码家"},{"_id":"5829b5b2421aa911e32d87e3","createdAt":"2016-11-14T21:01:38.860Z","desc":"动画插值器的编辑器","images":["http://img.gank.io/fa3bb06d-1bfb-41a7-8bfe-78adeb55c049"],"publishedAt":"2016-11-16T11:37:18.947Z","source":"chrome","type":"Android","url":"https://github.com/MartinRGB/RapidInterpolator","used":true,"who":"Jason"},{"_id":"582a67ac421aa9102c2ec6e9","createdAt":"2016-11-15T09:41:00.851Z","desc":"人生问题：为什么吵架永远吵不过女朋友 男生内心： 你，经历过绝望吗？ ","publishedAt":"2016-11-16T11:37:18.947Z","source":"chrome","type":"休息视频","url":"http://weibo.com/tv/v/EhxXTv9K7?fid=1034:82d69e08660431770e24fc4e48c7206d","used":true,"who":"lxxself"}]
     */

    public boolean error;
    /**
     * _id : 5829408b421aa911dbc91566
     * createdAt : 2016-11-14T12:41:47.492Z
     * desc : 一图详解我的软件世界观与方法论
     * publishedAt : 2016-11-17T11:32:04.807Z
     * source : web
     * type : 拓展资源
     * url : https://www.processon.com/view/link/58288e3be4b00c4fc88dcadf
     * used : true
     * who : 王下邀月熊(Chevalier)
     */

    public List<ResultsBean> results;

    public static class ResultsBean implements Serializable {
        public String _id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String who;
        public List<String> images;
    }
}
