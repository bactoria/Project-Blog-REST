package com.bactoria.toy1.domain.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostSaveRequestDto {

    private String title;
    private String content;
    private String author;
    private Long categoryId;

    public Post toEntity() {
        return Post.builder()
                    .title(title)
                    .content(content)
                    .author(author)
                    .categoryId(categoryId)
                    .build();
    }
}
