package com.zqzess.sift.infrastructure;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/07/28 14:35
 * @Package com.zqzess.sift.infrastructure
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@Component
@Slf4j
public class StartWorkTest implements CommandLineRunner , ApplicationRunner , InitializingBean {

    @PostConstruct
    public void text() {
        // 生命周期最早,bean初始化
//        System.out.println(1);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 生命周期最早,bean初始化
//        System.out.println(2);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 容器启动后执行
//        System.out.println(3);
    }

    @Override
    public void run(String... args) throws Exception {
        // 容器启动后执行，生命周期最晚
//        System.out.println(4);
    }

}
