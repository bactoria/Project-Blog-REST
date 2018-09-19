package com.bactoria.toy1.web;

import com.bactoria.toy1.domain.post.FeedService;
import com.bactoria.toy1.domain.post.Post;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api/feed")
public class FeedController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebRestController.class);

    FeedService feedService;

    @GetMapping("")
    public List<Object[]> resFeed() {
        LOGGER.info("GET  /api/posts");
        return feedService.resFeed();
    }
}
