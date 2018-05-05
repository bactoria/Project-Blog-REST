package com.bactoria.toy1.web;

import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.category.CategoryRepository;
import com.bactoria.toy1.domain.category.CategorySaveRequestDto;
import com.bactoria.toy1.domain.post.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor // 이게 @Autowired 대신 사용가능
public class WebRestController {

    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    private PostService postService;

    @GetMapping("/hello")
    public String hello() {
        return "HELLO WORLD !";
    }

    //글 추가
    @PostMapping("/api/post")
    public void savePost(@RequestBody PostSaveRequestDto dto) {
        postRepository.save(dto.toEntity());
    }

    //글 읽기
    @GetMapping("/api/post")
    public List<Post> resPost() {
        return postService.resPosts();
    }

    //특정 글 읽기
    @GetMapping("/api/post/{id}")
    public Optional<Post> resPostById(@PathVariable Long id) {

        return postService.resPostsById(id);
    }

    //특정 카테고리의 글 읽기

    @CrossOrigin
    @GetMapping("/api/category/{id}")
    public List<Post> resPostByCategory(@PathVariable Long id) {

        return postRepository.findByCategoryId(id);
    }

    //카테고리 추가
    @PostMapping("/api/category")
    public void savePost(@RequestBody CategorySaveRequestDto dto) {

        categoryRepository.save(dto.toEntity());
    }

    //카테고리 목록
    @GetMapping("/api/category")
    public List<Category> resCategoryById() {

        return categoryRepository.findAll();
    }

}

