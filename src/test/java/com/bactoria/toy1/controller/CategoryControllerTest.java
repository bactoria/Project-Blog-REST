package com.bactoria.toy1.controller;

import com.bactoria.toy1.configuration.WebSecurityConfig;
import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.category.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)

@WebMvcTest({CategoryController.class, WebSecurityConfig.class})
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryServiceMock;

    @Test
    public void test001_모든_카테고리_불러온다() throws Exception {

        // given
        List<Category> categoryList = Arrays.asList(
                Category.builder().name("카테고리1").build(),
                Category.builder().name("카테고리2").build()
        );

        given(categoryServiceMock.resCategory()).willReturn(categoryList);

        // when
        mockMvc.perform(get("/api/categories"))

                // then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("카테고리1")))
                .andExpect(jsonPath("$[1].name", is("카테고리2")));
    }

    @Test
    public void test002_특정_카테고리_불러온다() throws Exception {

        // given
        final String CATEGORY_NAME = "카테고리3";

        Category category = Category.builder().name(CATEGORY_NAME).build();

        given(categoryServiceMock.resCategoryById(anyLong())).willReturn(category);

        // when
        mockMvc.perform(get("/api/categories/{id}", 1)
                .accept(MediaType.APPLICATION_JSON_UTF8))

                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", is(CATEGORY_NAME)));
    }
}
