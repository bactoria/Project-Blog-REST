package com.bactoria.toy1.domain.category;

import com.bactoria.toy1.domain.category.dto.CategoryModifyRequestDto;
import com.bactoria.toy1.domain.category.dto.CategoryResponseDto;
import com.bactoria.toy1.domain.category.dto.CategorySaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/categories",
                            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity saveCategory(@RequestBody CategorySaveRequestDto dto) {
        LOGGER.info("POST /api/categories");
        CategoryResponseDto responseDto = categoryService.saveCategory(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + responseDto.getId())
                .build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity resCategory() {
        LOGGER.info("GET  /api/categories");
        List<Category> categories = categoryService.resCategory();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity resCategoryById(@PathVariable Long id) {
        LOGGER.info("GET  /api/categories/" + id);
        Category category = categoryService.resCategoryById(id);
        return ResponseEntity.ok(category);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity ModifyCategory(@PathVariable Long id, @RequestBody CategoryModifyRequestDto dto) {
        LOGGER.info("PUT  /api/categories/" + id);
        categoryService.modifyCategory(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id) {
        LOGGER.info("DELETE /api/categories/" + id);
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
