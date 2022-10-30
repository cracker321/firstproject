package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository <Article, Long> {
    //1.JPA에서 제공하는 '인터페이스 CrudRepository'를 활용하여 쉽게 'repository'를 만들어준다.
    //2.'제네릭 <>'에
    //(1)관리대상이 되는 'Entity 객체'인 'Article 객체'와
    //(2)그 'Article 객체'의 '대푯값 id의 타입'인 'Long'을 넣어준다.

    //'부모 클래스 CrudRepository'의 '부모 메소드 findAll'을 오버라이딩해준다.
    //마우스 우클릭 --> Generate --> 'Override Methods' 누르고 --> '메소드 findAll' 찾아서 누르기

    @Override
    ArrayList <Article> findAll(); //원래 최초에는 'Iterable<Article> findAll()'로 반환해주는데,
                                   //이를 우리에게 익숙한 'ArrayList 타입'으로 변환시켜줬음
}
