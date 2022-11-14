package com.example.firstproject.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


//[ AOP ]: Aspect-Oriented Programming. 관점 지향 프로그래밍.
//쉽게 말해, '비즈니스 로직 구현 내부'에서 '핵심과는 거리가 있는 파트(e.g: 로깅, 수행시간 측정..)'들은 따로 '다른 패키지'에 몰아넣는 것.
@Aspect //'AOP 클래스' 선언: '부가 기능'을 주입해주는 클래스. 30강 06:45~
@Slf4j //로깅 기능 사용을 위해 필요한 어노테이션
@Component //이 '클래스 DebuggingAspect'를 '클래스 ~'에서 사용하기(DI시키기) 위해, 이 '클래스 DebuggingAspect'를
           //'스프링 IoC 컨테이너'에 '등록('클래스 DebuggingAspect'를 '객체'로 생성 및 관리)'시키려면, 이 때 필요한 어노테이션이다!
public class DebuggingAspect {



    //[ 삽입할 지점의 대상 메소드 선택 ]
    //'클래스 CommentService의 메소드 create'의 '로그'를 확인하기 위함. 저기 'create(..)'의 '..'은 넣어줘도 되고, 아예 그냥
    //빼버려도 됨. 매개변수 노상관임.


    //@Pointcut("execution(* com.example.firstproject.service.CommentSerivce.*(..))")
                    //'Pointcut'의 적용 대상: 이제 '클래스 CommentService의 '모든 메소드('*')'에 '삽입할 내용'을
                    //이 메소드 안에 작성해야 함
                    //만약, '클래스 CommentService의 메소드 create'에만 '로깅'을 적용하려면, 저기 '*' 대신, 'create'를 입력하면 됨!
    //private void cut(){
    @Pointcut("execution(* com.example.firstproject.api.*.*(..))") //'Pointcut'의 적용 대상: '패키지 api의 모든 메소드'에
                                                                   //아래 '메소드 cut'을 적용할 수 있음
    private void cut(){

    }

    //[ 입력값 로깅 AOP ] : 'log.info(~~)'를 '다른 클래스의 어떤 메소드 안에 비집어서 넣을 것인지' 30강 07:40~
    //이 '클래스 Aspect 객체'를 '어느 (다른) 클래스의 어느 (다른) 메소드'에 낑겨 질러 넣을건지 결정.
    //'주입 대상 지정'

    //< 실행 시점 설정 >: '메소드 cut'의 '적용 대상 메소드 create'가 '실행되기 이전 시점'에 '로깅'을 실행시키고자 함
    @Before("cut()") //'메소드 cut에 지정된 @Pointcut(execution(* 메소드주소))'의 '메소드 create'에 아래 '메소드 loggingArgs'가
                     //수행된다!
    public void loggingArgs(JoinPoint joinPoint){ //'메소드 cut의 대상 메소드 create의 근처(부근)에서 '매개변수'를 가져온다'

        //< 입력값 가져오기 >
        Object[] args = joinPoint.getArgs(); //getArgs 이런 건 그냥 구글링해보면 됨


        //< 클래스명 >
        String className = joinPoint.getTarget()   //getTarget, getClass 이런 건 그냥 구글링해보면 됨
                                     .getClass()
                                     .getSimpleName();

        //< 메소드명 >
        String methodName = joinPoint.getSignature()
                              .getName();


        //< 입력값 로깅 >
        //만약, '클래스 CommentService의 메소드 create'의 '요청 URL 입력값(articleId)'이 5라면(5번째 게시글),
        //'로깅 예상 출력값'은 'CommentDto(id=null, articleId=5, nickname=홍팍, body=고추바사삭)'으로 로깅 기록이 나온다!
        for(Object obj : args){
            log.info("{}#{}의 입력값 => {}", className,methodName, obj); //'첫 번째 중괄호'에는 'className'이 들어가고
                                                                        //'두 번째 중괄호'에는 'methodName'이 들어가고
                                                                        //'세 번째 중괄호'에는 'obj'가 들어간다
        }
    }


//=====================================================================================================================

    //[ 반환값 로깅 AOP ]

    //< 실행 시점 설정 >: '메소드 cut'의 '적용 대상 메소드 create'가 '실행된 이후'에 '로깅을 실행'하게 함
    @AfterReturning(value = "cut()", returning = "returnObj") //-'returning': '메소드 creat 실행 후, 메소드 create의 리턴값'을
                                                              //받아오기 위한 매개변수
    public void loggingReturnValue(JoinPoint joinPoint, Object returnObj){ //-'메소드 cut의 대상 메소드 create의 근처(부근)에서
                                                                           //'매개변수'를 가져온다'
                                                                           //-'Object returnObj': '메소드 create의 리턴값'을
                                                                           //전달받을 수 있도록 함. 그냥 궁금해서 받아오는 것임.


        //< 클래스명 >
        String className = joinPoint.getTarget()   //getTarget, getClass 이런 건 그냥 구글링해보면 됨
                .getClass()
                .getSimpleName();


        //< 메소드명 >
        String methodName = joinPoint.getSignature()
                .getName();


        //< 입력값 로깅 >
        log.info("{}#{}의 반환환값 = {}", className,methodName, returnObj);

    }

//=====================================================================================================================


}
