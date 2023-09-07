package com.zqzess.sift.vo;

import com.zqzess.sift.bean.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/01 16:50
 * @Package com.zqzess.sift.vo
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails, Serializable {
    private User user; //用户信息实体类，里面存储着用户的账号，密码，权限信息
    //返回权限信息
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    //返回用户密码
    @Override
    public String getPassword() {
        return user.getUserPassword();
    }
    //返回用户账号
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    public int getUserrank() {
        return user.getUserRank();
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
