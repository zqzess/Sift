package com.zqzess.sift.infrastructure.execption;

import org.springframework.security.core.AuthenticationException;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/31 15:58
 * @Package com.zqzess.sift.infrastructure.execption
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
public class JwtExpiredException extends AuthenticationException {

    public JwtExpiredException(String msg, Throwable cause) {
        super(msg);
    }

    public JwtExpiredException(String msg) {
        super(msg);
    }

}
