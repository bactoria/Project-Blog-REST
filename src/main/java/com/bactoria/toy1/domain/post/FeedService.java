package com.bactoria.toy1.domain.post;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class FeedService {

    PostRepository postRepository;

    public List<Post> resFeed() {
        return postRepository.findLimit5();
    }
}
