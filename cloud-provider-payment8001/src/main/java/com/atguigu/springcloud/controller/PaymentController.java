package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.entities.CommontResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;


    @PostMapping(value = "/payment/create")
    public CommontResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("....插入..");
        if (result > 0) {
            return new CommontResult(200, "插入成功,serverPort:" + serverPort, result);
        }
        return new CommontResult(444, "插入失败", null);
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommontResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        if (Objects.nonNull(payment)) {
            return new CommontResult(200, "查找成功,serverPort:" + serverPort, payment);
        }
        return new CommontResult(444, "查找失败", null);
    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        services.stream().forEach(e ->{
            log.info("------services------" + e);
        });
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        serviceInstances.stream().forEach(e ->{
            log.info("------instance------" + e.getServiceId() + ":" + e.getPort() + ":" + e.getUri());
        });
        return this.discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }


    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.getStackTrace();
        }
        return serverPort;
    }
}
