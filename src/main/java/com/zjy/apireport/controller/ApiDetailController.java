package com.zjy.apireport.controller;

import com.zjy.apireport.dto.MeetingRequest;
import com.zjy.apireport.dto.UniqueValuesDTO;
import com.zjy.apireport.entity.ApiDetail;
import com.zjy.apireport.service.ApiDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class ApiDetailController {

    @Autowired
    private ApiDetailService apiDetailService;

    @GetMapping("/details")
    public List<ApiDetail> getAllApiDetails() {
        List<ApiDetail> allApiDetails = apiDetailService.getAllApiDetails();
        return allApiDetails;
    }

    @GetMapping("/details/{id}")
    public Optional<ApiDetail> getApiDetailById(@PathVariable Long id) {
        return apiDetailService.getApiDetailById(id);
    }

//    @GetMapping("/report")
//    public Map<String, Long> generateReport(@RequestParam String appName) {
//        return apiDetailService.generateReportBySourceApp(appName);
//    }

    @GetMapping("/unique-values")
    public UniqueValuesDTO getUniqueValues() {
        return apiDetailService.getUniqueValues();
    }

    @GetMapping("/report")
    public Map<String, Long> generateReport(
            @RequestParam String appName,
            @RequestParam String environment,
            @RequestParam String csi
    ) {
        return apiDetailService.generateReportBySourceApp(appName, environment, csi);
    }

    @PostMapping("/meeting")
    public void sendMeeting(@RequestParam MeetingRequest meetingRequest){
        System.out.println(meetingRequest);
    }

    // 其他RESTful接口方法
    // ...
}

