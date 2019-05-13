package com.bactoria.toy1.domain.post;

import com.bactoria.toy1.domain.BaseTimeEntity;
import com.bactoria.toy1.domain.category.Category;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter @Getter @ToString
@Builder @NoArgsConstructor @AllArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 100)
    private String subTitle;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
}