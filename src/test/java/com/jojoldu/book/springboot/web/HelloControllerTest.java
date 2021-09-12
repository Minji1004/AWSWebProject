package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
//WebMVCTest는 CustomOAuth2UserService를 스캔하지 않는다!!
//스캔 대상에서 SecurityConfig를 제거.
@WebMvcTest(controllers = HelloController.class,
            excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
            }
)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @WithMockUser(roles="USER")
    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));

    }

    /*
    @WithMockUser(roles="USER")
    @Test
    public void helloPost가_리턴된다() throws Exception{
        String helloPost = "helloPost";

        mvc.perform(post("/helloPost"))
                .andExpect(status().isOk())
                .andExpect(content().string(helloPost));
    }
    */

    @WithMockUser(roles="USER")
    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name)  //API 요청 파라미터
                        .param("amount", String.valueOf(amount)) //값은 String만 허용
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(name))) //Json응답값 필드별로 검증
                .andExpect(jsonPath("$.amount",is(amount))); //$를 기준으로 필드명 명시

    }
}
