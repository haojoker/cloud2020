package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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

    //服务熔断

    @HystrixCommand(fallbackMethod = "paymentCricuitBreaker_Fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否打开断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),//失败率达到多少跳闸
    })
    public String paymentCricuitBreaker(@PathVariable("id") Integer id){
        if (id < 0){
            throw new RuntimeException("id不能是负数");
        }
        String uuid = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "调用成功，流水号 = " + uuid;
    }

    public String paymentCricuitBreaker_Fallback(@PathVariable("id") Integer id){
        return "id不能为空 id:" + id;
    }

}
