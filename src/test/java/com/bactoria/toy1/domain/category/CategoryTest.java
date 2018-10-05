package com.bactoria.toy1.domain.category;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryTest {

    @Test
    public void test001_카테고리_도메인_생성한다() {

        final String CATEGORY_NAME = "CATEGORY_NAME";

        Category category = Category.builder().name(CATEGORY_NAME).build();

        assertThat(category.getName()).isEqualTo(CATEGORY_NAME);
    }
}
