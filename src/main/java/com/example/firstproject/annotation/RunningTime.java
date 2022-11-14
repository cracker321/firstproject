package com.example.firstproject.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//30강 19:15~
//[ 수행 시간 측정 AOP ]
//'클래스 Performance'와의 연계를 통해 '어노테이션 @RunningTime'의 로직을 완성한 후,
//이 '@RunningTime'을 '이 프로젝트 firstproject' 안에 있는 '어떤 메소드 위에' 갖다 붙이면,
//그러면, '@RunningTime'의 특성인 '수행시간 측정' 및 이에 대한 '로깅' 로직이 실행된다!
//(당연히, '로깅' 로직은 따로 붙여줘야 함)
//e.g) '컨트롤러 CommentApiController의 메소드 Delete' 위에 '@RunningTime'을 붙이면, '메소드 Delete'의 '수행 시간'을 측정해서
//로깅 가능!
@Target({ElementType.TYPE, ElementType.METHOD }) //'어노테이션' 적용 대상(target) 지정. 궁금하면 구글링하기
@Retention(RetentionPolicy.RUNTIME) //'어노테이션 유지기간'을 'Runtime 시점'까지 유지하겠다!. 궁금하면 구글링하기
public @interface RunningTime {


}
