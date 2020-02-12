package com.system.restaurant;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 참조블러그
 * https://webcache.googleusercontent.com/search?q=cache:esiJGy2EQJEJ:https://jeong-pro.tistory.com/171+&cd=4&hl=ko&ct=clnk&gl=kr
 * 
 */
@Aspect
@Component
public class MyAspect {

    private static final Logger logger =  LoggerFactory.getLogger(MyAspect.class);


    @Around("execution(* com.system.restaurant.**.*(..) )")
    // @Around("execution(public * com.system.restaurant.*Controller.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("start - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
        Object result = pjp.proceed();
        logger.info("finished - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
        return result;
    }
}