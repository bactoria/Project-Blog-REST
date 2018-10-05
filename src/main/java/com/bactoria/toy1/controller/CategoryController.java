package com.bactoria.toy1.controller;

import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.category.CategoryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api/categories")
public class CategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebRestController.class);

    private CategoryService categoryService;

    @GetMapping // == GetMapping("") != GetMapping("/")
    public List<Category> resCategory() {
        LOGGER.info("GET  /api/categories");
        return categoryService.resCategory();
    }

    @GetMapping("/{id}")
    public Category resCategoryById(
            @PathVariable Long id ) {
        LOGGER.info("GET  /api/categories/" + id);
        return categoryService.resCategoryById(id);
    }

}
