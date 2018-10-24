package com.bactoria.toy1.domain.post;

import com.bactoria.toy1.configuration.JpaAuditConfig;
import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.category.CategoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(JpaAuditConfig.class)
public class PostRepositoryTest {

    @Autowired
    private PostRepository postsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @Before
    public void setup() {
        categoryRepository.save(category.builder().name("카테고리").build());
        category = categoryRepository.findAll().get(0);
    }

    @Test
    public void test001_게시글을_불러온다() {

        //given
        final String TITLE = "test001_제목";
        final String CONTENT = "test001_내용";

        postsRepository.save(Post.builder()
                .title(TITLE)
                .content(CONTENT)
                .category(category)
                .build());

        //when
        List<Post> postsList = postsRepository.findAll();

        //then
        Post post = postsList.get(0);
        assertThat(post.getTitle()).isEqualTo(TITLE);
        assertThat(post.getContent()).isEqualTo(CONTENT);
        assertThat(post.getCategory()).isEqualTo(category);
    }

    @Test
    public void test002_게시글작성시_등록시간과_수정시간이_같이_저장된다() {

        //given
        LocalDateTime now = LocalDateTime.now();

        //when
        final long ID = postsRepository.save(Post.builder()
                .title("test002_제목")
                .content("test002_내용")
                .category(category)
                .build())
                .getId();

        //then
        Post post = postsRepository.getOne(ID);
        assertThat(post.getCreatedDate().isAfter(now)).isTrue();
        assertThat(post.getModifiedDate().isAfter(now)).isTrue();
    }

    @Test
    public void test003_게시글_제목과_내용을_수정한다() {

        //given
        final String TITLE = "test003_제목";
        final String CONTENT = "test003_내용";

        final long ID = postsRepository.save(Post.builder()
                .title(TITLE)
                .content(CONTENT)
                .category(category)
                .build())
                .getId();

        //when
        postsRepository.modifyPost(ID, TITLE, CONTENT, category);

        //then
        Post post = postsRepository.findById(ID).get();
        assertThat(post.getId()).isEqualTo(ID);
        assertThat(post.getTitle()).isEqualTo(TITLE);
        assertThat(post.getContent()).isEqualTo(CONTENT);
    }

    @Test
    public void test004_게시글을_검색_한다() {

        final String POST_TITLE = "test004_제목";
        final String POST_CONTENT = "test004_내용";
        final String SEARCH_DATA = POST_TITLE.substring(5);

        postsRepository.save(Post.builder()
                .title(POST_TITLE)
                .content(POST_CONTENT)
                .category(category)
                .build());

        assertThat(postsRepository.findBySearchData(SEARCH_DATA, Pageable.unpaged())).isNotEmpty();
        assertThat(postsRepository.findBySearchData(SEARCH_DATA, Pageable.unpaged()).getContent().get(0)[2]).isEqualTo(POST_TITLE);
    }

    @Test
    public void test005_게시글을_저장한다() {

        //given
        final String TITLE = "test005_제목";
        final String CONTENT = "test005_내용";

        //when
        postsRepository.save(Post.builder()
                .title(TITLE)
                .content(CONTENT)
                .category(category)
                .build());


        //then
        Post post = postsRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo(TITLE);
        assertThat(post.getContent()).isEqualTo(CONTENT);
        assertThat(post.getCategory()).isEqualTo(category);
    }

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

}