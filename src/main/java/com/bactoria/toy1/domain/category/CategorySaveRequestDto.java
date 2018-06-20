package com.bactoria.toy1.domain.category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

@Setter
@Getter
@NoArgsConstructor
public class CategorySaveRequestDto {

    private String name;

    @Builder
    CategorySaveRequestDto(String name) {this.name = name;}

    public Category toEntity () {
        return Category.builder()
                .name(this.name)
                .build();
    }



}
