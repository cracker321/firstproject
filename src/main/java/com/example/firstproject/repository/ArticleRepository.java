package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


//https://wikidocs.net/161165
//https://jobc.tistory.com/120
//https://doorbw.tistory.com/227


//스프링부트에서는 entity의 기본적인 CRUD가 가능하도록, '인터페이스 JpaRepository'를 제공한다.
//JPA에서 자동 제공하는 '인터페이스 JpaRepository'를 상속하기만 하면 되며, 인터페이스에 따로 @Repository 등을 추가할 필요 없음
//JpaRepository를 상속받을 때에는, '사용될 entity 클래스'와 'id값'이 들어가게 된다.
//즉, 'JpaRepository< T, id >가 된다.

//'JpaRepository'를 상속받는 것 하나만으로, '자체 인터페이스 ArticleRepository'는, '해당하는 1개의 entity'에 대하여
//아래와 같은 기능을 사용할 수 있게 된다.
//'메소드 save': 레코드 저장(insert, update)
//'메소드 findOne': primary key(=id)로 레코드 단건(1개의 레코드) 찾기
//'메소드 findAll': 전체 레코드 불러오기. 정렬(sort), 페이징(pageable) 기능
//'메소드 count': 레코드 개수
//'메소드 delete': 레코드 삭제


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

