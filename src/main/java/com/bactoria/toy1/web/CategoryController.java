package com.bactoria.toy1.web;

import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.category.CategoryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
public class CategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebRestController.class);

    private CategoryService categoryService;

    @GetMapping("/api/categories")
    public List<Category> resCategory() {
        LOGGER.info("GET  /api/categories");
        return categoryService.resCategory();
    }

    @GetMapping("/api/categories/{id}")
    public Category resCategoryById(
            @PathVariable Long id ) {
        LOGGER.info("GET  /api/categories/" + id);
        return categoryService.resCategoryById(id);
    }

}
