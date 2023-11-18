package com.zjy.apireport.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "API_DETAIL")
public class ApiDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appName")
    private String appName;

    @Column(name = "ENV")
    private String environment;

    @Column(name = "CSI")
    private String csi;

    @Column(name = "API")
    private String api;

    @Column(name = "METHOD")
    private String method;

    @Column(name = "SourceApp")
    private String sourceApp;

    @Column(name = "API_REQUEST_DATE_UTC")
    private Date apiRequestDateUtc;

    // Constructors, Getters, and Setters
    // ...
}

