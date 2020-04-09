package com.loryi.simulate.vo;

/**
 * @author: loryi
 * @description: 默认请求返回类
 * @create: 2020/04/09 14:31
 **/
public class JsonResult<T> {

    public static final String CODE_SUCCESS = "0";
    public static final String CODE_FAILUER = "1";

    private String code;
    private String message;
    private T data;

    public JsonResult(String code, String message){
        this.code = code;
        this.message = message;
    }

    public static JsonResult success(String message){
        return new JsonResult(CODE_SUCCESS, message);
    }

    public static JsonResult failure(String message){
        return new JsonResult(CODE_FAILUER, message);
    }

    public JsonResult(String code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
