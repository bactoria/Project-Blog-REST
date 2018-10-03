package com.bactoria.toy1.web;

import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.category.CategorySaveRequestDto;
import com.bactoria.toy1.domain.category.CategoryService;
import com.bactoria.toy1.domain.post.Post;
import com.bactoria.toy1.domain.post.PostSaveRequestDto;
import com.bactoria.toy1.domain.post.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminControllerTest {

    @InjectMocks
    AdminController adminController;

    @Mock
    CategoryService categoryServiceMock;

    @Mock
    PostService postServiceMock;

    MockMvc mockMvc;

    Category category;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(adminController)
                .build();

        category = Category.builder().name("카테고리1").build();

    }

    private String jsonStringFromObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Test
    public void test001_카테고리_추가한다() throws Exception {

        CategorySaveRequestDto dto = CategorySaveRequestDto.builder().name("카테고리1").build();

        String dtoJson = jsonStringFromObject(dto);

        when(categoryServiceMock.saveCategory(any(CategorySaveRequestDto.class))).thenReturn(category);

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJson))

                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name",is("카테고리1")));

    }

    @Test
    public void test002_게시글_추가한다() throws Exception {

        String title = "제목";
        String content = "내용";

        PostSaveRequestDto dto = PostSaveRequestDto.builder().content(content).title(title).category(category).build();

        String dtoJson = jsonStringFromObject(dto);

        when(postServiceMock.savePost(any(PostSaveRequestDto.class))).thenReturn( Post.builder().title("제목").content("내용").build() );

        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJson))

                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title",is("제목")));

    }

}
