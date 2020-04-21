package com.spring.test.domain.posts;

import com.spring.test.domain.BaseTimeEntity;
import lombok.*;
import javax.persistence.*;


/*
    글을 쓰는 post 기능의 Entity 클래스
    Entity class에는 절대 Setter를 만들지 않는다.
 */
@Getter
@NoArgsConstructor
@Entity
public class posts extends BaseTimeEntity {

    @Id//PK임을 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY)//PK 생성 규칙
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private  String content;

    private String author;

    @Builder
    public posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title=title;
        this.content=content;
    }

}
