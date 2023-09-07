package com.zqzess.sift.infrastructure.execption;

import com.zqzess.sift.infrastructure.Result;
import org.springframework.security.core.AuthenticationException;
/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/11 17:42
 * @Package com.zqzess.sift.infrastructure.execption
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
public class JwtAuthenticationException extends AuthenticationException {


    public JwtAuthenticationException(String msg, Throwable cause) {
        super(msg);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
