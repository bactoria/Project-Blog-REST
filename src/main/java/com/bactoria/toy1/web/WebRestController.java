package com.bactoria.toy1.web;


import com.bactoria.toy1.domain.categories.Categories;
import com.bactoria.toy1.domain.categories.CategoriesRepository;
import com.bactoria.toy1.domain.categories.CategoriesSaveRequestDto;
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
    private CategoriesRepository categoriesRepository;

    @GetMapping("/hello")
    public String hello() {
        return "HELLO WORLD !";
    }

    @PostMapping("/api/posts")
    public void savePosts(@RequestBody PostsSaveRequestDto dto) {
        postsRepository.save(dto.toEntity());
    }

    @GetMapping("/api/posts")
    public List<Posts> resPosts() {
        return postsRepository.findAll();
    }

    @GetMapping("/api/categories")
    public List<Categories> resCategories() {
        return categoriesRepository.findAll();
    }

    @PostMapping("/api/categories")
    public void savePosts(@RequestBody CategoriesSaveRequestDto dto) {
        categoriesRepository.save(dto.toEntity());
    }

}

