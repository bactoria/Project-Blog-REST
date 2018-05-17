package com.bactoria.toy1.web;

import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.category.CategoryRepository;
import com.bactoria.toy1.domain.category.CategorySaveRequestDto;
import com.bactoria.toy1.domain.post.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor // 이게 @Autowired 대신 사용가능
public class WebRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebRestController.class);

    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    private PostService postService;
    private Environment env;

    @GetMapping("/profile")
    public String getProfile () {
        return Arrays.stream(env.getActiveProfiles())
                .findFirst()
                .orElse("Not Exist");
    }

    @GetMapping("/")
    public String index() {
        return "bactoris's API Server";
    }

    @CrossOrigin
    @PostMapping("/api/post")
    public void savePost(@RequestBody PostSaveRequestDto dto) {
        LOGGER.info("post  /api/post");
        postRepository.save(dto.toEntity());
    }

    @CrossOrigin
    @GetMapping("/api/post")
    public List<Post> resPost() {
        LOGGER.info("get  /api/post");
        return postService.resPosts();
    }

    @CrossOrigin
    @GetMapping("/api/post/{id}")
    public Optional<Post> resPostById(@PathVariable Long id) {

        LOGGER.info("get  /api/post/"+id);
        return postService.resPostsById(id);
    }

    @CrossOrigin
    @GetMapping("/api/category/{id}")
    public Page<Object[]> resPostsByCategory(
            @PathVariable Long id,
            @PageableDefault( sort = {"id"}, direction= Sort.Direction.DESC, size = 10 ) Pageable pageable ) {

        LOGGER.info("get  /api/category/"+id);
        return postRepository.findByCategoryIdMin(id,pageable);
    }

    @CrossOrigin
    @GetMapping("/api/csr")
    public List<Object[]> resCSR () {
        LOGGER.info("get  /api/csr");
        return postRepository.findCSR();
    }

    @CrossOrigin
    @GetMapping("/api/category")
    public List<Category> resCategory() {
        LOGGER.info("get  /api/category");
        return categoryRepository.findAll();
    }

    @CrossOrigin
    @PostMapping("/api/category")
    public void savePost(@RequestBody CategorySaveRequestDto dto) {

        categoryRepository.save(dto.toEntity());
    }

/*
    @CrossOrigin
    @GetMapping("/api/search")
    public String resPostBySearch(@RequestParam("searchData") String data) {
        LOGGER.info("get  /api/search" + "searchData : " + data);
        return data;
    }
    */

    @CrossOrigin
    @GetMapping("/api/search/{searchData}")
    public List<Object[]> resPostBySearch(@PathVariable String searchData) {
        LOGGER.info("get  /api/search" + "  searchData : " + searchData);
        return postRepository.findBySearchData(searchData.trim());
    }
}

