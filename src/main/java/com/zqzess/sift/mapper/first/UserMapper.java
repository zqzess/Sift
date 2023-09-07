package com.zqzess.sift.mapper.first;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zqzess.sift.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/07/26 17:17
 * @Package com.zqzess.sift.mapper
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@Mapper
public interface UserMapper{
    List<User> findAllUser();
    User findUserById(Integer id);
    User findUserByName(String userName);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUserById(Integer id);
}
