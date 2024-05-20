package org.mmj.stock.vo.resp;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)//用于忽略返回值为null的属性，当code为null时不会返回值。
public class R<T> implements Serializable {
    private static final long serialVersionUID = 7735505903525411467L;
    private static final int SUCCESS_CODE = 1;
    private static final int ERROR_CODE = 0;
    private int code;
    private String msg;
    private T data;

    private R(int code) {
        this.code = code;
    }

    private R(int code, T data) {
        this.code = code;
        this.data = data;
    }

    private R(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> R<T> ok() {
        return new R(1, "success");
    }

    public static <T> R<T> ok(String msg) {
        return new R(1, msg);
    }

    public static <T> R<T> ok(T data) {
        return new R(1, data);
    }

    public static <T> R<T> ok(String msg, T data) {
        return new R(1, msg, data);
    }

    public static <T> R<T> error() {
        return new R(0, "error");
    }

    public static <T> R<T> error(String msg) {
        return new R(0, msg);
    }

    public static <T> R<T> error(int code, String msg) {
        return new R(code, msg);
    }

    public static <T> R<T> error(ResponseCode res) {
        return new R(res.getCode(), res.getMessage());
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }
}
