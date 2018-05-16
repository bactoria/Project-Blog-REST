package com.bactoria.toy1.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    //Name 기반 Method

    //List<Post> findByCategoryId(Long categoryId);
    //List<Post> findByCategoryIdOrderByIdDesc(Long categoryId);

    @Query (value = "select post.id, post.categoryId, post.title, post.createdDate from Post post where post.categoryId = ?1")
    Page<Object[]> findByCategoryIdMin(Long categoryId, Pageable pabeable);


    @Query (value = "select post.id, post.categoryId, post.title, post.createdDate from Post post")
    List<Object[]> findCSR();

}
