package com.bactoria.toy1.domain.post;

import com.bactoria.toy1.domain.category.Category;
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

}
