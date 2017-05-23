package com.go.cqh.goodprojects.entry;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caoqianghui on 2017/3/9.
 */

public class AiBean implements Serializable {


    /**
     * code : 302000
     * text : 亲，已帮您找到相关新闻
     * list : [{"article":"美国务卿将访华 韩媒期待其劝中国停止\u201c报复\u201d","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/news/transform/20170309/CGYO-fychhuq3357697.jpg/w160h120l1t142b.jpg","detailurl":"http://news.sina.cn/2017-03-09/detail-ifychihc5896811.d.html?vt=4&pos=3"},{"article":"二孩家庭或将少缴税 外媒：税负考虑开支更公平","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/news/transform/20170309/Dk_2-fychhvn7877130.jpg/w160h120l1t1e1c.jpg","detailurl":"http://news.sina.cn/gn/2017-03-09/detail-ifychhuq3337770.d.html?vt=4&pos=3"},{"article":"揭秘两会\u201c喜感哥\u201d：连续4年追问部长的博士","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/news/transform/20170309/tqly-fychhuq3358267.jpg/w160h120l1t1c5b.jpg","detailurl":"http://news.sina.cn/gn/2017-03-09/detail-ifychhus0110835.d.html?vt=4&pos=3"},{"article":"美国中情局被曝入侵智能设备 三星电视变窃听器","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/news/transform/20170309/ZSp8-fychhvn7875280.jpg/w160h120l1t118c.jpg","detailurl":"http://news.sina.cn/gj/2017-03-09/detail-ifychhuq3334677.d.html?vt=4&pos=3"},{"article":"中国外长\u201c金句\u201d：台所谓\u201c外交关系\u201d没前途","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/news/transform/20170309/AQP--fychhvn7938913.jpg/w160h120l1t10d2.jpg","detailurl":"http://news.sina.cn/2017-03-09/detail-ifychavf2110088.d.html?vt=4&pos=3"},{"article":"无人机黑飞盛行 有商家改写GPS坐标破解禁飞区","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/news/crawl/20170309/srsA-fycapec3734559.jpg/w160h120l1t1533.jpg","detailurl":"http://news.sina.cn/gn/2017-03-09/detail-ifychhuq3350837.d.html?vt=4&pos=3"},{"article":"云南副省长被坑 省长强推\u201c史上最严\u201d措施整治","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/news/transform/20170308/g_AK-fycapec3699524.jpg/w160h120l1t113e.jpg","detailurl":"http://news.sina.cn/gn/2017-03-08/detail-ifychavf2090387.d.html?vt=4&pos=3"},{"article":"韩宪法法院10日上午宣判朴槿惠弹劾案","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/news/transform/20170308/jsBC-fychhuq3302931.jpg/w160h120l1t19ab.jpg","detailurl":"http://news.sina.cn/2017-03-08/detail-ifychavf2068358.d.html?vt=4&pos=3"},{"article":"人大法工委:港区人大代表应遵守宪法 没为什么","source":"新浪新闻","icon":"","detailurl":"http://news.sina.cn/2017-03-09/detail-ifychihc5955961.d.html?vt=4&pos=3"},{"article":"商务部:坚决反对美国用国内法对中企实施制裁","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/news/crawl/20170309/nOBh-fychhvn7942215.jpg/w160h120l1t1076.jpg","detailurl":"http://news.sina.cn/gn/2017-03-09/detail-ifychhuq3416900.d.html?vt=4&pos=3"},{"article":"女子给朋友帮忙打美容针 致人左眼失明","source":"新浪新闻","icon":"","detailurl":"http://photo.sina.cn/album_1_2841_108547.htm?vt=4&pos=3&ch=1"},{"article":"老太曾因走私入狱 81岁再次成为千万富翁","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/news/crawl/20170309/Rxkz-fychhus0185464.jpg/w160h120l1t1f2b.jpg","detailurl":"http://news.sina.cn/sh/2017-03-09/detail-ifychhuq3411045.d.html?vt=4&pos=3"},{"article":"外媒:\u201c萨德\u201d最早下月运行 中俄反制或将升级","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/news/transform/20170309/Gnbq-fychhuq3408859.jpg/w160h120l1t17f5.jpg","detailurl":"http://news.sina.cn/gj/2017-03-09/detail-ifychhuq3409024.d.html?vt=4&pos=3"},{"article":"台学者:蔡英文\u201c自我殖民\u201d史观会致台分崩离析","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/translate/20170309/da08-fychhvn7920100.jpg/w160h120l1t1471.jpg","detailurl":"http://news.sina.cn/gn/2017-03-09/detail-ifychavf2151706.d.html?vt=4&pos=3"},{"article":"菲律宾总统任命马纳洛为代理外交部长","source":"新浪新闻","icon":"","detailurl":"http://news.sina.cn/gj/2017-03-09/detail-ifychhuq3384766.d.html?vt=4&pos=3"},{"article":"美国第一夫人梅拉尼娅妇女节在白宫宴请宾客","source":"新浪新闻","icon":"","detailurl":"http://photo.sina.cn/album_1_2841_108541.htm?vt=4&pos=3&ch=1"},{"article":"美国务院首场发布会:21次提中国 尊重\u201c一中\u201d","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/news/crawl/20170309/CkZ0-fychhuq3381350.jpg/w160h120l1t12d6.jpg","detailurl":"http://news.sina.cn/gj/2017-03-09/detail-ifychhus0156787.d.html?vt=4&pos=3"},{"article":"日媒:朝鲜所射导弹或落入迄今距日本土最近处","source":"新浪新闻","icon":"","detailurl":"http://news.sina.cn/2017-03-09/detail-ifychihc5935210.d.html?vt=4&pos=3"},{"article":"美国助理国务卿:\u201c川蔡通话\u201d只是温情支持","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/translate/20170309/PllS-fycapec3761686.jpg/w160h120l1t12a2.jpg","detailurl":"http://news.sina.cn/gn/2017-03-09/detail-ifychavf2146748.d.html?vt=4&pos=3"},{"article":"9岁男孩烧伤食道 自己从肚皮注射米汤续命","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/news/crawl/20170309/ArvT-fychhus0152283.jpg/w160h120l1t1d15.jpg","detailurl":"http://news.sina.cn/sh/2017-03-09/detail-ifychhus0152776.d.html?vt=4&pos=3"},{"article":"韩将现场直播朴槿惠弹劾案 明日上午11时见分晓","source":"新浪新闻","icon":"","detailurl":"http://news.sina.cn/2017-03-09/detail-ifychavf2142954.d.html?vt=4&pos=3"},{"article":"轿车起火烧死2岁女童 父亲无法施救跪地痛哭","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/news/crawl/20170309/Nm0p-fychhus0146895.jpg/w160h120l1t1f1d.jpg","detailurl":"http://news.sina.cn/sh/2017-03-09/detail-ifychhus0147305.d.html?vt=4&pos=3"},{"article":"韩国推动欧洲第1座慰安妇和平少女像在德国落成","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/news/transform/20170309/R9uc-fychhus0148919.jpg/w160h120l1t1919.jpg","detailurl":"http://news.sina.cn/2017-03-09/detail-ifychihc5924292.d.html?vt=4&pos=3"},{"article":"少女整日照料弟妹郁闷跳江 父亲:让她死掉好了","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/news/transform/20170309/9h07-fychhus0132585.jpg/w160h120l1t1b36.jpg","detailurl":"http://news.sina.cn/sh/2017-03-09/detail-ifychhus0133460.d.html?vt=4&pos=3"},{"article":"坑娘坑总统的崔顺实之女 高中学历被取消变初中","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/news/transform/20170309/yFAi-fychhuq3355022.jpg/w160h120l1t1b19.jpg","detailurl":"http://news.sina.cn/gj/2017-03-09/detail-ifychhuq3356068.d.html?vt=4&pos=3"},{"article":"\u201c占中\u201d推手自曝与美鹰派政客密谈 被骂当汉奸","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/translate/20170309/W0sP-fycapec3742201.jpg/w160h120l1t1273.jpg","detailurl":"http://news.sina.cn/gn/2017-03-09/detail-ifychihc5917376.d.html?vt=4&pos=3"},{"article":"维基解密曝CIA窃听全球智能设备 美方拒回应","source":"新浪新闻","icon":"","detailurl":"http://news.sina.cn/2017-03-09/detail-ifychihc5913173.d.html?vt=4&pos=3"},{"article":"台情报高官赴宴时伸咸猪手 骚扰卸任女官员","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/translate/20170309/DLvN-fychhuq3355360.jpg/w160h120l1t1df1.jpg","detailurl":"http://news.sina.cn/2017-03-09/detail-ifychihc5914100.d.html?vt=4&pos=3"},{"article":"西班牙这个中式酒店 你敢住么？","source":"新浪新闻","icon":"","detailurl":"http://photo.sina.cn/album_1_2841_108533.htm?vt=4&pos=3&ch=1"},{"article":"震撼人心：知名摄影师深陷毒瘾全过程","source":"新浪新闻","icon":"","detailurl":"http://photo.sina.cn/album_1_2841_108529.htm?vt=4&pos=3&ch=1"},{"article":"戈兰高地女兵:枪不离身容颜姣好","source":"新浪新闻","icon":"","detailurl":"http://photo.sina.cn/album_1_2841_108527.htm?vt=4&pos=3&ch=1"},{"article":"辣妈一年狂瘦231斤 帮助胖友获6万余粉丝","source":"新浪新闻","icon":"","detailurl":"http://photo.sina.cn/album_1_2841_108526.htm?vt=4&pos=3&ch=1"},{"article":"老人代女儿在公园相亲3年:先看父母颜值","source":"新浪新闻","icon":"https://ks.sinaimg.cn/n/news/crawl/20170309/8XP1-fychhvn7883085.jpg/w160h120l1t1f94.jpg","detailurl":"http://news.sina.cn/sh/2017-03-09/detail-ifychhuq3346517.d.html?vt=4&pos=3"}]
     */

    public int code;
    public boolean isMyMsg;
    public String text;
    public String url;//图片类，列车类，航班类
    public List<ListBean> list;//新闻类，菜谱类

    public static class ListBean implements Serializable {
        /**
         * article : 美国务卿将访华 韩媒期待其劝中国停止“报复”
         * source : 新浪新闻
         * icon : https://ks.sinaimg.cn/n/news/transform/20170309/CGYO-fychhuq3357697.jpg/w160h120l1t142b.jpg
         * detailurl : http://news.sina.cn/2017-03-09/detail-ifychihc5896811.d.html?vt=4&pos=3
         */

        public String article;//新闻标题
        public String source;//新浪新闻
        public String icon;//信息图标，新闻图片 电影图标
        public String detailurl;//详情链接 电影介绍地址

        public String code;//菜谱类标识码
        public String text;//提示语
        public String name;//菜名 电影名
        public String info;//菜谱信息
    }
}
