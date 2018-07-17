package com.bactoria.toy1.domain.post;

import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.category.CategoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ImportResource("test/postRepositoryTest.sql")
@Transactional
public class PostRepositoryTest {

    @Autowired
    PostRepository postsRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private Category category;

    @Before
    public void 카테고리_ID1() {
        categoryRepository.save(category.builder().name("카테고리").build());
        category = categoryRepository.findAll().get(0);
    }

    @After
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void test001_게시글을_불러온다() {


        //given
        postsRepository.save(Post.builder()
                .title("test001_제목")
                .content("test001_내용")
                .category(category)
                .build());

        //when
        List<Post> postsList = postsRepository.findAll();

        //then
        Post post = postsList.get(0);
        assertThat(post.getTitle(), is("test001_제목"));
        assertThat(post.getContent(), is("test001_내용"));

    }

    @Test
    public void test002_게시글작성시_등록시간과_수정시간이_같이_저장된다() {
        //given  데이터 하나 넣자.

        LocalDateTime now = LocalDateTime.now();

        final long ID = postsRepository.save(Post.builder()
                .title("test002_제목")
                .content("test002_내용")
                .category(category)
                .build())
                .getId();

        //then 리스트 중 하나 선택해서 테스트코드수행
        Post post = postsRepository.getOne(ID);
        assertTrue(post.getCreatedDate().isAfter(now));
        assertTrue(post.getModifiedDate().isAfter(now));

    }

    @Test
    public void test003_게시글_제목과_내용을_수정한다() {

        final long ID = postsRepository.save(Post.builder()
                .title("test003_제목")
                .content("test003_내용")
                .category(category)
                .build())
                .getId();

        postsRepository.modifyPost(ID,"test003_제목_수정","test003_내용_수정",category);

        Post post = postsRepository.findById(ID).get();
        assertThat(post.getId(), is(ID));
        assertThat(post.getTitle(), is("test003_제목_수정"));
        assertThat(post.getContent(), is("test003_내용_수정"));

    }

    @Test
    public void test004_게시글을_검색_한다() {

        postsRepository.save(Post.builder()
                .title("test004_제목")
                .content("test004_내용")
                .category(category)
                .build());

        assertFalse(postsRepository.findBySearchData("test004_제").isEmpty());

    }

    @Test
    public void test005_게시글을_저장한다() {

        final String TITLE = "test005_제목";
        final String CONTENT = "test005_내용";

        final long ID = postsRepository.save(Post.builder()
                .title(TITLE)
                .content(CONTENT)
                .category(category)
                .build())
                .getId();

        assertThat(postsRepository.getOne(ID).getTitle(), is(TITLE));

    }


}
