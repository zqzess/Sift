package com.zqzess.sift.service.monitor;

import com.zqzess.sift.infrastructure.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.Map;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/08/30 11:22
 * @Package com.zqzess.sift.monitor
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@Service
@Slf4j
public class SystemMonitor {
    private OperatingSystemMXBean osMxBean= ManagementFactory.getOperatingSystemMXBean();
    private ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
    private long preTime = System.nanoTime();
    private long preUsedTime = 0;


    /**
     * 获取进程负载
     * @return
     */
    public double getProcessCpu() {
        long totalTime = 0;
        for (long id : threadBean.getAllThreadIds()) {
            totalTime += threadBean.getThreadCpuTime(id);
        }
        long curtime = System.nanoTime();
        long usedTime = totalTime - preUsedTime;
        long totalPassedTime = curtime - preTime;
        preTime = curtime;
        preUsedTime = totalTime;
        return (((double) usedTime) / totalPassedTime / osMxBean.getAvailableProcessors()) * 100;
    }

    /**
     * 获取所有负载
     * @return
     */
    public double getCpu() {
        OperatingSystemMXBean osMxBean = ManagementFactory.getOperatingSystemMXBean();
        double cpu = osMxBean.getSystemLoadAverage();
        return cpu;
    }

    public Result<?> getSystemInfo() {
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        double[] prevTicks = processor.getSystemLoadAverage(1);
        long totalmem = systemInfo.getHardware().getMemory().getTotal();
        long freemem = systemInfo.getHardware().getMemory().getAvailable();
        long netSpeed = systemInfo.getHardware().getNetworkIFs().get(0).getSpeed();
//        log.error(systemInfo.getHardware().getNetworkIFs(true).toString());
        Map<String, Object> map = new HashMap<>();
        map.put("cpus", prevTicks);
        map.put("totalmem", totalmem);
        map.put("freemem", freemem);
        map.put("netSpeed", netSpeed);
        return Result.success(null, null, map);
    }
}
