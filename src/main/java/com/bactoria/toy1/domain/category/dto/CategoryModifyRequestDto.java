package com.bactoria.toy1.domain.category.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter @Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class CategoryModifyRequestDto {
    @NotBlank(message = "name is blank")
    private String name;
}
