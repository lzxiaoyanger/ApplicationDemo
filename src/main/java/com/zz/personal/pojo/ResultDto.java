package com.zz.personal.pojo;

import java.io.Serializable;

public class ResultDto<T> implements Serializable {

    /**
     * 请求状态，1成功 0失败
     */
    private Integer state;

    /**
     * 返回的数据
     */
    private T data;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 是否进行跳转
     */
    private boolean is_redirect = false;

    /**
     * 跳转地址
     */
    private String redirect_url;

    /**
     * token
     */
    private String token;

    public static <T> ResultDto<T> create() {
        ResultDto<T> result = new ResultDto<T>();
        return result;
    }

    public ResultDto<T> success(T data) {
        this.state = 1;
        this.data = data;
        return this;
    }

    public ResultDto<T> fail(String msg) {
        this.state = 0;
        this.msg = msg;
        return this;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isIs_redirect() {
        return is_redirect;
    }

    public void setIs_redirect(boolean is_redirect) {
        this.is_redirect = is_redirect;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
