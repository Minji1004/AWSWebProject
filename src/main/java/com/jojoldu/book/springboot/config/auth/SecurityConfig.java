package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity  //SpringSecurity설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http
                //h2-console 화면을 사용하기 위해 해당 옵션들을 disable
                .csrf().disable()
                .headers().frameOptions().disable()

                .and()
                    //authorizeRequests - URL별 권한 관리 설정하는 옵션의 시작점.
                    //authorizeRequests가 선언되어야먄 antMatchers 옵션을 사용할 수 있다.
                    .authorizeRequests()
                      //antMaters 권한 관리 대상 설정. URL, HTTP 메소드 별로 관리 가능
                       .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**","/profile/**").permitAll() //전체 열람 권한
                       .antMatchers("/api/v1/**").hasRole(Role.USER.name()) //User 권한 가진 사람
                      //anyRequest - 설정된 값들 이외 나머지 URL
                      //authenticated 나머지 URL들은 모두 인증된 사용자들에게만 허용
                       .anyRequest().authenticated() 
                .and()
                    .logout()
                        .logoutSuccessUrl("/") //로그아웃 성공시 "/"로 이동
                .and()
                    .oauth2Login()
                        //성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService); //로그인 성공 시 후속 조치를 실행할 UserService 인터페이스의 구현체 등록
    }


}
