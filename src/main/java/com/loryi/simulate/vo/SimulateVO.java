package com.loryi.simulate.vo;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: loryi
 * @description: 模拟类
 * @create: 2020/04/08 08:42
 **/
public class SimulateVO {

    /**请求路径*/
    protected String path;

    /**请求描述*/
    protected String desc;

    /**请求方式*/
    protected String method;

    /**请求返回内容*/
    protected String result;

    /**请求参数定义*/
    protected String params;

    /**请求体定义*/
    protected String body;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
