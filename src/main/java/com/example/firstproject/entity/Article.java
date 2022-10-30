package com.example.firstproject.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


//'@Entity'를 붙여주어야, 이제 DB가 이 'entity 객체'를 인식하여 db에 저장시켜줄 수 있게 됨
//'Entity 객체' 내용 자체는 'dto 객체' 내용과 거의 유사함
@Entity
@AllArgsConstructor //'롬복'. 아마 필요한 생성자를 대신 추가해주는 어노테이션.
@NoArgsConstructor //'롬복'. '디폴트 생성자'를 추가해주는 어노테이션. 아래에서 롬복을 통해 '생성자 만드는 과정'을 없애줬지만, 그래도 어떤 이유로
                   //(JPA스펙에서 디폴트 생성자를 요구하기 떄문) '디폴트 생성자'가 필요함(홍팍 [스프링부트 입문 11] 데이터조회하기 with JPA 14:00~).
                   //그런데, '디폴트 생성자'는 아래에서 직접 쓰지 않고, 이렇게 어노테이션 하면 동일하게 기능함.
@ToString //'롬복'.
@Getter //'롬복'. 필요한 모든 '게터'들을 자동 생성시켜줌
@Setter //'롬복'. 필요한 모든 '세터'들을 자동 생성시켜줌
public class Article {


    //'대푯값 지정(id)'. 사람으로 따지면 '주민등록번호'임. '제목'과 '이름'이 같은 여러 데이터가 있을 수 있고,
    //그 데이터들 사이에서 이 데이터를 정확히 구분짓기 위해 보통 아래처럼 설정해둠
    @Id
    @GeneratedValue
    private Long id;

    //'@Column'을 붙여주어야, 이제 아래 핃드를 'DB에서 관리하는 컬럼'으로 db에서 인식하게 됨
    @Column
    private String title;

    @Column
    private String content;


    //'게터', '세터'를 아래처럼 추가해줄 수 있으나, 위에서 '롬복'을 통해서 필요한 모든 '게터', 세터'들을 자동 생성시켜줬음
    /*
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    */


//< '롬복'을 통해 저~ 위에 '@AllArgsConstructor'를 붙여주면, 아래 '생성자 만드는 과정'은 없애줄 수 있음
    /*
    public Article(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
     */


    //< '롬복'을 통해 저~ 위에 '@ToString'을 붙여주면, 아래 '메소드 toString를 만드는 과정'은 없애줄 수 있음 >
    /*
    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
     */
}
