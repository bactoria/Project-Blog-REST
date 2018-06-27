package com.bactoria.toy1.domain.category;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before //Before 가 맞나 After가 맞나..
    public void 모든_카테고리_삭제한다() {
        categoryRepository.deleteAll();
    }

    @Test
    public void test001_카테고리가_비어있다() {

        List<Category> categories = categoryRepository.findAll();

        assertTrue(categories.isEmpty());
    }

    @Test
    public void test002_카테고리를_생성한다() {

        Category category = new CategorySaveRequestDto("카테고리1").toEntity();
        categoryRepository.save(category);

        final Long ID = categoryRepository.findAll().get(0).getId();

        assertThat(categoryRepository.findById(ID).get().getName(), is("카테고리1"));
    }

    @Test
    public void test003_카테고리를_삭제한다() {
        Category category = new CategorySaveRequestDto("test003_카테고리").toEntity();
        categoryRepository.save(category);

        assertFalse(categoryRepository.findAll().isEmpty());

        categoryRepository.deleteAll();

        assertTrue(categoryRepository.findAll().isEmpty());

    }

    @Test
    public void test004_카테고리의_이름을_수정한다() {
        //given
        Category category = new CategorySaveRequestDto("test004_카테고리").toEntity();
        categoryRepository.save(category);

        final Long ID = categoryRepository.findAll().get(0).getId();

        categoryRepository.modifyCategory(ID,"category004");

        assertThat(categoryRepository.findById(ID).get().getName(), is("category004"));
    }

}
