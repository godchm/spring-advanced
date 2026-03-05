package org.example.expert.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TimeCheckAop {

    // 어떤것을: CommentAdminService
    // 언제: 메서드 실행 전후
    // 어떻게:

    // 메서드 실행 전후 시간을 비교 분석하는 기능을 구현해보자.

    // 유저 삭제
    @Around("execution(* org.example.expert.domain.comment.controller.CommentAdminController.deleteComment(..))")
    public Object executionTime(ProceedingJoinPoint joinPoint) throws Throwable{

        // 메서드 실행 전
        long start= System.currentTimeMillis();
        Object result=joinPoint.proceed();

        // 메서드 실행 후
        long end= System.currentTimeMillis();

        log.info("[AOP] {} 실행됨 in {} ms", joinPoint.getSignature(),end-start);

        return result;
    }

    // 유저 역할 변경
    @Around("execution(* org.example.expert.domain.user.controller.UserAdminController.changeUserRole(..))")
    public Object executionTimeForChangeUserRole(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        log.info("[AOP] {} 실행됨 in {} ms", joinPoint.getSignature(), end - start);

        return result;
    }
}
