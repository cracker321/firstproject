package com.example.firstproject.ioc;


import org.springframework.stereotype.Component;

@Component //이 '클래스 IngredientFactory'를 '클래스 TestChef'에 DI시키기 위해, 이 '클래스 IngredientFactory'를
           //'스프링 IoC 컨테이너'에 '등록'시키려면, 이 때 필요한 어노테이션이다!
public class IngredientFactory {

    public Ingredient get(String menu) {
        switch (menu) { //'menu'가 들어올 때,
            case "돈까스": //만약, 'menu'로 '돈까스'가 들어오면,
                return new Pork("한돈 등심"); //'한돈 등심'이 들어간 'Pork 객체(=재료)'를 리턴해라
            case "스테이크": //만약, 'menu'로 '스테이크'가 들어오면
                return new Beef("한우 꽃등심"); //'한우 꽃등심'이 들어간 'Beef 객체(=재료)'를 리턴해라
            case "크리스피 치킨": //만약, 'menu'로 '크리스피 치킨'이 들어오면
                return new Chicken("국내산 10호 닭"); //'국내산 10호 닭'이 들어간 'Chicken 객체(=재료)'를 리턴해라
            default: //만약, '올바른 요청이 들어온 것이 아니라면',
                return null;  //'null'을 리턴해라
        }
    }
}
