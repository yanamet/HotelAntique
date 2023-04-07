package com.example.hotelantique.components;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AopComponent {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.example.hotelantique.service.EmailService.*(..))")
    void trackPerformance() {
    };

    @Around("trackPerformance()")
    public Object beforeEachMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        long before = System.currentTimeMillis();
        Object proceed = proceedingJoinPoint.proceed();
        long after = System.currentTimeMillis();

        Object[] args = proceedingJoinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        Arrays.stream(args).forEach(a -> sb.append(a.toString()).append(" "));

        Signature signature = proceedingJoinPoint.getSignature();
        long completedInMs = after - before;

        LOGGER.info("Method with signature {} completed in {}ms. Method invoked with {} params",
                signature, completedInMs, sb.toString());

        return proceed;
    }


}
