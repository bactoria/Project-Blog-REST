package com.bactoria.toy1.domain.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategorySaveRequestDto {

    private String name;

    public Category toEntity () {
        return Category.builder()
                .name(this.name)
                .build();
    }


}
