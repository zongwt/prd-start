package com.prd.result;

/**
 * @Author: fb
 * @Description 数据返回JSON格式
 * @Date: Create in 13:43 2018/2/5
 * @Modified By
 */
public class DataJsonResult {

    private String code;//错误代码

    private String msg;//错误信息描述

    private String ret;//请求是否成功

    private Object data;//返回的数据

    public DataJsonResult(){

    }

    public DataJsonResult(String code, String msg, String ret, Object data) {
        this.code = code;
        this.msg = msg;
        this.ret = ret;
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataJsonResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", ret='" + ret + '\'' +
                ", data=" + data +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
