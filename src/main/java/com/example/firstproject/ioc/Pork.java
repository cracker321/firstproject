package com.example.firstproject.ioc;
public class Pork extends Ingredient{


    public Pork(String name){ //일단 여기서 '자식 클래스 Pork'는 기본적인 '부모 클래스 Ingredient 생성자의 매개변수 name'만 일단
                              //가지고 왔음. 원한다면, 여기서 '자식 클래스 Pork'만의 고유의 매개변수를 더 추가해줘도 됨
                              //물론, 그럴 경우, 당연히 '자식 클래스 Pork 내부에서 해당하는 필드'를 선언해주고, 그 중에서 선택해서
                              //가져와야 하는 것임.
        super(name); //이클립스 super() 공부한 부분 참고.
    }



    /*
    private String name; //@Getter, @Setter 는 '필드'에 대해 하는 것이다!
    //그리고, @Getter, @Setter 하는 '필드'들은 'private'으로 하기!

    public Pork(String name){ //'사용자 정의 생성자의 매개변수'는 '해당 필드 중에서 일부 또는 전부를 선택해서 넣어준다!'
        this.name = name;
    }

    public String getName() {
        return name; //사실 이 'getter'에는 'return name' 대신 'return "유종쓰"' 가 들어가도 된다! 상관없음!
    }
    */
}