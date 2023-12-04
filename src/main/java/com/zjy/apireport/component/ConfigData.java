package com.zjy.apireport.component;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.zjy.apireport.entity.Job;
import com.zjy.apireport.service.ScheduleService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Data
@Component
public class ConfigData {

    @Autowired
    private ScheduleService scheduleService;

    @Value("${schedule.job.in}")
    private String json;

    private List<Job> jobList;


    @PostConstruct
    public void post(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.jobList = objectMapper.readValue(json, new TypeReference<List<Job>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(jobList.size());
        System.out.println("start time: " + Instant.now());

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(jobList.size());

        for (Job job : jobList) {
            long timeInterval = job.getTime(); // 获取时间间隔
            executorService.scheduleWithFixedDelay(() -> {
                try {
                    scheduleService.process(job); // 执行ScheduleService的process方法
                } catch (Exception e) {
                    e.printStackTrace();
                    // 异常处理逻辑，可以记录日志或采取其他措施
                }
            }, 0, timeInterval, TimeUnit.MILLISECONDS);
        }

    }

}
