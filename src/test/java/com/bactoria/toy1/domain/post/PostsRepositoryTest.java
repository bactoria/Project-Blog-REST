package com.bactoria.toy1.domain.post;

import com.bactoria.toy1.domain.category.Category;
import com.bactoria.toy1.domain.category.CategoryRepository;
import com.bactoria.toy1.domain.category.CategorySaveRequestDto;
import com.bactoria.toy1.domain.post.Post;
import com.bactoria.toy1.domain.post.PostRepository;
import com.bactoria.toy1.domain.post.PostSaveRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostRepository postsRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private Category category;

    @Before
    public void 카테고리_ID1() {
        category = categoryRepository.getOne((long)1);
    }

        @After
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {


        //given
        postsRepository.save(Post.builder()
                .title("제목입니다")
                .content("내용이다내요오오옹")
                .category(category)
                .build());

        //when
        List<Post> postsList = postsRepository.findAll();

        //then
        Post posts = postsList.get(0);
        assertThat(posts.getTitle(), is("제목입니다"));
        assertThat(posts.getContent(), is("내용이다내요오오옹"));

    }

    @Test
    public void BaseTimeEntity_등록() {
        //given  데이터 하나 넣자.

        LocalDateTime now = LocalDateTime.now();

        postsRepository.save(Post.builder()
                .title("타임등록하자")
                .content("타임내용이다")
                .category(category)
                .build());

        //when 리스트 뽑아와
        List<Post> postsList = postsRepository.findAll();

        //then 리스트 중 하나 선택해서 테스트코드수행
        Post posts = postsList.get(0);
        assertTrue(posts.getCreatedDate().isAfter(now));
        assertTrue(posts.getModifiedDate().isAfter(now));

    }

}
