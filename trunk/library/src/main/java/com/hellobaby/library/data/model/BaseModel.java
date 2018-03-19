package com.hellobaby.library.data.model;

/**
 * Created by zwj on 2016/12/28.
 * description :
 * "code": "200",
 * "msg": "ok",
 * "data": {}|[]
 */

public class BaseModel<T> {
    protected String code;
    protected String msg;
    protected T data;

    public String getCode() {
        return code;
    }

    public boolean isSuccess() {
        return "200".equals(code);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 业务逻辑错误
     *
     * @return
     */
    public boolean isError() {
        return "400".equals(code);
    }

    /**
     * 服务器错误
     *
     * @return
     */
    public boolean isServerError() {
        return "500".equals(code);
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
