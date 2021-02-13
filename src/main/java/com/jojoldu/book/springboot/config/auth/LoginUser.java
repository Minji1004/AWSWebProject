package com.jojoldu.book.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//이 어노테이션이 생성될 수 있는 위치 지정. Parameter라 메소드의 파라미터로 선언된 객체에서만 사용 가능. Type은 클래스 선언문에 쓸수 있음
@Target(ElementType.PARAMETER)  
@Retention(RetentionPolicy.RUNTIME)
//@interface 이 파일을 어노테이션 클래스로 지정. LoginUser라는 이름을 가진 어노테이션이 생성됨
public @interface LoginUser {
}
