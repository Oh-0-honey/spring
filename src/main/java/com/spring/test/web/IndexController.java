package com.spring.test.web;

import com.spring.test.config.auth.LoginUser;
import com.spring.test.config.auth.dto.SessionUser;
import com.spring.test.service.posts.PostsService;
import com.spring.test.web.dto.PostsResponseDto;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;

//페이지에 관련된 컨트롤러는 모두 IndexController를 사용
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){//httpSession.getAttribute를 어노테이션으로 개선. 어느 컨트롤러도 @LoginUser로 세션 정보를 가져올 수 있다.
        model.addAttribute("posts", postsService.findAllDesc());


        if(user!=null) model.addAttribute("userName", user.getName());//세션에 저장된 값이 있을 때만 model에 userName으로 등록
        // 값이 없으면 model엔 아무런 값이 없는 상태이니 로그인 버튼이 보이게됨

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";// /posts/save를 호출하면 posts-save.mustache를 호출
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);
        return "posts-update";
    }

}
