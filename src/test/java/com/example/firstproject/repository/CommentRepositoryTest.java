package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//[스프링 부트 입문 22] 댓글 엔티티와 리파지터리(feat. 테스트)
@DataJpaTest //JPA와 연동한 테스트
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회") //'테스트 결과'에 '보여줄 이름': '특정 게시글의 모든 댓글 조회'
    void findByArticleId() {


        //[ Case 1 : '4번 게시글(article)'의 '모든 댓글들(List<Comment>)'을 '조회'하기 ] : 22강 20:20~

        //1.< 예상되는 출력값 >
        Long articleId = 4L; //만약, '사용자'가 '4번 게시글 id'를 입력하는 경우를 가정함.

        Article article = new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ");//먼저, '4번 게시글(article)'을 만듦.
        
        //'4번 게시글(article)'에 '달린' '모든 댓글들'의 목록
        Comment expected1 = new Comment(1L, article, "내 닉네임은 Park", "내 인생영화는 굳 윌 헌팅");
        Comment expected2 = new Comment(2L, article, "내 닉네임은 Kim", "내 인생영화는 아이 엠 샘");
        Comment expected3 = new Comment(3L, article, "내 닉네임은 Choi", "내 인생영화는 쇼생크 탈출");

        //cf) '메소드 Arrays.asList()':
        //'배열(Array)'을 '리스트(List)'로 '변환'시켜주는 메소드
        //e.g: List<Integer> list = Arrays.asList(364, 21, null);

        //cf) 'ArrayList'와 'List'의 차이:
        //'인터페이스 List'는 '도형'이라고 할 수 있고, '구현클래스 ArrayList'는 '정사각형'이라고 말할 수 있다.
        //'List<> list = new ArrayList<>()' 와 'ArrayList<> list = new ArrayList<>()'는 '동일한 결과'를 발생시키지만,
        //전자(List를 사용해 ArrayList를 생성하는 것)가 '유연성'에 있어서 더 큰 효과를 볼 수 있다.
        //https://velog.io/@junbeomm-park/Java-List-%EC%99%80-ArrayList-%EC%B0%A8%EC%9D%B4

        //'배열 {expected1, expected2, expected3}'를 'List'로 변환시켜준 것이다!
        List<Comment> expected = Arrays.asList(expected1, expected2, expected3);



        //2.< 실제 수행 식(CommentRepository 파일에 내가 기 작성한 구문) >
        List<Comment> comments = commentRepository.findByArticleId(articleId);


        //3.< 검증 >
        assertEquals(expected.toString(), comments.toString(), "4번 게시글에 달린 모든 댓글을 출력!");



//================================================================================================================

        //[ Case 2 : '1번 게시글(article)'의 '모든 댓글들 집합(List<Comment>)'을 '조회'하기 ]


        //1.< 예상되는 출력값 >
        Long articleId01 = 1L; //만약, '사용자'가 '1번 게시글 id'를 입력하는 경우를 가정함

        Article article01 = new Article(1L, "가가가가", "1111"); //먼저, '1번 게시글(Article)'을 만들어줌

        //Comment expected01 = new Comment(); //'1번 게시글(Article)'에 '달린' 모든 댓글들'의 목록
                                              //그런데, '1번 게시글(Article)'에 달린 댓글은 단 1개도 없게 내가 더미데이터로 이미
                                              //설정함. 즉, 1번 게시글은 그 자체만 존재하지, 하위에 어떤 댓글도 가지고 있지 않음
                                              //따라서, 여기서 '테스트 댓글'을 작성할 수 조차 없음.


        List<Comment> expected01 = Arrays.asList(); //따라서, 당연히 '비어 있는 댓글 배열(Array)'로 만든 'List'는
                                                    //당연히 '비어 있을 수밖에' 없음
                        
        
        
        //2.< 실제 수행 식(CommentRepository 파일에 내가 기 작성한 구문) >
        List<Comment> comments01 = commentRepository.findByArticleId(articleId);



        //3.< 검증 >
        assertEquals(expected01.toString(), comments01.toString(), "1번 게시글에 달린 댓글은 없음!");


//================================================================================================================




        //과제1
        //[ Case 3 : '9번 게시글(article)'의 '모든 댓글들 집합(List<Comment>)'을 '조회'하기 ]





//================================================================================================================

        //과제2
        //[ Case 4 : '-1번 게시글(article)'의 '모든 댓글들 집합(List<Comment>)'을 '조회'하기 ]

}



//================================================================================================================


    @Test //22강 28:44~
    @DisplayName("특정 닉네임의 모든 댓글들을 조회")
    void findByNickname() {


    }
}