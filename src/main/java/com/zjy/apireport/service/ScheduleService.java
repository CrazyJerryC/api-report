package com.zjy.apireport.service;

import com.zjy.apireport.entity.Job;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
public class ScheduleService {

    @Value("${email.address}")
    private String email;

    Random random = new Random();

    public void process(Job job) throws Exception {
        System.out.println("email address: " +email);

        System.out.println(Thread.currentThread().getName() + " " + Instant.now() + " Job:" + job);
        Thread.sleep(random.nextInt(10000));
        if(random.nextInt(10) < 3){
            throw new Exception("ex");
        }
    }

    public String callValue(){
        System.out.println(email);
        return email;
    }

}
