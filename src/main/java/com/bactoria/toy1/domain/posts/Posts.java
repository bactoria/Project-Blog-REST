package com.bactoria.toy1.domain.posts;

import com.bactoria.toy1.domain.BaseTimeEntity;
import com.bactoria.toy1.domain.categories.Categories;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED) //lombok
@Getter //lombok
@Entity //javax.persistence
public class Posts extends BaseTimeEntity {

    @Id //javax.persistence
    @GeneratedValue //javax.persistence
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CATEGORIES_ID")
    private Categories category;

    @Column(length = 100, nullable = false) //javax.persistence
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false) //javax.persistence
    private String content;

    private String author;

    @Builder //lombok
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

}

