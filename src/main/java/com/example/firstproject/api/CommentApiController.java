package com.example.firstproject.api;


import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.service.CommentSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//[스프링 부트 입문 23] 댓글 서비스와 컨트롤러

@RestController
public class CommentApiController {

    @Autowired
    private CommentSerivce commentSerivce;


//======================================================================================================================

    //[ GET ]: '댓글 조회'를 요청. '특정 게시글의 모든 댓글들'을 조회 23강 05:00~
    @GetMapping("/api/aritlces/{articleId}/comments") //'n번 게시글'에 달린 '모든 댓글들(집합체)' 조회
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId){ //- '사용자'에게 'Comment 엔티티'를
                                                                                    // 바로 리턴하기보다,
                                                                                    //'CommentDto'로 만들어서 변환하는 방식
                                                                                    //사용하는 것이 더 낫다

        //'service'에서는 'repository'가 db로부터 가져온 'entity 형식의 데이터들'을 '컨트롤러 및 사용자'에게 전달해주기 위해 Dto로 변환함.
        //: 백엔드 로직 다 처리하고, '컨트롤러'에서 '사용자'에게 'dto 객체'로 '변환'시켜서
        //다시 전달해주기로 '컨트롤러'에서 그렇게 로직을 작성했기 때문에, 'service'에서 'entity 데이터들'를 'dto 형태'로 변환시켜줌!


        //1.< '컨트롤러'가 'service'에게 먼저 'dto객체'를 전달해주고, 나중에 'serivce' 및 'repository'가 db 작업로직 완료 후
        //     '컨트롤러'는 'service'로부터 다시 'dto 객체'를 받아와서, 이를 여기 '컨트롤러'에서 'dto 객체 묶음'으로 만들어줌 >
        List<CommentDto> commentDtos = commentSerivce.comments(articleId); //'commentService.comments(articleId)'를 실행하고
                                                                           //넘어온 리턴값인 '묶음 댓글 dto'를,
                                                                           //여기 컨트롤러에서 '묶음 댓글 dto'로 만들어,
                                                                           //'commentDtos'에 저장해줌
                                                                           //(사용자에게 전달하는 최종물이 'commentDtos')


        //2.< '결과를 가져온다' > //강의에서는 일단 '잘 조회되었다'고 가정하여 ~~하게 썼으나,  23강 06:00~
                                //내가 임의로 '성공적으로 조회된 경우'와 '조회 실패한 경우'를 나눠서 아래처럼 작성해봄
        return (commentDtos != null) ?
                ResponseEntity.status(HttpStatus.OK).body(commentDtos) : //성공적으로 조회되었다면, 사용자에게
                                                                         //'commentDtos'를 실어서 보내주면 되고,
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();   //
    }


//======================================================================================================================




//======================================================================================================================

//https://cheershennah.tistory.com/179
//https://wildeveloperetrain.tistory.com/144

// < @RequestBody > 요청 본문. 클라이언트 --> 서버
//클라이언트에서 서버로 필요한 데이터를 요청하기 위해 JSON 데이터를 요청 '본문(body)'에 담아서 서버로 보내면,
//서버에서는 @RequestBody 어노테이션을 사용하여 HTTP 요청 본문에 담긴 값들을 자바객체로 변환시켜, 객체에 저장한다.
//즉, 'HttpRequeset'의 본문인 requestBody의 내용'을 '자바 객체'로 '매핑'하는 역할
//'클라이언트의 요청'이 '@RequestBody가 붙어있는 메소드'로 올 경우, 'DispatcherServlet'에서 먼저 '해당 HttpRequest의 미디어타입을
//확인'하고, 해당 미디어타입에 맞는 'MessageConverter'를 통해, '요청 본문인 requestBody'를 통채로 변환해서, 해당 메소드로 전달해준다.
//cf) 'GET 메소드'의 요청 경우에는, '클라이언트의 요청 데이터'가 'HttpRequest'의 requestBody'로 전달되는 것이 아니라!,
//'URL 또는 URI의 파라미터'로 전달되기 때문에, '@PathVariable' 또는 'RequestParam' 등의 어노테이션을 통해서
//'클라이언트의 요청'을 전달받아야 한다.

