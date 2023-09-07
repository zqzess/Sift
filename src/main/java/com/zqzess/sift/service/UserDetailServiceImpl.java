package com.zqzess.sift.service;

import com.zqzess.sift.vo.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.zqzess.sift.bean.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/01 16:51
 * @Package com.zqzess.sift.service
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;


    @Override
    public LoginUser loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.warn(userName + "用户登陆");
        //根据用户名从数据库中查询数据信息
//        Object ob = userMapper.selectOne(queryWrapper);
//        User user = JSON.parseObject(JSON.toJSONString(ob),User.class);
        User user = userService.findUserByName(userName);
        //查询不到该用户信息抛异常
        if(user == null){
            throw new UsernameNotFoundException(userName + "未找到");
        }
        else {
            // 2. 设置权限集合，后续需要数据库查询（授权篇讲解）
            List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList("role");
            // 3. 返回UserDetails类型用户
            return new LoginUser(user); // 账号状态这里都直接设置为启用，实际业务可以存在数据库中
        }
    }


//    @Override
//    public PearlUserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        //根据用户名从数据库中查询数据信息
////        Object ob = userMapper.selectOne(queryWrapper);
////        User user = JSON.parseObject(JSON.toJSONString(ob),User.class);
//        User user = userService.findUserByName(userName);
//        //查询不到该用户信息抛异常
//        if(user == null){
//            throw new UsernameNotFoundException(userName + "未找到");
//        }
//        else {
//            // 2. 设置权限集合，后续需要数据库查询（授权篇讲解）
//            List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList("role");
//            // 3. 返回UserDetails类型用户
//            return new PearlUserDetails(userName, user.getPassWord(), user.getUserRank(), authorityList,
//                    true, true, true, true); // 账号状态这里都直接设置为启用，实际业务可以存在数据库中
//        }
//    }
}
