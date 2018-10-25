package com.bactoria.toy1.controller;

import com.bactoria.toy1.configuration.WebSecurityConfig;
import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.category.CategoryService;
import com.bactoria.toy1.domain.category.dto.CategorySaveRequestDto;
import com.bactoria.toy1.domain.post.Post;
import com.bactoria.toy1.domain.post.PostService;
import com.bactoria.toy1.domain.post.dto.PostSaveRequestDto;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest({WebSecurityConfig.class, AdminController.class})
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryServiceMock;

    @MockBean
    private PostService postServiceMock;

    private final String POST_TITLE = "제목";
    private final String POST_CONTENT = "내용";
    private final String CATEGORY_NAME = "카테고오리";

    private String jsonStringFromObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Test
    public void 인증하지않은_사용자가_게시글_추가하면_401_Unauthorized() throws Exception {
        mockMvc.perform(post("/api/posts"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void 인증한_사용자가_게시글_추가하면_정상적으로_동작한다() throws Exception {

        // given
        Category category = Category.builder().name(CATEGORY_NAME).build();
        PostSaveRequestDto dto = PostSaveRequestDto.builder().content(POST_CONTENT).title(POST_TITLE).category(category).build();

        given(postServiceMock.savePost(any(PostSaveRequestDto.class))).willReturn(Post.builder().category(category).title("제목").content("내용").build());

        // when
        mockMvc.perform(post("/api/posts")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonStringFromObject(dto)))

                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.title", is(POST_TITLE)))
                .andExpect(jsonPath("$.content", is(POST_CONTENT)))
                .andExpect(jsonPath("$.category.name", is(CATEGORY_NAME)));
    }

    @Test
    public void 인증하지않은_사용자가_게시글_변경하면_401_Unauthorized() throws Exception {
        mockMvc.perform(put("/api/posts"))
                .andExpect(status().isUnauthorized());
    }

    // regacy ?? 그래서 카테고리 변경 / 카테고리 삭제/ 게시글 삭제 는 아직 추가 안함.
    // void로 반환하는 것은 어떻게 슬라이싱 테스트 할지..
    @Test
    @WithMockUser
    public void 인증한_사용자가_게시글_변경하면_200_isOk() throws Exception {

        // given
        final String POST_TITLE_MOD = "변경된 제목";
        final String POST_CONTENT_MOD = "변경된 내용";

        Category category = Category.builder().name(CATEGORY_NAME).build();
        PostSaveRequestDto dto = PostSaveRequestDto.builder().content(POST_CONTENT).title(POST_TITLE).category(category).build();

        // when
        mockMvc.perform(put("/api/posts/{id}", 1)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonStringFromObject(dto)))

                // then
                .andExpect(status().isOk());
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

        // when
        mockMvc.perform(delete("/api/categories/" + "1")
                .accept(MediaType.APPLICATION_JSON_UTF8))

                // then
                .andExpect(status().isOk());
    }

    @Test
    public void 인증하지_않은_사용자가_게시글_삭제하면_401_Unauthorized() throws Exception {

        // when
        mockMvc.perform(delete("/api/posts/" + "1")
                .accept(MediaType.APPLICATION_JSON_UTF8))

                // then
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void 인증한_사용자가_게시글_삭제하면_200_isOk() throws Exception {

        // when
        mockMvc.perform(delete("/api/posts/" + "1")
                .accept(MediaType.APPLICATION_JSON_UTF8))

                // then
                .andExpect(status().isOk());
    }
}