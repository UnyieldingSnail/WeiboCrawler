package com.zhangyujie.poms.entity;

import java.io.Serializable;

public class Result implements Serializable {
    //0 success 1 error
    private int status = 0;
    private String msg;
    private Object data;

    public Result() {
        super();
    }

    public Result(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result [status=" + status + ", msg=" + msg + ", data=" + data + "]";
    }


}
