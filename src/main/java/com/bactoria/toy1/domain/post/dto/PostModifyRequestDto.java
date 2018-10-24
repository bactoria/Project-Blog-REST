package com.bactoria.toy1.domain.post.dto;

import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//롬복 어노테이션
@Getter
@Setter
@NoArgsConstructor
public class PostModifyRequestDto {

    private String title;
    private String content;
    private Category category;

    public Post toEntity() {
        return Post.builder()
                    .title(title)
                    .content(content)
                    .category(category)
                    .build();
    }

    @Builder
    public PostModifyRequestDto(String title, String content, Category category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
