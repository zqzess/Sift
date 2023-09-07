package com.zqzess.sift.infrastructure.filter;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSONException;
import com.zqzess.sift.infrastructure.JwtAuthenticationToken;
import com.zqzess.sift.infrastructure.Result;
import com.zqzess.sift.infrastructure.AuthenticationConstants;
import com.zqzess.sift.infrastructure.execption.JwtAuthenticationException;
import com.zqzess.sift.infrastructure.resolver.BearerTokenResolver;
import com.zqzess.sift.util.ResponseUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/11 17:55
 * @Package com.zqzess.sift.infrastructure.filter
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@Getter
@Setter
public class JwtTokenSecurityContextHolderFilter extends GenericFilterBean {

    private static final String FILTER_APPLIED = JwtTokenSecurityContextHolderFilter.class.getName() + ".APPLIED";
    private BearerTokenResolver bearerTokenResolver;
//    private JwtValidator jwtValidator = new HutoolJwtValidator();
    private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();
    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();

    private UserDetailsService userDetailsService;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (request.getAttribute(FILTER_APPLIED) != null) {
            chain.doFilter(request, response);
        } else {
            String token;
            request.setAttribute(FILTER_APPLIED, Boolean.TRUE);
            // 1. 解析请求中的令牌
            try {
                token = this.bearerTokenResolver.resolve(request);
            } catch (JwtAuthenticationException e) {
                ResponseUtils.buildResponse(response, Result.jwtAuthFail("token解析错误"), HttpStatus.UNAUTHORIZED);
                return;
            }
            // 2. 令牌不存在，直接进入下一个过滤器
            if (token == null) {
                chain.doFilter(request, response);
            } else {
                // 3. 存在令牌
                try {
                    // 3.1 校验
                    boolean verify = JWTUtil.verify(token, AuthenticationConstants.JWT_KEY.getBytes());
                    if (!verify) {
                       throw new JwtAuthenticationException("token非法");
                    }
                    // 已过期
                    final JWT jwt = JWTUtil.parseToken(token);
                    jwt.getHeader(JWTHeader.TYPE);
                    long expireTime = (long) jwt.getPayload("expire_time");
                    if (System.currentTimeMillis() >= expireTime) {
                        throw new JwtAuthenticationException("token已失效");
                    }
                    String userName = (String) jwt.getPayload("username");
                    // 3.2 根据标识组装用户信息，实际开发可以使用缓存
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                    // 3.3 组装认证信息
                    JwtAuthenticationToken authentication = JwtAuthenticationToken.authenticated(userDetails, token, userDetails.getAuthorities());
                    // 3.3 创建安装上下文
                    SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
                    context.setAuthentication(authentication);
                    // 3.4 保存到Holder、存储中
                    this.securityContextHolderStrategy.setContext(context);
                    this.securityContextRepository.saveContext(context, request, response);
                    chain.doFilter(request, response);
                } catch (JWTException | JSONException | JwtAuthenticationException e) {

                    ResponseUtils.buildResponse(response,
                            Result.jwtExpired(e.getMessage()),
                            HttpStatus.UNAUTHORIZED);
                } finally {
                    request.removeAttribute(FILTER_APPLIED);
                }
            }
        }
    }
}
