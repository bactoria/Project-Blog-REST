package com.bactoria.toy1.domain.post;

import com.bactoria.toy1.domain.category.Category;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Transactional
@Service //나 스프링빈으로 등록시켜줘! (@Component를 가짐)
public class PostService {

    private PostRepository postRepository;

    public List<Post> resPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> resPostsById(Long id) {return postRepository.findById(id); }

    public Page<Object[]> resPostsByCategory(Long id, Pageable pageable) {
        return postRepository.findByCategoryIdMin(id,pageable);
    }

    public Page<Object[]> resPostsMin(Pageable pageable) {
        return postRepository.findMin(pageable);
    }

    public Page<Object[]> resPostBySearchData (String searchData, Pageable pageable) {
        return postRepository.findBySearchData(searchData.trim(), pageable);
    }

    public Post savePost (PostSaveRequestDto dto) {

        //예외 처리
        String title = dto.getTitle();
        if (title == null || title.trim().isEmpty()) throw new IllegalArgumentException();

        String content = dto.getContent();
        if (content == null || content.trim().isEmpty()) throw new IllegalArgumentException();

        Category category = dto.getCategory();
        if (category == null ) throw new IllegalArgumentException();

        //
        return postRepository.save(dto.toEntity());
    }


    public void modifyPost (Long id, PostModifyRequestDto dto) {
        postRepository.modifyPost(id,dto.getTitle(),dto.getContent(),dto.getCategory());
    }

    public void deletePost (Long id) {
        postRepository.deleteById(id);
    }


}

