package com.fupeng.netty.rpc.api.impl;


import com.fupeng.netty.rpc.api.NumberService;

public class NumberServiceImpl implements NumberService {
    @Override
    public String getCode(Long id) {
        return "你已经成功了";
    }
}
