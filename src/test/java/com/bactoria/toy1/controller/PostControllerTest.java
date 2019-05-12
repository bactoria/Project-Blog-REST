package com.bactoria.toy1.controller;

import com.bactoria.toy1.configuration.WebSecurityConfig;
import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.post.Post;
import com.bactoria.toy1.domain.post.PostService;
import com.bactoria.toy1.domain.post.dto.PostSaveRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest({WebSecurityConfig.class, PostController.class})
public class PostControllerTest {

    /* issue : https://github.com/spring-projects/spring-boot/issues/12938 */
    @TestConfiguration
    static class TestConfig implements WebMvcConfigurer {

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Test
    public void test001_전체_게시글들을_불러온다() throws Exception {

        // given

        // regacy ?
        List<Object[]> postList = Arrays.asList(
                new Object[]{1, 2},
                new Object[]{3, 4}
        );

        Page<Object[]> postPage = new PageImpl<>(postList);
        given(postServiceMock.resPostsMin(any())).willReturn(postPage);

        // when
        mockMvc.perform(get("/api/posts")
                .accept(MediaType.APPLICATION_JSON_UTF8))

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0][0]", is(1)))
                .andExpect(jsonPath("$.content[1][1]", is(4)));
    }

    @Test
    public void test002_특정_게시글을_불러온다() throws Exception {

        //given
        Post post = Post.builder().category(Category.builder().name("카테고리1").build()).content("내용").title("제목").build();
        given(postServiceMock.resPostsById(anyLong())).willReturn(Optional.of(post));

        // when
        mockMvc.perform(get("/api/posts/1")
                .accept(MediaType.APPLICATION_JSON_UTF8))

                // then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is("제목")))
                .andExpect(jsonPath("$.content", is("내용")));

    }

    @Test
    public void test003_특정_카테고리의_게시글들을_불러온다() throws Exception {

        // given
        List<Object[]> postList = Arrays.asList(
                new Object[]{1, 2},
                new Object[]{3, 4}
        );
        Page<Object[]> postPage = new PageImpl<>(postList);
        given(postServiceMock.resPostsByCategory(anyLong(), any(Pageable.class))).willReturn(postPage);

        // when
        mockMvc.perform(get("/api/posts/categories/1")
                .accept(MediaType.APPLICATION_JSON_UTF8))

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0][0]", is(1)))
                .andExpect(jsonPath("$.content[1][1]", is(4)));
    }

    @Test
    public void test004_게시글_제목을_검색하여_게시글들을_불러온다() throws Exception {

        // given
        enableKoreanOnMockMvc();
        List<Object[]> postList = Arrays.asList(
                new Object[]{1, 2},
                new Object[]{3, 4}
        );
        Page<Object[]> postPage = new PageImpl<>(postList);
        given(postServiceMock.resPostBySearchData(anyString(), isA(Pageable.class))).willReturn(postPage);

        // when
        mockMvc.perform(get("/api/posts/search/" + "한글테스트")
                .accept(MediaType.APPLICATION_JSON_UTF8))

                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content[0][0]", is(1)))
                .andExpect(jsonPath("$.content[1][1]", is(4)));
    }

    // mockMvc 한글깨짐 해결
    private void enableKoreanOnMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
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
        final String POST_TITLE = "제목";
        final String POST_CONTENT = "내용";
        final String CATEGORY_NAME = "카테고오리";

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

    private String jsonStringFromObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Test
    public void 인증하지않은_사용자가_게시글_변경하면_401_Unauthorized() throws Exception {
        mockMvc.perform(put("/api/posts"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void 인증한_사용자가_게시글_변경하면_200_isOk() throws Exception {

        // given
        final String CATEGORY_NAME = "카테고오리";
        final String POST_TITLE_MOD = "변경된 제목";
        final String POST_CONTENT_MOD = "변경된 내용";

        Category category = Category.builder().name(CATEGORY_NAME).build();
        PostSaveRequestDto dto = PostSaveRequestDto.builder().content(POST_TITLE_MOD).title(POST_CONTENT_MOD).category(category).build();

        // when
        mockMvc.perform(put("/api/posts/{id}", 1)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonStringFromObject(dto)))

                // then
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void 인증한_사용자가_게시글_삭제하면_200_isOk() throws Exception {
        // given
        final int ID = 1;

        // when
        mockMvc.perform(delete("/api/posts/" + ID)
                .accept(MediaType.APPLICATION_JSON_UTF8))

                // then
                .andExpect(status().isOk());
    }
}
