package com.sagar.aoppoc.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggerAspect {

    @Before("execution(* com.sagar.aoppoc.controller..*)")
    public void logBeforeMethod() {
        log.info("Before method");
    }

//    @Pointcut("within(com.sagar.aoppoc.controller..*) || within(com.sagar.aoppoc.service..*)")
//    public void applicationMethods() {
//    }
//
//    @Before("applicationMethods()")
//    public void before(JoinPoint joinPoint) {
//        log.info("AOP >> entering {}", signature(joinPoint));
//    }
//
//    @AfterReturning(pointcut = "applicationMethods()", returning = "result")
//    public void afterReturning(JoinPoint joinPoint, Object result) {
//        log.info("AOP << returned from {} -> {}", signature(joinPoint),
//                result == null ? "null" : result.getClass().getSimpleName());
//    }
//
//    @AfterThrowing(pointcut = "applicationMethods()", throwing = "ex")
//    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
//        log.error("AOP !! {} threw {}: {}", signature(joinPoint),
//                ex.getClass().getSimpleName(), ex.getMessage());
//    }
//
//    @Around("within(com.sagar.aoppoc.controller..*)")
//    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
//        long start = System.currentTimeMillis();
//        try {
//            return joinPoint.proceed();
//        } finally {
//            log.info("AOP timing {} took {} ms", signature(joinPoint),
//                    System.currentTimeMillis() - start);
//        }
//    }
//
//    private String signature(JoinPoint joinPoint) {
//        return joinPoint.getSignature().getDeclaringType().getSimpleName()
//                + "." + joinPoint.getSignature().getName() + "()";
//    }
}
