package com.bactoria.toy1.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    //Name 기반 Method

    List<Post> findByCategoryId(Long categoryId);

    Page<Post> findByCategoryId(Long categoryId, Pageable pabeable);

    List<Post> findByCategoryIdOrderByIdDesc(Long categoryId);




}
