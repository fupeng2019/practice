package com.example.demo.config;

import com.example.demo.register.DynamicDataSourceRegister;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DynamicDataSourceRegister.class)
public class Config {
}
