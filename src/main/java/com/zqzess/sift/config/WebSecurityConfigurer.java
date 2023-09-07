package com.zqzess.sift.config;

import com.zqzess.sift.infrastructure.AuthenticationEntry;
import com.zqzess.sift.infrastructure.handler.JsonAuthenticationFailureHandler;
import com.zqzess.sift.infrastructure.handler.JwtTokenAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.Collections;

import static com.zqzess.sift.config.JwtAuthenticationConfigurer.jwtAuth;
import static org.springframework.security.config.Customizer.withDefaults;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/03 15:25
 * @Package com.zqzess.sift.config
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@Configuration
@EnableWebSecurity(debug = false)
public class WebSecurityConfigurer{
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web
                .debug(false) // 开启DEBUG
                //.httpFirewall() // 防火请
                //.expressionHandler() // 注解权限表达式处理器
                .ignoring().requestMatchers("/resources/**", "/static/**"); // 配置放行路径，不会经过过滤器一般用于静态资源
    }

    @Bean
    @Order(0) // 过滤器链，定义顺序，数值越小越靠前
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((authorize) -> authorize
//                .anyRequest().authenticated()
//        );
//        http.securityMatcher("/v2/api/**") // 过滤器链匹配路径
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().hasRole("ADMIN"));

//        http
//                //以下是验证请求拦截和放行配置
//                .authorizeHttpRequests(auth -> {
//                    auth.anyRequest().authenticated();    //将所有请求全部拦截，一律需要验证
//                })
//                //以下是表单登录相关配置
//                .formLogin(conf -> {
//                    conf.loginPage("/login");   //将登录页设置为我们自己的登录页面
//                    conf.loginProcessingUrl("/doLogin"); //登录表单提交的地址，可以自定义
//                    conf.defaultSuccessUrl("/");   //登录成功后跳转的页面
//                    conf.permitAll();    //将登录相关的地址放行，否则未登录的用户连登录界面都进不去
//                    //用户名和密码的表单字段名称，不过默认就是这个，可以不配置，除非有特殊需求
//                    conf.usernameParameter("username");
//                    conf.passwordParameter("password");
//                });
        http.authorizeHttpRequests(auth->{
            auth.requestMatchers("/v1/api/user/checkUserName").permitAll();
//            auth.requestMatchers("/sift/file/**").permitAll();
            auth.anyRequest().authenticated();
        });
        http.formLogin(conf->{
            conf.successHandler(new JwtTokenAuthenticationSuccessHandler());
            conf.failureHandler(new JsonAuthenticationFailureHandler());
            conf.usernameParameter("userName");
            conf.passwordParameter("userPassword");
        });
        http.httpBasic(withDefaults());
        http.exceptionHandling(cf -> {
            cf.authenticationEntryPoint(new AuthenticationEntry());
        });
//        http.addFilterAfter(new JwtTokenSecurityContextHolderFilter3(), UsernamePasswordAuthenticationFilter.class);
        http.apply(jwtAuth());
        http.csrf(cf-> cf.disable());
        // 如果没有单独配置cors，则默认使用spring mvc的cors配置
//        http.cors(withDefaults());
        http.cors(cs->cs.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        return authenticationManager;
    }
    @Bean

    public CorsConfigurationSource corsConfigurationSource() {

//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowCredentials(true);
//        configuration.addExposedHeader("*");
//        configuration.addAllowedMethod(HttpMethod.OPTIONS);
//        configuration.addAllowedMethod(HttpMethod.GET);
//        configuration.addAllowedMethod(HttpMethod.POST);
//        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5173", "http://localhost:5173"));
//        configuration.addAllowedOrigin("http://localhost:5173");
//        configuration.addAllowedOriginPattern("*");
//        configuration.setMaxAge(Duration.ofHours(1));
//        source.registerCorsConfiguration("/**", configuration);
//        return source;

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
//        configuration.addAllowedOriginPattern("http://localhost:8088");
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setMaxAge(Duration.ofHours(1));
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}

