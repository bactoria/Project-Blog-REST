package com.bactoria.toy1.domain.post;

import com.bactoria.toy1.config.AppConfig;
import com.bactoria.toy1.config.WebSecurityConfig;
import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.post.dto.PostMinResponseDto;
import com.bactoria.toy1.domain.post.dto.PostResponseDto;
import com.bactoria.toy1.domain.post.dto.PostSaveRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
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

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest({WebSecurityConfig.class, PostController.class, AppConfig.class})
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

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void 게시글들의_제목과_부제목을_불러온다() throws Exception {

        // given
        final String TITLE1 = "제목1";
        final String SUB_TITLE1 = "# 1";

        final String TITLE2 = "제목2";
        final String SUB_TITLE2 = "# 2";

        final long CATEGORY_ID = 1L;
        final String CATEGORY_NAME = "카테고리1";
        final Category category = getCategory(CATEGORY_ID, CATEGORY_NAME);

        PostMinResponseDto dto1 = new PostMinResponseDto();
        dto1.setId(1L);
        dto1.setCategory(category);
        dto1.setTitle(TITLE1);
        dto1.setSubTitle(SUB_TITLE1);

        PostMinResponseDto dto2 = new PostMinResponseDto();
        dto2.setId(2L);
        dto2.setCategory(category);
        dto2.setTitle(TITLE2);
        dto2.setSubTitle(SUB_TITLE2);

        Page<PostMinResponseDto> postPage = new PageImpl<>(Arrays.asList(
                dto1, dto2
        ));

        given(postServiceMock.resPostsMin(any())).willReturn(postPage);

        // when
        mockMvc.perform(get("/api/posts")
                .accept(MediaType.APPLICATION_JSON_UTF8))

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title", is(TITLE1)))
                .andExpect(jsonPath("$.content[0].subTitle", is(SUB_TITLE1)))
                .andExpect(jsonPath("$.content[0].category.name", is(CATEGORY_NAME)))
                .andExpect(jsonPath("$.content[1].title", is(TITLE2)))
                .andExpect(jsonPath("$.content[1].subTitle", is(SUB_TITLE2)))
                .andExpect(jsonPath("$.content[1].category.name", is(CATEGORY_NAME)));
    }

    private Category getCategory(long id, String name) throws Exception {
        final Category category = Category.builder().name(name).build();
        Field field = category.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(category, id);

        return category;
    }

    @Test
    public void 특정_게시글을_불러온다() throws Exception {

        // given
        final String TITLE = "제목";
        final String CONTENT = "내용";

        PostResponseDto dto = new PostResponseDto();
        dto.setCategory(Category.builder().name("카테고리1").build());
        dto.setTitle(TITLE);
        dto.setContent(CONTENT);

        given(postServiceMock.resPostsById(anyLong())).willReturn(dto);

        // when
        mockMvc.perform(get("/api/posts/1")
                .accept(MediaType.APPLICATION_JSON_UTF8))

                // then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is(TITLE)))
                .andExpect(jsonPath("$.content", is(CONTENT)));

    }

    @Test
    public void 특정_카테고리의_게시글들의_제목과_부제목을_불러온다() throws Exception {

        // given
        final String TITLE1 = "제목1";
        final String SUB_TITLE1 = "# 1";

        final String TITLE2 = "제목2";
        final String SUB_TITLE2 = "# 2";

        final long CATEGORY_ID = 1L;
        final String CATEGORY_NAME = "카테고리1";
        final Category category = getCategory(CATEGORY_ID, CATEGORY_NAME);

        PostMinResponseDto dto1 = new PostMinResponseDto();
        dto1.setId(1L);
        dto1.setCategory(category);
        dto1.setTitle(TITLE1);
        dto1.setSubTitle(SUB_TITLE1);

        PostMinResponseDto dto2 = new PostMinResponseDto();
        dto2.setId(2L);
        dto2.setCategory(category);
        dto2.setTitle(TITLE2);
        dto2.setSubTitle(SUB_TITLE2);

        Page<PostMinResponseDto> postPage = new PageImpl<>(Arrays.asList(
                dto1, dto2
        ));

        given(postServiceMock.resPostsByCategory(anyLong(), any(Pageable.class))).willReturn(postPage);

        // when
        mockMvc.perform(get("/api/posts/categories/1")
                .accept(MediaType.APPLICATION_JSON_UTF8))

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title", is(TITLE1)))
                .andExpect(jsonPath("$.content[0].subTitle", is(SUB_TITLE1)))
                .andExpect(jsonPath("$.content[0].category.name", is(CATEGORY_NAME)))
                .andExpect(jsonPath("$.content[1].title", is(TITLE2)))
                .andExpect(jsonPath("$.content[1].subTitle", is(SUB_TITLE2)))
                .andExpect(jsonPath("$.content[1].category.name", is(CATEGORY_NAME)));
    }

    @Test
    public void 한글_검색이_가능하다() throws Exception {

        // given
        final String SEARCH_DATA = "검색";

        final String TITLE = "제목";
        final String SUB_TITLE = "# 1";

        final long CATEGORY_ID = 1L;
        final String CATEGORY_NAME = "카테고리1";
        final Category category = getCategory(CATEGORY_ID, CATEGORY_NAME);

        PostMinResponseDto dto = new PostMinResponseDto();
        dto.setId(1L);
        dto.setCategory(category);
        dto.setTitle(TITLE);
        dto.setSubTitle(SUB_TITLE);

        Page<PostMinResponseDto> postPage = new PageImpl<>(Arrays.asList(
                dto
        ));

        given(postServiceMock.resPostBySearchData(anyString(), isA(Pageable.class))).willReturn(postPage);

        supportKoreanOnMockMvc();

        // when
        mockMvc.perform(get("/api/posts/search/" + SEARCH_DATA)
                .accept(MediaType.APPLICATION_JSON_UTF8))

                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content[0].title", is(TITLE)));
    }

    // mockMvc 한글깨짐 해결
    private void supportKoreanOnMockMvc() {
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
    public void 인증한_사용자가_게시글_추가하면_201_Created() throws Exception {

        // given
        final String POST_TITLE = "제목";
        final String POST_CONTENT = "내용";
        final String CATEGORY_NAME = "카테고오리";

        Category category = Category.builder().name(CATEGORY_NAME).build();
        PostSaveRequestDto dto = PostSaveRequestDto.builder().content(POST_CONTENT).title(POST_TITLE).category(category).build();

        Post post = Post.builder().category(category).title("제목").content("내용").build();
        PostResponseDto responseDto = modelMapper.map(post, PostResponseDto.class);

        given(postServiceMock.savePost(any(PostSaveRequestDto.class))).willReturn(responseDto);

        // when
        mockMvc.perform(post("/api/posts")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(dto)))

                // then
                .andExpect(status().isCreated());
    }

    @Test
    public void 인증하지않은_사용자가_게시글_변경하면_401_Unauthorized() throws Exception {
        mockMvc.perform(put("/api/posts"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void 인증한_사용자가_게시글_변경하면_204_NoContent() throws Exception {

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
                .content(objectMapper.writeValueAsString(dto)))

                // then
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    public void 인증한_사용자가_게시글_삭제하면_204_NoContent() throws Exception {
        // given
        final int ID = 1;

        // when
        mockMvc.perform(delete("/api/posts/" + ID))

                // then
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    public void 게시글_추가시_제목이_null이면_400_BadRequest() throws Exception {

        // given
        final String CONTENT = "내용";
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .content(CONTENT)
                .build();

        // when
        mockMvc.perform(post("/api/posts")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(requestDto)))

                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field",is("title")))
                .andExpect(jsonPath("$[0].code",is("NotBlank")));
    }

    @Test
    @WithMockUser
    public void 게시글_추가시_제목이_비어있으면_400_BadRequest() throws Exception {

        // given
        final String TITLE = "";
        final String CONTENT = "내용";
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(TITLE)
                .content(CONTENT)
                .build();

        // when
        mockMvc.perform(post("/api/posts")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(requestDto)))

                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field",is("title")))
                .andExpect(jsonPath("$[0].code",is("NotBlank")));
    }

    @Test
    @WithMockUser
    public void 게시글_추가시_제목이_공백이면_400_BadRequest() throws Exception {

        // given
        final String TITLE = " ";
        final String CONTENT = "내용";
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(TITLE)
                .content(CONTENT)
                .build();

        // when
        mockMvc.perform(post("/api/posts")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(requestDto)))

                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field",is("title")))
                .andExpect(jsonPath("$[0].code",is("NotBlank")));
    }

    @Test
    @WithMockUser
    public void 게시글_추가시_내용이_Null이면_400_BadRequest() throws Exception {

        // given
        final String TITLE = "제목";
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(TITLE)
                .build();

        // when
        mockMvc.perform(post("/api/posts")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(requestDto)))

                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field",is("content")))
                .andExpect(jsonPath("$[0].code",is("NotBlank")));
    }

    @Test
    @WithMockUser
    public void 게시글_추가시_내용이_비어있으면_400_BadRequest() throws Exception {

        // given
        final String TITLE = "제목";
        final String CONTENT = "";
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(TITLE)
                .content(CONTENT)
                .build();

        // when
        mockMvc.perform(post("/api/posts")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(requestDto)))

                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field",is("content")))
                .andExpect(jsonPath("$[0].code",is("NotBlank")));
    }

    @Test
    @WithMockUser
    public void 게시글_추가시_내용이_공백이면_400_BadRequest() throws Exception {

        // given
        final String TITLE = "제목";
        final String CONTENT = " ";
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(TITLE)
                .content(CONTENT)
                .build();

        // when
        mockMvc.perform(post("/api/posts")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(requestDto)))

                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field",is("content")))
                .andExpect(jsonPath("$[0].code",is("NotBlank")));
    }
}
