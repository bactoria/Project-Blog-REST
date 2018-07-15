package com.bactoria.toy1.domain.post;

import com.bactoria.toy1.domain.BaseTimeEntity;
import com.bactoria.toy1.domain.category.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED) //lombok
@Getter //lombok
@Entity //javax.persistence
public class Post extends BaseTimeEntity {

    @Id //javax.persistence
    @GeneratedValue //javax.persistence
    private Long id;

    //@Min(value = 1)
    //private Long categoryId;

    @ManyToOne(optional=false)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(length = 100, nullable = false) //javax.persistence
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false) //javax.persistence
    private String content;

    @Builder //lombok
    public Post(String title, String content, Category category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public void setCategory(Category category) {
        this.category = category;

    }

}