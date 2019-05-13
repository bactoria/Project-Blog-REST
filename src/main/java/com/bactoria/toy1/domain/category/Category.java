package com.bactoria.toy1.domain.category;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter @Getter
@Entity
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 30, nullable = false, unique = true)
    private String name;

    @Builder
    Category(String name) {
        this.name = name;
    }

}
