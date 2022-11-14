package com.example.firstproject.api;


import com.example.firstproject.dto.ArticleDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
REST API: https://memostack.tistory.com/180 참조

https://www.inflearn.com/questions/93071
REST API는 서로의 최소한의 약속 정도로 봐주시면 될듯 하며, 강의에서 말씀드린 URL 규칙, REST들은 실제 실무에서는
환경, 상황, 보안 등 여러가지 상황으로 인해서 REST 하지 않을 수 있습니다.

             <   SQL   > 	   -	  <   HTTP Method   >
C : 생성(Create. SQL의 CREATE)  - 	     	POST
R : 조회(Read. SQL의 SELECT)    - 	        GET
U : 수정(Update) 			   -	   	 PUT / PATCH
D : 삭제(Delete) 			   - 	       DELETE


- 'RestController'는 'return 값'으로 'JSON형식의 데이터'를 반환해서 사용자에게 응답해준다!
- '일반 Controller'는 'return 값'으로 '뷰 페이지 경로'를 반환해주는 것 같음...확실히!


클라이언트: 손님
Controller: 웨이터(손님 응대)
Service: 주방장
Repository: 보조요리사

 */

//[스프링 부트 입문 19] HTTP와 RestController.
@Slf4j //'log.info'를 작동 가능하게 해주는 어노테이션.
@RestController //'RestAPI용 컨트롤러'. 보통 JSON형식의 데이터를 반환함
public class ArticleApiController {

    @Autowired //의존성주입. '서비스객체 ArticleService'를 이제 여기 '컨트롤러 ArticleApiController'에서도 사용할 수 있게 됨
               //'클래스 ArticleService'를 이렇게 바로 여기서 '필드화' 시켜 DI 해줄 수 있음.
    private ArticleService articleService;

    //[ GET ]: '게시글 조회'를 요청. '모든 게시글들'을 조회
    @GetMapping("api/articles")
    public List<Article> index() { //'모든 article(게시글)들(=데이터 집합체이기에 'List'를 사용)을 db로부터 가져오는 메소드.

        return articleService.index(); //이제 기존의 'return articleRepository.findAll()'이 아니라,
        //이 'reuturn문'을 'service'로 보내서 그 내부에 작성하도록 하고,
        //여기서는 그 'return문 내용을 담은', 'serivce 객체의 메소드 index'를 호출한다.
    }


    //[ GET ]: '게시글 조회'를 요청. '특정 하나의 게시글'을 조회
    @GetMapping("api/articles/{id}")
    public Article show(@PathVariable Long id) { //'사용자'가 '컨트롤러'에게 'id 번호'를 JSON 형식 데이터로 dto에 담아 건네주면서 
        //'특정 하나의 article(게시글)'을 db로부터 가져와! 라고 명령했기에,
        //이를 실행하고자, '컨트롤러'가 'service'에게 명령하며 데이터 조회하라는 메소드

        return articleService.show(id);
    }


//==================================================================================================================




//==================================================================================================================

    //[ POST ]: '새로운 게시글을 작성'하는 요청.
    @PostMapping("api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleDto articleDto) { //- 'Rest API'에서 '클라이언트'가
                                                                                //JSON 형식으로 매개변수에 dto 데이터를 전송할 때는
                                                                                //무조건 @RequestBody를 넣어줘야 함
        Article created = articleService.create(articleDto); //'사용자'로부터 전달받은 'dto 객체'를 'service'에 전달해주고,
                                                             //이것이 '잘 전달되어서, db까지 가서 잘 작업해서 다시 컨트롤러로
                                                             //잘 리턴되었다'라는 정보를,
                                                             //'Article 엔티티 객체'에 넣어줌.

                                                             //cf) 근데, 이것도 사실 'CommentApiController'에서처럼
                                                             //'Article 엔티티 객체'로 반환할 것이 아니라,
                                                             //'Article Dto 객체'로 반환해야 가장 이상적이다!
                                                             //(23강 댓글 밑으로 쭉 내리면 관련 질문 및 답변 있음)


        return (created != null) ? //'사용자'로부터 '새로운 게시글 정보'를 요청받아 이것을 'db에 저장'하여 '새로운 게시글 작성'함.
                ResponseEntity.status(HttpStatus.OK).body(created) : //성공하면 --> 'OK 상태코드'. 'body'에 'created'를 실어서 보내줌
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();//실패하면 --> '에러 상태코드'. cf) 삼항연산자.
        //'build'를 'build(null)'로 해줘도 됨.
    }

//==================================================================================================================




//==================================================================================================================

