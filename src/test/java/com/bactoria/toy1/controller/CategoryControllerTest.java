package com.bactoria.toy1.controller;

import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.category.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryServiceMock;

    private String jsonStringFromObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Test
    public void test001_모든_카테고리_불러온다() throws Exception {

        List<Category> categoryList = Arrays.asList(
                Category.builder().name("카테고리1").build(),
                Category.builder().name("카테고리2").build()
                );

        String category_json = this.jsonStringFromObject(categoryList);

        when(categoryServiceMock.resCategory()).thenReturn(categoryList);

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name",is("카테고리1")))
                .andExpect(jsonPath("$[1].name",is("카테고리2")))
                .andExpect(content().string(equalTo(category_json)));
     }

    @Test
    public void test002_특정_카테고리_불러온다() throws Exception {

        Category category = Category.builder().name("카테고리1").build();

        String categoryJson = jsonStringFromObject(category);

        when(categoryServiceMock.resCategoryById(anyLong())).thenReturn(category);

        mockMvc.perform(get("/api/categories/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", Matchers.is("카테고리1")))
                .andExpect(content().string(equalTo(categoryJson)));
    }
}
