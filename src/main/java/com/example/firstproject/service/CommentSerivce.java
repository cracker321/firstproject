package com.example.firstproject.service;


import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentSerivce { //댓글 서비스

    @Autowired
    private CommentRepository commentRepository; //

    @Autowired
    private ArticleRepository articleRepository; //'댓글 서비스'도 어쨌든 결국엔 '게시글(Article) 데이터'도 db에서 가져와서 사용해야함



//======================================================================================================================

    //[ GET ] : 댓글 목록 '조회' 요청. 23강 09:00~
    public List<CommentDto> comments(Long articleId){

        //'개별 댓글 entity ---> 묶음 댓글 entity ---> 개별 댓글 entity ---> 개별 댓글 dto ---> 묶음 댓글 dto'의 과정임

        //1.< 'service'는 'repository'에게 명령하여 db로 접근해서 'entity 형식의 댓글들' 가져오는 작업 하라고 시킴 >
        List<Comment> comments = commentRepository.findByArticleId(articleId);  //- 'repository'가 db에 접근하여,
                                                                    //'특정 게시글(articleId)에 속해 있는 댓글 모두'를
                                                                                //'개별 댓글 entity' 형태로 가져와준다!
                                                                                //- 위 데이터들은 '개별 댓글 entity'이므로,
                                                                                //그것들을 '묶음 댓글 entity'로 저장해준다.


        //2.< 'repository'가 db로부터 가져온 '개별 댓글 entity'들을 '컨트롤러 및 사용자'에게 전달해주기 위해 '묶음 댓글 Dto'로 변환 >
        //: 백엔드 로직 다 처리하고, '컨트롤러'에서 '사용자'에게 'dto 객체'로 '변환'시켜서
        //다시 전달해주기로 '컨트롤러'에서 그렇게 로직을 작성했기 때문에, 'service'에서 'entity 데이터들'를 'dto 형태'로 변환시켜줌!

        List<CommentDto> commentDtos = new ArrayList<CommentDto>();//db로부터 가져온 '개별 댓글 entity'들을
                                                                   //'묶음 댓글 dto'로 '아래'에서 변환시키기 위해,
                                                                   //'개별 댓글 dto'를 담아둘 ArrayList를 여기서 '미리' 만들어둠.
                                                                   //그리고, 아래 for문을 통해 이 ArrayList를 채워주어,
                                                                   //최종적으로, '묶음 댓글 dto'로 변환시켜준다!


        for(int i=0; i<comments.size(); i++){ //'comments': '묶음 댓글 entity'

            Comment c = comments.get(i); //'repository'가 db로부터 가져온 '묶음 댓글 entity'를 하나씩 풀어헤쳐 꺼내어
                                         //'개별 댓글 entity'로 변환시켜준다.
            CommentDto commentDto = CommentDto.createCommentDto(c); //- '클래스 CommentDto' 내부에 'entity --> dto 변환시키는
                                                                    //메소드인 createCommentDto'를 사전에 하나 만들어두고,
                                                                    //- 이를 통해, '개별 댓글 entity의 집합체인 c'를
                                                                    //'개별 댓글 dto 객체 commentDto'로 변환시켜준다.
            commentDtos.add(commentDto); //'개별 댓글 dto 객체 commmentDto'를, 이제 최종적으로 '묶음 댓글 commentDtos'로 만들어준다!
        }

        /* '위의 for문'은 '아래의 stream'으로 바꿔서 간단히 표현할 수 있다. // 23강 14:00~
        '저 아래 return문'과, '저 위에 List<Comment> comments = commentRepository.findByArticleId(articleId)'도 다 생략해줌.
        return commentRepository.findByArticleId(aritlceId)
                .stream()
                .map(content -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
         */


        //3.< 리턴 >
        return null;

    }

//======================================================================================================================

    //[ POST ]: '새로운 댓글을 작성'하는 요청. 23강 18:44~
    @Transactional //이 '메소드 create'는 db의 내부 정보를 건드리기에, db 정보에 변경이 일어날 수 있기 때문에,
                   //'@Transactional'을 적용시켜준다! 메소드 로직 진행 중에 어떤 문제가 생기면 '롤백'될 수 있도록 해주는 것이다!
    public CommentDto create(Long articleId, CommentDto commentDto){ //- '컨트롤러'의 '요청 URL'에 'articleId'를 거친 이후에,
                                                                     //'comments'로 들어갈 수 있도록 설계했으니,
                                                                     //당연히! 'articleId'에 대한 정보를 로직에 주어야 한다!
                                                                     //- 또한, 이 '메소드 create'의 리턴값으로 'dto 객체'를
                                                                     //반환하여 '컨트롤러'에게 전달시켜줘야 하기 때문에
                                                                     //이 '메소드 create의 타입은 CommentDto'이다!

        //1.< 'service'는 'repository'에게 명령하여 db로 접근해서 '사용자가 보낸 새롭게 생성한 댓글이 달린 특정 게시글의 정보와
        //  게시글 id가 담긴 dto 객체'에 대응하는 'entity 형식 게시글'이 db에 존재하는지 여부를 파악해서,
        //  존재하면 그 게시글들을 'entity 형식의 게시글'이라고 해서 저장함 >
        //          +
        //2.< db에 해당 게시글이 없는 경우, 내가 임의로 강제로 예외 발생시키는 구문 작성 >
        Article article = articleRepository.findById(articleId) //'메소드 findById'는 'JpaRepository의 기본 내장 메소드'임.
        .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! DB에 해당 댓글이 달린 특정 게시글이 없습니다!"));
                                                                //- '사용자가 보낸 댓글 dto 객체 속에 달린 특정 게시글'이
                                                                //'db에 존재하는지를 파악한다!
                                                                //- 만약, '그에 해당하는 게시글'이 db에 없다면(=orElse)
                                                                //'강제로 예외 IllegalArgumentException'을 발생시켜준다!(Throw)

        //3.< '사용자'와 '컨트롤러'로부터 받아온 '게시글 id'에 해당하는 '게시글'이 db에 있는 경우, 이제 드디어
        //    '사용자가 이미 작성완료해서 컨트롤러로 전송해준 새로운 댓글 정보가 담긴 dto 객체'를 여기 service에서 '새로운 댓글 entity'로
        //    변환시켜주는 단계 >
        Comment comment = Comment.toComment(commentDto, article);   //- 컨트롤러로부터 '사용자가 이미 작성완료한 새로운 댓글
                                                                        //정보가 담긴 dto 객체'를 받아왔고,
                                                                        //그 'dto 객체'를 'entity 객체'로 변환시켜서
                                                                        //'db'에 저장켜주는 단계.


        //4.< (1) '변환된 새로운 댓글 entity'를 db에 저장시키고(commentRepository.save(comment))
        //    (2) ''db에 저장완료 했어!'라는 정보'를 다시 'service'로 가져와서, 이를 'entity 객체'에 담아줌(Comment created = ~) >
        Comment created = commentRepository.save(comment);
        //    (2)                  (1)


        //5.< 'db에 저장완료 했어!'라는 정보'를 담고 있는 'entity'를 'dto'로 변환시켜주기 >
        //  즉, '사용자'에게 '이러이러한 새롭게 댓글을 생성했어요! 새롭게 생성한 댓글 내용의 id, title, content,.. 속의 내용은
        //  이러이러해요!'라는 정보를, 'service'에서 'dto 객체'로 변환시켜주고, 이를 '컨트롤러'에서 '사용자'에게 'JSON 객체'로 전달해주는 흐름이다!
        //  Postman에서 이를 'JSON 객체' 형태로 확인 가능!
        return CommentDto.toCommentDto(created);
    }

//======================================================================================================================

    //[ PATCH ] : '기존의 댓글을 수정'하는 요청. 23강 28:55
    @Transactional
    public CommentDto update(Long id, CommentDto commentDto){ //- '댓글 수정'은 '컨트롤러'의 '요청 URL'에 'articleId'이 없고
                                                              //'comments'의 'id'로 들어갈 수 있도록 설계했으니,
                                                              //당연히! 'articleId'에 대한 정보는 로직에 필요없다!
                                                              //- 또한, 이 '메소드 create'의 리턴값으로 'dto 객체'를
                                                              //반환하여 '컨트롤러'에게 전달시켜줘야 하기 때문에
                                                              //이 '메소드 create의 타입은 CommentDto'이다!

        //< 댓글 조회 및 예외 발생시킴 >
        //1.< 'service'는 'repository'에게 명령하여 db로 접근해서 '사용자가 보낸 수정완료한 댓글의 정보와 id가 담긴 dto 객체'에 대응하는
        //    'entity 형식 댓글'이 db에 존재하는지 여부를 파악해서, 존재하면 그 게시글들을 'entity 형식의 댓글'이라고 해서 저장함 >
        //          +
        //2.< db에 '해당 댓글 entity'가 없는 경우, 내가 임의로 강제로 예외 발생시키는 구문 작성 >
        Comment target = commentRepository.findById(id)//'db 내부에서' '해당하는 id'를 찾고,
                                                       //'db 내부에서' 이에 해당하는 'entity 객체'를 찾는 과정
                                                       //- '변수 target'이나 '변수 comment'나 그냥 이름만 다를 뿐 걍 차이 없음!
                                                       //그냥 강사가 임의로 'comment' 대신 'target'이라 쓴 게 맞음.
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! DB에 해당 기존 댓글이 없습니다!"));


        //< 댓글 수정작업 >
        //3.< db로부터 수정되어야 할 기존 댓글을 가져왔다면, 이제 댓글 수정작업 로직 시작 >: 'db 내부에서' '수정작업 진행'함.
        target.patch(commentDto) //'기존 댓글 내용'을 '새로운 댓글 내용'으로 수정해주는 '메소드 patch'는 'Comment 타입'이기 때문에,
                                 //'메소드 patch'를 '클래스 Comment'에 작성해줌.


        //< DB를 갱신시킴 >
        //4.< '사용자가 보낸 수정완료한 댓글'을 'db'에 '업데이트'시킴 >: 'db 내부에서' '수정작업' 완료했고,
        //                                                       'db 내부에' 이를 저장(업데이트)하여,
        //                                                        이와 관련된 정보를, 'entity 형태 객체(=변수 updated)'에 담아
        //                                                        'service'로 가져옴
        Comment updated = commentRepository.save(target);


        //4.< '수정완료한 댓글의 내용 entity'를 '수정완료한 댓글의 내용 dto(=사용자에게 다시 보낼 JSON 객체)'로 변환시키기 >
        //  즉, '사용자'에게 '이러이러한 댓글을 수정했어요! 수정한 댓글 내용의 id, title, content,.. 속의 내용은 이러이러해요!'라는 정보를
        //  'service'에서 'dto 객체'로 변환시켜주고, 이를 '컨트롤러'에서 '사용자'에게 'JSON 객체'로 전달해주는 흐름이다!
        //  Postman에서 이를 'JSON 객체' 형태로 확인 가능!
        return CommentDto.toCommentDto(updated);

    }

//======================================================================================================================

    //[ PATCH ] : '기존의 댓글을 삭제'하는 요청. 23강 35:12~
    @Transactional //'db의 내용을 건드리는 작업들'은 무조건 '@Transactional'을 붙여줘야 한다!
    public CommentDto delete(Long id){ //- 또한, 이 '메소드 create'의 리턴값으로 'dto 객체'를
        //반환하여 '컨트롤러'에게 전달시켜줘야 하기 때문에
        //이 '메소드 create의 타입은 CommentDto'이다!


        //< 댓글 조회 및 예외 발생시킴 >
        //1.< 'service'는 'repository'에게 명령하여 db로 접근해서 '사용자가 보낸 요청 URL 속의 id'에 대응하는
        //     'id가 db에 존재하는지 여부'를 파악해서, 존재하면 그 댓글을 db에서 삭제시킴 >
        //          +
        //2.< db에 '해당 댓글 entity'가 없는 경우, 내가 임의로 강제로 예외 발생시키는 구문 작성 >
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! '사용자가 보낸 요청 URL 속의 id'에 대응되는" +
                        "id가 db 속에 없습니다");


        //< 댓글 삭제작업 >
        //3.< db로부터 삭제되어야 할 기존 댓글을 가져왔다면, 이제 댓글 수정작업 로직 시작 >: 'db 내부에서' '삭제작업 진행'함.
        commentRepository.delete(comment); //'삭제 작업'이기에, 이것은 어딘가에 다시 저장해두거나 하지 않아도 된다!
                                           //그냥 이 자체로 오케이!

        
        //4.< '삭제된 댓글 entity'를 '삭제된 댓글 dto(=사용자에게 다시 보낼 JSON 객체)'로 변환시키기 > 23강 37:09~
        //  즉, '사용자'에게 '이러이러한 댓글을 삭제했어요! 삭제한 댓글 id, title, content,.. 는 이러이러해요!'라는 정보를
        //  'service'에서 'dto 객체'로 변환시켜주고, 이를 '컨트롤러'에서 '사용자'에게 'JSON 객체'로 전달해주는 흐름이다!
        //  Postman에서 이를 'JSON 객체' 형태로 확인 가능!
        return CommentDto.toCommentDto(comment);
    }

}


//======================================================================================================================
















































































