package com.spring.test.web.dto;

import com.spring.test.domain.posts.posts;
import lombok.*;

/*
    이 Dto 클래스는 Entity 클래스와 매우 유사하다.
    하지만 Entity 클래스는 DB와 맞닿은 핵심이므로 Request/Response 클래스로 사용해서는 안된다.
    Entity는 변경되면 여러 클래스에 영향을 주지만
    Request/Response Dto는 View를 위한 클래스라 정말 자주 변경이 필요하다.
    따라서 View Layer와 DB Layer는 구분을 해야한다.
 */
@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author){
        this.title=title;
        this.content=content;
        this.author=author;
    }

    public posts toEntity(){
        return posts.builder().title(title).content(content).author(author).build();
    }
}
