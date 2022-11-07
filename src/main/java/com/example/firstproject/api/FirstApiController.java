package com.example.firstproject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//'@Controller'는 '뷰 템플릿 페이지'를 반환하지만, 
//'@RestController'는 'REST API용 컨트롤러'이므로, 'return 뒤에 입력하는 텍스트' 또는 'JSON'을 반환해줌.
public class FirstApiController {

    @GetMapping("/api/hello")
    public String hello(){

        return "hello world!"; //여기서 저 url 입력하면, 화면에 'hello world'를 보여줌.
    }
}
