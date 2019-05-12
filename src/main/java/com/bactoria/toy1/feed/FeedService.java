package com.bactoria.toy1.feed;

import com.bactoria.toy1.domain.post.Post;
import com.bactoria.toy1.domain.post.PostRepository;
import com.rometools.rome.feed.rss.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class FeedService {

    private PostRepository postRepository;

    private Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }

    public Channel resFeed() {

        List<Post> postList = postRepository.findInFive();

        Channel channel = new Channel();
        channel.setFeedType("rss_2.0");
        channel.setTitle("Bactoria 블로그");
        channel.setDescription("Bactoria 블로그입니다.");
        channel.setLink("https://bactoria.me/api/feed");
        channel.setGenerator("Bactoria Blog");

        Image image = new Image();
        image.setTitle("Bactoria 이미지");
        image.setUrl("https://avatars0.githubusercontent.com/u/25674959?s=460&amp;v=4");
        image.setLink("https://bactoria.me/api/feed");

        List<Item> itemList = new ArrayList<>();

        for (Post post : postList) {

            Item item = new Item();
            item.setTitle(post.getTitle());
            item.setLink("https://bactoria.me/post/" + post.getId());
            item.setPubDate(convertToDateViaSqlTimestamp(post.getCreatedDate()));

            List<Category> categoryList = new ArrayList<>();
            Category category = new Category();
            category.setValue(post.getCategory().getName());
            categoryList.add(category);
            item.setCategories(categoryList);

            Description description = new Description();
            description.setValue(post.getContent());
            item.setDescription(description);

            itemList.add(item);
        }

        channel.setItems(itemList);

        return channel;
    }
}
