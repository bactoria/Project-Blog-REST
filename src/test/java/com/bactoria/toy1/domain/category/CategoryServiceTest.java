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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepositoryMock;

    @InjectMocks
    CategoryService categoryService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test001_모든_카테고리를_불러온다 () {

        given(categoryRepositoryMock.findAll()).willReturn(Arrays.asList(
                Category.builder().name("카테고리1").build(),
                Category.builder().name("카테고리2").build()
        ));

        List<Category> categoryList = categoryService.resCategory();

        verify(categoryRepositoryMock, times(1)).findAll();
        assertThat(categoryList, hasSize(2));
        assertThat(categoryList.get(0).getName(), is("카테고리1"));
        assertThat(categoryList.get(1).getName(), is("카테고리2"));

    }

    @Test
    public void test001_특정_카테고리를_불러온다 () {

        //수정 될 수도 있음.

        given(categoryRepositoryMock.findById(anyLong())).willReturn (
                java.util.Optional.ofNullable(Category.builder().name("카테고리1").build())
        );

        Category category = categoryService.resCategoryById((long)1);

        verify(categoryRepositoryMock, times(1)).findById((long)1);
        assertThat(category.getName(), is("카테고리1"));

    }

}
