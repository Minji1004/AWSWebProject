package com.jojoldu.book.springboot.domain.posts;

import com.jojoldu.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//Entity 클래스에서는 절대 Setter 메소드를 만들지 않는다. 인스턴스 값들이 언제 어디서 변해야 하는 지 코드상 알 기 힘들어짐
//Entity에 값 세팅 - 생성자를 통해 최종값을 채운 후 DB에 삽입. 값 변경이 필요한 경우 해당 이벤트에 맞는 public 메소드를 호출하여 변경
//값 변경 시 명확히 목적과 의도를 나타낼 수 있는 메소드를 추가
@Getter
@NoArgsConstructor
@Entity //JPA의 Annotation. 실제 DB의 테이블과 매칭될 클래스임을 알림(Entity 클래스)
public class Posts extends BaseTimeEntity {  //시간 자동으로 등록되도록 BaseTimeEntity 상속

    @Id //해당 테이블의 PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK 생성 규칙. IDENTITY를 써야 Auto_increment됨.
    private Long id;  //PK는 Long 형식의 Auto_increment를 추천

    //테이블의 컬럼. 굳이 선언 안해도 필드는 모두 컬럼이 됨.
    //기본값 외에 추가로 필요한 욥션이 있을 때 사용
    @Column(length = 500, nullable = false)  //varchar 255가 기본.
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false) //varchar에서 Text 타입으로 변경.
    private String content;

    //Column없어도 테이블의 컬럼이 됨.
    private String author;

    //생성자 대신 Builder 클래스를 사용한다. Builder 클래스도 생성자처럼 생성 시점에 값을 채워준다.
    //Builder를 사용하게 되면 어느 필드에 어떤 값을 채워야할 지 명확하게 인지할 수 있다. 생성자는 명확히 인지 못해 실수하기 좋다.
    //Builder 패턴을 적극 사용함.
    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

}
