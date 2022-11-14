package com.example.firstproject.service;


import com.example.firstproject.dto.ArticleDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service //서비스 선언! (서비스 객체를 스프링부트에 생성하는 어노테이션)
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository; //'클래스 ArticleRepository'를 이렇게 바로 여기서 '필드화' 시켜서 DI 해줌.


//======================================================================================================================

    //[ GET ]: '게시글 조회'를 요청. '모든 게시글들'을 조회
    public List<Article> index(){

       return articleRepository.findAll(); //'service'가 'repository'에게
                                           //'너 db에 접근해서 모든 게시글(데이터) 집합체 '조회'해서 가져와!'라고 명령하는 것
                                           //'데이터 집합체'이기에 'List'가 'Article 객체'를 감싸줌.
    }

//======================================================================================================================

    //[ GET ]: '게시글 조회'를 요청. '특정 하나의 게시글'을 조회
    public Article show(Long id){ //'사용자'가 '컨트롤러'에게 'id 번호'를 JSON 형식 데이터로 dto에 담아 건네주면서
                                  //'특정 하나의 article(게시글)'을 db로부터 가져와! 라고 명령했기에,
                                  //여기서도, 당연히 그 'dto'를 받아서 처리해줘야 함.

        return articleRepository.findById(id).orElse(null);
    }

//======================================================================================================================

    //[ POST ]: '새로운 게시글을 작성'하는 요청.
    @Transactional //단순 '데이터 조회'가 아닌, '데이터 수정, 조작' 등의 경우에는, 모두 '트랜잭션'으로 묶어줘야 한다!
    public Article create(ArticleDto articleDto){ //'사용자'가 '컨트롤러'에게 'JSON 형식의 새로운 게시글 데이터'를
                                                  //'dto'에 담아 보내줬기 때문에, 여기서도, 당연히 그 'dto'를 받아서 처리해줘야 함
        Article article = articleDto.toEntity(); //'service'에서 'dto 객체'를 'entity' 객체로 변환시켜주고,
                                                 //저 아래에서 이를 'repository'에게 건네줘서,
                                                 //'repository'가 db에 접근하여 이를 저장(save)함.

        if(article.getId() != null) {  //만약에, '사용자'가 '컨트롤러'에게 보낸 '새로운 게시글 데이터'에 'id'가 이미 입력되어 있다면,
            return null;               //'사용자'가 입력한 id는 빼버려라! 왜냐하면, 게시글이 작성될 때, 게시글 번호(id)는 자동으로
                                       //생성되기 때문이다.
        }
        return articleRepository.save(article); //'service'가 'repository'에게 'dto에서 entity로 변환한 이 객체'를
                                                //'db'로 가져가서 db에 저장(save)해! 라고 명령하는 것
    }

