package com.jojoldu.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //JAP Auditing 기능 활성화 - 자동 Entity 시간 기록을 위해
@SpringBootApplication  //얘가 있는 위치부터 설정을 읽어가기 때문에, 이 클래스는 항상 프로젝트의 최상단에 위치.
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        //SpringApplication.run으로 인해 내장 WAS를 실행. 내장 WAS는 서버에 톰캣을 설치할 필요가 없게되고,
        //스프링 부트로 만들어진 JAR 파일(실행 가능한 JAVA 패키징 파일)로 실행하면 된다.
        //내부 WAS > 외부 WAS (버전이 달라지면 외부 WAS는 서버마다 설정해야 됨. 그래서 대부분 WAS로 넘어옴)


    }
}
