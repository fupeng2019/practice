package com.example.demo.vo;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class WorkVO {
    /**
     * 不包含该字段
     */
    @EqualsAndHashCode.Exclude
    private Integer id;
    private String workName;
    private Date receiveDate;


}
