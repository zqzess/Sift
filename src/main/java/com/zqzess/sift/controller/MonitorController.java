package com.zqzess.sift.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zqzess.sift.service.monitor.SystemMonitor;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/30 11:26
 * @Package com.zqzess.sift.controller
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@RestController
@RequestMapping("/v1/monitor")
@Slf4j
@CrossOrigin
public class MonitorController {
    @Resource
    SystemMonitor cpuMonitor;

    @RequestMapping("/sys")
    @ResponseBody
    public ResponseEntity<?> getHostSystemInfo(MultipartFile file, HttpServletRequest request) throws IllegalStateException, JsonProcessingException {
        return ResponseEntity.ok(cpuMonitor.getSystemInfo());
    }
}
