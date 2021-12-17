package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.service.posts.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//AutoWired 대신 롬복의 @RequiredArgsConstructor가 final이 선언된 모든 필드를 인자값으로 하는 생성자를 생성.
//Autowired 대신 생성자로 Bean객체(ex. PostsService 를 받아온다.)
@RequiredArgsConstructor
//@RestController: JSON을 반환하는 컨트롤러로 만들어준다.
// //예전에 @ResponseBody를 각 메소드마다 선언했던 것을 한번에 사용할 수 있게 해줌
@RestController
public class PostsApiController {

    private final PostsService postsService;

    //@RequestBody는 Request의 Data를 JSON 형식으로 받는다.
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id){
        postsService.delete(id);
        return id;
    }
}
