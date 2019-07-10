package com.example.demo.vo;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExecuteSql {

    private Integer id;
    private Integer dataSourceId;
    private String executeType;
    private String tableName;
    private Integer type;
    private List<ExecuteSqlDetailed> executeSqlDetailedList;
    private String name;

}