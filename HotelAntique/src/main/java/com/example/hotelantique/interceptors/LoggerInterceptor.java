package com.example.hotelantique.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import org.slf4j.Logger;

import java.util.stream.Collectors;

@Component
public class LoggerInterceptor  implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("startingTime", System.currentTimeMillis());
        logger.info("Pre handle - method: {}, uri: {}", request.getMethod(), request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

        long millisTookToHandle = System.currentTimeMillis() - (long )request.getAttribute("startingTime");

        logger.info("Post handle - Generated response in completed in: {}ms, status: {}", millisTookToHandle,
                response.getStatus());
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

        long millisTookToHandle = System.currentTimeMillis() - (long )request.getAttribute("startingTime");
        logger.info("After Completion - successful request completed in {}ms, method: {}, uri: {}",
                millisTookToHandle, request.getMethod(), request.getRequestURI());

        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
