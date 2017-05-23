package com.go.cqh.goodprojects.entry;

import java.util.List;

/**
 * Created by caoqianghui on 2017/1/19.
 */

public class CtsBean {

    /**
     * showapi_res_body : {"list":["好事枉朝轩，好日当秋半。学道北海仙，习之势翩翩。","好妇惟相妒，好此结茅庐。学剑来山东，习习九门通。","好胜耽长行，好酒浓且清。学彼长生道，习定至中宵。","好古笑流俗，好音怜铩羽。学筵尊授几，习战亦开池。","好勇复知机，好物随人秘。学彼长生道，习定至中宵。","好风吹长条，好闲容问道。学剑觅封侯，习性防已后。"],"ret_code":0}
     * showapi_res_code : 0
     * showapi_res_error :
     */

    public ShowapiResBodyBean showapi_res_body;
    public int showapi_res_code;
    public String showapi_res_error;

    public static class ShowapiResBodyBean {
        /**
         * list : ["好事枉朝轩，好日当秋半。学道北海仙，习之势翩翩。","好妇惟相妒，好此结茅庐。学剑来山东，习习九门通。","好胜耽长行，好酒浓且清。学彼长生道，习定至中宵。","好古笑流俗，好音怜铩羽。学筵尊授几，习战亦开池。","好勇复知机，好物随人秘。学彼长生道，习定至中宵。","好风吹长条，好闲容问道。学剑觅封侯，习性防已后。"]
         * ret_code : 0
         */

        public int ret_code;
        public List<String> list;
    }
}
