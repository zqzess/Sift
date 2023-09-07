package com.zqzess.sift.infrastructure.resolver;

import jakarta.servlet.http.HttpServletRequest;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/11 17:39
 * @Package com.zqzess.sift.infrastructure.resolver
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@FunctionalInterface
public interface BearerTokenResolver {

    String resolve(HttpServletRequest request);
}
