package com.duanyao.music.util;

public enum ResponseCode {
    SUCCESS(0, "SUCCESS"),

    ERROR(1, "ERROR");

    private int code;

    private String msg;

    ResponseCode(int code, String msg) {

        this.code = code;

        this.msg = msg;

    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}