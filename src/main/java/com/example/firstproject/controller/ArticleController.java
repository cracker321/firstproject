package com.example.firstproject.controller;


import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


//'form 태그'로부터 넘어온 데이터가 --> 'dto 객체'를 통해 --> '컨트롤러'로 넘어온다
@Controller
@Slf4j //'로깅' 기능을 위한 어노테이션
       //'로깅 기능'
       //: 서버에서 일어나고 있는 모든 일들을, 마치 자동차의 블랙박스처럼 매 순간 다 기록해주는 것
public class ArticleController {


    @Autowired //'단순 변수 선언 후'에, '@Autoweird'만 붙여주면, 스프링부트가 미리 'ArticleRepository 객체' 생성해둔 것을, 
    // '클참뉴클' 과정 없이, 자동으로 객체 생성됨
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {

        return "articles/new";
    }


    //1.'뷰 new'에서 화면의 '제목', '내용'에 사용자가 데이터를 입력하면
    //2.'입력된 데이터'의 최종 목적지는 '컨트롤러'이기에, 여기 아래에 해당 내용 작성하되, 일단 순서는  'dto 객체'로 넘어오고
    //3.'dto 객체'에 해당 내용 적어서 다시 '컨트롤러(여기)'로 넘어옴
    @PostMapping("/articles/create") //'뷰 new의 폼 태그'에서의 'action'에 적힌 URL주소.  + '뷰 new'는 'Post 방식'
    public String createArticle(ArticleForm form) { //'form 태그(='뷰 new'의 'action="/articles/create" method="post"')
                                                    // 로부터 넘어온 데이터는 다음처럼
                                                    // '매개변수'로 'dto 객체 타입'의 'form'을 넣어줌(선언해줌)


        log.info(form.toString());
        /* 'form 태그'로부터 데이터를 잘 받아왔는지 여부의 확인은 --> 'log.info(article.toString)'으로 써야 함
        System.out.println(form.toString()); //
        */

        //-'Entity'는 DB가 '자바 객체'를 잘 이해할 수 있게 규격화한 데이터
        //-'Enttiy'는 'Repository'라는 '일꾼'을 통해 DB에 전달되고 처리됨. 즉, 이제 db속 테이블에 관리됨
        //DTO를 --> Entity로 변환하고 --> 이 Entity는 Repository를 통해 --> DB에 전달되어 저장됨

        //==========================================================================================================

        //< 1.DTO를 'Entity'로 변환시키고, 이후 >
        Article article = form.toEntity();

        log.info(article.toString());
        /* 잘 변환됐는지 여부의 확인은 --> 'log.info(article.toString)'으로 써야 함
        System.out.println(article.toString()); //'dto 객체'가 'entity 객체'로 잘 변환되었는지 여부를 '이 출력문' 통해서 확인해기 가능
        */
        //(1)'클래스 Article('dto객체'를 변환시켜 줄 'entity 객체)''을 '컨트롤러와 등급인 위치'에 추가시켜줌.
        //(2)'메소드 toEntity'를 'dto 객체'에 추가시켜줌


        //==========================================================================================================

        //< 2.Repository가 Entity를 DB 속에 저장되게 함 >
        //(1)'save된 데이터'를 'Aricle 타입'의 데이터로 반환하게 한다.
        //(2)'ArticleRepository 객체'를 사용하기 위해 저~~ 위에 필드에서 'ArticleRepository 객체 만들어주고',
        //   '아래 변수 articleRepository'를 선언해줌.
        //   그런데, 굳이 '클참뉴클' 안 해도 되는 것이, '단순 변수 선언해준 곳 위에' '@Autoweird'만  붙여주면
        //   스프링부트가 알아서 '클참뉴클' 해주기 때문이다
        //(3)'클래스 repositoryArticle('entity 객체'를 db에 전달시켜 줄 'repository 객체')를 '컨트롤러와 동급인 위치'에 추가시켜줌
        //(4)'클래스 repositoryArticle'에서 관련 작업 해주고, 
        Article saved = articleRepository.save(article); //'메소드 save'는 'entity 객체'를 db에 넘겨주는 중간 전달 역할

        log.info(saved.toString());
        //잘 변환됐는지 여부의 확인은 --> 'log.info(saved.toString)'으로 써야 함
        /*
        System.out.println(saved.toString()); //'entity 객체'가 'repository'를 통해 'db'에 잘 'saved' 되었는지 여부를 이 메소드로 확인함
        */

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}") //클라이언트가 던진 요청 URL 주소값 '/articles/{변수 id(id값은 1, 2, 3 등 변할 수 있음)}'을
    //이제 '컨트롤러'에서 받아와야 하는데
    //'변수 id'는 '개별 게시글 번호'임. 사용자가 '화면'에서 '개별 게시글'을 누르면, URL로 'http://localhost:9090/articles/{id}'가 전달됨.
    public String show(@PathVariable Long id, Model model) {
        //< '@PathVariable' >
        // '요청 URL 속의 변수 id'를 제대로 받기 위해 '
        // 타입 Long인 변수 id('entity 객체' 속에 선언되어있음)'를 매개변수로 주고
        //이 '변수 id'는 '요청 URL에 실려 들어왔다'는 의미를 나타내주는 '@PathVariable'을 입력시켜줌


        //'URL 속의 변수 id'를 잘 받아왔는지 여부 확인하는 단계
        //아래를 통해서, URL '~ localhost:9090/articles/1000(또는 아무 숫자나 다 됨. 23, 4250 등등)' 치면, 아래 로그에 기록됨
        log.info(String.valueOf(id)); //원래 강의에서는 'log info(id)'였음. 그런데 이거 오류떠서 저렇게 인텔리제이가 수정해줌.


        //1< .'사용자가 입력한 데이터'를 '변수 id'를 통해  'db에 전달'(데이터를 가져옴(조회))해주고, 그것을 다시 db로부터 '컨트롤러'가 가져옴 >
        //  :'컨트롤러'와 'db' 사이의 'Entity 객체(데이터) 전달'을 담당해주는 'repository 객체'를 사용함
        //   즉, 'repository 객체'에서 '메소드 findById'와 '변수 id'를 통해 'id값 조회' 가능
        //   ('컨트롤러' --> 'DB')

        //'articleRepository'가 'findById''로 '값을 반환'할 때, 그 '반환값의 타입'이 'Article'이 아니기 때문에, 
        //아래처럼 'Optional < >' 를 'Article'에 씌워줌
        Article articleEntity = articleRepository.findById(id).orElse(null);
        //또는, Optional<Article> articleEntity = articleRepository.findById(id); 로 적어줘도 됨
        //'URL로 들어온 변수 id'값을 찾았는데, 만약 db에 해당 'id 값'이 없다면, 'null'을 반환하라는 의미.


        //< 2.'db로부터 가져온 데이터'를 '모델 객체'에 등록 >
        //< 'Model model' >
        //클라이언트로부터 가져온 데이터를 '모델 객체'에 등록시킴. 그리고, 이것을
        //( 'DB' --> '컨트롤러' )

        model.addAttribute("article", articleEntity);


        //< 3.'보여줄 페이지'를 설정 >
        return "articles/show";
    }

