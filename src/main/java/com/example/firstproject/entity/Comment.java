package com.example.firstproject.entity;


//'1개의 Article(게시글)'에 'N개의 Comment(댓글)'관계 = Many to One 관계

import com.example.firstproject.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {


    @Id //'자신의 댓글 번호' = PK(Primary Key)  (자신의 key 번호). 여기서 PK는 총 9개 있음(1~9번까지의 댓글이 있으니, 총 9개의 댓글)
        //'자신이 댓글을 단 게시글의 게시물 번호' = FK(Foreign Key) (상대방 key 번호) 여기서 FK는 총 3개 있음(1~6번까지의 게시글이 있음)
    @GeneratedValue(strategy = GenerationType.IDENTITY) //'해당 댓글의 게시물 번호'를 'db'가 '자동으로 1씩 늘어나게 붙여주도록 함'.
    private Long id; //'해당 댓글의 게시물 번호'



    @ManyToOne //'N개의 Comment(댓글) entity'가 '1개의 Article(게시글) entity'에 달리는 관계 = Many to One 관계(다대일 관계)
    @JoinColumn(name = "article_id") //'해당 N개의 댓글들'이 '몇 번 id의 게시글(article_id)'에 달려 있는지를,
                                     // 'comment 테이블의 컬럼'에 명시해줌
                                     //이 'article_id'가 'FK(Foreign Key)'임
                                     //!!!그리고, 이 컬럼명은 'article_id'가 된다!! 절대, 'article'이 아니다!!!!
    private Article article; //'해당 댓글'의 '부모 게시글'이 있어야 되기 때문에, 속성으로 달아줌. '어떤 게시글'에 '댓글'을 달 지 결정해야 하니.



    @Column
    private String nickname; //'해당 댓글 단 사람의 nickname'



    @Column
    private String body; //'해당 댓글의 내용'


    //'service'가 'ropository'에게 db에 접근하라고 명령하기 전에, 우선 'dto 객체'를 'entity 객체'로 변환시켜서 넘거줘야 하기 때문에, 
    //그 경우에 'dto --> entity' 과정에 필요하므로, 아래처럼 dto 객체를 entity 객체로 변환시켜주는 '메소드 toComment'가 필요함
    public static Comment toComment(CommentDto commentDto, Article article) { //23강 22:20~

        //1-(1) < db에 해당 게시글이 없는 경우, 내가 임의로 강제로 예외 발생시키는 구문 작성 Case 1 >
        if (commentDto.getId() != null) //만약, '사용자'가 '자신이 새롭게 작성한 댓글을 commentDto 객체에 넘겨줬는데, 그 새로운 댓글에
            //사용자 자신이 임의로 '새로운 댓글 id'를 붙여준 경우'에는 올바르게 작동하지 않는다.
            //왜냐하면, '새로운 댓글'이 달리 때마다, '댓글의 id번호'는
            //'저~ 위에 @GeneratedValue(strategy=~)'에 의해
            //'시스템이 자동으로 댓글 게시물 번호를 1씩 증가'시켜주기 때문이다.
            //따라서, 그런 경우에는, 예외를 발생시켜줘야 한다!
            throw new IllegalArgumentException("댓글 생성 실패! 사용자님께서 임의로 댓글 id를 작성하시면 안됩니다!");


        //1-(2) < db에 해당 게시글이 없는 경우, 내가 임의로 강제로 예외 발생시키는 구문 작성 Case 2 >
        if (commentDto.getArticleId() != article.getId()) //- '사용자가 자신이 새롭게 작성한 댓글을 commentDto 객체에 담아
            //'컨트롤러'와 'service'에 넘겼는데',
            //이 때 '새롭게 작성한 댓글이 속한 게시글(article)의 id'와
            //- JSON에 담겨져 넘어온 'URL 요청 속의 {articleId}'가 다를 경우에는,
            //이것도 문제이기에, 이 경우에도 예외를 발생시켜줘야 한다!
            throw new IllegalArgumentException("댓글 생성 실패! 사용자님께서 게시글의 id를 잘못 넘겨주셨습니다!");


        //2.정상적으로 'commentDto 객체'를 'comment 엔티티 객체'로 변환하여 entity를 생성해주는 로직
        return new Comment(
                commentDto.getId(), //'commentDto 객체의 id값'을 가져와서, 그것을 'comment 엔티티 객체의 id값'으로 설정해줌
                article,
                commentDto.getNickname(), //commentDto 객체의 nickname값'을 가져와서, 그것을 'comment 엔티티 객체의 nickname값'으로
                commentDto.getBody() //'commentDto 객체의 body값'을 가져와서, 그것을 'comment 엔티티 객체의 body값'으로 설정해줌
        );

    }
    
    //'댓글 수정' 작업 후, 'Comment 엔티티'를 'Comment Dto'로 변환시켜주는 메소드 
    public void patch(CommentDto commentDto){ //23강 31:50~. //'commentDto'는, Postman 통신할 때 JSON 형태로
                                                             //화면에 구체적으로 보여지는 id: ~, title: ~, content: ~
                                                             //이런 내용을 말하는 것임.

        //1.< 'id'가 일치하지 않아서, 예외 발생시켜야 하는 경우 >
        if(this.id != commentDto.getId()) //'db 내부에서 찾은 id(사용자가 보낸 URL요청 속의 id)'가(this.id),
                                          //'사용자가 보낸 수정완료한 댓글 정보가 담긴 dto 객체(사용자가 보낸 JSON 객체의 내용) 속의 id'와
                                          //(comment.getId())
                                          //다르다면, 아래처럼 예외를 발생시켜줘야 한다.
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다!");

        //2-(1)< 정상적으로 수정작업 진행시켜서, 'nickname'을 업데이트 해야 하는 경우 >
        if(commentDto.getNickname() != null) //'사용자가 보낸 수정완료한 댓글 정보가 담긴 dto 객체(사용자가 보낸 JSON 객체의 내용) 속의
                                             //'nickname'이 올바르게 잘 존재한다면
                                             //(사용자가 'JSON 객체 속의 id, title, content와 같은 것'을 올바르게 잘 입력해서 보냈다면)
            this.nickname = commentDto.getNickname(); //'db 내부에서' 'db속 기존 댓글 entity의 nickname 부분'을
                                                      //'사용자가 보낸 dto 객체 속의 nickname'으로 업데이트해라!


        //2-(2)< 정상적으로 수정작업 진행시켜서, 'body'를 업데이트 해야 하는 경우 >
        if(commentDto.getBody() != null)
            this.body = commentDto.getBody();


    }

}
