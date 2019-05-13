package com.bactoria.toy1.feed;

import com.bactoria.toy1.configuration.WebSecurityConfig;
import com.bactoria.toy1.feed.FeedController;
import com.bactoria.toy1.feed.FeedService;
import com.rometools.rome.feed.rss.Channel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest({FeedController.class, WebSecurityConfig.class})
public class FeedControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FeedController feedController;

    @MockBean
    FeedService feedService;

    @Test
    public void Feed_요청하면_Channel_반환한다() throws Exception {

        // given
        Channel channel = new Channel();
        channel.setFeedType("rss");

        given(feedService.resFeed()).willReturn(channel);

        // when
        mockMvc.perform(get("/api/feed")
                .accept(MediaType.APPLICATION_JSON_UTF8))

                //then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.feedType",is("rss")));
    }
}