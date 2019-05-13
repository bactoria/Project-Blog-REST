package com.bactoria.toy1.domain.post.dto;

import com.bactoria.toy1.domain.category.Category;
import lombok.*;

@Setter @Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class PostSaveRequestDto {
    private String title;
    private String subTitle;
    private String content;
    private Category category;
}
