package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.service.posts.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    @Autowired
    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){

        //Model: 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다. (서버 템플릿 엔진: JSP, mustache 등)
        model.addAttribute("posts",postsService.findAllDesc());

        return "index"; //문자열을 반환할 때 앞의 경로와 뒤의 파일 확장자는 자동으로 지정
        //앞의 경로는 기본인 src/main/resources/templates, 뒤의 확장자는 mustache
    }

    @GetMapping("/posts/save")
    public String postSave(){

        return "posts-save";
    }


    @GetMapping("/posts/update/{id}")
    public String index(@PathVariable Long id, Model model){

        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }


}
