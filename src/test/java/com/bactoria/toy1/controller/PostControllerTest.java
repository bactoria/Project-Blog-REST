package com.bactoria.toy1.controller;

import com.bactoria.toy1.configuration.SecurityConfig;
import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.post.Post;
import com.bactoria.toy1.domain.post.PostService;
import org.junit.Before;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = {PostController.class, SecurityConfig.class})
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

    @Before
    public void setup() {

        // mockMvc 한글깨짐 해결
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();

    }

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

}
