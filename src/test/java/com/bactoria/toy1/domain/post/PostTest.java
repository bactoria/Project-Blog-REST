package com.bactoria.toy1.domain.post;

import com.bactoria.toy1.domain.category.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostTest {

    @Test
    public void test001_Post_도메인_생성한다() {

        final String POST_TITLE = "POST_TITLE";
        final String POST_CONTENT = "POST_CONTENT";
        final Category category = Category.builder().build();

        Post post = Post.builder().title(POST_TITLE).content(POST_CONTENT).category(category).build();

        assertThat(post.getTitle()).isEqualTo(POST_TITLE);
        assertThat(post.getContent()).isEqualTo(POST_CONTENT);
        assertThat(post.getCategory()).isSameAs(category);
    }
}