package com.zqzess.sift.controller;

import com.zqzess.sift.infrastructure.Result;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/02 10:59
 * @Package com.zqzess.sift.controller
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@RestController
@RequestMapping("/v1/api/common")
@Transactional
public class SecurityController {
    @RequestMapping(value = "/needLogin", method = RequestMethod.GET)
    public Result needLogin() {
        return Result.loginExpired();
    }
}
