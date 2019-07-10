package com.example.demo.dao;


import com.example.demo.vo.DataSourceVO;
import com.example.demo.vo.ExecuteSql;
import com.example.demo.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface BaseDao {

    @Select("select * from data_source")
    List<DataSourceVO> queryDataSourceVO();

    @Select("select count(0) from data_source where name = #{name}")
    int exist(String name);

    @Select("select * from t_user")
    List<UserVo> queryUserVo();

    List<ExecuteSql> querySqlByExecuteSql(ExecuteSql executeSql);

    List<Map<String,Object>> queryBySql(@Param("sql") String sql);
}
