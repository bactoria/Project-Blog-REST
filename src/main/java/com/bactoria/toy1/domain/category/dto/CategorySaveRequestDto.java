package com.bactoria.toy1.domain.category.dto;

import com.bactoria.toy1.domain.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

@Getter
@NoArgsConstructor
public class CategorySaveRequestDto {

    private String name;

    @Builder
    public CategorySaveRequestDto(String name) {this.name = name;}

    public Category toEntity () {
        return Category.builder()
                .name(this.name)
                .build();
    }

}
