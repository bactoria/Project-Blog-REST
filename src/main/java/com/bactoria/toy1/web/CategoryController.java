package com.bactoria.toy1.web;

import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.category.CategoryService;
import com.bactoria.toy1.domain.post.PostRepository;
import com.bactoria.toy1.domain.post.PostService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebRestController.class);

    private PostService postService;
    private CategoryService categoryService;

    @CrossOrigin
    @GetMapping("/api/categories")
    public List<Category> resCategory() {
        LOGGER.info("GET  /api/categories");
        return categoryService.resCategory();
    }

    @CrossOrigin
    @GetMapping("/api/categories/{id}/posts")
    public Page<Object[]> resPostsByCategory(
            @PathVariable Long id,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable) {

        LOGGER.info("GET  /api/categories/" + id + "posts");
        return postService.resPostsByCategory(id, pageable);
    }

    @CrossOrigin
    @GetMapping("/api/categories/{id}")
    public Category resCategoryById(
            @PathVariable Long id ) {
        LOGGER.info("GET  /api/categories/" + id);
        return categoryService.resCategoryById(id);
    }

}
