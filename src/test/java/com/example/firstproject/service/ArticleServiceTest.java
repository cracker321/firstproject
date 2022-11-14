package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleDto;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//[스프링 부트 입문 21] 테스트 작성하기

@SpringBootTest //'@SpringBootTest': 이제 아래 해당 클래스는 '스프링부트'와 '연동'되어 테스팅된다.
class ArticleServiceTest {

    @Autowired
    ArticleService articleService; //'클래스 ArticleService'를 테스트하기 위해, 그것을 이렇게 필드화시켜서 DI 시켜줌.


//==================================================================================================================

    @Test //아래 해당 메소드가 테스트를 위한 메소드이다! 라고 말해주는 것
    void index() {

        //1.< 예상(되는 데이터 출력값) >
        //'data.sql' 파일에서, 더미 데이터로 기존에 집어넣어줬기 때문에, 당연히 '데이터 조회'를 명령해주는
        //'articleService.index()'를 실행시키면, 아래와 같은 데이터들이 '조회'되어 결괏값으로 나온다.
        Article article1 = new Article(1L, "aaaa", "1111"); //1번 게시글 객체(=new Article(1L, "aaaa", "1111")
        Article article2 = new Article(2L, "bbbb", "2222"); //2번 게시글 객체(=new Article(2L, "bbbb", "2222")
        Article article3 = new Article(3L, "cccc", "3333"); //3번 게시글 객체(=new Article(3L, "cccc", "3333")

        List<Article> expected = new ArrayList<Article>(Arrays.asList(article1, article2, article3));
        //1, 2, 3번 게시글 객체를 ArrayList에 담아서('데이터묶음화'하여)
        //그것을 보기 편하게 다시 'List(데이터묶음)'화 시켜줌.


        //2.< 실제 수행 식(articleService 파일에 내가 기 작성한 구문) >
        List<Article> articles = articleService.index(); //'클래스 articleService'의 '메소드 index'를 호출하면
                                                         //그 결괏값은 '게시글 데이터 집합묶음'을 리턴함.


        //3.< 비교 >
        assertEquals(expected.toString(), articles.toString()); //'변수 expected의 출력값'과 '변수 articles의 출력값'이
                                                                //'같은지(assertEquals) 여부'를 비교 확인한다!
    }

//==================================================================================================================

    @Test
    void show_테스트성공하는경우_사용자가_입력한id값이_db에_존재하는id인경우() { //여기에는 따로 매개변수로 id 안 집어넣어도 되는 듯..?
        //< 예상(되는 데이터 출력값) >
        Long id = 1L; //만약, '사용자'가 '1번 id'를 입력하는 경우를 가정함. 항상 '오른쪽'부터 식 세워서 왼쪽으로 옮겨가기.
        Article expected = new Article(id, "가가가가", "1111"); //'1번 id'에 해당하는 '게시글 객체(=new Article(..))


        //< 실제 수행 식(ArticleService 파일에 내가 기 작성한 구문) >
        Article article = articleService.show(id);

        //< 비교 >
        assertEquals(expected.toString(), article.toString());
    }



    @Test
    void show_테스트실패하는경우_사용자가_입력한id값이_db에_존재하지않는id인경우(){
        //< 예상(되는 데이터 출력값) >
        Long id = -1L; //만약, '사용자'가 '-1번 id'를 입력하는 경우를 가정함. 더미데이터로는 1~3번 id밖에 없기 때문에 '-1번 id'는 존재X
        Article expected = null; //'메소드 show'의 '리턴값'은 'return articleRepository.findById(id).orElse(null);'이기에,
                                 //당연히, 여기서도 'null'이 그 결괏값으로 예상된다고 써준 것임.

        //< 실제 수행 식(ArticleService 파일에 내가 기 작성한 구문) >
        Article article = articleService.show(id);


        //< 비교 >
        assertEquals(expected, article); //'변수 expected'의 '예상되는 리턴값은 null'이어서, 'toString'으로 출력값이 없기에
                                         //비교할 때 'expected'와 'article' 둘 다 'toString'은 지워서 비교함.

    }

//==================================================================================================================

    @Test
    void create_사용자가_title과_content만_있는_dto를입력하는경우() { //21강 16:30~

        //< 예상(되는 데이터 출력값) >



        //< 실제 수행 식(ArticleService 파일에 내가 기 작성한 구문) >
        //articleService.create(ArticleDto);



        //< 비교 >


    }

//==================================================================================================================



}