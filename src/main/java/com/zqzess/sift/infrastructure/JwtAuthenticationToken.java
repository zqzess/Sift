package com.zqzess.sift.infrastructure;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;
/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/11 20:54
 * @Package com.zqzess.sift.infrastructure
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 600L;
    private final Object principal;
    private Object credentials;

    public JwtAuthenticationToken(Object principal, Object credentials) {
        super((Collection)null);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    public static JwtAuthenticationToken unauthenticated(Object principal, Object credentials) {
        return new JwtAuthenticationToken(principal, credentials);
    }

    public static JwtAuthenticationToken authenticated(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        return new JwtAuthenticationToken(principal, credentials, authorities);
    }

    public Object getCredentials() {
        return this.credentials;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
