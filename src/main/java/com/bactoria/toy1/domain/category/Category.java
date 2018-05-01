package com.bactoria.toy1.domain.category;

import com.bactoria.toy1.domain.post.Post;
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
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 30, nullable = false, unique = true) //javax.persistence
    private String name;

/*
    @OneToMany(mappedBy = "category")
    private List<Post> postList = new ArrayList<>();
*/

    @Builder
    Category(String name) {
        this.name = name;
    }

}