// < @ResponseBody > 응답 본문. 서버 --> 클라이언트
//서버에서 클라이언트로 응답 데이터를 전송하기 위해 @ResponseBody 어노테이션을 사용하여 자바 객체를
//HTTP 응답 본문의 객체로 변환하여 클라이언트로 전송한다.
//즉, '자바 객체'를 'HttpResponse의 본문인 responseBody의 내용'으로 '매핑'하는 역할
//마찬가지로, '리턴 타입'에 맞는 'MessageConverter'를 통해 '리턴하는 객체'를 '해당 타입'으로 변환시켜서 '클라이언트'에게 전달해줌
//@RestController 를 붙인 컨트롤러의 경우, 따로 @ResponseBody를 명시하지 않아도,
//자동으로 'HttpResponse의 본문 responseBody'에 '자바 객체'가 '매핑'되어 전달된다.

    //[ POST ]: '새로운 댓글을 작성'하는 요청. 23강 15:35~
    @PostMapping("/api/articles/{articleId}/comments") //'요청 URL'에 'articleId'를 거친 이후에, 'comments'로 들어갈 수 있도록
                                                          //설계했으니, 당연히! 'articleId'에 대한 정보를 아래 로직에 주어야 한다!
        public ResponseEntity<List<CommentDto>> create(@PathVariable Long articleId,
                @RequestBody CommentDto commentDto){ //'사용자'가, '사용자가 작성한 새로운 댓글 내용'을 'JSON 객체의 body'에 담아서
                                                     //'컨트롤러'에게 전송함. '내용'을 'body'에 담아서 전송했기에,
                                                     //'@RequestBody'가 필요함

        //1.< '컨트롤러'가 'service'에게 먼저 'dto객체'를 전달해주고, 나중에 'serivce' 및 'repository'가 db 작업로직 완료 후
        //    '컨트롤러'는 'service'로부터 다시 'dto 객체'를 받아옴 >
        CommentDto createdDto = commentSerivce.create(articleId, commentDto); //'사용자'로부터 전달받은 'dto 객체'를
                                                                              //'service'에 전달해주고, 이것이 '잘 전달되어서,
                                                                              //db까지 가서 잘 작업해서 다시 컨트롤러로
                                                                              //잘 리턴되었다'라는 정보가 createdDto에 담김

                                                                              //- '사용자'에게 'Comment 엔티티'를
                                                                              // 바로 리턴하기보다, 이렇게
                                                                              //'CommentDto'로 변환하여 그 dto객체를
                                                                              //전달해주는 방식이 더 좋다!


        return (createdDto != null) ? //'사용자'로부터 '새로운 댓글 작성 정보'를 요청받아, 이것을 'db에 저장'하여 '새로운 댓글 작성'함.
                ResponseEntity.status(HttpStatus.OK).body(createdDto) : //'새로운 댓글 작성'을 최종적으로 성공하면, 'body'에
                                                                        //'createdDto'를 실어서 보냄

                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  //'새로운 댓글 작성'을 실패하면, '에러 상태코드'
    }

//======================================================================================================================




//======================================================================================================================

    //[ PATCH ] : '기존의 댓글을 수정'하는 요청. 23강 27:10~
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update (@PathVariable Long id, @RequestBody CommentDto commentDto){
        //'댓글 수정'은 '컨트롤러'의 '요청 URL'에 'articleId'이 없어서, 'comments'의 'id'로 들어갈 수 있도록 설계했으니,
        //당연히! 'articleId'에 대한 정보는 아래 로직에 필요없다!

        //1.< '컨트롤러'가 'service'에게 먼저 'dto객체'를 전달해주고, 나중에 'serivce' 및 'repository'가 db 작업로직 완료 후
        //    '컨트롤러'는 'service'로부터 다시 'dto 객체'를 받아옴 >
        CommentDto updatedDto = commentSerivce.update(id, commentDto);




        //2.< '결과를 가져온다' >
        return (updatedDto =! null) ?
             ResponseEntity.status(HttpStatus.OK).body(updatedDto) :
             ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

//======================================================================================================================




//======================================================================================================================

    //[ 댓글 삭제 ] : '기존의 댓글을 삭제'하는 요청. 23강 34:30~
    @DeleteMapping("api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id){ //'삭제 요청'이기에, '사용자'로부터 받을 'JSON 객체'도 없고
                                                                     //따라서, 당연히 '@RequestBody'도 필요없다!

        CommentDto deletedDto = commentService.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);


        return null;
    }










//======================================================================================================================




//======================================================================================================================








//======================================================================================================================



}
