package com.zjy.apireport.service;

import com.zjy.apireport.dto.UniqueValuesDTO;
import com.zjy.apireport.entity.ApiDetail;
import com.zjy.apireport.repository.ApiDetailRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApiDetailServiceTest {

    @InjectMocks
    private ApiDetailService apiDetailService;

    @Mock
    private ApiDetailRepository apiDetailRepository;

    @Test
    public void testGenerateReportBySourceAppWhenDataExistsThenReturnCorrectCounts() {
        // Arrange
        String appName = "TestApp";
        String environment = "TestEnv";
        String csi = "TestCSI";
        ApiDetail apiDetail1 = new ApiDetail();
        apiDetail1.setSourceApp("SourceApp1");
        ApiDetail apiDetail2 = new ApiDetail();
        apiDetail2.setSourceApp("SourceApp2");
        ApiDetail apiDetail3 = new ApiDetail();
        apiDetail3.setSourceApp("SourceApp1");
        List<ApiDetail> apiDetails = Arrays.asList(apiDetail1, apiDetail2, apiDetail3);
        when(apiDetailRepository.findByAppNameAndEnvironmentAndCSI(appName, environment, csi)).thenReturn(apiDetails);

        // Act
        Map<String, Long> result = apiDetailService.generateReportBySourceApp(appName, environment, csi);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get("SourceApp1")).isEqualTo(2);
        assertThat(result.get("SourceApp2")).isEqualTo(1);
    }

    @Test
    public void testGenerateReportBySourceAppWhenDataDoesNotExistThenReturnEmptyMap() {
        // Arrange
        String appName = "NonExistentApp";
        String environment = "NonExistentEnv";
        String csi = "NonExistentCSI";
        when(apiDetailRepository.findByAppNameAndEnvironmentAndCSI(appName, environment, csi)).thenReturn(Collections.emptyList());

        // Act
        Map<String, Long> result = apiDetailService.generateReportBySourceApp(appName, environment, csi);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    public void testGetUniqueValuesWhenDataExistsThenReturnCorrectUniqueValues() {
        // Arrange
        List<String> appNames = Arrays.asList("App1", "App2");
        List<String> environments = Arrays.asList("Env1", "Env2");
        List<String> csiList = Arrays.asList("CSI1", "CSI2");
        when(apiDetailRepository.findDistinctAppNames()).thenReturn(appNames);
        when(apiDetailRepository.findDistinctEnvironments()).thenReturn(environments);
        when(apiDetailRepository.findDistinctCSI()).thenReturn(csiList);

        // Act
        UniqueValuesDTO result = apiDetailService.getUniqueValues();

        // Assert
        assertThat(result.getAppNames()).isEqualTo(appNames);
        assertThat(result.getEnvironments()).isEqualTo(environments);
        assertThat(result.getCsiList()).isEqualTo(csiList);
    }

    @Test
    public void testGetUniqueValuesWhenDataDoesNotExistThenReturnEmptyUniqueValues() {
        // Arrange
        when(apiDetailRepository.findDistinctAppNames()).thenReturn(Collections.emptyList());
        when(apiDetailRepository.findDistinctEnvironments()).thenReturn(Collections.emptyList());
        when(apiDetailRepository.findDistinctCSI()).thenReturn(Collections.emptyList());

        // Act
        UniqueValuesDTO result = apiDetailService.getUniqueValues();

        // Assert
        assertThat(result.getAppNames()).isEmpty();
        assertThat(result.getEnvironments()).isEmpty();
        assertThat(result.getCsiList()).isEmpty();
    }
}
