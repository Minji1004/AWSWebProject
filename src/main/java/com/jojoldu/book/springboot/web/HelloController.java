package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController //JSON을 반환하는 컨트롤러로 만들어준다. 예전에 @ResponseBody를 각 메소드마다 선언했던 것을 한번에 사용할 수 있게 해줌
public class HelloController {

    @GetMapping("/hello") //GeT 요청을 받을 수 있는 API를 만들어줌.
    public String hello() {
        return "hello";
    }

    @PostMapping("/helloPost")
    public String helloPost(){return "helloPost";}
    
    @GetMapping("/hello/dto") 
    //RequestParam 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount){
        return new HelloResponseDto(name, amount);
    }
    
}
