package com.bactoria.toy1.domain.posts;

import com.bactoria.toy1.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED) //lombok
@Getter //lombok
@Entity //javax.persistence
public class Posts extends BaseTimeEntity {

    @Id //javax.persistence
    @GeneratedValue //javax.persistence
    private Long id;

    @Column(length = 500, nullable = false) //javax.persistence
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

