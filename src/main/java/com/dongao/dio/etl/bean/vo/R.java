package com.dongao.dio.etl.bean.vo;

/**
 * @author 贾先笋
 * @date 2019/8/3 15:21
 * @description
 */
public class R {
    private int code;
    private String msg;
    private Object result;

    public R(int code, String msg, Object result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public R(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static R suceess() {
        return new R(200, "success", true);
    }

    public static R fail() {
        return new R(500, "fail", false);
    }

    public static R fail(String message) {
        return new R(500, message, false);
    }

    public static R error() {
        return new R(500, "An unknown error occurred.", null);
    }

    public static R error(int code, String msg) {
        return new R(code, msg);
    }

    public static R error(String msg) {
        return new R(500, msg, null);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
