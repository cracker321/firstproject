package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


//https://wikidocs.net/161165
//https://jobc.tistory.com/120
//https://doorbw.tistory.com/227
//https://wikidocs.net/160890

/*
< repositoy >
'entity' 자체만으로는 'db의 테이블'에 '저장', '조회' 등을 할 수 없다. 데이터 처리를 위해서는, 실제 db에 접근하여 연동하는
'DB Layer 접근자'인 'repository'가 필요함
'repository'는 'entity에 의해 생성된 db 테이블'에 '접근하는 메소드들(e.g: 메소드 findAll, 메소드 save..)을 사용하기 위한 인터페이스'임
'데이터 처리'를 위해서는, 'db 테이블'에 어떤 값을 넣거나, 어떤 값을 조회하는 등의 CRUD 가 필요하다.
이 때, 이러한 CRUD를 어떻게 처리할지 정의하는 계층이 바로 repository임
이제, 'ArticleRepository'를 통해, 'db의 article 테이블'에 '데이터를 저장, 조회'할 수 있음.


========================================================================================================
여기에, '메소드 findBy + entity클래스에 작성된 속성명(id, question, contetn, subject, create_date 등)'을
메소드 형식으로 작성해주면, 이제 그 해당 속성의 값을 db로부터 가져와서 조회할 수 잇음.
'repository 내부에 작성된 메소드명(e.g: findByAll, findById, findBySubject...)'은
'데이터를 조회하는 쿼리문의 where 조건을 결정하는 역할'임. 매우 많은 조합을 가질 수 있음.
https://kim-oriental.tistory.com/34

아래는 'JpaRepository'에 내장되어있는 기본제공 내장 메소드들임. 이 외에도 더 있고, 내가 임의로 더 생성해서 'repository' 안에 작성해 됨.

< 항목 >         :                           < 예제 >                                      :       < 설명 >
And             :   findBySubjectAndContent(String subject, String content)              :  여러 컬럼을 and로 검색
Or              :   findBySubjectOrContent(String subject, String content)               :  여러 컬럼을 or로 검색
Between         :   findByCreateDateBetween(LocalDateTime fromDate, LocalDateTimetoDate) :  컬럼을 between으로 검색
LessThan        :   findByLessThan(Integer id)                                           :  작은 항목 검색
GreaterThanEqual:   findByIdGreaterThanEqual(Integer id)                                 :  크거나 같은 항목 검색
Like            :   findBySubjectLike(String subject)                                    :  like 검색
In              :   findBySubjectIn(String[] subjects)                                   :  여러 값들 중에 하나인 항목 검색
OrderBy         :   findBySubjectOrderByCreateDateAsc(String subject)                    :  검색 결과를 정렬하여 전달

e.g 1) 'lastname'과 'firstname'이 입력받은 값과 일치하는 User들을 userId의 역순으로 정렬하여 5개의 데이터만 가져오는 쿼리문.
       첫 번째 작성된 '클래스 repository' 내부에 내가 작성한 이 쿼리메소드는, 그 아래 작성된 '실제 쿼리문'과 동일하다
       userRepository.findFirst5ByLastnameAndFirstnameOrderByUserIdDesc("ori", "kim");
             위, 아래는 동일한 의미임
       select *
       from user
       where last_name=?
       and first_name=?
       order by user_id desc limit5

e.g 2) '입력받은 날짜'보다 'startDate'가 '적거나 같은( <= )' 데이터가 있다면, true를, 없다면 false를 리턴하는 메소드
        userRepository.existsByStartDateLessThanEqual(LocalDateTime.now());
            위, 아래는 동일한 의미임



cf) 응답 결과가 여러 건인 경우에는, repository 메소드의 리턴타입을 Question이 아닌 'List<Question>'으로 해야 한다.
보다 자세한 쿼리 생성 규칙은, 여기 공식 문서 참조
https://kim-oriental.tistory.com/34
https://www.devkuma.com/docs/jpa/%EC%9E%90%EB%8F%99-%EC%83%9D%EC%84%B1-%EC%BF%BC%EB%A6%AC-%EB%A9%94%EC%86%8C%EB%93%9C%EC%9D%98-%EB%AA%85%EB%AA%85-%EA%B7%9C%EC%B9%99/
https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation


*/

//스프링부트에서는 entity의 기본적인 CRUD가 가능하도록, '인터페이스 JpaRepository'를 제공한다.
//JPA에서 자동 제공하는 '인터페이스 JpaRepository'를 상속하기만 하면 되며, 인터페이스에 따로 @Repository 등을 추가할 필요 없음
//JpaRepository를 상속받을 때에는, '사용될 entity 클래스'와 'id값'이 들어가게 된다.
//즉, 'JpaRepository< T, id >가 된다.


//'JpaRepository'를 상속받는 것 하나만으로, '자체 인터페이스 ArticleRepository'는, '해당하는 1개의 entity'에 대하여
//아래와 같은 기능을 사용할 수 있게 된다.
//아래는 'JpaRepository의 기본 내장 메소드들'로, 기본적인 CRUD를 처리할 수 있게 해줌.
//이 외에도 내가 임의로 명명규칙에 따라 추가해줘도 된다.

//'메소드 save': 레코드 저장(insert, update)
//'메소드 findOne': primary key(id번호)로 레코드 단건(1개의 레코드) 찾기
//'메소드 findAll': 전체 레코드 불러오기. 정렬(sort), 페이징(pageable) 기능
//'메소드 count': 레코드 개수
//'메소드 delete': 레코드 삭제
//'메소드 findById': '컨트롤러'로부터 전달받은 primary key(id번호)로 해당되는 레코드 찾기
//'메소드 getOne':

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

