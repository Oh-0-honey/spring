package com.spring.test.service.posts;

import com.spring.test.domain.posts.PostRepository;
import com.spring.test.domain.posts.posts;
import com.spring.test.web.dto.PostsListResponseDto;
import com.spring.test.web.dto.PostsResponseDto;
import com.spring.test.web.dto.PostsSaveRequestDto;
import com.spring.test.web.dto.PostsUpdateRequestDto;
import  lombok.RequiredArgsConstructor;
import  org.springframework.stereotype.Service;
import  org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostRepository postRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){//쿼리문 없이 Entity 객체의 값만 변경하면 된다. 이 개념을 더티체킹이라고 한다.
        posts posts=postRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("해당 게시물이 없습니다. id="+id));
        System.out.println(requestDto.getTitle()+"!!!!!!!!!!!!!!!!!!");
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id){
        posts entity=postRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("해당 게시물이 없습니다. id="+id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)//readOnly를 통한 조회속도 개선
    public List<PostsListResponseDto> findAllDesc(){
        return postRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id){
        posts posts=postRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("해당 게시물이 없습니다. id="+id));
        postRepository.delete(posts);

    }
}
