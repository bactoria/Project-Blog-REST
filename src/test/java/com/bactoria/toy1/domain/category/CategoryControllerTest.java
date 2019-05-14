package com.bactoria.toy1.domain.category;

import com.bactoria.toy1.config.AppConfig;
import com.bactoria.toy1.config.WebSecurityConfig;
import com.bactoria.toy1.domain.category.dto.CategoryModifyRequestDto;
import com.bactoria.toy1.domain.category.dto.CategoryResponseDto;
import com.bactoria.toy1.domain.category.dto.CategorySaveRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest({CategoryController.class, WebSecurityConfig.class, AppConfig.class})
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

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
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
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
    public void 인증한_사용자가_카테고리_추가하면_201_Created() throws Exception {

        // given
        final Long ID = 1L;
        final String CATEGORY_NAME = "카테고리";
        CategorySaveRequestDto dto = CategorySaveRequestDto.builder().name(CATEGORY_NAME).build();
        Category category = Category.builder()
                .id(ID)
                .name(CATEGORY_NAME)
                .build();
        CategoryResponseDto responseDto = modelMapper.map(category, CategoryResponseDto.class);
        given(categoryServiceMock.saveCategory(any(CategorySaveRequestDto.class))).willReturn(responseDto);

        // when
        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(dto)))

                // then
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
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
    public void 인증한_사용자가_카테고리_삭제하면_204_NoContent() throws Exception {
        // given
        final int ID = 1;

        // when
        mockMvc.perform(delete("/api/categories/" + ID)
                .accept(MediaType.APPLICATION_JSON_UTF8))

                // then
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    public void 카테고리_추가시_이름이_null이면_400_BadRequest() throws Exception {

        // given
        CategorySaveRequestDto requestDto = CategorySaveRequestDto.builder().build();

        // when
        mockMvc.perform(post("/api/categories")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(requestDto)))

                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field",is("name")))
                .andExpect(jsonPath("$[0].code",is("NotBlank")));
    }

    @Test
    @WithMockUser
    public void 카테고리_추가시_이름이_공백이면_400_BadRequest() throws Exception {
        //given
        final String NAME = " ";

        CategorySaveRequestDto requestDto = CategorySaveRequestDto.builder()
                .name(NAME)
                .build();

        // when
        mockMvc.perform(post("/api/categories")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(requestDto)))

                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field",is("name")))
                .andExpect(jsonPath("$[0].code",is("NotBlank")));
    }

    @Test
    @WithMockUser
    public void 카테고리_추가시_이름이_비어있으면_400_BadRequest() throws Exception {
        //given
        final String NAME = "";

        CategorySaveRequestDto requestDto = CategorySaveRequestDto.builder()
                .name(NAME)
                .build();

        // when
        mockMvc.perform(post("/api/categories")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(requestDto)))

                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field",is("name")))
                .andExpect(jsonPath("$[0].code",is("NotBlank")));
    }

    @Test
    @WithMockUser
    public void 카테고리_수정시_이름이_null이면_400_BadRequest() throws Exception {

        // given
        final int ID = 1;

        CategoryModifyRequestDto requestDto = CategoryModifyRequestDto.builder().build();

        // when
        mockMvc.perform(put("/api/categories/" + ID)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(requestDto)))

                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field",is("name")))
                .andExpect(jsonPath("$[0].code",is("NotBlank")));
    }

    @Test
    @WithMockUser
    public void 카테고리_수정시_이름이_비어있으면_400_BadRequest() throws Exception {

        // given
        final int ID = 1;
        final String NAME = "";

        CategoryModifyRequestDto requestDto = CategoryModifyRequestDto.builder()
                .name(NAME)
                .build();

        // when
        mockMvc.perform(put("/api/categories/" + ID)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(requestDto)))

                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field",is("name")))
                .andExpect(jsonPath("$[0].code",is("NotBlank")));
    }

    @Test
    @WithMockUser
    public void 카테고리_수정시_이름이_공백이면_400_BadRequest() throws Exception {

        // given
        final int ID = 1;
        final String NAME = " ";

        CategoryModifyRequestDto requestDto = CategoryModifyRequestDto.builder()
                .name(NAME)
                .build();

        // when
        mockMvc.perform(put("/api/categories/" + ID)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(requestDto)))

                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field",is("name")))
                .andExpect(jsonPath("$[0].code",is("NotBlank")));
    }
}
