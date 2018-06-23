package com.bactoria.toy1.domain.category;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryTest {

    @Test
    public void test001_카테고리_도메인_생성한다() {

        final String categoryName = "카테고리1";

        Category category = Category.builder().name(categoryName).build();

        assertThat(category.getName(), is(categoryName));

    }

}
