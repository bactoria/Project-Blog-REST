package com.bactoria.toy1.domain.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public List<Category> resCategory() {
        return categoryRepository.findAll();
    }

    public Category saveCategory(CategorySaveRequestDto dto) {
        Category categoryDto = dto.toEntity();
        categoryRepository.save(categoryDto);
        return categoryDto;
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category resCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(new Category());
    }
}
