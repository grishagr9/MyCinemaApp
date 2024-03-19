package com.example.MyCinemaApp.config;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;


public class DataSourceDemo extends DriverManagerDataSource {


    public DataSourceDemo(){
        this.setUrl("jdbc:postgresql://localhost:5432/Database1");
        this.setUsername("postgres");
        this.setPassword("password");
    }
}