package com.spring.test.web.dto;

import com.spring.test.domain.posts.posts;
import lombok.*;

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
