package com.example.firstproject.dto;


import com.example.firstproject.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentDto { //사용자가 보낸 '조회, 생성, 수정 등'의 정보가 담긴 dto 객체(사용자가 보낸 'JSON 객체의 세부 내용')
                          //'commentDto'는, Postman 통신할 때 JSON 형태로 '화면에 구체적으로 보여지는 id: ~, title: ~, content: ~'
                          //를 말하는 것임

    private Long id; //해당 댓글의 id번호


    @JsonProperty("article_id") //만약, '사용자'가 JSON으로 보낼 때, 그 본문 내용에 'article_id'로 작성했다면,
                                //이 '@JsonProperty'를 붙임으로써, 사용자는 저 'article_id'를 아래의 'articleId'로 보냈다는
                                //것을 컨트롤러가 인식하여 적절히 잘 교정하여 받아줄 수 있게 됨.
    private Long articleId; //해당 댓글이 속한 게시글의 id번호


    private String nickname; //해당 댓글의 작성자


    private String body; //해당 댓글의 내용


    //'Comment 엔티티 객체'를 'CommentDto 객체'로 '변환'시켜주는 메소드
    public static CommentDto toCommentDto(Comment comment){ //- '개별 댓글 entity 객체인 Comment'를 '개별 댓글 dto 객체'로
                                                                // 변환시켜주는 메소드.
                                                                //'static'은 '클래스 메소드'를 선언할 때 사용됨
        return new CommentDto( //23강 12:00~. '개별 댓글 CommentDto 객체를 만들어주는 생성 메소드 CommentDto'
                comment.getId(),
                comment.getArticle().getId(),
                comment.getNickname(),
                comment.getBody()
                );
    }

}
