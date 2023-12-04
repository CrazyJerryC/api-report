package com.zjy.apireport.entity;

import lombok.Data;

@Data
public class Job {
    private String appName;
    private Long time;
    private String address;
    private String callback;
}
