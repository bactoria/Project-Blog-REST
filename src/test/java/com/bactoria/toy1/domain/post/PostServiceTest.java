package com.bactoria.toy1.domain.post;

import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.post.dto.PostSaveRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {

    @MockBean
    private PostRepository postRepositoryMock;

    @Autowired
    private PostService postService;

    private Category category = Category.builder().name("카테고리").build();

    @Test
    public void test001_모든_게시글들을_불러온다() {

        given(postRepositoryMock.findAll()).willReturn(Arrays.asList(
                Post.builder().title("제목1").build(),
                Post.builder().title("제목2").build()
        ));

        List<Post> postList = postService.resPosts();

        verify(postRepositoryMock, times(1)).findAll();
        assertThat(postList).hasSize(2);
        assertThat(postList.get(0).getTitle()).isEqualTo("제목1");
        assertThat(postList.get(1).getTitle()).isEqualTo("제목2");

    }

    @Test
    public void test002_게시글_저장_시_제목이_null이면_예외() {

        Exception exception = null;

        try {
            postService.savePost(PostSaveRequestDto.builder()
                    .content("내용")
                    .category(category)
                    .build());
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void test003_게시글_저장_시_제목이_비어있으면_예외() {

        Exception exception = null;

        try {
            postService.savePost(PostSaveRequestDto.builder()
                    .title("")
                    .content("내용")
                    .category(category)
                    .build());
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void test004_게시글_저장_시_제목이_공백이면_예외() {

        Exception exception = null;

        try {
            postService.savePost(PostSaveRequestDto.builder()
                    .title(" ")
                    .content("내용")
                    .category(category)
                    .build());
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void test005_게시글_저장_시_내용이_null이면_예외() {

        Exception exception = null;

        try {
            postService.savePost(PostSaveRequestDto.builder()
                    .title("제목")
                    .category(category)
                    .build());
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void test006_게시글_저장_시_내용이_비어있으면_예외() {

        Exception exception = null;

        try {
            postService.savePost(PostSaveRequestDto.builder()
                    .title("제목")
                    .content("")
                    .category(category)
                    .build());
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void test007_게시글_저장_시_내용이_공백이면_예외() {

        Exception exception = null;

        try {
            postService.savePost(PostSaveRequestDto.builder()
                    .title("제목")
                    .content(" ")
                    .category(category)
                    .build());
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void test008_게시글_저장_시_카테고리가_null이면_예외() {

        Exception exception = null;

        try {
            postService.savePost(PostSaveRequestDto.builder()
                    .title("제목")
                    .content("내용")
                    .build());
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

}
