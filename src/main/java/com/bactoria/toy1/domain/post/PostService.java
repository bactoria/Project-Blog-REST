package com.bactoria.toy1.domain.post;

import lombok.AllArgsConstructor;
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

    //명명 규칙 모르겠음.. 수정필요
    public List<Object[]> resCSR( ) {return postRepository.findCSR();}
}
