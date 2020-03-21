package com.atguigu.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {


    public String paymentInfo_OK(Integer id){
        return "线程池:" + Thread.currentThread().getName() + "payment_OK, id:" + id + "\t" + "欧耶！";
    }


    @HystrixCommand(fallbackMethod = "paymentInfo_TineOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfo_TimeOut(Integer id){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池:" + Thread.currentThread().getName() + "paymentInfo_TimeOut, id:" + id + "\t" + "啊哦！";
    }

    public String paymentInfo_TineOutHandler(Integer id){
        return "线程池:" + Thread.currentThread().getName() + "网络繁忙, id:" + id + "\t" + "啊哦！";
    }

}