    //[ PATCH ] : '기존의 게시글을 수정'하는 요청
    //- '사용자'가 '수정해야 할 게시글'을 '이미 작성완료하여서', 그 데이터를 'dto'에 담아서 '컨트롤러'로 보내줌
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleDto articleDto){
        //-'컨트롤러(웨이터)'는 '클라이언트'로부터, '클라이언트가 보내는 정보(매개변수)'만 잘 받아오고,
        //- 중간 요리 과정은 '서비스(주방장)'에게 하라 명령하고
        //- 그 결과(return값)를 들고 손님에게 잘 갖다주기만 하면 된다.


       Article updated = articleService.update(id, articleDto); //'사용자'로부터 전달받은 'dto 객체'를 'service'에 전달해주고,
                                                                //이것이 '잘 전달되어서, db까지 가서 잘 작업해서 다시 컨트롤러로
                                                                //잘 리턴되었다'라는 '정보'를,
                                                                //'Article 엔티티 객체'에 넣어줌.

                                                                //- '컨트롤러(웨이터)'가 '서비스(주방장)'에게
                                                                //'클라이언트(손님)가 이미 수정완료한 게시물'을
                                                                //자신(컨트롤러)이 받았으니
                                                                //이를 'repository'에게 명령해서 db로 가져가서 업데이트 좀 해줘!
                                                                //라고 '서비스(주방장)'에게 명령하는 것.
                                                                //- 업데이트를 할 때는, '컨트롤러(웨어터)'가 '클라이언트(손님)'
                                                                //으로부터 받아온 'id'와 'ArticleDto'를 '서비스''에게 
                                                                //당연히 전달해줘야함.
                                                                //- 그리고, 그것을 'entity 객체'에 담아서 db로 전달해주기 위해
                                                                //'entity 객체'인 'Article'에 담아둠.


                                                                //cf) 근데, 이것도 사실 'CommentApiController'에서처럼
                                                                //'Article 엔티티 객체'로 반환할 것이 아니라,
                                                                //'Article Dto 객체'로 반환해야 가장 이상적이다!
                                                                //(23강 댓글 밑으로 쭉 내리면 관련 질문 및 답변 있음)


        return (updated != null) ? //'dto 객체'가 'entity 객체'로 '잘 변환되어 데이터가 온전히 존재'하면,
                ResponseEntity.status(HttpStatus.OK).body(updated): //'상태코드 OK'를 보내주고,
                                   ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //그렇지 않으면, '에러코드'를 보내줌
    }

//==================================================================================================================




//==================================================================================================================

    //[ DELETE ]
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){ //'삭제 기능'이므로, '사용자'로부터 '특정 데이터'를 '받아올 필요가
                                                               //없으니', '@RequestBody ArticleDto articleDto'는 없다.

        Article deleted = articleService.delete(id); //'사용자'로부터 전달받은 'dto 객체'를 'service'에 전달해주고,
                                                     //이것이 '잘 전달되어서, db까지 가서 잘 작업해서 다시 컨트롤러로
                                                     //잘 리턴되었다'라는 '정보'를
                                                     //'Article 엔티티 객체'에 넣어줌..
                                                     //- '웨이터(컨트롤러)'는 주문만 받고,
                                                     //'요리(=삭제)'는 '주방장(=서비스 객체)'에게 맡긴다!
                                                     //- db에서 '삭제되어야 할 대상이 되는 entity' 찾아서,
                                                     //그것을 'deleted'이라는 'entity 객체'에 집어넣기

                                                     //cf) 근데, 이것도 사실 'CommentApiController'에서처럼
                                                     //'Article 엔티티 객체'로 반환할 것이 아니라,
                                                     //'Article Dto 객체'로 반환해야 가장 이상적이다!
                                                     //(23강 댓글 밑으로 쭉 내리면 관련 질문 및 답변 있음)

        return (deleted != null) ? //'Article 엔티티인 deleted'가
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() : //성공적으로 삭제되었다면, good요청을 보내주면 되고
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //삭제에 실패했다면, bad요청으로 보내주면 된다
    }

