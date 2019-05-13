package com.bactoria.toy1.domain.post;

import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.post.dto.PostModifyRequestDto;
import com.bactoria.toy1.domain.post.dto.PostResponseDto;
import com.bactoria.toy1.domain.post.dto.PostSaveRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PostServiceTest {

    private PostRepository postRepositoryMock;
    private PostService postService;
    private ModelMapper modelMapper = new ModelMapper();

    private Category category = Category.builder().name("카테고리").build();

    @Before
    public void setup() {
        this.postRepositoryMock = Mockito.mock(PostRepository.class);
        this.postService = new PostService(postRepositoryMock, modelMapper);
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
    public void 특정_게시글을_정상적으로_불러온다() throws Exception {

        // given
        final Long ID = 1L;

        given(postRepositoryMock.findById(ID)).willReturn(
                Optional.of(Post.builder().title("제목1").build())
        );

        // when
        postService.resPostsById(ID);

        // then
        verify(postRepositoryMock).findById(ID);
    }

    @Test
    public void 게시글을_정상적으로_검색한다() {
        // given
        final String SEARCH_DATA = "검색데이터";
        final Pageable pageable = Pageable.unpaged();

        given(postRepositoryMock.findBySearchData(SEARCH_DATA, pageable)).willReturn(
                Page.empty()
        );

        // when
        postService.resPostBySearchData(SEARCH_DATA, Pageable.unpaged());

        // then
        verify(postRepositoryMock).findBySearchData(SEARCH_DATA, pageable);
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

        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(POST_TITLE)
                .content(POST_CONTENT)
                .category(category)
                .build();

        Post savedPost = Post.builder()
                .title(POST_TITLE)
                .content(POST_CONTENT)
                .category(category)
                .build();

        given(postRepositoryMock.save(any(Post.class))).willReturn(savedPost);

        // when
        PostResponseDto result = postService.savePost(requestDto);

        // then
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

        Post post = Post.builder().title("Old 제목").build();

        final PostModifyRequestDto DTO = PostModifyRequestDto.builder()
                .title(TITLE)
                .content(CONTENT)
                .category(category)
                .build();

        given(postRepositoryMock.findById(ID)).willReturn(Optional.of(post));

        // when
        postService.modifyPost(ID, DTO);

        // then
        verify(postRepositoryMock, times(1)).save(any(Post.class));
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
