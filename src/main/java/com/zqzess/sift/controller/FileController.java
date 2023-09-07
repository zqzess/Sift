package com.zqzess.sift.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zqzess.sift.infrastructure.Result;
import com.zqzess.sift.service.AuthenUserInfoService;
import com.zqzess.sift.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/16 14:01
 * @Package com.zqzess.sift.controller
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@RestController
@RequestMapping("/v1/file")
@Slf4j
@CrossOrigin
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private AuthenUserInfoService userInfoService;

    @RequestMapping("/upload")
    @ResponseBody
    public String imageUpload(MultipartFile file, HttpServletRequest request) throws IllegalStateException, JsonProcessingException {
        String strBackUrl = "http://" + request.getServerName() + ":" + request.getServerPort();
        Result<?> rs = fileService.insertFile(file);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(rs);
    }

    @GetMapping("/old/list")
    @ResponseBody
    public ResponseEntity<?> getFileListOld(@RequestParam(name = "offset") int offset, @RequestParam(name = "limit") int limit) throws IllegalStateException, JsonProcessingException {
        Result<?> rs = fileService.getFileListByPageOld(offset, limit);
        return ResponseEntity.ok(rs);
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<?> getFileList(@RequestParam(name = "page") int page, @RequestParam(name = "pageSize") int pageSize) throws IllegalStateException, JsonProcessingException {
        Result<?> rs = fileService.getFileListByPage(page, pageSize);
        return ResponseEntity.ok(rs);
    }
}
