package com.bactoria.toy1.domain.post;

import com.bactoria.toy1.configuration.JpaAuditConfig;
import com.bactoria.toy1.domain.category.Category;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
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
    private PostRepository postRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    Category category;

    @Before
    public void setup() {
        category = Category.builder().name("카테고리").build();
        testEntityManager.persist(category);
    }

    @Test
    public void 게시글이_비어있다() {

        // given

        // when
        List<Post> result = postRepository.findAll();

        // then
        assertThat(result).isEmpty();
    }


    @Test
    public void 게시글을_저장하면_정상적으로_저장된다() {

        // given

        // when
        postRepository.save(getPost1());

        // then
        assertThat(postRepository.findAll()).isNotEmpty();
    }

    @Test
    public void 모든_게시글을_불러온다() {

        // given
        Post post1 = getPost1();
        testEntityManager.persist(post1);

        Post post2 = getPost2();
        testEntityManager.persist(post2);

        // when
        List<Post> postList = postRepository.findAll();

        // then
        assertThat(postList).hasSize(2);
        assertThat(postList).contains(post1, post2);
    }

    @Test
    public void 게시글을_저장하면_등록시간과_수정시간이_같이_저장된다() {

        // given
        Post post = getPost1();
        LocalDateTime now = LocalDateTime.now();

        // when
        postRepository.save(post);

        // then
        Post result = postRepository.getOne(post.getId());
        assertThat(result.getCreatedDate()).isAfter(now);
        assertThat(result.getModifiedDate()).isAfter(now);
    }

    @Test
    public void 특정_게시글의_제목을_수정하면_정상적으로_수정된다() {

        // given
        Post post = getPost1();
        testEntityManager.persist(post);
        final String POST_TITLE_NEW = "새로운 제목";

        // when
        postRepository.modifyPost(post.getId(), POST_TITLE_NEW, post.getContent(), post.getCategory());

        // then
        assertThat(postRepository.getOne(post.getId()).getTitle()).isEqualTo(POST_TITLE_NEW);
    }

    @Test
    public void 특정_게시글의_내용을_수정하면_정상적으로_수정된다() {

        // given
        Post post = getPost1();
        testEntityManager.persist(post);
        final String POST_CONTENT_NEW = "새로운 내용";

        // when
        postRepository.modifyPost(post.getId(), post.getTitle(), POST_CONTENT_NEW, post.getCategory());

        // then
        assertThat(postRepository.getOne(post.getId()).getContent()).isEqualTo(POST_CONTENT_NEW);
    }

/*
    @Test
    public void 특정_게시글을_수정하면_수정시간이_변경된다() {

        // given

        // when
        안된다... ㄷㄷ

        // then
        assertThat(postRepository.getOne(post.getId()).getModifiedDate()).isAfter(now);
    }
*/

    @Test
    public void 게시글들_중_제목이_포함되지_않은_검색어로_검색하면_게시글을_불러오지_않는다() {

        // given
        Post post1 = getPost1();
        testEntityManager.persist(post1);

        Post post2 = getPost2();
        testEntityManager.persist(post2);

        final String SEARCH_DATA = "목3";

        // when
        Page<Object[]> page = postRepository.findBySearchData(SEARCH_DATA, Pageable.unpaged());

        // then
        assertThat(page.getContent()).isEmpty();
    }

    @Test
    public void 게시글들_중_제목이_포함된_검색어로_검색하면_정상적으로_불러온다() {

        // given
        Post post1 = getPost1(); // Title : 제목1
        testEntityManager.persist(post1);

        Post post2 = getPost2(); // Title : 제목2
        testEntityManager.persist(post2);

        final String SEARCH_DATA = "목1";

        // when
        Page<Object[]> page = postRepository.findBySearchData(SEARCH_DATA, Pageable.unpaged());

        // then
        assertThat(page.getContent()).hasSize(1);
    }

    @After
    public void cleanup() {
        postRepository.deleteAll();
    }

    private Post getPost1() {
        return new Post().builder()
                .title("제목1")
                .content("내용1")
                .category(category)
                .build();
    }

    private Post getPost2() {
        return new Post().builder()
                .title("제목2")
                .content("내용2")
                .category(category)
                .build();
    }

}