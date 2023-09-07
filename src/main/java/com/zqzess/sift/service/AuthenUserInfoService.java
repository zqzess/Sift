package com.zqzess.sift.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zqzess.sift.vo.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Service;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/24 19:16
 * @Package com.zqzess.sift.service
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@Service
public class AuthenUserInfoService {
    SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();

    public String getLoginUserName() {
        return securityContextHolderStrategy.getContext().getAuthentication().getName();
    }

    public int getLoginUserRank() {
        Object o = securityContextHolderStrategy.getContext().getAuthentication().getPrincipal();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        LoginUser u = objectMapper.convertValue(o, LoginUser.class);
        return u.getUserrank();
    }
}
