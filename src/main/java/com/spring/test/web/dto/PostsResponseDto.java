package com.spring.test.web.dto;

import com.spring.test.domain.posts.posts;
import lombok.Getter;

/*
    Entity의 필드 중 일부를 사용하므로 생성자로 Entity를 받아 필드에 값을 넣는다.
    (그럼 모든 값을 쓸 때는??)
 */
@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(posts entity){
        this.id=entity.getId();
        this.title=entity.getTitle();
        this.content=entity.getContent();
        this.author=entity.getAuthor();

    }
}
