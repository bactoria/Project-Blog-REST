package com.bactoria.toy1.domain.category;

import com.bactoria.toy1.domain.category.dto.CategoryModifyRequestDto;
import com.bactoria.toy1.domain.category.dto.CategorySaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

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

    public void modifyCategory(Long id, CategoryModifyRequestDto dto) {
        categoryRepository.modifyCategory(id, dto.getName());
    }
}
