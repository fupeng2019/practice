package com.example.demo.dao;

import com.example.demo.vo.WorkVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkDao {

    int insertList(@Param("list") List<WorkVO> list);

    List<WorkVO> query();
}
