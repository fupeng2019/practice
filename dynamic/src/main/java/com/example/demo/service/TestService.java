package com.example.demo.service;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.Constant;
import com.example.demo.annotation.DataSource;
import com.example.demo.dao.BaseDao;
import com.example.demo.dao.WorkDao;
import com.example.demo.extend.HandlerDataSource;
import com.example.demo.vo.*;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestService {

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private WorkDao workDao;


    public List<DataSourceVO> queryDataSourceVO(){
        return baseDao.queryDataSourceVO();
    }

    @DataSource("case")
    public List<UserVo> queryUserVo(){
        return baseDao.queryUserVo();
    }

    public Map<String,Object> queryAll(){
        Map<String,Object> map =new HashMap<>();
        HandlerDataSource.putDataSource("case");
        List<UserVo> userVos = baseDao.queryUserVo();
        HandlerDataSource.clear();
        HandlerDataSource.putDataSource("psp");
        List<DataSourceVO> dataSourceVOS =baseDao.queryDataSourceVO();
        HandlerDataSource.clear();
        map.put("object1",userVos);
        map.put("object2",dataSourceVOS);
        return map;
    }

    public void doMessage(){
        ExecuteSql executeSql =new ExecuteSql();
        executeSql.setType(0);
        Map<String,List<String>> dataSourceSql = this.getSqlList(baseDao.querySqlByExecuteSql(executeSql));
        if(CollectionUtils.isEmpty(dataSourceSql)){
            return;
        }
        List<Map<String,Object>> all =new ArrayList<>();
        dataSourceSql.forEach((key,list)->{
            HandlerDataSource.putDataSource(key);
            list.forEach(sql->{
                List<Map<String,Object>> result= baseDao.queryBySql(sql);
                if(!CollectionUtils.isEmpty(result)){
                    all.addAll(result);
                }
            });
            HandlerDataSource.clear();
        });

        List<WorkVO> list =JSONArray.parseArray(JSONArray.toJSONString(all),WorkVO.class);
        workDao.insertList(list);
    }

    public List<WorkVO> query(){
        return workDao.query();
    }

    /**
     * 组装sql
     * @param list
     * @return
     */
    private Map<String,List<String>> getSqlList(List<ExecuteSql> list){
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        Map<String,List<String>> dataSourceSql = Maps.newHashMap();
        list.forEach(sql -> {
            StringBuffer sb =new StringBuffer();
            if(StringUtils.isEmpty(sql.getExecuteType())||StringUtils.isEmpty(sql.getTableName())){
                return;
            }
            sb.append(sql.getExecuteType()).append(Constant.BLANK_SPACE);
            List<ExecuteSqlDetailed> executeSqlDetailedList =sql.getExecuteSqlDetailedList();
            if(CollectionUtils.isEmpty(executeSqlDetailedList)){
                return;
            }
            //字段和别名
            executeSqlDetailedList.forEach(executeSqlDetailed -> {
               String field = executeSqlDetailed.getField();
                String asField =executeSqlDetailed.getAsField();
                if(StringUtils.isEmpty(field)){
                    return;
                }
                sb.append(field);
                if(!StringUtils.isEmpty(asField)){
                    sb.append(Constant.BLANK_SPACE).append("as").append(Constant.BLANK_SPACE);
                }
                sb.append(asField).append(Constant.COMMA);
            });
            //移除最后一个逗号
            sb.setLength(sb.length()-1);
            sb.append(Constant.BLANK_SPACE).append(Constant.FROM).append(Constant.BLANK_SPACE).append(sql.getTableName());
            this.setMap(dataSourceSql,sql.getName(),sb.toString());
        });
        return dataSourceSql;
    }

    /**
     * 添加元素
     * @param dataSourceSql
     * @param key
     * @param sql
     */
    private void setMap( Map<String,List<String>> dataSourceSql,String key,String sql){
        List<String> list = dataSourceSql.get(key);
        if(CollectionUtils.isEmpty(list)){
            list =new ArrayList<>();
            list.add(sql);
            dataSourceSql.put(key,list);
        }else {
            list.add(sql);
        }
    }
}
