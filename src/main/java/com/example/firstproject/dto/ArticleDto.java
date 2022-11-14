package com.example.firstproject.dto;


import com.example.firstproject.entity.Article;
import lombok.*;

//'사용자'가 전달한 '데이터'를 'dto 형식'으로 'controller'가 받는 것임
//즉, 사용자가 전달한 데이터가 'dto'에 담겨지는 것임. 즉, 'dto'는 '사용자가 전달한 데이터 덩어리' 그 자체임.

//'dto 객체'는 'form 데이터'를 받아서 '컨트롤러'에 전달해 줄 '그릇'
//'form 태그'에서 '3개의 데이터'를 던졌으니, 'dto 객체'에도 '3개의 변수'가 있어야 함
@AllArgsConstructor //'롬복'
@NoArgsConstructor //'롬복'
@ToString //'롬복'
@Getter //'롬복'
@Setter //'롬복'
public class ArticleDto {

    private Long id;
    private String title;
    private String content;


    //< '롬복'을 통해 저~ 위에 '@AllArgsConstructor'를 붙여주면, 아래 '생성자 만드는 과정'은 없애줄 수 있음 >
    /*
    public ArticleDto(String title, String content) { //'생성한 2개의 변수'에 대한 '생성자'를 만들어줌. 마우스 우클릭 -> Generator
        this.title = title;
        this.content = content;
    }
     */


    //< '롬복'을 통해 저~ 위에 '@ToString'을 붙여주면, 아래 '메소드 toString를 만드는 과정'은 없애줄 수 있음 >
    /*
    @Override
    public String toString() { //'form 태그'로부터 데이터를 잘 받았는지 여부를 확인하기 위해, '메소드 toString'도 생성해줌. 마우스 우클릭 -> Generator
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
     */

    
    //'Article'은 '클래스 Entity'를 의미함
    //'Article'은 '객체'이기 때문에, '해당 생성자'를 호출해야 함.
    //'entity 객체'의 '해당 생성자'는 매개변수로 (Long id, String title, String content)를 받고 있기 때문에,
    //아래에도 매개변수에 그것을 동일하게 넣어줌
    public Article toEntity() {

        return new Article(id, title, content);
        //'메소드 toEntity'가 'Article 타입'이기 때문에, 'Article'을 반환해줌
    }


}
