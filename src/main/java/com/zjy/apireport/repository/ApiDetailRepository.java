package com.zjy.apireport.repository;

import com.zjy.apireport.entity.ApiDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiDetailRepository extends JpaRepository<ApiDetail, Long> {
    List<ApiDetail> findByAppName(String appName);
    // 这里可以定义额外的查询方法，Spring Data JPA 会自动实现
}

