package com.wit.baojims.Config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Classname Aop
 * @Description TODO
 * @Author Shawn Yue
 * @Date 14:00
 * @Version 1.0
 **/
@Component
@Aspect
@Slf4j
public class MyAdvice {
    @Pointcut("execution(* com.wit.baojims.controller.*.*(..))")
    public void controllerCut() {}

    @Around("controllerCut()")
    public Object logger(ProceedingJoinPoint pjp) throws JsonProcessingException {
        Object[] args = pjp.getArgs();
        ObjectMapper objectMapper = new ObjectMapper();
        String className = pjp.getTarget().getClass().toString();
        String methodName = pjp.getSignature().getName();
        log.info("【前置通知】类名:{}方法名:{}参数:+{}",className,methodName,args);
        Object obj = new Object();
        try{
            obj = pjp.proceed();
        } catch (Throwable e) {
            log.error("【异常通知】",e);
        } finally {
            //最终通知
        }
        log.info("【后置通知】类名:{}方法名:{}返回值:{}返回值json数据:\n{}",
                className,methodName,obj,objectMapper.writeValueAsString(obj));
        return obj;
    }
}
