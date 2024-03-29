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
                                                
           - @RequiredArgsConstructor: final이 선언된 모든 필드를 인자값으로 하는 생성자 대신 생성.
                                       롬복 설치 후 사용 가능
                                       생성자 주입 방식이 아닌 Bean 주입 방식인 @Autowired는 추천하지 않는다.
        

<br>
Domain 폴더 : 데이터베이스와 맞닿은 Domain 영역(Entiry, Repository)를 관리하는 폴더 

     Entity 폴더 : Entity 클래스와 Entity의 Repository 클래스는 기본적으로 함께 위치해야 한다. 
     
         Entity 클래스: 도메인 패키지에서 데이터베이스와 맞닿은 핵심 클래스
                       절대 Setter 메소드를 만들지 않는다. 
                       명확히 그 목적과 의도를 나타낼 수 있는 메소드로 추가 Ex) setUserName(X), updateUserName(O) 
                       
           - @NoArgsConstructor: 기본 생성자 자동 추가
           - @Builder: 해당 클래스의 빌더 패턴 클래스를 생성
      
      
         Repository 클래스: 데이터 저장소에 접근하는 영역. 기존의 DAO로 이해하면 된다.     
         
<br>
Config 폴더: 스프링 설정 관리하는 폴더


     auth 폴더 : Spring Security 설정 관련 클래스를 관리하는 폴더
     
         SecurityConfig 클래스: URL 별 접속 허용, 유저 별 권한 등 설정
                               CustomOAuth2UserService 써서 로그인한 유저 정보 가져옴
                           
         CustomOAuth2UserService 클래스: Request에서 User 정보 받은 후, Session에 SessionUser DTO로 유저 정보 세팅
     
         Config.auth.dto 폴더: 
             OAuthAttributes 클래스: 유저의 인증정보를 담음
             SessionUser 클래스: 유저의 기본정보 담음.
                             Entity인 User 객체를 놔두고 따로 Session User를 만든 이유는 직렬화 때문이다.
                             직렬화 대상에 자식 Class까지 포함되면 성능 상에 이슈가 생길 수 있다.
                             Entity인 User는 직렬화를 구현하지 않고, 직렬화 기능을 가진 SessionUser DTO를 추가로 만든다.
    
         LoginUser 클래스: Controller 클래스에서 세션에서 유저 정보를 가져오는 부분을 중복적으로 많이 사용하므로, 어노테이션으로 만듦
                          @LoginUser라는 어노테이션 생성
                            
         LoginUserArgumentResolver 클래스: @LoginUser 어노테이션 기능 구현
                                          HandlerMethodArgumentResolver 구현체.
                                          HandelrMethodArgumentResolver는 조건에 맞는 메소드가 있다면
                                          구현체가 지정한 값으로 해당 메소드의 파라미터를 넘길 수 있다.      
                                          ex) @GetMapping("/")
                                              public String index(Model model, @LoginUser SessionUser user){...}
                                          HandlerMethodArgumentResolver의 구현체는 Spring에서 인식할 수 있게 
                                          반드시 WebMvcConfigurer에 추가해야한다.
                           
     WebConfig 클래스: WebMvcConfigurer의 구현체. LoginUserArgumentResolver를 여기서 등록함. 
     
     JPAConfig 클래스: Application 클래스에 있던 @EnableJPAAuditing을 JPACongif클래스로 옮김. 
                      SpringSecurity 적용 후, Test할 때 Application에 @EnableJPAAuditing이 있으면 @Entity 클래스가 최소 하나 필요한데
                      @WebMvcTest는 없다보니 에러가 난다.
                      따라서 별도로 JPAConfig 클래스를 생성해 @EnableJPAAuditing 어노테이션을 추가한다.

<br>
<br>




## AWS 연동 및 CI/CD, 무중단 배포 구현                                     

![image](https://user-images.githubusercontent.com/41352652/133917905-a672e2ff-7c6c-46f4-847e-0d7d26ff3f80.png)

       
.travis.yml
- Travis CI 설정 파일.   
- Build할 파일 Zip으로 만들고, AWS S3로 파일 전송. CodeDeploy 실행  
- 메일, Slack 등 Notifiy 설정  


appspec.yml
- AWS CodeDeploy 설정 파일
- 배포 쉘 스크립트 실행 (ex., stops.sh, start.sh)


application.properties
- DB설정
- 포트 설정
- OAuth2의 구글 등록
- 다른 properties 포함 ex) spring.profiles.include=oauth


application-oauth.properties
- 구글, 네이버 로그인 연동 설정 파일
- 민감한 정보라 Git에 안올리고, 로컬과 AWS 서버에만 올림
- AWS의 deploy.sh에 따로 oauth관련 properites 포함하도록 작성. 


.gitgnore
- git에 올리지 않을 파일들 작성




