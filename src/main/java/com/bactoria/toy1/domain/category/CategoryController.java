package com.bactoria.toy1.domain.category;

import com.bactoria.toy1.domain.category.dto.CategoryModifyRequestDto;
import com.bactoria.toy1.domain.category.dto.CategoryResponseDto;
import com.bactoria.toy1.domain.category.dto.CategorySaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/categories",
                            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity saveCategory(@Valid @RequestBody CategorySaveRequestDto dto,
                                       Errors errors) {
        log.info("POST /api/categories");

        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        CategoryResponseDto responseDto = categoryService.saveCategory(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + responseDto.getId())
                .build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity resCategory() {
        log.info("GET  /api/categories");
        List<Category> categories = categoryService.resCategory();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity resCategoryById(@PathVariable Long id) {
        log.info("GET  /api/categories/" + id);
        Category category = categoryService.resCategoryById(id);
        return ResponseEntity.ok(category);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity ModifyCategory(@PathVariable Long id,
                                         @Valid @RequestBody CategoryModifyRequestDto dto,
                                         Errors errors) {
        log.info("PUT  /api/categories/" + id);

        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        categoryService.modifyCategory(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id) {
        log.info("DELETE /api/categories/" + id);
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
