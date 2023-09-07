package com.zqzess.sift.infrastructure;

import com.zqzess.sift.vo.enums.ResultEnum;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/02 10:32
 * @Package com.zqzess.sift.infrastructure
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
public class Result<T> {
    private String code;
    private String msg;
    private String desc;
    private int status;
    private T data;

    public String getDecs() {
        return desc;
    }

    public void setDecs(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public static <T> Result<T> response(String code, int status, String msg, String desc, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setStatus(status);
        result.setMsg(msg);
        result.setData(data);
        result.setDecs(desc);
        return result;
    }

    public static <T> Result<T> success() {
        return response(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getStatus(), ResultEnum.SUCCESS.getMsg(), null, null);
    }

    public static <T> Result<T> success(String msg) {
        return response(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getStatus(), msg, null, null);
    }

    public static <T> Result<T> success(String msg, String desc) {
        return response(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getStatus(), msg, desc, null);
    }

    public static <T> Result<T> success(String msg, String desc, T data) {
        return response(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getStatus(), msg, desc, data);
    }

    public static <T> Result<T> fail() {
        return response(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getStatus(), ResultEnum.FAIL.getMsg(), null, null);
    }

    public static <T> Result<T> fail(String msg) {
        return response(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getStatus(), msg, null, null);
    }

    public static <T> Result<T> fail(String msg, String desc) {
        return response(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getStatus(), msg, desc, null);
    }

    public static <T> Result<T> fail(String msg, String desc, T data) {
        return response(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getStatus(), msg, desc, data);
    }

    public static <T> Result<T> response(ResultEnum resultCodeEnum, String desc, T data) {
        return response(resultCodeEnum.getCode(), resultCodeEnum.getStatus(), resultCodeEnum.getMsg(), desc, data);
    }

    public static <T> Result<T> loginFail() {
        return response(ResultEnum.LOGIN_ERROR.getCode(), ResultEnum.LOGIN_ERROR.getStatus(), ResultEnum.LOGIN_ERROR.getMsg(), null, null);
    }

    public static <T> Result<T> jwtAuthFail(String msg) {
        return response("403", 0, msg, null, null);
    }

    public static <T> Result<T> jwtExpired(String msg) {
        return response("401", -1, msg, null, null);
    }

    public static <T> Result<T> loginExpired() {
        return response(ResultEnum.LOGIN_EXPIRED.getCode(), ResultEnum.LOGIN_EXPIRED.getStatus(), ResultEnum.LOGIN_EXPIRED.getMsg(), null, null);
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", desc='" + desc + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }
}
