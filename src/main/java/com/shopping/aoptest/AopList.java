package com.shopping.aoptest;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Order;
import org.springframework.stereotype.Component;

// @Order 사용 시 숫자가 큰 것이 먼저 실행됩니다.

@Aspect // 관심 사항으로 인식될
@Component // 컴포넌트입니다.
public class AopList {
    // AspecetJ 표현식
    // execution(수식어패턴? 리턴타입패턴 패키지패턴?이름패턴 (파라미터패턴)
    @Before("execution(* com.shopping.aoptest..*.*(..))")
    @Order(value = 0)
    public void Wash(){
        System.out.println("세수를 합니다.");
    }
    
    @Before("execution(* com.shopping.aoptest..*.*(..))")
    @Order(value = 1)
    public void BreakFast(){
        System.out.println("아침밥을 먹습니다.");
    }

    @After("execution(* com.shopping.aoptest..*.*(..))")
    @Order(value = 1)
    public void Lunch(){
        System.out.println("점심을 먹습니다.");
    }

    @After("execution(* com.shopping.aoptest..*.*(..))")
    @Order(value = 0)
    public void Getoff(){
        System.out.println("퇴근을 합니다.");
    }
}
