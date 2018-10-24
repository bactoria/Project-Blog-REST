package com.bactoria.toy1.controller;

import com.bactoria.toy1.domain.feed.FeedService;
import com.rometools.rome.feed.rss.Channel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/feed")
public class FeedController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedController.class);

    FeedService feedService;

    @GetMapping("")
    public Channel resFeed() {
        LOGGER.info("GET  /api/feed");
        return feedService.resFeed();
    }
}
