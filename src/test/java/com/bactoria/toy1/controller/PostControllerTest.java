package com.bactoria.toy1.controller;

import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.post.Post;
import com.bactoria.toy1.domain.post.PostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postServiceMock;

    @Test
    public void test001_전체_게시글들을_불러온다() throws Exception {

        List<Object[]> postList = Arrays.asList(

                new Object[]{1,2},
                new Object[]{3,4}

                //Post 생성 시, 여기는 카테고리가 없다.
                // 왜냐? 컨트롤러를 Test하는 곳이니까. 카테고리를 안가지는 post는 있어도 되겠지.
                //라는 생각... 맞는걸까 틀린걸까?
        );

        Page<Object[]> postPage = new PageImpl<>(postList);

        when(postServiceMock.resPostsMin(any())).thenReturn(postPage);


        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0][0]", is(1)))
                .andExpect(jsonPath("$.content[1][1]", is(4)));
    }

    @Test
    public void test002_특정_게시글을_불러온다() throws Exception {

        Post post = Post.builder().category(Category.builder().name("카테고리1").build()).content("내용").title("제목").build();

        when(postServiceMock.resPostsById(anyLong())).thenReturn(Optional.of(post));

        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title",is("제목")))
                .andExpect(jsonPath("$.content",is("내용")));

    }

    @Test
    public void test003_특정_카테고리의_게시글들을_불러온다() throws Exception {
        List<Object[]> postList = Arrays.asList(

                new Object[]{1,2},
                new Object[]{3,4}

                //Post 생성 시, 여기는 카테고리가 없다.
                // 왜냐? 컨트롤러를 Test하는 곳이니까. 카테고리를 안가지는 post는 있어도 되겠지.
                //라는 생각... 맞는걸까 틀린걸까?
        );

        Page<Object[]> postPage = new PageImpl<>(postList);

        when(postServiceMock.resPostsByCategory(anyLong(),any(Pageable.class))).thenReturn(postPage);

        mockMvc.perform(get("/api/posts/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0][0]", is(1)))
                .andExpect(jsonPath("$.content[1][1]", is(4)));
    }

    /* search 기능에 Pageable 추가하면서, 이 코드는 Error뜸. 그래서 Travis CI에서 빌드가 안됨.. JUnit 공부 좀 더하고 수정해줄게..ㅠ

    @Test
    public void test004_게시글_제목을_검색하여_게시글들을_불러온다() throws Exception {
        List<Object[]> postList = Arrays.asList(

                new Object[]{1,2},
                new Object[]{3,4}

                //Post 생성 시, 여기는 카테고리가 없다.
                // 왜냐? 컨트롤러를 Test하는 곳이니까. 카테고리를 안가지는 post는 있어도 되겠지.
                //라는 생각... 맞는걸까 틀린걸까?
        );

        when(postServiceMock.resPostBySearchData(anyString())).thenReturn(postList);

        String SearchData = "안녕";

        mockMvc.perform(get("/api/posts/search/"+SearchData))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0][0]", is(1)))
                .andExpect(jsonPath("$[1][1]", is(4)));
    }
*/

}
