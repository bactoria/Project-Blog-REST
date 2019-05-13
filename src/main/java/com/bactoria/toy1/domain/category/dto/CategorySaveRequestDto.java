package com.bactoria.toy1.domain.category.dto;

import lombok.*;

@Setter @Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class CategorySaveRequestDto {
    private String name;
}
