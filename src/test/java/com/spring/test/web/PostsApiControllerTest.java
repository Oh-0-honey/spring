package com.spring.test.web;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before//테스트 시작 전에 SpringBootTest -> MockMvcTest를 위해 MockMvc 인스턴스 생성
    public void setup(){
        mvc= MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "USER")//모의 사용자
    public void Posts_등록() throws Exception{
        String title= "title";
        String content="content";
        PostsSaveRequestDto reqDto= PostsSaveRequestDto.builder()
                                    .title(title).content(content).author("author")
                                    .build();

        String url = "http://localhost:"+port+"/api/v1/posts";

        /*
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, reqDto,Long.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        */

        mvc.perform(post(url)//생성된 MockMvc를 통해 API 테스트
                .contentType(MediaType.APPLICATION_JSON_UTF8)//본문을 문자열 표현을 위해 json으로 변환
                .content(new ObjectMapper().writeValueAsString(reqDto)))
                .andExpect(status().isOk());



        List<posts> all=postRep.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }


    @Test
    @WithMockUser(roles = "USER")//모의 사용자
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


        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<posts> all=postRep.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

    }

}
