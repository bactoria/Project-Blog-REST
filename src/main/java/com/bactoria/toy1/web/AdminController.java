package com.bactoria.toy1.web;

import com.bactoria.toy1.domain.category.CategorySaveRequestDto;
import com.bactoria.toy1.domain.category.CategoryService;
import com.bactoria.toy1.domain.post.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor // 이게 @Autowired 대신 사용가능
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebRestController.class);

    private PostService postService;
    private CategoryService categoryService;

    @CrossOrigin
    @PostMapping("/admin/newPost")
    public void savePost(@RequestBody PostSaveRequestDto dto) {
        LOGGER.info("post  /admin/newPost");
        postService.savePost(dto);
    }

    @CrossOrigin
    @PostMapping("/admin/newCategory")
    public void saveCategory(@RequestBody CategorySaveRequestDto dto) {
        categoryService.saveCategory(dto);
    }

}

