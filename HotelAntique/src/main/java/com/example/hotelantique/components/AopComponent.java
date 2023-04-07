package com.example.hotelantique.components;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopComponent {

    private  Logger LOGGER =  LoggerFactory.getLogger(this.getClass());

//    @Pointcut("execution(public com.example.hotelantique.service.EmailService.*(..))")
//    void trackPerformance(){};
//
//    @Before("trackPerformance()")
//    public void beforeEachMethod(){
//
//    }


}
