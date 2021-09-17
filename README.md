# 스프링 부트와 AWS로 혼자 구현하는 웹 서비스

이동욱 저자의 스프링 부트와 AWS로 혼자 구현하는 웹 서비스 책을 따라 구현함   
<br>
<br>
## JAVA 폴더 내 Directory 구조와 설명

Application 클래스: Application을 실행시키는 메인 클래스
- @SpringBootApplication : 스프링부트의 자동설정. 스프링 Bean 읽기와 생성을 모두 자동으로 설정  
</br>
Web 폴더 : URL 매핑 Controller와 View에 쓰이는 DTO 관리 폴더

     Controller 폴더 : Request의 URL에 따라 어떤 서비스 기능을 쓸 지 매핑해줌. 
     - @RestController: JSON을 반환하는 Controller를 만들어줌.
    
    
     DTO 폴더 : 뷰 템플릿에 사용할 객체. 
     - @RestController: JSON을 반환하는 Controller를 만들어줌.Repository Layer에서 결과를 넘겨준 객체
                        Entity와 달리 View를 위한 클래스라 자주 변경 가능
                        Entity는 도메인 패키지에서 데이터베이스와 맞닿은 핵심 클래스이므로 
                        Request/Response 클래스로 절대 사용하면 안된다.
                        
                        
     - @Getter: Getter메소드를 자동 생성. 롬복 설치 후 사용 가능하다.

<br>    
Service 폴더: Controller에서 해당 Request에 맞는 Service의 메소드를 호출한다.

     Service명 폴더 : 
     
         Service 클래스: 비즈니스 처리 로직은 Service가 아니라 Domain인 Entity에서 한다. 
                         Service는 트랜잭션 Domain간 순서 보장의 역할만 한다.   


<br>
Domain 폴더 : 데이터베이스와 맞닿은 Domain 영역(Entiry, Repository)를 관리하는 폴더 

     Entity 폴더 : Entity 클래스와 Entity의 Repository 클래스는 기본적으로 함께 위치해야 한다. 
     
         Entity 클래스: 도메인 패키지에서 데이터베이스와 맞닿은 핵심 클래스
                       절대 Setter 메소드를 만들지 않는다. 
                       명확히 그 목적과 의도를 나타낼 수 있는 메소드로 추가 Ex) setUserName(X), updateUserName(O) 
                       
           - @NoArgsConstructor: 기본 생성자 자동 추가
           - @Builder: 해당 클래스의 빌더 패턴 클래스를 생성
      
      
         Repository 클래스: 데이터 저장소에 접근하는 영역. 기존의 DAO로 이해하면 된다.       


