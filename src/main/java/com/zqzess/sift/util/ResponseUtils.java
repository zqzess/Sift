package com.zqzess.sift.util;

import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/11 13:48
 * @Package com.zqzess.sift.util
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
public class ResponseUtils {

    public static void buildResponse(HttpServletResponse response, Object result, HttpStatus httpStatus) throws IOException {
        response.setContentType("application/json;charset=utf-8"); // 返回JSON
        response.setStatus(httpStatus.value());  // 状态码
        response.getWriter().write(JSONUtil.toJsonStr(result));
    }
}