package com.bactoria.toy1.domain.categories;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoriesSaveRequestDto {

    private Long id;
    private String name;

    public Categories toEntity () {
        return Categories.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }


}
