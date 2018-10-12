package com.bactoria.toy1.domain.category.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryModifyRequestDto {

    private String name;

    @Builder
    public CategoryModifyRequestDto(String name) {
        this.name = name;
    }
}
