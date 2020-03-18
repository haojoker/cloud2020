package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommontResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderConsulController {
    public static final String PAYMENT_URL = "http://consul-provider-payment";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/consul")
    public String paymentInfo(){
        String result = restTemplate.getForObject(PAYMENT_URL+"/payment/consul",String.class);
        return result;
    }

}
