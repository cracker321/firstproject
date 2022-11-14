package com.example.firstproject.objectmapper;

//< Object Mapper 객체 >
//'JSON'과 '자바 DTO 객체' 간 '상호 변환'을 제공하는 객체
//JSON -------> DTO : 중간에 '메소드 readValue'를 통해 변환
//JSON <------- DTO : 중간에 '메소드 writeValueAsString'을 통해 변환

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
//< Object Mapper 객체 >
//'JSON'과 '자바 DTO 객체' 간 '상호 변환'을 제공하는 객체
//JSON -------> DTO : 중간에 '메소드 readValue'를 통해 변환
//JSON <------- DTO : 중간에 '메소드 writeValueAsString'을 통해 변환

@Getter //'Dto 객체'를 'JSON 객체'로 변환시켜주기 위해서는, '클래스 Burger 내'에 'Getter'가 필요하다!!!
@AllArgsConstructor
@ToString
public class Burger {

    private String name;
    private int price;
    private List<String> ingredients;
}
