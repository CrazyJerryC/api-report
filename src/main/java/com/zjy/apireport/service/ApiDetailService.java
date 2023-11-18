package com.zjy.apireport.service;

import com.zjy.apireport.entity.ApiDetail;
import com.zjy.apireport.repository.ApiDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApiDetailService {

    @Autowired
    private ApiDetailRepository apiDetailRepository;

    public List<ApiDetail> getAllApiDetails() {
        return apiDetailRepository.findAll();
    }

    public Optional<ApiDetail> getApiDetailById(Long id) {
        Optional<ApiDetail> opt = apiDetailRepository.findById(id);
        return opt;
    }

    public Map<String, Long> generateReportBySourceApp(String appName) {
        List<ApiDetail> apiDetails = apiDetailRepository.findByAppName(appName);

        // 统计每个 SourceApp 的数量
        Map<String, Long> countsBySourceApp = apiDetails.stream()
                .collect(Collectors.groupingBy(ApiDetail::getSourceApp, Collectors.counting()));

        return countsBySourceApp;
    }

    // 其他可能需要的业务逻辑方法
    // ...
}

