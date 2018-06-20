package com.bactoria.toy1.web;

import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.category.CategorySaveRequestDto;
import com.bactoria.toy1.domain.category.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AdminControllerTest {

    @InjectMocks
    AdminController adminController;

    @Mock
    CategoryService categoryServiceMock;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(adminController)
                .build();
    }

    private String jsonStringFromObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Test
    public void test001_카테고리_추가한다() throws Exception {

        CategorySaveRequestDto dto = CategorySaveRequestDto.builder().name("카테고리1").build();

        String dtoJson = jsonStringFromObject(dto);

        Category category = Category.builder().name("카테고리1").build();

        when(categoryServiceMock.saveCategory(any(CategorySaveRequestDto.class))).thenReturn(category);

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJson))

                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name",is("카테고리1")));

    }
}
