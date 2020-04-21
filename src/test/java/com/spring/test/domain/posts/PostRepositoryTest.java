package com.spring.test.domain.posts;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {

    @Autowired//bean을 주입받음
    PostRepository postRepository;

    @After//이하 메소드는 단위 테스트가 끝날 때 마다 수행됨
    public void cleanup(){//테스트간 데이터 침범을 막기 위해 사용
        postRepository.deleteAll();;
    }

    @Test
    public void 게시글저장_불러오기(){
        String title="테스트 글";
        String content = "테스트 내용";
        postRepository.save(posts.builder()//.save는 post 테이블에 대해 insert/update 쿼리를 실행. id값 유무에 따라 있으면 update, 없으면 insert
                            .title(title)
                            .content(content)
                            .author("oyh@naver.com")
                            .build());

        List<posts> postsList=postRepository.findAll();//테이블 post에 모든 데이터 조회

        posts post= postsList.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록(){
        LocalDateTime now=LocalDateTime.of(2020,4,19,0,0,0);
        postRepository.save(posts.builder()
                            .title("title")
                            .content("content")
                            .author("author")
                            .build());

        List<posts> postsList = postRepository.findAll();

        posts posts = postsList.get(0);

        System.out.println(">>>>>>>>>>>>>>>>>>>> createDate = "+posts.getCreatedDate()+", modifiedDate = "+posts.getModifiedDate());;

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }

}
