package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.OrderHystrixService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderHystrixController {

    @Resource
    private OrderHystrixService orderHystrixService;


    @GetMapping(value = "/consumer/payment/hystrix/ok/{id}")
    String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = orderHystrixService.paymentInfo_OK(id);
        System.out.println("***OrderHystrix_ok*****" + result);
        return result;
    }

    @GetMapping(value = "/consumer/payment/hystrix/timeout/{id}")
    String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        String result = orderHystrixService.paymentInfo_TimeOut(id);
        System.out.println("***OrderHystrix_timeout*****" + result);
        return result;
    }
}
