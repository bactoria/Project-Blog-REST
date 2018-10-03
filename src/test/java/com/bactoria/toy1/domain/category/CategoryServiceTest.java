package com.bactoria.toy1.domain.category;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepositoryMock;

    @InjectMocks
    private CategoryService categoryService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test001_모든_카테고리를_불러온다() {

        //given
        String firstCategoryName = "카테고리1";
        String secondCategoryName = "카테고리2";
        given(categoryRepositoryMock.findAll()).willReturn(Arrays.asList(
                Category.builder().name(firstCategoryName).build(),
                Category.builder().name(secondCategoryName).build()
        ));

        //when
        List<Category> categoryList = categoryService.resCategory();

        //then
        verify(categoryRepositoryMock, times(1)).findAll();
        assertThat(categoryList).hasSize(2);
        assertThat(categoryList.get(0).getName()).isEqualTo(firstCategoryName);
        assertThat(categoryList.get(1).getName()).isEqualTo(secondCategoryName);
    }

    @Test
    public void test002_특정_카테고리를_불러온다() {

        //given
        String categoryName = "카테고리1";
        given(categoryRepositoryMock.findById(anyLong())).willReturn(
                java.util.Optional.ofNullable(Category.builder().name(categoryName).build())
        );

        //when
        Category category = categoryService.resCategoryById(1L);

        //then
        verify(categoryRepositoryMock, times(1)).findById(1L);
        assertThat(category.getName()).isEqualTo(categoryName);
    }

}
