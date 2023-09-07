package com.zqzess.sift.vo.enums;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/11 13:28
 * @Package com.zqzess.sift.vo.enums
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
public enum ResultEnum {
    LOGIN_SUCCESS("200", 1, "登录成功!"),
    LOGIN_EXPIRED("401", -1, "登录过期!"),
    NEED_LOGIN("401", 0, "用户未登录!"),
    LOGIN_ERROR("903", 0, "用户名或密码错误"),
    LOGOUT_SUCCESS("200", 1, "注销成功"),
    OPERATION_ERROR("503", -1, "操作失败！"),
    OPERATION_SUCCESS("200", 1, "操作成功!"),
    OPERATION_DENIED("403", 0, "无权限！"),
    SUCCESS("200", 1, "OK"),
    FAIL("400", 0, "FAIL");

    private String code;
    private int status;
    private String msg;

    ResultEnum(String code, int status, String msg) {
        this.code = code;
        this.status = status;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}
