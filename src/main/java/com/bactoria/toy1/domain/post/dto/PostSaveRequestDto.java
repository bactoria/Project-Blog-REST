package com.bactoria.toy1.domain.post.dto;

import com.bactoria.toy1.domain.category.Category;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter @Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class PostSaveRequestDto {
    @NotBlank(message = "title is blank")
    private String title;
    private String subTitle;
    @NotBlank(message = "content is blank")
    private String content;
    private Category category;
}
