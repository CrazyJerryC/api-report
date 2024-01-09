package com.zjy.apireport.service;

import com.zjy.apireport.entity.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@TestPropertySource(properties = {"email.address=test@example.com"})
class ScheduleServiceTest {

    @Mock
    private Job job;

    private ScheduleService scheduleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        scheduleService = new ScheduleService();
//        ReflectionTestUtils.setField(scheduleService, "email", "test@example.com");

    }

//    @Test
//    void process_shouldPrintJobDetails() throws Exception {
//        // Arrange
//        when(job.toString()).thenReturn("Test Job");
//
//        // Act
//        scheduleService.process(job);
//
//        // Assert
//        verify(job, times(1)).toString();
//    }

    @Test
    void callValue_shouldPrintEmail() {
        // Act
        scheduleService.callValue();

        // Assert
        // Add your assertions here
    }

    @Test
    void callValue_shouldPrintEmail2() {
        // Act
        scheduleService.callValue();

        // Assert
        // Add your assertions here
    }
}