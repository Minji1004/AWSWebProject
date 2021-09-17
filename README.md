# 스프링 부트와 AWS로 혼자 구현하는 웹 서비스

이동욱 저자의 스프링 부트와 AWS로 혼자 구현하는 웹 서비스 책을 따라 구현함

## JAVA 폴더 내 Directory 구조와 설명

Application 클래스: Application을 실행시키는 메인 클래스
- @SpringBootApplication : 스프링부트의 자동설정. 스프링 Bean 읽기와 생성을 모두 자동으로 설정

Web 폴더 : URL 매핑 Controller와 View에 쓰이는 DTO 관리 폴더

     Controller 폴더 : Request의 URL에 따라 어떤 서비스 기능을 쓸 지 매핑해줌. 
     - @RestController: JSON을 반환하는 Controller를 만들어줌.
    
     DTO 폴더 : 뷰 템플릿에 사용할 객체. 
     - @RestController: JSON을 반환하는 Controller를 만들어줌.Repository Layer에서 결과를 넘겨준 객체
                        Entity와 달리 View를 위한 클래스라 자주 변경 가능
                        Entity는 도메인 패키지에서 데이터베이스와 맞닿은 핵심 클래스이므로 Request/Response 클래스로 절대 사용하면 안된다.
                        
                        
     - @Getter: Getter메소드를 자동 생성. 롬복 설치 후 사용 가능하다.
                Setter메소드 사용을 지양한다. 
                Setter는 특별한 목적을 나타낼 수 있게끔 메소드명에 나타낸다. Ex) setUserName(X), updateUserName(O)

Domain 폴더
