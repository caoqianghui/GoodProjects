package com.go.cqh.goodprojects.entry;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caoqianghui on 2017/1/13.
 */

public class SexImgBean implements Serializable {

    public int showapi_res_code;
    public String showapi_res_error;
    public ShowapiResBodyBean showapi_res_body;

    public SexImgBean() {
    }

    public static class ShowapiResBodyBean implements Serializable {

        public int code;
        public String msg;
        public List<NewslistBean> newslist;

        public static class NewslistBean implements Serializable {

            public String title;
            public String picUrl;
            public String description;
            public String ctime;
            public String url;
        }
    }
}
