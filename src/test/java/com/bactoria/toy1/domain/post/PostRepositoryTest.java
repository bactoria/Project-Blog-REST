package com.bactoria.toy1.domain.post;

import com.bactoria.toy1.config.JpaAuditConfig;
import com.bactoria.toy1.domain.category.Category;
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

import javax.persistence.EntityExistsException;
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
        Post post = getPost("제목", "부제", "내용", category);

        // when
        postRepository.save(post);

        // then
        assertThat(postRepository.findAll()).isNotEmpty();
    }

    private Post getPost(String title, String subTitle, String content, Category category) {
        return Post.builder()
                .title(title)
                .subTitle(subTitle)
                .content(content)
                .category(category)
                .build();
    }

    @Test
    public void 모든_게시글을_불러온다() {

        // given
        Post post1 = getPost("제목1", "부제1", "내용1", category);
        testEntityManager.persist(post1);

        Post post2 = getPost("제목2", "부제2", "내용2", category);
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
        Post post = getPost("제목", "부제", "내용", category);
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
        final String POST_TITLE_NEW = "새로운 제목";

        Post post = getPost("제목", "부제", "내용", category);
        testEntityManager.persist(post);
        Post savedPost = postRepository.findById(post.getId())
                .orElseThrow(EntityExistsException::new);
        savedPost.setTitle(POST_TITLE_NEW);

        // when
        postRepository.save(savedPost);

        // then
        assertThat(postRepository.getOne(post.getId()).getTitle()).isEqualTo(POST_TITLE_NEW);
    }

    @Test
    public void 특정_게시글의_내용을_수정하면_정상적으로_수정된다() {

        // given
        final String POST_CONTENT_NEW = "새로운 내용";

        Post post = getPost("제목", "부제", "내용", category);
        testEntityManager.persist(post);
        Post savedPost = postRepository.findById(post.getId())
                .orElseThrow(EntityExistsException::new);
        savedPost.setContent(POST_CONTENT_NEW);

        // when
        postRepository.save(savedPost);

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
        Post post1 = getPost("제목1", "부제1", "내용1", category);
        testEntityManager.persist(post1);

        Post post2 = getPost("제목2", "부제2", "내용2", category);
        testEntityManager.persist(post2);

        final String SEARCH_DATA = "제목3";

        // when
        Page<Post> page = postRepository.findBySearchData(SEARCH_DATA, Pageable.unpaged());

        // then
        assertThat(page.getContent()).isEmpty();
    }

    @Test
    public void 게시글들_중_제목이_포함된_검색어로_검색하면_정상적으로_불러온다() {
        // given
        final String TITLE = "제목1";
        final String SEARCH_DATA = "목1";

        Post post1 = getPost(TITLE, "부제1", "내용1", category);
        testEntityManager.persist(post1);

        Post post2 = getPost("제목2", "부제2", "내용2", category);
        testEntityManager.persist(post2);

        // when
        Page<Post> page = postRepository.findBySearchData(SEARCH_DATA, Pageable.unpaged());

        // then
        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getContent().get(0).getTitle()).isEqualTo(TITLE);
    }

}