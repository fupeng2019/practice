package com.example.demo.vo;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataSourceVO {

    private Long id;
    private String username;
    private String password;
    private String url;
    private String driver;
    @JSONField(name="database_type")
    private String databaseType;
    private String name;
    @JSONField(name="data_source_type")
    private String dataSourceType;
}
