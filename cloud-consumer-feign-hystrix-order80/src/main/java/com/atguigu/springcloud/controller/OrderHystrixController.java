package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.OrderHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@DefaultProperties(defaultFallback = "order_Global_FallbackMethod")
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
    @HystrixCommand(fallbackMethod = "paymentInfo_TineOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1000")
    })
    String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        String result = orderHystrixService.paymentInfo_TimeOut(id);
        System.out.println("***OrderHystrix_timeout*****" + result);
        return result;
    }

    public String paymentInfo_TineOutHandler(Integer id){
        return "线程池:" + Thread.currentThread().getName() + "***OrderHystrix_timeout*****  网络繁忙, id:" + id + "\t" + "啊哦！";
    }

    public String order_Global_FallbackMethod(){

        return  "测试全局降级方法";
    }
}
