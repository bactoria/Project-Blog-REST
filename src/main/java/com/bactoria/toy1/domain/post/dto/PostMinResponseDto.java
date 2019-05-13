package com.bactoria.toy1.domain.post.dto;

import com.bactoria.toy1.domain.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class PostMinResponseDto {
    private Long id;
    private Category category;
    private String title;
    private String subTitle;
    private String createdDate;
}
