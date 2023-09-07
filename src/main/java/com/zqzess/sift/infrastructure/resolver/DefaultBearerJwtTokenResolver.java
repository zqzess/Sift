package com.zqzess.sift.infrastructure.resolver;

import com.zqzess.sift.infrastructure.AuthenticationConstants;
import com.zqzess.sift.infrastructure.execption.JwtAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/11 17:40
 * @Package com.zqzess.sift.infrastructure.resolver
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
public final class DefaultBearerJwtTokenResolver implements BearerTokenResolver {

    private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$",
            Pattern.CASE_INSENSITIVE);

//    private String bearerTokenHeaderName = HttpHeaders.AUTHORIZATION;
    private String bearerTokenHeaderName = AuthenticationConstants.TOKEN_HEADER;

    @Override
    public String resolve(final HttpServletRequest request) {
        return resolveFromAuthorizationHeader(request);
    }

    public void setBearerTokenHeaderName(String bearerTokenHeaderName) {
        this.bearerTokenHeaderName = bearerTokenHeaderName;
    }

    private String resolveFromAuthorizationHeader(HttpServletRequest request) {
        String authorization = request.getHeader(this.bearerTokenHeaderName);
        if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
            return null;
        }
        Matcher matcher = authorizationPattern.matcher(authorization);
        if (!matcher.matches()) {
            throw new JwtAuthenticationException("令牌格式错误");
        }
        return matcher.group("token");
    }
}
