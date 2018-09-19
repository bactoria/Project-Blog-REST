package com.bactoria.toy1.domain.post;

import com.bactoria.toy1.domain.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    //Name 기반 Method

    //List<Post> findByCategoryId(Long categoryId);
    //List<Post> findByCategoryIdOrderByIdDesc(Long categoryId);

    @Query (value = "select post.id, post.category, post.title, post.createdDate from Post post where post.category.id = ?1")
    Page<Object[]> findByCategoryIdMin(Long categoryId, Pageable pabeable);

    @Query (value = "select post.id, post.category, post.title, post.createdDate from Post post")
    Page<Object[]> findMin(Pageable pabeable);

    @Query (value = "select post.id, post.category.id, post.title, post.createdDate from Post post where lower(post.title) like lower(concat('%',?1,'%'))")
    List<Object[]> findBySearchData(String SearchData);

    @Query (value = "select post.id, post.category.id, post.title, post.createdDate from Post post")
    List<Object[]> findInFive();

    //객체로 받는게 이쓸까? title, content, category를 한번에..
    @Modifying(clearAutomatically = true)
    @Query(value = "update Post p set p.title = ?2, p.content = ?3, p.category = ?4 where p.id = ?1")
    void modifyPost(Long id, String title, String content, Category category);
}
