package com.qing.admin.system.entity;

public class JsonResponse<T> {
    private String msg;
    private String code;
    private T data;

    public JsonResponse(String msg, String code) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResponse(T data) {
        this.code = "0";
        this.msg = "success";
        this.data = data;
    }

    public static JsonResponse<String> success() {
        return new JsonResponse<>(null);
    }

    public static JsonResponse<String> success(String data) {
        return new JsonResponse<>(data);
    }

    public static JsonResponse<String> fail() {
        return new JsonResponse<>("1", "fail");
    }

    public static JsonResponse<String> fail(String msg, String code) {
        return new JsonResponse<>(msg, code);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
