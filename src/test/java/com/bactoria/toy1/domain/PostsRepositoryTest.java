package com.bactoria.toy1.domain;

import com.bactoria.toy1.domain.post.Post;
import com.bactoria.toy1.domain.post.PostRepository;
import org.junit.After;
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
                .author("bactoria@gmail.com")
                .build());

        //when 리스트 뽑아와
        List<Post> postsList = postsRepository.findAll();

        //then 리스트 중 하나 선택해서 테스트코드수행
        Post posts = postsList.get(0);
        assertTrue(posts.getCreatedDate().isAfter(now));
        assertTrue(posts.getModifiedDate().isAfter(now));

    }

}
