package com.spring.test.service.posts;

import com.spring.test.domain.posts.PostRepository;
import com.spring.test.domain.posts.posts;
import com.spring.test.web.dto.PostsResponseDto;
import com.spring.test.web.dto.PostsSaveRequestDto;
import com.spring.test.web.dto.PostsUpdateRequestDto;
import  lombok.RequiredArgsConstructor;
import  org.springframework.stereotype.Service;
import  org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostRepository postRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        posts posts=postRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("해당 게시물이 없습니다. id="+id));
        return id;
    }

    public PostsResponseDto findById(Long id){
        posts entity=postRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("해당 게시물이 없습니다. id="+id));
        return new PostsResponseDto(entity);
    }
}
