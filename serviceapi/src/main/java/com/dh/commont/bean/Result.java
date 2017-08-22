package com.dh.commont.bean;

import java.io.Serializable;

/**
 * Created by wangbin on 14-10-17.
 */
public class Result implements Serializable {
    private String m = "";//请求接口返回的消息 类型为字符串

    private String c;//请求接口成功或失败 0:失败  1 成功

    private Object o = new Object();//请求接口主要返回的数据 类型不定

    private Object e = new Object();//其他返回数据 请求接口额外返回的对象  类型不定

    private String r; //请求接口的错误状态码  请求成功为NULL

    public Result(String c) {
        this.c = c;
    }

    public Result(Boolean c) {
        if (c) {
            this.c = "1";
        } else {
            this.c = "0";
        }
    }


    public Result error(String r) {
        this.r = r;
        return this;
    }

    public Result msg(String m) {
        this.m = m;
        return this;
    }


    public Result data(Object o) {
        this.o = o;
        return this;
    }

    public Result otherData(Object e) {
        this.e = e;
        return this;
    }


    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }

    public Object getE() {
        return e;
    }

    public void setE(Object e) {
        this.e = e;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }
}
