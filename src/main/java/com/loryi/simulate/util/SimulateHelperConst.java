package com.loryi.simulate.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: loryi
 * @description: 模拟接口相关变量
 * @create: 2020/04/09 10:37
 **/
public class SimulateHelperConst {

    /**
     * 获取静态文件中的请求接口数据
     */
    public static JSONObject urlJson = CustomizingJsonUtil.getJsonObjByJsonFile("/data/simulate.json");

}
