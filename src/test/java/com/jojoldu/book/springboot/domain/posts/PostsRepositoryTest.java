package com.jojoldu.book.springboot.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest //보통 @SpringBootTest 사용할 경우 H2 데이터베이스를 자동으로 실행해준다.
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    //@After
    // Junit에서 단위테스트가 끝날때마다 수행되는 메소드를 지정.
    // 배포 전 전체 테스트를 수행할 때 테스트간 데이터 침범을 막기 위해 사용
    @AfterEach
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        //save() insert, update 실행. ID있으면 update, 없으면 insert.
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("AWS Study")
                .build()
                );

        //when
        List<Posts> postsList = postsRepository.findAll(); //findAll은 모든 데이터를 조회

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록(){
        //given
        LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build()
        );

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>> createDate="+posts.getCreatedDate()+", modifedDate="+posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
