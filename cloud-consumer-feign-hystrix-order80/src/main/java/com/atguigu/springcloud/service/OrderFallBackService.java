package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class OrderFallBackService implements OrderHystrixService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "OrderFallBackService_paymentInfo_OK";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "OrderFallBackService_paymentInfo_TimeOut";
    }
}
