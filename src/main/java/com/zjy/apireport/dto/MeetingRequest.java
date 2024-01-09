package com.zjy.apireport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MeetingRequest {
    String orgnaizer;
    String[] attendee;
    Long startDate;
    Long endDate;
    String uid;
}
