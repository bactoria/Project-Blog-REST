package com.bactoria.toy1.web;


import com.bactoria.toy1.domain.posts.Posts;
import com.bactoria.toy1.domain.posts.PostsRepository;
import com.bactoria.toy1.domain.posts.PostsSaveRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor // 이게 @Autowired 대신 사용가능
public class WebRestController {

    private PostsRepository postsRepository;

    @GetMapping("/hello")
    public String hello() {
        return "HELLO WORLD !";
    }

    @PostMapping("/posts")
    public void savePosts(@RequestBody PostsSaveRequestDto dto) {
        postsRepository.save(dto.toEntity());
    }

    @GetMapping("/posts")
    public List<Posts> resPosts() {
        return postsRepository.findAll();
    }


}
