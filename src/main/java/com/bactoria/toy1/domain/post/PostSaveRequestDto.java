package com.bactoria.toy1.domain.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//롬복 어노테이션
@Getter
@Setter
@NoArgsConstructor

public class PostSaveRequestDto {

    private String title;
    private String content;
    private Long categoryId;

    public Post toEntity() {
        return Post.builder()
                    .title(title)
                    .content(content)
                    .categoryId(categoryId)
                    .build();
    }

}
