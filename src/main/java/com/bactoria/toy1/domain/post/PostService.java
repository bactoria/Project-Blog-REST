package com.bactoria.toy1.domain.post;

import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.post.dto.PostMinResponseDto;
import com.bactoria.toy1.domain.post.dto.PostModifyRequestDto;
import com.bactoria.toy1.domain.post.dto.PostResponseDto;
import com.bactoria.toy1.domain.post.dto.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public List<Post> resPosts() {
        return postRepository.findAll();
    }

    public PostResponseDto resPostsById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(post, PostResponseDto.class);
    }

    public Page<PostMinResponseDto> resPostsByCategory(Long id, Pageable pageable) {
        return postRepository.findByCategoryId(id, pageable)
                .map(post -> modelMapper.map(post, PostMinResponseDto.class));
    }

    public Page<PostMinResponseDto> resPostsMin(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(post -> modelMapper.map(post, PostMinResponseDto.class));
    }

    public Page<PostMinResponseDto> resPostBySearchData(String searchData, Pageable pageable) {
        return postRepository.findBySearchData(searchData.trim(), pageable)
                .map(post -> modelMapper.map(post, PostMinResponseDto.class));
    }

    public PostResponseDto savePost(PostSaveRequestDto requestDto) {

        String title = requestDto.getTitle();
        if (title == null || title.trim().isEmpty()) throw new IllegalArgumentException();

        String content = requestDto.getContent();
        if (content == null || content.trim().isEmpty()) throw new IllegalArgumentException();

        Category category = requestDto.getCategory();
        if (category == null) throw new IllegalArgumentException();

        Post post = modelMapper.map(requestDto, Post.class);
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostResponseDto.class);
    }


    public void modifyPost(Long id, PostModifyRequestDto requestDto) {
        Post savedPost = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        modelMapper.map(requestDto, savedPost);
        postRepository.save(savedPost);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

}

