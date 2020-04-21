package com.spring.test.web;
import com.spring.test.service.posts.PostsService;
import com.spring.test.web.dto.PostsResponseDto;
import com.spring.test.web.dto.PostsSaveRequestDto;
import com.spring.test.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor//final 생성자 / @Autowired 대신 생성자로 Bean 객체를 받는 것이 권장된다?
@RestController//json 반환 가능
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")//생성. id가 없음
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return  postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")//수정
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return  postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")//조회
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id){
        postsService.delete(id);
        return id;
    }

}
