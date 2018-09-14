package com.fims.common.utils;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/23.
 */
public class URLgetter {
    /**
     * 获取action的URL
     * @param params
     * @param rre
     */
    public static void getRealURL(Map<String, Object> params, HttpServletRequest rre) {
        System.out.println(JSONUtils.beanToJson(params));
        String url = null;
        url = rre.getScheme() + "://" + rre.getServerName()
                + ":" + rre.getServerPort()
                + rre.getServletPath();
        if (rre.getQueryString() != null) {
            url += "?" + rre.getQueryString();
        }

        System.out.println("!!!!!!!!!!!!");
        System.out.println(url);
        System.out.println(JSONUtils.beanToJson(params));
    }
}
