package com.example.demo.aop;


import com.example.demo.annotation.DataSource;
import com.example.demo.extend.HandlerDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 使用AOP拦截特定的注解去动态的切换数据源
 */
@Aspect
@Component
@Order(1)
public class DataSourceAop {

    @Pointcut("@annotation(com.example.demo.annotation.DataSource)")
    public void aspect(){}

    @Before(value = "aspect()")
    private void doBefore(JoinPoint joinPoint){
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        //获取方法上的注解
        DataSource dataSource = method.getAnnotation(DataSource.class);
        if(dataSource!=null){
            HandlerDataSource.putDataSource(dataSource.value());
        }
    }

    @After("aspect()")
    public void after(JoinPoint point) {
        //清理掉当前设置的数据源，让默认的数据源不受影响
        HandlerDataSource.clear();
    }
}
