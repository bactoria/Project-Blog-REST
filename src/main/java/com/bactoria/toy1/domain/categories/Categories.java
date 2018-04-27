package com.bactoria.toy1.domain.categories;

import com.bactoria.toy1.domain.posts.Posts;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED) //lombok
@Getter
@Entity
public class Categories {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 30, nullable = false) //javax.persistence
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Posts> postsList = new ArrayList<>();

    @Builder
    Categories(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
