package com.example.demo.controller;

import com.example.demo.service.TestService;
import com.example.demo.vo.DataSourceVO;
import com.example.demo.vo.UserVo;
import com.example.demo.vo.WorkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "test")
public class TestController {
    @Autowired
    private TestService testService;

    @RequestMapping(value = "/test1", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public List<DataSourceVO> test1(){
        return testService.queryDataSourceVO();
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public List<UserVo> test2(){ return testService.queryUserVo();
    }
    @RequestMapping(value = "/test3", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String,Object> test3(){return testService.queryAll();}

    @RequestMapping(value = "/doMessage", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String doMessage(){ testService.doMessage();return "success";}

    @RequestMapping(value = "/query", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public  List<WorkVO> query(){ return testService.query();}
}
