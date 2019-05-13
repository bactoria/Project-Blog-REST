package com.bactoria.toy1.domain.category;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter @Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 30, nullable = false, unique = true)
    private String name;
}
