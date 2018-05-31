package com.bactoria.toy1.web;

import com.bactoria.toy1.domain.category.CategorySaveRequestDto;
import com.bactoria.toy1.domain.category.CategoryService;
import com.bactoria.toy1.domain.post.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor // 이게 @Autowired 대신 사용가능
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebRestController.class);

    private PostService postService;
    private CategoryService categoryService;

    //POST

    @CrossOrigin
    @PostMapping("/api/posts")
    public void savePost(@RequestBody PostSaveRequestDto dto) {
        LOGGER.info("POST  /api/posts");
        postService.savePost(dto);
    }

    @CrossOrigin
    @PutMapping("/api/posts/{id}")
    public void modifyPost(@PathVariable Long id, @RequestBody PostModifyRequestDto dto) {
        LOGGER.info("PUT  /api/posts/" + id);
        postService.modifyPost(id, dto);
    }

    @CrossOrigin
    @DeleteMapping("/api/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        LOGGER.info("DELETE /api/posts/" + id);
        postService.deletePost(id);
    }

    //CATEGORY

    @CrossOrigin
    @PostMapping("/api/categories")
    public void saveCategory(@RequestBody CategorySaveRequestDto dto) {
        LOGGER.info("POST /api/categories");
        categoryService.saveCategory(dto);
    }

    @CrossOrigin
    @DeleteMapping("/api/categories/{id}")
    public void deleteCategory(@PathVariable Long id) {
        LOGGER.info("DELETE /api/categories/" + id);
        categoryService.deleteCategory(id);
    }

}

