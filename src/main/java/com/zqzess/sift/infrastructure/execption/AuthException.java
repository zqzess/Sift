package com.zqzess.sift.infrastructure.execption;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/02 11:14
 * @Package com.zqzess.sift.infrastructure.execption
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
public class AuthException extends InternalAuthenticationServiceException {
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_MSG = "用户认证失败！";
    private String msg;

    public AuthException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public AuthException() {
        super(DEFAULT_MSG);
        this.msg = DEFAULT_MSG;
    }

    public AuthException(String msg, Throwable t) {
        super(msg, t);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
