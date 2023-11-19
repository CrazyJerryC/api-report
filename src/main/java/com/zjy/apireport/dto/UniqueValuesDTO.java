package com.zjy.apireport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UniqueValuesDTO {
    List<String> appNames;
    List<String> environments;
    List<String> csiList;

}
