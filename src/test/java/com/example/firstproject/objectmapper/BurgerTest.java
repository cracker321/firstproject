package com.example.firstproject.objectmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//< Object Mapper 객체 >
//'JSON'과 '자바 DTO 객체' 간 '상호 변환'을 제공하는 객체
//JSON -------> DTO : 중간에 '메소드 readValue'를 통해 변환
//JSON <------- DTO : 중간에 '메소드 writeValueAsString'을 통해 변환
//'Jackson 라이브러리'를 구글링하기!

class BurgerTest {

    @Test
    public void 자바객체인Dto객체를_JSON객체로_변환() throws JsonProcessingException {

        //1.< 준비 >
        ObjectMapper objectMapper = new ObjectMapper(); //'ObjectMapper 객체' 생성

        List<String> ingredients = Arrays.asList("통새우 패티", "순쇠고기 패티", "토마토", "스파이시 어니언 소스");
        //'클래스 Burger'의 '필드 ingredients'가 'List'로 선언되어 있으니,

        Burger burger = new Burger("맥도날드 슈비버거", 5500, ingredients);


        //2.< 예상(되는 데이터 출력값) >
        String expected = "{\name\":\"맥도날드 슈비버거\",\"price\":5500,\"ingredients\"" +
                ":[\"통새우 패티\",\"순쇠고기 패티"\",\"토마토\",\"스파이시 어니언 소스"\]; //'Dto 객체'가 'JSON 객체'로 '변환될 때'
                                                                                    //예상되는 'JSON 객체'의 내용
                                                                
                    



        //3.< 실제 수행 식(CommentRepository 파일에 내가 기 작성한 구문) >
        String json = objectMapper.writeValueAsString(burger); //'ObjectMapper 객체'가 '새롭게 JSON 객체'를 만들기 원함
                                                               //'내장 메소드 writeValueAsString'에 'Burger 객체'를 넣어주면,
                                                               //그것이 이제, String 타입의 'JSON 객체'를 리턴해준다!


        //4.< 비교 검증 >
        assertEquals(expected, json);

        JsonNode jsonNode = objectMapper.readTree(json); //'변환된 JSON 객체'를 '트리 형식'으로 좀 더 이쁘게 출력해주는 기능.
                                                         //'내장 메소드 readTree'.
        System.out.println(jsonNode.toPrettyString());   //'내장 메소드 toPrettyString'. 이쁘게 출력해줌.

        //System.out.println(json); //잘 작동되었다면, 확인차 'JSON 객체'를 한 번 출력해보는 것

    }

//======================================================================================================================

    //31강 12:10~
    @Test
    public void JSON객체를_자바객체인Dto객체로_변환 () throws  {

        //1.< 준비 >



        //2.< 예상(되는 데이터 출력값) >



        //3.< 실제 수행 식(CommentRepository 파일에 내가 기 작성한 구문) >



        //4.< 비교 검증 >

}