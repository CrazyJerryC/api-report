package com.zjy.apireport.repository;

import com.zjy.apireport.entity.ApiDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiDetailRepository extends JpaRepository<ApiDetail, Long> {
    List<ApiDetail> findByAppName(String appName);

    List<ApiDetail> findByAppNameAndEnvironmentAndCSI(String appName, String environment, String csi);

    List<String> findDistinctAppNames();

    List<String> findDistinctEnvironments();

    List<String> findDistinctCSI();

}

