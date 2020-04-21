package com.spring.test.web;

import com.spring.test.domain.posts.PostRepository;
import com.spring.test.domain.posts.posts;
import com.spring.test.web.dto.PostsSaveRequestDto;
import com.spring.test.web.dto.PostsUpdateRequestDto;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//JPA기능까지 한번에 테스트할 때는 WebMvcTest 대신 이것을 사용
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRep;

    @After
    public void tearDown() throws Exception{
        postRep.deleteAll();
    }

    @Test
    public void Posts_등록() throws Exception{
        String title= "title";
        String content="content";
        PostsSaveRequestDto reqDto= PostsSaveRequestDto.builder()
                                    .title(title).content(content).author("author")
                                    .build();

        String url = "http://localhost:"+port+"/api/v1/posts";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, reqDto,Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<posts> all=postRep.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }


    @Test
    public void Posts_수정() throws Exception{
        posts savedPosts= postRep.save(posts.builder()
                                        .title("title")
                                        .content("content")
                                        .author("author")
                                        .build());
        Long updateId=savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto=PostsUpdateRequestDto.builder()
                                            .title(expectedTitle).content(expectedContent)
                                            .build();
        String url="http://localhost:"+port+"api/v1/posts/"+updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity=new HttpEntity<>(requestDto);

        ResponseEntity<Long> responseEntity=restTemplate.exchange(url, HttpMethod.PUT,requestEntity,Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<posts> all=postRep.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

    }

}
