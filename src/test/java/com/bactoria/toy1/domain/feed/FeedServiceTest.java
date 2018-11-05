package com.bactoria.toy1.domain.feed;

import com.bactoria.toy1.domain.post.Post;
import com.bactoria.toy1.domain.post.PostRepository;
import com.rometools.rome.feed.rss.Channel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class FeedServiceTest {

    private PostRepository postRepositoryMock;

    private FeedService feedService;

    @Before
    public void setup() {
        this.postRepositoryMock = Mockito.mock(PostRepository.class);
        this.feedService = new FeedService(postRepositoryMock);
    }

    @Test
    public void Mocking이_정상적으로_이루어진다() {
        assertThat(postRepositoryMock).isNotNull();
        assertThat(feedService).isNotNull();
    }

    @Test
    public void feed를_요청하면_rss2_타입으로_반환한다() {
        //given
        List<Post> postList = Arrays.asList();

        given(postRepositoryMock.findInFive()).willReturn(postList);

        //when
        Channel channel = feedService.resFeed();
        System.out.println(channel);
        assertThat(channel.getFeedType()).isEqualTo("rss_2.0");
    }

    @Test
    public void feed를_요청하면_feed의_링크도_함께_반환한다() {
        //given
        List<Post> postList = Arrays.asList();

        given(postRepositoryMock.findInFive()).willReturn(postList);

        //when
        Channel channel = feedService.resFeed();
        System.out.println(channel);
        assertThat(channel.getLink()).isEqualTo("https://bactoria.me/api/feed");
    }

    @Test
    public void feed를_요청하면_feed의_title도_함께_반환한다() {
        //given
        List<Post> postList = Arrays.asList();

        given(postRepositoryMock.findInFive()).willReturn(postList);

        //when
        Channel channel = feedService.resFeed();
        System.out.println(channel);
        assertThat(channel.getTitle()).isEqualTo("Bactoria 블로그");
    }

}