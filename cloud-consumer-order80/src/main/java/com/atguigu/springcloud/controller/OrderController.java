package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.entities.CommontResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;


@RestController
public class OrderController {

    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;
    @GetMapping(value = "/consumer/payment/create")
    public CommontResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create",payment, CommontResult.class);
    }
    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommontResult<Payment> getPaymentById(@PathVariable("id")Long id){
        System.out.printf("调用Payment模块接口");
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id , CommontResult.class);
    }
}
