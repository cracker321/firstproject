package com.example.firstproject.ioc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


//'객체 간 의존성이 높은 코드' --> '요구사항 변경에 취약!'
//따라서, '외부에서 값을 삽입받는 DI 방식'을 최대한 활용하도록 유연한 코드 작성(리팩토링)을 하여야 한다!
//이게 가장 '객체 지향적 OOP' 작성법이다!

@SpringBootTest
class ChefTest { //29강 02:35~

    @Autowired
    IngredientFactory ingredientFactory; //'스프링 IoC컨테이너'가 'IngredientFactory 객체'를 여기 '클래스 ChefTest'에
                                         //DI 해주도록 만들게 한다!

    @Autowired
    Chef chef;//'스프링 IoC컨테이너'가 'Chef 객체'를 여기 '클래스 ChefTest'에 DI 해주도록 만들게 한다!

//=====================================================================================================================

    @Test
    void 돈가스_요리하기(){


        //5.'쉐프(Chef)'와 '식재료(Pork)' 사이에, '식재료 공장(IngredientFactory)'을 두어 '쉐프 <--> 식재료' 상호 간 의존성을 낮춘다!
        //29강 08:10~
        //(요구사항 변경에 유연하도록 코드를 개선!)
        //IngredientFactory ingredientFactory = new IngredientFactory();
        //Chef chef = new Chef(ingredientFactory); //'식재료 공장'을 '쉐프'에 넣어줌.


        //1.< 준비 >
        //Chef chef = new Chef(); //'Chef 객체'를 새롭게 만듦
        String menu = "돈까스"; //'메뉴(menu)'는 '문자열'로 '돈까스'라고 설정해줌


        //2.< 예상(되는 데이터 출력값) >
        String expected = "한돈 등심으로 만든 돈까스"; //사용자는, '쉐프(chef)'가 '어떤 메뉴(menu)'를 바탕으로 만든 '요리(cook)'가,
                                                   //'한돈 등심으로 만든 돈까스' 였으면 좋겠어함!


        //3.< 실제 수행 식(CommentRepository 파일에 내가 기 작성한 구문) >
        String food = chef.cook(menu); //'쉐프(chef)'가 '어떤 메뉴(menu)'를 바탕으로 '요리(cook)'를 했으면 좋겠다!
                                       //그리고, 그 '요리'는 'food'이다!


        //4.< 비교 검증 >
        assertEquals(expected, food); //'변수 expected'와 '변수 food'를 비교 검증
        System.out.println(food);

    }

//=====================================================================================================================




//=====================================================================================================================

    @Test
    void 스테이크_요리하기(){

        //5.'쉐프(Chef)'와 '식재료(Beef)' 사이에, '식재료 공장(IngredientFactory)'을 두어 '쉐프 <--> 식재료' 상호 간 의존성을 낮춘다!
        //29강 08:10~
        //(요구사항 변경에 유연하도록 코드를 개선!)
        //IngredientFactory ingredientFactory = new IngredientFactory();
        //Chef chef = new Chef(ingredientFactory);

        //1.< 준비 >
        //Chef chef = new Chef();
        String menu = "스테이크";


        //2.< 예상(되는 데이터 출력값) >
        String expected = "한우 꽃등심으로 만든 스테이크";


        //3.< 실제 수행 식(CommentRepository 파일에 내가 기 작성한 구문) >
        String food = chef.cook(menu);


        //4.< 비교 검증 >
        assertEquals(expected, food);
        System.out.println(food);

    }

//=====================================================================================================================

    @Test
    void 크리스피치킨_요리하기(){


        //5.'쉐프(Chef)'와 '식재료(Chicken)' 사이에, '식재료 공장(IngredientFactory)'을 두어 '쉐프 <--> 식재료' 상호 간 의존성을 낮춘다!
        //29강 08:10~
        //(요구사항 변경에 유연하도록 코드를 개선!)
        //IngredientFactory ingredientFactory = new IngredientFactory(); //먼저, '식재료공장'을 만들고,
        //Chef chef = new Chef(ingredientFactory); //'쉐프(Chef)'에게 '식재료공장'을 주입 DI하여, 빠른 재료 조달을 가능케 하고


        //1.< 준비 >
        //Chef chef = new Chef();
        String menu = "크리스피 치킨";


        //2.< 예상(되는 데이터 출력값) >
        String food = chef.cook(menu);


        //3.< 실제 수행 식(CommentRepository 파일에 내가 기 작성한 구문) >
        String expected = "국내산 10호 닭으로 만든 크리스피 치킨";


        //4.< 비교 검증 >
        assertEquals(expected, food);
        System.out.println(food);
    }

//=====================================================================================================================





}