    //< 데이터 Read >
    @GetMapping("/articles") //이 URL 요청이 들어오면,
    public String index(Model model) {

        //< 1.모든 Article을 가져온다 >
        //JPA 부분( 컨트롤러 --> DB ). 'db'로부터 데이터를 가져오기 위해서는 'repository'가 필요함
        //'메소드 findAll()': '해당 repository'에 있는 데이터를 모두 가져옴

        //방법(1): '데이터 묶음'이기에 '컬렉션 List' 사용함. 그리고 그 데이터는 'Article 타입'으로 가져옴.
        List<Article> articleEntityList = (List<Article>) articleRepository.findAll(); //다운캐스팅 해줘야 함
        //'레포 ArticleRepository'에서 'ArrayList <Article> findAll();' 이렇게 오버라이딩 해줬기 때문에,
        //여기서도 'List <Article>' 대신 그 하위타입인 'ArrayList <Article>' 로도 해줘도 된다.

        //방법(2): '해당 repository의 반환타입인 iterable'에 'articleEntityList의 타입'을 맞춰준다.
        //Iterable<Article> articleEntityList = articleRepository.findAll();   // 이렇게 해줘도 됨. 방법(1), 방법 (2) 중 아무거나 선택.


        //< 2.가져온 Article 묶음을 '뷰'로 전달함 >
        //위에서 가져온 'articleEntityList'를 '뷰'로 전달할 떄는, '모델 객체'를 사용하여 '뷰'에 전달해줌
        //그리고, '모델 객체'를 사용하기 위해서는, 위에 '해당 메소드의 매개변수'에 '모델 객체'를 넣어줘야 함
        model.addAttribute("articleList", articleEntityList);


        //< 3.'뷰 페이지'를 설정 >
        return "articles/index"; // URL 'articles/index' 안에 '뷰 페이지 articles/index.mustache'가 나올 수 있도록 해줌
    }


    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){ //아래 '메소드 findById()의 매개변수로 id'를 주었기 때문에, 이 'id'가 뭔지 여기 메소드에서
                                               //'미리 선언'시켜줘야 하기 때문에, 여기 매개변수로 'Long id'를 넣어줌.
                                               //그런데 그 'Long id'를 어디서 가져온다? 'URL 경로(Path)'에서 가져온다!
                                               //따라서, '@PathVariable'을 입력해줌
                                               //cf) '@GetMapping("/articles/{id}edit") 속의 'id' 글자'와
                                               //    'public String edit(@PathVariable Long id, Model model) 속의 'id' 글자'가
                                               //반드시 '같아야' 한다!
        //1.'수정할 데이터'를 'DB'로부터 'Repository'를 통해 '가져오기'
        Optional<Article> articleEntity = articleRepository.findById(id); //여기서 'id'는 '위 URL 요청값'으로 들어온 '변수 id'에 해당하는 값을
                                        //'db'에서 찾아야 하기 때문에 매개변수로 넣어줌

        //2.'모델 객체'에 '데이터를 등록'시켜줌
        //  바로 위에서 'URL 요청으로 들어온 id'값에 해당하는 데이터를 'db'로부터 가져왔기 때문에,
        //  이제 그 데이터를 사용할 수 있도록 '모델 객체'에 그 데이터를 등록시켜줌
        //  (1)이 '메소드 edit의 매개변수로 model'을 넣어줌으로써 '변수 model'을 '미리 선언'해주고,
        //  (2)이제 그 '모델 객체'에서 '데이터를 넣어줌'
        model.addAttribute("article", articleEntity);
        //이제 'db'로부터 가져온 데이터를, 'article'이라는 이름으로 '뷰 페이지'에서 사용할 수 있게 됨

        //3.뷰 페이지 설정
        return "articles/edit"; //수정 관련 '뷰 페이지 edit.mustache'로 연결됨
    }

    @PostMapping("/articles/update")  //원래는 'CRUD'의 '수정 Update'이기 때문에, 'HTTP의 'PATCH(데이터 수정)'에 해당하는
                                        //'@PatchMapping()'을 넣어줘야 하는데, 'form 태그 자체'가 '@GetMapping'과 '@PostMapping'만
                                        //지원하기 때문에 '임시로 어쩔 수 없이' '@PostMapping'을 사용했음
    public String update(ArticleForm form){ //'form 태그(='뷰 edit'의 'action="/articles/update" method="post"')
                                            //로부터 넘어온 데이터는 다음처럼
                                            //'매개변수'로 'dto 객체 타입'의 'form'을 넣어줌(선언해줌)

        log.info(form.toString()); //데이터가 잘 전달되었는지 여부를 파악해주는 메소드

        //1.'컨트롤러'가 'form 태그(='뷰 edit'의 'action="/articles/update" method="post"')'로부터 받아온 데이터를 DB에 저장하기
        //  즉, 'Dto 객체'를 'Entity 객체'로 변환시키기
        //(1)'Dto 객체를 관리하는 클래스 ArticleForm'의 '메소드 toEntity'를 통해 'Dto 객체'를 'Entity 객체'로 변환시킨다.
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());


        //2.'Entity 객체'를 'DB'에 전달하여 저장시키기
        //순서 2-(1): 'repository'를 통해서, DB에서 기존 데이터를 가져온다. 'repository'가 '자동으로 제공하는 메소드 findById'를
        // 활용하여, '변환된 Entity 객체'에 'getter 메소드'를 활용하여 id값'을 집어넣음
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        //또는, Optional<Article> target = articleRepository.findById(articleEntity.getId()); 로 적어줘도 됨
        
        //'URL로 들어온 변수 id'값을 찾았는데, 만약 db에 해당 'id 값'이 없다면, 'null'을 반환하라는 의미.
        //(='articleRepository'가 '메소드 findById'를 통해 'id 값(현재 db에 있는 기존 데이터의 id)'을 가져오고자 하는데,
        // 만약에 해당하는 'id 값'이 없다면, 'null'을 리턴해라 라는 뜻)
        //즉, '변수 target'에 데이터가 담겨있으면, 'articleEntity'가 연결될 것이고,
        //                  데이터가 없다면, 'title(target?)의 값'은 null이 될 것임

        //순서 2-(2): '업데이트가 되어야 하는 기존 데이터의 값'을 '수정 갱신'한다
        if(target != null) { //'업데이트가 되어야 하는 기존 데이터'가 있다면(= 'target != null')
            articleRepository.save(articleEntity); //'repository'를 통해서 '새로운 Entity 객체(데이터)'를 'db'에 '갱신, 저장(save)'해줌
        }


        //3.'수정 결과를 뷰 페이지로' '리다이렉트 시키기'
        //(1)return "articles/index";   와   (2)return "redirect:/articles/" ('리다이렉트'는 '모델' 필요 없음)
        //위 두 개는 결과적으로 보여주는 페이지는 같으나,
        //(1)은 '해당 뷰 페이지'를 보여주는 것이고,
        //(2)'리다이렉트'는 'URL 주소로 바로 보내주는 것'이다.
        //https://sassun.tistory.com/61  에 설명.
        //
        //(3) https://blog.naver.com/allkanet72/220964699929
        //a)'리다이렉트'가 발생하면, 원래 요청은 끊어지고, 새로운 HTTP의 GET요청이 시작.
        //때문에, '리다이렉트 실행 이전'의 '모델 객체 데이터'는 소멸된다. 따라서, '리다이렉트'로 모델을 전달하는 것은 의미가 없기에,
        //애초에 '해당 메소드의 매개변수'에 '모델 객체'를 '선언할 필요 없음)
        //b)그러나, '리다이렉트' 방식으로도 '데이터를 전달'하고 싶으면, 'HTTP GET 방식'의 특징을 이용하여, '클래스 RedirectAttributes'를 활용하기.
        return "redirect:/articles/" + articleEntity.getId();
    }


    @DeleteMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청이 들어왔습니다"); //아직 화면에 나타낼 로직을 여기에 작성하진 않은 상태에서, 아래 콘솔창에 일단 '로그'라도 맞게 입력되는지
                                          //여부를 파악하기 위해서 작성함. 즉, 일단 '뷰 show'에 관련 코드 작성했으니,
                                          //'삭제 요청'이라도 제대로 들어오는지 여부를 파악하는 것.

        //1.'삭제 대상'을 가져오기
        //(1)'컨트롤러'와 'DB'사이를 소통시켜 주는 'JPA'는 'repository'를 통해 작동한다.
        //   즉, '컨트롤러'가 'DB'와 '일을 할 떄는', 항상 'repository'를 통해 일하므로, 'repository'를 작성해줘야 함.
        //  1)따라서, 'repository'를 통해(클래스 articleRepository)
        //  2)'들어온 URL 요청으로 들어온 id값에 해당하는(대응되는) id값'이 'db에 있는지 여부'를 파악(메소드 findById())해줘야 한다.
        //    일단 '특정 id값을 가져왔다고 가정'하고(findById(id). 또한, '메소드 매개변수'에 'Long id'를 넣어서 선언해줌.
        //    또한, '들어온 URL 요청 속 id값'이 'Long id'에 대입되기 위해서는, '@PathVariable'도 입력해줘야 함)
        //  3)만약에, '들어온 URL 요청으로 들어온 id값에 해당하는(대응되는) id값'이 'db에 없는 경우'에도 대비하여 코드 작성해줌
        //    (.orElse(null))
        //(2)'db'로부터 가져온 데이터를 'Article 타입'의 '변수 target'이라 지정하고 여기에 넣어줌
        //   즉, '변수 target'에는 'db로부터 가져온 데이터'가 입력된다. 즉, '변수 target'은 'db로부터 가져온 데이터'를 의미한다.
        Article target = articleRepository.findById(id).orElse(null);


        //2.'삭제 대상 엔티티'를 '삭제하기'
        if(target != null){ //'들어온 URL 요청에 해당하는 id값'이 'DB'에 있어서, 그 '해당하는 데이터'를 가져오는데 '성공'하면
                            // (='target'에 '데이터'가 입력되면)
            articleRepository.delete(target); //이제 'repository가 '들어온 URL 요청에 해당하는 id값'을 '삭제'시켜준다.


        //3.'삭제 완료 후', '게시판 글 목록 페이지'로 돌아오면, '게시판 글 목록 페이지'에 아래와 같은 '삭제 완료 메시지 출력'하기
        //'뷰 index(게시판 글 목록 페이지)'의 최상단에 'msg' 로직 작성
        //그리고, '클래스 RedirectAttributes의 자동저장 메소드 addFlashAttribute'를 활용하여, '1회성의 데이터'를 전달해줌
        //('1회성 데이터'이기에 '한 번 전달된 후'에는, 그 데이터는 사라져버림. 'addAttribute'와의 차이점임.)
        //https://web-obj.tistory.com/455 참고. ('리다이렉트'이기에 당연히 '모델'은 필요X)
        //이를 활용하기 위해서는, 어쨌든 '현재 메소드'에 'RidirectAttributes 객체'을 '변수'에 담아 '미리 선언'해줘야 하기 때문에
        //위에서 '현재 메소드'에 '매개변수 rttr(임의로 아무렇게나)'르 넣어줬음
            rttr.addFlashAttribute("msg", "Delete Completeeeeed!!!!");
        }


        //4.'삭제 후'에는, 기존 '게시판 글 목록 뷰 페이지'로 '리다이렉트'해주기
        //(1)return "articles/index";   와   (2)return "redirect:/articles/" ('리다이렉트'는 '모델' 필요 없음)
        //위 두 개는 결과적으로 보여주는 페이지는 같으나,
        //(1)은 '해당 뷰 페이지'를 보여주는 것이고,
        //(2)'리다이렉트'는 'URL 주소로 바로 보내주는 것'이다.
        //https://sassun.tistory.com/61  에 설명.
        //
        //(3) https://blog.naver.com/allkanet72/220964699929
        //a)'리다이렉트'가 발생하면, 원래 요청은 끊어지고, 새로운 HTTP의 GET요청이 시작.
        //때문에, '리다이렉트 실행 이전'의 '모델 객체 데이터'는 소멸된다. 따라서, '리다이렉트'로 모델을 전달하는 것은 의미가 없기에,
        //애초에 '해당 메소드의 매개변수'에 '모델 객체'를 '선언할 필요 없음)
        //b)그러나, '리다이렉트' 방식으로도 '데이터를 전달'하고 싶으면, 'HTTP GET 방식'의 특징을 이용하여,
        //'클래스(객체) RedirectAttributes'를 활용하기. 따라서, 'RedirectAttributes'를 사용해야 하니, '현재 메소드의 매개변수'에
        //이를 넣어주어 선언한다.
        //그리고,
        return "redirect:/articles";
    }
}

