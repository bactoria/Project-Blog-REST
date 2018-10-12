package com.bactoria.toy1.domain.category;

import com.bactoria.toy1.domain.category.dto.CategorySaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void test001_카테고리가_비어있다() {
        //given
        List<Category> categories = categoryRepository.findAll();

        //then
        assertThat(categories).isEmpty();
    }

    @Test
    public void test002_카테고리를_생성한다() {

        //given
        String categoryName = "카테고리1";
        Category category = new CategorySaveRequestDto(categoryName).toEntity();

        //when
        categoryRepository.save(category);

        //then
        final Long ID = categoryRepository.findAll().get(0).getId();
        assertThat(categoryRepository.findById(ID).get().getName()).isEqualTo(categoryName);
    }

    @Test
    public void test003_카테고리를_삭제한다() {

        //given
        Category category = new CategorySaveRequestDto("test003_카테고리").toEntity();
        categoryRepository.save(category);

        //when
        categoryRepository.deleteAll();

        //then
        assertThat(categoryRepository.findAll()).isEmpty();
    }

    @Test
    public void test004_카테고리의_이름을_수정한다() {
        //given

        String oldCategoryName = "old_카테고리";
        String newCategoryName = "new_카테고리";

        Category category = new CategorySaveRequestDto(oldCategoryName).toEntity();
        categoryRepository.save(category);
        final Long ID = categoryRepository.findAll().get(0).getId();

        //when
        categoryRepository.modifyCategory(ID, newCategoryName);

        //then
        assertThat(categoryRepository.findById(ID).get().getName()).isEqualTo(newCategoryName);
    }

    @After
    public void 모든_카테고리_삭제한다() {
        categoryRepository.deleteAll();
    }
}
