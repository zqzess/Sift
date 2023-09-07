package com.zqzess.sift.infrastructure;

import cn.hutool.json.JSONUtil;
import com.zqzess.sift.vo.enums.ResultEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/02 10:35
 * @Package com.zqzess.sift.infrastructure
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@Component
public class AuthenticationEntry implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(401);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
//        response.getOutputStream().write("用户未登录".getBytes());
        out.write(JSONUtil.toJsonStr(Result.response(ResultEnum.NEED_LOGIN, null, null)));
        out.flush();
        out.close();
    }
}
