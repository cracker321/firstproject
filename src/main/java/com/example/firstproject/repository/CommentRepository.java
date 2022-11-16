package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//'ropository'에는 'db와 연동하는 코드들'이 작성됨
//'JpaRepository':  '페이징'과 'sorting'도 다 가능한 repository임

public interface CommentRepository extends JpaRepository<Comment, Long> { //'<Comment, Long id>'
                                                                          //'Comment': '관리할 entity'는 'Comment'임
                                                                          //'Long': '관리할 entity'인 'Comment'의
                                                                          //        'PK의 타입'

    //< '특정 게시글(article)'에 달린 '모든 댓글들'을 '조회'하기 > : '쿼리문으로 수행하는 방식'. Native Query 방식.
    @Query(value =                              //우리가 '수행시킬 쿼리문'을 여기에 입력시킴
            "SELECT *" +
            " FROM comment" +
            " WHERE article_id = : articleId",  //'articleId'는 '사용자가 입력해서 전달해준 특정 게시글의 id번호'
            nativeQuery = true)                 //이 'SQL문'에 맞게 수행하게 됨.
    List<Comment> findByArticleId(Long articleId); //'사용자'가 '특정 게시글의 id'를 입력했을 때,
                                                   //'해당 게시글'에 '달린' '댓글들 모두(=묶음)'를 'db'로부터 가져온다.


    //< '특정 닉네임(nickname)'이 작성한 '모든 댓글들'을 ''조회'하기 > : 'xml 파일' 속 로직으로 수행하는 방식(orm.xml 파일에 로직 있음)
    List<Comment> findByNickname(String nickname);

}
