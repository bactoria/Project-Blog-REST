package com.bactoria.toy1.web;

import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.category.CategoryService;
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
    private PostService postService;
    private CategoryService categoryService;
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


    //POST

    @CrossOrigin
    @GetMapping("/api/posts")
    public List<Post> resPost() {
        LOGGER.info("GET  /api/posts");
        return postService.resPosts();
    }

    @CrossOrigin
    @GetMapping("/api/posts/{id}")
    public Optional<Post> resPostById(@PathVariable Long id) {

        LOGGER.info("GET  /api/posts/"+id);
        return postService.resPostsById(id);
    }


    //CATEGORY

    @CrossOrigin
    @GetMapping("/api/categories/{id}")
    public Page<Object[]> resPostsByCategory(
            @PathVariable Long id,
            @PageableDefault( sort = {"id"}, direction= Sort.Direction.DESC, size = 10 ) Pageable pageable ) {

        LOGGER.info("GET  /api/categories/"+id);
        return postService.resPostsByCategory(id,pageable);
    }

    @CrossOrigin
    @GetMapping("/api/categories")
    public List<Category> resCategory() {
        LOGGER.info("GET  /api/categories");
        return categoryService.resCategory();
    }

    @CrossOrigin
    @GetMapping("/api/csr")
    public List<Object[]> resCSR () {
        LOGGER.info("get  /api/csr");
        return postRepository.findCSR();
    }

    @CrossOrigin
    @GetMapping("/api/search")
    public String resPostBySearch(@RequestParam("searchData") String data) {
        LOGGER.info("GET  /api/search" + "searchData : " + data);
        return data;
    }

    @CrossOrigin
    @GetMapping("/api/search/{searchData}")
    public List<Object[]> resPostBySearchData(@PathVariable String searchData) {
        LOGGER.info("GET  /api/search" + "  searchData : " + searchData);
        return postService.resPostBySearchData(searchData.trim());
    }
}

