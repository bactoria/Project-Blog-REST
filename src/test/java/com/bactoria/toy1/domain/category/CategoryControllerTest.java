package com.bactoria.toy1.domain.category;

import com.bactoria.toy1.config.WebSecurityConfig;
import com.bactoria.toy1.domain.category.dto.CategorySaveRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @Test
    public void 인증하지_않은_사용자가_카테고리_추가하면_401_Unauthorized() throws Exception {

        //then
        mockMvc.perform(post("/api/categories"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void 인증한_사용자가_카테고리_추가하면_200_isOk() throws Exception {

        // given
        final String CATEGORY_NAME = "카테고리";
        CategorySaveRequestDto dto = CategorySaveRequestDto.builder().name(CATEGORY_NAME).build();
        Category category = Category.builder().name(CATEGORY_NAME).build();
        given(categoryServiceMock.saveCategory(any(CategorySaveRequestDto.class))).willReturn(category);

        // when
        mockMvc.perform(post("/api/categories")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonStringFromObject(dto)))

                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", is(CATEGORY_NAME)));
    }

    private String jsonStringFromObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Test
    public void 인증하지_않은_사용자가_카테고리_삭제하면_401_Unauthorized() throws Exception {

        // when
        mockMvc.perform(delete("/api/categories/" + "1")
                .accept(MediaType.APPLICATION_JSON_UTF8))

                // then
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void 인증한_사용자가_카테고리_삭제하면_200_isOK() throws Exception {
        // given
        final int ID = 1;

        // when
        mockMvc.perform(delete("/api/categories/" + ID)
                .accept(MediaType.APPLICATION_JSON_UTF8))

                // then
                .andExpect(status().isOk());
    }

    @Test
    public void 인증하지_않은_사용자가_게시글_삭제하면_401_Unauthorized() throws Exception {
        // given
        final int ID = 1;

        // when
        mockMvc.perform(delete("/api/posts/" + ID)
                .accept(MediaType.APPLICATION_JSON_UTF8))

                // then
                .andExpect(status().isUnauthorized());
    }
}
