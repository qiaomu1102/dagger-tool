package com.springdagger.web.common;

/**
 * @package: com.qiaomu.common.enums
 * @author: kexiong
 * @date: 2019/8/28 15:02
 * @Description: TODO
 */
public enum EnumStatusCode {

    SUCCESS(0,"success"),
    FAILURE(1, "failure");


    private int code;
    private String msg;

    EnumStatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
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
}


