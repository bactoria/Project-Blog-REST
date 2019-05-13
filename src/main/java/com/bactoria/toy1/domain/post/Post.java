package com.bactoria.toy1.domain.post;

import com.bactoria.toy1.domain.BaseTimeEntity;
import com.bactoria.toy1.domain.category.Category;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter @Getter
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(length = 100, nullable = false) //javax.persistence
    private String title;

    @Column(length = 100)
    private String subTitle;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder
    public Post(String title, String subTitle, String content, Category category) {
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.category = category;
    }

    public void setCategory(Category category) {
        this.category = category;

    }

}