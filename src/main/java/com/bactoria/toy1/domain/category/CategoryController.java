package com.bactoria.toy1.domain.category;

import com.bactoria.toy1.domain.category.dto.CategoryModifyRequestDto;
import com.bactoria.toy1.domain.category.dto.CategorySaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    @GetMapping // == GetMapping("") != GetMapping("/")
    public List<Category> resCategory() {
        LOGGER.info("GET  /api/categories");
        return categoryService.resCategory();
    }

    @PostMapping
    public Category saveCategory(@RequestBody CategorySaveRequestDto dto) {
        LOGGER.info("POST /api/categories");
        return categoryService.saveCategory(dto);
    }

    @GetMapping("/{id}")
    public Category resCategoryById(
            @PathVariable Long id ) {
        LOGGER.info("GET  /api/categories/" + id);
        return categoryService.resCategoryById(id);
    }
    
    @PutMapping("/{id}")
    public void ModifyCategory(@PathVariable Long id, @RequestBody CategoryModifyRequestDto dto) {
        LOGGER.info("PUT  /api/categories/" + id);
        categoryService.modifyCategory(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        LOGGER.info("DELETE /api/categories/" + id);
        categoryService.deleteCategory(id);
    }

}
