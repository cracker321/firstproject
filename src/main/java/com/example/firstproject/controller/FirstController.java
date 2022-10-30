package com.example.firstproject.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //'요청 URL' 자체는 '컨트롤러'가 받는다
public class FirstController {

    @GetMapping("/hi") //'컨트롤러'에서 받은 URL요청의 내용이 'hi'라면, 아래 절차 거친 후, '뷰 greetings'로 찾아감
    public String niceToMeetYou(Model model){ //'모델 객체'를 이용하여 'greetings.mustache의 변수 uesername'에
                                              //데이터 전달해주기 위해, '메소드 niceToMeetYou'의 '매개변수'로 '모델 객체' 삽입
        model.addAttribute("username", "yujong");
        //'변수 username'에 주입해줄 정보(값) 'yujong'을 '모델 객체'에 실어넣어서 보내줌

        return "greetings";
        //'뷰 greetings'를 찾아감
        //'templates/greetings.mustache'를 알아서 찾은 후, 브라우저로 전송해주는 역할
        //화면에 실제 표시되는 것 --> '뷰 greetings'내부의 로직이 화면에 표시됨
    
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){

        model.addAttribute("nickname", "yujong");
        return "goodbye";
    }

    @GetMapping("/getbootstrap")
    public String getBootStrap(Model model){
        model.addAttribute("username", "yujong");
        return "getbootstrap";
    }

}