//==================================================================================================================




//==================================================================================================================

    //[ 트랜잭션 --> 실패 --> 롤백 ]
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>>  transactionTest(@RequestBody List<ArticleDto> articleDtos){
                                                                        //'사용자'가 '상태코드를 함께 보여주는 컨트롤러'에
                                                                        //'Article 객체의 데이터 묶음단위'를 전송함.

        List<Article> createdList = articleService.createArticles(articleDtos); //'사용자'로부터 전달받은 'dto 객체'를
                                                                                //'service'에 전달해주고,
                                                                //이것이 '잘 전달되어서, db까지 가서 잘 작업해서 다시 컨트롤러로
                                                                //잘 리턴되었다'라는 '정보'를, 'Article 엔티티 객체'에 넣어줌.

                                                                    //cf) 근데, 이것도 사실 'CommentApiController'에서처럼
                                                                    //'Article 엔티티 객체'로 반환할 것이 아니라,
                                                                    //'Article Dto 객체'로 반환해야 가장 이상적이다!
                                                                    //(23강 댓글 밑으로 쭉 내리면 관련 질문 및 답변 있음)

        return (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }




}

//==================================================================================================================
/*

        //홍팍 강의에서 '20강_서비스 계층과 트랜잭션' 배우기 전까지는 아래 내용대로 했음.
        //'컨트롤러'가 'repository'를 직접 다루는 등의 2인분 역할까지 했던 것임.

        @Autowired //외부(스프링부트 자체)에서 아래 필드를 DI 해줘야 하기 때문에 '@Autowired'를 쓰는 것.
        private ArticleRepository articleRepository;

//==================================================================================================================




//==================================================================================================================

    //[ GET ]: '조회'하는 요청. '모든 게시글들'을 조회

    @GetMapping("/api/articles")
    public List<Article> index(){ //'모든 article(게시글)들(=데이터 집합체이기에 'List'를 사용)을 db로부터 가져와 조회하는 메소드.


        return articleRepository.findAll(); //'모든 article(게시글)들(=데이터 집합체이기에 'List'를 사용)을 db로부터 가져옴
    }



    //[ GET ]: '조회'하는 요청. '특정 하나의 게시글'을 조회
    
    @GetMapping("/api/articles/{id}") //'특정 하나의 article(게시글)'을 db로부터 가져와 조회하는 메소드
    public Article index(@PathVariable Long id){ //- 바로 위에 '메소드 index'와는 '오버라이딩 관계(매개변수만 다름)'이기 때문에
                                                 //'메소드 이름'이 같아도 괜찮다.
                                                 //그리고, 위와는 달리 '개별 article(게시글)'이기에, '데이터 집합체(List 형식)'가
                                                 // 아니므로, 'List<Article>'을 빼줌.
                                                 //- 아래에서 리턴으로 'findById(id)'를 해주기 때문에, '메소드 index'의 '매개변수'로
                                                 //당연히 'id'도 넣어줘야 함
                                                 //- 그리고, 그 'id'도 'URL 요청'으로부터 가져와야 하기 때문에, 
                                                 //'@PathVariable'을 넣어주어야 함

                                                 //'사용자'가 '컨트롤러'에게 'id 번호'를 JSON 형식 데이터로 dto에 담아 건네주면서
                                                 //'특정 하나의 article(게시글)'을 db로부터 가져와! 라고 명령했기에,


        return articleRepository.findById(id).orElse(null); //db에서 'id 값'을 가져오는데, '해당 id값'이 없는 경우에는
                                                                  //'null'을 리턴함.
    }

//==================================================================================================================




//==================================================================================================================

    //[ POST ]: '새로운 게시글을 작성'하는 요청.
    @PostMapping("/api/articles")    //dto 흐름도: https://sedangdang.tistory.com/296
                                     //           https://plo-developdiary.tistory.com/m/62
                                     //1.'사용자'가 전송한 '게시글(article)'을 'dto' 형태로 'cotroller'가 받아서
                                     //2.이를 'entity'로 변환하는 과정을 하고,
                                     //3.그 변환한 'entity'를 'service'로 보내고
                                     //4.'service'가 다시 'repository'에 보내고
                                     //5.'repository'가 'db'에 접근하여
                                     //6. 이 데이터를 '저장(save)'해야 하기 때문에,
                                     //'POST'를 사용함
    public Article create(@RequestBody ArticleDto articleDto){ //- 강의에서는 'ArticleForm dto'이라고 써주긴 했으나,
                                                               //확실한 개념정립을 위해
                                                               //나는 'ArticleDTO articleDto'라고 해줌
                                                               //- 'RestAPI'에서 '클라이언트'가 'JSON 형식'으로 데이터를
                                                               //전달할 때에는, 반.드.시. '@RequestBody'를 붙여줘야 함
                                                               //'@RequestBody'에서 'ArticleDto'를 받아와라! 라는 뜻임
        Article article = articleDto.toEntity(); //'DTO 객체'를 'entity 객체'로 변환시켜줌
        return articleRepository.save(article); //'사용자'가 전송한 '게시글(article)'을 'repository'를 통해 
                                                //'db'에 '저장(save)'해야 하기 때문에 'repository'를 사용함
    }

//==================================================================================================================




//==================================================================================================================

    //[ PATCH ] : '새로운 게시글을 수정'하는 요청
    //- '사용자'가 '수정해야 할 게시글'을 '이미 작성완료하여서', 그 데이터를 'dto'에 담아서 '컨트롤러'로 보내줌
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleDto articleDto){
        //'update작업'할 때에, 컨트롤러는, db처리 작업 완료 후, 다시 사용자에게 응답하며 말해줄 때,
        //'에러 상태코드(404, 500, ...)'를 같이 보내주기 위해서는,
        // 'Article 객체(entity)'를 '자바 내장클래스 ResponseEntity'에 '담.아.서' 보내줘야 함.

        //1.'사용자가 이미 수정완료한 article 데이터'가 'dto'에 담겨져서 '컨트롤러'로 보내지고,
        //2.'컨트롤러'는 이 'dto'를  받아서, 이를 '수정완료된 ariticle(게시글) entity'로 변환시켜주고,
        //3.'repository'는 'db'로 접근하여, '수정될 대상이 되는 entity'가 'db'에 있는지를 '해당 id값'으로 조회해서 가져오고
        //4.db에 '해당 id'에 대응되는 데이터가 있다면, 이를 '변수 target'에 담은 후,
        //5.'사용자가 수정완료한 aritcle 데이터'를 'repository'를 통해 '새로운 entity인 'updated''에 담아서 '저장(save)'해준다.



        //1.< '수정용 entity'를 생성 >
        Article article = articleDto.toEntity();
        log.info("id: {}, article: {}", id, article.toString()); //log를 찍어봄('controller' 상단에 '@Slf4j' 붙여줘야 함.
                                                                 //'id(우)'는 '첫 번째 중괄호'로 들어가고,
                                                                 //'article.toString(우)'는 '두 번째 중괄호'로 들어감

        //2.< '수정되어야 할 대상 entity'가 'db에 있는지 조회(db에 '해당되는 id값'이 없거나, '해당 id값과는 다른 id값'만이 있는 경우) >
        Article target = articleRepository.findById(id).orElse(null); //'수정되어야 할 entity'를 'db'로부터 찾아보는데
                                                                            //그 '수정되어야 할 entity'가 있다면, 그것을
                                                                            //가져오고, 그 entity를 '변수 target'이라고 함.

        //3.< 잘못된 요청 처리 >
        if(target == null || id != article.getId()){ //- 'db'에 '수정되어야 할 대상 데이터(entity)'가 '없거나',
                                                     //- 사용자가 보낸 URL 요청 속에 들어 있는 'id'(1, 2, 3 .. id번호 그 자체)와
                                                     //('URL 주소 /api/articles/2'에서의 '2')
                                                     //'컨트롤러'가 'JSON 형식'을 통해 받아온 'dto' 속의 'id'가 다른 경우
                                                     //(Postman에서 그 JSON형식으로 받아온 dto 속의 id를 확인할 수 있음.
                                                     // 19강 18:30~)

            log.info("잘못된 요청인 id: {}, article: {}", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); //'자바 내장클래스 ResponseEntity'의 
                                                                             //'내장메소드 status'에 저렇게 써서 보내주면
                                                                             //'에러 상태코드'를 같이 보내줄 수 있다.
                                                                             //일단 'body'에는 아무것도 안 실어서 보내줌

        }


        //4.< 잘못된 요청이 아니라면, 사용자가 수정완료한 article 데이터를, 'repository'를 활용하여,
        // '새 entity인 'udpdated''에 '저장(save)'하여서, 정상 업데이트 및 정상 응답(데이터 통신 시 '200을 띄워줌') >

        //4-(1): '사용자'가 '컨트롤러'에 'JSON 형식'으로 데이터를 'dto'에 담아 보낼 때, 필요한 모든 데이터가 아니라
        //       일부가 빠진 상태로 보낸 경우
        //       e.g) 사용자가 JSON을 통해 'id', 'title', 'content'을 다 보내야 하는데,
        //            'id', 'content'만 보낸 경우 등.
        //상세설명 https://programmer93.tistory.com/39
        //        https://papababo.tistory.com/entry/HTTP-METHOD-PUT-vs-PATCH-%EC%B0%A8%EC%9D%B4%EC%A0%90
        target.patch(article); //'사용자'가 '일부만 변경된(일부는 일부러 집어넣지 않거나. 여기서는 'title'을 빼고 수정함) 게시글'
                               // 데이터를 'dto'로 보내면, 그것을 entity로 변환한 후,
                               //'메소드 patch('Article 엔티티'에 적어주는 메소드)'에 넣고,
                               //그것을 '변수 target('db'에서 '수정되어야 할 entity'를 조회하여, '변수 target'에 집어넣었음)'으로
                               //표현된 'article 엔티티 객체'에 '붙여줌'

        //Article updated = articleRepository.save(target); //그리고, 그 '사용자가 일부분 수정완료한 article 엔티티(=변수 target)'을
                                                            //'새로운 entity'인 'updated'에 담아서 '저장(save)'해준다.
                                                            //여기서는 바로 아래 '변수 updated'와 겹치니깐, 그냥 이거 주석처리해줌


        
        //4-(2).일반적으로 정상적인 경우
       Article updated = articleRepository.save(article); //'사용자가 수정완료한 aritcle 데이터'를 'repository'를 통해
                                                          // '새로운 entity인 'updated'에 담아서 '저장(save)'해준다.

        return ResponseEntity.status(HttpStatus.OK).body(updated); //- 그냥 단순히 'return updated'로만 적어서 보내줘도 되지만,
                                                                   //'상태코드'도 같이 보내주기 위해 저렇게 해서
                                                                   //'updated'도 같이 저기에 '실어서' 보내줌.
                                                                   //저기 'OK' 대신 '200'(=정상응답)을 적어서 보내줘도 됨.
    }

//==================================================================================================================




//==================================================================================================================

    //[ DELETE ]
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){ //'삭제 기능'이므로, '사용자'로부터 '특정 데이터'를 '받아올 필요가
                                                                  //없으니', '@RequestBody ArticleDto articleDto'는 삭제한다.

        //1.< db에서 '삭제되어야 할 대상이 되는 entity' 찾아서, 그것을 'target'이라는 'entity 객체'에 집어넣기 >
        Article target = articleRepository.findById(id).orElse(null);

        
        //2.< 잘못된 요청 처리 >
        if(target == null){ //'db'에서 '삭제되어야 할 대상이 되는 entity'를 '못 찾는다면'(=db에 삭제되어야 할 entity가 없다면)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        //3.< 정상 요청: '삭제되어야 할 대상'을 '삭제'하기 >
        articleRepository.delete(target); //'Delete 삭제 과정'은, '삭제한 entity'를 '어딘가에 담아서' 또 보내줘야 하거나
                                          //그런 과정이 필요 없으므로, 'repository'가 '삭제되어야 할 entity'를 삭제하게 명령하고
                                          //나면, 이제 더 이상 할 건 없음ㄴ

        return ResponseEntity.status(HttpStatus.OK).build(); //'build(null)'로 해줘도 됨. 어차피 디폴트값이 null이기 때문
    }

     */


