package com.example.firstproject.api;


import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//[스프링 부트 입문 19] HTTP와 RestController.

@RestController //'RestAPI용 컨트롤러'. 보통 JSON형식의 데이터를 반환함
public class ArticleApiController {

        @Autowired //외부에서 아래 필드를 DI 해줘야 하기 때문에 '@Autowired'를 쓰는 것.
        private ArticleRepository articleRepository;

    //GET
    @GetMapping("/api/articles")
    public List<Article> index(){


        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public List<Article> index(){


        return articleRepository.findById(id).orElse(null); //19강 10:00~
    }



    //POST


    //PATCH


    //DELETE
}
