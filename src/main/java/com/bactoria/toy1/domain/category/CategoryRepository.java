package com.bactoria.toy1.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "update Category c set c.name = ?2 where c.id = ?1")
    void modifyCategory(Long id, String name);
}
