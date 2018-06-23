package com.bactoria.toy1.domain.category;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryModifyRequestDTO {

    private String name;

    @Builder
    public CategoryModifyRequestDTO(String name) {
        this.name = name;
    }
}
