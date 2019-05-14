package com.bactoria.toy1.domain.post;

import com.bactoria.toy1.domain.post.dto.PostMinResponseDto;
import com.bactoria.toy1.domain.post.dto.PostModifyRequestDto;
import com.bactoria.toy1.domain.post.dto.PostResponseDto;
import com.bactoria.toy1.domain.post.dto.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping(value = "/api/posts",
                            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PostController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;

    private final int PAGE_SIZE = 5;

    @PostMapping
    public ResponseEntity savePost(@RequestBody PostSaveRequestDto requestDto) {
        LOGGER.info("POST  /api/posts");
        PostResponseDto responseDto = postService.savePost(requestDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + responseDto.getId())
                .build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity resPost(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = PAGE_SIZE) Pageable pageable) {
        LOGGER.info("GET  /api/posts");
        Page<PostMinResponseDto> page = postService.resPostsMin(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity resPostById(@PathVariable Long id) {
        LOGGER.info("GET  /api/posts/" + id);
        PostResponseDto responseDto = postService.resPostsById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity resPostsByCategory(
            @PathVariable Long id,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = PAGE_SIZE) Pageable pageable) {

        LOGGER.info("GET  /api/posts/categories/" + id);
        Page<PostMinResponseDto> page = postService.resPostsByCategory(id, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/search/{searchData}")
    public ResponseEntity resPostBySearchData(@PathVariable String searchData,
                                                        @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = PAGE_SIZE) Pageable pageable) {
        LOGGER.info("GET  /api/search" + "  searchData : " + searchData);
        Page<PostMinResponseDto> page = postService.resPostBySearchData(searchData.trim(), pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity modifyPost(@PathVariable Long id, @RequestBody PostModifyRequestDto requestDto) {
        LOGGER.info("PUT  /api/posts/" + id);
        postService.modifyPost(id, requestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable Long id) {
        LOGGER.info("DELETE /api/posts/" + id);
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

}
