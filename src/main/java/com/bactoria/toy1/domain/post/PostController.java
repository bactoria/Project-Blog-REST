package com.bactoria.toy1.domain.post;

import com.bactoria.toy1.domain.post.dto.PostMinResponseDto;
import com.bactoria.toy1.domain.post.dto.PostModifyRequestDto;
import com.bactoria.toy1.domain.post.dto.PostResponseDto;
import com.bactoria.toy1.domain.post.dto.PostSaveRequestDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api/posts")
public class PostController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    private PostService postService;

    private final int PAGE_SIZE = 5;

    @GetMapping // @GetMapping == @GetMapping("") != @GetMapping("/")
    public Page<PostMinResponseDto> resPost(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = PAGE_SIZE) Pageable pageable) {
        LOGGER.info("GET  /api/posts");
        return postService.resPostsMin(pageable);
    }

    @PostMapping
    public Post savePost(@RequestBody PostSaveRequestDto dto) {
        LOGGER.info("POST  /api/posts");
        return postService.savePost(dto);
    }

    @GetMapping("/{id}")
    public PostResponseDto resPostById(@PathVariable Long id) {
        LOGGER.info("GET  /api/posts/" + id);
        return postService.resPostsById(id);
    }

    @PutMapping("/{id}")
    public void modifyPost(@PathVariable Long id, @RequestBody PostModifyRequestDto dto) {
        LOGGER.info("PUT  /api/posts/" + id);
        postService.modifyPost(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        LOGGER.info("DELETE /api/posts/" + id);
        postService.deletePost(id);
    }

    @GetMapping("/categories/{id}")
    public Page<PostMinResponseDto> resPostsByCategory(
            @PathVariable Long id,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = PAGE_SIZE) Pageable pageable) {

        LOGGER.info("GET  /api/posts/categories/" + id);
        return postService.resPostsByCategory(id, pageable);
    }

    @GetMapping("/search/{searchData}")
    public Page<PostMinResponseDto> resPostBySearchData(@PathVariable String searchData,
                                              @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = PAGE_SIZE) Pageable pageable) {
        LOGGER.info("GET  /api/search" + "  searchData : " + searchData);
        return postService.resPostBySearchData(searchData.trim(), pageable);
    }

}