//======================================================================================================================

    //[ PATCH ] : '새로운 게시글을 수정'하는 요청
    @Transactional //단순 '데이터 조회'가 아닌, '데이터 수정, 조작' 등의 경우에는, 모두 '트랜잭션'으로 묶어줘야 한다!
    public Article update(Long id, ArticleDto articleDto){

        //1.< 수정용 entity를 생성 >: 'service'에서 'dto 객체'를 'entity' 객체로 변환시켜주고,
        //                          이를 'repository'에게 건네줘서, 'repository'가 db에 접근하여 이를 저장(save)함.

        Article article = articleDto.toEntity(); //- '컨트롤러(웨이터)'가 '서비스(주방장)'에게
                                                 //'클라이언트(손님)가 이미 수정완료한 게시물'을 자신(컨트롤러)이 받았으니
                                                 //이를 'repository'에게 명령해서 db로 가져가서 업데이트 좀 해줘!
                                                 //라고 '서비스(주방장)'에게 명령하는 것.
                                                 //- 그리고, 그 'dto 객체'를  'entity 객체'로 변환시킨 후, 'entity 객체'에 담아서
                                                 //db로 전달해주기 위해 'entity 객체'인 'Article'에 담아둠.


        //2.< '수정되어야 할 대상 entity'가 'db에 있는지 조회(db에 '해당되는 id값'이 없거나, '해당 id값과는 다른 id값'만이 있는 경우) >
        Article target = articleRepository.findById(id).orElse(null); //'수정되어야 할 entity'를 'db'로부터 찾아보는데
                                                                            //그 '수정되어야 할 entity'가 있다면, 그것을 가져오고,
                                                                            //그 entity를 '변수 target'이라고 함.

        //3.< 잘못된 요청 처리 >
        if(target == null || id != article.getId()){

            return null; //'컨트롤러'에서는 이 부분이 ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null) 로 작성됨.
                         //이것은 '컨트롤러(웨이터)'의 역할이니, 여기 '서비스(주방장)' 파트에서는 이 부분 뺀다.
                         //따라서, '잘못된 요청'이 올 경우에는, 그냥 'null'만 리턴해주면 된다.
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

        Article updated = articleRepository.save(target); //그리고, 그 '사용자가 일부분 수정완료한 article 엔티티(=변수 target)'을
                                                          //'repository'를 통해 '새로운 entity'인 'updated'에 담아서 '저장(save)'해준다.
                                                          //여기서는 바로 아래 '변수 updated'와 겹치니깐, 그냥 이거 주석처리해줌
        return updated;
    }

//======================================================================================================================

    //[ DELETE ]
    @Transactional //단순 '데이터 조회'가 아닌, '데이터 수정, 조작' 등의 경우에는, 모두 '트랜잭션'으로 묶어줘야 한다!
    public Article delete(Long id){

        //1.< db에서 '삭제되어야 할 대상이 되는 entity' 찾아서, 그것을 'target'이라는 'entity 객체'에 집어넣기 >
        Article target = articleRepository.findById(id).orElse(null);


        //2.< 잘못된 요청 처리 >
        if(target == null){ //'db'에서 '삭제되어야 할 대상이 되는 entity'를 '못 찾는다면'(=db에 삭제되어야 할 entity가 없다면)
            return null; //'컨트롤러'에서는 이 부분이 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); 로 작성됨.
                         //이것은 '컨트롤러(웨이터)'의 역할이니, 여기 '서비스(주방장)' 파트에서는 이 부분 뺀다.
                         //따라서, '잘못된 요청'이 올 경우에는, 그냥 'null'만 리턴해주면 된다.
        }

        //3.< 정상 요청: '삭제되어야 할 대상'을 '삭제'하기 >
        articleRepository.delete(target); //'Delete 삭제 과정'은, '삭제한 entity'를 '어딘가에 담아서' 또 보내줘야 하거나
                                          //그런 과정이 필요 없으므로, 'repository'가 '삭제되어야 할 entity'를 삭제하게 명령하고
                                          //나면, 이제 더 이상 할 건 없음.

        return target; //'삭제된 대상 entity 객체'를 '리턴해줌'
    }

//======================================================================================================================

    //[ 트랜잭션 --> 실패 --> 롤백 ] : 20강 18:56~
    @Transactional //'@Transactioanl'을 넣으면, 이제 아래 메소드는 '트랜잭션'으로 묶인다!
                   //즉, 아래 메소드의 로직을 순서대로 실행시키다가, 만약에 '실패'하게 된다면,
                   //이 메소드가 실행되기 이전 상태로 '롤백'을 한다!
                   //'@Transactional'은 'service'에서 붙여준다!('트랜잭션' 처리는 'service'에서 해주는 것이다!)
                   //단순 '데이터 조회'가 아닌, '데이터 수정, 조작' 등의 경우에는, 모두 '트랜잭션'으로 묶어줘야 한다!
    public List<Article> createArticles(List<ArticleDto> articleDtos){

        //1.< 'dto 데이터묶음'을 'entity 묶음'으로 변환시킴 >  //23강 23:05~
        List<Article> articleList = new ArrayList<>();
        for(int i=0; i<articleDtos.size(); i++){       //
            ArticleDto articleDto = articleDtos.get(i);//'ArrayList'의 '메소드 get(i)'는, 'i번째 인덱스'의 위치에 있는 객체를 리턴.
            Article entity = articleDto.toEntity();    //
            articleList.add(entity);                   //'ArrayList' 속에 저장하려면, '메소드 add'를 활용하는 것임.
        }

        /* 위 'for문'과 아래 'stream' 형식은 동일한 의미임.
        List<Article> articleList = articleDtos.stream()  //'dto 데이터 묶음'을 '스트림화'시킨 후
                .map(articleDto -> articleDto.toEntity()) //'map'으로 만들어서, '하나 하나의 ArticleDto가 올 때마다',
                                                          //'entity'로 변환시켜주고, 
                                                          //이것을 '매핑'해주고,
                .collect(Collectors.toList());            //그 매핑된 것들을 이제 'List(데이터 묶음)'로 변환시켜줌.
        */


        //2.< 'entity 묶음'을 'db'에 저장(save)시킴 >
        for(int i=0; i<articleList.size(); i++){
            Article article = articleList.get(i); //'ArrayList'의 '메소드 get(i)'는, 'i번째 인덱스'의 위치에 있는 객체를 리턴.
            articleRepository.save(article);
        }

        /* 위 'for문'과 아래 'stream' 형식은 동일한 의미임.
        articleList.stream() //'articleList'를 '스트림화'시킨 후
                .forEach(article -> articleRepository.save(article)); //'한 개의 article(게시글)'을 전송받을 때마다,
                                                                      //그것들을 'db'에 '저장(save)' 시켜준다
        */


        //3.< 강제 예외 발생시키도록 함 >: 일부러 예외 발생시켜보려고, db에 '-1번째 id값'이 있는지 찾아봐라! 라고 repository에게 명령함.
        //                             트랜잭션 실패에 따른 롤백을 테스트해보기 위함이다!
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결제 실패!")
        );


        //4.< 결괏값 반환 >
        return articleList; //위에서 강제로 예외가 발생되긴 하지만, 그래도 형식상 'articleList'를 리턴해준다고 써주긴 했음.



    }


}
