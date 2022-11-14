package com.example.firstproject.ioc;

public abstract class Ingredient {

    private String name;

    public Ingredient (String name){ //'사용자 생성자' 정의함
        this.name = name;
    }

    public String getName(){
        return name;
    }


}
