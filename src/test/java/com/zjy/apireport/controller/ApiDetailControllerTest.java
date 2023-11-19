package com.zjy.apireport.controller;

import com.zjy.apireport.dto.UniqueValuesDTO;
import com.zjy.apireport.entity.ApiDetail;
import com.zjy.apireport.service.ApiDetailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ApiDetailController.class)
public class ApiDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApiDetailService apiDetailService;

    @Test
    public void testGetAllApiDetailsWhenApiDetailsExistThenReturnApiDetails() throws Exception {
        when(apiDetailService.getAllApiDetails()).thenReturn(Arrays.asList(new ApiDetail(), new ApiDetail()));

        mockMvc.perform(get("/details"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetAllApiDetailsWhenNoApiDetailsThenReturnEmptyList() throws Exception {
        when(apiDetailService.getAllApiDetails()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/details"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void testGetApiDetailByIdWhenApiDetailExistsThenReturnApiDetail() throws Exception {
        when(apiDetailService.getApiDetailById(1L)).thenReturn(Optional.of(new ApiDetail()));

        mockMvc.perform(get("/details/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetApiDetailByIdWhenApiDetailDoesNotExistThenReturnNotFound() throws Exception {
        when(apiDetailService.getApiDetailById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/details/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetUniqueValuesThenReturnUniqueValues() throws Exception {
        when(apiDetailService.getUniqueValues()).thenReturn(new UniqueValuesDTO(Arrays.asList("app1", "app2"), Arrays.asList("env1", "env2"), Arrays.asList("csi1", "csi2")));

        mockMvc.perform(get("/unique-values"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
