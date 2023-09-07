package com.zqzess.sift.infrastructure.handler;

import cn.hutool.json.JSONUtil;
import com.zqzess.sift.infrastructure.AuthenticationConstants;
import com.zqzess.sift.infrastructure.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import cn.hutool.jwt.JWTUtil;
import org.springframework.stereotype.Component;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/03 17:20
 * @Package com.zqzess.sift.infrastructure.handler
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
public class JwtTokenAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8"); // 返回JSON
        response.setStatus(HttpStatus.OK.value());  // 状态码 200
        // 封装为JWT
        Map<String, Object> jwtMap = new HashMap<>();
        jwtMap.put("username", authentication.getName()); // 用户名
        jwtMap.put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * AuthenticationConstants.expiredTime); // 过期时间，expiredTime小时后过期, 1000*60*60 = 1h
        jwtMap.put("authentication", authentication); // 认证信息
        String token = JWTUtil.createToken(jwtMap, AuthenticationConstants.JWT_KEY.getBytes());
        Map<String, String> data = new HashMap<>();
        data.put("token", "Bearer " + token);
        response.getWriter().write(JSONUtil.toJsonStr(Result.success(" 登录成功", null, data)));
    }
}
