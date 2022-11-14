package com.example.firstproject.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


//[ AOP ]: Aspect-Oriented Programming. 관점 지향 프로그래밍.
//쉽게 말해, '비즈니스 로직 구현 내부'에서 '핵심과는 거리가 있는 파트(e.g: 로깅, 수행시간 측정..)'들은 따로 '다른 패키지'에 몰아넣는 것.


@Aspect //'AOP 클래스' 선언: 부가 기능을 주입해주는 클래스. 30강 21:18~
@Slf4j
@Component
public class PerformanceAspect { //'각 메소드의 수행시간 측정'


//==================================================================================================================

    //'내가 만든 어노테이션 @RunningTime'을 사용하고자 함
    @Pointcut("@annotation(com.example.firstproject.annotation.RunningTime)") //'@Pointcut의 적용 대상':
    private void enableRunningTime(){

    }

//==================================================================================================================

    @Pointcut("execution(* com.example.firstproject..*.*(..))") //'@Pointcut의 적용 대상':
                                                                //이 '프로젝트 firstproject 하위(=기본 패키지의 하위)의
                                                                //모든 메소드들'의 '수행시간을 측정'!!
                                                                //'(..)'의 '..'은 없애줘도 되고, 써줘도 되고 상관없다!
    private void cut(){

    }

//==================================================================================================================

    //30강 23:14~
    @Around("cut() && enableRunningTime()") //'위에 작성한 두 메소드('메소드 cut'과 '메소드 enableRunningTime)의 조건을
                                            //동시에 만족시키는 메소드'가 '실행되는 시점 전 후'에 '부가기능'을 삽입시키겠다!
                                            //여기서 '부가기능'은 아래 작성할 메소드의 특성이 된다.
    public void LoggingRunningTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        //< 메소드 실행 전, 측정 시작 >
        StopWatch stopWatch = new StopWatch(); //시간측정을 위해 스프링에서 제공하는 '내장 클래스 StopWatch'
        stopWatch.start(); //시간측정 시작!


        //< 메소드를 실행 >
        Object returningObj = proceedingJoinPoint.proceed();


        //< 메소드 실행 후, 측정 종료 >
        stopWatch.stop(); //시간측정 끝!


        //< 메소드명 >
        String methodName = proceedingJoinPoint.getSignature()
                            .getName();


        //< 로깅 >
        log.info("{}의 총 수행 시간 => {} sec", methodName, stopWatch.getTotalTimeSeconds());

    }

}
