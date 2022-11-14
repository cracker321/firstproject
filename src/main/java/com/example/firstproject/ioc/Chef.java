package com.example.firstproject.ioc;

import org.springframework.stereotype.Component;

@Component //이 '클래스 Chef'를 '클래스 TestChef'에서 사용하기(DI시키기)위해,
           // 이 '클래스 Chef'를 '스프링 IoC 컨테이너'에 '등록('클래스 Chef'를 '객체'로 생성 및 관리)'시키려면,
           //이 때 필요한 어노테이션이다!
public class Chef {


    //'쉐프(Chef)'는 '식재료 공장(IngredientFactory)'를 알고 있음
    private IngredientFactory ingredientFactory;

    //'쉐프(Chef)'가 '식재료 공장(IngredientFactory)'돠 '협업'하기 위한 DI
    public Chef(IngredientFactory ingredientFactory){ //'생성자 작성을 통한 DI'

        this.ingredientFactory = ingredientFactory;
    }


    public String cook(String menu) {

        //1.< 돈까스(요리) 재료(=돼지고기 Pork 객체) 준비 >
        //Pork pork = new Pork("한돈 등심"); //'한돈 등심이 재료'로 들어간 '돈까스 재료인 돼지고기(=Pork 객체)'를 생성함
       Ingredient ingredient = ingredientFactory.get(menu); //'쉐프(Chef)'가 돈까스건 스테이크건 '요리할 때(cook)',
                                                            //'식재료공장(ingredientFactory)'에 'menu'를 입력(주문)하면
                                                            //그것이 알아서 그에 맞는 재료(pork 또는 beef)를 리턴하도록
                                                            //'Ingredient 객체': 'Pork 객체'와 'Beef 객체'의 '부모 클래스 타입!'


        //2.< 돈까스(요리)를 만든 후, 돈까스(요리)를 리턴함 >
        //return pork.getName() + "으로 만든" + menu; //'클래스 Pork의 필드 name'을 'getter'를 통해 가져옴
        return ingredient.getName();
    }

}
