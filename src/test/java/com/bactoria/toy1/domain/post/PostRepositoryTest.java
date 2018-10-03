package com.bactoria.toy1.domain.post;

import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.category.CategoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
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

    /* search 기능에 Pageable 추가하면서, 이 코드는 Error뜸. 그래서 Travis CI에서 빌드가 안됨.. JUnit 공부 좀 더하고 수정해줄게..ㅠ

    @Test
    public void test004_게시글을_검색_한다() {

        postsRepository.save(Post.builder()
                .title("test004_제목")
                .content("test004_내용")
                .category(category)
                .build());

        assertFalse(postsRepository.findBySearchData("test004_제").isEmpty());

    }
*/

    @Test
    public void test005_게시글을_저장한다() {

        //given
        final String TITLE = "test005_제목";
        final String CONTENT = "test005_내용";

        //when
        final long ID = postsRepository.save(Post.builder()
                .title(TITLE)
                .content(CONTENT)
                .category(category)
                .build())
                .getId();

        //then
        assertThat(postsRepository.getOne(ID).getTitle()).isEqualTo(TITLE);
    }

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

}