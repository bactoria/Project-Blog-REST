package com.bactoria.toy1.domain.post;

import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.post.dto.PostModifyRequestDto;
import com.bactoria.toy1.domain.post.dto.PostSaveRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PostServiceTest {

    private PostRepository postRepositoryMock;
    private PostService postService;

    private Category category = Category.builder().name("카테고리").build();

    @Before
    public void setup() {
        this.postRepositoryMock = Mockito.mock(PostRepository.class);
        this.postService = new PostService(postRepositoryMock);
    }

    @Test
    public void Mocking이_정상적으로_만들어졌다() {
        assertThat(postRepositoryMock).isNotNull();
        assertThat(postService).isNotNull();
    }

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
    public void 특정_게시글을_정상적으로_불러온다() {

        // given
        final Long ID = 1L;

        // when
        postService.resPostsById(ID);

        // then
        verify(postRepositoryMock).findById(ID);
    }

    @Test
    public void 게시글을_정상적으로_검색한다() {
        // given
        final String SEARCH_DATA = "검색데이터";

        // when
        postService.resPostBySearchData(SEARCH_DATA, Pageable.unpaged());

        // then
        verify(postRepositoryMock).findBySearchData(SEARCH_DATA, Pageable.unpaged());
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

    @Test
    public void 게시글을_정상적으로_저장한다() {

        // given
        final String POST_TITLE = "제목";
        final String POST_CONTENT = "내용";

        PostSaveRequestDto postDto = PostSaveRequestDto.builder()
                .title(POST_TITLE)
                .content(POST_CONTENT)
                .category(category)
                .build();

        given(postRepositoryMock.save(any(Post.class))).willReturn(postDto.toEntity());

        // when
        Exception exception = null;
        Post result = null;

        try {
            result = postService.savePost(postDto);
        } catch (Exception e) {
            exception = e;
        }

        // then
        assertThat(exception).isNull();
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(POST_TITLE);
        assertThat(result.getContent()).isEqualTo(POST_CONTENT);
    }

    @Test
    public void 특정_게시글을_정상적으로_수정한다() {
        // given
        final Long ID = 1L;
        final String TITLE = "제목";
        final String CONTENT = "내용";

        final PostModifyRequestDto DTO = PostModifyRequestDto.builder()
                .title(TITLE)
                .content(CONTENT)
                .category(category)
                .build();

        // when
        postService.modifyPost(ID, DTO);

        // then
        verify(postRepositoryMock).modifyPost(ID, TITLE, CONTENT, category);
    }

    @Test
    public void 특정_게시글을_정상적으로_삭제한다() {
        // given
        final Long ID = 1L;

        // when
        postService.deletePost(ID);

        // then
        verify(postRepositoryMock).deleteById(ID);
    }

}
