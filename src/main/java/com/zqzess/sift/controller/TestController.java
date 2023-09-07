package com.zqzess.sift.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zqzess.sift.infrastructure.Result;
import com.zqzess.sift.service.AuthenUserInfoService;
import com.zqzess.sift.service.FileService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/01 16:42
 * @Package com.zqzess.sift.controller
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@CrossOrigin
@RestController
@RequestMapping("/v1/test")
@Slf4j
public class TestController {

    @Resource
    AuthenUserInfoService userInfoService;

    @Resource
    FileService fileService;

    @GetMapping("getUserName")
    @ResponseBody
    public String checkUser() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(userInfoService.getLoginUserRank());
    }

    @GetMapping("getFile")
    @ResponseBody
    public ResponseEntity<?> getFile(@RequestParam Integer fileId) {
        return ResponseEntity.ok(Result.success(null, null, fileService.getFileByFileId(fileId)));
    }
}
