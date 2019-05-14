package com.bactoria.toy1.domain.category;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityExistsException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    TestEntityManager testEntityManager;

    Category category;

    @Before
    public void setup() {
        category = Category.builder().name("카테고리").build();
    }

    @Test
    public void test001_카테고리가_비어있다() {
        //when
        List<Category> categories = categoryRepository.findAll();

        //then
        assertThat(categories).isEmpty();
    }

    @Test
    public void 카테고리를_저장한다() {

        // given

        // when
        categoryRepository.save(category);

        // then
        assertThat(categoryRepository.getOne(category.getId())).isEqualTo(category);
    }

    @Test
    public void test003_카테고리를_삭제한다() {

        // given
        testEntityManager.persist(category);

        // when
        categoryRepository.deleteAll();

        // then
        assertThat(categoryRepository.findAll()).isEmpty();
    }

    @Test
    public void 카테고리의_이름을_수정한다() {
        //given
        final String CATEGORY_NAME_NEW = "새로운 카테고리";
        testEntityManager.persist(category);

        Category savedCategory = categoryRepository.findById(category.getId())
                .orElseThrow(EntityExistsException::new);

        savedCategory.setName(CATEGORY_NAME_NEW);

        //when
        categoryRepository.save(savedCategory);

        //then
        assertThat(categoryRepository.getOne(category.getId()).getName()).isEqualTo(CATEGORY_NAME_NEW);
    }
}
