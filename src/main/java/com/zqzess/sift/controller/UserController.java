package com.zqzess.sift.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zqzess.sift.bean.User;
import com.zqzess.sift.infrastructure.Result;
import com.zqzess.sift.service.UserService;
import com.zqzess.sift.util.StringUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/07/27 10:37
 * @Package com.zqzess.sift.controller
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@RestController
@RequestMapping("/v1/api/user")
@Slf4j
@Validated
public class UserController {
    @Autowired
    UserService userService;

    /**
     *  查询用户是否已经注册
     * @param userName
     * @return
     */
    @GetMapping("/checkUserName")
    @ResponseBody
    public ResponseEntity<?> checkUser(@RequestParam String userName) {
        if(!StringUtil.isNotEmpty(userName)) {
            return ResponseEntity.ok(Result.fail(" 参数不能为空"));
        }
        User user = userService.findUserByName(userName);
        if (user != null) {
            return ResponseEntity.ofNullable(Result.fail("用户已存在"));
        }
        else {
            return ResponseEntity.ok(Result.success());
        }
    }

}
