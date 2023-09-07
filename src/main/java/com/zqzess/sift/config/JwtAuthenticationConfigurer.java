package com.zqzess.sift.config;

import com.zqzess.sift.infrastructure.filter.JwtTokenSecurityContextHolderFilter;
import com.zqzess.sift.infrastructure.resolver.BearerTokenResolver;
import com.zqzess.sift.infrastructure.resolver.DefaultBearerJwtTokenResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/11 17:37
 * @Package com.zqzess.sift.config
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
public class JwtAuthenticationConfigurer extends AbstractHttpConfigurer<JwtAuthenticationConfigurer, HttpSecurity> {

    private BearerTokenResolver bearerTokenResolver;

    public JwtAuthenticationConfigurer() {
    }

    @Override
    public void init(HttpSecurity http) throws Exception {
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 1. 全局禁用 Session
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).disable()
        );
        // 2. 创建过滤器
        JwtTokenSecurityContextHolderFilter filter = new JwtTokenSecurityContextHolderFilter();
        // 用户信息查询类
        UserDetailsService userDetailService = http.getSharedObject(ApplicationContext.class).getBean(UserDetailsService.class);
        filter.setUserDetailsService(userDetailService);
        // 令牌解析器
        BearerTokenResolver bearerTokenResolver = getBearerTokenResolver(http);
        filter.setBearerTokenResolver(bearerTokenResolver);
        // 后置处理
        filter = postProcess(filter);
        // SecurityContextHolder策略
        filter.setSecurityContextHolderStrategy(getSecurityContextHolderStrategy());
        // 3. 添加过滤器
//        http.addFilterAfter(filter, SecurityContextHolderFilter.class);
        http.addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
    }

    public static JwtAuthenticationConfigurer jwtAuth() {
        return new JwtAuthenticationConfigurer();
    }

    BearerTokenResolver getBearerTokenResolver(HttpSecurity http) {
        ApplicationContext context = http.getSharedObject(ApplicationContext.class);
        if (this.bearerTokenResolver == null) {
            if (context.getBeanNamesForType(BearerTokenResolver.class).length > 0) {
                this.bearerTokenResolver = context.getBean(BearerTokenResolver.class);
            } else {
                this.bearerTokenResolver = new DefaultBearerJwtTokenResolver();
            }
        }
        return this.bearerTokenResolver;
    }
}
