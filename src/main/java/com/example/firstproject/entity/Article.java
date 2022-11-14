package com.example.firstproject.entity;

import lombok.*;

import javax.persistence.*;

//https://wikidocs.net/161165
//https://jobc.tistory.com/120
//https://doorbw.tistory.com/227


//@Entity: 데이터베이스에 저장하기 위해 사용자가 정의한 클래스. '객체' 그 자체. 'DB 테이블'과 '매핑'되는 '자바 클래스'.
//일반적으로, 'DB에서의 Table들을 객체화시킨 것'.
//'질문'과 '답변'을 할 수 있는 게시판 서비스를 만드려면, '질문'과 '답변'에 해당하는 'entity'가 있어야 함.

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
    //그 데이터들 사이에서 이 데이터를 정확히 구분짓기 위해 보통 아래처럼 설정해둠.
    //즉, 엔티티를 유일하게 구분할 수 있는 속성임.
    //즉, primary key를 가지는 변수를 선언하는 것을 뜻함.
    @Id //자기 자신의 '게시글 번호' = PK(Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 이제 db가 알아서 'id값'을 '자동으로 생성'해주는 어노테이션
                                                        // 개별 데이터값은 각각의 고유한 'id값'이 있고,
                                                        // 이 'id값'의 번호는 절대 중복되어서는 안된다!
    private Long id;

    //'@Column'을 붙여주어야, 이제 아래 핃드를 'DB에서 관리하는 컬럼'으로 db에서 인식하게 됨
    //기본적으로, '여기에서의 변수명'과 일치하는 'DB에서의 칼럼'을 '매핑'한다.
    @Column
    private String title;

    @Column
    private String content;


    public void patch(Article article){ //19강 23:00~
        if(article.title != null) {//'사용자'가 '일부만 변경된 게시글' 데이터를 dto 형식으로 컨트롤러에게 보냈는데,
                                   //그 데이터 속성(id, title, content) 중에서 'title'은 정상적으로 포함되어 있다면,
                                   //그 title은 정상적으로 여기에 넣고
            this.title = article.title;
        }
        if(article.content != null){ // "       "       "
            this.content = article.content; // 그 content는 정상적으로 여기에 넣으면 된다
        }
    }


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
