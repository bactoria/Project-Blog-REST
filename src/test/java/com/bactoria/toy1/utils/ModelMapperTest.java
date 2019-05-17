package com.bactoria.toy1.utils;

import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.category.dto.CategorySaveRequestDto;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelMapperTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void 어떤_객체의_필드들이_같은_타입의_새로운_객체에_정상적으로_매핑된다() {
        // given
        final String CATEGORY_NAME = "카테고리";
        CategorySaveRequestDto requestDto = CategorySaveRequestDto.builder()
                .name(CATEGORY_NAME)
                .build();

        // when
        Category category = modelMapper.map(requestDto, Category.class);

        // then
        assertThat(category.getName()).isEqualTo(CATEGORY_NAME);
    }

    @Test
    public void 어떤_객체의_필드들이_같은_타입의_다른_객체에_정상적으로_매핑된다() {
        // given
        final String CATEGORY_NAME = "카테고리";
        CategorySaveRequestDto requestDto = CategorySaveRequestDto.builder()
                .name(CATEGORY_NAME)
                .build();
        Category category = Category.builder()
                .build();

        // when
        modelMapper.map(requestDto, category);

        // then
        assertThat(category.getName()).isEqualTo(CATEGORY_NAME);
    }
}
